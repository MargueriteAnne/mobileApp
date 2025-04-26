package com.example.mobileapp.models;

import java.util.List;

public class PlayerResponse {
    private String status;
    private List<Player> data;

    public String getStatus(){
        return status;
    }
    public List<Player> getData(){
        return data;
    }
}
