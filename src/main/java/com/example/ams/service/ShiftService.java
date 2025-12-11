package com.example.ams.service;

import com.example.ams.model.Shift;
import com.example.ams.repository.ShiftRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShiftService {
    @Autowired
    private ShiftRepo repo;

    public List<Shift> getAllShifts() {
        return repo.findAll();
    }

    public void addShift(Shift shift) {
        repo.save(shift);
    }

    public List<Shift> getShiftsByType(String day) {
        return repo.findAllByShiftType(day);
    }
}
