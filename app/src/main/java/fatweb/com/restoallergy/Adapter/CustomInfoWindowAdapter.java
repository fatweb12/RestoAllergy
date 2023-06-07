package com.fatweb.allergysafenz.Adapter;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatRatingBar;


import com.fatweb.allergysafenz.DataObject.Restaurant;
import com.fatweb.allergysafenz.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import java.util.HashMap;


/**
 * Created by aamad on 2/14/2018.
 */

public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
    private Activity context;
    HashMap<Marker, Restaurant> r;
    private View mRestaurantInfoView;

    public CustomInfoWindowAdapter() {
        mRestaurantInfoView =
                context.getLayoutInflater().inflate(R.layout.custom_info_window, null);
    }

    public CustomInfoWindowAdapter(Activity context, HashMap<Marker, Restaurant> r) {
        this.context = context;
        this.r = r;
    }

    @Override
    public View getInfoWindow(Marker marker) {

        View view = context.getLayoutInflater().inflate(R.layout.custom_info_window, null);

        TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        TextView tvAddress = (TextView) view.findViewById(R.id.tvAddress);
        TextView tvCategory = (TextView) view.findViewById(R.id.tvCategory);
        TextView tvRating = (TextView) view.findViewById(R.id.tvRating);
        ImageView ivResto = (ImageView) view.findViewById(R.id.ivResto);
        RelativeLayout info=(RelativeLayout)view.findViewById(R.id.moreinfo);
       // Glide.with(context).load(R.drawable.resto_faded).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE).placeholder(R.drawable.resto_faded).dontAnimate().centerCrop()).into(ivResto);

        AppCompatRatingBar rbRateRestaurant = (AppCompatRatingBar) view.findViewById(R.id.rbRateRestaurant);



        try {

                tvTitle.setText(r.get(marker).getCompany());
                tvAddress.setText(r.get(marker).getAddress());
                tvCategory.setText(r.get(marker).getTypes());



            rbRateRestaurant.setRating(r.get(marker).getRating().isEmpty() ? 0 : Float.valueOf(r.get(marker).getRating()));
            tvRating.setText(r.get(marker).getRating().isEmpty() ? "0" : r.get(marker).getRating());
        } catch (Exception ex) {
            ex.printStackTrace();
            rbRateRestaurant.setRating(0);
        }


        return view;
    }

    @Override
    public View getInfoContents(Marker marker) {


//        View view = context.getLayoutInflater().inflate(R.layout.custom_info_window, null);
//
//        TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);
//        TextView tvAddress = (TextView) view.findViewById(R.id.tvAddress);
//        TextView tvCategory = (TextView) view.findViewById(R.id.tvCategory);
//        AppCompatRatingBar rbRateRestaurant = (AppCompatRatingBar) view.findViewById(R.id.rbRateRestaurant);
//
//
////        String name = r.get(marker).getRestoName()==null?r.get(marker).getCompany(): r.get(marker).getRestoName();
//        tvTitle.setText(r.get(marker).getCompany());
//        tvAddress.setText(r.get(marker).getAddress());
//        tvCategory.setText(r.get(marker).getTypes());
//        try{
//            rbRateRestaurant.setRating(r.get(marker).getRating().isEmpty()?0:Float.valueOf(r.get(marker).getRating()));
//        }catch (Exception ex){
//            ex.printStackTrace();
//            rbRateRestaurant.setRating(0);
//        }
//
//
//        return view;
        return null;
    }


}
