package com.teamup.Farm360.AllActivities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.teamup.app_sync.AppSyncChangeNavigationColor;
import com.teamup.app_sync.AppSyncHideTop;
import com.teamup.Farm360.AllModules.Admin;
import com.teamup.Farm360.AllModules.TinyDB;
import com.teamup.Farm360.R;
import com.teamup.app_sync.AppSyncCurrentDate;
import com.whiteelephant.monthpicker.MonthPickerDialog;

public class FilterActivity extends AppCompatActivity {

    Button FilterBtn, clearFilterBtn;
    TextView selectText, selectYear;
    TinyDB tinyDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppSyncHideTop.hide(this);
        setContentView(R.layout.activity_filter);

        FilterBtn = findViewById(R.id.FilterBtn);
        selectText = findViewById(R.id.selectText);
        selectYear = findViewById(R.id.selectYear);
        clearFilterBtn = findViewById(R.id.clearFilterBtn);
        tinyDB = new TinyDB(this);

        FilterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tinyDB.putBoolean("filterStat", true);
                tinyDB.putString("filterCrop", selectText.getText().toString());
                tinyDB.putString("khataYear", selectYear.getText().toString());
                finish();
            }
        });

        selectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FilterActivity.this, SelectCrop.class));
            }
        });

        clearFilterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tinyDB.putString("filterCrop", "Select");
                tinyDB.putString("khataYear", AppSyncCurrentDate.getDateTimeInFormat("yyyy"));
                tinyDB.putBoolean("filterStat", true);
                finish();
            }
        });

        if (tinyDB.getBoolean("filterStat") == true) {
            clearFilterBtn.setVisibility(View.VISIBLE);

            selectYear.setText(tinyDB.getString("khataYear"));
            selectText.setText(tinyDB.getString("filterCrop"));

        } else {
            clearFilterBtn.setVisibility(View.GONE);
        }


        selectYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(FilterActivity.this, new MonthPickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(int selectedMonth, int selectedYear) {
                        selectYear.setText(Integer.toString(selectedYear));

                    }
                }, Integer.parseInt(AppSyncCurrentDate.getDateTimeInFormat("yyyy")), 0);

                builder.showYearOnly()
                        .setYearRange(1990, 2040)
                        .build()
                        .show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        AppSyncChangeNavigationColor.change(this);

        if (!TextUtils.isEmpty(Admin.cropName)) {
            selectText.setText("" + Admin.cropName);
            Admin.cropName = "";
        }
    }
}