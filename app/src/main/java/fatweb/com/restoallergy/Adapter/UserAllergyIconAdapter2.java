package com.fatweb.allergysafenz.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.fatweb.allergysafenz.DataObject.Allergy;
import com.fatweb.allergysafenz.R;


import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class UserAllergyIconAdapter2 extends RecyclerView.Adapter<UserAllergyIconAdapter2.PostViewHolder> {

    private List<Allergy> allergyList;
    private Context context;

    public UserAllergyIconAdapter2(List<Allergy> allergyList, Context context) {
        this.allergyList = allergyList;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return allergyList.size();
    }

    @Override
    public void onBindViewHolder(final PostViewHolder postViewHolder,final int position) {

        final Allergy item = allergyList.get(position);
       // postViewHolder.tvAllergy.setVisibility(View.GONE);
        postViewHolder.tvAllergy.setText(item.getCategoryName());
        String image = allergyList.get(position).getAllergyimg();
        // Glide.with(RegistrationActivity.this).load(image).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).placeholder(R.drawable.ic_launcher).dontAnimate().override(600, 200).timeout(60000)).into(postViewHolder.ivIcon);

        Glide.with(context).load(image).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).placeholder(R.drawable.ic_launcher).dontAnimate().override(30, 30).timeout(60000)).into(postViewHolder.ivIcon);

       /* switch (item.getCategoryName()) {
            case "Peanuts":
                postViewHolder.ivIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_peanuts));
                break;
            case "Wheat":
                postViewHolder.ivIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_wheat));
                break;
            case "Sesame":
                postViewHolder.ivIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_sesame));
                break;
            case "Tree Nuts":
                postViewHolder.ivIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_tree_nuts));
                break;
            case "Gluten":
                postViewHolder.ivIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_gluten));
                break;
            case "Soy":
                postViewHolder.ivIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_soy));
                break;
            case "Dairy":
                postViewHolder.ivIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_dairy));
                break;
            case "Fish":
                postViewHolder.ivIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_fish));
                break;
            case "Eggs":
                postViewHolder.ivIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_egg));
                break;
            case "Shellfish":
                postViewHolder.ivIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_shell_fish));
                break;
            case "Milk":
                postViewHolder.ivIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_milk));
                break;
            case "Lupin":
                postViewHolder.ivIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_lupin));
                break;
            case "Mustard":
                postViewHolder.ivIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_mustard));
                break;
            case "Sulphur Dioxide":
                postViewHolder.ivIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_sulphur));
                break;
            case "Molluscs":
                postViewHolder.ivIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_mollusc));
                break;
            case "Celery":
                postViewHolder.ivIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_celery));
                break;
            case "Crustaceans":
                postViewHolder.ivIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_crustaceans));
                break;
            case "Other":
                postViewHolder.ivIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_other));
                break;
            default:
                postViewHolder.ivIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_other));
                break;

        }*/
        postViewHolder.ivIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postViewHolder.tvAllergy.setVisibility(View.VISIBLE);

                postViewHolder.tvAllergy.setText(item.getCategoryName());
                postViewHolder.tvAllergy.postDelayed(new Runnable() {
                    public void run() {
                        postViewHolder.tvAllergy.setVisibility(View.GONE);
                    }
                }, 1500);


            }
        });
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_allergy_icon1, viewGroup, false);


        return new PostViewHolder(itemView);
    }


    public  class PostViewHolder extends RecyclerView.ViewHolder {

        protected TextView tvAllergy;
        protected CircleImageView ivIcon;
        public PostViewHolder(View v) {
            super(v);
            tvAllergy = (TextView) v.findViewById(R.id.tvAllergy);
            ivIcon = (CircleImageView) v.findViewById(R.id.ivIcon);

        }
    }
}


