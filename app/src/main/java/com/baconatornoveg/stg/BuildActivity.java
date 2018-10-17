package com.baconatornoveg.stg;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.baconatornoveg.stglib.SmiteTeamGenerator;

public class BuildActivity extends AppCompatActivity {

    private SmiteTeamGenerator stb = new SmiteTeamGenerator();
    private int playerCount = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_build);
        Context context = this;
        setTextViews();
    }

    private void setTextViews() {

        //Initialize TextViews
        /*TextView player1God = findViewById(R.id.player1God);
        TextView player1Build = findViewById(R.id.player1Build);
        TextView player1Build1 = findViewById(R.id.player1Build1);
        TextView player1Build2 = findViewById(R.id.player1Build2);
        TextView player1Build3 = findViewById(R.id.player1Build3);
        TextView player1Build4 = findViewById(R.id.player1Build4);
        TextView player1Build5 = findViewById(R.id.player1Build5);
        TextView player2God = findViewById(R.id.player2God);
        TextView player2Build = findViewById(R.id.player2Build);
        TextView player2Build1 = findViewById(R.id.player2Build1);
        TextView player2Build2 = findViewById(R.id.player2Build2);
        TextView player2Build3 = findViewById(R.id.player2Build3);
        TextView player2Build4 = findViewById(R.id.player2Build4);
        TextView player2Build5 = findViewById(R.id.player2Build5);
        TextView player3God = findViewById(R.id.player3God);
        TextView player3Build = findViewById(R.id.player3Build);
        TextView player3Build1 = findViewById(R.id.player3Build1);
        TextView player3Build2 = findViewById(R.id.player3Build2);
        TextView player3Build3 = findViewById(R.id.player3Build3);
        TextView player3Build4 = findViewById(R.id.player3Build4);
        TextView player3Build5 = findViewById(R.id.player3Build5);
        TextView player4God = findViewById(R.id.player4God);
        TextView player4Build = findViewById(R.id.player4Build);
        TextView player4Build1 = findViewById(R.id.player4Build1);
        TextView player4Build2 = findViewById(R.id.player4Build2);
        TextView player4Build3 = findViewById(R.id.player4Build3);
        TextView player4Build4 = findViewById(R.id.player4Build4);
        TextView player4Build5 = findViewById(R.id.player4Build5);
        TextView player5God = findViewById(R.id.player5God);
        TextView player5Build = findViewById(R.id.player5Build);
        TextView player5Build1 = findViewById(R.id.player5Build1);
        TextView player5Build2 = findViewById(R.id.player5Build2);
        TextView player5Build3 = findViewById(R.id.player5Build3);
        TextView player5Build4 = findViewById(R.id.player5Build4);
        TextView player5Build5 = findViewById(R.id.player5Build5);*/
        TextView[] player1 = {findViewById(R.id.player1God), findViewById(R.id.player1Build)};
        TextView[] player2 = {findViewById(R.id.player2God), findViewById(R.id.player2Build)};
        TextView[] player3 = {findViewById(R.id.player3God), findViewById(R.id.player3Build)};
        TextView[] player4 = {findViewById(R.id.player4God), findViewById(R.id.player4Build)};
        TextView[] player5 = {findViewById(R.id.player5God), findViewById(R.id.player5Build)};
        TextView[][] players = {player1, player2, player3, player4, player5};

        //Set the text in the TextViews
        for (int i = 0; i < playerCount; i++) {
            players[i][0].setText(MainActivity.players.get(i).get(0));
            String[] buildArray = processBuild(MainActivity.players.get(i).get(1));
            String tvString = "";
            for (int j = 0; j < 6; j++) {
                tvString += buildArray[j] + "\n";
            }
            players[i][1].setText(tvString);

        }

        /*player1God.setText(MainActivity.player1God);
        player1Build.setText(processBuild(MainActivity.player1Build)[0]);
        player1Build1.setText(processBuild(MainActivity.player1Build)[1]);
        player1Build2.setText(processBuild(MainActivity.player1Build)[2]);
        player1Build3.setText(processBuild(MainActivity.player1Build)[3]);
        player1Build4.setText(processBuild(MainActivity.player1Build)[4]);
        player1Build5.setText(processBuild(MainActivity.player1Build)[5]);
        player2God.setText(MainActivity.player2God);
        player2Build.setText(processBuild(MainActivity.player2Build)[0]);
        player2Build1.setText(processBuild(MainActivity.player2Build)[1]);
        player2Build2.setText(processBuild(MainActivity.player2Build)[2]);
        player2Build3.setText(processBuild(MainActivity.player2Build)[3]);
        player2Build4.setText(processBuild(MainActivity.player2Build)[4]);
        player2Build5.setText(processBuild(MainActivity.player2Build)[5]);
        player3God.setText(MainActivity.player3God);
        player3Build.setText(processBuild(MainActivity.player3Build)[0]);
        player3Build1.setText(processBuild(MainActivity.player3Build)[1]);
        player3Build2.setText(processBuild(MainActivity.player3Build)[2]);
        player3Build3.setText(processBuild(MainActivity.player3Build)[3]);
        player3Build4.setText(processBuild(MainActivity.player3Build)[4]);
        player3Build5.setText(processBuild(MainActivity.player3Build)[5]);
        player4God.setText(MainActivity.player4God);
        player4Build.setText(processBuild(MainActivity.player4Build)[0]);
        player4Build1.setText(processBuild(MainActivity.player4Build)[1]);
        player4Build2.setText(processBuild(MainActivity.player4Build)[2]);
        player4Build3.setText(processBuild(MainActivity.player4Build)[3]);
        player4Build4.setText(processBuild(MainActivity.player4Build)[4]);
        player4Build5.setText(processBuild(MainActivity.player4Build)[5]);
        player5God.setText(MainActivity.player5God);
        player5Build.setText(processBuild(MainActivity.player5Build)[0]);
        player5Build1.setText(processBuild(MainActivity.player5Build)[1]);
        player5Build2.setText(processBuild(MainActivity.player5Build)[2]);
        player5Build3.setText(processBuild(MainActivity.player5Build)[3]);
        player5Build4.setText(processBuild(MainActivity.player5Build)[4]);
        player5Build5.setText(processBuild(MainActivity.player5Build)[5]);*/
    }

    private String[] processBuild(String buildArray) {
        String[] processedBuild = new String[6];
        if (!buildArray.equals("")) {
            String buildArrayTrimmed = buildArray.substring(1, buildArray.length() - 1);
            processedBuild = buildArrayTrimmed.split(",");
        }
        return processedBuild;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_build, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_reroll:
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
                stb.generateTeam(playerCount, prefs.getBoolean("KEY_FORCE_OFFENSIVE", false), prefs.getBoolean("KEY_FORCE_DEFENSIVE", false), prefs.getBoolean("KEY_FORCE_BALANCED", true));
                MainActivity.prepareBuildActivity(stb);
                setTextViews();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

}
