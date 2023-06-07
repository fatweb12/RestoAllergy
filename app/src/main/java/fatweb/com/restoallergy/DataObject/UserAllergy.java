package com.fatweb.allergysafenz.DataObject;

import com.google.gson.annotations.SerializedName;

/**
 * Created by aamad on 1/22/2018.
 */

public class UserAllergy {



    @SerializedName("user_id")private String userId;
    @SerializedName("allergy_id")private String allergyId;
    @SerializedName("allergy")private Allergy allergy;

    public Allergy getAllergy() {
        return allergy;
    }

    public void setAllergy(Allergy allergy) {
        this.allergy = allergy;
    }





    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAllergyId() {
        return allergyId;
    }

    public void setAllergyId(String allergyId) {
        this.allergyId = allergyId;
    }




}
