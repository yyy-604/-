package org.example.service;

import org.example.model.Favorite;
import org.example.repository.DB;

import java.util.ArrayList;
import java.util.List;

public class FavoriteManager extends AbstractManager<Favorite> {
    public FavoriteManager(DB db) {
        super(db);
    }

    @Override
    public void add(Favorite favorite) {
        db.addFavorite(favorite);
    }

    @Override
    public void update(Favorite favorite) {
        db.addFavorite(favorite);
    }

    @Override
    public void remove(String id) {
        db.removeFavorite(id);
    }

    @Override
    public Favorite findById(String id) {
        return db.getFavorite(id);
    }

    @Override
    public List<Favorite> getAll() {
        return new ArrayList<>(db.getAllFavorites());
    }
}