package fatweb.com.restoallergy.Activity.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.Toast;

import fatweb.com.restoallergy.R;
import fatweb.com.restoallergy.utils.NetworkUtil;

public class Splash_act extends AppCompatActivity {
    ImageView imgSplash;
    String currentVersion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        imgSplash = (ImageView) findViewById(R.id.imgSplash);
        // ActionBar actionBar = getSupportActionBar();

        /*if (actionBar != null) {
            actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbarimg));
            actionBar.setTitle("Health Units");
        }*/
        //getSupportActionBar().hide();
        if (NetworkUtil.getConnectivityStatusString(getApplicationContext()).equals("unavailable")) {
            Toast toast = Toast.makeText(Splash_act.this, "Internet Connection Not Available!", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return;
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i=new Intent(Splash_act.this,
                            Home_act.class);
                    //Intent is used to switch from one activity to another.

                    startActivity(i);
                    //invoke the SecondActivity.

                    finish();
                    //the current activity will get finished.
                }
            }, 3000);
            // forceUpdate();



        }

     /*   if (NetworkUtil.getConnectivityStatusString(getApplicationContext()).equals("unavailable")) {
            Toast toast = Toast.makeText(Splash_act.this, "Internet Connection Not Available!", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return;
        } else {

            forceUpdate();


        }
    }
    public void forceUpdate(){
        PackageManager packageManager = this.getPackageManager();
        PackageInfo packageInfo = null;
        try {
            packageInfo =packageManager.getPackageInfo(getPackageName(),0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        currentVersion = packageInfo.versionName;
        Log.d("CURRENT_VERSION", String.valueOf(currentVersion));
       new GetVersionCode().execute();
    }

    class GetVersionCode extends AsyncTask<Void, String, String> {

        @Override

        protected String doInBackground(Void... voids) {

            String newVersion = null;

            try {
                Document document = Jsoup.connect("https://play.google.com/store/apps/details?id=" + Splash_act.this.getPackageName() + "&hl=en")
                        .timeout(30000)
                        .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                        .referrer("http://www.google.com")
                        .get();
                if (document != null) {
                    Elements element = document.getElementsContainingOwnText("Current Version");
                    for (Element ele : element) {
                        if (ele.siblingElements() != null) {
                            Elements sibElemets = ele.siblingElements();
                            for (Element sibElemet : sibElemets) {
                                newVersion = sibElemet.text();
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return newVersion;

        }


        @Override

        protected void onPostExecute(String onlineVersion) {

            super.onPostExecute(onlineVersion);

            if (onlineVersion != null && !onlineVersion.isEmpty()) {

            }

            Log.d("update", "Current version " + currentVersion + "playstore version " + onlineVersion);

            if (currentVersion.equals(onlineVersion)){

                Thread timer = new Thread() {
                    public void run() {
                        try {
                            Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade);
                            imgSplash.startAnimation(animation);
                            sleep(3000);

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } finally {


                            Intent i = new Intent(Splash_act.this, Home_act.class);
                            startActivity(i);
                            finish();


                        }
                    }
                };
                timer.start();


            }else{
                final Dialog dialog = new Dialog(Splash_act.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.dialog_update);

                Button dialogButton = (Button) dialog.findViewById(R.id.btn_done);
                Button dialogButtonCancel = (Button) dialog.findViewById(R.id.btn_cancel);
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.fatweb.deliveryhubapp"));
                        startActivity(intent);
                        dialog.dismiss();
                    }
                });
                dialogButtonCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Thread timer = new Thread() {
                            public void run() {
                                try {
                                    sleep(1000);

                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                } finally {


                                    Intent i = new Intent(Splash_act.this, Home_act.class);
                                    startActivity(i);
                                    finish();


                                }
                            }
                        };
                        timer.start();

                        dialog.dismiss();
                    }
                });

                dialog.show();
            }

        }


    }*/
    }


}



