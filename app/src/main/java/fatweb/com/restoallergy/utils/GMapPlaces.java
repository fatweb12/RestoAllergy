package com.fatweb.allergysafenz.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Medi on 12/26/2017.
 */

public class GMapPlaces {
    String pageToken="";

    public List<HashMap<String, String>> parse(JSONObject jObject) {

        JSONArray jPlaces = null;
        try {
            /** Retrieves all the elements in the 'places' array */
            if(jObject.has("GoogleAPIGetByNameResult")){
                jPlaces = jObject.getJSONArray("GoogleAPIGetByNameResult");
            }else{
                jPlaces = jObject.getJSONArray("GoogleAPIGetNearByResult");
            }

            if(jObject.has("next_page_token")){
                pageToken = jObject.getString("next_page_token");
            }else pageToken = null;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        /** Invoking getPlaces with the array of json object
         * where each json object represent a place
         */
        return getPlaces(jPlaces);
    }

    public String getPageToken(){
        return pageToken;
    }

    private List<HashMap<String, String>> getPlaces(JSONArray jPlaces) {
        int placesCount = jPlaces.length();
        List<HashMap<String, String>> placesList = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> place = null;

        /** Taking each place, parses and adds to list object */
        for (int i = 0; i < placesCount; i++) {
            try {
                /** Call getPlace with place JSON object to parse the place */
                place = getPlace((JSONObject) jPlaces.get(i));
                placesList.add(place);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return placesList;
    }

    /**
     * Parsing the Place JSON object
     */
    private HashMap<String, String> getPlace(JSONObject jPlace) {

        HashMap<String, String> place = new HashMap<String, String>();
        String placeName = "-NA-";
        String vicinity = "-NA-";
        String latitude = "";
        String longitude = "";
        String reference = "";
        String id ="";
        String rating="";
        String types ="";
        String scope="";
        String placeId="";
String website="";
        try {
            // Extracting Place name, if available
            if (!jPlace.isNull("name")) {
                placeName = jPlace.getString("name");
            }

            // Extracting Place Vicinity, if available
            if (!jPlace.isNull("vicinity")) {
                vicinity = jPlace.getString("vicinity");
            }

            if (!jPlace.isNull("rating")) {
                rating = jPlace.getString("rating");
            }
            if (!jPlace.isNull("website")) {
                website = jPlace.getString("website");
            }


            latitude = jPlace.getJSONObject("geometry").getJSONObject("location").getString("lat");
            longitude = jPlace.getJSONObject("geometry").getJSONObject("location").getString("lng");
            reference = jPlace.getString("reference");
            id = jPlace.getString("id");
            scope = jPlace.getString("scope");
            placeId =  jPlace.getString("place_id");
            types = jPlace.getJSONArray("types").toString();
            place.put("place_name", placeName);
            place.put("vicinity", vicinity);
            place.put("lat", latitude);
            place.put("lng", longitude);
            place.put("reference", reference);
            place.put("id",id);
            place.put("rating",rating);
            place.put("types",types);
            place.put("scope",scope);
            place.put("placeId",placeId);
            place.put("website",website);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return place;
    }
}
