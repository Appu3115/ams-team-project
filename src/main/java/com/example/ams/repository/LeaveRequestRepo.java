package com.example.ams.repository;

import com.example.ams.model.LeaveRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeaveRequestRepo extends JpaRepository<LeaveRequest, Long> {
    List<LeaveRequest> findByEmployee_Id(int id);

    List<LeaveRequest> findAllByStatus(String approved);
}


