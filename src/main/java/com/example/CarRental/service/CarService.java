package com.example.CarRental.service;

import com.example.CarRental.domain.model.CarEntity;
import com.example.CarRental.domain.model.CarRentalEntity;
import com.example.CarRental.domain.model.CarReturnEntity;
import com.example.CarRental.domain.model.ClientEntity;
import com.example.CarRental.domain.repository.CarRentalRepository;
import com.example.CarRental.domain.repository.CarRepository;
import com.example.CarRental.domain.repository.ClientRepository;
import com.example.CarRental.model.AvailableCarsQuery;
import com.example.CarRental.model.Car;
import com.example.CarRental.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CarService {


    @Autowired
    CarRepository carRepository;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    CarRentalRepository carRentalRepository;


    public List<Car> getAvailableCarsByParameter(AvailableCarsQuery query) {
        List<CarEntity> carEntities = carRepository
                .getAvailableCarsByParameter(query.getAmount(), query.getCarBodyType(),
                        query.getModel(), query.getReleaseYear());

        return carEntities.stream()
                .map(x -> map(x))
                .collect(Collectors.toList());
    }

    public List<Car> getAllCars() {
        List<CarEntity> carEntities = carRepository.getAllCars();

        return carEntities.stream()
                .map(x -> map(x))
                .collect(Collectors.toList());
    }

    public UUID addCar(Car car){
        return carRepository.save(map(car)).getId();
    }

    public UUID addClient(Client client){
        return clientRepository.save(map(client)).getId();
    }

    public List<Client> getAllClients(){
        List<Client> clients = new ArrayList<>();
        clientRepository.getAllClients().forEach(x->clients.add(map(x)));
        return clients;
    }


    public UUID addRentCar(UUID carId, UUID clientid, LocalDate startDate, LocalDate endDate){
     Optional<CarEntity> car = carRepository.findById(carId);
     Optional<ClientEntity> client = clientRepository.findById(clientid);

        CarReturnEntity carReturnEntity = CarReturnEntity
                .builder()
                .build();

     Double amountFromCar = car.get().getAmount();

     Period period = Period.between(startDate, endDate);

     Double periodOfDay = (double) (period.getYears() * 365 + period.getMonths() * 30 + period.getDays());

        CarRentalEntity carRentalEntity =  CarRentalEntity
                .builder()
                .amount(amountFromCar * periodOfDay)
                .startDate(startDate)
                .endDate(endDate)
                .rentDate(LocalDate.now())
                .carEntity_id(car.get())
                .clientEntity_id(client.get())
                .carReturnEntity(carReturnEntity)
                .build();

        return carRentalRepository.save(carRentalEntity).getId();
    }



    private Client map(ClientEntity source){
        return Client
                .builder()
                .address(source.getAddress())
                .email(source.getEmail())
                .name(source.getName())
                .surname(source.getSurname())
                .build();
    }

    private ClientEntity  map(Client source){
        return  ClientEntity
                .builder()
                .address(source.getAddress())
                .email(source.getEmail())
                .name(source.getName())
                .surname(source.getSurname())
                .build();
    }

    public Car map(CarEntity source) {
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

    public CarEntity map(Car source) {
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
