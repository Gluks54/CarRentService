package com.pl.CarRental.model;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public class Client {
    private UUID id;

    private String name;

    private String surname;

    private String email;

    private String address;
}
