package com.web.warehouse.sys.db.mysql.dao;

import com.web.warehouse.sys.db.TransactionManager;
import com.web.warehouse.sys.db.abstr.ConnectionManager;
import com.web.warehouse.sys.db.abstr.CustomerDAO;
import com.web.warehouse.sys.db.mysql.DBException;
import com.web.warehouse.sys.entity.Customer;
import com.web.warehouse.sys.entity.ProductContainer;
import com.web.warehouse.sys.entity.ProductDescription;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.LongFunction;

public final class MySqlCustomerDAO implements CustomerDAO {
    private final MySqlProductRelationshipTableDAO cartItemsDAO;
    private final MySqlProductRelationshipTableDAO wishlistItemsDAO;
    private final ConnectionManager cm;
    private final TransactionManager tm;

    public MySqlCustomerDAO(ConnectionManager cm, TransactionManager tm) {
        this.cm = cm;
        this.tm = tm;

        this.cartItemsDAO = getSupplierProductsDAO();
        this.wishlistItemsDAO = getWishlistProductsDAO();
    }

    public MySqlProductRelationshipTableDAO getSupplierProductsDAO() {
        return cartItemsDAO != null
                ? cartItemsDAO
                : new MySqlProductRelationshipTableDAO(cm, "cart") {
        };
    }

