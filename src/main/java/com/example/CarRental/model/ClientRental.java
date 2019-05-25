package com.example.CarRental.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Builder
@Data
public class ClientRental {
    private UUID id;

    private UUID carId;

    private LocalDate rentDate;
    private LocalDate returnDate;

    private LocalDate startDate;
    private LocalDate endDate;

    private Double amount;

    private String comments;
    private RentalStatus status;
}
