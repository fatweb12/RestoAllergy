package com.fatweb.allergysafenz.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.fatweb.allergysafenz.Activity.RestaurantViewActivity;
import com.fatweb.allergysafenz.DataObject.Restaurant;
import com.fatweb.allergysafenz.R;
import com.google.gson.Gson;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by aamad on 1/24/2018.
 */

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.PostViewHolder> {

    private List<Restaurant> list;
    Context context;
    UserAllergyIconAdapter adapter;

    public RestaurantAdapter(Context context, List<Restaurant> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onBindViewHolder(final PostViewHolder postViewHolder,final int position) {

        final Restaurant item = list.get(position);



        postViewHolder.tvAddress.setText(item.getAddress());
        postViewHolder.tvCategory.setText(item.getTypes()==null?"":item.getTypes());
        try{
            postViewHolder.tvTitle.setText(item.getCompany()==null?item.getRestoName():item.getCompany());
            postViewHolder.rbRateRestaurant.setRating(item.getRating().isEmpty()?0: Float.valueOf(item.getRating()));
            postViewHolder. tvRating.setText(item.getRating().isEmpty()?"0":item.getRating());
        }catch (Exception ex){
            ex.printStackTrace();
            postViewHolder.rbRateRestaurant.setRating(0);
        }



        postViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(item.getRestaurantId()!=null){
                    //new GetRestaurantById(item.getRestaurantId()).execute();
                }else{
                    Intent i =new Intent(context, RestaurantViewActivity.class);
                    i.putExtra("restaurant",new Gson().toJson(item,Restaurant.class));
                    context.startActivity(i);
                  //  new GetRestaurantByPlaceId(item).execute();
                }



            }
        });



    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_restaurant, viewGroup, false);


        return new PostViewHolder(itemView);
    }


    public  class PostViewHolder extends RecyclerView.ViewHolder {



        @BindView(R.id.tvTitle) protected TextView tvTitle;
        @BindView(R.id.tvAddress) protected TextView tvAddress;
        @BindView(R.id.tvRating) protected TextView tvRating;
        @BindView(R.id.tvCategory) protected TextView tvCategory;
        @BindView(R.id.ivResto) protected ImageView ivResto;
        @BindView(R.id.rbRateRestaurant) protected AppCompatRatingBar rbRateRestaurant;
        @BindView(R.id.cardView) protected CardView cardView;



        public PostViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);

        }
    }




}
