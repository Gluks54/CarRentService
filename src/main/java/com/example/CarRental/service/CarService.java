package com.example.CarRental.service;

import com.example.CarRental.domain.model.CarEntity;
import com.example.CarRental.domain.model.ClientEntity;
import com.example.CarRental.domain.repository.CarRepository;
import com.example.CarRental.domain.repository.ClientRepository;
import com.example.CarRental.model.AvailableCarsQuery;
import com.example.CarRental.model.Car;
import com.example.CarRental.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CarService {


    @Autowired
    CarRepository carRepository;

    @Autowired
    ClientRepository clientRepository;


    public List<Car> getAvailableCarsByParameter(AvailableCarsQuery query){
        List<CarEntity> carEntities = carRepository
                .getAvailableCarsByParameter(query.getAmount(),query.getCarBodyType(),
                        query.getModel(),query.getReleaseYear());

        return carEntities.stream()
                .map(x->map(x))
                .collect(Collectors.toList());
    }


    public Car map(CarEntity source){
        return Car
                .builder()
                .id(source.getId())
                .carBodyType(source.getCarBodyType())
                .carStatus(source.getCarStatus())
                .amount(source.getAmount())
                .model(source.getModel())
                .releaseYear(source.getReleaseYear())
                .build();
    }

    public CarEntity map(Car source){
        return CarEntity
                .builder()
                .carBodyType(source.getCarBodyType())
                .carStatus(source.getCarStatus())
                .amount(source.getAmount())
                .model(source.getModel())
                .releaseYear(source.getReleaseYear())
                .build();
    }
}
