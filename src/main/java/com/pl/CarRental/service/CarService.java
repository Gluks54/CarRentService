package com.pl.CarRental.service;

import com.pl.CarRental.domain.model.CarEntity;
import com.pl.CarRental.domain.model.CarReturnEntity;
import com.pl.CarRental.domain.model.ClientEntity;
import com.pl.CarRental.domain.repository.CarRepository;
import com.pl.CarRental.domain.repository.ClientRepository;
import com.pl.CarRental.model.AvailableCarsQuery;
import com.pl.CarRental.model.Car;
import com.pl.CarRental.model.CarRental;
import com.pl.CarRental.model.CarStatus;
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
    private final CarRepository carRepository;
    private final ClientRepository clientRepository;
    private final CarRentalService carRentalService;

    @Autowired
    public CarService(
            CarRepository carRepository,
            ClientRepository clientRepository,
            CarRentalService carRentalService) {
        this.carRepository = carRepository;
        this.clientRepository = clientRepository;
        this.carRentalService = carRentalService;
    }

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

        if (days == 0) {
            days++;
        }

        return carRentalService.addEntry(
                startDate,
                endDate,
                clientEntity,
                carRepository.findById(car.getId()).get(),
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
                .URL(source.getURL())
                .build();
    }

    public CarEntity map(Car source) {
        return CarEntity
                .builder()
                .id(source.getId())
                .carBodyType(source.getCarBodyType())
                .carStatus(source.getCarStatus())
                .amount(source.getAmount())
                .model(source.getModel())
                .releaseYear(source.getReleaseYear())
                .URL(source.getURL())
                .build();
    }
}
