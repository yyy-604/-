package org.example.service.parking;

import org.example.service.AbstractManager;
import org.example.model.Product;
import org.example.model.Parking;
import org.example.model.ParkingPass;
import org.example.model.Space;
import org.example.model.Ticket;

import java.util.ArrayList;

public class ParkingSystemManager extends AbstractManager<Product> {
    private ParkingManager parkingManager;
    private ParkingPassManager parkingPassManager;
    private SpaceManager spaceManager;
    private TicketManager ticketManager; 

    public ParkingSystemManager() {
        super();

        parkingManager = new ParkingManager();
        parkingPassManager = new ParkingPassManager();
        spaceManager = new SpaceManager();
        ticketManager = new TicketManager();
    }

    @SuppressWarnings("unchecked")
    private <T extends Product> AbstractManager<T> getManager(T product) {
        if (product instanceof Parking) {
            return (AbstractManager<T>) parkingManager;
        } else if (product instanceof ParkingPass) {
            return (AbstractManager<T>) parkingPassManager;
        } else if (product instanceof Space) {
            return (AbstractManager<T>) spaceManager;
        } else if (product instanceof Ticket) {
            return (AbstractManager<T>) ticketManager;
        } else {
            throw new IllegalArgumentException("Unknown product type: " + product.getClass());
        }
    }


    @Override
    public void add(Product product) {
        getManager(product).add(product);
    }

    @Override
    public void update(Product product) {
        getManager(product).update(product);
    }

    @Override
    public void remove(Product product) {
        getManager(product).remove(product);
    }

    @Override
    public ArrayList<Product> search(Product filter) {
        return getManager(filter).search(filter);
    }
}