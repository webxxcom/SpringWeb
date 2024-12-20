package ua.nure.st.kpp.example.demo.dao.mysql.dao;

import ua.nure.st.kpp.example.demo.dao.*;
import ua.nure.st.kpp.example.demo.dao.abstr.*;
import ua.nure.st.kpp.example.demo.entity.*;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public abstract class MySqlProductRelationshipTableDAO
        implements RelationshipTableDAO<ProductDescription, Product>, Processable<Product> {
    protected final String contentName;
    protected final String tableName;
    protected final String columnName;
    protected final String generatorTableName;
    protected final ConnectionManager cm;
    protected final TransactionManager tm;

    protected final RelationHandler rh;
    protected final GeneratorDAO generatorDAO;

    protected class GeneratorDAO {
        public void delete(long parentId) {
            tm.executeTransaction(con -> delete(con, parentId));
        }

        public void delete(Connection con, long generatorId) {
            String query = "delete from " + generatorTableName + " where " + columnName + " = ?";

            processUpdate(con, query, generatorId);
        }

        public long generateId(Connection con) {
            String query = "insert into " + generatorTableName + " value()";

            return ((Number) processUpdateAndGetGeneratedKeys(con, query).get(0)).longValue();
        }
    }

    protected class RelationHandler implements Processable<ProductDescription> {

        List<ProductDescription> getAll(long containerId){
            String query = "select product_id, quantity from " + tableName + " where " + columnName + " = ?";

            return processQuery(cm, query, containerId);
        }

        Optional<Long> getQuantity(long containerId, long productId) {
            String query = "select quantity from " + tableName +
                    " where product_id = ?" +
                    " and " + columnName + " = ?";

            return getColumnValue(cm, query, "quantity",
                    productId, containerId).map(o -> ((long) o));
        }

        @Override
        public ProductDescription map(@NotNull ResultSet rs) throws SQLException {
            return new ProductDescription(
                    rs.getInt("product_id"),
                    rs.getInt("quantity")
            );
        }
    }

    protected MySqlProductRelationshipTableDAO(ConnectionManager cm, String contentName) {
        this.cm = cm;
        this.tm = new TransactionManager(cm);

        this.contentName = contentName;
        this.tableName = contentName + "_items";
        this.columnName = contentName + "_id";
        this.generatorTableName = contentName + "s";
        this.rh = new RelationHandler();
        this.generatorDAO = new GeneratorDAO();
    }

    @Override
    public Optional<Product> getById(long customerId, long productId) {
        String query = String.format("SELECT\n" +
                "    pr.id,\n" +
                "    pr.name,\n" +
                "    dm.quantity,\n" +
                "    pr.price,\n" +
                "    pr.category,\n" +
                "    dm.%s\n" +
                "FROM\n" +
                "    all_products pr\n" +
                "JOIN\n" +
                "    %s dm ON pr.id = dm.product_id\n" +
                "WHERE\n" +
                "    pr.id = ? AND\n" +
                "    EXISTS (\n" +
                "        SELECT 1\n" +
                "        FROM\n" +
                "            customers c\n" +
                "        WHERE\n" +
                "            c.id = ? AND c.%s = dm.%s\n" +
                "    )", columnName, tableName, columnName, columnName);

        return getSingle(processQuery(cm, query, productId, customerId));
    }

    @Override
    public List<Product> getAll(long customerId) {
        String query = String.format(
                "SELECT\n" +
                        "    pr.id,\n" +
                        "    pr.name,\n" +
                        "    dm.quantity,\n" +
                        "    pr.price,\n" +
                        "    pr.category,\n" +
                        "    dm.%s\n" +
                        "FROM\n" +
                        "    all_products pr\n" +
                        "JOIN\n" +
                        "    %s dm ON pr.id = dm.product_id\n" +
                        "WHERE\n" +
                        "    EXISTS (\n" +
                        "        SELECT 1\n" +
                        "        FROM\n" +
                        "            customers c\n" +
                        "        WHERE\n" +
                        "            c.id = ? AND c.%s = dm.%s\n" +
                        "    )", columnName, tableName, columnName, columnName);

        return processQuery(cm, query, customerId);
    }

    @Override
    public List<ProductDescription> getAllRelations(long containerId) {
        return rh.getAll(containerId);
    }

    @Override
    public void insert(long cartId, ProductDescription rec) {
        Objects.requireNonNull(rec);

        try(Connection con = cm.getConnection()){
            insert(con, cartId, rec);
        }catch (SQLException ex){
            throw new DBException(ex);
        }
    }

    public void insert(Connection con, long id, ProductDescription rec) {
        Objects.requireNonNull(rec);

        String query;
        Object[] params;
        Optional<Long> quantity = rh.getQuantity(id, rec.id());
        if (quantity.isPresent()) {
            /* We should update quantity of the available product */
            query = "update " + tableName + " set quantity = ? " + " where " + columnName + " = ? and product_id = ?";
            long newQuantity = quantity.get() + rec.quantity();
            params = new Object[]{newQuantity, id, rec.id()};
        } else {
            /* Or add this product if not available */
            query = "insert into " + tableName +
                    "(" + columnName + ", product_id, quantity) " +
                    "value(?, ?, ?)";
            params = new Object[]{id, rec.id(), rec.quantity()};
        }
        processUpdate(con, query, params);
    }

    @Override
    public void insertAll(long cartId, List<ProductDescription> recs) {
        tm.executeTransaction(con -> insertAll(con, cartId, recs));
    }

    public void insertAll(Connection con, long parentId, List<ProductDescription> recs)  {
        Objects.requireNonNull(recs);
        recs.forEach(el -> insert(con, parentId, el));
    }

    public void delete(int containerId){
        generatorDAO.delete(containerId);
    }

    public void delete(Connection con, long containerId){
        generatorDAO.delete(con, containerId);
    }

    public void removeProduct(Connection con, long containerId, long productId){
        String query = "delete from " + tableName + " where " + columnName + " = ? and product_id = ?";

       processUpdate(con, query, containerId, productId);
    }

    @Override
    public long generateId(Connection con) {
        return generatorDAO.generateId(con);
    }

    @Contract("_ -> new")
    @Override
    public Product map(@NotNull ResultSet rs) throws SQLException {
        return new Product(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getBigDecimal("price"),
                rs.getInt("quantity"),
                rs.getString("category")
        );
    }
}
