package com.fatweb.allergysafenz.DataObject;

import com.google.gson.annotations.SerializedName;

/**
 * Created by aamad on 1/22/2018.
 */

public class UserRatingReview {


    @SerializedName("user_info")public UserInfo user_info;
    @SerializedName("ratings")public UserRating ratings;
    @SerializedName("reviews")public UserReview reviews;
    public User user;




}
