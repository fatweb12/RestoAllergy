package com.fatweb.allergysafenz.DataObject;

import com.google.gson.annotations.SerializedName;

/**
 * Created by aamad on 1/22/2018.
 */

public class UserRating {

    @SerializedName("date_rated")public String date_rated;
    @SerializedName("date_rated_utc")public String date_rated_utc;
    @SerializedName("rating_status")public String rating_status;
    @SerializedName("ratings")public String ratings;
    @SerializedName("rating_user_id")public String ratings1;

}
