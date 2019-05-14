package com.example.CarRental.controllers;

import com.example.CarRental.model.AvailableCarsQuery;
import com.example.CarRental.model.Car;
import com.example.CarRental.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class YahorTestControllers {

    @Autowired
    CarService carService;


    @GetMapping("/listOfCars")
    List<Car> getListOfCars(){

        AvailableCarsQuery carsQuery = AvailableCarsQuery
                .builder()
                .amount(Double.valueOf(25.0))
                .carBodyType("Hatchback")
                .releaseYear(Integer.valueOf(2011))
                .model("Mercedes-Benz")
                .build();

       return carService.getAvailableCarsByParameter(carsQuery);
    }
}
