package org.example.service;

import org.example.model.*;
import org.example.repository.DB;

import java.util.List;
import java.util.Map;

public class ServiceManager {
    private final UserManager userManager;
    private final ParkingManager parkingManager;
    private final TicketManager ticketManager;
    private final SpaceManager spaceManager;
    private final FavoriteManager favoriteManager;

    public ServiceManager() {
        DB db = DB.getInstance();
        this.userManager = new UserManager(db);
        this.parkingManager = new ParkingManager(db);
        this.ticketManager = new TicketManager(db);
        this.spaceManager = new SpaceManager(db);
        this.favoriteManager = new FavoriteManager(db);
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
        // 필요하다면 로그아웃 상태 처리
    }

    public User findUser(String id) {
        return userManager.findById(id);
    }

    public void updateUserInfo(String userId, String name, String phone) {
        userManager.updateUserInfo(userId, name, phone);
    }

    public void updateUserCars(String userId, List<Car> cars) {
        userManager.updateUserCars(userId, cars);
    }

    // --- Parking + Ticket + Space 등록을 한 번에 처리 ---
    public boolean registerFullParkingInfo(
            String userId,
            String name,
            String address,
            boolean isPublic,
            boolean isPrivate,
            boolean isGeneral,
            boolean isOnSite,
            boolean isMobile,
            boolean isFree,
            boolean isPaid,
            int spaceCount,
            String openTime,
            String closeTime,
            int price,
            int weekendPrice,
            int extraFee,
            Map<String, Integer> spaceMap
    ) {
        try {
            // 1. Parking 등록 (ID 생성해서 반환)
            String parkId = parkingManager.registerParking(
                    userId, name, address, isPublic, isPrivate, isGeneral,
                    isOnSite, isMobile, isFree, isPaid, spaceCount
            );

            // 2. Ticket 등록
            ticketManager.registerTicketForParking(
                    parkId, openTime, closeTime, price, weekendPrice, extraFee
            );

            // 3. Space(장소) 등록
            spaceManager.registerSpacesForParking(parkId, spaceMap);

            return true;
        } catch (Exception ex) {
            ex.printStackTrace(); // 필요시 로그로 대체
            return false;
        }
    }

    // --- Parking 관련 단일 기능 ---
    public String registerParking(
            String userId,
            String name,
            String address,
            boolean isPublic,
            boolean isPrivate,
            boolean isGeneral,
            boolean isOnSite,
            boolean isMobile,
            boolean isFree,
            boolean isPaid,
            int spaceCount
    ) {
        return parkingManager.registerParking(
                userId, name, address, isPublic, isPrivate, isGeneral,
                isOnSite, isMobile, isFree, isPaid, spaceCount
        );
    }

    public Parking findParking(String id) {
        return parkingManager.findById(id);
    }

    public void updateParking(
            String parkId,
            String name,
            String address,
            boolean isPublic,
            boolean isPrivate,
            boolean isGeneral,
            boolean isOnSite,
            boolean isMobile,
            boolean isFree,
            boolean isPaid,
            int spaceCount
    ) {
        parkingManager.updateParking(
                parkId, name, address, isPublic, isPrivate, isGeneral,
                isOnSite, isMobile, isFree, isPaid, spaceCount
        );
    }

    public void removeParking(String id) {
        parkingManager.remove(id);
    }

    // --- Ticket 관련 ---
    public void registerTicketForParking(
            String parkId, String openTime, String closeTime, int price, int weekendPrice, int extraFee
    ) {
        ticketManager.registerTicketForParking(parkId, openTime, closeTime, price, weekendPrice, extraFee);
    }

    // Ticket을 ParkingId로 조회 (폼에서 편하게 사용)
    public Ticket findTicketByParkingId(String parkId) {
        return ticketManager.findByParkingId(parkId);
    }

    // Ticket 수정
    public void updateTicket(String ticketId, String parkId, int price, int extraFee) {
        ticketManager.updateTicket(ticketId, parkId, price, extraFee);
    }

    // --- Space 관련 ---
    public void registerSpacesForParking(String parkId, Map<String, Integer> spaceTypeCountMap) {
        spaceManager.registerSpacesForParking(parkId, spaceTypeCountMap);
    }

    // Space 목록 반환
    public List<Space> findSpacesByParkingId(String parkId) {
        return spaceManager.findByParkingId(parkId);
    }

    // --- 기타 Manager/기능 확장 시 추가 메서드 작성 가능 ---

    // Getter
    public UserManager getUserManager() { return userManager; }
    public ParkingManager getParkingManager() { return parkingManager; }
    public TicketManager getTicketManager() { return ticketManager; }
    public SpaceManager getSpaceManager() { return spaceManager; }
    public FavoriteManager getFavoriteManager() { return favoriteManager; }
}