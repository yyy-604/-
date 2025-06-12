package org.example.service;

import org.example.model.*;
import java.util.List;
import java.util.Map;

public class UI {
    private ServiceManager serviceManager;
    private User currentUser;
    private FilterOptions filterOptions; // 최근 검색/필터 옵션 등 (선택사항)

    public UI() {
        this.serviceManager = new ServiceManager();
        this.currentUser = null;
        this.filterOptions = null;
    }

    // --- ServiceManager 반환 (폼에서 직접 사용시) ---
    public ServiceManager getServiceManager() {
        return serviceManager;
    }

    // --- 회원가입 ---
    public void signUp(User user) {
        serviceManager.signUp(user);
    }

    // --- 로그인 ---
    public User login(String id, String password) {
        User user = serviceManager.login(id, password);
        if (user != null) {
            this.currentUser = user;
        }
        return user;
    }

    // --- 정보수정 ---
    public void updateUserInfo(String name, String phone) {
        serviceManager.getUserManager().updateUserInfo(currentUser.getId(), name, phone);
        currentUser.setName(name);
        currentUser.setPhoneNumber(phone);
    }

    public void updateUserCars(List<Car> cars) {
        serviceManager.getUserManager().updateUserCars(currentUser.getId(), cars);
        currentUser.setCars(cars);
    }

    // --- 로그아웃 ---
    public void logout() {
        this.currentUser = null;
    }

    // --- 현재 로그인 유저 반환 ---
    public User getCurrentUser() {
        return currentUser;
    }

    // --- 회원 정보 수정 ---
    public void updateUser(User user) {
        serviceManager.updateUser(user);
        if (currentUser != null && currentUser.getId().equals(user.getId())) {
            this.currentUser = user;
        }
    }

    // --- 아이디로 유저 조회(회원가입 중복 체크 등) ---
    public User findUser(String id) {
        return serviceManager.findUser(id);
    }

    // --- 필터 옵션 관리 ---
    public void setFilterOptions(FilterOptions options) {
        this.filterOptions = options;
    }
    public FilterOptions getFilterOptions() {
        return filterOptions;
    }

    // --- 주차장 관련 위임 (폼에서 쉽게 호출) ---
    public Parking findParking(String id) {
        return serviceManager.findParking(id);
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
        serviceManager.updateParking(
                parkId, name, address, isPublic, isPrivate, isGeneral,
                isOnSite, isMobile, isFree, isPaid, spaceCount
        );
    }
    public void removeParking(Parking parking) {
        serviceManager.removeParking(parking.getId());
    }

    // --- [NEW] 폼에서 모든 데이터 한번에 등록 가능 ---
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

        return serviceManager.registerFullParkingInfo(
                userId, name, address, isPublic, isPrivate, isGeneral, isOnSite, isMobile, isFree, isPaid, spaceCount,
                openTime, closeTime, price, weekendPrice, extraFee, spaceMap
        );
    }

    // 필요하면 Space, Ticket 등도 위임 메서드 추가 가능
}