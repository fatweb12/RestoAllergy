package com.fatweb.allergysafenz.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;


import com.fatweb.allergysafenz.R;

import java.util.List;


/**
 * Created by aamad on 1/24/2018.
 */

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.PostViewHolder> {

    private List<String> list;
    Context context;

    public ImageAdapter(List<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onBindViewHolder(final PostViewHolder postViewHolder,final int position) {

        byte[] decodedString = Base64.decode(list.get(position), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0,decodedString.length);
        postViewHolder.ivMeal.setImageBitmap(decodedByte);


    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_image, viewGroup, false);


        return new PostViewHolder(itemView);
    }


    public  class PostViewHolder extends RecyclerView.ViewHolder {


        protected ImageView ivMeal;
        public PostViewHolder(View v) {
            super(v);
            ivMeal = (ImageView) v.findViewById(R.id.ivMeal);


        }
    }
}
