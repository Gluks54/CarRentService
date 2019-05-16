package com.example.CarRental.loadToDB;


import com.example.CarRental.domain.model.CarEntity;
import com.example.CarRental.domain.model.CarRentalEntity;
import com.example.CarRental.domain.model.CarReturnEntity;
import com.example.CarRental.domain.model.ClientEntity;
import com.example.CarRental.domain.repository.CarRentalRepository;
import com.example.CarRental.domain.repository.CarRepository;
import com.example.CarRental.domain.repository.CarReturnRepository;
import com.example.CarRental.domain.repository.ClientRepository;

import com.example.CarRental.model.CarReturn;
import com.example.CarRental.model.CarStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Component
public class LoadDbClass {

    @Autowired
    CarRentalRepository carRentalRepository;

    @Autowired
    CarRepository carRepository;

    @Autowired
    CarReturnRepository carReturnRepository;

    @Autowired
    ClientRepository clientRepository;




    @PostConstruct
    public void loadDataToDb(){


        CarEntity carEntity1 = CarEntity
                .builder()
                .carBodyType("Sedan")
                .model("Volvo")
                .carStatus(CarStatus.AVAILABLE)
                .amount(Double.valueOf(20.0))
                .releaseYear(Integer.valueOf(2001))
                .build();


//        CarRentalEntity carRentalEntity = CarRentalEntity
//                .builder()
//                .amount(Double.valueOf(40.0))
//                .startDate(new Date(120592))
//                .endDate(new Date(100592))
////                .carReturnEntity(carReturnEntity1)
//                .rentDate(new Date(140303))
////                .clientEntity_id(clientEntity1)
//
//                .build();


//        carRepository.save(carEntity1);}}

        CarEntity carEntity2 = CarEntity
                .builder()
                .carBodyType("Hatchback")
                .model("Mercedes-Benz")
                .carStatus(CarStatus.AVAILABLE)
                .amount(Double.valueOf(25.0))
                .releaseYear(Integer.valueOf(2011))
                .build();


        CarEntity carEntity3 = CarEntity
                .builder()
                .carBodyType("Crossover")
                .model("Land Rover")
                .carStatus(CarStatus.AVAILABLE)
                .amount(Double.valueOf(24.0))
                .releaseYear(Integer.valueOf(2010))
                .build();

        CarEntity carEntity4 = CarEntity
                .builder()
                .carBodyType("Minivan")
                .model("Suzuki")
                .carStatus(CarStatus.AVAILABLE)
                .amount(Double.valueOf(50.0))
                .releaseYear(Integer.valueOf(2019))
                .build();

        CarEntity carEntity5 = CarEntity
                .builder()
                .carBodyType("Crossover")
                .model("Audi ")
                .carStatus(CarStatus.RENTED)
                .amount(Double.valueOf(32.0))
                .releaseYear(Integer.valueOf(2016))
                .build();


//        carRepository.save(carEntity1);
        carRepository.save(carEntity2);
        carRepository.save(carEntity3);
        carRepository.save(carEntity4);
        carRepository.save(carEntity5);


        ClientEntity clientEntity1 = ClientEntity
                .builder()
                .name("Marian")
                .surname("Worzniak")
                .address("Komorowska 4")
                .email("mariam@gmail.com")
                .build();

        ClientEntity clientEntity2 = ClientEntity
                .builder()
                .name("Edek")
                .surname("Grocholski")
                .address("brzozowa 12")
                .email("Edek@gmail.com")
                .build();


        ClientEntity clientEntity3 = ClientEntity
                .builder()
                .name("Janek")
                .surname("Dzubel")
                .address("Lesna 15")
                .email("Janek@gmail.com")
                .build();


//        clientRepository.save(clientEntity1);
        clientRepository.save(clientEntity2);
        clientRepository.save(clientEntity3);


        LocalDate date1 = LocalDate.of(2001,3,22);
        LocalDate date2 = LocalDate.of(2011,10,15);

        CarReturnEntity carReturnEntity1 = CarReturnEntity
                .builder()
                .surcharge(Double.valueOf(29))
                .comments("it was good car")
                .return_date(date1)
                .build();



        CarReturnEntity carReturnEntity2 = CarReturnEntity
                .builder()
                .surcharge(Double.valueOf(25))
                .comments("it was bad car")
                .return_date(date2)
                .build();


        LocalDate startDate = LocalDate.of(1992,12,22);
        LocalDate endDate = LocalDate.of(1993,10,22);
        LocalDate rentDate = LocalDate.of(1994,9,15);


        CarRentalEntity carRentalEntity = CarRentalEntity
                .builder()
                .amount(Double.valueOf(40.0))
                .startDate(startDate)
                .endDate(endDate)
                .carReturnEntity(carReturnEntity1)
                .rentDate(rentDate)
                .clientEntity_id(clientEntity1)
                .carEntity_id(carEntity1)
                .build();

        carRentalRepository.save(carRentalEntity);

    }}

