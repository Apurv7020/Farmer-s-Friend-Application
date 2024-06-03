package com.teamup.Farm360.AllActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.teamup.app_sync.AppSyncChangeNavigationColor;
import com.teamup.app_sync.AppSyncDirectResponseListenNew;
import com.teamup.app_sync.AppSyncHideTop;
import com.teamup.Farm360.AllAdapters.AppInfoAdapter;
import com.teamup.Farm360.AllModules.Admin;
import com.teamup.Farm360.AllReqs.AppInforReq;
import com.teamup.Farm360.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AppInfo extends AppCompatActivity {

    RecyclerView recyclerview;
    ArrayList<AppInforReq> list;
    EditText search_edt;
    AppInfoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppSyncChangeNavigationColor.change(this);
        AppSyncHideTop.hide(this);
        setContentView(R.layout.activity_app_info);

        search_edt = findViewById(R.id.search_edt);
        recyclerview = findViewById(R.id.recyclerview);
        recyclerview.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, true));
        list = new ArrayList<>();
        adapter = new AppInfoAdapter(list);
        recyclerview.setAdapter(adapter);


        search_edt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = search_edt.getText().toString();
                if (text.length() > 2) {
                    LoadAppInfo(text, "");
                } else {
                    LoadAppInfo("", "");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        LoadAppInfo("", "");
    }

    private void LoadAppInfo(String title, String desc) {
        AppSyncDirectResponseListenNew.getResponseFromUrl(Admin.BASE_URL + "api_appInfo.php?title=" + title, this, new AppSyncDirectResponseListenNew.ResponseListener() {
            @Override
            public void responser(String s) {
                try {
                    list.clear();
                    adapter.notifyDataSetChanged();
                    JSONArray jsonArray = new JSONArray(s);
                    if (jsonArray.length() > 0) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            String id = String.valueOf(obj.getInt("id"));
                            String title = obj.getString("title");
                            String description = obj.getString("description");

                            AppInforReq air = new AppInforReq(id, title, description);
                            list.add(air);
                            adapter.notifyDataSetChanged();


                        }
                    } else {

                    }
                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }
        });
    }
}