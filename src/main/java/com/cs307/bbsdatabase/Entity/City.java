package com.cs307.bbsdatabase.Entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
/*
City的id、城市、国家均为final
 */
public class City {
    @TableId(type = IdType.AUTO)
    private final Integer city_id;
    private final String city;
    private final String country;

    public City(Integer city_id, String city, String country) {
        this.city_id = city_id;
        this.city = city;
        this.country = country;
    }

    public Integer getCity_id() {
        return city_id;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }
}
