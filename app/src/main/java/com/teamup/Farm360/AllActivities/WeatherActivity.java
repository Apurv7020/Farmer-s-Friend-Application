package com.teamup.Farm360.AllActivities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.teamup.app_sync.AppSyncChangeNavigationColor;
import com.teamup.app_sync.AppSyncDirectResponseListen;
import com.teamup.app_sync.AppSyncHideTop;
import com.teamup.Farm360.AllAdapters.WeatherAdapter;
import com.teamup.Farm360.AllModules.TinyDB;
import com.teamup.Farm360.AllReqs.WeatherReq;
import com.teamup.Farm360.R;
import com.teamup.app_sync.AppSyncPleaseWait;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;

public class WeatherActivity extends AppCompatActivity {


    TinyDB tinyDB;
    TextView selectCityTxt, humidityTxt, windTxt, sunriseTxt, sunsetTxt;

    RecyclerView recyclerView;
    ArrayList<WeatherReq> list;
    WeatherAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppSyncChangeNavigationColor.change(this);
        AppSyncHideTop.hide(this);
        setContentView(R.layout.activity_weather);

        tinyDB = new TinyDB(this);

        selectCityTxt = findViewById(R.id.selectCityTxt);
        humidityTxt = findViewById(R.id.humidityTxt);
        windTxt = findViewById(R.id.windTxt);
        sunriseTxt = findViewById(R.id.sunriseTxt);
        sunsetTxt = findViewById(R.id.sunsetTxt);

        selectCityTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WeatherActivity.this, SelectCity.class));
            }
        });

        recyclerView = findViewById(R.id.recycler);
        list = new ArrayList<>();
        adapter = new WeatherAdapter(list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(adapter);


    }

    private void LoadData() {
        AppSyncPleaseWait.showDialog(this, "Loading..");
        AppSyncDirectResponseListen as = new AppSyncDirectResponseListen(this);
        as.getResponseFromUrl(new AppSyncDirectResponseListen.ResponseListener() {
            @Override
            public void responser(String response, String datakey) {
                if (datakey.equalsIgnoreCase("H8891")) {

                    try {
                        JSONObject json = new JSONObject("" + response);
                        JSONObject jsonResponse = json.getJSONObject("forecast");
                        JSONArray ja_data = jsonResponse.getJSONArray("forecastday");

                        String humidity = json.getJSONObject("current").getString("humidity");
                        humidityTxt.setText("" + humidity);
                        String wind_kph = json.getJSONObject("current").getString("wind_kph");
                        windTxt.setText("" + wind_kph);


                        try {
                            JSONObject sunriseObj = ja_data.getJSONObject(0);
                            sunriseTxt.setText("" + sunriseObj.getJSONObject("astro").getString("sunrise"));
                            sunsetTxt.setText("" + sunriseObj.getJSONObject("astro").getString("sunset"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        for (int i = 0; i < ja_data.length(); i++) {
                            JSONObject jOBJ = ja_data.getJSONObject(i);
                            JSONArray jArray = jOBJ.getJSONArray("hour");
                            for (int j = 0; j < jArray.length(); j++) {
                                JSONObject jOBJNEW = jArray.getJSONObject(j);
                                String temp_c = jOBJNEW.getString("temp_c");
                                String time = jOBJNEW.getString("time");

                                String icon = null;
                                String text = "";
                                try {

                                    JSONObject objectDetails2 = jOBJNEW.getJSONObject("condition");
                                    icon = "http:" + objectDetails2.getString("icon");
                                    text = objectDetails2.getString("text");

//                                    Toast.makeText(WeatherActivity.this, "" + icon, Toast.LENGTH_SHORT).show();

                                } catch (Exception v) {
                                    Toast.makeText(WeatherActivity.this, "" + v, Toast.LENGTH_SHORT).show();
                                }

                                WeatherReq wr = new WeatherReq(temp_c, time, icon, text);
                                list.add(wr);
                                adapter.notifyDataSetChanged();
                            }
                        }


                        try {
                            AppSyncPleaseWait.stopDialog(WeatherActivity.this);
                        } catch (Exception v) {

                        }


                    } catch (Exception v) {
                        try {
                            AppSyncPleaseWait.stopDialog(WeatherActivity.this);
                        } catch (Exception sv) {

                        }

//                        Toast.makeText(WeatherActivity.this, ""+v, Toast.LENGTH_SHORT).show();
                    }


                }
            }
        });
        as.getResponseFromUrlMethod("https://api.weatherapi.com/v1/forecast.json?key=c3159a0b0d0f477dab7234254201111&q=" + URLEncoder.encode(tinyDB.getString("weatherCity")) + "&days=1", "H8891");
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (TextUtils.isEmpty(tinyDB.getString("weatherCity"))) {
            tinyDB.putString("weatherCity", "Pune");
        }

        selectCityTxt.setText(tinyDB.getString("weatherCity"));
        list.clear();
        adapter.notifyDataSetChanged();
        LoadData();

    }
}