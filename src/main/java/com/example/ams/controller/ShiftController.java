package com.example.ams.controller;

import com.example.ams.model.Shift;
import com.example.ams.service.ShiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shift")
public class ShiftController {

    @Autowired
    private ShiftService service;

    @GetMapping("/getShift")
    public List<Shift> getAllShifts(){
        return service.getAllShifts();
    }

    @PostMapping("/addShift")
    public String addShift(@RequestBody Shift shift){
        service.addShift(shift);
        return "Added Successfully";
    }

    @GetMapping("/getDayShift")
    public List<Shift> getAllDayShifts(){
        return service.getShiftsByType("Day");
    }

    @GetMapping("/getNightShift")
    public List<Shift> getAllNightShifts(){
        return service.getShiftsByType("Night");
    }
    @GetMapping("/getEveningShifts")
    public List<Shift> getEveningShifts(){
        return service.getShiftsByType("Evening");
    }

}
