package com.example.CarRental.controllers;

import com.example.CarRental.model.*;
import com.example.CarRental.service.CarService;
import com.example.CarRental.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class CarRentController {

    @Autowired
    private CarService carService;

    @Autowired
    private ClientService clientService;

    @RequestMapping(value = "/filteredCars", method = GET)
    @ResponseBody
    public List<Car> availableCars(@RequestParam(required = false) Map<String, String> requestParams) {
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

        return carService.getAvailableCarsByParameter(query);
    }

    @RequestMapping(value = "/allCars", method = GET)
    @ResponseBody
    public List<Car> getAllCars() {
        return carService.getAllCars();
    }

    @PostMapping("/addClient")
    public ResponseEntity addClient(@RequestBody Client client) {
        clientService.addClient(client);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/rentCar")
    @ResponseBody
    public ResponseEntity rentCar(@RequestBody CarRentalRequestBody carRentalRequestBody) {
        // 1. check if client with ClientId exists
        UUID clientId = carRentalRequestBody.getClientId();
        if (!clientService.clientExists(clientId)) {
            return new ResponseEntity<>(
                    "Client with this clientId does not exist",
                    HttpStatus.BAD_REQUEST
            );
        }

        // 2. check if car with carId exists
        UUID carId = carRentalRequestBody.getCarId();
        Car car = carService.getCar(carId);
        if (car == null) {
            return new ResponseEntity<>(
                    "Car with this carId does not exist",
                    HttpStatus.BAD_REQUEST
            );
        }

        // 3. check if car is available
        if (car.getCarStatus() != CarStatus.AVAILABLE) {
            return new ResponseEntity<>(
                    "Car with this carId is not available",
                    HttpStatus.BAD_REQUEST
            );
        }

        // 4. check if startDate and endDate are valid
        // TODO

        // 5. rent car
        carService.updateCarStatus(carId, CarStatus.RENTED);
        carService.addRentCar(
                clientId,
                carId,
                carRentalRequestBody.getStartDate(),
                carRentalRequestBody.getEndDate()
        );

        return ResponseEntity.ok(HttpStatus.OK);
    }
}
