package com.igorbochkor.controllers;

import com.igorbochkor.lib.Injector;
import com.igorbochkor.model.Manufacturer;
import com.igorbochkor.service.ManufacturerService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddManufacturerController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("com.igorbochkor");
    private ManufacturerService manufacturerService
            = (ManufacturerService) injector.getInstance(ManufacturerService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/manufacturer/add.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String name = req.getParameter("name");
        String country = req.getParameter("country");
        if (!name.isEmpty() && !country.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/");
            Manufacturer manufacturer = new Manufacturer(name, country);
            manufacturerService.create(manufacturer);
        } else {
            req.setAttribute("message", "Fill your correct data");
            req.getRequestDispatcher("/WEB-INF/views/manufacturer/add.jsp").forward(req, resp);
        }
    }
}
