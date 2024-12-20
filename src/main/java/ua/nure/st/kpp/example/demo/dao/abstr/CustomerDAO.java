package ua.nure.st.kpp.example.demo.dao.abstr;

import org.springframework.stereotype.Service;
import ua.nure.st.kpp.example.demo.entity.Customer;
import ua.nure.st.kpp.example.demo.entity.ProductDescription;

import java.util.Optional;

@Service
public interface CustomerDAO extends PartnerDAO<Customer> {

    /* Getters */
    Optional<Long> getCartId(long customerId);
    Optional<Long> getWishlistId(long customerId);

    /* Updaters */
    void addToWishList(long productId, ProductDescription prd);
    void addToCart(long customerId, ProductDescription prd);
    void removeProductFromCart(long customerId, long productId);
    void removeProductFromWishlist(long customerId, long productId);
    void setCartId(long customerId, long cartId);
    void setWishlistId(long customerId, long wishlistId);
}
