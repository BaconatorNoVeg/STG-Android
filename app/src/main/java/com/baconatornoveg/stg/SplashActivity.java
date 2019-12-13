package com.baconatornoveg.stg;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.baconatornoveg.stg.database.BuildDatabase;
import com.baconatornoveg.stglib.SmiteTeamGenerator;

public class SplashActivity extends AppCompatActivity {

    public static SmiteTeamGenerator stg = new SmiteTeamGenerator();
    public Context context;
    public Intent mainIntent;

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
        ImageView appLogo1 = findViewById(R.id.applogo_1);
        ImageView appLogo2 = findViewById(R.id.applogo_2);
        appLogo1.startAnimation(AnimationUtils.loadAnimation(context, R.anim.clockwise_rot));
        appLogo2.startAnimation(AnimationUtils.loadAnimation(context, R.anim.counterclockwise_rot));
        new GetGeneratorLists().execute();
    }

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
}
