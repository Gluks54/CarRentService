package com.example.CarRental.controllers;

import com.example.CarRental.model.*;
import com.example.CarRental.service.CarRentalService;
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
@RequestMapping("api")
public class CarRentController {

    @Autowired
    private CarService carService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private CarRentalService carRentalService;

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

    @RequestMapping(value = "/getClient", method = GET)
    @ResponseBody
    public ResponseEntity getClient(@RequestParam(required = false) Map<String, String> requestParams) {
        UUID clientId;
        try {
            clientId = UUID.fromString(requestParams.get("clientId"));
        } catch (Exception e) {
            return new ResponseEntity<>(
                    "\"Given clientId is not UUID\"",
                    HttpStatus.BAD_REQUEST
            );
        }

        Client client = clientService.getClient(clientId);

        if (client == null) {
            return new ResponseEntity<>(
                    "\"Client with this clientId does not exist\"",
                    HttpStatus.NOT_FOUND
            );
        }

        return new ResponseEntity<>(client, HttpStatus.OK);
    }

    @RequestMapping(value = "/allCars", method = GET)
    @ResponseBody
    public List<Car> getAllCars() {
        return carService.getAllCars();
    }

    @PostMapping("/addClient")
    public ResponseEntity addClient(@RequestBody AddClientRequestBody addClientRequestBody) {
        Client query = Client
                .builder()
                .name(addClientRequestBody.getName())
                .surname(addClientRequestBody.getSurname())
                .email(addClientRequestBody.getEmail())
                .address(addClientRequestBody.getAddress())
                .build();

        Client newClient = clientService.addClient(query);
        return new ResponseEntity<>(newClient, HttpStatus.OK);
    }

    @PostMapping("/rentCar")
    @ResponseBody
    public ResponseEntity rentCar(@RequestBody CarRentalRequestBody carRentalRequestBody) {
        // 1. check if client with clientId exists
        UUID clientId = carRentalRequestBody.getClientId();
        if (!clientService.clientExists(clientId)) {
            return new ResponseEntity<>(
                    "\"Client with this clientId does not exist\"",
                    HttpStatus.BAD_REQUEST
            );
        }

        // 2. check if car with carId exists
        UUID carId = carRentalRequestBody.getCarId();
        Car car = carService.getCar(carId);
        if (car == null) {
            return new ResponseEntity<>(
                    "\"Car with this carId does not exist\"",
                    HttpStatus.BAD_REQUEST
            );
        }

        // 3. check if car is available
        if (car.getCarStatus() != CarStatus.AVAILABLE) {
            return new ResponseEntity<>(
                    "\"Car with this carId is not available\"",
                    HttpStatus.BAD_REQUEST
            );
        }

        // 4. check if startDate and endDate are valid
        // TODO:
        //     1. check if startDate <= endDate,
        //     2. startDate >= now()

        // 5. rent car
        carService.rentCar(
                clientId,
                car,
                carRentalRequestBody.getStartDate(),
                carRentalRequestBody.getEndDate()
        );

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/returnCar")
    @ResponseBody
    public ResponseEntity returnCar(@RequestBody CarReturnRequestBody returnCarRequestBody) {
        // 1. check if client with given clientId exists
        UUID clientId = returnCarRequestBody.getClientId();
        if (!clientService.clientExists(clientId)) {
            return new ResponseEntity<>(
                    "\"Client with this clientId does not exist\"",
                    HttpStatus.BAD_REQUEST
            );
        }

        // 2. check if carRental entry with given carRentalId exists
        UUID carRentalId = returnCarRequestBody.getCarRentalId();
        CarRental carRental = carRentalService.getCarRental(carRentalId);
        if (carRental == null) {
            return new ResponseEntity<>(
                    "\"CarRental entry with given carRentalId does not exist\"",
                    HttpStatus.BAD_REQUEST
            );
        }

        // 3. check if clientIds match
        UUID carRentalClientId = carRental.getClientEntity().getId();
        if (!clientId.equals(carRentalClientId)) {
            return new ResponseEntity<>(
                    "\"Given clientId does not match with the clientId belonging to the given carRental entry\"",
                    HttpStatus.BAD_REQUEST
            );
        }

        // 4. check if car has already been returned
        // NOTE! We assume that a user has returned a car when return_date is not null
        if (carRental.getCarReturnEntity().getReturn_date() != null) {
            return new ResponseEntity<>(
                    "\"The corresponding car has already been returned!\"",
                    HttpStatus.BAD_REQUEST
            );
        }

        Double amountToPay = carService.returnCar(carRental, returnCarRequestBody.getComments());

        CarRentalResponseBody response = CarRentalResponseBody
                .builder()
                .amountToPay(amountToPay >= 0 ? amountToPay : 0)
                .amountToReturn(amountToPay < 0 ? -amountToPay : 0)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/checkPayment")
    @ResponseBody
    public ResponseEntity checkPayment(@RequestBody CheckPaymentRequestBody checkPaymentRequestBody) {
        // 1. check if rentId exists
        UUID carRentalId = checkPaymentRequestBody.getCarRentalId();
        CarRental carRental = carRentalService.getCarRental(carRentalId);
        if (carRental == null) {
            return new ResponseEntity<>(
                    "\"CarRental entry with given carRentalId does not exist\"",
                    HttpStatus.BAD_REQUEST
            );
        }

        // 2. check if amountFromClient is correct
        Double amountFromClient = checkPaymentRequestBody.getAmountFromClient();
        Double surcharge = carRental.getCarReturnEntity().getSurcharge();

        if (!surcharge.equals(amountFromClient)) {
            return new ResponseEntity<>(
                    "\"Amount of surcharge is not correct. The correct surcharge is: " + surcharge + "\"",
                    HttpStatus.BAD_REQUEST
            );
        }

        return ResponseEntity.ok(HttpStatus.OK);
    }
}