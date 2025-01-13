package com.web.warehouse.sys.db.mysql.dao;

import com.web.warehouse.sys.db.TransactionManager;
import com.web.warehouse.sys.db.abstr.AllProductsDAO;
import com.web.warehouse.sys.db.abstr.ConnectionManager;
import com.web.warehouse.sys.db.mysql.DBException;
import com.web.warehouse.sys.entity.Product;
import org.jetbrains.annotations.NotNull;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class MySqlAllProductsDAO implements AllProductsDAO {
    private final ConnectionManager cm;
    private final TransactionManager tm;

    public MySqlAllProductsDAO(ConnectionManager cm, TransactionManager tm) {
        this.cm = cm;
        this.tm = tm;
    }

    @Override
    public Optional<Integer> getQuantity(long productId) {
        try (Connection con = cm.getConnection()) {
            String query = "select quantity from all_products where id = ?";
            return getColumnValue(con, query, "quantity", productId);
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    @Override
    public Optional<Product> getById(long id) {
        String query = "select * from all_products where id = ?";

        return getSingle(processQuery(cm, query, id));
    }

    @Override
    public List<Product> getByName(String name) {
        String query = "select * from all_products where name = ?";

        return processQuery(cm, query, name);
    }

    @Override
    public List<Product> getByNameLike(String pattern) {
        String query = "select * from all_products where name like ?";

        return processQuery(cm, query, pattern);
    }

    @Override
    public List<Product> getByCategoryLike(String pattern) {
        String query = "select * from all_products where category like ?";

        return processQuery(cm, query, pattern);
    }

    @Override
    public long getEmpty() {
        try (Connection con = cm.getConnection()) {
            String query = "select count(*) as count from all_products where quantity = 0";

            Optional<Long> countOptional = getColumnValue(con, query, "count");
            if (countOptional.isEmpty())
                throw new DBException();

            return countOptional.get();
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    @Override
    public void updateQuantity(long productId, int newQuantity) {
        String query = "update all_products " +
                "set quantity = ? " +
                "where id = ?";
        processUpdate(cm, query, newQuantity, productId);
    }

    @Override
    public void take(long productId, int quantity) {
        String query = "update all_products " +
                "set quantity = ? " +
                "where id = ?";

        Optional<?> quantityOpt = getQuantity(productId);
        if (quantityOpt.isEmpty()) {
            throw new DBException("product with such id " + productId + " does not exist in table 'al_products'");
        }
        processUpdate(cm, query, ((Number) quantityOpt.get()).intValue() - quantity, productId);
    }

    @Override
    public void add(Product product) {
        tm.executeTransaction(con -> insert(con, product));
    }

    public void insert(Connection con, Product product) {
        Objects.requireNonNull(con);
        Objects.requireNonNull(product);

        String query;
        if (product.hasId()) {
            query = "insert into all_products (id, name, price, category, quantity)" +
                    " value (?, ?, ?, ?, ?)";
            processUpdate(con, query,
                    product.getId(), product.getName(), product.getPrice(), product.getCategory(), product.getQuantity());
        } else {
            query = "insert into all_products (name, price, category, quantity)" +
                    " value (?, ?, ?, ?)";
            processUpdate(con, query,
                    product.getName(), product.getPrice(), product.getCategory(), product.getQuantity());
        }
    }

    @Override
    public List<Product> getAll() {
        String query = "select * from all_products";

        return processQuery(cm, query);
    }

    @Override
    public void addAll(Product[] entities) {
        if (entities == null || entities.length == 0) {
            return;
        }

        Arrays.stream(entities).forEach(ent ->
                tm.executeTransaction(con -> insert(con, ent))
        );
    }

    @Override
    public void remove(long productId) {
        String query = "delete from all_products where id = ?";
        processUpdate(cm, query, productId);
    }

    @Override
    @Transactional
    public synchronized void update(Product updated) {
        String query = "update all_products set name=?,price=?,category=?,quantity=? where id=?;";

        tm.executeTransaction(con ->
                processUpdate(con, query, updated.getName(), updated.getPrice(),
                            updated.getCategory(), updated.getQuantity(), updated.getId())
        );
    }

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
