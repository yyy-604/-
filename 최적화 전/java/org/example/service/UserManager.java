package org.example.service;

import org.example.model.User;
import org.example.repository.DB;
import org.example.model.Car;
import java.util.List;
import java.util.ArrayList;


public class UserManager extends AbstractManager<User> {
    public UserManager(DB db) {
        super(db);
    }

    public void createUser(String id, String pw, String name, String phone) {
        User user = new User();
        user.setId(id);
        user.setPassword(pw);
        user.setName(name);
        user.setPhoneNumber(phone);
        user.setCars(new ArrayList<>());
        user.setUserTypeTags("");
        user.setFilterOption(null);
        user.setParkingPassIDList(new ArrayList<>());
        user.setFavorites(new ArrayList<>());
        add(user); // DB에 저장
    }

    public void updateUserInfo(String userId, String name, String phone) {
        User user = findById(userId);
        if (user != null) {
            user.setName(name);
            user.setPhoneNumber(phone);
        }
    }

    public void updateUserCars(String userId, List<Car> cars) {
        User user = findById(userId);
        if (user != null) {
            user.setCars(cars);
        }
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