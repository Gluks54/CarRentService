package com.example.CarRental.model;

import com.example.CarRental.domain.model.CarEntity;
import com.example.CarRental.domain.model.ClientEntity;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
@Builder
@Data
public class CarRental {

    private Date rentDate;

    private ClientEntity clientEntity_id;

    private CarEntity carEntity_id;

    private Date startDate;

    private Date endDate;

    private Double amount;
}
