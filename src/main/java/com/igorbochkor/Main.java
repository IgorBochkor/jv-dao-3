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

        Manufacturer manufacturerVolvo = new Manufacturer("Volvo", "Sweden");
        Manufacturer manufacturerAudi = new Manufacturer("Audi", "Germany");
        manufacturerService.create(manufacturerVolvo);
        manufacturerService.create(manufacturerAudi);
        System.out.println(manufacturerService.getAll());
        System.out.println(manufacturerService.get(SECOND_INDEX));

        manufacturerVolvo.setCountry("Japan");
        manufacturerService.update(manufacturerVolvo);
        System.out.println(manufacturerService.get(FIRST_INDEX));

        manufacturerService.delete(FIRST_INDEX);
        System.out.println(manufacturerService.getAll());

        DriverService driverService = (DriverService) injector.getInstance(DriverService.class);
        Driver driverOne = new Driver("Tom", "BC");
        Driver driverTwo = new Driver("Carl", "A");
        driverService.create(driverOne);
        driverService.create(driverTwo);
        System.out.println(driverService.getAll());
        System.out.println(driverService.get(FIRST_INDEX));
        driverService.delete(FIRST_INDEX);
        Driver driverThree = new Driver("Orest", "E");
        driverService.create(driverThree);
        System.out.println(driverService.getAll());

        driverTwo.setLicenceNumber("CD");
        driverService.update(driverTwo);
        System.out.println(driverService.get(SECOND_INDEX));

        CarService carService = (CarService) injector.getInstance(CarService.class);
        Car carOne = new Car("Tesla", manufacturerVolvo);
        Car carTwo = new Car("BMW", manufacturerAudi);
        carService.create(carOne);
        carService.create(carTwo);
        System.out.println(carService.getAll());
        System.out.println(carService.get(SECOND_INDEX));
        carService.delete(FIRST_INDEX);
        carTwo.setModel("Aston Martin");
        carService.update(carTwo);
        System.out.println(carService.getAll());
        carService.addDriverToCar(driverTwo, carTwo);
        carService.addDriverToCar(driverThree, carTwo);
        System.out.println(carService.getAll());
        carService.removeDriverFromCar(driverTwo, carTwo);
        System.out.println(carService.getAll());
        carService.create(carOne);
        Car carThree = new Car("Honda", manufacturerAudi);
        carService.create(carThree);
        carService.addDriverToCar(driverThree, carThree);
        System.out.println(carService.getAll());

        System.out.println(carService.getAllByDriver(THIRD_INDEX));
    }
}
