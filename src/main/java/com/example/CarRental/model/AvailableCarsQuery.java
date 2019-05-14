package com.example.CarRental.model;

import com.example.CarRental.model.CarStatus;
import lombok.Builder;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
@Data
@Builder
public class AvailableCarsQuery {
    private  String model;
    private  String carBodyType;
    private Integer releaseYear;
    private CarStatus carStatus;
    private Double amount;
}
