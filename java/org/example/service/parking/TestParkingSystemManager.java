package org.example.service.parking;

import org.example.model.*;

import java.util.ArrayList;

public class TestParkingSystemManager {
    private static void printParkingArray(ArrayList<Product> array) {
        for (Product product : array)
            if (product instanceof Parking parking) {
                System.out.println("Name: " + parking.getName());
                System.out.println("Addr: " + parking.getAddress());

                for (Parking.Option option : parking.getOptions())
                    System.out.println(option);
            }
        System.out.println();
    }

    private static void printTicketArray(ArrayList<Product> array) {
        for (Product product : array)
            if (product instanceof Ticket ticket) {
                System.out.println("parkingId: " + ticket.getParkingId());
                System.out.println("timestamp: " + ticket.getTime());
            }
        System.out.println();
    }

    private static void printSpaceArray(ArrayList<Product> array) {
        for (Product product : array)
            if (product instanceof Space space) {
                System.out.println("parkingId: " + space.getParkingId());
                System.out.println("type: " + space.getSpaceType());
            }
        System.out.println();
    }


    public static void main(String[] args) {
        ParkingSystemManager system = new ParkingSystemManager();

        System.out.println("=== Test 1: Parking add & chage options ===");
        Parking parking = new Parking("롯데타워", "서울 송파구");
        parking.addOptions(
            Parking.Option.PUBLIC,
            Parking.Option.FREE,
            Parking.Option.GENERAL);

        system.add(parking);
        printParkingArray(system.search(parking));

        parking.removeOptions(Parking.Option.PUBLIC);
        parking.addOptions(Parking.Option.PRIVATE);

        system.update(parking);
        printParkingArray(system.search(parking));


        System.out.println("=== Test 2: Ticket add & remove ===");
        Ticket ticket = new Ticket(parking.getId(), 1, true, 1500, 2000);

        system.add(ticket);
        printTicketArray(system.search(ticket));

        system.remove(ticket);
        printTicketArray(system.search(ticket));

        
        System.out.println("=== Test 3: Space update ===");
        Space space = new Space(parking.getId(), "전기차");

        system.add(space);
        printSpaceArray(system.search(space));

        space.setSpaceType("일반");
        system.update(space);

        space.setAvailable(true);
        printSpaceArray(system.search(space));
    }
}
