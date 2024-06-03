package com.teamup.Farm360.AllModules;

import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;

import com.teamup.Farm360.AllAdapters.CropsAdapter;
import com.teamup.Farm360.AllReqs.CropsReq;
import com.teamup.app_sync.AppSyncDirectResponseListen;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLDecoder;

public class LoadNovoItems {


    public static void loadBy(Context context, CropsAdapter adapter, ProgressBar prog) {

        AppSyncDirectResponseListen rr = new AppSyncDirectResponseListen(context);
        rr.getResponseFromUrl(new AppSyncDirectResponseListen.ResponseListener() {
            @Override
            public void responser(String response, String datakey) {
                try {
                    if (datakey.equalsIgnoreCase("llo")) {
                        Admin.cropsList.clear();
                        JSONArray jsonArray = new JSONArray(response);
                        if (jsonArray.length() > 0) {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject obj = jsonArray.getJSONObject(i);
                                int id = obj.getInt("id");
                                String name = URLDecoder.decode(obj.getString("name"));
                                String nameMarathi = URLDecoder.decode(obj.getString("nameMarathi"));
                                String imgUrl = URLDecoder.decode(obj.getString("imgUrl"));

                                CropsReq cr = new CropsReq(id, name, nameMarathi, imgUrl);
                                Admin.cropsList.add(cr);
                                if (adapter != null) {
                                    adapter.notifyDataSetChanged();
                                    prog.setVisibility(View.GONE);
                                }

                            }
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        rr.getResponseFromUrlMethod(Admin.BASE_URL + "api_novo_items.php", "llo");
    }
}
