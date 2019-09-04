package com.pl.CarRental.model;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public class CarReturnRequestBody {
    private UUID carRentalId;

    private UUID clientId;

    private String comments;
}
