package com.example.CarRental.loadToDB;


import com.example.CarRental.domain.model.CarEntity;
import com.example.CarRental.domain.model.CarRentalEntity;
import com.example.CarRental.domain.model.CarReturnEntity;
import com.example.CarRental.domain.model.ClientEntity;
import com.example.CarRental.domain.repository.CarRentalRepository;
import com.example.CarRental.domain.repository.CarRepository;
import com.example.CarRental.domain.repository.CarReturnRepository;
import com.example.CarRental.domain.repository.ClientRepository;

import com.example.CarRental.model.CarStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;

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
    public void loadDataToDb() {

        CarEntity carEntity1 = CarEntity
                .builder()
                .carBodyType("Sedan")
                .model("Volvo S60")
                .carStatus(CarStatus.AVAILABLE)
                .amount(Double.valueOf(20.0))
                .releaseYear(Integer.valueOf(2019))
                .URL("https://postmediadriving.files.wordpress.com/2018/05/chrome-image-394131.png?w=800&h=520&crop=1")
                .build();

//        CarRentalEntity carRentalEntity = CarRentalEntity
//                .builder()
//                .amount(Double.valueOf(40.0))
//                .startDate(new Date(120592))
//                .endDate(new Date(100592))
////                .carReturnEntity(carReturnEntity1)
//                .rentDate(new Date(140303))
////                .clientEntity_id(clientEntity1)
//
//                .build();

//        carRepository.save(carEntity1);}}

        CarEntity carEntity2 = CarEntity
                .builder()
                .carBodyType("Hatchback")
                .model("Mercedes-Benz")
                .carStatus(CarStatus.AVAILABLE)
                .amount(Double.valueOf(25.0))
                .releaseYear(Integer.valueOf(2018))
                .URL("https://c4d709dd302a2586107d-f8305d22c3db1fdd6f8607b49e47a10c.ssl.cf1.rackcdn.com/thumbnails/stock-images/ce6a700ba071f03056a4a02fe09e6f96.png")
                .build();

        CarEntity carEntity3 = CarEntity
                .builder()
                .carBodyType("Crossover")
                .model("Land Rover")
                .carStatus(CarStatus.AVAILABLE)
                .amount(Double.valueOf(24.0))
                .releaseYear(Integer.valueOf(2017))
                .URL("https://st.motortrend.ca/uploads/sites/10/2017/11/2017-land-rover-discovery-sport-hse-lux-suv-angular-front.png")
                .build();

        CarEntity carEntity4 = CarEntity
                .builder()
                .carBodyType("Sedan")
                .model("Suzuki Kizashi")
                .carStatus(CarStatus.AVAILABLE)
                .amount(Double.valueOf(50.0))
                .releaseYear(Integer.valueOf(2019))
                .URL("https://www.cstatic-images.com/car-pictures/xl/usc30szc101c021001.png")
                .build();

        CarEntity carEntity5 = CarEntity
                .builder()
                .carBodyType("Crossover")
                .model("Audi Q5")
                .carStatus(CarStatus.AVAILABLE)
                .amount(Double.valueOf(32.0))
                .releaseYear(Integer.valueOf(2016))
                .URL("https://c4d709dd302a2586107d-f8305d22c3db1fdd6f8607b49e47a10c.ssl.cf1.rackcdn.com/thumbnails/stock-images/febe338086b4304a06da5dbbe85d93e9.png")
                .build();

        CarEntity carEntity6 = CarEntity
                .builder()
                .carBodyType("Sedan")
                .model("Mitsubishi Lancer")
                .carStatus(CarStatus.AVAILABLE)
                .amount(Double.valueOf(32.0))
                .releaseYear(Integer.valueOf(2018))
                .URL("https://st.motortrend.com/uploads/sites/10/2015/11/2012-mitsubishi-lancer-evolution-mr-sedan-angular-front.png")
                .build();

        CarEntity carEntity7 = CarEntity
                .builder()
                .carBodyType("Sedan")
                .model("Toyota Corolla")
                .carStatus(CarStatus.AVAILABLE)
                .amount(Double.valueOf(32.0))
                .releaseYear(Integer.valueOf(2018))
                .URL("https://www.cstatic-images.com/car-pictures/xl/usc70toc041g021001.png")
                .build();

        CarEntity carEntity8 = CarEntity
                .builder()
                .carBodyType("Convertible")
                .model("Mazda Miata")
                .carStatus(CarStatus.AVAILABLE)
                .amount(Double.valueOf(32.0))
                .releaseYear(Integer.valueOf(2011))
                .URL("https://st.motortrend.com/uploads/sites/10/2015/11/2011-mazda-mx5-miata-gt-hard-top-convertible-angular-front.png")
                .build();

        CarEntity carEntity9 = CarEntity
                .builder()
                .carBodyType("Sedan")
                .model("Honda Accord")
                .carStatus(CarStatus.AVAILABLE)
                .amount(Double.valueOf(32.0))
                .releaseYear(Integer.valueOf(2019))
                .URL("https://images.honda.ca/models/H/Models/2019/accord_sedan/lx_10560_crystal_black_pearl_front.png?width=1000")
                .build();

        CarEntity carEntity10 = CarEntity
                .builder()
                .carBodyType("Crossover")
                .model("Nissan Juke")
                .carStatus(CarStatus.AVAILABLE)
                .amount(Double.valueOf(32.0))
                .releaseYear(Integer.valueOf(2017))
                .URL("https://st.motortrend.com/uploads/sites/10/2015/11/2014-nissan-juke-sv-fwd-cvt-suv-angular-front.png")
                .build();

   //     carRepository.save(carEntity1);
        carRepository.save(carEntity2);
        carRepository.save(carEntity3);
        carRepository.save(carEntity4);
        carRepository.save(carEntity5);
        carRepository.save(carEntity6);
        carRepository.save(carEntity7);
        carRepository.save(carEntity8);
        carRepository.save(carEntity9);
        carRepository.save(carEntity10);

        ClientEntity clientEntity1 = ClientEntity
                .builder()
                .name("Marian")
                .surname("Worzniak")
                .address("Komorowska 4")
                .email("mariam@gmail.com")
                .build();

        ClientEntity clientEntity2 = ClientEntity
                .builder()
                .name("Edek")
                .surname("Grocholski")
                .address("brzozowa 12")
                .email("Edek@gmail.com")
                .build();


        ClientEntity clientEntity3 = ClientEntity
                .builder()
                .name("Janek")
                .surname("Dzubel")
                .address("Lesna 15")
                .email("Janek@gmail.com")
                .build();


//        clientRepository.save(clientEntity1);
        clientRepository.save(clientEntity2);
        clientRepository.save(clientEntity3);


        LocalDate date1 = LocalDate.of(2001, 3, 22);
        LocalDate date2 = LocalDate.of(2011, 10, 15);

        CarReturnEntity carReturnEntity1 = CarReturnEntity
                .builder()
                .surcharge(39.0)
                .comments("it was good car")
                .return_date(date1)
                .build();


        CarReturnEntity carReturnEntity2 = CarReturnEntity
                .builder()
                .surcharge(20.0)
                .comments("it was bad car")
                .return_date(date2)
                .build();


        LocalDate startDate = LocalDate.of(1992, 12, 22);
        LocalDate endDate = LocalDate.of(1993, 10, 22);
        LocalDate rentDate = LocalDate.of(1994, 9, 15);


        CarRentalEntity carRentalEntity = CarRentalEntity
                .builder()
                .amount(Double.valueOf(40.0))
                .startDate(startDate)
                .endDate(endDate)
                .carReturnEntity(carReturnEntity1)
                .rentDate(rentDate)
                .clientEntity_id(clientEntity1)
                .carEntity_id(carEntity1)
                .build();

        carRentalRepository.save(carRentalEntity);
    }
}

