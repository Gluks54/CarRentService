package com.example.CarRental.model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
@Builder
@Data
public class Car {

    private  String model;

    private  String carBodyType;

    private Integer releaseYear;

//    @Enumerated(EnumType.STRING)
    private CarStatus carStatus;

    private Double amount;
}
