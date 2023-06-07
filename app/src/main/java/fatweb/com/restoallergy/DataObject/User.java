package com.fatweb.allergysafenz.DataObject;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Asus-Amad-PC on 11/14/2017.
 */

public class User implements Serializable {

    @SerializedName("id") private String id;
    @SerializedName("first_name") private String firstName;
    @SerializedName("about_bio") private String aboutBio;
    @SerializedName("last_name") private String lastName;
    @SerializedName("password") private String password;

    @SerializedName("Target") private String taget_code;

    public String getTaget_code() {
        return taget_code;
    }

    public void setTaget_code(String taget_code) {
        this.taget_code = taget_code;
    }

    public String getLang_text() {
        return lang_text;
    }

    public void setLang_text(String lang_text) {
        this.lang_text = lang_text;
    }

    @SerializedName("text") private String lang_text;

    public String getAboutBio() {
        return aboutBio;
    }

    public void setAboutBio(String aboutBio) {
        this.aboutBio = aboutBio;
    }

    @SerializedName("profile_pic") private String profilePic;
    @SerializedName("email_address") private String emailAddress;
    @SerializedName("contact_number") private String contactNumber;
    @SerializedName("status") private String status;
    @SerializedName("address") private String address;
    @SerializedName("message") private String message;
    @SerializedName("rating_user_id") private String ratingid;
    @SerializedName("role") private String role;
    @SerializedName("is_followed") private String isFollowed;
    @SerializedName("allergens")public List<String> allergens = new ArrayList<>();
    @SerializedName("name") public String name="";
    @SerializedName("user_id") public String user_id="";
    @SerializedName("android_version") private String androidVersion;
    @SerializedName("converted_text") private String convertedtext;

    public String getConvertedtext() {
        return convertedtext;
    }

    public void setConvertedtext(String convertedtext) {
        this.convertedtext = convertedtext;
    }

    public String getAndroidVersion() {
        return androidVersion;
    }

    public void setAndroidVersion(String androidVersion) {
        this.androidVersion = androidVersion;
    }



    public String getIsFollowed() {
        return isFollowed;
    }

    public void setIsFollowed(String isFollowed) {
        this.isFollowed = isFollowed;
    }



    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }



    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfilePic() {
        return profilePic.replace("http://","https://").replace("45.76.120.40","api.allergyrate.com");
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
