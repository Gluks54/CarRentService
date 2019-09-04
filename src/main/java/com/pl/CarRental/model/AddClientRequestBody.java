package com.pl.CarRental.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AddClientRequestBody {
    private String name;

    private String surname;

    private String email;

    private String address;
}
