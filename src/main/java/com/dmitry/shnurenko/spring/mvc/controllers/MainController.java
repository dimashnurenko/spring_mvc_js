package com.dmitry.shnurenko.spring.mvc.controllers;

import com.dmitry.shnurenko.spring.mvc.dao.EmployeeDao;
import com.dmitry.shnurenko.spring.mvc.entity.Employee;
import com.dmitry.shnurenko.spring.mvc.inject.EntityFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping("/")
public class MainController {

    private final EmployeeDao employeeDao;
    private final EntityFactory entityFactory;

    @Autowired
    public MainController(EmployeeDao employeeDao, EntityFactory entityFactory) {
        this.employeeDao = employeeDao;
        this.entityFactory = entityFactory;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String printWelcome() {
        return "index";
    }

    @RequestMapping(value = "add/employee",
                    method = RequestMethod.POST,
                    produces = "application/json")
    public @ResponseBody Employee addEmployee(@RequestParam("id") int id,
                                              @RequestParam("name") String name) throws SQLException {
        Employee employee = entityFactory.createEmployee(id, name);

        boolean isSaved = employeeDao.save(employee);

        if (isSaved) {
            return employee;
        } else {
            throw new SQLException("Can't save employee to database...");
        }
    }

    @RequestMapping(value = "get/all",
                    method = RequestMethod.GET,
                    produces = "application/json")
    public @ResponseBody List<Employee> getAll() {
        return employeeDao.getAllEmployees();
    }

    @RequestMapping(value = "delete/employee",
                    method = RequestMethod.GET,
                    produces = "application/json")
    public @ResponseBody Employee removeEmployee(@RequestParam("id") int id,
                                                 @RequestParam("name") String name) throws SQLException {

        Employee employee = entityFactory.createEmployee(id, name);

        return employeeDao.delete(employee);
    }

    @RequestMapping(value = "update/employee",
                    method = RequestMethod.GET,
                    produces = "application/json")
    public @ResponseBody Employee updateEmployee(@RequestParam("id") int id,
                                                 @RequestParam("name") String name) throws SQLException {

        return employeeDao.update(entityFactory.createEmployee(id, name));
    }
}