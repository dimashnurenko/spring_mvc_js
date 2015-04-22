package com.dmitry.shnurenko.spring.mvc.controllers;

import com.dmitry.shnurenko.spring.mvc.converter.ConverterFactory;
import com.dmitry.shnurenko.spring.mvc.converter.ObjectConverter;
import com.dmitry.shnurenko.spring.mvc.dao.moreinfo.AddressDao;
import com.dmitry.shnurenko.spring.mvc.entity.moreinfo.Address;
import com.dmitry.shnurenko.spring.mvc.exceptions.DBException;
import com.dmitry.shnurenko.spring.mvc.inject.EntityFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author Dmitry Shnurenko
 */
@Controller
@RequestMapping("/address")
public class AddressController {

    private final AddressDao               addressDao;
    private final EntityFactory            entityFactory;
    private final ObjectConverter<Address> converter;

    @Autowired
    public AddressController(AddressDao addressDao,
                             EntityFactory entityFactory,
                             ConverterFactory<Address> converterFactory) {
        this.addressDao = addressDao;
        this.entityFactory = entityFactory;
        this.converter = converterFactory.createConverter(entityFactory.createAddress());
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public void saveAddress(HttpServletRequest request) throws DBException, IllegalAccessException {
        String employeeId = request.getParameter("employeeId");
        Address address = converter.getObjectFrom(request);

        addressDao.save(Integer.valueOf(employeeId), address);
    }

    @RequestMapping(value = "/get",
                    method = RequestMethod.GET,
                    produces = APPLICATION_JSON_VALUE)
    public @ResponseBody Address getAddress(@RequestParam("employeeId") int employeeId) throws DBException {
        Address address = addressDao.get(employeeId);

        if (address == null) {
            return entityFactory.createAddress("", "", "", 0, 0);
        }

        return address;
    }
}
