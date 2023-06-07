package fatweb.com.restoallergy.Activity.ui.Profile;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.fatweb.allergysafenz.Activity.AllergyGuide;
import com.fatweb.allergysafenz.Activity.ContactUs;
import com.fatweb.allergysafenz.Activity.Feed_act;
import com.fatweb.allergysafenz.Activity.Login_act;
import com.fatweb.allergysafenz.Activity.MyReviews;
import com.fatweb.allergysafenz.Activity.Profile_act;
import com.fatweb.allergysafenz.Activity.SearchUser;
import com.fatweb.allergysafenz.Activity.ui.dashboard.DashboardViewModel;
import com.fatweb.allergysafenz.R;

import fatweb.com.restoallergy.Activity.ui.dashboard.DashboardViewModel;
import fatweb.com.restoallergy.R;


public class MyAccountFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    LinearLayout btn_login;
    AppCompatButton btn_login1;
    String sname, suser, spass, strname, strlstname, selectedtext_str1;
    LinearLayout lin_detail,  lin_allegyguide, lin_contact,lin_serachUser,lin_feed;
   AppCompatButton lin_profile,lin_reviews;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_myaccount, container, false);
        btn_login = root.findViewById(R.id.notlogin);
        btn_login1 = root.findViewById(R.id.Login);
        lin_detail = root.findViewById(R.id.lin_detail);
        lin_profile = root.findViewById(R.id.lin_profile);
        lin_feed = root.findViewById(R.id.lin_feed);
        lin_reviews = root.findViewById(R.id.btn_reviews);
        lin_allegyguide = root.findViewById(R.id.allergyguide);
        lin_contact = root.findViewById(R.id.lin_contactus);
        lin_serachUser = root.findViewById(R.id.lin_searchUser);
        SharedPreferences sp = getActivity().getSharedPreferences("save_data", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        suser = sp.getString("email_address", null);
        spass = sp.getString("password", null);
        sname = sp.getString("first", null);
        Log.d("USERDETAIL", suser + spass + sname);
        if ((suser == null) && (spass == null)) {
            lin_detail.setVisibility(View.GONE);

        } else {
            lin_detail.setVisibility(View.GONE);

           btn_login.setVisibility(View.GONE);
            Intent in = new Intent(getContext(), Profile_act.class);
            startActivity(in);
        }
        btn_login1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getContext(), Login_act.class);
                startActivity(in);
            }
        });
        lin_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getContext(), Profile_act.class);
                startActivity(in);
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
