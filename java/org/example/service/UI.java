package org.example.service;

import org.example.model.*;
import org.example.repository.DB;

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
    public void registerParking(Parking parking) {
        serviceManager.registerParking(parking);
    }
    public Parking findParking(String id) {
        return serviceManager.findParking(id);
    }
    public void updateParking(Parking parking) {
        serviceManager.updateParking(parking);
    }

    // 필요하면 Space, Ticket 등도 위임 메서드 추가 가능
}