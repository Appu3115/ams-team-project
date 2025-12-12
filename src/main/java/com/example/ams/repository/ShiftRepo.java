package com.example.ams.repository;

import com.example.ams.model.Shift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShiftRepo extends JpaRepository<Shift, Integer> {
    List<Shift> findAllByShiftType(String day);
}
