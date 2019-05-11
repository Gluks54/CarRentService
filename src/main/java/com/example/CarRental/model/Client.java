package com.example.CarRental.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
@Builder
@Data
public class Client {

    private String name;

    private String surname;

//            @Email
    private  String email;

    private String address;
}
