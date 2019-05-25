package com.example.CarRental.domain.model;

import com.example.CarRental.model.RentalStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.time.LocalDate;
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

    @Column(name="return_date")
    LocalDate return_date;

    private String comments;

    private Double surcharge;

    @OneToOne(mappedBy = "carReturnEntity",fetch = FetchType.LAZY)
    private CarRentalEntity carRentalEntity;

    @Enumerated(EnumType.STRING)
    private RentalStatus status;
}
