package org.example.model;

import java.util.UUID;

public abstract class Product {
    private UUID id;

    public Product() {
        this.id = UUID.randomUUID();
    }

    public UUID getId() { return id; }
}
