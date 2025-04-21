package com.example.mobileapp.models;

public class Player {
    private String name;
    private String country;

    public Player(String name, String country){
        this.name = name;
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }
}
