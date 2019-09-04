package com.pl.CarRental.model;

import com.pl.CarRental.domain.model.CarEntity;
import com.pl.CarRental.domain.model.CarReturnEntity;
import com.pl.CarRental.domain.model.ClientEntity;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Builder
@Data
public class CarRental {
    private UUID id;

    private LocalDate rentDate;

    private ClientEntity clientEntity;

    private CarEntity carEntity;

    private LocalDate startDate;

    private LocalDate endDate;

    private Double amount;

    private CarReturnEntity carReturnEntity;
}
