package org.example.service;

import org.example.model.ParkingPass;
import org.example.repository.DB;

import java.util.ArrayList;
import java.util.List;

public class ParkingPassManager extends AbstractManager<ParkingPass> {
    public ParkingPassManager(DB db) {
        super(db);
    }

    @Override
    public void add(ParkingPass pass) {
        db.addParkingPass(pass);
    }

    @Override
    public void update(ParkingPass pass) {
        db.addParkingPass(pass);
    }

    @Override
    public void remove(String id) {
        db.removeParkingPass(id);
    }

    @Override
    public ParkingPass findById(String id) {
        return db.getParkingPass(id);
    }

    @Override
    public List<ParkingPass> getAll() {
        return new ArrayList<>(db.getAllParkingPasses());
    }
}