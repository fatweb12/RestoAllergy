package com.fatweb.allergysafenz.DataObject;

import com.google.gson.annotations.SerializedName;

/**
 * Created by aamad on 1/22/2018.
 */

public class Rating {



    @SerializedName("user_id")private String userId;
    @SerializedName("restaurant_id")private String restaurantId;
    @SerializedName("ratings")private String rating;
    @SerializedName("date_added")private String dateAdded;
    @SerializedName("restaurant")private Restaurant restaurant;
    @SerializedName("user")private  User user;
    @SerializedName("date_rated")private String dateRated;

    @SerializedName("rating_user_id") public String rating_user_id;
    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }




    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }



}
