package com.teamup.Farm360.AllActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.teamup.app_sync.AppSyncChangeNavigationColor;
import com.teamup.app_sync.AppSyncHideTop;
import com.teamup.app_sync.AppSyncInitialize;
import com.teamup.app_sync.AppSyncPermissions;
import com.teamup.app_sync.AppSyncToast;
import com.teamup.Farm360.AllActivities.DiseaseDetectFolder.TestKolin1;
import com.teamup.Farm360.Webview.WebMainActivity;
import com.teamup.Farm360.dialogflowbot.ChatMainActivity;
import com.teamup.Farm360.AllActivities.NearByFolder.MapsActivity;
import com.teamup.Farm360.AllModules.Admin;
import com.teamup.Farm360.AllModules.LoadNovoItems;
import com.teamup.Farm360.AllModules.TinyDB;
import com.teamup.Farm360.R;
import com.teamup.app_sync.AppSyncPleaseWait;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    TinyDB tinyDB;
    TextView weatherTxt, cityTxt;
    ExtendedFloatingActionButton fab2;
    ImageView cloudImg, logoutImg;
    public static CardView crop_rates_card, govt_card, harvesting_tech_card, nearby_krushi_seva_card, soil_and_water_card, weather_forecast_card,
            farmers_wallet_card, app_info_card, weatherCard, agri_calendar_card, disease_detectCard, nearbymaps_card, yieldprediction1 ;
    static String ServerURL1 = Admin.BASE_URL + "make_it_query.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppSyncHideTop.hide(this);
        AppSyncChangeNavigationColor.change(this);
        setContentView(R.layout.activity_main);
        AppSyncInitialize.init(this);
        tinyDB = new TinyDB(this);
        Admin.initViews(this);

        AppSyncPermissions.CAMERA_PERMISSION(this, 54);

        fab2 = findViewById(R.id.fab2);
        cityTxt = findViewById(R.id.cityTxt);
        logoutImg = findViewById(R.id.logoutImg);
        cloudImg = findViewById(R.id.cloudImg);
        weatherTxt = findViewById(R.id.weatherTxt);
        weatherCard = findViewById(R.id.weatherCard);
        weatherCard.setVisibility(View.GONE);
        if (TextUtils.isEmpty(tinyDB.getString("weatherCity"))) {
            tinyDB.putString("weatherCity", "Pune");
        }
        weathering();

        app_info_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AppInfo.class));
                Admin.overrriredTrans(MainActivity.this);
            }
        });

          fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ChatMainActivity.class));
                Admin.overrriredTrans(MainActivity.this);
            }
        });


        nearbymaps_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MapsActivity.class));
                Admin.overrriredTrans(MainActivity.this);
            }
        });
        disease_detectCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TestKolin1.class));
                Admin.overrriredTrans(MainActivity.this);
            }
        });

        yieldprediction1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, WebMainActivity.class));
                Admin.overrriredTrans(MainActivity.this);
            }
        });


        crop_rates_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CropRates.class));
                Admin.overrriredTrans(MainActivity.this);
            }
        });
        agri_calendar_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AgriCalendar.class));
                Admin.overrriredTrans(MainActivity.this);
            }
        });
        govt_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, GovtSchemes.class));
                Admin.overrriredTrans(MainActivity.this);
            }
        });
        farmers_wallet_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, KhataBook.class));
                Admin.overrriredTrans(MainActivity.this);
            }
        });
        harvesting_tech_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, HarvestingTechs.class));
                Admin.overrriredTrans(MainActivity.this);
            }
        });
        weatherCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, WeatherActivity.class));
                Admin.overrriredTrans(MainActivity.this);
            }
        });
        nearby_krushi_seva_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Admin.headTxt = "Krushi Seva Kendra";
                Admin.domain = "Krushi";
                startActivity(new Intent(MainActivity.this, NearbyActivity.class));
                Admin.overrriredTrans(MainActivity.this);
            }
        });
        soil_and_water_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Admin.headTxt = "Soil & Water Testing Labs";
                Admin.domain = "Lab";
                startActivity(new Intent(MainActivity.this, NearbyActivity.class));
                Admin.overrriredTrans(MainActivity.this);
            }
        });
        weather_forecast_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this, WeatherActivity.class));
                Admin.overrriredTrans(MainActivity.this);
            }
        });

        LoadNovoItems.loadBy(this, null, null);

        ReactiveNetwork.observeInternetConnectivity()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(isConnectedToInternet -> {
                    // do something with isConnectedToInternet value

                    if (!isConnectedToInternet) {
                        AppSyncToast.showToast(getApplicationContext(), "No Internet Connection");
                    }

                });


        if (!tinyDB.getBoolean("login")) {
            finishAffinity();
            startActivity(new Intent(MainActivity.this, LoginPage.class));
            Admin.overrriredTrans(MainActivity.this);
        }

        logoutImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tinyDB.clear();
                finishAffinity();
                startActivity(new Intent(MainActivity.this, MainActivity.class));
                Admin.overrriredTrans(MainActivity.this);
            }
        });
    }

    public static void makeItQuery(final String query, final Context context) {
        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                nameValuePairs.add(new BasicNameValuePair("query", query));
                try {
                    HttpClient httpClient = new DefaultHttpClient();

                    HttpPost httpPost = new HttpPost(ServerURL1);

                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                    HttpResponse httpResponse = httpClient.execute(httpPost);
                    httpResponse.setHeader("Content-Type", "text/html; charset=utf-8");

                    HttpEntity httpEntity = httpResponse.getEntity();


                } catch (ClientProtocolException e) {
                    Log.e("L151", e.getMessage());
                } catch (IOException e) {
                    Log.e("L151", e.getMessage());
                }
                return "Data Inserted Successfully";
            }

            @Override
            protected void onPostExecute(String result) {

                super.onPostExecute(result);

                try {
                } catch (Exception v) {
                    Log.e("L151", v.getMessage());
                }
            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(query);
    }

    private void weathering() {
        new Admin.FetchWeather().execute("http://api.weatherapi.com/v1/current.json?key=c3159a0b0d0f477dab7234254201111&q=" + tinyDB.getString("weatherCity"));

        Admin.resultArrayOfWeather = null;
        Admin.listenWeather.observe(MainActivity.this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (Admin.response != null) {
                    try {
                        JSONObject json = new JSONObject("" + Admin.response);
                        JSONObject jsonResponse = json.getJSONObject("current");
                        String team = jsonResponse.getString("temp_c");
                        weatherTxt.setText(team + "\u2103");

                        JSONObject objectDetails2 = jsonResponse.getJSONObject("condition");
                        String icon = objectDetails2.getString("icon");
                        Glide.with(getApplicationContext()).load("http://" + icon).into(cloudImg);

                        if (team != null) {
                            weatherCard.setVisibility(View.VISIBLE);
                        }

                    } catch (Exception v) {

                    }


                } else {
                    try {
                        AppSyncPleaseWait.stopDialog(MainActivity.this);
                    } catch (Exception v) {

                    }

                }

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!weatherTxt.getText().toString().equalsIgnoreCase(tinyDB.getString("weatherCity"))) {
            cityTxt.setText(tinyDB.getString("weatherCity"));
            weathering();
        }
    }
}