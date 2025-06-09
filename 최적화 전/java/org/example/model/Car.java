package org.example.model;

public class Car {
    private String carNumber;
    private String carType;
    private boolean electric;

    public Car() {}

    public Car(String carNumber, String carType, boolean electric) {
        this.carNumber = carNumber;
        this.carType = carType;
        this.electric = electric;
    }

    public String getCarNumber() { return carNumber; }
    public void setCarNumber(String carNumber) { this.carNumber = carNumber; }

    public String getCarType() { return carType; }
    public void setCarType(String carType) { this.carType = carType; }

    public boolean isElectric() { return electric; }
    public void setElectric(boolean electric) { this.electric = electric; }

    @Override
    public String toString() {
        return carNumber + " (" + carType + ")";
    }
}