package com.pl.CarRental.model;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public class CheckPaymentRequestBody {
    private UUID carRentalId;

    private Double amountFromClient;
}