    @Override
    public boolean emailExists(String email) {
        String query = "select count(*) as c from customers where email = ?";

        try (Connection con = cm.getConnection()) {
            Optional<Long> emails = getColumnValue(con, query, "c", email);
            return emails.isPresent() && emails.get() > 0;
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public MySqlProductRelationshipTableDAO getWishlistProductsDAO() {
        return wishlistItemsDAO != null
                ? wishlistItemsDAO
                : new MySqlProductRelationshipTableDAO(cm, "wishlist") {
        };
    }

    @Override
    public @NotNull Optional<Long> getCartId(long customerId) {
        try (Connection con = cm.getConnection()) {
            return getCartId(con, customerId);
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    private Optional<Long> getCartId(Connection con, long customerId) {
        String query = "SELECT cart_id FROM customers WHERE id = ?";

        return getColumnValue(con, query, "cart_id", customerId)
                .map(o -> ((Integer) o).longValue());
    }

    @Override
    public @NotNull Optional<Long> getWishlistId(long customerId) {
        try (Connection con = cm.getConnection()) {
            return getWishlistId(con, customerId);
        } catch (SQLException e) {
            throw new DBException("Error getting wishlist id for customer id = " + customerId);
        }
    }

    @Override
    public boolean isEmailInUse(String email, long excludeCustomerId) {
        String query = "SELECT COUNT(*) FROM customers WHERE email = ? AND id != ?";
        try (Connection con = cm.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            setParamsToPreparedStatement(ps, new Object[]{email, excludeCustomerId});
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            throw new DBException("Error checking for duplicate email", e);
        }
        return false;
    }

    public @NotNull Optional<Long> getWishlistId(Connection con, long customerId) {
        String query = "select wishlist_id from customers where id = ?";

        return getColumnValue(con, query, "wishlist_id", customerId)
                .map(o -> ((Integer) o).longValue());
    }

    @Override
    public void addToWishList(long customerId, ProductDescription prd) {
        tm.executeTransaction(con -> {
            long wishlistId;
            Optional<Long> wishlistIdOpt = getWishlistId(con, customerId);
            if (wishlistIdOpt.isEmpty()) {
                wishlistId = cartItemsDAO.generateId(con);
                setWishlistId(con, customerId, wishlistId);
            } else {
                wishlistId = wishlistIdOpt.get();
            }

            wishlistItemsDAO.insert(con, wishlistId, prd);
        });
    }

    @Override
    public void addToCart(long customerId, ProductDescription prd) {
        tm.executeTransaction(con -> {
            long cartId;
            Optional<Long> cartIdOpt = getCartId(con, customerId);
            if (cartIdOpt.isEmpty()) {
                cartId = cartItemsDAO.generateId(con);
                setCartId(con, customerId, cartId);
            } else {
                cartId = cartIdOpt.get();
            }
            cartItemsDAO.insert(con, cartId, prd);
        });
    }

    @Override
    public void removeProductFromWishlist(long customerId, long productId) {
        tm.executeTransaction(con -> {
            Optional<Long> wishlistId = getWishlistId(con, customerId);
            if (wishlistId.isEmpty())
                throw new DBException("Customer with id = " + customerId + " does not have a wishlist to delete an item from");

            wishlistItemsDAO.removeProduct(con, wishlistId.get(), productId);
        });
    }

    @Override
    public void removeProductFromCart(long customerId, long productId) {
        tm.executeTransaction(con -> {
            Optional<Long> cartId = getCartId(con, customerId);
            if (cartId.isEmpty())
                throw new DBException("Customer with id = " + customerId + " does not have a cart to delete an item from");

            cartItemsDAO.removeProduct(con, cartId.get(), productId);
        });
    }

    @Override
    public void setCartId(long customerId, long cartId) {
        tm.executeTransaction(con ->
                setCartId(con, customerId, cartId)
        );
    }

    private void setCartId(Connection con, long customerId, long cartId) {
        String query = "update customers set cart_id = ? where id = ?";

        processUpdate(con, query, cartId, customerId);
    }

    @Override
    public void setWishlistId(long customerId, long wishlistId) {
        tm.executeTransaction(con ->
                setWishlistId(con, customerId, wishlistId)
        );
    }

    public void setWishlistId(Connection con, long customerId, long wishlistId) {
        String query = "update customers set wishlist_id = ? where id = ?";

        processUpdate(con, query, wishlistId, customerId);
    }

    @Override
    public Optional<Customer> getById(long id) {
        final String query = "select * from customers where id = ?";

        return getSingle(processQuery(cm, query, id));
    }

    @Override
    public @NotNull List<Customer> getByName(String name) {
        String query = "select * from customers where name = ?";

        return processQuery(cm, query, name);
    }

    @Override
    public @NotNull List<Customer> getByNameLike(String pattern) {
        String query = "select * from customers where name like ?";

        return processQuery(cm, query, pattern);
    }

    @Override
    public @NotNull List<Customer> getAll() {
        final String query = "select * from customers";

        return processQuery(cm, query);
    }

    @Override
    public void addAll(Customer... customers) {
        Objects.requireNonNull(customers);

        tm.executeTransaction(con -> {
            for (Customer cs : customers)
                insert(con, Objects.requireNonNull(cs));
        });
    }

    @Override
    public void add(Customer cs) {
        tm.executeTransaction(con ->
                insert(con, cs)
        );
    }

    private void insert(Connection con, Customer cs) throws SQLException {
        Objects.requireNonNull(cs);
        if (emailExists(cs.getEmail()))
            throw new IllegalArgumentException("Customer with such an email already exists");

        ConnectionManager.requireValidConnection(con);
        LocalDate registrationDate = LocalDate.now();

        /* Try to insert the customer into table 'customers' */

        String query = "INSERT INTO " +
                "customers(name, address, email, date_of_birth, registration_date) " +
                "value (?, ?, ?, ?, ?)";
        List<Object> keys = processUpdateAndGetGeneratedKeys(con, query,
                cs.getName(), cs.getAddress(), cs.getEmail(), cs.getDateOfBirth(), registrationDate);
        cs.setId(((BigInteger) keys.get(0)).intValue());
        cs.setRegistrationDate(registrationDate);

        /* Handle case when customer has a cart or a wishlist */
        if (cs.getCart() != null) {
            generateProductContainerWithItems(con, cartItemsDAO, cs.getCart());
            setCartId(con, cs.getId(), cs.getCart().getId());
        }
        if (cs.getWishlist() != null) {
            generateProductContainerWithItems(con, wishlistItemsDAO, cs.getWishlist());
            setWishlistId(con, cs.getId(), cs.getWishlist().getId());
        }
    }

    private void generateProductContainerWithItems(Connection con,
                                                   MySqlProductRelationshipTableDAO dao,
                                                   ProductContainer pc) {
        /* Generate cart_id */
        long pcId = dao.generateId(con);

        /* Set customer's 'cart_id' column */
        pc.setId(pcId);

        /* Add records into the cart_items table */
        dao.insertAll(con, pcId, pc.getContent());
    }

    @Override
    public void remove(long id) {
        tm.executeTransaction(con -> {
                    /* First delete associated cart and wishlist */
                    deleteProductContainerIfExists(con, this::getCartId, cartItemsDAO, id);
                    deleteProductContainerIfExists(con, this::getWishlistId, wishlistItemsDAO, id);

                    String query = "delete from customers where id = ?";
                    processUpdate(con, query, id);
                }
        );
    }

    @Override
    public void update(Customer updated) {
        String email = updated.getEmail();
        if (isEmailInUse(email, updated.getId()))
            throw new IllegalArgumentException("Customer with such email already exists");

        String query = "update customers set name=?,address=?,email=?,registration_date=?,date_of_birth=? where id=?";
        tm.executeTransaction(con ->
                processUpdate(con, query,
                        updated.getName(),
                        updated.getAddress(),
                        updated.getEmail(),
                        updated.getRegistrationDate(),
                        updated.getDateOfBirth(),
                        updated.getId())

        );
    }

    private void deleteProductContainerIfExists(Connection con,
                                                LongFunction<Optional<Long>> idGetter,
                                                MySqlProductRelationshipTableDAO dao,
                                                long customerId) {
        Optional<Long> id = idGetter.apply(customerId);
        id.ifPresent(el -> dao.delete(con, el));
    }

    @Override
    public @NotNull Customer map(@NotNull ResultSet rs) throws SQLException {
        int customerID = rs.getInt("id");
        int cartId = rs.getInt("cart_id");
        int wishlistId = rs.getInt("wishlist_id");

        return new Customer(
                customerID,
                rs.getString("name"),
                rs.getString("address"),
                rs.getString("email"),
                rs.getDate("date_of_birth").toLocalDate(),
                rs.getDate("registration_date").toLocalDate(),
                cartId != 0 ? new ProductContainer(cartId, cartItemsDAO.getAllRelations(cartId)) : null,
                wishlistId != 0 ? new ProductContainer(wishlistId, wishlistItemsDAO.getAllRelations(wishlistId)) : null
        );
    }
}
