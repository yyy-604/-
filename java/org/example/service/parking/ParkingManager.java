package org.example.service.parking;

import org.example.service.AbstractManager;
import org.example.model.Parking;

import java.util.ArrayList;

public class ParkingManager extends AbstractManager<Parking> {
    public ParkingManager() {
        super();
    }

    @Override
    public void add(Parking parking) {
        products.put(parking.getId(), parking);
    }

    @Override
    public void update(Parking parking) {
        Parking target = products.get(parking.getId());

        target.setName(parking.getName());
        target.setAddress(parking.getAddress());
        target.setOptions(parking.getOptions());
    }

    @Override
    public void remove(Parking parking) {
        products.remove(parking.getId());
    }

    @Override
    public ArrayList<Parking> search(Parking filter) {
        ArrayList<Parking> result = new ArrayList<Parking>();

        products.forEach((_, value) -> {
            if (value.getId() == filter.getId()) {
                result.add(value);
            }
        });

        return result;
    }
}