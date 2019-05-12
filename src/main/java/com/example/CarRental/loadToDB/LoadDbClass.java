package com.example.CarRental.loadToDB;


import com.example.CarRental.domain.model.CarEntity;
import com.example.CarRental.domain.model.CarRentalEntity;
import com.example.CarRental.domain.model.ClientEntity;
import com.example.CarRental.domain.repository.CarRentalRepository;
import com.example.CarRental.domain.repository.CarRepository;
import com.example.CarRental.domain.repository.CarReturnRepository;
import com.example.CarRental.domain.repository.ClientRepository;
import com.example.CarRental.model.CarRental;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class LoadDbClass {

    @Autowired
    CarRentalRepository carRentalRepository;

    @Autowired
    CarRepository carRepository;

    @Autowired
    CarReturnRepository carReturnRepository;

    @Autowired
    ClientRepository clientRepository;

    @PostConstruct
    public void loadDataToDb(){

    }
}
