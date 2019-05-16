package com.example.CarRental.service;

import com.example.CarRental.domain.model.CarEntity;
import com.example.CarRental.domain.model.CarRentalEntity;
import com.example.CarRental.domain.model.CarReturnEntity;
import com.example.CarRental.domain.model.ClientEntity;
import com.example.CarRental.domain.repository.CarRentalRepository;
import com.example.CarRental.model.CarRental;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Service
public class CarRentalService {

    @Autowired
    CarRentalRepository carRentalRepository;

    public UUID addEntry(
            LocalDate startDate,
            LocalDate endDate,
            ClientEntity clientEntity,
            CarEntity carEntity,
            Double amount
    ) {
        // create an empty entry in CarReturn table
        CarReturnEntity carReturnEntity = CarReturnEntity
                .builder()
                .return_date(null)
                .build();

        CarRentalEntity carRentalEntity = CarRentalEntity
                .builder()
                .amount(amount)
                .startDate(startDate)
                .endDate(endDate)
                .rentDate(LocalDate.now())
                .carEntity_id(carEntity)
                .clientEntity_id(clientEntity)
                .carReturnEntity(carReturnEntity)
                .build();

        return carRentalRepository.save(carRentalEntity).getId();
    }

    public CarRental getCarRental(UUID carRentalId) {
        Optional<CarRentalEntity> carRentalEntityOptional = carRentalRepository.findById(carRentalId);
        if (!carRentalEntityOptional.isPresent()) {
            return null;
        }
        return map(carRentalEntityOptional.get());
    }

    public CarRental map(CarRentalEntity source) {
        return CarRental
                .builder()
                .id(source.getId())
                .rentDate(source.getRentDate())
                .clientEntity(source.getClientEntity_id())
                .carEntity(source.getCarEntity_id())
                .startDate(source.getStartDate())
                .endDate(source.getEndDate())
                .amount(source.getAmount())
                .build();
    }

    public CarRentalEntity map(CarRental source) {
        return CarRentalEntity
                .builder()
                .rentDate(source.getRentDate())
                .clientEntity_id(source.getClientEntity())
                .carEntity_id(source.getCarEntity())
                .startDate(source.getStartDate())
                .endDate(source.getEndDate())
                .amount(source.getAmount())
                .build();
    }
}
