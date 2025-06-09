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
    private final ParkingPassManager parkingPassManager;

    public ServiceManager() {
        DB db = DB.getInstance();
        this.userManager = new UserManager(db);
        this.parkingManager = new ParkingManager(db);
        this.ticketManager = new TicketManager(db);
        this.spaceManager = new SpaceManager(db);
        this.favoriteManager = new FavoriteManager(db);
        this.parkingPassManager = new ParkingPassManager(db);
    }

    // --- 예약 처리 ---
    public boolean reserveSpace(User user, Parking parking, String spaceType) {
        List<Space> spaceList = spaceManager.findByParkingId(parking.getId());
        for (Space space : spaceList) {
            if (space.getSpaceType().equals(spaceType) && space.isAvailable()) {
                spaceManager.reserveSpaceForUser(space, user.getId());
                String passId = parkingPassManager.createParkingPass(
                        user.getId(),
                        parking.getId(),
                        space.getId()
                );
                if (passId != null) {
                    user.getParkingPassIDList().add(passId);
                    userManager.update(user);
                    spaceManager.update(space);
                    return true; // 예약 성공
                }
            }
        }
        return false; // 예약 실패
    }

    public boolean returnParkingPass(String passId) {
        ParkingPass pass = parkingPassManager.findById(passId);
        if (pass == null) return false;

        // 공간 사용 해제
        Space space = spaceManager.findById(pass.getSpaceId());
        if (space != null) {
            space.setUserId(null);
            spaceManager.update(space);
        }

        // 사용자에서 주차권 제거
        User user = userManager.findById(pass.getUserId());
        if (user != null) {
            user.getParkingPassIDList().remove(passId);
            userManager.update(user);
        }

        // 주차권 삭제
        parkingPassManager.remove(passId);

        return true;
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
            String parkId = parkingManager.registerParking(
                    userId, name, address, isPublic, isPrivate, isGeneral,
                    isOnSite, isMobile, isFree, isPaid, spaceCount
            );

            ticketManager.registerTicketForParking(
                    parkId, openTime, closeTime, price, weekendPrice, extraFee
            );

            spaceManager.registerSpacesForParking(parkId, spaceMap);

            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
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

    public Ticket findTicketByParkingId(String parkId) {
        return ticketManager.findByParkingId(parkId);
    }

    public void updateTicket(String ticketId, String parkId, int price, int extraFee) {
        ticketManager.updateTicket(ticketId, parkId, price, extraFee);
    }

    public void registerSpacesForParking(String parkId, Map<String, Integer> spaceTypeCountMap) {
        spaceManager.registerSpacesForParking(parkId, spaceTypeCountMap);
    }

    public List<Space> findSpacesByParkingId(String parkId) {
        return spaceManager.findByParkingId(parkId);
    }

    public UserManager getUserManager() { return userManager; }
    public ParkingManager getParkingManager() { return parkingManager; }
    public TicketManager getTicketManager() { return ticketManager; }
    public SpaceManager getSpaceManager() { return spaceManager; }
    public FavoriteManager getFavoriteManager() { return favoriteManager; }
    public ParkingPassManager getParkingPassManager() { return parkingPassManager; }
}