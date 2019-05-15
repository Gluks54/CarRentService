package com.example.CarRental.domain.repository;

import com.example.CarRental.domain.model.ClientEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface ClientRepository extends CrudRepository<ClientEntity, UUID> {
    @Modifying
    @Query(value = "INSERT INTO ClientEntity VALUES(:name, :surname, :email, :address)", nativeQuery = true)
    void addClient(
            @Param("name") String name,
            @Param("surname") String surname,
            @Param("email") String email,
            @Param("address") String address
    );
}
