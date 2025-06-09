package org.example.service;

import org.example.repository.DB;
import java.util.List;

/**
 * DB 기반 통합 매니저 추상 클래스 (제네릭)
 */
public abstract class AbstractManager<T> {
    protected DB db;

    public AbstractManager(DB db) {
        this.db = db;
    }

    public abstract void add(T item);
    public abstract void update(T item);
    public abstract void remove(String id);
    public abstract T findById(String id);
    public abstract List<T> getAll();
}