package com.fatweb.allergysafenz.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import com.fatweb.allergysafenz.Activity.RestaurantViewActivity;
import com.fatweb.allergysafenz.Connectivity.RestClient;
import com.fatweb.allergysafenz.DataObject.Allergy;
import com.fatweb.allergysafenz.DataObject.Restaurant;
import com.fatweb.allergysafenz.DataObject.RestaurantRatingReview;
import com.fatweb.allergysafenz.R;
import com.fatweb.allergysafenz.utils.TimeUtils;
import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import de.hdodenhof.circleimageview.CircleImageView;


public class UsersReviewsAdapter1 extends RecyclerView.Adapter<UsersReviewsAdapter1.PostViewHolder> {

    private List<RestaurantRatingReview> list;
    Context context;
    UserAllergyIconAdapter adapter;
    ImageReviewAdapter iradapter;
    Boolean showEdit = false;

    public interface UsersReviewsListener1 {
        void select(RestaurantRatingReview item, int position);
    }
    private UsersReviewsListener1 listener;

    public UsersReviewsAdapter1(List<RestaurantRatingReview> list, UsersReviewsListener1 listener, Context context, Boolean showEdit) {
        this.list = list;
        this.context = context;
        this.listener = listener;
        this.showEdit = showEdit;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
    @Override
    public void onBindViewHolder(final PostViewHolder postViewHolder,final int position) {

        final RestaurantRatingReview item = list.get(position);
        postViewHolder.tvUser.setText(item.restaurant_info.restaurant_name);



        postViewHolder.tvUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    new GetRestaurantById(item.restaurant_info.restaurant_id).execute();
            }
        });

        try{
            postViewHolder.rbResto.setRating(item.ratings.ratings!=null? Float.valueOf(item.ratings.ratings):0);
            postViewHolder.tvDetails.setText(item.reviews.reviews);

        }catch (Exception ex){
            ex.printStackTrace();
        }

        try{

            String date = item.ratings.date_rated_utc==null?item.reviews.date_review_utc:item.ratings.date_rated_utc;
            postViewHolder.tvDateAdded.setText(TimeUtils.properDateFormat(date));

        }catch (Exception ex){
            ex.printStackTrace();
        }

        try{
            if(item.user.allergens.size()>0){
                List<Allergy> aList = new ArrayList<>();
                for(String s : item.user.allergens){
                    Allergy a = new Allergy();
                    a.setCategoryName(s);
                    aList.add(a);
                }
                adapter = new UserAllergyIconAdapter(aList,context);
                adapter.notifyDataSetChanged();
                postViewHolder.rvAllergyIcon.setAdapter(adapter);
            }



        }catch (Exception ex){
            ex.printStackTrace();
        }



        try{
            if(item.reviews.meal_pictures.size()>0){
                iradapter = new ImageReviewAdapter(item.reviews.meal_pictures,context);
                iradapter.notifyDataSetChanged();
                postViewHolder.rvMealImages.setAdapter(iradapter);
            }


        }catch (Exception ex){
            ex.printStackTrace();
        }

        Glide.with(context).load(item.user.getProfilePic()).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE).skipMemoryCache(true).placeholder(R.drawable.ic_launcher).dontAnimate().timeout(60000)).into(postViewHolder.ivImage);
        postViewHolder.rlMoreInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  Toast.makeText(context, "review", Toast.LENGTH_SHORT).show();
                MaterialDialog dialog = new MaterialDialog.Builder(context)
                        .customView(R.layout.dialog_rate, false)
                        .cancelable(false)
                        .canceledOnTouchOutside(false)
                        .negativeText("Close")
                        .autoDismiss(false)
                        .show();

                dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                final AppCompatRatingBar rbRateRestaurant = (AppCompatRatingBar)dialog.getCustomView().findViewById(R.id.rbRateRestaurant);
                final RadioGroup rg1 = (RadioGroup)dialog.getCustomView().findViewById(R.id.rg1);
                final RadioGroup rg2 = (RadioGroup)dialog.getCustomView().findViewById(R.id.rg2);
                final RadioGroup rg3 = (RadioGroup)dialog.getCustomView().findViewById(R.id.rg3);
                final RadioGroup rg4 = (RadioGroup)dialog.getCustomView().findViewById(R.id.rg4);
                final RadioGroup rg5 = (RadioGroup)dialog.getCustomView().findViewById(R.id.rg5);
                final AppCompatButton btnAllergen = (AppCompatButton) dialog.getCustomView().findViewById(R.id.btnAddAllergen);
                final AppCompatButton btnCaptureMeal = (AppCompatButton) dialog.getCustomView().findViewById(R.id.btnAddMealImages);
                final RecyclerView rvMealImages = (RecyclerView) dialog.getCustomView().findViewById(R.id.rvMealImages);
                final EditText etReview  = (EditText)dialog.getCustomView().findViewById(R.id.etReview);
                btnCaptureMeal.setVisibility(View.GONE);
                btnAllergen.setVisibility(View.GONE);
                etReview.setVisibility(View.GONE);
                RadioButton rb1 = (RadioButton)dialog.getCustomView().findViewById(rg1.getCheckedRadioButtonId());
                RadioButton rb2 = (RadioButton)dialog.getCustomView().findViewById(rg2.getCheckedRadioButtonId());
                RadioButton rb3 = (RadioButton)dialog.getCustomView().findViewById(rg3.getCheckedRadioButtonId());
                RadioButton rb4 = (RadioButton)dialog.getCustomView().findViewById(rg4.getCheckedRadioButtonId());
                RadioButton rb5 = (RadioButton)dialog.getCustomView().findViewById(rg5.getCheckedRadioButtonId());
                rg1.setEnabled(false);
                rg2.setEnabled(false);
                rg3.setEnabled(false);
                rg4.setEnabled(false);
                rg5.setEnabled(false);

                for (int i = 0; i < rg1.getChildCount(); i++) {
                    rg1.getChildAt(i).setEnabled(false);
                }
                for (int i = 0; i < rg1.getChildCount(); i++) {
                    rg2.getChildAt(i).setEnabled(false);
                }
                for (int i = 0; i < rg1.getChildCount(); i++) {
                    rg3.getChildAt(i).setEnabled(false);
                }
                for (int i = 0; i < rg1.getChildCount(); i++) {
                    rg4.getChildAt(i).setEnabled(false);
                }
                for (int i = 0; i < rg1.getChildCount(); i++) {
                    rg5.getChildAt(i).setEnabled(false);
                }


