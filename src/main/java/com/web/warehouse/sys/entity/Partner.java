package com.web.warehouse.sys.entity;


import java.util.*;

public abstract class Partner extends Entity {
    protected String address;
    protected String email;

    protected Partner(String name, String address, String email){
        super(name);

        this.address = address;
        this.email = email;
    }

    protected Partner(long id, String name, String address, String email){
        super(id, name);

        this.address = address;
        this.email = email;
    }

    public String getAddress(){
        return address;
    }

    public String getEmail(){
        return email;
    }

    @Override
    public String toString() {
        return "Partner{" +
                "address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Partner)) return false;
        if (!super.equals(o)) return false;
        Partner partner = (Partner) o;
        return Objects.equals(address, partner.address) && Objects.equals(email, partner.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), address, email);
    }
}
