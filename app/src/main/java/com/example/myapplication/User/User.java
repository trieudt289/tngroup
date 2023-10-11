package com.example.myapplication.User;

public class User {
    private String name;
    private String gmail;
    private Boolean Gender;

    public User(String name, String gmail, Boolean gender) {
        this.name = name;
        this.gmail = gmail;
        Gender = gender;
    }

    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public Boolean getGender() {
        return Gender;
    }

    public void setGender(Boolean gender) {
        Gender = gender;
    }
}
