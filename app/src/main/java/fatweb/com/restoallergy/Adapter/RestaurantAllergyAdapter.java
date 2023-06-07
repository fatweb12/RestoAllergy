package com.fatweb.allergysafenz.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fatweb.allergysafenz.Activity.RestaurantViewActivity;
import com.fatweb.allergysafenz.DataObject.Restaurant;
import com.fatweb.allergysafenz.R;
import com.google.gson.Gson;

import java.util.List;


/**
 * Created by aamad on 1/24/2018.
 */

public class RestaurantAllergyAdapter extends RecyclerView.Adapter<RestaurantAllergyAdapter.PostViewHolder> {

    private List<Restaurant> list;
    Context context;
    UserAllergyIconAdapter adapter;

    public RestaurantAllergyAdapter(Context context, List<Restaurant> list) {
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
        postViewHolder.tvRestoName.setText(item.getRestoName());
        postViewHolder.tvRestoAddress.setText(item.getAddress());
        postViewHolder.tvRestoPhone.setText(item.getPhone());


        postViewHolder.cvRestoDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item.setCompany(item.getRestoName());
                Intent i =new Intent(context, RestaurantViewActivity.class);

                i.putExtra("restaurant",new Gson().toJson(item));
                context.startActivity(i);
            }
        });

        try{
            if(item.restoAllergens.size()>0){
                adapter = new UserAllergyIconAdapter(item.restoAllergens,context);
                adapter.notifyDataSetChanged();
                postViewHolder.rvAllergyIcon.setAdapter(adapter);
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }

//        postViewHolder.tvAllergy.setText(item.getCategoryName());
//        switch (item.getCategoryName()){
//            case "Peanuts":
//                postViewHolder.ivIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_peanuts));
//                break;
//            case "Wheat":
//                postViewHolder.ivIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_wheat));
//                break;
//            case "Sesame":
//                postViewHolder.ivIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_sesame));
//                break;
//            case "Tree Nuts":
//                postViewHolder.ivIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_tree_nuts));
//                break;
//            case "Gluten":
//                postViewHolder.ivIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_gluten));
//                break;
//            case "Soy":
//                postViewHolder.ivIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_soy));
//                break;
//            case "Dairy":
//                postViewHolder.ivIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_cheese));
//                break;
//            case "Fish":
//                postViewHolder.ivIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_fish));
//                break;
//            case "Eggs":
//                postViewHolder.ivIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_egg));
//                break;
//            case "Shellfish":
//                postViewHolder.ivIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_shell_fish));
//                break;
////       }

    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_resto_allergy, viewGroup, false);


        return new PostViewHolder(itemView);
    }


    public  class PostViewHolder extends RecyclerView.ViewHolder {

        protected TextView tvRestoName,tvRestoAddress,tvRestoPhone;
        protected CardView cvRestoDetails;
//        protected TextView tvAllergy;
//        protected ImageView ivIcon;
        protected RecyclerView rvAllergyIcon;
        public PostViewHolder(View v) {
            super(v);
            tvRestoName = (TextView) v.findViewById(R.id.tvRestoName);
            tvRestoAddress = (TextView) v.findViewById(R.id.tvRestoAddress);
            tvRestoPhone = (TextView) v.findViewById(R.id.tvRestoPhone);
            cvRestoDetails = (CardView) v.findViewById(R.id.cvRestoDetails);
//            tvAllergy = (TextView) v.findViewById(R.id.tvAllergy);
//            ivIcon = (ImageView) v.findViewById(R.id.ivIcon);
            rvAllergyIcon = (RecyclerView) v.findViewById(R.id.rvAllergyIcon);
            rvAllergyIcon.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false));

        }
    }
}
