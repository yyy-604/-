package org.example.service;

import org.example.model.ParkingPass;
import org.example.repository.DB;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.time.LocalDateTime;

public class ParkingPassManager extends AbstractManager<ParkingPass> {
    public ParkingPassManager(DB db) {
        super(db);
    }

    public String createParkingPass(String userId, String parkId, String spaceId) {
        String passId = UUID.randomUUID().toString(); // 여기서 ID를 새로 생성

        ParkingPass pass = new ParkingPass(
                passId,
                userId,
                parkId,     // ticketId (필요 시 사용)
                spaceId,
                LocalDateTime.now()
        );
        this.add(pass); // DB에 저장
        return pass.getId(); // 고유 ID 반환
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