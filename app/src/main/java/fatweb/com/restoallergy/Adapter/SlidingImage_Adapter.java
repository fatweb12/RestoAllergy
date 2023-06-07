package com.fatweb.allergysafenz.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;

import com.fatweb.allergysafenz.Model.ImageModel;
import com.fatweb.allergysafenz.R;

import java.util.ArrayList;

/**
 * Created by Parsania Hardik on 23/04/2016.
 */
public class SlidingImage_Adapter extends PagerAdapter {


    private ArrayList<ImageModel> imageModelArrayList;
    private LayoutInflater inflater;
    private Context context;


    public SlidingImage_Adapter(Context context, ArrayList<ImageModel> imageModelArrayList) {
        this.context = context;
        this.imageModelArrayList = imageModelArrayList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return imageModelArrayList.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, final int position) {
        View imageLayout = inflater.inflate(R.layout.slidingimages_layout, view, false);

        assert imageLayout != null;
        final ImageView imageView = (ImageView) imageLayout
                .findViewById(R.id.image);


        imageView.setImageResource(imageModelArrayList.get(position).getImage_drawable());

        view.addView(imageLayout, 0);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position==0){

                   /* Intent in = new Intent(context, Business_act_retro.class);
                    in.putExtra("cat_name", "24");
                    in.putExtra("suburb", "");
                    in.putExtra("catheading", "Camera & Photo Accessories");

                    context.startActivity(in);*/
                }
                else if (position==1){
                    /*Intent in = new Intent(context, Business_act_retro.class);
                    in.putExtra("cat_name", "22");
                    in.putExtra("suburb", "");
                    in.putExtra("catheading", "Electronic Accessories & Gadgets");

                    context.startActivity(in);*/

                }
                else if (position==2){
                   /* Intent in = new Intent(context, Business_act_retro.class);
                    in.putExtra("cat_name", "30");
                    in.putExtra("suburb", "");
                    in.putExtra("catheading", "Bags and Accessories");

                    context.startActivity(in);*/

                }

            }
        });

        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }


}