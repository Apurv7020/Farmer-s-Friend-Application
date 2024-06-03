package com.teamup.Farm360.AllActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.teamup.app_sync.AppSyncChangeNavigationColor;
import com.teamup.app_sync.AppSyncDirectResponseListen;
import com.teamup.app_sync.AppSyncHideTop;
import com.teamup.app_sync.AppSyncToast;
import com.teamup.Farm360.AllAdapters.CityAdapter;
import com.teamup.Farm360.AllAdapters.NearbyAdapter;
import com.teamup.Farm360.AllModules.Admin;
import com.teamup.Farm360.AllReqs.CityReq;
import com.teamup.Farm360.AllReqs.NearbyReq;
import com.teamup.Farm360.R;
import com.teamup.app_sync.AppSyncBottomSheetDialog;
import com.teamup.app_sync.AppSyncDirectResponseListen;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NearbyActivity extends AppCompatActivity {

    ArrayList<NearbyReq> list;
    RecyclerView recyclerview;
    NearbyAdapter adapter;
    TextView cityTxt, headTxt;
    SwipeRefreshLayout swiper;
    String selectedCity = "Pune";
    public static ArrayList<CityReq> citylist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppSyncChangeNavigationColor.change(this);
        AppSyncHideTop.hide(this);
        setContentView(R.layout.activity_nearby);

        list = new ArrayList<>();
        adapter = new NearbyAdapter(list);
        headTxt = findViewById(R.id.headTxt);
        headTxt.setText("" + Admin.headTxt);
        recyclerview = findViewById(R.id.recyclerview);
        swiper = findViewById(R.id.swiper);
        recyclerview.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerview.setAdapter(adapter);
        cityTxt = findViewById(R.id.cityTxt);
        cityTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HandleCitySelector();
            }
        });

        swiper.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                LoadNearby();
            }
        });


        LoadNearby();


    }

    private void HandleCitySelector() {
        AppSyncBottomSheetDialog.showSquared(this, R.layout.bottom_city, false);
        View vv = AppSyncBottomSheetDialog.view2;
        Button closeBtn = vv.findViewById(R.id.closeBtn);
        TextView comodityHeadTxt = vv.findViewById(R.id.comodityHeadTxt);
        Button proceedBtn = vv.findViewById(R.id.proceedBtn);
        RecyclerView recycler = vv.findViewById(R.id.recycler);
        citylist = new ArrayList<>();
        CityAdapter adapter = new CityAdapter(citylist);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(adapter);
        ShimmerFrameLayout shimmer = vv.findViewById(R.id.shimmer);
        shimmer.startShimmerAnimation();
        shimmer.setVisibility(View.VISIBLE);

        recycler.setVisibility(View.GONE);

        AppSyncDirectResponseListen rr = new AppSyncDirectResponseListen(this);
        rr.getResponseFromUrl(new AppSyncDirectResponseListen.ResponseListener() {
            @Override
            public void responser(String response, String datakey) {
                if (datakey.equalsIgnoreCase("ghy")) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        if (jsonArray.length() > 0) {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject obj = jsonArray.getJSONObject(i);
                                int id = obj.getInt("id");
                                String title = obj.getString("title");


                                CityReq cr = new CityReq(id, title, false);
                                citylist.add(cr);
                                adapter.notifyDataSetChanged();
                            }
                            shimmer.stopShimmerAnimation();
                            shimmer.setVisibility(View.GONE);
                            recycler.setVisibility(View.VISIBLE);
                            comodityHeadTxt.setText("City (" + list.size() + ")");
                        } else {
                            recycler.setVisibility(View.GONE);
                            shimmer.stopShimmerAnimation();
                            shimmer.setVisibility(View.GONE);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        recycler.setVisibility(View.GONE);
                        shimmer.stopShimmerAnimation();
                        shimmer.setVisibility(View.GONE);
                    }
                }
            }
        });
        rr.getResponseFromUrlMethod(Admin.BASE_URL + "api_city.php", "ghy");

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedCity = "Select";
                AppSyncBottomSheetDialog.dismiss(NearbyActivity.this);
                try {
                    cityTxt.setText("" + selectedCity);
                } catch (Exception e) {
                    cityTxt.setText("select");
                    e.printStackTrace();
                }
                LoadNearby();
            }
        });


        proceedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedCity = Admin.slectedCityA;
                AppSyncBottomSheetDialog.dismiss(NearbyActivity.this);
                cityTxt.setText("" + selectedCity);
                LoadNearby();
            }
        });

    }

    public static void makeAllFalse() {
        for (int y = 0; y < citylist.size(); y++) {
            citylist.get(y).setSelected(false);
        }
    }


    private void LoadNearby() {
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
                                String cat = obj.getString("cat");
                                String location = obj.getString("location");
                                String city = obj.getString("city");

                                NearbyReq nr = new NearbyReq(id, title, description, cat, location);
                                list.add(nr);
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
        as.getResponseFromUrlMethod(Admin.BASE_URL + "api_nearby.php?cat=" + Admin.domain + "&city=" + selectedCity, "H889");
    }
}