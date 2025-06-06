package org.example.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

/**
 * DB 기반 통합 매니저 추상 클래스 (제네릭)
 */
public abstract class AbstractManager<T> {
    protected HashMap<UUID,T> products;

    public AbstractManager() {
        this.products = new HashMap<UUID,T>();
    }

    public abstract void add(T product);
    public abstract void update(T product);
    public abstract void remove(T product);
    public abstract ArrayList<T> search(T filter);
}