package com.teamup.Farm360.AllActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;

import com.teamup.app_sync.AppSyncChangeNavigationColor;
import com.teamup.app_sync.AppSyncDirectResponseListen;
import com.teamup.app_sync.AppSyncHideTop;
import com.teamup.app_sync.AppSyncToast;
import com.teamup.Farm360.AllAdapters.HarvestingAdapter;
import com.teamup.Farm360.AllModules.Admin;
import com.teamup.Farm360.AllReqs.HarvestingReq;
import com.teamup.Farm360.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HarvestingTechs extends AppCompatActivity {

    ArrayList<HarvestingReq> list;
    RecyclerView recyclerview;
    HarvestingAdapter adapter;
    SwipeRefreshLayout swiper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppSyncChangeNavigationColor.change(this);
        AppSyncHideTop.hide(this);
        setContentView(R.layout.activity_harvesting_techs);

        swiper = findViewById(R.id.swiper);
        recyclerview = findViewById(R.id.recyclerview);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        adapter = new HarvestingAdapter(list);
        recyclerview.setAdapter(adapter);

        swiper.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                LoadTechs();
            }
        });

        LoadTechs();
    }

    private void LoadTechs() {

        swiper.setRefreshing(true);
        AppSyncDirectResponseListen as = new AppSyncDirectResponseListen(this);
        as.getResponseFromUrl(new AppSyncDirectResponseListen.ResponseListener() {
            @Override
            public void responser(String response, String datakey) {
                if (datakey.equalsIgnoreCase("H889")) {
                    try {
                        list.clear();
                        adapter.notifyDataSetChanged();
                        JSONArray jsonArray = new JSONArray(response);
                        if (jsonArray.length() > 0) {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject obj = jsonArray.getJSONObject(i);
                                String id = String.valueOf(obj.getInt("id"));
                                String title = obj.getString("title");
                                String description = obj.getString("description");
                                String imgUrl = obj.getString("imgUrl");
                                String link = obj.getString("link");

                                HarvestingReq hr = new HarvestingReq(id, title, description, imgUrl, link);
                                list.add(hr);
                                adapter.notifyDataSetChanged();
                            }
                            swiper.setRefreshing(false);
                        } else {
//                            Empty
                            AppSyncToast.showToast(getApplicationContext(), "Nothing to show");
                            swiper.setRefreshing(false);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        swiper.setRefreshing(false);
                    }
                }
            }
        });
        as.getResponseFromUrlMethod(Admin.BASE_URL + "api_harvesting.php", "H889");
    }
}