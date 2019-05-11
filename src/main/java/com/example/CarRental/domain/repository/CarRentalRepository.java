package com.example.CarRental.domain.repository;

import com.example.CarRental.domain.model.CarRentalEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface CarRentalRepository extends CrudRepository<CarRentalEntity, UUID> {
}
