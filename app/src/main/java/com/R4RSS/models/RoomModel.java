package com.R4RSS.models;

/**
 * Created by Son on 4/14/2016.
 */
public class RoomModel {

    private int id;
    private int userId;
    private int price;
    private double area;
    private String description;
    private String image_album_url;
    private double latitude;
    private double longtitude;
    private String district;
    private String street;
    private String ward;
    private String city;
    private String created_day;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getCreated_day() {
        return created_day;
    }

    public void setCreated_day(String created_day) {
        this.created_day = created_day;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage_album_url() {
        return image_album_url;
    }

    public void setImage_album_url(String image_album_url) {
        this.image_album_url = image_album_url;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(double longtitude) {
        this.longtitude = longtitude;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
