package com.web.warehouse.sys.entity;


import java.math.BigDecimal;
import java.util.Objects;

public final class Product extends Entity {
    public static final int NO_QUANTITY = -1;

    //Fields
    private final BigDecimal price;
    private final String category;
    private int quantityInStock;

    public static void validateQuantity(int q) throws IllegalArgumentException{
        if(q <= NO_QUANTITY) throw new IllegalArgumentException();
    }

    public Product(String name, double price, int quantityInStock, String category){
        this(NO_ID, name, BigDecimal.valueOf(price), quantityInStock, category);
    }

    public Product(long id, String name, double price, int quantityInStock, String category) {
        this(id, name, BigDecimal.valueOf(price), quantityInStock, category);
    }

    public Product(long id, String name, BigDecimal price, int quantityInStock, String category) {
        super(id, name);
        validateQuantity(quantityInStock);

        this.price = price;
        this.category = category;
        this.quantityInStock = quantityInStock;
    }

    public Product(long id, String name, BigDecimal price, String category) {
        this(id, name, price, 1, category);
    }

    public BigDecimal getPrice(){
        return price;
    }

    public int getQuantity(){
        return quantityInStock;
    }

    public String getCategory(){
        return this.category;
    }

    public void setQuantity(int quantityInStock) throws IllegalArgumentException{
        validateQuantity(quantityInStock);

        this.quantityInStock = quantityInStock;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", category='" + category + '\'' +
                ((quantityInStock == NO_QUANTITY) ? "" //Do not display quantity if not provided
                        : (", " + "quantity: " + quantityInStock)) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        if (!super.equals(o)) return false;
        Product product = (Product) o;
        return quantityInStock == product.quantityInStock && Objects.equals(price, product.price) && Objects.equals(category, product.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), price);
    }
}
