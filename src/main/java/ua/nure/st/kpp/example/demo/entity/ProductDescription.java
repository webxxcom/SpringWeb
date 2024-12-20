package ua.nure.st.kpp.example.demo.entity;


import java.util.Objects;

public class ProductDescription implements Comparable<ProductDescription>{
    long id;
    int quantity;

    public ProductDescription(long id, int quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    public int quantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public long id() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductDescription)) return false;
        ProductDescription that = (ProductDescription) o;
        return id == that.id && quantity == that.quantity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, quantity);
    }

    @Override
    public String toString() {
        return "ProductDescription{" +
                "id=" + id +
                ", quantity=" + quantity +
                '}';
    }

    @Override
    public int compareTo(ProductDescription o) {
        return Long.compare(o.id, this.id);
    }
}

