package org.example.model;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String id;
    private String password;
    private String name;
    private String phoneNumber;
    private List<Car> cars;                    // 여러 대의 차량
    private String userTypeTags;
    private FilterOptions filterOption;         // 유저별 필터 옵션
    private List<String> parkingPassIDList;     // 여러 개의 주차권 ID
    private List<Favorite> favorites;           // 즐겨찾기 리스트

    // 기본 생성자: 필드 초기화
    public User() {
        this.cars = new ArrayList<>();
        this.userTypeTags = "";
        this.filterOption = new FilterOptions(); // null 방지
        this.parkingPassIDList = new ArrayList<>();
        this.favorites = new ArrayList<>();
    }

    public User(String id, String password, String name, String phoneNumber,
                List<Car> cars, String userTypeTags, FilterOptions filterOption,
                List<String> parkingPassIDList, List<Favorite> favorites) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.cars = cars != null ? cars : new ArrayList<>();
        this.userTypeTags = userTypeTags != null ? userTypeTags : "";
        this.filterOption = filterOption != null ? filterOption : new FilterOptions();
        this.parkingPassIDList = parkingPassIDList != null ? parkingPassIDList : new ArrayList<>();
        this.favorites = favorites != null ? favorites : new ArrayList<>();
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public List<Car> getCars() { return cars; }
    public void setCars(List<Car> cars) { this.cars = cars != null ? cars : new ArrayList<>(); }

    public String getUserTypeTags() { return userTypeTags; }
    public void setUserTypeTags(String userTypeTags) { this.userTypeTags = userTypeTags != null ? userTypeTags : ""; }

    public FilterOptions getFilterOption() {
        if (filterOption == null) filterOption = new FilterOptions();
        return filterOption;
    }
    public void setFilterOption(FilterOptions filterOption) {
        this.filterOption = filterOption != null ? filterOption : new FilterOptions();
    }

    public List<String> getParkingPassIDList() { return parkingPassIDList; }
    public void setParkingPassIDList(List<String> parkingPassIDList) {
        this.parkingPassIDList = parkingPassIDList != null ? parkingPassIDList : new ArrayList<>();
    }

    public List<Favorite> getFavorites() { return favorites; }
    public void setFavorites(List<Favorite> favorites) {
        this.favorites = favorites != null ? favorites : new ArrayList<>();
    }
}