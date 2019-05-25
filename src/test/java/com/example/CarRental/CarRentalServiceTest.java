package com.example.CarRental;

import com.example.CarRental.domain.model.CarEntity;
import com.example.CarRental.domain.model.CarRentalEntity;
import com.example.CarRental.domain.model.CarReturnEntity;
import com.example.CarRental.domain.model.ClientEntity;
import com.example.CarRental.domain.repository.CarRentalRepository;
import com.example.CarRental.domain.repository.CarRepository;
import com.example.CarRental.domain.repository.CarReturnRepository;
import com.example.CarRental.domain.repository.ClientRepository;
import com.example.CarRental.model.CarRental;
import com.example.CarRental.model.CarStatus;
import com.example.CarRental.service.CarRentalService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.assertEquals;
import java.time.LocalDate;
import java.util.UUID;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

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
        public void addEntryTest(){

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
        public  void getCarRentalTest(){

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
                assertEquals(LocalDate.of(2001,3,22),carRentalFromRepository.getStartDate());
                assertEquals(LocalDate.of(2011,10,15),carRentalFromRepository.getEndDate());
                assertEquals(Double.valueOf(20),carRentalFromRepository.getAmount());
            }

           @Test
           public void updateReturnEntityTest(){
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

                   carReturnEntity = carRentalService.updateReturnEntry(carRental,"Nice car");

                   assertEquals("Nice car", carReturnEntity.getComments());
                   assertEquals(LocalDate.now(), carReturnEntity.getReturn_date());
           }
}