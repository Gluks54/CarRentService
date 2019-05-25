package com.example.CarRental.model;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public class SettleSubchargeRequestBody {

    private UUID clientId;

    private UUID carRentalId;
}
