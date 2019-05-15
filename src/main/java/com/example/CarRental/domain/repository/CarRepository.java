package com.example.CarRental.domain.repository;


import com.example.CarRental.domain.model.CarEntity;
import com.example.CarRental.model.AvailableCarsQuery;
import com.example.CarRental.model.Car;
import com.example.CarRental.model.CarStatus;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CarRepository extends CrudRepository<CarEntity, UUID> {


    @Query("SELECT a FROM CarEntity a" +
            " WHERE a.carStatus = 'AVAILABLE' " +
            "AND ((a.amount = :amount OR a.carBodyType = :bodyType  OR a.model = :model OR a.releaseYear = :reliseYear) " +
            "OR (a.amount = :amount AND a.carBodyType = :bodyType) " +
            "OR (a.amount = :amount AND a.model = :model) " +
            "OR (a.amount = :amount AND a.releaseYear = :reliseYear)" +
            "OR (a.carBodyType = :bodyType  AND a.model = :model) " +
            "OR (a.carBodyType = :bodyType AND a.releaseYear = :reliseYear) " +
            "OR (a.model = :model AND a.releaseYear = :reliseYear))")
    List<CarEntity> getAvailableCarsByParameter(
            @Param("amount") Double amount,
            @Param("bodyType") String bodyType,
            @Param("model")String model,
            @Param("reliseYear")Integer reliseYear
            );

    @Query("SELECT a FROM CarEntity a")
    List<CarEntity> getAllCars();

//    query.getAmount(),query.getCarBodyType(),query.getCarStatus(),
//            query.getModel(),query.getReleaseYear());



//    /    @Query(value = "SELECT * FROM User c WHERE c.age = ?1 ",nativeQuery = true)
//        List<User> findAllByAddress(String s);

//    @Query("SELECT a FROM CarEntity a" +
//            " WHERE a.carStatus = 'AVAILABLE' AND a.amount = :amount OR a.carBodyType = :bodyType " +
////            " OR a.model = :model OR a.releaseYear = :reliseYear")

//    @Query("SELECT u FROM User u WHERE u.status = :status and u.name = :name")
//    User findUserByStatusAndNameNamedParams(
//            @Param("status") Integer status,
//            @Param("name") String name);

}
