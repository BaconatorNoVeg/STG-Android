package com.baconatornoveg.stg;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

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
        mainIntent = new Intent(getApplicationContext(), MainActivity.class);
        new GetGeneratorLists().execute();
    }
}
