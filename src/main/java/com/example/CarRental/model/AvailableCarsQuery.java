package com.example.CarRental.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AvailableCarsQuery {
    private String model;
    private String carBodyType;
    private Integer releaseYear;
    private CarStatus carStatus;
    private Double amount;
}
