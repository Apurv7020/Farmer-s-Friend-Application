package com.teamup.Farm360.AllActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.teamup.app_sync.AppSyncBottomSheetDialog;
import com.teamup.app_sync.AppSyncChangeNavigationColor;
import com.teamup.app_sync.AppSyncCurrentDate;
import com.teamup.app_sync.AppSyncDirectResponseListenNew;
import com.teamup.app_sync.AppSyncHideTop;
import com.teamup.app_sync.AppSyncToast;
import com.teamup.Farm360.AllAdapters.ChecklistAdapter;
import com.teamup.Farm360.AllModules.Admin;
import com.teamup.Farm360.AllModules.TinyDB;
import com.teamup.Farm360.AllReqs.ChecklistReq;
import com.teamup.Farm360.R;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

public class AgriCalendar extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    FloatingActionButton addBtn;
    RecyclerView recyclerview;
    TinyDB tinyDB;
    ArrayList<ChecklistReq> list;
    ChecklistAdapter adapter;
    TextView dateTxt;
    SwipeRefreshLayout swiper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppSyncChangeNavigationColor.change(this);
        AppSyncHideTop.hide(this);
        setContentView(R.layout.activity_agri_calendar);

        tinyDB = new TinyDB(this);


        list = new ArrayList<>();
        adapter = new ChecklistAdapter(list);
        swiper = findViewById(R.id.swiper);
        recyclerview = findViewById(R.id.recyclerview);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setAdapter(adapter);
        addBtn = findViewById(R.id.addBtn);

        swiper.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                LoadChecklist();
            }
        });
        LoadChecklist();


        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HandleChecklist();
            }
        });

    }

    private void HandleChecklist() {
        AppSyncBottomSheetDialog.showRounded(this, R.layout.bottom_checklist_add, true);
        View vv = AppSyncBottomSheetDialog.view2;
        Button btn = vv.findViewById(R.id.btn);
        EditText edt_title = vv.findViewById(R.id.edt_title);
        dateTxt = vv.findViewById(R.id.dateTxt);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = edt_title.getText().toString();
                if (!TextUtils.isEmpty(title)) {
                    MainActivity.makeItQuery("INSERT INTO `Checklist` (`id`, `title`, `date`, `email`, `checked`) VALUES" +
                            " (NULL, '" + title + "', '" + dateTxt.getText().toString() + "', '" + tinyDB.getString("email") + "', '0');", getApplicationContext());
                    AppSyncBottomSheetDialog.dismiss(AgriCalendar.this);
                    LoadChecklist();
                } else {
                    AppSyncToast.showToast(getApplicationContext(), "Please enter title");
                }
            }
        });

        dateTxt.setText("" + AppSyncCurrentDate.getDateTimeInFormat("dd/MM/yyyy"));

        dateTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        AgriCalendar.this,
                        now.get(Calendar.YEAR), // Initial year selection
                        now.get(Calendar.MONTH), // Initial month selection
                        now.get(Calendar.DAY_OF_MONTH) // Inital day selection
                );
                dpd.show(getSupportFragmentManager(), "ds");
            }
        });
    }

    private void LoadChecklist() {
        swiper.setRefreshing(true);
        AppSyncDirectResponseListenNew.getResponseFromUrl(Admin.BASE_URL + "api_checklist.php?email=" + tinyDB.getString("email"), this, new AppSyncDirectResponseListenNew.ResponseListener() {
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
                            String date = obj.getString("date");
                            String email = obj.getString("email");
                            int checked = obj.getInt("checked");

                            ChecklistReq cr = new ChecklistReq(id, title, date, email, checked);
                            list.add(cr);
                            adapter.notifyDataSetChanged();
                            swiper.setRefreshing(false);

                        }
                    } else {
//                        Empty
                        swiper.setRefreshing(false);
                    }
                } catch (JSONException e) {
                    swiper.setRefreshing(false);
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        dateTxt.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
    }
}