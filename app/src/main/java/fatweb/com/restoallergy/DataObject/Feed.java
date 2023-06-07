package com.fatweb.allergysafenz.DataObject;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Medi on 12/7/2017.
 */

public class Feed {



    @SerializedName("id") private String id;
    @SerializedName("to_user_object") private User user;
    @SerializedName("restaurant_object") private Restaurant resto;
    @SerializedName("date_added") private String dateAdded;
    @SerializedName("date_added_utc") private String dateAddedUtc;
    @SerializedName("rating") private String rating;
    @SerializedName("review") private String review;
    private List<String> mealPictures;
    private List<Allergy> reviewAllergy;


    public List<String> getMealPictures() {
        return mealPictures;
    }

    public void setMealPictures( List<String> mealPictures) {
        this.mealPictures = mealPictures;
    }



    public List<Allergy> getReviewAllergy() {
        return reviewAllergy;
    }

    public void setReviewAllergy(List<Allergy> reviewAllergy) {
        this.reviewAllergy = reviewAllergy;
    }

    public String getDateAddedUtc() {
        return dateAddedUtc;
    }

    public void setDateAddedUtc(String dateAddedUtc) {
        this.dateAddedUtc = dateAddedUtc;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Restaurant getResto() {
        return resto;
    }

    public void setResto(Restaurant resto) {
        this.resto = resto;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



}
