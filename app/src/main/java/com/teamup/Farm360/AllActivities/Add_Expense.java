package com.teamup.Farm360.AllActivities;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.teamup.app_sync.AppSyncChangeNavigationColor;
import com.teamup.app_sync.AppSyncHideTop;
import com.teamup.Farm360.AllModules.Admin;
import com.teamup.Farm360.AllModules.TinyDB;
import com.teamup.Farm360.R;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.teamup.app_sync.AppSyncCurrentDate;

import java.net.URLEncoder;
import java.util.Calendar;

public class Add_Expense extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    TextView dateTxt, selectText, addTxt;
    EditText edt_title, edt_descritpion, edt_expense;
    Button btn;
    TinyDB tinyDB;
    String conater = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppSyncHideTop.hide(this);
        setContentView(R.layout.activity_add__expense);

        tinyDB = new TinyDB(this);

        dateTxt = findViewById(R.id.dateTxt);
        edt_expense = findViewById(R.id.edt_expense);
        selectText = findViewById(R.id.selectText);
        addTxt = findViewById(R.id.addTxt);

        edt_title = findViewById(R.id.edt_title);
        edt_descritpion = findViewById(R.id.edt_descritpion);
        btn = findViewById(R.id.btn);

        if (Admin.KhataBook.equalsIgnoreCase("Income")) {
            conater = "In-";
            addTxt.setText("Add Income");
        } else {
            conater = "Exp-";
            addTxt.setText("Add Expense");
        }


        if (Admin.updation) {
            dateTxt.setText(Admin.KhataTimer);
            selectText.setText(Admin.KhataCat);
            edt_descritpion.setText(Admin.KhataDesc);
            edt_title.setText(Admin.KhataTitle);
            edt_expense.setText("" + Admin.KhataAmount);
            btn.setText(getResources().getString(R.string.update));
        } else {
            dateTxt.setText("" + AppSyncCurrentDate.getDateTimeInFormat("dd/MM/yyyy"));
        }

        if (Admin.addFromTrans) {
            dateTxt.setText(Admin.KhataTimer);
            selectText.setText(Admin.KhataCat);
            edt_descritpion.setText(Admin.KhataDesc);
            edt_title.setText(Admin.KhataTitle);
            edt_expense.setText("" + Admin.KhataAmount);
            btn.setText(getResources().getString(R.string.submit));
        } else {
            dateTxt.setText("" + AppSyncCurrentDate.getDateTimeInFormat("dd/MM/yyyy"));
        }

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = edt_title.getText().toString();
                String desc = edt_descritpion.getText().toString();
                String cat = selectText.getText().toString();
                String date = dateTxt.getText().toString();
                String expense = edt_expense.getText().toString();

                if (cat.equalsIgnoreCase("Select") || cat.equalsIgnoreCase("निवडा")) {
                    cat = "Other";
                }

                if (!TextUtils.isEmpty(expense)) {

                    expense = conater + edt_expense.getText().toString();

                    if (!TextUtils.isEmpty(title)) {

                        if (!Admin.updation) {

                            MainActivity.makeItQuery(getString(R.string.insert_expense) + tinyDB.getString("email")  + "', '" + URLEncoder.encode(tinyDB.getString("name")) + "', '" + URLEncoder.encode(expense) + "', '" + URLEncoder.encode(cat) + "', '" + URLEncoder.encode(desc) + "', '" + date + "', '" + URLEncoder.encode(title) + "');", getApplicationContext());
                            finish();
                            Admin.showToast(getApplicationContext(), "Added successfully");

                            tinyDB.putBoolean("" + title, true);

                        } else {
                            MainActivity.makeItQuery("UPDATE `Expense` SET `expense` = '" + URLEncoder.encode(expense) + "',`cat` = '" + URLEncoder.encode(cat) + "',`description` = '" + URLEncoder.encode(desc) + "',`timer` = '" + date + "',`title` = '" + URLEncoder.encode(title) + "' WHERE `Expense`.`id` = " + Admin.khataId, getApplicationContext());
                            finish();
                            Admin.showToast(getApplicationContext(), "Added successfully");
                        }
                    } else {
                        Admin.showToast(getApplicationContext(), "Please enter title");
                    }
                } else {
                    Admin.showToast(getApplicationContext(), "Please enter " + Admin.KhataBook);
                }


            }
        });


        dateTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        Add_Expense.this,
                        now.get(Calendar.YEAR), // Initial year selection
                        now.get(Calendar.MONTH), // Initial month selection
                        now.get(Calendar.DAY_OF_MONTH) // Inital day selection
                );
                dpd.show(getSupportFragmentManager(), "ds");
            }
        });

        selectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Add_Expense.this, SelectCrop.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        AppSyncChangeNavigationColor.change(this);
        if (!TextUtils.isEmpty(Admin.cropName)) {
            selectText.setText("" + Admin.cropName);
        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        dateTxt.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
    }
}