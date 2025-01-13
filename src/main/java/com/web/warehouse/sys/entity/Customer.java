package com.web.warehouse.sys.entity;

import java.time.LocalDate;
import java.util.*;

public class Customer extends Partner {
    private final LocalDate dateOfBirth;
    private LocalDate registrationDate;
    private ProductContainer cart;
    private ProductContainer wishlist;

    /* Constructor to initialize Customer without a cart and a wishlist */
    public Customer(String name, String address, String email, LocalDate dateOfBirth){
        this(name, address, email, dateOfBirth, null);
    }

    public Customer(String name, String address, String email,
                    LocalDate dateOfBirth, LocalDate registrationDate) {
        super(name, address, email);

        this.dateOfBirth = dateOfBirth;
        this.registrationDate = registrationDate;
    }

    public Customer(long id, String name, String address, String email,
                    LocalDate dateOfBirth, LocalDate registrationDate) {
        super(id, name, address, email);

        this.dateOfBirth = dateOfBirth;
        this.registrationDate = registrationDate;
    }

    public Customer(String name, String address, String email,
                    LocalDate dateOfBirth, ProductContainer cart, ProductContainer wishlist) {
        super(name, address, email);

        this.registrationDate = null;
        this.dateOfBirth = dateOfBirth;
        this.cart = cart == null ? null : new ProductContainer(cart);
        this.wishlist = wishlist == null ? null : new ProductContainer(wishlist);
    }

    public Customer(int id, String name, String address, String email,
                    LocalDate dateOfBirth, LocalDate registrationDate, ProductContainer cart,
                    ProductContainer wishlist) {
        super(id, name, address, email);

        this.dateOfBirth = dateOfBirth;
        this.registrationDate = registrationDate;
        this.cart = cart == null ? null : new ProductContainer(cart);
        this.wishlist = wishlist == null ? null : new ProductContainer(wishlist);
    }

    public ProductContainer getCart(){
        return cart;
    }

    public void setCart(ProductContainer cart) {
        this.cart = cart;
    }

    public ProductContainer getWishlist(){
        return wishlist;
    }

    public void setWishlist(ProductContainer wishlist) {
        this.wishlist = wishlist;
    }

    public LocalDate getDateOfBirth(){
        return dateOfBirth;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof Customer)) return false;

        Customer that = (Customer)o;
        return super.equals(o)
                && Objects.equals(email, that.email)
                && Objects.equals(dateOfBirth, that.dateOfBirth)
                && Objects.equals(registrationDate, that.registrationDate)
                && Objects.equals(cart, that.cart)
                && Objects.equals(wishlist, that.wishlist);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", wishlist=" + wishlist +
                ", cart=" + cart +
                ", registrationDate=" + registrationDate +
                ", dateOfBirth=" + dateOfBirth +
                '}';
    }
}
