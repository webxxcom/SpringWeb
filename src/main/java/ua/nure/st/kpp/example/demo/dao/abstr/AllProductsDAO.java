package ua.nure.st.kpp.example.demo.dao.abstr;

import ua.nure.st.kpp.example.demo.entity.Product;

import java.util.List;
import java.util.Optional;

public interface AllProductsDAO extends EntityDAO<Product> {
    /* Getters */
    Optional<Integer> getQuantity(long id);
    Optional<Product> getById(long id);
    List<Product> getByCategoryLike(String pattern);
    long getEmpty();

    /* Updaters */
    void updateQuantity(long productId, int newQuantity);
    void take(long productId, int quantity);
    void insert(Product product);
    void insertAll(Product... products);

}