//                Log.e("review answers",new Gson().toJson(item.reviews.review_answers));

                try{
                    if(Float.valueOf(item.reviews.review_answers.get(0))==1){
                        rg3.check(R.id.rg3rb1);
                    }else{
                        rg3.check(R.id.rg3rb2);
                    }

                    switch (item.reviews.review_answers.get(1)){
                       case "1.0":
                        case "1":
                            rg1.check(R.id.rg1rb1);
                            break;
                       case "2.0":
                        case "2":
                            rg1.check(R.id.rg1rb2);
                            break;
                       case "3.0":
                        case "3":
                            rg1.check(R.id.rg1rb3);
                            break;
                       case "4.0":
                        case "4":
                            rg1.check(R.id.rg1rb4);
                            break;
                       case "5.0":
                        case "5":
                            rg1.check(R.id.rg1rb5);
                            break;



                    }

                    switch (item.reviews.review_answers.get(2)){
                       case "1.0":
                        case "1":
                            rg2.check(R.id.rg2rb1);
                            break;
                       case "2.0":
                        case "2":
                            rg2.check(R.id.rg2rb2);
                            break;
                       case "3.0":
                        case "3":
                            rg2.check(R.id.rg2rb3);
                            break;
                       case "4.0":
                        case "4":
                            rg2.check(R.id.rg2rb4);
                            break;
                       case "5.0":
                        case "5":
                            rg2.check(R.id.rg2rb5);
                            break;
                    }

                    switch (item.reviews.review_answers.get(3)){
                       case "1.0":
                        case "1":
                            rg4.check(R.id.rg4rb1);
                            break;
                       case "2.0":
                        case "2":
                            rg4.check(R.id.rg4rb2);
                            break;
                       case "3.0":
                        case "3":
                            rg4.check(R.id.rg4rb3);
                            break;
                       case "4.0":
                        case "4":
                            rg4.check(R.id.rg4rb4);
                            break;
                       case "5.0":
                        case "5":
                            rg4.check(R.id.rg4rb5);
                            break;
                    }

                    switch (item.reviews.review_answers.get(4)){
                       case "1.0":
                        case "1":
                            rg5.check(R.id.rg5rb1);
                            break;
                       case "2.0":
                        case "2":
                            rg5.check(R.id.rg5rb2);
                            break;
                       case "3.0":
                        case "3":
                            rg5.check(R.id.rg5rb3);
                            break;
                       case "4.0":
                        case "4":
                            rg5.check(R.id.rg5rb4);
                            break;
                       case "5.0":
                        case "5":
                            rg5.check(R.id.rg5rb5);
                            break;
                    }
                    Float rating = 0.0f;
                    for(int i =0;i<item.reviews.review_answers.size();i++){

                        rating+= Float.valueOf(item.reviews.review_answers.get(i));
                    }

                    rbRateRestaurant.setRating(item.ratings.ratings!=null? Float.valueOf(item.ratings.ratings):0);
                    //.setRating(rating/item.reviews.review_answers.size());

                }catch (Exception ex){
                    ex.printStackTrace();
                }



                dialog.getBuilder().onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                });



            }
        });

        if(showEdit){
            postViewHolder.rlDeleteEdit.setVisibility(View.VISIBLE);
        }
       else postViewHolder.rlDeleteEdit.setVisibility(View.VISIBLE);

        postViewHolder.rlDeleteEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                        new MaterialDialog.Builder(context)
