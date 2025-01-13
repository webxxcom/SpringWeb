package com.web.warehouse.sys.db.abstr;

import com.web.warehouse.sys.entity.Product;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface AllProductsDAO extends EntityDAO<Product> {
    /* Getters */
    Optional<Integer> getQuantity(long id);
    Optional<Product> getById(long id);
    List<Product> getByCategoryLike(String pattern);
    long getEmpty();

    /* Updaters */
    void updateQuantity(long productId, int newQuantity);
    void take(long productId, int quantity);
    void add(Product product);
    void addAll(Product... products);

}
