package com.igorbochkor.controllers;

import com.igorbochkor.lib.Injector;
import com.igorbochkor.model.Car;
import com.igorbochkor.model.Manufacturer;
import com.igorbochkor.service.CarService;
import com.igorbochkor.service.ManufacturerService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddCarController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("com.igorbochkor");
    private CarService carService
            = (CarService) injector.getInstance(CarService.class);
    private ManufacturerService manufacturerService
            = (ManufacturerService) injector.getInstance(ManufacturerService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/car/add.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String model = req.getParameter("model");
        Long manufacturerId = Long.valueOf(req.getParameter("manufacturerId"));
        if (!model.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/");
            Manufacturer manufacturer = manufacturerService.get(manufacturerId);
            Car car = new Car(model, manufacturer);
            carService.create(car);
        } else {
            req.setAttribute("message", "Fill your correct data");
            req.getRequestDispatcher("/WEB-INF/views/car/add.jsp").forward(req, resp);
        }
    }
}
