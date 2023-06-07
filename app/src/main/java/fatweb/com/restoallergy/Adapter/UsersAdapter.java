package com.fatweb.allergysafenz.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import com.fatweb.allergysafenz.Activity.UserProfileActivity;
import com.fatweb.allergysafenz.DataObject.User;
import com.fatweb.allergysafenz.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by aamad on 1/24/2018.
 */

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.PostViewHolder> {

    private List<User> list;
    Context context;

    public UsersAdapter(List<User> list, Context context) {
        this.list = list;
        this.context = context;

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onBindViewHolder(final PostViewHolder postViewHolder,final int position) {

        final User item = list.get(position);
        if(item.name.isEmpty()){
            postViewHolder.tvUser.setText(item.getFirstName()+" "+item.getLastName());
        }else  postViewHolder.tvUser.setText(item.name);

        postViewHolder.tvUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(context, UserProfileActivity.class);
                i.putExtra("user",item);
                context.startActivity(i);
            }
        });
        Glide.with(context).load(item.getProfilePic()).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE).skipMemoryCache(true).placeholder(R.drawable.ic_launcher).dontAnimate().timeout(60000)).into(postViewHolder.ivImage);

    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_users, viewGroup, false);


        return new PostViewHolder(itemView);
    }


    public  class PostViewHolder extends RecyclerView.ViewHolder {

        protected TextView tvUser;
        protected CircleImageView ivImage;
        public PostViewHolder(View v) {
            super(v);
            tvUser = (TextView) v.findViewById(R.id.tvUser);
            ivImage = (CircleImageView) v.findViewById(R.id.ivImage);


        }
    }


}
