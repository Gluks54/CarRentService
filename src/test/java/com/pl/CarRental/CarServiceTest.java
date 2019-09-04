package com.pl.CarRental;


import com.pl.CarRental.domain.model.CarEntity;
import com.pl.CarRental.domain.repository.CarRepository;
import com.pl.CarRental.model.Car;
import com.pl.CarRental.model.CarStatus;
import com.pl.CarRental.service.CarService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CarServiceTest {

    @Autowired
    CarRepository carRepository;

    @Autowired
    CarService carService;

    @Test
    public void getAvailableCarsByParameterTest() {
        CarEntity carEntity = CarEntity
                .builder()
                .carBodyType("Sedan")
                .model("Volvo S60")
                .carStatus(CarStatus.AVAILABLE)
                .amount(5d)
                .releaseYear(2018)
                .URL("https://postmediadriving.files.wordpress.com/2018/05/chrome-image-394131.png?w=800&h=520&crop=1")
                .build();
        carRepository.save(carEntity);

        List<CarEntity> availableCars = carRepository.getAvailableCarsByParameter(5d, null, null, null);
        int size = availableCars.size();

        List<CarEntity> expectedCar = new ArrayList<>();
        expectedCar.add(carEntity);

        assertEquals(1, size);
    }


    @Test
    public void getAllCarsTest() {
        List<Car> carsBefore = carService.getAllCars();

        List<Car> allCars = carService.getAllCars();
        int size = allCars.size();

        assertEquals((carsBefore.size() + 1), size);
    }
}
