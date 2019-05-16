package com.example.CarRental.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Builder
@Data
public class CarRentalRequestBody {

    private UUID clientId;

    private UUID carId;

    private LocalDate startDate;

    private LocalDate endDate;
}
