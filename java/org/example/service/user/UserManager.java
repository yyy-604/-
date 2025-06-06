package org.example.service.user;

import org.example.model.User;
import org.example.repository.DB;
import org.example.service.AbstractManager;

import java.util.List;
import java.util.ArrayList;


public class UserManager extends AbstractManager<User> {
    public UserManager(DB db) {
        super(db);
    }

    @Override
    public void add(User user) {
        db.addUser(user);
    }

    @Override
    public void update(User user) {
        db.addUser(user); // ID 동일하면 덮어쓰기
    }

    @Override
    public void remove(String id) {
        db.removeUser(id);
    }

    @Override
    public User findById(String id) {
        return db.getUser(id);
    }

    @Override
    public List<User> getAll() {
        return new ArrayList<>(db.getAllUsers());
    }
}