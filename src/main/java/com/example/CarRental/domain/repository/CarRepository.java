package com.example.CarRental.domain.repository;
import com.example.CarRental.domain.model.CarEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface CarRepository extends JpaRepository<CarEntity, UUID> {

    @Query("SELECT a FROM CarEntity a" +
            " WHERE a.carStatus = 'AVAILABLE' " +
            "AND (a.amount = :amount OR a.carBodyType = :bodyType OR a.model = :model OR a.releaseYear = :reliseYear)")
    List<CarEntity> getAvailableCarsByParameter(
            @Param("amount") Double amount,
            @Param("bodyType") String bodyType,
            @Param("model")String model,
            @Param("reliseYear")Integer reliseYear
            );

    @Query(value = "" +
            "SELECT COUNT(*)\n" +
            "FROM car_entity\n" +
            "INNER JOIN car_rental_entity\n" +
            "ON car_entity.id = car_rental_entity.car_id AND car_entity.id =?1 ",nativeQuery = true)

    Integer countValueNumberOfUseCar(UUID carId);
}
