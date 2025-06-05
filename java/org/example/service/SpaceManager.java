package org.example.service;

import org.example.model.Space;
import org.example.repository.DB;

import java.util.ArrayList;
import java.util.List;

public class SpaceManager extends AbstractManager<Space> {
    public SpaceManager(DB db) {
        super(db);
    }

    @Override
    public void add(Space space) {
        db.addSpace(space);
    }

    @Override
    public void update(Space space) {
        db.addSpace(space);
    }

    @Override
    public void remove(String id) {
        db.removeSpace(id);
    }

    @Override
    public Space findById(String id) {
        return db.getSpace(id);
    }

    @Override
    public List<Space> getAll() {
        return new ArrayList<>(db.getAllSpaces());
    }
}