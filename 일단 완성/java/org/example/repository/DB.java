package org.example.repository;

import org.example.model.*;
import java.util.*;

public class DB {
    private static DB instance;

    // === Entity 저장소 ===
    private Map<String, User> userTable = new HashMap<>();
    private Map<String, Parking> parkingTable = new HashMap<>();
    private Map<String, Space> spaceTable = new HashMap<>();
    private Map<String, Ticket> ticketTable = new HashMap<>();
    private Map<String, Favorite> favoriteTable = new HashMap<>();
    private Map<String, ParkingPass> parkingPassTable = new HashMap<>();

    // === 싱글턴 패턴 ===
    private DB() { }
    public static DB getInstance() {
        if (instance == null) instance = new DB();
        return instance;
    }

    // --- User 관련 ---
    public void addUser(User user) {
        userTable.put(user.getId(), user);
    }
    public User getUser(String id) {
        return userTable.get(id);
    }
    public void removeUser(String id) {
        userTable.remove(id);
    }
    public Collection<User> getAllUsers() {
        return userTable.values();
    }

    // --- Parking 관련 ---
    public void addParking(Parking parking) {
        parkingTable.put(parking.getId(), parking);
    }
    public Parking getParking(String id) {
        return parkingTable.get(id);
    }
    public void removeParking(String id) {
        parkingTable.remove(id);
    }
    public Collection<Parking> getAllParkings() {
        return parkingTable.values();
    }

    // --- Space 관련 ---
    public void addSpace(Space space) {
        spaceTable.put(space.getId(), space);
    }
    public Space getSpace(String id) {
        return spaceTable.get(id);
    }
    public void removeSpace(String id) {
        spaceTable.remove(id);
    }
    public Collection<Space> getAllSpaces() {
        return spaceTable.values();
    }

    // --- Ticket 관련 ---
    public void addTicket(Ticket ticket) {
        ticketTable.put(ticket.getId(), ticket);
    }
    public Ticket getTicket(String id) {
        return ticketTable.get(id);
    }
    public void removeTicket(String id) {
        ticketTable.remove(id);
    }
    public Collection<Ticket> getAllTickets() {
        return ticketTable.values();
    }
    public void updateTicket(Ticket ticket) {
        ticketTable.put(ticket.getId(), ticket);
    }

    // --- Favorite 관련 ---
    public void addFavorite(Favorite favorite) {
        favoriteTable.put(favorite.getId(), favorite);
    }
    public Favorite getFavorite(String id) {
        return favoriteTable.get(id);
    }
    public void removeFavorite(String id) {
        favoriteTable.remove(id);
    }
    public Collection<Favorite> getAllFavorites() {
        return favoriteTable.values();
    }

    // --- ParkingPass 관련 ---
    public void addParkingPass(ParkingPass pass) {
        parkingPassTable.put(pass.getId(), pass);
    }
    public ParkingPass getParkingPass(String id) {
        return parkingPassTable.get(id);
    }
    public void removeParkingPass(String id) {
        parkingPassTable.remove(id);
    }
    public Collection<ParkingPass> getAllParkingPasses() {
        return parkingPassTable.values();
    }

    // === 필요시 더 다양한 엔티티/테이블 추가 가능 ===
}