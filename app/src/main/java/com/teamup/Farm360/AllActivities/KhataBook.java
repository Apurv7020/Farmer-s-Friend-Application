package com.teamup.Farm360.AllActivities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityOptionsCompat;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.teamup.Farm360.AllModules.Admin;
import com.teamup.Farm360.AllModules.TinyDB;
import com.teamup.Farm360.AllReqs.KhataReq;
import com.teamup.Farm360.R;
import com.teamup.app_sync.AppSyncCurrentDate;
import com.teamup.app_sync.AppSyncPleaseWait;
import com.teamup.app_sync.AppSyncRandomNumber;
import com.whiteelephant.monthpicker.MonthPickerDialog;


import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class KhataBook extends AppCompatActivity {

    TinyDB tinyDB;
    RecyclerView recycleView;
    ArrayList<KhataReq> list;
    //    MyCropAdapter adapter;
    FloatingActionButton addBtn;

    CardView expenseCard, incomeCard;

    double finalExpense = 0.00;
    double finalIncome = 0.00;

    TextView expenseTxt, incomeTxt, yearTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_khata_book);

        tinyDB = new TinyDB(this);

        list = new ArrayList<>();

        expenseCard = findViewById(R.id.expenseCard);
        incomeCard = findViewById(R.id.incomeCard);
        expenseTxt = findViewById(R.id.expenseTxt);
        incomeTxt = findViewById(R.id.incomeTxt);
        yearTxt = findViewById(R.id.yearTxt);


        yearTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(KhataBook.this, new MonthPickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(int selectedMonth, int selectedYear) {
                        yearTxt.setText(Integer.toString(selectedYear));
                        tinyDB.putString("khataYear", yearTxt.getText().toString());
                        Admin.listen.setValue("" + AppSyncRandomNumber.generateRandomNumber(5));

                    }
                }, Integer.parseInt(AppSyncCurrentDate.getDateTimeInFormat("yyyy")), 0);

                builder.showYearOnly()
                        .setYearRange(1990, 2040)
                        .build()
                        .show();
            }
        });

        expenseCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Admin.KhataBook = "Expense";
                Admin.finalExpense = finalExpense;
                CardView doctorImg = findViewById(R.id.expenseCard);
                Intent intent = new Intent(KhataBook.this, Expense.class);
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(KhataBook.this, (View) doctorImg, "profile");
                startActivity(intent, options.toBundle());
            }
        });

        incomeCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Admin.KhataBook = "Income";
                Admin.finalIncome = finalIncome;
                CardView doctorImg = findViewById(R.id.incomeCard);
                Intent intent = new Intent(KhataBook.this, Income.class);
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(KhataBook.this, (View) doctorImg, "profile");
                startActivity(intent, options.toBundle());
            }
        });


        Admin.resultArray = null;
        Admin.listen.observe(KhataBook.this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                try {
                    if (Admin.resultArray != null) {
                        if (Admin.resultArray.length() > 0) {
                            try {
                                finalExpense = 0.00;
                                finalIncome = 0.00;
                                incomeTxt.setText("₹ " + finalIncome);
                                expenseTxt.setText("₹ " + finalExpense);

                                list.clear();
                                //                        adapter.notifyDataSetChanged();

                                for (int i = 0; i < Admin.resultArray.length(); i++) {
                                    try {

                                        JSONObject obj = null;
                                        try {
                                            obj = Admin.resultArray.getJSONObject(i);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                        int id = obj.getInt("id");
                                        String name = URLDecoder.decode(obj.getString("name"));
                                        String mobile = URLDecoder.decode(obj.getString("email"));
                                        String expense = URLDecoder.decode(obj.getString("expense"));
                                        String cat = URLDecoder.decode(obj.getString("cat"));
                                        String description = URLDecoder.decode(obj.getString("description"));
                                        String timer = URLDecoder.decode(obj.getString("timer"));

                                        KhataReq nr = new KhataReq(id, name, mobile, expense, cat, description);

                                        String strCurrentDate = "" + timer;
                                        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                                        Date newDate = format.parse(strCurrentDate);

                                        format = new SimpleDateFormat("yyyy");
                                        String date = format.format(newDate);

                                        if (yearTxt.getText().toString().equalsIgnoreCase(date)) {

                                            list.add(nr);

                                            if (expense.contains("Exp-")) {
                                                String currentString = "" + expense;
                                                String[] separated = currentString.split("-");
                                                String one = separated[0]; // this will contain "Fruit"
                                                String two = separated[1];
                                                finalExpense = finalExpense + Double.parseDouble(two);
                                                expenseTxt.setText("₹ " + finalExpense);
                                            }

                                            if (expense.contains("In-")) {
                                                String currentString = "" + expense;
                                                String[] separated = currentString.split("-");
                                                String one = separated[0]; // this will contain "Fruit"
                                                String two = separated[1];
                                                finalIncome = finalIncome + Double.parseDouble(two);
                                                incomeTxt.setText("₹ " + finalIncome);
                                            }
                                        }

                                        //                                adapter.notifyDataSetChanged();

                                        try {
                                            AppSyncPleaseWait.stopDialog(KhataBook.this);
                                        } catch (Exception v) {

                                        }

                                    } catch (Exception v) {
                                        //                                Admin.showToast(getApplicationContext(), "" + v);
                                    }
                                }

                            } catch (Exception v) {
                                //                        Admin.showToast(getApplicationContext(), "" + v);
                            }
                        } else {
                            try {
                                AppSyncPleaseWait.stopDialog(KhataBook.this);
                            } catch (Exception v) {

                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("Err215", e.getMessage());
                }
            }
        });

    }

    private void checkForName() {

        if (TextUtils.isEmpty(tinyDB.getString("fullName")) || TextUtils.isEmpty(tinyDB.getString("city"))) {

            AlertDialog.Builder builder = new AlertDialog.Builder(KhataBook.this);
            builder.setTitle("Enter Information : ");

// Set up the input

            LinearLayout layout = new LinearLayout(this);
            layout.setOrientation(LinearLayout.VERTICAL);

            final EditText input = new EditText(this);
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            input.setHint("Enter Name");
            layout.addView(input);


            final EditText input2 = new EditText(this);
            input2.setInputType(InputType.TYPE_CLASS_TEXT);
            input2.setHint("Enter City");
            layout.addView(input2);

            builder.setView(layout);
            input.setText(tinyDB.getString("fullName"));
            input.setText(tinyDB.getString("city"));

            if (!TextUtils.isEmpty(tinyDB.getString("fullName"))) {
                input.setVisibility(View.GONE);
            }

            if (!TextUtils.isEmpty(tinyDB.getString("city"))) {
                input2.setVisibility(View.GONE);
            }

// Set up the buttons
            builder.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (TextUtils.isEmpty(tinyDB.getString("fullName"))) {
                        String m_Text = input.getText().toString();
                        tinyDB.putString("fullName", m_Text);
                    }

                    if (TextUtils.isEmpty(tinyDB.getString("city"))) {
                        String m_Text2 = input2.getText().toString();
                        tinyDB.putString("city", m_Text2);
                    }

                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            builder.show();
        } else {

        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            checkForName();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            finalExpense = 0.00;
            finalIncome = 0.00;

            incomeTxt.setText("₹ " + finalIncome);
            expenseTxt.setText("₹ " + finalExpense);

            AppSyncPleaseWait.showDialog(KhataBook.this, "Please wait..");
//        get all MyCrops by farmers mobile number
            Admin.resultArray = null;

            new Admin.CommonJsonTask().execute(Admin.BASE_URL + "fetch_expense.php?query=select%20*%20from%20Expense%20where%20email%20=%20" + "'" + tinyDB.getString("email") + "'");
            Log.e("L309", Admin.BASE_URL + "fetch_expense.php?query=select%20*%20from%20Expense%20where%20email%20=%20" + "'" + tinyDB.getString("email") + "'");

            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the windowe
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
                window.setNavigationBarColor(getResources().getColor(R.color.colorPrimaryDark));
            }

            yearTxt.setText(AppSyncCurrentDate.getDateTimeInFormat("yyyy"));

            if (!TextUtils.isEmpty(tinyDB.getString("khataYear"))) {
                yearTxt.setText(tinyDB.getString("khataYear"));
            } else if (yearTxt.getText().toString().equalsIgnoreCase("Select")) {
                yearTxt.setText(AppSyncCurrentDate.getDateTimeInFormat("yyyy"));
                tinyDB.putString("khataYear", AppSyncCurrentDate.getDateTimeInFormat("yyyy"));
            } else {
                yearTxt.setText(AppSyncCurrentDate.getDateTimeInFormat("yyyy"));
                tinyDB.putString("khataYear", AppSyncCurrentDate.getDateTimeInFormat("yyyy"));
            }

            tinyDB.putBoolean("filterStat", true);
            tinyDB.putBoolean("filterStatIn", true);

            Admin.addFromTrans = false;
            Admin.updation = false;


        } catch (Exception c) {

        }

    }
}