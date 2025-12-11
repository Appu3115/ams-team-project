package com.example.ams.service;

import com.example.ams.model.Employees;
import com.example.ams.model.LeaveRequest;
import com.example.ams.repository.EmployeeRepo;
import com.example.ams.repository.LeaveRequestRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class LeaveRequestService {

    @Autowired
    private LeaveRequestRepo repo;
    @Autowired
    private EmployeeRepo EmpRepo;

    public LeaveRequest applyLeave(LeaveRequest request) {
        Employees emp = EmpRepo.findById(request.getEmployee().getId())
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        request.setEmployee(emp);

        if (request.getStatus() == null) {
            request.setStatus("pending");
        }
        return repo.save(request);
    }


    public List<LeaveRequest> getAllLeave() {
        return  repo.findAll();
    }

    public List<LeaveRequest> getAllLeaveById(int id) {
        return repo.findByEmployee_Id(id);
    }

    public LeaveRequest approveLeave(int id) {
        LeaveRequest leave = repo.findById((long) id)
                .orElseThrow(() -> new RuntimeException("Leave not found"));
        leave.setStatus("approved");
        leave.setApprovedDate(LocalDate.now());
        return repo.save(leave);
    }

    public LeaveRequest rejectLeave(int id) {
        LeaveRequest leave = repo.findById((long) id).orElseThrow(() -> new RuntimeException("Leave not found"));
        leave.setStatus("rejected");
        leave.setApprovedDate(LocalDate.now());
        repo.save(leave);
        return leave;
    }

    public List<LeaveRequest> getLeaveStatus(String approved) {
        return  repo.findAllByStatus(approved);
    }
}
