package com.fatweb.allergysafenz.Activity.ui.home;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.fatweb.allergysafenz.Activity.FindRestaurant;
import com.fatweb.allergysafenz.Activity.SearchActivity;
import com.fatweb.allergysafenz.Activity.SearchByAllergen;
import com.fatweb.allergysafenz.Activity.Searchnearbyme;
import com.fatweb.allergysafenz.Activity.Splash_act;
import com.fatweb.allergysafenz.Adapter.SlidingImage_Adapter;
import com.fatweb.allergysafenz.Model.ImageModel;
import com.fatweb.allergysafenz.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private ArrayList<ImageModel> imageModelArrayList;
    CirclePageIndicator indicator;
    LinearLayout lin_firstbanner;
    String sname, suser, spass, strname, strlstname, selectedtext_str1;
    Button btn_logout;
    private int[] myImageList = new int[]{R.drawable.banner1, R.drawable.banner2, R.drawable.banner3};
    int PERMISSION_ID = 44;
    FusedLocationProviderClient mFusedLocationClient;
    String lat, lng;
LinearLayout btnSearch,btnRateResto,btnFindResto,btnSearchbyallegen;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        btn_logout = root.findViewById(R.id.logout);
        btnSearch = root.findViewById(R.id.btnSearch);
        btnRateResto = root.findViewById(R.id.btnRateResto);
        btnFindResto = root.findViewById(R.id.btnFindResto);
        btnSearchbyallegen = root.findViewById(R.id.btnSearchbyallegen);
        imageModelArrayList = new ArrayList<>();
        imageModelArrayList = populateList();
        mPager = (ViewPager) root.findViewById(R.id.pager);
        mPager.setAdapter(new SlidingImage_Adapter(getActivity(), imageModelArrayList));

        indicator =
                root.findViewById(R.id.indicator);
        init();
        SharedPreferences sp = getActivity().getSharedPreferences("save_data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        suser = sp.getString("email", null);
        spass = sp.getString("password", null);
        sname = sp.getString("firstname", null);
        Log.d("USERDETAIL", suser + spass + sname);
        if ((suser == null) && (spass == null)) {
            btn_logout.setVisibility(View.GONE);

        } else {


        }
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());

        getLastLocation();
        btnRateResto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("LOCATION_VALUES",lat+"\n"+lng);
                Intent in = new Intent(getActivity(), SearchActivity.class);

                in.putExtra("lat", lat);
                in.putExtra("lng", lng);
                startActivity(in);
                Log.d("LOC",lat+" "+lng);
            }
        });
        btnFindResto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("LOCATION_VALUES",lat+"\n"+lng);
                Intent in = new Intent(getActivity(), FindRestaurant.class);

                in.putExtra("lat", lat);
                in.putExtra("lng", lng);
                startActivity(in);
                Log.d("LOC",lat+" "+lng);
            }
        });
        btnSearchbyallegen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("LOCATION_VALUES",lat+"\n"+lng);
                Intent in = new Intent(getActivity(), SearchByAllergen.class);

                in.putExtra("lat", lat);
                in.putExtra("lng", lng);
                startActivity(in);
                Log.d("LOC",lat+" "+lng);
            }
        });
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("LOCATION_VALUES",lat+"\n"+lng);
                Intent in = new Intent(getActivity(), Searchnearbyme.class);

                in.putExtra("lat", lat);
                in.putExtra("lng", lng);
                startActivity(in);
                Log.d("LOC",lat+" "+lng);
            }
        });
        btn_logout.setOnClickListener(new View.OnClickListener() {
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


        return root;
    }

    private ArrayList<ImageModel> populateList() {

        ArrayList<ImageModel> list = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            ImageModel imageModel = new ImageModel();
            imageModel.setImage_drawable(myImageList[i]);
            list.add(imageModel);
        }

        return list;
    }

    private void init() {


        indicator.setViewPager(mPager);

        final float density = getResources().getDisplayMetrics().density;

//Set circle indicator radius
        indicator.setRadius(5 * density);

        NUM_PAGES = imageModelArrayList.size();

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 5000, 5000);

        // Pager listener over indicator
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPage = position;

            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });
    }

    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.getLastLocation().addOnCompleteListener(
                        new OnCompleteListener<Location>() {
                            @Override
                            public void onComplete(@NonNull Task<Location> task) {
                                Location location = task.getResult();
                                if (location == null) {
                                    requestNewLocationData();
                                } else {
                                    lat = location.getLatitude() + "";
                                    lng = location.getLongitude() + "";
                                    /*latTextView.setText(location.getLatitude()+"");
                                    lonTextView.setText(location.getLongitude()+"");*/
                                }
                            }
                        }
                );
            } else {
                Toast.makeText(getActivity(), "Turn on location", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            requestPermissions();
        }
    }


    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(0);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        mFusedLocationClient.requestLocationUpdates(
                mLocationRequest, mLocationCallback,
                Looper.myLooper()
        );

    }

    private LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            lat = mLastLocation.getLatitude() + "";
            lng = mLastLocation.getLongitude() + "";
           /* latTextView.setText(mLastLocation.getLatitude()+"");
            lonTextView.setText(mLastLocation.getLongitude()+"");*/
        }
    };

    private boolean checkPermissions() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(
                getActivity(),
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                PERMISSION_ID
        );
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }
}
