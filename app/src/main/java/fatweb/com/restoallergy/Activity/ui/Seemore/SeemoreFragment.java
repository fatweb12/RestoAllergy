package fatweb.com.restoallergy.Activity.ui.Seemore;

import static android.content.Context.MODE_PRIVATE;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.fatweb.allergysafenz.Activity.AboutUs_act;
import com.fatweb.allergysafenz.Activity.AllergyGuide;
import com.fatweb.allergysafenz.Activity.ContactUs;
import com.fatweb.allergysafenz.Activity.Feed_act;
import com.fatweb.allergysafenz.Activity.MyReviews;
import com.fatweb.allergysafenz.Activity.Privacy_act;
import com.fatweb.allergysafenz.Activity.SearchUser;
import com.fatweb.allergysafenz.Activity.Splash_act;
import com.fatweb.allergysafenz.Activity.ui.dashboard.DashboardViewModel;
import com.fatweb.allergysafenz.R;


public class SeemoreFragment extends Fragment {
    LinearLayout lin_detail, lin_allegyguide, lin_contact, lin_serachUser, lin_feed, lin_reviews, lin_privacy, lin_about,lin_logoout;

    private DashboardViewModel dashboardViewModel;
    String str_img, strurl, str_call, str_web, str_contact, sname, suser, spass, strname;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_seemore, container, false);
        lin_feed = root.findViewById(R.id.lin_feed);
        lin_reviews = root.findViewById(R.id.btn_reviews);
        lin_allegyguide = root.findViewById(R.id.allergyguide);
        lin_contact = root.findViewById(R.id.lin_contactus);
        lin_privacy = root.findViewById(R.id.lin_privacy);
        lin_about = root.findViewById(R.id.lin_about);
        lin_logoout = root.findViewById(R.id.lin_logoout);

        lin_serachUser = root.findViewById(R.id.lin_searchUser);
        SharedPreferences sp = getActivity().getSharedPreferences("save_data", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        suser = sp.getString("email_address", null);
        spass = sp.getString("password", null);
        sname = sp.getString("first", null);
        Log.d("USERDETAIL", suser + spass + sname);

        if ((suser == null) && (spass == null)) {
            lin_feed.setVisibility(View.GONE);
            lin_reviews.setVisibility(View.GONE);
            lin_serachUser.setVisibility(View.GONE);
            lin_logoout.setVisibility(View.GONE);

        } else {

        }
        lin_logoout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.dialog_logout);
                Button dialogButton = (Button) dialog.findViewById(R.id.btn_done);
                Button dialogButtonCancel = (Button) dialog.findViewById(R.id.btn_cancel);
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferences preferences = getActivity().getSharedPreferences("save_data", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.clear();
                        editor.commit();
                        Intent intent = new Intent(getActivity(), Splash_act.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        dialog.dismiss();
                    }
                });
                dialogButtonCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });


        lin_feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getContext(), Feed_act.class);
                startActivity(in);
            }
        });
        lin_reviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getContext(), MyReviews.class);
                startActivity(in);
            }
        });
        lin_allegyguide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getContext(), AllergyGuide.class);
                startActivity(in);
            }
        });
        lin_privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getContext(), Privacy_act.class);
                startActivity(in);
            }
        });
        lin_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getContext(), AboutUs_act.class);
                startActivity(in);
            }
        });
        lin_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getContext(), ContactUs.class);
                startActivity(in);
            }
        });
        lin_serachUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getContext(), SearchUser.class);
                startActivity(in);
            }
        });
        return root;
    }


}
