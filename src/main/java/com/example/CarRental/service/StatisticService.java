package com.example.CarRental.service;

import com.example.CarRental.domain.model.CarEntity;
import com.example.CarRental.domain.repository.CarRentalRepository;
import com.example.CarRental.domain.repository.CarRepository;
import com.example.CarRental.domain.repository.CarReturnRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class StatisticService {

    @Autowired
    CarRentalRepository carRentalRepository;

    @Autowired
    CarReturnRepository carReturnRepository;

    @Autowired
    CarRepository carRepository;

    public Double CalculateIncome() {
//        Musi policzyc sume amount+surcharge
//        проверить есть ли вообще записи в базе данных
        if( carRentalRepository.findAll() == null){return 0.0;}

        List<Double> tempList = new ArrayList<>();

        carRentalRepository.
                findAll()
                .forEach(x -> tempList.add(x.getAmount() + x.getCarReturnEntity().getSurcharge()));

        return tempList
                .stream()
                .reduce(0.0, Double::sum);
    }
//       1) I am add validation @NonNull in the CarRentalEntity and CarRenturnEntity therefore  I can't get null;
//       2) I didn't add validation on the negative value because income also can be negative;


    //    1. Ile konkretny samochod dal przychodu.

    public Double sumOfDefinedCar(UUID carId){

        if (carRentalRepository.findAllRentalDealWithCar(carRepository.getById(carId).get()) == null){return 0.0;}

        List<Double> tempList = new ArrayList<>();
//    проверка существует ли автомобил + существуют ли такие рекорды с рентал енитити
        carRentalRepository
                .findAllRentalDealWithCar(carRepository.getById(carId).get())
                .forEach(x -> tempList.add(x.getAmount() + x.getCarReturnEntity().getSurcharge()));

        return tempList
                .stream()
                .reduce(0.0, Double::sum);
    }

//    2. Ktory samochod zostal ile razy wyporzyczony


}





