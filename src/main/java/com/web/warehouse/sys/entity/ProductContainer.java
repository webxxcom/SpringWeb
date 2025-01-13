package com.web.warehouse.sys.entity;

import java.util.*;
import java.util.stream.Collectors;

public class ProductContainer {
    long id;
    final List<ProductDescription> content;

    public ProductContainer(){
        this(Entity.NO_ID, List.of());
    }

    public ProductContainer(long id) {
        this(id, List.of());
    }

    public ProductContainer(ProductDescription... products) {
        this(Entity.NO_ID, Arrays.stream(products).collect(Collectors.toList()));
    }

    public ProductContainer(long id, ProductDescription... products) {
        this(id, Arrays.stream(products).collect(Collectors.toList()));
    }

    public ProductContainer(List<ProductDescription> products) {
        this(Entity.NO_ID, products);
    }

    public ProductContainer(long id, List<ProductDescription> products) {
        this.id = id;
        this.content = products;
    }

    public ProductContainer(ProductContainer other){
        Objects.requireNonNull(other);

        this.id = other.id;
        this.content = other.content;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean has(ProductDescription prd){
        return content.stream()
                .anyMatch(el -> el.id() == prd.id() && el.quantity() >= prd.quantity());
    }

    public List<ProductDescription> getContent(){
        return content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductContainer)) return false;
        ProductContainer that = (ProductContainer) o;
        return id == that.id && Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, content);
    }

    @Override
    public String toString() {
        return "ProductContainer{" +
                "id=" + id +
                ", content=" + content +
                '}';
    }
}
