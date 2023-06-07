package com.fatweb.allergysafenz.DataObject;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;


/**
 * Created by Asus-Amad-PC on 11/14/2017.
 */

public class Restaurant implements Serializable {

    @SerializedName("id")
    private String id;

    public String getRestaurantId() {
        return restaurant_id;
    }

    public void setRestaurantId(String restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    @SerializedName("restaurant_id")
    private String restaurant_id;
    @SerializedName("place_id")
    private String placeId;
    @SerializedName("address")
    private String address;
    @SerializedName("company")
    private String company;
    @SerializedName("latitude")
    private String latitude;
    @SerializedName("longitude")
    private String longitude;
    @SerializedName("phone")
    private String phone;
    @SerializedName("avatar")
    private String logo;
    @SerializedName("restaurant_name")
    private String restoName;
    @SerializedName("first_name")
    private String firstName;
    @SerializedName("last_name")
    private String lastName;
    @SerializedName("number_of_units")
    private String numOfUnits;
    @SerializedName("position")
    private String position;
    @SerializedName("rating")
    private String rating;
    @SerializedName("types")
    private String types;
    @SerializedName("name")
    private String name;
    @SerializedName("category_name")
    private String categoryName;
    @SerializedName("resto_allergens")
    public List<Allergy> restoAllergens;

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    @SerializedName("url")
    private String website;
    @SerializedName("logo")
    private String rest_image;

    public String getRest_image() {
        return rest_image;
    }

    public void setRest_image(String rest_image) {
        this.rest_image = rest_image;
    }

    public String getContact_num() {
        return contact_num;
    }

    public void setContact_num(String contact_num) {
        this.contact_num = contact_num;
    }

    @SerializedName("contact_number")
    private String contact_num;

    @SerializedName("average_rating")
    public String average_rating;
    @SerializedName("over_all_ratings")
    public String over_all_ratings;
    @SerializedName("scope")
    public String scope;
    public String ratings;

    public String getEmail_address() {
        return email_address;
    }

    public void setEmail_address(String email_address) {
        this.email_address = email_address;
    }

    @SerializedName("email_address")
    public String email_address;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNumOfUnits() {
        return numOfUnits;
    }

    public void setNumOfUnits(String numOfUnits) {
        this.numOfUnits = numOfUnits;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }


    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRestoName() {
        return restoName;
    }

    public void setRestoName(String restoName) {
        this.restoName = restoName;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }

}
