package com.baconatornoveg.stg;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.baconatornoveg.stg.database.BuildDatabase;
import com.baconatornoveg.stglib.SmiteTeamGenerator;

public class SplashActivity extends AppCompatActivity {

    public Context context;
    public static SmiteTeamGenerator stg = new SmiteTeamGenerator();
    public Intent mainIntent;

    private class GetGeneratorLists extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String[] params) {
            stg.getLists(false);
            return null;
        }

        @Override
        protected void onPostExecute(String message) {
            startActivity(mainIntent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        context = this;
        BuildDatabase db = Room
                .databaseBuilder(getApplicationContext(), BuildDatabase.class, "builds")
                .build();
        TextView appSubtitle = findViewById(R.id.appSubtitle);
        String appVersion = null;
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(getPackageName(), 0);
            appVersion = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String stgLibVersion = stg.getVersion();
        String subtitleString = "App Version: " + appVersion + "\nSTG-Lib Version: " + stgLibVersion + "\nProudly coded by Joshua Luce.";
        appSubtitle.setText(subtitleString);
        mainIntent = new Intent(getApplicationContext(), MainActivity.class);
        new GetGeneratorLists().execute();
    }
}
