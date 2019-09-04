package com.pl.CarRental.domain.repository;

import com.pl.CarRental.domain.model.CarReturnEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface CarReturnRepository extends CrudRepository<CarReturnEntity, UUID> {
}
