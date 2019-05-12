package com.example.CarRental.domain.model;

import com.example.CarRental.model.CarStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CarEntity {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id")
    private UUID id;

    private  String model;

    private  String carBodyType;

    private Integer releaseYear;

    @Enumerated(EnumType.STRING)
    private CarStatus carStatus;

    private Double amount;

    @OneToMany(mappedBy = "carEntity_id",fetch = FetchType.EAGER)
    List<CarRentalEntity> carRentalEntities;
}
