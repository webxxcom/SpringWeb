package com.web.warehouse.sys.db.abstr;

import com.web.warehouse.sys.entity.Customer;
import com.web.warehouse.sys.entity.ProductDescription;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface CustomerDAO extends PartnerDAO<Customer> {

    /* Getters */
    Optional<Long> getCartId(long customerId);
    Optional<Long> getWishlistId(long customerId);
    boolean isEmailInUse(String email, long excludeCustomerId);

    /* Updaters */
    void addToWishList(long productId, ProductDescription prd);
    void addToCart(long customerId, ProductDescription prd);
    void removeProductFromCart(long customerId, long productId);
    void removeProductFromWishlist(long customerId, long productId);
    void setCartId(long customerId, long cartId);
    void setWishlistId(long customerId, long wishlistId);

}
