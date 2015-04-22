package com.dmitry.shnurenko.spring.mvc.controllers;

import com.dmitry.shnurenko.spring.mvc.controllers.register.UserLoginManager;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Controller
@RequestMapping("/")
public class MainController {

    private final EmployeeDao      employeeDao;
    private final EntityFactory    entityFactory;
    private final UserLoginManager userLoginManager;

    @Autowired
    public MainController(EmployeeDao employeeDao,
                          EntityFactory entityFactory,
                          UserLoginManager userLoginManager) {
        this.employeeDao = employeeDao;
        this.entityFactory = entityFactory;
        this.userLoginManager = userLoginManager;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String showMainPage(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (!session.isNew()) {
            String id = (String) session.getAttribute("id");
            return userLoginManager.isContainId(id) ? "index" : "unregister";
        }

        return "unregister";

    }

    @RequestMapping(method = RequestMethod.GET, value = "info")
    public String getMoreInfo() {
        return "moreInfo";
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
                                              @RequestParam("lastName") String lastName) throws DBException {
        Employee employee = entityFactory.createManager(id, firstName, lastName);

        employeeDao.save(employee);

        return employee;
    }

    @RequestMapping(value = "get/all",
                    method = RequestMethod.GET,
                    produces = APPLICATION_JSON_VALUE)
    public @ResponseBody List<Employee> getAll() throws DBException {
        List<Employee> employees = new ArrayList<>();

        employees.addAll(employeeDao.getAllEmployees());

        return employees;
    }

    @RequestMapping(value = "delete/employee",
                    method = RequestMethod.GET,
                    produces = APPLICATION_JSON_VALUE)
    public @ResponseBody Employee removeEmployee(@RequestParam("id") int id,
                                                 @RequestParam("firstName") String firstName,
                                                 @RequestParam("lastName") String lastName) throws DBException {

        Employee employee = entityFactory.createManager(id, firstName, lastName);

        employeeDao.delete(employee);

        return employee;
    }

    @RequestMapping(value = "update/employee",
                    method = RequestMethod.GET,
                    produces = APPLICATION_JSON_VALUE)
    public @ResponseBody Employee updateEmployee(@RequestParam("id") int id,
                                                 @RequestParam("firstName") String firstName,
                                                 @RequestParam("lastName") String lastName) throws DBException {

        Employee employee = entityFactory.createManager(id, firstName, lastName);

        employeeDao.update(employee);

        return employee;
    }
}