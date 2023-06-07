package com.fatweb.allergysafenz.DataObject;

import com.google.gson.annotations.SerializedName;

/**
 * Created by aamad on 1/22/2018.
 */

public class RestaurantInfo {


    @SerializedName("address")public String address;
    @SerializedName("google_ratings")public String google_ratings;
    @SerializedName("latitude")public String latitude;
    @SerializedName("longitude")public String longitude;
    @SerializedName("place_id")public String place_id;
    @SerializedName("restaurant_id")public String restaurant_id;
    @SerializedName("restaurant_name")public String restaurant_name;
    @SerializedName("resto_date_added")public String resto_date_added;
    @SerializedName("status")public String status;
    @SerializedName("rating_user_id") public String rating_user_id;




}
