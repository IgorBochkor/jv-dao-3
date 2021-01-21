package com.igorbochkor.controllers;

import com.igorbochkor.lib.Injector;
import com.igorbochkor.model.Car;
import com.igorbochkor.model.Driver;
import com.igorbochkor.service.CarService;
import com.igorbochkor.service.DriverService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddDriverToCarController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("com.igorbochkor");
    private CarService carService
            = (CarService) injector.getInstance(CarService.class);
    private DriverService driverService
            = (DriverService) injector.getInstance(DriverService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/car/addDriver.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String driverIdString = req.getParameter("driverId");
        String carIdString = req.getParameter("carId");
        if (driverIdString.matches("\\d+") && carIdString.matches("\\d+")) {
            Long driverId = Long.valueOf(driverIdString);
            Long carId = Long.valueOf(carIdString);
            Car car = carService.get(carId);
            Driver driver = driverService.get(driverId);
            carService.addDriverToCar(driver, car);
            resp.sendRedirect(req.getContextPath() + "/");
        } else {
            req.setAttribute("message", "Fill your correct data");
            req.getRequestDispatcher("/WEB-INF/views/car/addDriver.jsp").forward(req, resp);
        }
    }
}
