package com.example.CarRental.domain.repository;

import com.example.CarRental.domain.model.ClientEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
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

    @Query("SELECT a FROM ClientEntity a")
    List<ClientEntity> getAllClients();

    Optional<ClientEntity> findById(UUID clientID);

    @Query(value = "" +
            "SELECT COUNT(*)\n"   +
            "FROM client_entity\n" +
            "INNER JOIN car_rental_entity\n" +
            "ON client_entity.id = car_rental_entity.client_entity_id AND client_entity.id = ?1" ,nativeQuery = true)
    Integer countValueRentOfClient(UUID clientID);
}
