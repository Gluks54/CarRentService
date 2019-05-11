package com.example.CarRental.domain.repository;

import com.example.CarRental.domain.model.ClientEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ClientRepository extends CrudRepository<ClientEntity, UUID> {
}
