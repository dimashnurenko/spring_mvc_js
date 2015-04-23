package com.dmitry.shnurenko.spring.mvc.controllers;

import com.dmitry.shnurenko.spring.mvc.dao.moreinfo.AddressDao;
import com.dmitry.shnurenko.spring.mvc.entity.moreinfo.Address;
import com.dmitry.shnurenko.spring.mvc.exceptions.DBException;
import com.dmitry.shnurenko.spring.mvc.inject.EntityFactory;
import com.dmitry.shnurenko.spring.mvc.util.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.Map;

import static java.lang.Integer.parseInt;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author Dmitry Shnurenko
 */
@Controller
@RequestMapping("/address")
public class AddressController {

    private final AddressDao    addressDao;
    private final EntityFactory entityFactory;

    @Autowired
    public AddressController(AddressDao addressDao, EntityFactory entityFactory) {
        this.addressDao = addressDao;
        this.entityFactory = entityFactory;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public void saveAddress(HttpServletRequest request) throws DBException, SQLException {
        Map<String, String> parameters = RequestUtil.getParameterMap(request);

        int employeeId = parseInt(parameters.get("employeeId"));

        String county = parameters.get("country");
        String city = parameters.get("city");
        String street = parameters.get("street");
        String house = parameters.get("house");
        String flat = parameters.get("flat");

        boolean isHouseOrFlatEmpty = house.isEmpty() || flat.isEmpty();

        Address address = entityFactory.createAddress(county,
                                                      city,
                                                      street,
                                                      isHouseOrFlatEmpty ? 0 : parseInt(house),
                                                      isHouseOrFlatEmpty ? 0 : parseInt(flat));

        addressDao.saveOrUpdate(employeeId, address);
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
