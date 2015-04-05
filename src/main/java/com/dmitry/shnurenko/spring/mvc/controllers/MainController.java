package com.dmitry.shnurenko.spring.mvc.controllers;

import com.dmitry.shnurenko.spring.mvc.dao.employee.EmployeeDao;
import com.dmitry.shnurenko.spring.mvc.entity.employees.Employee;
import com.dmitry.shnurenko.spring.mvc.exceptions.DBException;
import com.dmitry.shnurenko.spring.mvc.inject.EntityFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Controller
@RequestMapping("/")
public class MainController {

    private final EmployeeDao   employeeDao;
    private final EntityFactory entityFactory;

    @Autowired
    public MainController(EmployeeDao employeeDao, EntityFactory entityFactory) {
        this.employeeDao = employeeDao;
        this.entityFactory = entityFactory;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String showMainPage() {
        return "index";
    }

    @RequestMapping(method = RequestMethod.GET, value = "confirmation")
    public String confirmRegistry() {
        return "confirmRegister";
    }

    @RequestMapping(value = "add/employee",
                    method = RequestMethod.POST,
                    produces = APPLICATION_JSON_VALUE)
    public @ResponseBody Employee addEmployee(@RequestParam("id") int id,
                                              @RequestParam("firstName") String firstName,
                                              @RequestParam("lastName") String lastName) {
        Employee employee = entityFactory.createManager(id, firstName, lastName);

        try {
            employeeDao.save(employee);
        } catch (DBException e) {
            e.printStackTrace();
        }

        return employee;
    }

    @RequestMapping(value = "get/all",
                    method = RequestMethod.GET,
                    produces = APPLICATION_JSON_VALUE)
    public @ResponseBody List<Employee> getAll() {
        List<Employee> employees = new ArrayList<>();
        try {
            employees.addAll(employeeDao.getAllEmployees());
        } catch (DBException e) {
            e.printStackTrace();
        }

        return employees;
    }

    @RequestMapping(value = "delete/employee",
                    method = RequestMethod.GET,
                    produces = APPLICATION_JSON_VALUE)
    public @ResponseBody Employee removeEmployee(@RequestParam("id") int id,
                                                 @RequestParam("firstName") String firstName,
                                                 @RequestParam("lastName") String lastName) {

        Employee employee = entityFactory.createManager(id, firstName, lastName);

        try {
            employeeDao.delete(employee);
        } catch (DBException e) {
            e.printStackTrace();
        }

        return employee;
    }

    @RequestMapping(value = "update/employee",
                    method = RequestMethod.GET,
                    produces = APPLICATION_JSON_VALUE)
    public @ResponseBody Employee updateEmployee(@RequestParam("id") int id,
                                                 @RequestParam("firstName") String firstName,
                                                 @RequestParam("lastName") String lastName) {

        Employee employee = entityFactory.createManager(id, firstName, lastName);

        try {
            employeeDao.update(employee);
        } catch (DBException e) {
            e.printStackTrace();
        }

        return employee;
    }
}