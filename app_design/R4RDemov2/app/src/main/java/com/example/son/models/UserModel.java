package com.example.son.models;

/**
 * Created by Son on 4/30/2016.
 */
public class UserModel {

    private int id;
    private String phone;
    private String email;
    private String createdDay;
    private String updatedDay;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUpdatedDay() {
        return updatedDay;
    }

    public void setUpdatedDay(String updatedDay) {
        this.updatedDay = updatedDay;
    }

    public String getCreatedDay() {
        return createdDay;
    }

    public void setCreatedDay(String createdDay) {
        this.createdDay = createdDay;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


}
