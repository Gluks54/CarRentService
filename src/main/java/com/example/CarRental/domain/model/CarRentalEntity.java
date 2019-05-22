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
public class CarRentalEntity {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

//    @Temporal(value= TemporalType.DATE)
    @Column(name="rentDate")
    private LocalDate rentDate;



    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "clientEntity_id")
    private ClientEntity clientEntity_id;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "car_id")
    CarEntity carEntity_id;

//    @Temporal(value= TemporalType.DATE)
    @Column(name="start_date")
    LocalDate startDate;


//    @Temporal(value= TemporalType.DATE)
    @Column(name="end_date")
    LocalDate endDate;

    Double amount;

    @OneToOne(cascade = CascadeType.ALL,optional = false,fetch = FetchType.EAGER)
    @JoinColumn(name = "returnCar_id", referencedColumnName = "id")
    CarReturnEntity carReturnEntity;
}