//                                .title("Warning!")
//                                .content("This restaurant contains the following allergens:")
//                                .positiveText("Close")
//                                .show();
                listener.select(item,position);

            }
        });
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_reviews1, viewGroup, false);


        return new PostViewHolder(itemView);
    }


    public  class PostViewHolder extends RecyclerView.ViewHolder {

        protected TextView tvUser,tvDateAdded,tvDetails;
        protected RecyclerView rvAllergyIcon,rvMealImages;
        protected AppCompatRatingBar rbResto;
        protected CircleImageView ivImage;
        protected RelativeLayout rlMoreInfo,rlDeleteEdit;
        public PostViewHolder(View v) {
            super(v);
            rbResto = (AppCompatRatingBar) v.findViewById(R.id.rbResto);
            tvUser = (TextView) v.findViewById(R.id.tvUser);
            tvDateAdded = (TextView) v.findViewById(R.id.tvDateAdded);
            tvDetails = (TextView) v.findViewById(R.id.tvDetails);
            rvAllergyIcon = (RecyclerView) v.findViewById(R.id.rvAllergyIcon);
            ivImage = (CircleImageView) v.findViewById(R.id.ivImage);
            rvMealImages = (RecyclerView) v.findViewById(R.id.rvMealImages);
            rlMoreInfo = (RelativeLayout) v.findViewById(R.id.rlMoreInfo);
            rvAllergyIcon.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false));
            rvMealImages.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false));
            rlDeleteEdit = (RelativeLayout) v.findViewById(R.id.rlDeleteEdit);
            rlDeleteEdit.setVisibility(View.GONE);
        }
    }


    private class GetRestaurantById extends AsyncTask<Void, Void, Boolean> {
        Boolean success;
        String responseString;
         String id;
        ProgressDialog pd;
        Restaurant thisResto;


        public  GetRestaurantById(String id){
            this.id = id;


        }



        @Override
        protected void onPreExecute() {

            pd = new ProgressDialog(context);
            pd.setMessage("Loading...");
            pd.show();

            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Void... v) {

            try {

                RequestParams params = new RequestParams();
//                params.put("access_token", prefs.getString(HSApplication.ACCESS_TOKEN));
//                android.util.Log.i("token", prefs.getString(HSApplication.ACCESS_TOKEN));

                String normalUrl = context.getString(R.string.url_live) + "GetRestaurantById/"+id;
                Log.d("GetRestaurantByPlaceId",normalUrl);
                RestClient.get(normalUrl, params, new JsonHttpResponseHandler() {
                    @Override
                    public void onFailure(int statusCode, Header[] headers, String response, Throwable throwable) {
                        super.onFailure(statusCode, headers, responseString, throwable);
                        success = false;
                        responseString = response;
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                        success = false;
                        //                       responseString = errorResponse.toString();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                        Log.d("response", errorResponse.toString());
                        success = false;
                        responseString = errorResponse.toString();
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseString) {
                        super.onSuccess(statusCode, headers, responseString);
                        success = true;

//                        prefs.put(HSApplication.VERSION, newUser.getAndroidVersion());
//                        prefs.put(HSApplication.thisUser, new Gson().toJson(newUser));
                        Log.i("responseString", responseString);

                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        Log.i("JSONObject response", response.toString());
                        try {
                            if (response.has("GetRestaurantByIdResult")) {
                                if(response.isNull("GetRestaurantByIdResult")){
                                    success = false;
                                }else {
                                    success = true;
                                    Log.i("response", response.toString());
                                    JSONObject object = response.getJSONObject("GetRestaurantByIdResult");
                                    thisResto = new Gson().fromJson(object.toString(),Restaurant.class);
                                }

                            } else {
                                success = false;

                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }


                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                        Log.i("JSONArray response", response.toString());
                        success = true;


                    }
                });

                return success;
            } catch (Exception e) {
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean s) {
            super.onPostExecute(s);
            //  swipeContainer.setRefreshing(false);
            try{
                pd.dismiss();
                //  refreshOverlay.setVisibility(View.GONE);
            }
            catch (Exception ex){
                ex.printStackTrace();
            }

            if(s){

                Intent i =new Intent(context, RestaurantViewActivity.class);
                i.putExtra("restaurant",new Gson().toJson(thisResto,Restaurant.class));
                context.startActivity(i);
            }


        }



    }
}
