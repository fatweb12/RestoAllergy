package com.fatweb.allergysafenz.DataObject;

import android.media.Rating;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aamad on 1/22/2018.
 */

public class Reviews {



    @SerializedName("user_id")private String userId;
    @SerializedName("restaurant_id")private String restaurantId;
    @SerializedName("details")private String details;
    @SerializedName("rating_user_id")private String details1;
    @SerializedName("date_added")private String dateAdded;
    @SerializedName("company")private String company;

    @SerializedName("restaurant")private Restaurant restaurant;
    @SerializedName("users")private  User user;
    @SerializedName("user")private String userName;
    @SerializedName("added_by")private String addedBy;
    @SerializedName("profile_pic")private String profilePic;
    @SerializedName("ratings")private Rating ratings;
    @SerializedName("rating_user_id")private String rating_user;
    public Rating getRatings() {
        return ratings;
    }

    public void setRatings(Rating ratings) {
        this.ratings = ratings;
    }


   private String rating;
    private List<String> mealPictures;

    public List<String> getMealPictures() {
        return mealPictures;
    }

    public void setMealPictures( List<String> mealPictures) {
        this.mealPictures = mealPictures;
    }




    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }



    public List<Allergy> getReviewAllergy() {
        return reviewAllergy;
    }

    public void setReviewAllergy(List<Allergy> reviewAllergy) {
        this.reviewAllergy = reviewAllergy;
    }

   private List<Allergy> reviewAllergy;
    private ArrayList<Allergy> userAllergy;

    private  User thisUser,currentUser;



    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public List<Allergy> getUserAllergy() {
        return userAllergy;
    }

    public void setUserAllergy(ArrayList<Allergy> userAllergy) {
        this.userAllergy = userAllergy;
    }

    public User getThisUser() {
        return thisUser;
    }

    public void setThisUser(User thisUser) {
        this.thisUser = thisUser;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }



    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }




    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }



    public String getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(String addedBy) {
        this.addedBy = addedBy;
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

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }


    public String getRating_user() {
        return rating_user;
    }

    public void setRating_user(String rating_user) {
        this.rating_user = rating_user;
    }
}
