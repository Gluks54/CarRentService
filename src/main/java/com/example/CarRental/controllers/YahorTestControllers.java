package com.example.CarRental.controllers;

import com.example.CarRental.domain.repository.CarRentalRepository;
import com.example.CarRental.model.AvailableCarsQuery;
import com.example.CarRental.model.Car;
import com.example.CarRental.model.CarStatus;
import com.example.CarRental.model.Client;
import com.example.CarRental.service.CarService;
import com.example.CarRental.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
public class YahorTestControllers {

    @Autowired
    CarService carService;

    @Autowired
    StatisticService statisticService;

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

//    @GetMapping("/listOfClients")
//    List<Client> getAllClients(){
//        return carService.getAllClients();
//    }


    @PostMapping("/addClienstSecondVersion")
    public void addClient() {


        Client client = Client
                .builder()
                .name("Marcin")
                .surname("Pazurak")
                .address("Katowice 4")
                .email("mazxzxriam@gmail.com")
                .build();
    }

        @GetMapping ("/getIncome")
        public Double getIncome(){
            return statisticService.CalculateIncome();
        }


        @GetMapping("/getSumOfDefineCar")
        public Double getSumOfDefineCar(@RequestParam("CarID")String carID){

        return statisticService.sumOfDefinedCar(UUID.fromString(carID));
//            return null;
        }
//
//        System.out.println(carService.addClient(client));
////'Content-Type': 'application/json'
//    }


//    @GetMapping("/addCarTest")
//    public void addCartest(){
//       Car car = Car
//               .builder()
//               .carBodyType("Crossover")
//               .model("AudiTT ")
//               .carStatus(CarStatus.RENTED)
//               .amount(Double.valueOf(50.0))
//               .releaseYear(Integer.valueOf(2016))
//               .build();
//
//        carService.addCar(car);
//    }

//    @GetMapping("/testMethodAddRentCar")
//    public void testMethod(@RequestParam("carId") String carId,
//                           @RequestParam("clientid") String clientid){
//
//        LocalDate date1 = LocalDate.of(2019,10,10);
//        LocalDate date2 = LocalDate.of(2019,10,15);
//        System.out.println(UUID.fromString(carId));
//        carService.addRentCar(UUID.fromString(carId),UUID.fromString(clientid),date1,date2);
//
//    }

//    @PostMapping("/checkPayment")
//    @ResponseBody
//    public RequestEntity chackPayment(@RequestParam("rentId") String rentId,
//                                      @RequestParam ("amount") Double amountFromClient){
//
//        if(carService.checkPayment(UUID.fromString(rentId),amountFromClient)){
//            return  new ResponseEntity< String >("everything is Ok", HttpStatus.OK);
//        }else new ResponseEntity< String >("everything is Ok", HttpStatus.BAD_REQUEST;    }
//
//
//    }
}
