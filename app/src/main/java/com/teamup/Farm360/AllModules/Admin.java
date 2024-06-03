package com.teamup.Farm360.AllModules;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;

import com.marcoscg.materialtoast.MaterialToast;
import com.teamup.Farm360.AllActivities.MainActivity;
import com.teamup.Farm360.AllReqs.CropsReq;
import com.teamup.Farm360.BuildConfig;
import com.teamup.Farm360.R;
import com.teamup.app_sync.AppSyncRandomNumber;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import static com.teamup.Farm360.AllActivities.MainActivity.crop_rates_card;

public class Admin {
    public static final String BASE_URL = BuildConfig.android_base;
    public static String slectedCityA = "";
    public static String headTxt = "";
    public static String domain = "";

    public static double finalExpense = 0;
    public static double finalIncome = 0;
    public static String KhataBook;
    public static boolean addFromTrans = false;
    public static ArrayList<CropsReq> cropsList = new ArrayList<>();

    public static JSONArray resultArray;
    public static MutableLiveData<String> listen = new MutableLiveData<>();
    public static boolean updation = false;
    public static String cropName = "";
    public static String finalAcrePicked;
    public static String cropImgUrl;
    public static int khataId;
    public static String KhataAmount;
    public static String KhataCat;
    public static String KhataDesc;
    public static String KhataTimer;
    public static String KhataTitle;
    public static boolean removed = false;

    public static class FetchWeather extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String... params) {

            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                java.net.URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                    Log.d("Response: ", "> " + line);   //here u ll get whole response...... :-)

                }

                return buffer.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            try {

                Admin.response = result;
                Admin.listenWeather.setValue("" + AppSyncRandomNumber.generateRandomNumber(5));


            } catch (Exception c) {

//                Admin.listen.setValue("" + AppSyncRandomNumber.generateRandomNumber(5));

            }
        }
    }

    public static JSONArray resultArrayOfWeather;
    public static MutableLiveData<String> listenWeather = new MutableLiveData<>();
    public static String response = null;


    public static void showToast(Context context, String msg) {
        new MaterialToast(context)
                .setMessage(msg)
                .setDuration(Toast.LENGTH_SHORT)
                .setBackgroundColor(Color.parseColor("#032DA1"))
                .show();
    }

    public static void initViews(MainActivity mainActivity) {
        crop_rates_card = mainActivity.findViewById(R.id.crop_rates_card);
        MainActivity.govt_card = mainActivity.findViewById(R.id.govt_card);
        MainActivity.harvesting_tech_card = mainActivity.findViewById(R.id.harvesting_tech_card);
        MainActivity.nearby_krushi_seva_card = mainActivity.findViewById(R.id.nearby_krushi_seva_card);
        MainActivity.soil_and_water_card = mainActivity.findViewById(R.id.soil_and_water_card);
        MainActivity.weather_forecast_card = mainActivity.findViewById(R.id.weather_forecast_card);
        MainActivity.farmers_wallet_card = mainActivity.findViewById(R.id.farmers_wallet_card);
        MainActivity.app_info_card = mainActivity.findViewById(R.id.app_info_card);
        MainActivity.agri_calendar_card = mainActivity.findViewById(R.id.agri_calendar_card);
        MainActivity.disease_detectCard = mainActivity.findViewById(R.id.disease_detectCard);
        MainActivity.yieldprediction1 = mainActivity.findViewById(R.id.yieldprediction1);
        MainActivity.nearbymaps_card = mainActivity.findViewById(R.id.nearbymaps_card);

    }

    public static void overrriredTrans(Context context) {
        ((Activity) context).overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public static class CommonJsonTask extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String... params) {

            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                java.net.URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                    Log.d("Response: ", "> " + line);   //here u ll get whole response...... :-)

                }

                return buffer.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            try {

                Admin.resultArray = new JSONArray(result);
                Admin.listen.setValue("" + AppSyncRandomNumber.generateRandomNumber(5));


            } catch (Exception c) {

                Log.e("L145", c.getMessage());
//                Admin.listen.setValue("" + AppSyncRandomNumber.generateRandomNumber(5));
//                Toast.makeText(News.context, "" + c, Toast.LENGTH_SHORT).show();
            }
        }
    }

}
