package com.example.CarRental.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CarReturnEntity {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

//    @Temporal(value= TemporalType.DATE)
    @Column(name="return_date")
    LocalDate return_date;


    private String comments;

    private Double surcharge;

    @OneToOne(mappedBy = "carReturnEntity",fetch = FetchType.LAZY)
    private CarRentalEntity carRentalEntity;

}
