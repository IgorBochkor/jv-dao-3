package com.igorbochkor;

import com.igorbochkor.lib.Injector;
import com.igorbochkor.model.Car;
import com.igorbochkor.model.Driver;
import com.igorbochkor.model.Manufacturer;
import com.igorbochkor.service.CarService;
import com.igorbochkor.service.DriverService;
import com.igorbochkor.service.ManufacturerService;

public class Main {
    private static Injector injector = Injector.getInstance("com.igorbochkor");
    private static final Long FIRST_INDEX = 1L;
    private static final Long SECOND_INDEX = 2L;
    private static final Long THIRD_INDEX = 3L;

    public static void main(String[] args) {
        ManufacturerService manufacturerService =
                (ManufacturerService) injector.getInstance(ManufacturerService.class);
        System.out.println(manufacturerService.get(FIRST_INDEX));
        final Manufacturer manufacturerTesla = manufacturerService.get(FIRST_INDEX);
        System.out.println(manufacturerService.getAll());
        Manufacturer manufacturerVolvo = new Manufacturer("Volvo", "Sweden");
        manufacturerService.create(manufacturerVolvo);
        manufacturerVolvo.setCountry("Italy");
        manufacturerVolvo.setId(THIRD_INDEX);
        manufacturerService.update(manufacturerVolvo);
        manufacturerService.delete(SECOND_INDEX);

        DriverService driverService = (DriverService) injector.getInstance(DriverService.class);
        Driver driverTwo = new Driver("Bob", "CE");
        Driver driverThird = new Driver("Alice", "ABC");
        driverService.create(driverThird);
        System.out.println(driverService.get(SECOND_INDEX));
        driverTwo.setId(SECOND_INDEX);
        driverTwo.setName("Albert");
        driverTwo.setLicenceNumber("B");
        driverService.update(driverTwo);
        driverService.delete(FIRST_INDEX);
        System.out.println(driverService.getAll());

        CarService carService = (CarService) injector.getInstance(CarService.class);
        Car carOne = new Car("Model S", manufacturerTesla);
        Car carTwo = new Car("Volvo xc90", manufacturerVolvo);
        Car carThree = new Car("V50", manufacturerVolvo);
        carService.create(carOne);
        carTwo.setId(SECOND_INDEX);
        Car car = carService.create(carThree);
        carTwo.setManufacturer(manufacturerVolvo);
        carService.update(carTwo);
        carService.delete(car.getId());
        System.out.println(carService.get(FIRST_INDEX));
        carTwo.setManufacturer(manufacturerTesla);
        System.out.println(carService.update(carTwo));
        carService.addDriverToCar(driverTwo, carTwo);
        System.out.println(carService.getAll());
        System.out.println(carService.getAllByDriver(SECOND_INDEX));
    }
}
