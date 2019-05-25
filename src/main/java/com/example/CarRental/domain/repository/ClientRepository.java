package com.example.CarRental.domain.repository;

import com.example.CarRental.domain.model.ClientEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.UUID;

public interface ClientRepository extends CrudRepository<ClientEntity, UUID> {

    @Query(value = "" +
            "SELECT COUNT(*)\n"   +
            "FROM client_entity\n" +
            "INNER JOIN car_rental_entity\n" +
            "ON client_entity.id = car_rental_entity.client_entity_id AND client_entity.id = ?1" ,nativeQuery = true)
    Integer countValueRentOfClient(UUID clientID);
}
