package com.example.CarRental.service;

import com.example.CarRental.domain.model.CarEntity;
import com.example.CarRental.domain.model.CarReturnEntity;
import com.example.CarRental.domain.model.ClientEntity;
import com.example.CarRental.domain.repository.CarRepository;
import com.example.CarRental.domain.repository.ClientRepository;
import com.example.CarRental.model.AvailableCarsQuery;
import com.example.CarRental.model.Car;
import com.example.CarRental.model.CarRental;
import com.example.CarRental.model.CarStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CarService {

    @Autowired
    CarRepository carRepository;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    CarRentalService carRentalService;

    public List<Car> getAvailableCarsByParameter(AvailableCarsQuery query) {
        List<CarEntity> carEntities = carRepository
                .getAvailableCarsByParameter(query.getAmount(), query.getCarBodyType(),
                        query.getModel(), query.getReleaseYear());

        return carEntities.stream()
                .map(x -> map(x))
                .collect(Collectors.toList());
    }

    public List<Car> getAllCars() {
        return StreamSupport.stream(carRepository.findAll().spliterator(), false)
                .map(x -> map(x))
                .collect(Collectors.toList());
    }

    public UUID rentCar(UUID clientId, Car car, LocalDate startDate, LocalDate endDate) {
        Double amountFromCar = car.getAmount();
        long days = Duration.between(startDate.atStartOfDay(), endDate.atStartOfDay()).toDays();

        ClientEntity clientEntity = clientRepository.findById(clientId).get();

        updateCarStatus(car.getId(), CarStatus.RENTED);

        return carRentalService.addEntry(
                startDate,
                endDate,
                clientEntity,
                map(car),
                amountFromCar * days
        );
    }

    public Double returnCar(CarRental carRental, String comments) {
        CarReturnEntity carReturnEntity = carRentalService.updateReturnEntry(carRental, comments);
        updateCarStatus(carRental.getCarEntity().getId(), CarStatus.AVAILABLE);
        return carReturnEntity.getSurcharge();
    }

    public Car getCar(UUID carId) {
        Optional<CarEntity> carEntityOptional = carRepository.findById(carId);
        if (!carEntityOptional.isPresent()) {
            return null;
        }
        return map(carEntityOptional.get());
    }

    public void updateCarStatus(UUID carId, CarStatus carStatus) {
        Optional<CarEntity> carEntityOptional = carRepository.findById(carId);
        if (!carEntityOptional.isPresent()) {
            return;
        }
        CarEntity carEntity = carEntityOptional.get();
        carEntity.setCarStatus(carStatus);
        carRepository.save(carEntity);
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
