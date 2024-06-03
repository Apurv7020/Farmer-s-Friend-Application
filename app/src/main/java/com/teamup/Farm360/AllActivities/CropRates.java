package com.teamup.Farm360.AllActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;

import com.teamup.app_sync.AppSyncChangeNavigationColor;
import com.teamup.app_sync.AppSyncDirectResponseListen;
import com.teamup.app_sync.AppSyncHideTop;
import com.teamup.Farm360.AllAdapters.CropRateAdater;
import com.teamup.Farm360.AllModules.Admin;
import com.teamup.Farm360.AllReqs.CropRatesReq;
import com.teamup.Farm360.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CropRates extends AppCompatActivity {

    ArrayList<CropRatesReq> list;
    CropRateAdater adater;
    RecyclerView recyclerview;
    SwipeRefreshLayout swiper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppSyncChangeNavigationColor.change(this);
        AppSyncHideTop.hide(this);
        setContentView(R.layout.activity_crop_rates);

        list = new ArrayList<>();
        adater = new CropRateAdater(list);
        recyclerview = findViewById(R.id.recyclerview);
        swiper = findViewById(R.id.swiper);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setAdapter(adater);

        swiper.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                LoadCropRates();
            }
        });

        LoadCropRates();
    }

    private void LoadCropRates() {
        swiper.setRefreshing(true);
        AppSyncDirectResponseListen as = new AppSyncDirectResponseListen(this);
        as.getResponseFromUrl(new AppSyncDirectResponseListen.ResponseListener() {
            @Override
            public void responser(String response, String datakey) {
                if (datakey.equalsIgnoreCase("H889")) {
                    try {
                        list.clear();
                        adater.notifyDataSetChanged();
                        JSONArray jsonArray = new JSONArray(response);
                        if (jsonArray.length() > 0) {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject obj = jsonArray.getJSONObject(i);
                                String id = String.valueOf(obj.getInt("id"));
                                String title = obj.getString("title");
                                String description = obj.getString("description");
                                String rate = obj.getString("rate");
                                String imgUrl = obj.getString("imgUrl");

                                CropRatesReq cr = new CropRatesReq(id, title, description, rate, imgUrl);
                                list.add(cr);
                                adater.notifyDataSetChanged();
                            }

                            swiper.setRefreshing(false);
                        } else {
//                            Empty
                            swiper.setRefreshing(false);
                        }
                    } catch (JSONException e) {
                        swiper.setRefreshing(false);
                        e.printStackTrace();
                    }
                }
            }
        });
        as.getResponseFromUrlMethod(Admin.BASE_URL + "api_CropRates.php", "H889");
    }
}