package fatweb.com.restoallergy.Activity.ui.notifications;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.fatweb.allergysafenz.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    FrameLayout refreshlayout;
    TextView tvindicator, txt_addres/*, txt_heading,*/, txt_description;
    String str_heading, str_description;
    LinearLayout back;
    WebView webview;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.activity_allergy_guide, container, false);
        refreshlayout = root.findViewById(R.id.refreshOverlay);
        tvindicator = root.findViewById(R.id.tvIndicator);
/*
        txt_heading = findViewById(R.id.txt_heading);
*/
        txt_description = root.findViewById(R.id.txt_descr);
        webview = root.findViewById(R.id.webview);
        webview.setBackgroundColor(Color.TRANSPARENT);
        back = root.findViewById(R.id.back);
        back.setVisibility(View.INVISIBLE);

       // new GetAllergyGuide().execute();

        return root;
    }


    class GetAllergyGuide extends AsyncTask<Void, Void, String> {


        @Override
        protected void onPreExecute() {
            refreshlayout.setVisibility(View.VISIBLE);

            tvindicator.setText("Please Wait");
            super.onPreExecute();
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            refreshlayout.setVisibility(View.GONE);
            //   Toast.makeText(getApplicationContext(), "sas" + s, Toast.LENGTH_SHORT).show();
            Log.d("RESPONSE", s);
            try {
                JSONObject emp = new JSONObject(s);

                str_description = emp.getString("description");

                /*    txt_heading.setText(str_heading);*/
                // txt_description.setText(str_description);


                webview.getSettings().setJavaScriptEnabled(true);
                webview.loadDataWithBaseURL("", emp.getString("description"), "text/html", "UTF-8", "");


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //in this method we are fetching the json string
        @Override
        protected String doInBackground(Void... voids) {


            try {
                //creating a URL

                URL url = new URL( /*getString(R.string.url_live) +*/"https://oldonestoptrade.dev001.fatweb.co.nz/allergysafe/allegy_guides/1");
                Log.d("GEt Detail url", String.valueOf(url));
                //Opening the URL using HttpURLConnection
                HttpURLConnection con = (HttpURLConnection) url.openConnection();

                //StringBuilder object to read the string from the service
                StringBuilder sb = new StringBuilder();

                //We will use a buffered reader to read the string from service
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                //A simple string to read values from each line
                String json;

                //reading until we don't find null
                while ((json = bufferedReader.readLine()) != null) {

                    //appending it to string builder
                    sb.append(json + "\n");
                }

                //finally returning the read string
                return sb.toString().trim();
            } catch (Exception e) {
                return null;
            }

        }
    }

}
