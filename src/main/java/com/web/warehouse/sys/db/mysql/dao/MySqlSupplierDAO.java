package com.web.warehouse.sys.db.mysql.dao;

import com.web.warehouse.sys.db.TransactionManager;
import com.web.warehouse.sys.db.abstr.ConnectionManager;
import com.web.warehouse.sys.db.abstr.SupplierDAO;
import com.web.warehouse.sys.entity.Product;
import com.web.warehouse.sys.entity.Supplier;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public final class MySqlSupplierDAO implements SupplierDAO {
    private final MySqlProductRelationshipTableDAO supplierProductsDAO;
    private final ConnectionManager cm;
    private final TransactionManager tm;

    @Override
    public MySqlProductRelationshipTableDAO getSupplierProductsDAO() {
        return supplierProductsDAO != null
                ? supplierProductsDAO
                : new MySqlProductRelationshipTableDAO(cm, "supplier"){

            @Override
            public Optional<Product> getById(long customerId, long productId) {
                String query = String.format(
                        "SELECT\n" +
                                "    pr.id,\n" +
                                "    pr.name,\n" +
                                "    dm.quantity,\n" +
                                "    pr.price,\n" +
                                "    pr.category\n" +
                                "FROM\n" +
                                "    all_products pr\n" +
                                "JOIN\n" +
                                "    %s dm ON pr.id = dm.product_id\n" +
                                "WHERE\n" +
                                "    pr.id = ? AND\n" +
                                "    EXISTS (\n" +
                                "        SELECT 1\n" +
                                "        FROM\n" +
                                "            suppliers s\n" +
                                "        WHERE\n" +
                                "            s.id = ? AND s.id = dm.%s\n" +
                                "    )", tableName, columnName);

                return getSingle(processQuery(cm, query, productId, customerId));
            }

            @Override
            public List<Product> getAll(long supplierId) {
                String query = String.format(
                "SELECT\n" +
                        "    pr.id,\n" +
                        "    pr.name,\n" +
                        "    dm.quantity,\n" +
                        "    pr.price,\n" +
                        "    pr.category\n" +
                        "FROM\n" +
                        "    all_products pr\n" +
                        "JOIN\n" +
                        "    %s dm ON pr.id = dm.product_id\n" +
                        "WHERE\n" +
                        "    EXISTS (\n" +
                        "        SELECT 1\n" +
                        "        FROM\n" +
                        "            suppliers s\n" +
                        "        WHERE\n" +
                        "            s.id = ? AND s.id = dm.%s\n" +
                        "    )",tableName, columnName);

                return processQuery(cm, query, supplierId);
            }
        };
    }

    @Override
    public boolean emailExists(String email) {
        throw new RuntimeException();
    }

    public MySqlSupplierDAO(ConnectionManager cm, TransactionManager tm) {
        this.cm = cm;
        this.tm = tm;

        this.supplierProductsDAO = getSupplierProductsDAO();
    }

    @Override
    public Optional<Supplier> getById(long id) {
        String query = "select * from suppliers where id = ?";

        return getSingle(processQuery(cm, query, id));
    }

    @Override
    public List<Supplier> getByName(String name) {
        String query = "select * from suppliers where name = ?";

        return processQuery(cm, query, name);
    }

    @Contract(pure = true)
    @Override
    public List<Supplier> getByNameLike(String pattern) {
        String query = "select * from suppliers where name like ?";

        return processQuery(cm, query, pattern);
    }

    @Override
    public List<Supplier> getAll() {
        String query = "select * from suppliers";

        return processQuery(cm, query);
    }

    @Override
    public void addAll(Supplier... suppliers) {
        Objects.requireNonNull(suppliers);
        tm.executeTransaction(con ->{
            for(Supplier sp : suppliers)
                insert(con, sp);
        });
    }

    @Override
    public void add(Supplier supplier) {
        tm.executeTransaction(con ->
            insert(con, supplier)
        );
    }

    public void insert(Connection con, Supplier s) {
        Objects.requireNonNull(s);

        String query = "insert into suppliers(name, address, email) value(?, ?, ?)";
        List<Object> rs = processUpdateAndGetGeneratedKeys(con, query,
                s.getName(), s.getAddress(), s.getEmail());
        s.setId(((Number) rs.get(0)).longValue());

        if(!s.getProducts().isEmpty()){
            supplierProductsDAO.insertAll(con, s.getId(), s.getProducts());
        }
    }

    @Override
    public void remove(long supplierId) {
        tm.executeTransaction(con ->
            delete(con, supplierId)
        );
    }

    @Override
    public void update(Supplier updated) {
        throw new RuntimeException();
    }

    public void delete(Connection con, long supplierId){
        String query = "delete from suppliers where id = ?";
        processUpdate(con, query, supplierId);
    }

    @Contract("_ -> new")
    @Override
    public @NotNull Supplier map(@NotNull ResultSet rs) throws SQLException {
        long id = rs.getInt("id");

        return new Supplier(
                id,
                rs.getString("name"),
                rs.getString("address"),
                rs.getString("email"),
                supplierProductsDAO.getAllRelations(id)
        );
    }
}
