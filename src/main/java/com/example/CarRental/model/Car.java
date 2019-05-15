package com.example.CarRental.model;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public class Car{

    private UUID id;

    private String model;

    private String carBodyType;

    private Integer releaseYear;

    private CarStatus carStatus;

    private Double amount;
}
