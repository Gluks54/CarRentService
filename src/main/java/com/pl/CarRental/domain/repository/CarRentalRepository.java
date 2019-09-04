package com.pl.CarRental.domain.repository;

import com.pl.CarRental.domain.model.CarEntity;
import com.pl.CarRental.domain.model.CarRentalEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface CarRentalRepository extends CrudRepository<CarRentalEntity, UUID> {

    @Query("SELECT a FROM CarRentalEntity a WHERE a.carEntity_id = ?1")
    List<CarRentalEntity> findAllRentalDealWithCar(CarEntity carEntity);
}
