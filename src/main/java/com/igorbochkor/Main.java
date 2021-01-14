package com.igorbochkor;

import com.igorbochkor.lib.Injector;
import com.igorbochkor.model.Manufacturer;
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
        System.out.println(manufacturerService.getAll());
        Manufacturer manufacturerVolvo = new Manufacturer("Volvo", "Sweden");
        manufacturerService.create(manufacturerVolvo);
        manufacturerVolvo.setCountry("Italy");
        manufacturerVolvo.setId(THIRD_INDEX);
        manufacturerService.update(manufacturerVolvo);
        manufacturerService.delete(SECOND_INDEX);
    }
}
