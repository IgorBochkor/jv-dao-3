package com.igorbochkor;

import com.igorbochkor.lib.Injector;
import com.igorbochkor.model.Manufacturer;
import com.igorbochkor.service.ManufacturerService;

public class Main {
    private static Injector injector = Injector.getInstance("com.igorbochkor");
    private static final Long FIRST_MANUFACTURER = 1L;
    private static final Long SECOND_MANUFACTURER = 2L;

    public static void main(String[] args) {
        ManufacturerService manufacturerService =
                (ManufacturerService) injector.getInstance(ManufacturerService.class);

        Manufacturer manufacturerVolvo = new Manufacturer("Volvo", "Sweden");
        Manufacturer manufacturerAudi = new Manufacturer("Audi", "Germany");
        manufacturerService.create(manufacturerVolvo);
        manufacturerService.create(manufacturerAudi);
        System.out.println(manufacturerService.getAll());
        System.out.println(manufacturerService.get(SECOND_MANUFACTURER));

        manufacturerVolvo.setCountry("Japan");
        manufacturerService.update(manufacturerVolvo);
        System.out.println(manufacturerService.get(FIRST_MANUFACTURER));

        manufacturerService.delete(FIRST_MANUFACTURER);
        System.out.println(manufacturerService.getAll());
    }
}
