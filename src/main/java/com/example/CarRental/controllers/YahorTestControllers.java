package com.example.CarRental.controllers;

import com.example.CarRental.model.AvailableCarsQuery;
import com.example.CarRental.model.Car;
import com.example.CarRental.model.CarStatus;
import com.example.CarRental.model.Client;
import com.example.CarRental.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

    @GetMapping("/listOfClients")
    List<Client> getAllClients(){
        return carService.getAllClients();
    }


    @PostMapping("/addClienstSecondVersion")
    public void addClient(){


        Client client = Client
                .builder()
                .name("Marcin")
                .surname("Pazurak")
                .address("Katowice 4")
                .email("mazxzxriam@gmail.com")
                .build();


        System.out.println(carService.addClient(client));
//'Content-Type': 'application/json'
    }


    @GetMapping("/addCarTest")
    public void addCartest(){
       Car car = Car
               .builder()
               .carBodyType("Crossover")
               .model("AudiTT ")
               .carStatus(CarStatus.RENTED)
               .amount(Double.valueOf(50.0))
               .releaseYear(Integer.valueOf(2016))
               .build();

        carService.addCar(car);
    }
}
