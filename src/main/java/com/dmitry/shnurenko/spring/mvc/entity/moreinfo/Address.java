package com.dmitry.shnurenko.spring.mvc.entity.moreinfo;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author Dmitry Shnurenko
 */
public class Address {

    private String country;
    private String city;
    private String street;
    private int    house;
    private int    flat;

    public Address() {
    }

    public Address(@Nonnull String country,
                   @Nonnull String city,
                   @Nullable String street,
                   @Nonnegative int house,
                   @Nonnegative int flat) {
        this.country = country;
        this.city = city;
        this.street = street;
        this.house = house;
        this.flat = flat;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public int getHouse() {
        return house;
    }

    public int getFlat() {
        return flat;
    }
}
