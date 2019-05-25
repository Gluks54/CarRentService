package com.example.CarRental.service;

import com.example.CarRental.domain.model.CarEntity;
import com.example.CarRental.domain.model.CarRentalEntity;
import com.example.CarRental.domain.model.CarReturnEntity;
import com.example.CarRental.domain.model.ClientEntity;
import com.example.CarRental.domain.repository.CarRentalRepository;
import com.example.CarRental.domain.repository.CarReturnRepository;
import com.example.CarRental.model.CarRental;
import com.example.CarRental.model.ClientRental;
import com.example.CarRental.model.RentalStatus;
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
public class CarRentalService {

    @Autowired
    CarRentalRepository carRentalRepository;

    @Autowired
    CarReturnRepository carReturnRepository;

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
                .status(RentalStatus.RENTED)
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

    public CarReturnEntity updateReturnEntry(CarRental carRental, String comments) {
        LocalDate returnDate = LocalDate.now();

        long days = Duration.between(carRental.getEndDate().atStartOfDay(), returnDate.atStartOfDay()).toDays();
        Double pricePerDay = carRental.getCarEntity().getAmount();

        Double subcharge = days * pricePerDay;

        CarReturnEntity carReturnEntity = carRental.getCarReturnEntity();
        carReturnEntity.setReturn_date(returnDate);
        carReturnEntity.setComments(comments);
        carReturnEntity.setSurcharge(subcharge);
        carReturnEntity.setStatus(RentalStatus.RETURNED_BUT_CHARGE_NOT_SETTLED);
        carReturnRepository.save(carReturnEntity);

        return carReturnEntity;
    }

    public List<ClientRental> getClientRentals(UUID clientId) {
        return StreamSupport
                .stream(carRentalRepository.findAll().spliterator(), false)
                .filter(item -> item.getClientEntity_id().getId() == clientId)
                .map(item -> ClientRental
                    .builder()
                    .id(item.getId())
                    .carId(item.getCarEntity_id().getId())
                    .rentDate(item.getRentDate())
                    .returnDate(item.getCarReturnEntity().getReturn_date())
                    .startDate(item.getStartDate())
                    .endDate(item.getEndDate())
                    .amount(item.getAmount())
                    .comments(item.getCarReturnEntity().getComments())
                    .status(item.getCarReturnEntity().getStatus())
                    .build()
                )
                .collect(Collectors.toList());
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
                .carReturnEntity(source.getCarReturnEntity())
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
                .carReturnEntity(source.getCarReturnEntity())
                .build();
    }
}
