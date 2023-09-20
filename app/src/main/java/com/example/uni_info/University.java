package com.example.uni_info;

import java.util.List;

public class University {
    private String name;
    private String country;
    private List<String> web_pages;

    public University(String name, String country, List<String> web_pages) {
        this.name = name;
        this.country = country;
        this.web_pages = web_pages;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<String> getWebPages() {
        return web_pages;
    }

    public void setWebPages(List<String> webPages) {
        this.web_pages = webPages;
    }
}
