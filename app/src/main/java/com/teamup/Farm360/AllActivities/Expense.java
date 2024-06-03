package com.teamup.Farm360.AllActivities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class Expense extends AppCompatActivity {

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
        setContentView(R.layout.activity_expense);

        expenseTxt = findViewById(R.id.expenseTxt);
        add_noter = findViewById(R.id.add_noter);

        expenseTxt.setText("₹ " + Admin.finalExpense);

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
                startActivity(new Intent(Expense.this, Add_Expense.class));

            }
        });

        fetchAll();


        CardView expenseCard = findViewById(R.id.expenseCard);
        expenseCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Expense.this, FilterActivity.class));
            }
        });


    }

    private void fetchAll() {

        boolean firstOver = false;
        Admin.resultArray = null;
        Admin.listen.observe(Expense.this, new Observer<String>() {
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

                                    if (expense.contains("Exp-")) {

                                        String strCurrentDate = "" + timer;
                                        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                                        Date newDate = format.parse(strCurrentDate);

                                        format = new SimpleDateFormat("yyyy");
                                        String date = format.format(newDate);

                                        if (tinyDB.getBoolean("filterStat")) {
                                            if (!tinyDB.getString("filterCrop").equalsIgnoreCase("Select") && !tinyDB.getString("khataYear").equalsIgnoreCase("Select")) {
                                                if (tinyDB.getString("filterCrop").equalsIgnoreCase(cat) && tinyDB.getString("khataYear").equalsIgnoreCase(date)) {
                                                    expense = expense.replace("Exp-", "");
                                                    ExpenseReq nr = new ExpenseReq(id, mobile, name, expense, cat, description, timer, title);
                                                    list.add(nr);

                                                    add_noter.setVisibility(View.GONE);

                                                    finalExpense = finalExpense + Double.parseDouble(expense);
                                                    expenseTxt.setText("₹ " + finalExpense);
                                                }
                                            } else {
                                                if (!tinyDB.getString("filterCrop").equalsIgnoreCase("Select")) {
                                                    if (tinyDB.getString("filterCrop").equalsIgnoreCase(cat)) {
                                                        expense = expense.replace("Exp-", "");
                                                        ExpenseReq nr = new ExpenseReq(id, mobile, name, expense, cat, description, timer, title);
                                                        list.add(nr);

                                                        add_noter.setVisibility(View.GONE);

                                                        finalExpense = finalExpense + Double.parseDouble(expense);
                                                        expenseTxt.setText("₹ " + finalExpense);
                                                    }
                                                } else if (!tinyDB.getString("khataYear").equalsIgnoreCase("Select")) {


                                                    if (tinyDB.getString("khataYear").equalsIgnoreCase(date)) {
                                                        expense = expense.replace("Exp-", "");
                                                        ExpenseReq nr = new ExpenseReq(id, mobile, name, expense, cat, description, timer, title);
                                                        list.add(nr);

                                                        add_noter.setVisibility(View.GONE);

                                                        finalExpense = finalExpense + Double.parseDouble(expense);
                                                        expenseTxt.setText("₹ " + finalExpense);
                                                    }
                                                } else {
                                                    expense = expense.replace("Exp-", "");
                                                    ExpenseReq nr = new ExpenseReq(id, mobile, name, expense, cat, description, timer, title);
                                                    list.add(nr);

                                                    add_noter.setVisibility(View.GONE);

                                                    finalExpense = finalExpense + Double.parseDouble(expense);
                                                    expenseTxt.setText("₹ " + finalExpense);
                                                }
                                            }
                                        } else {
                                            expense = expense.replace("Exp-", "");
                                            ExpenseReq nr = new ExpenseReq(id, mobile, name, expense, cat, description, timer, title);
                                            list.add(nr);

                                            add_noter.setVisibility(View.GONE);

                                            finalExpense = finalExpense + Double.parseDouble(expense);
                                            expenseTxt.setText("₹ " + finalExpense);
                                        }

                                    }

                                    adapter.notifyDataSetChanged();

                                    try {
                                        AppSyncPleaseWait.stopDialog(Expense.this);
                                    } catch (Exception v) {

                                    }


                                } catch (Exception v) {
                                    //                                    Admin.showToast(getApplicationContext(), "" + v);
                                }
                            }

                        } catch (Exception v) {
                            Admin.showToast(getApplicationContext(), "" + v);
                        }
                    } else {
                        try {
                            add_noter.setVisibility(View.VISIBLE);
                            AppSyncPleaseWait.stopDialog(Expense.this);
                        } catch (Exception v) {

                        }
                    }
                }
            }
        });

        adapter.notifyDataSetChanged();
    }


    @Override
    protected void onResume() {

        try {
            AppSyncPleaseWait.showDialog(Expense.this, "Please wait..");

            finalExpense = 0.00;
            finalIncome = 0.00;
            expenseTxt.setText("₹ " + finalExpense);
            new Admin.CommonJsonTask().execute(Admin.BASE_URL + getString(R.string.expense_query2) + "'" + tinyDB.getString("email") + "'" + " Order By id DESC");

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


        if (TextUtils.isEmpty(tinyDB.getString("filterCrop"))) {
            tinyDB.putString("filterCrop", "Select");
        }

        super.onResume();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            if (Admin.removed) {
                Admin.removed = false;
                try {
                    AppSyncPleaseWait.showDialog(Expense.this, "Please wait..");

                    finalExpense = 0.00;
                    finalIncome = 0.00;
                    expenseTxt.setText("₹ " + finalExpense);
                    new Admin.CommonJsonTask().execute(Admin.BASE_URL + getString(R.string.expense_query) + "'" + tinyDB.getString("email") + "'" + " Order By id DESC");
                } catch (Exception v) {

                }
            }
        }
    }
}