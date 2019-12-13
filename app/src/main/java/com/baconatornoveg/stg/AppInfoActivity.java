package com.baconatornoveg.stg;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.baconatornoveg.stg.database.BuildDatabase;
import com.baconatornoveg.stglib.SmiteTeamGenerator;

public class AppInfoActivity extends AppCompatActivity {

    public static SmiteTeamGenerator stg = new SmiteTeamGenerator();
    public Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appinfo);
        context = this;
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
        Button supportButton = findViewById(R.id.supportButton);
        Button roadmapButton = findViewById(R.id.roadmapButton);
        Button issueButton = findViewById(R.id.issueButton);
        supportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.buymeacoffee.com/codazed"));
                startActivity(browserIntent);
            }
        });
        roadmapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent roadmapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://trello.com/b/jWwfWeOs/smite-team-generator"));
                startActivity(roadmapIntent);
            }
        });
        issueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent githubIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://gitlab.com/Codazed/SmiteTeamGenerator/issues"));
                startActivity(githubIntent);
            }
        });
        appSubtitle.setText(subtitleString);
    }
}
