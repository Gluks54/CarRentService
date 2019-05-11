package com.example.CarRental.domain.repository;


import com.example.CarRental.domain.model.CarEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CarRepository extends CrudRepository<CarEntity, UUID> {
    List<CarEntity> findAllById(Long l);
}
