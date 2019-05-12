package com.example.CarRental.controllers;

import com.example.CarRental.model.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/carRental")
public class CarRentalController {

    @Autowired
    private CarRentalService carRentalService;

    @GetMapping("/allCarsList")
    public Map<UUID, Car> cars() {
        return carRentalService.getAllCars();
    }


}
