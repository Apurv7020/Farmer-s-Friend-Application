package com.teamup.Farm360.AllActivities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.teamup.app_sync.AppSyncHideTop;
import com.teamup.Farm360.AllAdapters.ExpenseAdapter;
import com.teamup.Farm360.AllModules.Admin;
import com.teamup.Farm360.AllModules.TinyDB;
import com.teamup.Farm360.AllReqs.ExpenseReq;
import com.teamup.Farm360.R;
import com.teamup.app_sync.AppSyncPleaseWait;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class Income extends AppCompatActivity {

    TextView expenseTxt;

    TinyDB tinyDB;
    RecyclerView recycleView;
    ArrayList<ExpenseReq> list;
    ExpenseAdapter adapter;
    FloatingActionButton addBtn;
    double finalExpense = 0.00;
    double finalIncome = 0.00;

    ImageView add_noter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppSyncHideTop.hide(this);
        setContentView(R.layout.activity_income);

        expenseTxt = findViewById(R.id.expenseTxt);
        add_noter = findViewById(R.id.add_noter);

        expenseTxt.setText("₹ " + Admin.finalIncome);

        tinyDB = new TinyDB(this);

        recycleView = findViewById(R.id.recycleView);
        list = new ArrayList<>();
        adapter = new ExpenseAdapter(list);
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        recycleView.setHasFixedSize(true);
        recycleView.setAdapter(adapter);
        addBtn = findViewById(R.id.addBtn);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Admin.updation = false;
                Admin.cropName = "";
                startActivity(new Intent(Income.this, Add_Expense.class));

            }
        });

        fetchAll();


        CardView expenseCard = findViewById(R.id.expenseCard);
        expenseCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Income.this, FilterIncomeActivity.class));
            }
        });


    }

    private void fetchAll() {

        boolean firstOver = false;
        Admin.resultArray = null;
        Admin.listen.observe(Income.this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (Admin.resultArray != null) {
                    if (Admin.resultArray.length() > 0) {
                        try {
                            finalExpense = 0.00;
                            finalIncome = 0.00;
                            list.clear();
                            adapter.notifyDataSetChanged();

                            for (int i = 0; i < Admin.resultArray.length(); i++) {
                                try {

                                    JSONObject obj = null;
                                    try {
                                        obj = Admin.resultArray.getJSONObject(i);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                    int id = obj.getInt("id");
                                    String mobile = URLDecoder.decode(obj.getString("email"));
                                    String name = URLDecoder.decode(obj.getString("name"));
                                    String expense = URLDecoder.decode(obj.getString("expense"));
                                    String cat = URLDecoder.decode(obj.getString("cat"));
                                    String description = URLDecoder.decode(obj.getString("description"));
                                    String timer = URLDecoder.decode(obj.getString("timer"));
                                    String title = URLDecoder.decode(obj.getString("title"));

                                    if (expense.contains("In-")) {

                                        String strCurrentDate = "" + timer;
                                        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                                        Date newDate = format.parse(strCurrentDate);

                                        format = new SimpleDateFormat("yyyy");
                                        String date = format.format(newDate);

                                        if (tinyDB.getBoolean("filterStatIn")) {
                                            if (!tinyDB.getString("filterCropIn").equalsIgnoreCase("Select") && !tinyDB.getString("khataYear").equalsIgnoreCase("Select")) {
                                                if (tinyDB.getString("filterCropIn").equalsIgnoreCase(cat) && tinyDB.getString("khataYear").equalsIgnoreCase(date)) {
                                                    expense = expense.replace("In-", "");
                                                    ExpenseReq nr = new ExpenseReq(id, mobile, name, expense, cat, description, timer, title);
                                                    list.add(nr);

                                                    add_noter.setVisibility(View.GONE);

                                                    finalIncome = finalIncome + Double.parseDouble(expense);
                                                    expenseTxt.setText("₹ " + finalIncome);
                                                }
                                            } else {
                                                if (!tinyDB.getString("filterCropIn").equalsIgnoreCase("Select")) {
                                                    if (tinyDB.getString("filterCropIn").equalsIgnoreCase(cat)) {
                                                        expense = expense.replace("In-", "");
                                                        ExpenseReq nr = new ExpenseReq(id, mobile, name, expense, cat, description, timer, title);
                                                        list.add(nr);

                                                        add_noter.setVisibility(View.GONE);

                                                        finalIncome = finalIncome + Double.parseDouble(expense);
                                                        expenseTxt.setText("₹ " + finalIncome);
                                                    }
                                                } else if (!tinyDB.getString("khataYear").equalsIgnoreCase("Select")) {


                                                    if (tinyDB.getString("khataYear").equalsIgnoreCase(date)) {
                                                        expense = expense.replace("In-", "");
                                                        ExpenseReq nr = new ExpenseReq(id, mobile, name, expense, cat, description, timer, title);
                                                        list.add(nr);

                                                        add_noter.setVisibility(View.GONE);

                                                        finalIncome = finalIncome + Double.parseDouble(expense);
                                                        expenseTxt.setText("₹ " + finalIncome);
                                                    }
                                                } else {
                                                    expense = expense.replace("In-", "");
                                                    ExpenseReq nr = new ExpenseReq(id, mobile, name, expense, cat, description, timer, title);
                                                    list.add(nr);

                                                    add_noter.setVisibility(View.GONE);

                                                    finalIncome = finalIncome + Double.parseDouble(expense);
                                                    expenseTxt.setText("₹ " + finalIncome);
                                                }
                                            }
                                        } else {
                                            expense = expense.replace("In-", "");
                                            ExpenseReq nr = new ExpenseReq(id, mobile, name, expense, cat, description, timer, title);
                                            list.add(nr);

                                            add_noter.setVisibility(View.GONE);

                                            finalIncome = finalIncome + Double.parseDouble(expense);
                                            expenseTxt.setText("₹ " + finalIncome);
                                        }

                                    }

                                    adapter.notifyDataSetChanged();

                                    try {
                                        AppSyncPleaseWait.stopDialog(Income.this);
                                    } catch (Exception v) {

                                    }


                                } catch (Exception v) {
//                                    Admin.showToast(getApplicationContext(), "" + v);
                                }
                            }

                        } catch (Exception v) {
//                            Admin.showToast(getApplicationContext(), "" + v);add_smart.webp
                        }
                    } else {
                        try {
                            add_noter.setVisibility(View.VISIBLE);
                            AppSyncPleaseWait.stopDialog(Income.this);
                        } catch (Exception v) {

                        }

                    }
                }
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                try {
                    AppSyncPleaseWait.stopDialog(Income.this);
                } catch (Exception v) {

                }
            }
        }, 3500);


        adapter.notifyDataSetChanged();
    }


    @Override
    protected void onResume() {

        try {
            AppSyncPleaseWait.showDialog(Income.this, "Please wait..");

            finalExpense = 0.00;
            finalIncome = 0.00;
            expenseTxt.setText("₹ " + finalIncome);
            new Admin.CommonJsonTask().execute(Admin.BASE_URL + "fetch_expense.php?query=select%20*%20from%20Expense%20where%20email%20=%20" + "'" + tinyDB.getString("email") + "'" + URLEncoder.encode(" Order By id DESC"));

            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the windowe
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
                window.setNavigationBarColor(getResources().getColor(R.color.colorPrimaryDark));
            }

        } catch (Exception c) {

        }

        if (TextUtils.isEmpty(tinyDB.getString("filterCropIn"))) {
            tinyDB.putString("filterCropIn", "Select");
        }


        super.onResume();
    }
}