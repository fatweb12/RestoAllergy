package com.fatweb.allergysafenz.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
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

import com.fatweb.allergysafenz.Activity.UserProfileActivity;
import com.fatweb.allergysafenz.Connectivity.RestClient;
import com.fatweb.allergysafenz.DataObject.Allergy;
import com.fatweb.allergysafenz.DataObject.User;
import com.fatweb.allergysafenz.DataObject.UserRatingReview;
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


public class UserReviewsAdapter extends RecyclerView.Adapter<UserReviewsAdapter.PostViewHolder> {
    private List<UserRatingReview> list;
    Context context;
    UserAllergyIconAdapter adapter;
    ImageReviewAdapter iradapter;
    User loggedinUser;

    public interface UserReviewsListener {
        void select(UserRatingReview item, int position);
    }
    private UserReviewsListener listener;


    public UserReviewsAdapter(List<UserRatingReview> list,UserReviewsListener listener,User loggedinUser,Context context) {
        this.list = list;
        this.context = context;
        this.listener = listener;
        this.loggedinUser =loggedinUser ;

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onBindViewHolder(final PostViewHolder postViewHolder,final int position) {

        final UserRatingReview item = list.get(position);
        postViewHolder.tvUser.setText(item.user_info.name);
        postViewHolder.tvRatingid1.setText(item.ratings.ratings1);
        postViewHolder.tvUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CheckUserInfo(item.user,item.user_info.user_id,context).execute();
            }
        });

        try{
            postViewHolder.rbResto.setRating(item.ratings.ratings!=null?Float.valueOf(item.ratings.ratings):0);
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
            if(item.user_info.allergens.size()>0){
                List<Allergy> aList = new ArrayList<>();
                for(String s : item.user_info.allergens){
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
        Glide.with(context).load(item.user_info.profile_pic).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE).skipMemoryCache(true).placeholder(R.drawable.ic_launcher).dontAnimate().timeout(60000)).into(postViewHolder.ivImage);


        postViewHolder.rlMoreInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

                        rating+=Float.valueOf(item.reviews.review_answers.get(i));
                    }
                    try{
                        rbRateRestaurant.setRating(item.ratings.ratings!=null?Float.valueOf(item.ratings.ratings):0);


                    }catch (Exception ex){
                        ex.printStackTrace();
                    }
                    // rbRateRestaurant.setRating(rating/item.reviews.review_answers.size());

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

        try{
            if(loggedinUser.getId().equalsIgnoreCase(item.user_info.user_id)){
                postViewHolder.rlDeleteEdit.setVisibility(View.VISIBLE);
            }else{
                postViewHolder.rlDeleteEdit.setVisibility(View.GONE);
            }
        }catch (Exception ex){
            postViewHolder.rlDeleteEdit.setVisibility(View.GONE);
        }



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
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_reviews, viewGroup, false);


        return new PostViewHolder(itemView);
    }


    public  class PostViewHolder extends RecyclerView.ViewHolder {

        protected TextView tvUser,tvDateAdded,tvDetails,tvRatingid1;
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
            tvRatingid1 = (TextView) v.findViewById(R.id.tvratingid1);

            rvAllergyIcon = (RecyclerView) v.findViewById(R.id.rvAllergyIcon);
            ivImage = (CircleImageView) v.findViewById(R.id.ivImage);
            rvMealImages = (RecyclerView) v.findViewById(R.id.rvMealImages);
            rlMoreInfo = (RelativeLayout) v.findViewById(R.id.rlMoreInfo);
            rlDeleteEdit = (RelativeLayout) v.findViewById(R.id.rlDeleteEdit);
            rvAllergyIcon.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
            rvMealImages.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));

        }
    }


    private class CheckUserInfo extends AsyncTask<Void, Void, Boolean> {
        Boolean success;
        String responseString;

        User currentUser;
        String toUserId;
        User thisUser;
        ProgressDialog pd;
        Context context;



        public  CheckUserInfo(User currentUser,String toUserId,Context context){
            this.currentUser = currentUser;
            this.toUserId = toUserId;
            this.context = context;

        }
        @Override
        protected void onPreExecute() {
            pd = new ProgressDialog(context);
            pd.setCancelable(false);
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

                String normalUrl = context.getString(R.string.url_live) + "GetViewedUserInfoByUserId/"+currentUser.getId()+"/"+toUserId;
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
                        android.util.Log.d("response", errorResponse.toString());
                        success = false;
                        responseString = errorResponse.toString();
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseString) {
                        super.onSuccess(statusCode, headers, responseString);
                        success = true;

//                        prefs.put(HSApplication.VERSION, newUser.getAndroidVersion());
//                        prefs.put(HSApplication.thisUser, new Gson().toJson(newUser));
                        android.util.Log.i("responseString", responseString);

                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        android.util.Log.i("JSONObject response", response.toString());
                        try {
                            if (response.has("GetViewedUserInfoByUserIdResult")) {
                                success = true;
                                android.util.Log.i("response", response.toString());
                                JSONArray jsonArray = response.getJSONArray("GetViewedUserInfoByUserIdResult");
                                for(int i = 0 ;i<jsonArray.length();i++){
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    thisUser = new Gson().fromJson(object.toString(),User.class);

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
                        android.util.Log.i("JSONArray response", response.toString());
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
            } catch (Exception ex){
                ex.printStackTrace();
            }

            if(s){
                Intent i =new Intent(context, UserProfileActivity.class);
                i.putExtra("user",thisUser);
                context.startActivity(i);


            }else{


            }


        }

    }
}
