package com.teamup.Farm360.AllActivities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.accounts.AccountManager;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.teamup.app_sync.AppSyncChangeNavigationColor;
import com.teamup.app_sync.AppSyncGoogleSignIn;
import com.teamup.app_sync.AppSyncHideTop;
import com.teamup.app_sync.AppSyncToast;
import com.teamup.Farm360.AllModules.Admin;
import com.teamup.Farm360.AllModules.TinyDB;
import com.teamup.Farm360.R;

public class LoginPage extends AppCompatActivity {

    Button loginBtn;
    TinyDB tinyDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppSyncChangeNavigationColor.change(this);
        AppSyncHideTop.hide(this);
        setContentView(R.layout.activity_login_page);

        tinyDB = new TinyDB(this);

        loginBtn = findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppSyncGoogleSignIn.getAccounts(LoginPage.this, 512);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 512 && resultCode == RESULT_OK) {

            String accountName = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);

            if (!TextUtils.isEmpty(accountName)) {
                tinyDB.putBoolean("login", true);
                tinyDB.putString("email", accountName);
                AppSyncToast.showToast(getApplicationContext(), "Signed in as : " + accountName);
                finish();
                startActivity(new Intent(LoginPage.this, MainActivity.class));
                Admin.overrriredTrans(LoginPage.this);
            } else {
                AppSyncToast.showToast(getApplicationContext(), "Please try again");
            }
        }
    }
}