package com.example.CarRental.model;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Builder
@Data
public class CarRentalRequestBody {

    private UUID clientId;

    private UUID carId;

    private Date startDate;

    private Date endDate;
}
