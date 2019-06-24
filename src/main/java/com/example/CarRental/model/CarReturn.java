package com.example.CarRental.model;

import lombok.Builder;
import lombok.Data;
import java.util.UUID;

@Builder
@Data
public class CarReturn {

    private double surcharge;

    UUID CarRental;
}
