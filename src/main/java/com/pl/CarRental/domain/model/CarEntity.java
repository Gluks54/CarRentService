package com.pl.CarRental.domain.model;

import com.pl.CarRental.model.CarStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

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

    private String model;

    private String carBodyType;

    private Integer releaseYear;

    @Enumerated(EnumType.STRING)
    private CarStatus carStatus;

    private Double amount;

    private String URL;

    @OneToMany(mappedBy = "carEntity_id", fetch = FetchType.LAZY)
    List<CarRentalEntity> carRentalEntities;
}
