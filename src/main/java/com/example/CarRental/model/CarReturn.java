package com.example.CarRental.model;

import com.example.CarRental.domain.model.CarRentalEntity;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Builder
@Data
public class CarReturn {

    private Date return_date;

    private String comments;

    private String surcharge;

    UUID CarRental;
}
