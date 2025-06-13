package org.example.model;

public abstract class Product {
    protected String id;

    public Product() {}

    public Product(String id) {
        this.id = id;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
}