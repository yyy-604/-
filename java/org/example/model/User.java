package org.example.model;

import java.util.List;

public class User {
    private String id;
    private String password;
    private String name;
    private String phoneNumber;
    private List<Car> cars;                    // 여러 대의 차량
    private String userTypeTags;
    private FilterOptions filterOption;
    private List<String> parkingPassIDList;    // 여러 개의 주차권 ID
    private List<Favorite> favorites;          // 즐겨찾기 리스트

    public User() {}

    public User(String id, String password, String name, String phoneNumber,
                List<Car> cars, String userTypeTags, FilterOptions filterOption,
                List<String> parkingPassIDList, List<Favorite> favorites) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.cars = cars;
        this.userTypeTags = userTypeTags;
        this.filterOption = filterOption;
        this.parkingPassIDList = parkingPassIDList;
        this.favorites = favorites;
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
    public void setCars(List<Car> cars) { this.cars = cars; }

    public String getUserTypeTags() { return userTypeTags; }
    public void setUserTypeTags(String userTypeTags) { this.userTypeTags = userTypeTags; }

    public FilterOptions getFilterOption() { return filterOption; }
    public void setFilterOption(FilterOptions filterOption) { this.filterOption = filterOption; }

    public List<String> getParkingPassIDList() { return parkingPassIDList; }
    public void setParkingPassIDList(List<String> parkingPassIDList) { this.parkingPassIDList = parkingPassIDList; }

    public List<Favorite> getFavorites() { return favorites; }
    public void setFavorites(List<Favorite> favorites) { this.favorites = favorites; }
}