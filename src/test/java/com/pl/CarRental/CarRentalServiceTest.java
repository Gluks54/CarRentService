package com.pl.CarRental;

import com.pl.CarRental.domain.model.CarEntity;
import com.pl.CarRental.domain.model.CarRentalEntity;
import com.pl.CarRental.domain.model.CarReturnEntity;
import com.pl.CarRental.domain.model.ClientEntity;
import com.pl.CarRental.domain.repository.CarRentalRepository;
import com.pl.CarRental.domain.repository.CarRepository;
import com.pl.CarRental.domain.repository.CarReturnRepository;
import com.pl.CarRental.domain.repository.ClientRepository;
import com.pl.CarRental.model.CarRental;
import com.pl.CarRental.model.CarStatus;
import com.pl.CarRental.model.ClientRental;
import com.pl.CarRental.service.CarRentalService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CarRentalServiceTest {
    @Autowired
    CarRentalService carRentalService;
    @Autowired
    ClientRepository clientRepository;

    @Autowired
    CarRepository carRepository;

    @Autowired
    CarRentalRepository carRentalRepository;

    @Autowired
    CarReturnRepository carReturnRepository;

    @Test
    public void addEntryTest() {

        CarEntity carEntity = CarEntity
                .builder()
                .carBodyType("Sedan")
                .model("Volvo S60")
                .carStatus(CarStatus.AVAILABLE)
                .amount(Double.valueOf(20.0))
                .releaseYear(Integer.valueOf(2019))
                .URL("https://postmediadriving.files.wordpress.com/2018/05/chrome-image-394131.png?w=800&h=520&crop=1")
                .build();

        ClientEntity clientEntity = ClientEntity
                .builder()
                .name("Marian")
                .surname("Worzniak")
                .address("Komorowska 4")
                .email("mariam@gmail.com")
                .build();

        LocalDate startDate = LocalDate.of(2001, 3, 22);
        LocalDate endDate = LocalDate.of(2011, 10, 15);

        UUID uuid = carRentalService.addEntry(
                startDate,
                endDate,
                clientEntity,
                carEntity,
                20d);

        assertNotNull(uuid);
    }

    @Test
    public void getCarRentalTest() {

        CarEntity carEntity = CarEntity
                .builder()
                .carBodyType("Sedan")
                .model("Volvo S60")
                .carStatus(CarStatus.AVAILABLE)
                .amount(Double.valueOf(20.0))
                .releaseYear(Integer.valueOf(2019))
                .URL("https://postmediadriving.files.wordpress.com/2018/05/chrome-image-394131.png?w=800&h=520&crop=1")
                .build();

        ClientEntity clientEntity = ClientEntity
                .builder()
                .name("Marian")
                .surname("Worzniak")
                .address("Komorowska 4")
                .email("mariam@gmail.com")
                .build();

        LocalDate startDate = LocalDate.of(2001, 3, 22);
        LocalDate endDate = LocalDate.of(2011, 10, 15);
        LocalDate date = LocalDate.of(2018, 7, 15);

        CarReturnEntity carReturnEntity = CarReturnEntity
                .builder()
                .return_date(date)
                .build();

        CarRentalEntity carRentalEntity = CarRentalEntity
                .builder()
                .amount(20d)
                .startDate(startDate)
                .endDate(endDate)
                .rentDate(LocalDate.now())
                .carEntity_id(carEntity)
                .clientEntity_id(clientEntity)
                .carReturnEntity(carReturnEntity)
                .build();
        UUID uuid = carRentalRepository.save(carRentalEntity).getId();

        CarRental carRentalFromRepository;
        carRentalFromRepository = carRentalService.getCarRental(uuid);

        assertNotNull(carRentalEntity);
        assertEquals(LocalDate.of(2001, 3, 22), carRentalFromRepository.getStartDate());
        assertEquals(LocalDate.of(2011, 10, 15), carRentalFromRepository.getEndDate());
        assertEquals(Double.valueOf(20), carRentalFromRepository.getAmount());
    }

    @Test
    public void updateReturnEntityTest() {
        CarEntity carEntity = CarEntity
                .builder()
                .carBodyType("Sedan")
                .model("Volvo S60")
                .carStatus(CarStatus.AVAILABLE)
                .amount(Double.valueOf(20.0))
                .releaseYear(Integer.valueOf(2019))
                .URL("https://postmediadriving.files.wordpress.com/2018/05/chrome-image-394131.png?w=800&h=520&crop=1")
                .build();

        ClientEntity clientEntity = ClientEntity
                .builder()
                .name("Marian")
                .surname("Worzniak")
                .address("Komorowska 4")
                .email("mariam@gmail.com")
                .build();

        LocalDate startDate = LocalDate.of(2001, 3, 22);
        LocalDate endDate = LocalDate.of(2011, 10, 15);
        LocalDate date = LocalDate.of(2018, 7, 15);

        CarReturnEntity carReturnEntity = CarReturnEntity
                .builder()
                .return_date(date)
                .build();

        CarRentalEntity carRentalEntity = CarRentalEntity
                .builder()
                .amount(20d)
                .startDate(startDate)
                .endDate(endDate)
                .rentDate(LocalDate.now())
                .carEntity_id(carEntity)
                .clientEntity_id(clientEntity)
                .carReturnEntity(carReturnEntity)
                .build();

        CarRental carRental = carRentalService.map(carRentalEntity);

        carReturnEntity = carRentalService.updateReturnEntry(carRental, "Nice car");

        assertEquals("Nice car", carReturnEntity.getComments());
        assertEquals(LocalDate.now(), carReturnEntity.getReturn_date());
    }

    @Test
    @Transactional
    public void getClientRentalsTest() {
        CarEntity carEntity = CarEntity
                .builder()
                .carBodyType("Sedan")
                .model("Volvo S60")
                .carStatus(CarStatus.AVAILABLE)
                .amount(20.0)
                .releaseYear(2019)
                .URL("https://postmediadriving.files.wordpress.com/2018/05/chrome-image-394131.png?w=800&h=520&crop=1")
                .build();
        carRepository.save(carEntity);

        ClientEntity clientEntity = ClientEntity
                .builder()
                .name("Anna")
                .surname("Kowalska")
                .address("Kwiatowa 13")
                .email("annakowalska@test.pl")
                .build();
        clientRepository.save(clientEntity);

        LocalDate startDate = LocalDate.of(2019, 5, 30);
        LocalDate endDate = LocalDate.of(2019, 6, 14);
        LocalDate date = LocalDate.of(2019, 6, 15);

        CarReturnEntity carReturnEntity = CarReturnEntity
                .builder()
                .return_date(date)
                .build();
        carReturnRepository.save(carReturnEntity);

        CarRentalEntity carRentalEntity = CarRentalEntity
                .builder()
                .amount(20d)
                .startDate(startDate)
                .endDate(endDate)
                .rentDate(LocalDate.now())
                .carEntity_id(carEntity)
                .clientEntity_id(clientEntity)
                .carReturnEntity(carReturnEntity)
                .build();

        ClientEntity clientEntityFromRepo = carRentalEntity.getClientEntity_id();
        UUID client_id = clientEntityFromRepo.getId();

        List<ClientRental> clientRentals = carRentalService.getClientRentals(client_id);
        int size = clientRentals.size();
        assertEquals(Integer.valueOf(1), Integer.valueOf(size));
    }
}
