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
import com.teamup.Farm360.AllAdapters.GovtSchemeAdapter;
import com.teamup.Farm360.AllModules.Admin;
import com.teamup.Farm360.AllReqs.GovtSchemesReq;
import com.teamup.Farm360.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GovtSchemes extends AppCompatActivity {

    RecyclerView recyclerview;
    ArrayList<GovtSchemesReq> list;
    GovtSchemeAdapter adapter;
    SwipeRefreshLayout swiper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppSyncHideTop.hide(this);
        AppSyncChangeNavigationColor.change(this);
        setContentView(R.layout.activity_govt_schemes);

        swiper = findViewById(R.id.swiper);
        recyclerview = findViewById(R.id.recyclerview);
        list = new ArrayList<>();
        adapter = new GovtSchemeAdapter(list);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setAdapter(adapter);

        swiper.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                LoadSchemes();
            }
        });
        LoadSchemes();

    }

    private void LoadSchemes() {
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
                                String link = obj.getString("link");

                                GovtSchemesReq gr = new GovtSchemesReq(id, title, description, link);
                                list.add(gr);
                                adapter.notifyDataSetChanged();


                            }
                            swiper.setRefreshing(false);
                        } else {
//                            Empty
                            swiper.setRefreshing(false);
                            AppSyncToast.showToast(getApplicationContext(), "Nothing to show");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        swiper.setRefreshing(false);
                    }
                }
            }
        });
        as.getResponseFromUrlMethod(Admin.BASE_URL + "api_govt_schemes.php", "H889");
    }
}