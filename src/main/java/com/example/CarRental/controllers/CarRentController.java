package com.example.CarRental.controllers;

import com.example.CarRental.model.*;
import com.example.CarRental.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class CarRentController {

    @Autowired
    private CarService carRentalService;

    @RequestMapping(value = "/filteredCars", method = GET)
    @ResponseBody
    public List<Car> availableCars(@RequestParam(required = false) Map<String,String> requestParams) {
        String model = requestParams.get("model");

        String carBodyType = requestParams.get("carBodyType");

        String releaseYear = requestParams.get("releaseYear");
        Integer year = releaseYear == null ? null : Integer.valueOf(releaseYear);

        String amount1 = requestParams.get("amount");
        Double amount = amount1 == null ? null : Double.valueOf(amount1);

        AvailableCarsQuery query = AvailableCarsQuery
                .builder()
                .amount(amount)
                .carBodyType(carBodyType)
                .releaseYear(year)
                .model(model)
                .carStatus(CarStatus.AVAILABLE)
                .build();

        return carRentalService.getAvailableCarsByParameter(query);
    }

    @RequestMapping(value = "/allCars", method = GET)
    @ResponseBody
    public List<Car> getAllCars() {
        return carRentalService.getAllCars();
    }

    @PostMapping("/addClient")
    public ResponseEntity addClient(@RequestBody Client client) {
        carRentalService.addClient(client);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
