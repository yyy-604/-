package org.example.service;

import org.example.model.*;
import org.example.repository.DB;

public class ServiceManager {
    private UserManager userManager;
    private ParkingManager parkingManager;

    public ServiceManager() {
        // 매니저 객체 생성 시 DB 싱글턴과 연동
        this.userManager = new UserManager(DB.getInstance());
        this.parkingManager = new ParkingManager(DB.getInstance());
    }

    // --- User 관련 기능 ---
    public void signUp(User user) {
        userManager.add(user);
    }

    public User login(String id, String password) {
        User user = userManager.findById(id);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    public void updateUser(User user) {
        userManager.update(user);
    }

    public void logout() {
        // 필요하다면 로그아웃 상태 처리(세션, currentUser 등)
    }

    public User findUser(String id) {
        return userManager.findById(id);
    }

    // --- Parking 관련 기능 ---
    public void registerParking(Parking parking) {
        parkingManager.add(parking);
    }

    public Parking findParking(String id) {
        return parkingManager.findById(id);
    }

    public void updateParking(Parking parking) {
        parkingManager.update(parking);
    }

    public void removeParking(String id) {
        parkingManager.remove(id);
    }

    // --- 기타 Manager/기능 확장 시 추가 메서드 작성 가능 ---

    // Getter
    public UserManager getUserManager() { return userManager; }
    public ParkingManager getParkingManager() { return parkingManager; }
}