package com.igorbochkor;

import com.igorbochkor.lib.Injector;

public class Main {
    private static Injector injector = Injector.getInstance("com.igorbochkor");
    private static final Long FIRST_INDEX = 1L;
    private static final Long SECOND_INDEX = 2L;
    private static final Long THIRD_INDEX = 3L;

    public static void main(String[] args) {
        /*ManufacturerService manufacturerService =
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
        final Driver driverTwo = new Driver("Bob", "CE");
        Driver driverThird = new Driver("Alice", "ABC");
        Driver driverCreate = driverService.create(driverThird);
        System.out.println(driverService.get(driverCreate.getId()));
        driverCreate.setName("Albert");
        driverCreate.setLicenceNumber("B");
        driverService.update(driverCreate);
        driverService.delete(driverCreate.getId());
        System.out.println(driverService.getAll());

        CarService carService = (CarService) injector.getInstance(CarService.class);
        Car carOne = new Car("Model S", manufacturerTesla);
        final Car carTwo = new Car("Volvo xc90", manufacturerVolvo);
        Car carThree = new Car("V50", manufacturerVolvo);
        carService.create(carOne);
        Car car = carService.create(carThree);
        car.setManufacturer(manufacturerVolvo);
        carService.update(car);
        System.out.println(carService.get(car.getId()));
        carTwo.setManufacturer(manufacturerTesla);
        System.out.println(carService.update(carTwo));
        carService.addDriverToCar(driverTwo, carTwo);
        System.out.println(carService.getAll());
        System.out.println(carService.getAllByDriver(driverTwo.getId()));
        carService.delete(car.getId());*/
    }
}
