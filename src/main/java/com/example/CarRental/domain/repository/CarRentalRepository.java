package com.example.CarRental.domain.repository;

import com.example.CarRental.domain.model.CarEntity;
import com.example.CarRental.domain.model.CarRentalEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CarRentalRepository extends CrudRepository<CarRentalEntity, UUID> {
    Optional<CarRentalEntity> findById(UUID id);

    @Query("SELECT a FROM CarRentalEntity a WHERE a.carEntity_id = ?1")
    List<CarRentalEntity> findAllRentalDealWithCar(CarEntity carEntity);


}
