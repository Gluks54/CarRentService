package com.example.CarRental.controllers;

import com.example.CarRental.service.StatisticService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@Api(value="CarRentController", description="There statistic information;")
public class StatisticController {

    private final StatisticService statisticService;

    @Autowired
    public StatisticController(StatisticService statisticService){
        this.statisticService = statisticService;
    }

    @GetMapping("/calculateIncome")
    public ResponseEntity<Double> calculateIncome(){
        Double income = statisticService.CalculateIncome();
        return new ResponseEntity<>(income, HttpStatus.OK);
    }

    @GetMapping("/sumOfDefinedCar")
    public ResponseEntity<Double> sumOfDefineCar(@RequestParam String carId){
        Double sumOfDefinedCar = statisticService.sumOfDefinedCar(UUID.fromString(carId));
        return new ResponseEntity<>(sumOfDefinedCar,HttpStatus.OK);
    }

    @GetMapping("/mostPopularCar")
    public ResponseEntity<UUID> mostPopularCar(){
        UUID carId = statisticService.mostPopularCar();
        return new ResponseEntity<>(carId,HttpStatus.OK);
    }

    @GetMapping("/notPopCar")
    public ResponseEntity<UUID> notPopCar(){
        UUID carId = statisticService.notPopCar();
        return new ResponseEntity<>(carId,HttpStatus.OK);
    }

    @GetMapping("/bestClient")
    public ResponseEntity<UUID> bestClient(){
        UUID clientID = statisticService.bestClient();
        return new ResponseEntity<>(clientID,HttpStatus.OK);
    }
}
