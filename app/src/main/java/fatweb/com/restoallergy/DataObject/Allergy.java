package com.fatweb.allergysafenz.DataObject;

import com.google.gson.annotations.SerializedName;


/**
 * Created by aamad on 1/22/2018.
 */

public class Allergy {


    @SerializedName("id") private String id;
    @SerializedName("category_name") private String categoryName;
    @SerializedName("allergy_id") private String categoryId;

    public String getRest_name() {
        return rest_name;
    }

    public void setRest_name(String rest_name) {
        this.rest_name = rest_name;
    }

    @SerializedName("name") private String rest_name;

    @SerializedName("image") private String allergyimg;
    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
    private String orderNum;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }




    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    boolean isChecked;


    public String getAllergyimg() {
        return allergyimg;
    }

    public void setAllergyimg(String allergyimg) {
        this.allergyimg = allergyimg;
    }
}
