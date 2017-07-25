package com.entreprise.entities;

import java.util.List;

public class BeanWithList {

    private List<String> m_cities;
    private Integer m_id;

    public BeanWithList(List<String> cities, Integer id) {
        m_cities = cities;
        m_id = id;
    }

    public List<String> getCities() {
        return m_cities;
    }

    public Integer getId() {
        return m_id;
    }
}