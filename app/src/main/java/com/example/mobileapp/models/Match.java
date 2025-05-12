package com.example.mobileapp.models;

import java.util.List;

public class Match {
    public String id;
    public String name;
    public String matchType;
    public String status;
    public String venue;
    public String dateTimeGMT;
    public List<String> teams;
    public List<TeamInfo> teamInfo;

    public static class TeamInfo {
        public String name;
        public String shortname;
        public String img;
    }
}
