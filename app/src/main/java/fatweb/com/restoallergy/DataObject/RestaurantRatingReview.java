package com.fatweb.allergysafenz.DataObject;

import com.google.gson.annotations.SerializedName;

/**
 * Created by aamad on 1/22/2018.
 */

public class RestaurantRatingReview {


    @SerializedName("restaurant_info")public RestaurantInfo restaurant_info;
    @SerializedName("ratings")public RestaurantRating ratings;

    public RestaurantRating getRatings() {
        return ratings;
    }

    public void setRatings(RestaurantRating ratings) {
        this.ratings = ratings;
    }

    @SerializedName("reviews")public RestaurantReview reviews;
    @SerializedName("rating_user_id") public String rating_user_id;
    public User user;




}
