package com.example.CarRental.domain.repository;

import com.example.CarRental.domain.model.CarReturnEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface CarReturnRepository extends CrudRepository<CarReturnEntity, UUID> {
}
