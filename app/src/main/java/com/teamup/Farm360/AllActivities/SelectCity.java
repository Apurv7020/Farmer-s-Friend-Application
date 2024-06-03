package com.teamup.Farm360.AllActivities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.teamup.app_sync.AppSyncChangeNavigationColor;
import com.teamup.app_sync.AppSyncDirectResponseListen;
import com.teamup.app_sync.AppSyncHideTop;
import com.teamup.Farm360.AllAdapters.WeatherCityAdapter;
import com.teamup.Farm360.AllModules.TinyDB;
import com.teamup.Farm360.AllReqs.WeatherCityReq;
import com.teamup.Farm360.R;
import com.teamup.app_sync.AppSyncInternetCheck;
import com.teamup.app_sync.AppSyncPleaseWait;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLDecoder;
import java.util.ArrayList;

public class SelectCity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<WeatherCityReq> list;
    WeatherCityAdapter adapter;
    Button searchBtn;
    TinyDB tinyDB;
    EditText searchEdt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppSyncHideTop.hide(this);
        AppSyncChangeNavigationColor.change(this);
        setContentView(R.layout.activity_select_city);
        AppSyncInternetCheck.checkInternet(this);

        tinyDB = new TinyDB(this);

        searchEdt = findViewById(R.id.searchEdt);
        searchBtn = findViewById(R.id.searchBtn);
        recyclerView = findViewById(R.id.recycler);
        list = new ArrayList<>();
        adapter = new WeatherCityAdapter(list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(adapter);

        searchEdt.setText(tinyDB.getString("weatherCity"));

        list.clear();
        adapter.notifyDataSetChanged();

        LoadCity();

        AppSyncPleaseWait.showDialog(SelectCity.this, "Loading..");


        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.clear();
                adapter.notifyDataSetChanged();

                LoadCity();

                AppSyncPleaseWait.showDialog(SelectCity.this, "Loading..");

            }
        });

    }

    private void LoadCity() {

        AppSyncDirectResponseListen as = new AppSyncDirectResponseListen(this);
        as.getResponseFromUrl(new AppSyncDirectResponseListen.ResponseListener() {
            @Override
            public void responser(String response, String datakey) {
                if (datakey.equalsIgnoreCase("H889")) {

                    try {
                        adapter.notifyDataSetChanged();

                        JSONArray resultArray = new JSONArray(response);

                        for (int i = 0; i < resultArray.length(); i++) {
                            try {

                                JSONObject obj = null;
                                try {
                                    obj = resultArray.getJSONObject(i);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                int id = obj.getInt("id");
                                String name = URLDecoder.decode(obj.getString("name"));
                                String region = URLDecoder.decode(obj.getString("region"));
                                String url = URLDecoder.decode(obj.getString("url"));

                                WeatherCityReq nr = new WeatherCityReq(name, region, url);


                                list.add(nr);

                                adapter.notifyDataSetChanged();

                                try {
                                    AppSyncPleaseWait.stopDialog(SelectCity.this);
                                } catch (Exception v) {

                                }

                            } catch (Exception v) {
//                                Admin.showToast(getApplicationContext(), "" + v);
                            }
                        }
                    } catch (Exception v) {
//                        Admin.showToast(getApplicationContext(), "" + v);
                    }


                }
            }
        });
        as.getResponseFromUrlMethod("http://api.weatherapi.com/v1/search.json?key=c3159a0b0d0f477dab7234254201111&q=" + searchEdt.getText().toString(), "H889");
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}