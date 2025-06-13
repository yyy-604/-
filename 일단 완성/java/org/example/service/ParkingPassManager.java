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

    public String createParkingPass(String userId, String parkId, String spaceId,int useTime) {
        String passId = UUID.randomUUID().toString(); // 여기서 ID를 새로 생성
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = (useTime > 0) ? startTime.plusHours(useTime) : null;

        ParkingPass pass = new ParkingPass(
                passId,
                userId,
                parkId,     // ticketId (필요 시 사용)
                spaceId,
                startTime,
                endTime
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

    // 자리에 대해 '현재 활성화된' ParkingPass를 반환
    public ParkingPass findActivePassBySpaceId(String spaceId) {
        LocalDateTime now = LocalDateTime.now();
        for (ParkingPass pass : getAll()) {  // <== getAll() 사용!
            if (
                    pass.getSpaceId().equals(spaceId) &&
                            pass.getStartTime() != null &&
                            pass.getEndTime() != null &&
                            !now.isBefore(pass.getStartTime()) && // now >= startTime
                            now.isBefore(pass.getEndTime())       // now < endTime
            ) {
                return pass;
            }
        }
        return null;
    }
}