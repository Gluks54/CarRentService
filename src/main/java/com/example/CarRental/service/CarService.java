package com.example.CarRental.service;

import com.example.CarRental.domain.model.CarEntity;
import com.example.CarRental.domain.model.CarRentalEntity;
import com.example.CarRental.domain.model.CarReturnEntity;
import com.example.CarRental.domain.model.ClientEntity;
import com.example.CarRental.domain.repository.CarRentalRepository;
import com.example.CarRental.domain.repository.CarRentalRepository;
import com.example.CarRental.domain.repository.CarRepository;
import com.example.CarRental.domain.repository.ClientRepository;
import com.example.CarRental.model.AvailableCarsQuery;
import com.example.CarRental.model.Car;
import com.example.CarRental.model.CarStatus;
import com.example.CarRental.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.DAYS;

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

     Double amountFromCar = car.get().getAmount();

     Double periodOfDay = (double)DAYS.between(startDate, endDate);

        CarRentalEntity carRentalEntity =  CarRentalEntity
                .builder()
                .amount(amountFromCar * periodOfDay)
                .startDate(startDate)
                .endDate(endDate)
                .rentDate(LocalDate.now())
                .carEntity_id(car.get())
                .clientEntity_id(client.get())
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

    public Car getCar(UUID carID){
      return map(carRepository.findById(carID).get());
    }


    public boolean checkPayment(UUID rentId, Double amountFromClient) {

      Double amountFromRent =  carRentalRepository.findById(rentId).get().getAmount();

      Double amountFromReturn = carRentalRepository.findById(rentId).get().getCarReturnEntity().getSurcharge();

      Double controlSum = amountFromRent + amountFromReturn;

      if(amountFromClient == controlSum){
          return true;
      }else return false;

    }


    public boolean updateCarStatus(UUID carID, CarStatus carStatus) {
        if(carRepository.findById(carID).isPresent()) {
            carRepository.findById(carID).get().setCarStatus(carStatus);
            return true;
        }else return false;
    }
}
