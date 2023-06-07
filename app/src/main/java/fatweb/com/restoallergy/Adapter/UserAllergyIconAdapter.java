package com.fatweb.allergysafenz.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.fatweb.allergysafenz.DataObject.Allergy;
import com.fatweb.allergysafenz.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by aamad on 1/24/2018.
 */

public class UserAllergyIconAdapter extends RecyclerView.Adapter<UserAllergyIconAdapter.PostViewHolder> {

    private List<Allergy> allergyList;
    private Context context;

    public UserAllergyIconAdapter(List<Allergy> allergyList, Context context) {
        this.allergyList = allergyList;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return allergyList.size();
    }

    @Override
    public void onBindViewHolder(final PostViewHolder postViewHolder, final int position) {

        final Allergy item = allergyList.get(position);
       // postViewHolder.tvAllergy.setVisibility(View.GONE);
        postViewHolder.tvAllergy.setText(item.getCategoryName());
        switch (item.getCategoryName()) {
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
                postViewHolder.ivIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_launcher));
                break;
        }
       /* postViewHolder.ivIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Allergy", Toast.LENGTH_SHORT).show();
                postViewHolder.tvAllergy.setVisibility(View.GONE);

                postViewHolder.tvAllergy.setText(item.getCategoryName());
                postViewHolder.tvAllergy.postDelayed(new Runnable() {
                    public void run() {
                        postViewHolder.tvAllergy.setVisibility(View.GONE);
                    }
                }, 1500);


            }
        });*/
        postViewHolder.tvAllergy.setVisibility(View.INVISIBLE);
        postViewHolder.ivIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // Toast.makeText(context, "allergy", Toast.LENGTH_SHORT).show();

               /* Toast toast = Toast.makeText(context,"Sample Toast",Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
                toast.show();*/
                postViewHolder.tvAllergy.setVisibility(View.VISIBLE);

                postViewHolder.tvAllergy.setText(item.getCategoryName());
                postViewHolder.tvAllergy.postDelayed(new Runnable() {
                    public void run() {
                        postViewHolder.tvAllergy.setVisibility(View.INVISIBLE);
                    }
                }, 1500);
            }
        });

    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_allergy_icon, viewGroup, false);


        return new PostViewHolder(itemView);
    }


    public class PostViewHolder extends RecyclerView.ViewHolder {

        protected TextView tvAllergy;
        protected CircleImageView ivIcon;

        public PostViewHolder(View v) {
            super(v);
            tvAllergy = (TextView) v.findViewById(R.id.tvAllergy);
            ivIcon = (CircleImageView) v.findViewById(R.id.ivIcon);

        }
    }
}
