package org.example.model;

import java.util.UUID;

public class TestProduct {
    public static void main(String[] args) {
        Space temp = new Space(UUID.randomUUID(), "test");

        System.out.println(temp.getId());
        System.out.println(temp.getParkingId());
    }
}
