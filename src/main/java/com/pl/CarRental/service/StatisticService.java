package com.pl.CarRental.service;

import com.pl.CarRental.domain.model.ClientEntity;
import com.pl.CarRental.domain.repository.CarRentalRepository;
import com.pl.CarRental.domain.repository.CarRepository;
import com.pl.CarRental.domain.repository.ClientRepository;
import com.pl.CarRental.model.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class StatisticService {
    private final CarRentalRepository carRentalRepository;
    private final CarRepository carRepository;
    private final CarService carService;
    private final ClientRepository clientRepository;

    @Autowired
    public StatisticService(
            CarRentalRepository carRentalRepository,
            CarRepository carRepository,
            CarService carService,
            ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
        this.carRentalRepository = carRentalRepository;
        this.carService = carService;
        this.carRepository = carRepository;
    }

    public Double CalculateIncome() {
//        Musi policzyc sume amount+surcharge
        if (carRentalRepository.findAll() == null) {
            return 0.0;
        }

        List<Double> tempList = new ArrayList<>();

        carRentalRepository.
                findAll()
                .forEach(x -> tempList.add(x.getAmount() + ((x.getCarReturnEntity().getSurcharge() == null) ? 0.0 : x.getCarReturnEntity().getSurcharge())));

        return tempList
                .stream()
                .reduce(0.0, Double::sum);
    }

    //    1. Ile konkretny samochod dal przychodu.

    public Double sumOfDefinedCar(UUID carId) {

        if (carRentalRepository.findAllRentalDealWithCar(carRepository.findById(carId).get()) == null) {
            return 0.0;
        }

        List<Double> tempList = new ArrayList<>();

        carRentalRepository
                .findAllRentalDealWithCar(carRepository.findById(carId).get())
                .forEach(x -> tempList.add(x.getAmount() + ((x.getCarReturnEntity().getSurcharge() == null) ? 0.0 : x.getCarReturnEntity().getSurcharge())));

        return tempList
                .stream()
                .reduce(0.0, Double::sum);
    }

    //    2. Ktory samochod zostal ile razy wyporzyczony
    public Integer countValueNumberOfUseCar(UUID carID) {
        return carRepository.countValueNumberOfUseCar(carID);
    }

    public Integer countValueRentOfClient(UUID clientID) {
        return clientRepository.countValueRentOfClient(clientID);
    }


    //    3. Jaki model samochodu jest najczesciej wypozyczany
    public UUID mostPopularCar() {

        List<Car> temListOfCar = carService.getAllCars();

        Map<UUID, Integer> allStatisticOfUseCar = temListOfCar
                .stream()
                .collect(Collectors.toMap(x -> x.getId(),
                        x -> countValueNumberOfUseCar(x.getId())));

        if (allStatisticOfUseCar.size() == 0) {
            return null;
        }

        return allStatisticOfUseCar
                .entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .findFirst()
                .get()
                .getKey();
    }

    public UUID notPopCar() {
        List<Car> temListOfCar = carService.getAllCars();

        Map<UUID, Integer> allStatisticOfNotPopCar = temListOfCar
                .stream()
                .collect(Collectors.toMap(x -> x.getId(),
                        x -> countValueNumberOfUseCar(x.getId())));

        if (allStatisticOfNotPopCar.size() == 0) {
            return null;
        }

        return allStatisticOfNotPopCar
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .findFirst()
                .get()
                .getKey();
    }

    //        5. Ktory klient wypozyczyl najwiecej
    public UUID bestClient() {
        List<ClientEntity> tempListOfClientEntity = new ArrayList<>();

        clientRepository
                .findAll()
                .forEach(x -> tempListOfClientEntity.add(x));

        Map<UUID, Integer> allStatRentClient = tempListOfClientEntity
                .stream()
                .collect(Collectors.toMap(x -> x.getId(),
                        x -> countValueRentOfClient(x.getId())));

        if (allStatRentClient.size() == 0) {
            return null;
        }

        return allStatRentClient
                .entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .findFirst()
                .get()
                .getKey();
    }
}







