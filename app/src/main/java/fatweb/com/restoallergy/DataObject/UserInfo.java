package com.fatweb.allergysafenz.DataObject;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aamad on 1/22/2018.
 */

public class UserInfo {


    @SerializedName("email")public String email;
    @SerializedName("name")public String name;
    @SerializedName("profile_pic")public String profile_pic;
    @SerializedName("user_id")public String user_id;
    @SerializedName("about_bio")public String aboutBio;
    @SerializedName("allergens")public List<String> allergens = new ArrayList<>();
    @SerializedName("rating_user_id")public String ratinguserid;

}
