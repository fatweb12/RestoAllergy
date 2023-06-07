package com.fatweb.allergysafenz.Adapter;

import android.content.Context;
import android.content.Intent;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.fatweb.allergysafenz.Activity.RestaurantViewActivity;
import com.fatweb.allergysafenz.Activity.UserProfileActivity;
import com.fatweb.allergysafenz.DataObject.Feed;
import com.fatweb.allergysafenz.DataObject.Restaurant;
import com.fatweb.allergysafenz.R;
import com.fatweb.allergysafenz.utils.TimeUtils;
import com.google.gson.Gson;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by aamad on 1/24/2018.
 */

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.PostViewHolder> {

    private List<Feed> list;
    Context context;
    UserAllergyIconAdapter adapter;
    ImageReviewAdapter iradapter;

    public FeedAdapter(List<Feed> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onBindViewHolder(final PostViewHolder postViewHolder,final int position) {

        final Feed item = list.get(position);
        postViewHolder.tvName.setText(item.getUser().getFirstName()+" "+item.getUser().getLastName());

        try{
            postViewHolder.tvDateAdded.setText(TimeUtils.properDateFormatFeed(item.getDateAddedUtc(),context));

        }catch (Exception ex){
            ex.printStackTrace();
        }
        postViewHolder.tvRestaurant.setText(item.getResto().getCompany());
        if(TextUtils.isEmpty(item.getReview())){
            postViewHolder.tvReview.setVisibility(View.GONE);
        }else{
            postViewHolder.tvReview.setVisibility(View.VISIBLE);
            postViewHolder.tvReview.setText(/*"\""+*/item.getReview()/*+"\""*/);
        }
        postViewHolder.rbResto.setRating(Float.valueOf(item.getRating()));
        postViewHolder.tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(context, UserProfileActivity.class);
                i.putExtra("user",item.getUser());
                context.startActivity(i);
            }
        });

        postViewHolder.tvRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(context, RestaurantViewActivity.class);
                i.putExtra("restaurant",new Gson().toJson(item.getResto(), Restaurant.class));
                context.startActivity(i);
            }
        });

        Glide.with(context).load(item.getUser().getProfilePic()).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE).skipMemoryCache(true).placeholder(R.drawable.ic_launcher).dontAnimate().timeout(60000)).into(postViewHolder.ivImage);

        postViewHolder.cvLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(context, RestaurantViewActivity.class);
              //  Toast.makeText(context, ""+item.getResto(), Toast.LENGTH_SHORT).show();
                i.putExtra("restaurant",new Gson().toJson(item.getResto(), Restaurant.class));
                context.startActivity(i);
            }
        });

        try{
            if(item.getReviewAllergy().size()>0){
                adapter = new UserAllergyIconAdapter(item.getReviewAllergy(),context);
                adapter.notifyDataSetChanged();
                postViewHolder.rvAllergyIcon.setAdapter(adapter);
                postViewHolder.rvAllergyIcon.setVisibility(View.VISIBLE);
            }else{
                postViewHolder.rvAllergyIcon.setVisibility(View.GONE);
            }



        }catch (Exception ex){
            ex.printStackTrace();
        }



        try{
            if(item.getMealPictures().size()>0){
                iradapter = new ImageReviewAdapter(item.getMealPictures(),context);
                iradapter.notifyDataSetChanged();
                postViewHolder.rvMealImages.setAdapter(iradapter);
                postViewHolder.rvMealImages.setVisibility(View.VISIBLE);
            }else{
                postViewHolder.rvMealImages.setVisibility(View.GONE);
            }


        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_feed, viewGroup, false);


        return new PostViewHolder(itemView);
    }


    public  class PostViewHolder extends RecyclerView.ViewHolder {

        protected TextView tvName,tvRestaurant,tvDateAdded,tvReview;
        protected CircleImageView ivImage;
        protected AppCompatRatingBar rbResto;
        protected CardView cvLayout;
        protected RecyclerView rvAllergyIcon,rvMealImages;
        public PostViewHolder(View v) {
            super(v);
            tvName = (TextView) v.findViewById(R.id.tvName);
            tvRestaurant = (TextView) v.findViewById(R.id.tvRestaurant);
            tvReview = (TextView) v.findViewById(R.id.tvReview);
            ivImage = (CircleImageView) v.findViewById(R.id.ivImage);
            rbResto = (AppCompatRatingBar) v.findViewById(R.id.rbResto);
            tvDateAdded = (TextView) v.findViewById(R.id.tvDateAdded);
            cvLayout = (CardView) v.findViewById(R.id.cvLayout);
            rvAllergyIcon = (RecyclerView) v.findViewById(R.id.rvAllergyIcon);
            rvMealImages = (RecyclerView) v.findViewById(R.id.rvMealImages);
            rvAllergyIcon.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
            rvMealImages.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
        }
    }
}
