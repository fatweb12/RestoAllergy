package fatweb.com.restoallergy.Activity.ui.NearBy;

import static android.content.Context.LOCATION_SERVICE;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.ResultReceiver;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.google.maps.android.ui.IconGenerator;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.securepreferences.SecurePreferences;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;
import fatweb.com.restoallergy.Activity.ui.dashboard.DashboardViewModel;
import fatweb.com.restoallergy.DataObject.Restaurant;
import fatweb.com.restoallergy.DataObject.User;
import fatweb.com.restoallergy.R;
import fatweb.com.restoallergy.utils.AppUtils;
import fatweb.com.restoallergy.utils.GMapPlaces;
import fatweb.com.restoallergy.utils.LocationTrackingService;


public class NearByFragment extends Fragment implements OnMapReadyCallback, com.google.android.gms.location.LocationListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private DashboardViewModel dashboardViewModel;
    protected static final int REQUEST_CHECK_SETTINGS = 3;
    boolean isInitialize = false;
    boolean canSearch = false;
    boolean isRateResto = false;
    LatLng lastLocation;
    GoogleMap mMap;
    private LatLng mCenterLatLong;
    private GoogleApiClient mGoogleApiClient;
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    LatLng loc;
    String str_lat, str_lng;
    Intent in;
    private AddressResultReceiver mResultReceiver;
    Context context;
    User thisUser;
    HashMap<Marker, Restaurant> hashMap;
    List<Restaurant> pinnedRestaurants;
    boolean isOpened = false;
    String restaurantEthnicity = "";
    private Geocoder geocoder;

    boolean showSearchButton = false;
    SecurePreferences prefs;
    SupportMapFragment mapView;
    Location userLocation;
    protected String mAddressOutput;
    protected String mAreaOutput;
    protected String mCityOutput;
    protected String mStreetOutput;
    double d_lat,d_lng;
    FrameLayout refreshlayout;
    TextView tvindicator;
    RelativeLayout back;
    int PERMISSION_ID = 44;
    FusedLocationProviderClient mFusedLocationClient;
    String lat, lng,str_lat1,str_lng1;
    @RequiresApi(api = Build.VERSION_CODES.M)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        refreshlayout = root.findViewById(R.id.refreshOverlay);
        tvindicator = root.findViewById(R.id.tvIndicator);
        context = getActivity();
        prefs = new SecurePreferences(context, RestoAllergyApplication.USERPREFERENCES, getString(R.string.app_name), true);
        Gson gson = new Gson();
        String json = prefs.getString(RestoAllergyApplication.thisUser);
        thisUser = gson.fromJson(json, User.class);
        hashMap = new HashMap<>();
        pinnedRestaurants = new ArrayList<>();
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());

        getLastLocation();
         str_lat1=lat;
         str_lng1=lng;
         Log.d("Dta",str_lat1+str_lng1);
       /* getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayUseLogoEnabled(false);*/

        /*mapView = ((SupportMapFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.map));
        mapView.getMapAsync(this);
*/
        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        geocoder = new Geocoder(context, Locale.US);
        mResultReceiver = new AddressResultReceiver(new Handler());
        if (checkPlayServices()) {
            // If this check succeeds, proceed with normal processing.
            // Otherwise, prompt user to get valid Play Services APK.
            if (!AppUtils.isLocationEnabled(context)) {
                // notify user
                android.app.AlertDialog.Builder dialog = new android.app.AlertDialog.Builder(context);
                dialog.setMessage("Location not enabled!");
                dialog.setPositiveButton("Open location settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(myIntent);
                    }
                });
                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        // TODO Auto-generated method stub

                    }
                });
                dialog.show();
            }
            buildGoogleApiClient();
        } else {
            Toast.makeText(context, "Location not supported in this device", Toast.LENGTH_SHORT).show();
        }
        checkProviderEnabled();


        return root;
    }



    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, getActivity(),
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                //finish();
            }
            return false;
        }
        return true;
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onConnected(Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            changeMap(mLastLocation);
            mCenterLatLong = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
            Log.d("HomeFragment", "ON connected");

        } else {
            try {
                LocationServices.FusedLocationApi.removeLocationUpdates(
                        mGoogleApiClient, this);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            LocationRequest mLocationRequest = new LocationRequest();
            mLocationRequest.setInterval(10000);
            mLocationRequest.setFastestInterval(5000);
            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            LocationServices.FusedLocationApi.requestLocationUpdates(
                    mGoogleApiClient, mLocationRequest, this);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onConnectionSuspended(int i) {
        Log.i("homeFragment", "Connection suspended");
        mGoogleApiClient.connect();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d("HomeFragment", "OnMapReady");
        //  setUpList();
        mMap = googleMap;
        mMap.getUiSettings().setMyLocationButtonEnabled(true);


        userLocation = LocationTrackingService.userLocation;

        /* if (userLocation != null) {*/
//            mCenterLatLong = new LatLng(userLocation.getLatitude(), userLocation.getLongitude());
         /*   CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(userLocation.getLatitude(), userLocation.getLongitude())).zoom(16f).build();

            getStreetName(mCenterLatLong);*/
           /* Circle circle = mMap.addCircle(new CircleOptions().center(loc).radius(3000).strokeColor(getResources().getColor(R.color.appcolor)).strokeWidth(1));
            circle.setVisible(true);*/
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(d_lat, d_lng)).zoom(14f).build();

        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

       /* } else {

        }
*/


        mMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                Log.d("Camera position change" + "", cameraPosition + "");
                mCenterLatLong = cameraPosition.target;

                //mMap.clear();

                try {

                    Location mLocation = new Location("dummy");
                    mLocation.setLatitude(mCenterLatLong.latitude);
                    mLocation.setLongitude(mCenterLatLong.longitude);


                    startIntentService(mLocation);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });



    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onLocationChanged(Location location) {


        try {

            if (location != null)
                changeMap(location);
            LocationServices.FusedLocationApi.removeLocationUpdates(
                    mGoogleApiClient, this);
            mCenterLatLong = new LatLng(location.getLatitude(), location.getLongitude());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void changeMap(Location location) {


        Log.d("HomeFragment", "Reaching map" + mMap);


        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling

            return;
        }

        // check if map is created successfully or not
        if (mMap != null) {
            mMap.getUiSettings().setZoomControlsEnabled(false);
            LatLng latLong;

            latLong = new LatLng(location.getLatitude(), location.getLongitude());

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(latLong).zoom(14f).build();
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
//            mMap.animateCamera(CameraUpdateFactory
//                    .newCameraPosition(cameraPosition));

            // mLocationMarkerText.setText("Lat : " + location.getLatitude() + "," + "Long : " + location.getLongitude());
            startIntentService(location);


        } else {
            Toast.makeText(context,
                    "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                    .show();
        }

    }


    class AddressResultReceiver extends ResultReceiver {
        public AddressResultReceiver(Handler handler) {
            super(handler);
        }

        /**
         * Receives data sent from FetchAddressIntentService and updates the UI in MainActivity.
         */
        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            resultData.getString(AppUtils.LocationConstants.LOCATION_DATA_EXTRA);
            resultData.getString(AppUtils.LocationConstants.LOCATION_DATA_AREA);
            resultData.getString(AppUtils.LocationConstants.LOCATION_DATA_CITY);
            resultData.getString(AppUtils.LocationConstants.LOCATION_DATA_STREET);
            mAreaOutput = resultData.getString(AppUtils.LocationConstants.LOCATION_DATA_AREA);

            mCityOutput = resultData.getString(AppUtils.LocationConstants.LOCATION_DATA_CITY);
            mStreetOutput = resultData.getString(AppUtils.LocationConstants.LOCATION_DATA_STREET);

            mAddressOutput = mStreetOutput + ", " + mAreaOutput + " " + mCityOutput;

            // Show a toast message if an address was found.
            if (resultCode == AppUtils.LocationConstants.SUCCESS_RESULT) {

            }

        }

    }

    protected void startIntentService(Location mLocation) {
        // Create an intent for passing to the intent service responsible for fetching the address.
        Intent intent = new Intent(getActivity(), FetchAddressIntentService.class);

        // Pass the result receiver as an extra to the service.
        intent.putExtra(AppUtils.LocationConstants.RECEIVER, mResultReceiver);

        // Pass the location data as an extra to the service.
        intent.putExtra(AppUtils.LocationConstants.LOCATION_DATA_EXTRA, mLocation);

        // Start the service. If the service isn't already running, it is instantiated and started
        // (creating a process for it if needed); if it is running then it remains running. The
        // service kills itself automatically once all intents are processed.
        getActivity().startService(intent);
    }




    private class ParserTask extends AsyncTask<String, Integer, List<HashMap<String, String>>> {

        JSONObject jObject;
        String token;

        int radius;
        double longitude, latitude;
        String type;

        ParserTask() {

        }

        ParserTask(int radius,  double latitude,double longitude, String type) {
            this.radius = radius;
            this.longitude = longitude;
            this.latitude = latitude;
            this.type = type;
        }

        // Invoked by execute() method of this object
        @Override
        protected List<HashMap<String, String>> doInBackground(String... jsonData) {

            List<HashMap<String, String>> places = null;
            GMapPlaces placeJson = new GMapPlaces();

            try {
                jObject = new JSONObject(jsonData[0]);

                places = placeJson.parse(jObject);
                token = placeJson.getPageToken();

            } catch (Exception e) {
                Log.d("Exception", e.toString());
            }
            return places;
        }

        // Executed after the complete execution of doInBackground() method
        @Override
        protected void onPostExecute(final List<HashMap<String, String>> list) {
//            Log.d("Map", "list size: " + list.size());

            // mMap.clear();

            try {


                if (list.size() > 0) {
//                if(!isRateResto){
                    if (!isInitialize) {
                        showSearchButton = true;
                        getActivity().invalidateOptionsMenu();
                    }

                    // }

                } else {
                   /* if (svSearch.getText().toString().isEmpty()) {
                        Msg msg = new Msg();
                        msg.SnackbarMessage("No results", etSearchByLocation);
                    } else {
                        new MaterialDialog.Builder(context)
                                .content("The restaurant you are trying to rate cannot be found. Click ok to let us know.")
                                .positiveText("Ok")
                                .negativeText("Cancel")
                                .onPositive(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        dialog.dismiss();
//                                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//                                    fragmentTransaction.replace(R.id.main,new RestaurantFragment());
//                                    fragmentTransaction.commit();
                                        Intent i = new Intent(context, ContactUsActivity.class);
                                        i.putExtra("restoname", svSearch.getText().toString());
                                        i.putExtra("address", etSearchByLocation.getText().toString());
                                        startActivity(i);
                                    }
                                })
                                .onNegative(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                    }
                                }).show();
                    }*/
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }


            for (int i = 0; i < list.size(); i++) {
                MarkerOptions markerOptions = new MarkerOptions();
                final HashMap<String, String> hmPlace = list.get(i);


                double lat = Double.parseDouble(hmPlace.get("lat"));
                double lng = Double.parseDouble(hmPlace.get("lng"));

                String name = hmPlace.get("place_name");
                Log.d("Map", "place: " + name + " " + hmPlace.get("id"));
                String vicinity = hmPlace.get("vicinity");
                final LatLng latLng = new LatLng(lat, lng);
                markerOptions.position(latLng);
                markerOptions.title(name);
                // markerOptions.snippet(hmPlace.ge)
                IconGenerator icg = new IconGenerator(context);
                // icg.setTextAppearance(R.style.iconGenText);
                Restaurant r = new Restaurant();
                if (hmPlace.get("scope").equalsIgnoreCase("")) {
                    r.setFirstName("No");
                    r.setLastName("Owner");
                    r.setNumOfUnits("1");
                    r.setPosition("Manager");
                    r.setAddress(hmPlace.get("vicinity").toString());
                    r.setWebsite(hmPlace.get("website").toString());
                    r.setPhone("000 0000");
                    r.setCompany(hmPlace.get("place_name").toString());
                    r.setRestoName(hmPlace.get("place_name").toString());
                    r.setPlaceId(hmPlace.get("placeId").toString());
                    r.setId(hmPlace.get("id").toString());
                    r.setLongitude(hmPlace.get("lng"));
                    r.setLatitude(hmPlace.get("lat"));
                    r.setRating(hmPlace.get("rating"));


                    if (!r.getRating().isEmpty()) {
                        if (!r.getRating().isEmpty()) {
                            if (Float.valueOf(r.getRating()) < 3.5) {
                                icg.setBackground(getResources().getDrawable(R.drawable.ic_marker_red_small));
                            } else if (Float.valueOf(r.getRating()) < 4 && Float.valueOf(r.getRating()) >= 3.5) {
                                icg.setBackground(getResources().getDrawable(R.drawable.ic_marker_red_small));
                            } else if (Float.valueOf(r.getRating()) <= 5 && Float.valueOf(r.getRating()) >= 4) {
                                icg.setBackground(getResources().getDrawable(R.drawable.ic_marker_red_small));
                            }
                        } else {
                            icg.setBackground(getResources().getDrawable(R.drawable.ic_marker_red_small));
                        }
                    } else {
                        icg.setBackground(getResources().getDrawable(R.drawable.ic_marker_red_small));
                    }


                    Bitmap bm = icg.makeIcon(r.getRating().isEmpty() ? "0" : r.getRating());
                    markerOptions.icon(BitmapDescriptorFactory.fromBitmap(bm));
                } else {
                    // icg.setTextAppearance(R.style.iconGenText);
                    icg.setBackground(getResources().getDrawable(R.drawable.ic_marker_red_small));

                    r.setFirstName("No");
                    r.setLastName("Owner");
                    r.setNumOfUnits("1");
                    r.setPosition("Manager");
                    r.setAddress(hmPlace.get("vicinity").toString());
                    r.setWebsite(hmPlace.get("website").toString());
                    r.setPhone("000 0000");
                    r.setCompany(hmPlace.get("place_name").toString());
                    r.setRestoName(hmPlace.get("place_name").toString());
                    r.setPlaceId(hmPlace.get("placeId").toString());
                    r.setId(hmPlace.get("id").toString());
                    r.setLongitude(hmPlace.get("lng"));
                    r.setLatitude(hmPlace.get("lat"));
                    r.setRating("0");

                    Bitmap bm = icg.makeIcon("0");
                    markerOptions.icon(BitmapDescriptorFactory.fromBitmap(bm));
                }

                r.scope = hmPlace.get("scope");
                final Marker m = mMap.addMarker(markerOptions);


                Log.d("types", hmPlace.get("place_name") + " " + " "+hmPlace.get("website"));




                hashMap.put(m, r);
                pinnedRestaurants.add(r);

                CustomInfoWindowAdapter adapter = new CustomInfoWindowAdapter(getActivity(), hashMap);
                mMap.setInfoWindowAdapter(adapter);
                m.showInfoWindow();
                m.hideInfoWindow();


                mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(Marker marker) {
                        Restaurant r = hashMap.get(marker);
                        // Toast.makeText(Findrest_act.this, ""+hashMap, Toast.LENGTH_SHORT).show();
                        Log.e("aaaaaaaaaaaaaaaaa scope", r.scope);
                        if (r.scope.equalsIgnoreCase("from db")) {
                            Intent i = new Intent(context, RestaurantViewActivity.class);
                            i.putExtra("restaurant", new Gson().toJson(r, Restaurant.class));
                            startActivity(i);
                        } else {
                            new AddRestaurant(r).execute();
                        }

                    }
                });
            }

            if (token != null) {
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // new GetMoreResults(token, radius, longitude, latitude, type).executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);

                    }
                }, 2000);

                Log.e("test token", token);
            } else {

            }

            try {
                if (isInitialize) {
                    mMap.clear();
                }
                List<Restaurant> rList = new ArrayList<>();
                rList.addAll(hashMap.values());
                List<String> rString = new ArrayList<>();
                for (Restaurant r : rList) {
                    rString.add(r.getRestoName());
                }
              /*  svSearch.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, rString));
                svSearch.setThreshold(1);
*/
                // loading_view.setVisibility(View.GONE);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }









    private class GoogleAPIGetNearBy extends AsyncTask<Void, Void, Boolean> {
        Boolean success;
        String responseString;
        List<Restaurant> list;
        HashMap<Marker, Restaurant> haspMap;

        int radius;
        double longitude, latitude;


        GoogleAPIGetNearBy(int radius,  double latitude,double longitude) {
            this.radius = radius;
            this.longitude = longitude;
            this.latitude = latitude;
        }


        @Override
        protected void onPreExecute() {
            haspMap = new HashMap<>();
            list = new ArrayList<>();
            refreshlayout.setVisibility(View.VISIBLE);

            tvindicator.setText("Please Wait");
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Void... v) {

            try {

                RequestParams params = new RequestParams();
//                params.put("access_token", prefs.getString(HSApplication.ACCESS_TOKEN));
//                android.util.Log.i("token", prefs.getString(HSApplication.ACCESS_TOKEN));

                // String normalUrl = context.getString(R.string.url_main) + "SearchRestaurant/@/"+etSearchByLocation.getText().toString()+"/@/@";
                if (radius == 100) {
                    radius = 100;
                } else {
                    radius = radius * 1000;
                }
                String normalUrl = context.getString(R.string.url_live) + "get_restaurants_near_by/" + radius + "/" + latitude + "/" + longitude;
                Log.d("more results", normalUrl);
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
                            if (response.has("GoogleAPIGetNearByResult")) {
                                responseString = response.toString();
                                success = true;
                                Log.i("response", response.toString());

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


            Message message = new Message();
            message.obj = responseString;

            ParserTask parserTask = new ParserTask(radius, longitude, latitude, "");
            parserTask.executeOnExecutor(SERIAL_EXECUTOR, responseString);

            refreshlayout.setVisibility(View.GONE);

        }

    }




    @RequiresApi(api = Build.VERSION_CODES.M)
    private void checkProviderEnabled() {

        LocationManager locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                !locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {

            mGoogleApiClient = new GoogleApiClient.Builder(context)
                    .addApi(LocationServices.API)
                    .build();

            mGoogleApiClient.connect();

            settingsRequest();
        } else {
            context.startService(new Intent(context, LocationTrackingService.class));

        }
    }


    private void settingsRequest() {

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(30 * 1000);
        locationRequest.setFastestInterval(5 * 1000);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(@NonNull LocationSettingsResult result) {
                final Status status = result.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:

                        System.out.println("startService");
                        if (mGoogleApiClient != null)
                            mGoogleApiClient.disconnect();
                        break;

                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            status.startResolutionForResult(getActivity(), REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            e.printStackTrace();
                        }
                        break;

                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        if (mGoogleApiClient != null)
                            mGoogleApiClient.disconnect();
                        break;
                }
            }
        });
    }

    public int getZoomLevel(Circle circle) {
        int zoomLevel = 0;
        if (circle != null) {
            double radius = circle.getRadius();
            double scale = radius / 500;
            zoomLevel = (int) (16 - Math.log(scale) / Math.log(2));
        }
        return zoomLevel;
    }

    private void getStreetName(LatLng latLng) {


        List<Address> addresses;
        try {
            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);

            if (!addresses.isEmpty()) {


                String address = addresses.get(0).getAddressLine(0);
                String aCity = addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();
                String subLocality = addresses.get(0).getSubLocality();


                if (address != null) {
                    //svSearch.setText(address);
                    mAddressOutput = address;

                }


                lastLocation = latLng;


            }

        } catch (IOException e) {

        }

    }
    public class AddRestaurant extends AsyncTask<Void, Void, Boolean> {

        Boolean success = false;
        Restaurant r = new Restaurant();
        ProgressDialog pd;

        String responseString;
        AddRestaurant(Restaurant r) {
            this.r = r;

        }

        @Override
        protected void onPreExecute() {

            pd = new ProgressDialog(getActivity());
            pd.setMessage("loading...");
            pd.show();
            super.onPreExecute();

        }

        @Override
        protected Boolean doInBackground(Void... v) {
            // TODO: attempt authentication against a network service.

            try {


                String normalUrl =getActivity().getString(R.string.url_live)+"CreateRestaurant";
                Log.i("add restaurant",normalUrl);

//                r.setFirstName(" ");
//                r.setLastName(" ");
//                r.setNumOfUnits("1");
//                r.setPosition("Manager");
//                r.setAddress(hmPlace.get("vicinity").toString());
//                r.setPhone("000 0000");
//                r.setCompany(hmPlace.get("place_name").toString());
//                r.setRestoName(hmPlace.get("place_name").toString());
//                r.setPlaceId(hmPlace.get("id").toString());

                final JSONObject jObject = new JSONObject();
                jObject.put("place_id",r.getPlaceId());
                jObject.put("first_name","No");
                jObject.put("last_name","Owner");
                jObject.put("number_of_units","1");
                jObject.put("position","Owner");
                String name=r.getCompany();
                String name1=name.replaceAll("'", "");
                jObject.put("company",name1);
                jObject.put("phone",r.getPhone());
                jObject.put("address",r.getAddress());
                jObject.put("longitude",r.getLongitude());
                jObject.put("latitude",r.getLatitude());
                //jObject.put("added_by",thisUser.getId());
                jObject.put("status","A");
                jObject.put("ratings",r.getRating().isEmpty()?"0":r.getRating());






                StringEntity entity = new StringEntity(jObject.toString(), "UTF-8");
                entity.setContentType("application/json");
                entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
                Log.i("test", jObject.toString());

                RestClient.post(getActivity(),normalUrl, entity, new JsonHttpResponseHandler() {


                    // RestClient.post(normalUrl,params, new JsonHttpResponseHandler() {
                    @Override
                    public void onFailure(int statusCode, Header[] headers, String response, Throwable throwable) {
                        super.onFailure(statusCode, headers, responseString, throwable);

                        responseString = response;
                        Log.d("String response error",responseString.toString());
                        if(responseString.replace("\"","").equalsIgnoreCase("success")){
                            success = true;
                        }else success = false;
                    }

                    @SuppressLint("LongLogTag")
                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                        responseString = errorResponse.toString();
                        if(responseString.equalsIgnoreCase("success")){
                            success = true;
                        }else success = false;
                        Log.d("JSONObject response error",errorResponse.toString());
                    }

                    @SuppressLint("LongLogTag")
                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                        Log.d("JSONArray response error",errorResponse.toString());
                        responseString = errorResponse.toString();
                        if(responseString.equalsIgnoreCase("success")){
                            success = true;
                        }else success = false;

                    }

                    @Override
                    public void onSuccess(int statusCode,Header[] headers, String responseString) {
                        super.onSuccess(statusCode, headers, responseString);
                        Log.i("responseString",responseString);
                        success = true;

                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        Log.i("JSONObject response",response.toString());
                        try{
                            //success = true;
                            if(response.has("message")){
                                if(response.getString("message").contains("error")){
                                    if(response.getString("message").contains("server encountered an issue, please try again later") ){
                                        success=false;
                                    }
                                    else if(response.getString("message").contains("Restaurant already exist")){
                                        success =true;
                                        r = new Gson().fromJson(response.toString(),Restaurant.class);
                                    }
                                    else{
                                        success =true;
                                        r = new Gson().fromJson(response.toString(),Restaurant.class);
                                    }
                                }else{
                                    success =true;
                                    r = new Gson().fromJson(response.toString(),Restaurant.class);

                                }

                            }else{
                                success = false;

                            }
                        }catch (Exception ex){
                            ex.printStackTrace();
                        }

                    }
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                        Log.i("JSONArray response",response.toString());
                        success = true;
                    }
                });

                return success;
            } catch (Exception e) {
                return false;
            }
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            try{
                pd.dismiss();
            }catch (Exception ex){
                ex.printStackTrace();
            }
            if (success) {
                Intent i =new Intent(context, RestaurantViewActivity.class);
                i.putExtra("restaurant",new Gson().toJson(r,Restaurant.class));
                startActivity(i);


            } else {
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
                builder.setTitle("error!");
                builder.setMessage("error");
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();

            }
        }


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
                                    Log.d("LOCATION_VALUES",lat+"\n"+lng);

                                    d_lat = Double.parseDouble(lat);
                                    d_lng = Double.parseDouble(lng);
                                    Log.d("LATTITYDE", d_lat + "\n" + lat);
                                    Log.d("LATTITYDE", str_lat + "\n" + lng);

                                    new GoogleAPIGetNearBy(1,d_lat, d_lng).executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
                                    /*latTextView.setText(location.getLatitude()+"");
                                    lonTextView.setText(location.getLongitude()+"");*/
                                    CameraPosition cameraPosition = new CameraPosition.Builder()
                                            .target(new LatLng(d_lat, d_lng)).zoom(14f).build();

                                    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
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
