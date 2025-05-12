package com.example.mobileapp.models;

public class Player {
    private String id;
    private String name;
    private String country;

    public Player(String name, String country){
        this.id = id;
        this.name = name;
        this.country = country;
    }
    public String getId(){
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }
}
