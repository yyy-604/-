package org.example.service;

import org.example.model.Parking;
import org.example.repository.DB;

import java.util.ArrayList;
import java.util.List;

public class ParkingManager extends AbstractManager<Parking> {
    public ParkingManager(DB db) {
        super(db);
    }

    @Override
    public void add(Parking parking) {
        db.addParking(parking);
    }

    @Override
    public void update(Parking parking) {
        db.addParking(parking);
    }

    @Override
    public void remove(String id) {
        db.removeParking(id);
    }

    @Override
    public Parking findById(String id) {
        return db.getParking(id);
    }

    @Override
    public List<Parking> getAll() {
        return new ArrayList<>( db.getAllParkings());
    }
}