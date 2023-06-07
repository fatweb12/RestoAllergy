package com.fatweb.allergysafenz.DataObject;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by aamad on 1/22/2018.
 */

public class RestaurantReview {

    @SerializedName("date_review")public String date_review;
    @SerializedName("date_review_utc")public String date_review_utc;
    @SerializedName("review_status")public String review_status;
    @SerializedName("reviews")public String reviews;
    @SerializedName("rating_user_id")public String reviews1;
    @SerializedName("allergens")public List<String> allergens;
    @SerializedName("meal_pictures")public List<String> meal_pictures;
    @SerializedName("review_answers")public List<String> review_answers;



}
