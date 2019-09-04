package com.example.CarRental.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CarRentalResponseBody {
    Double amountToPay;
    Double amountToReturn;
}
