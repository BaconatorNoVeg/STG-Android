package com.baconatornoveg.stg;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.baconatornoveg.stglib.SmiteTeamGenerator;
import com.baconatornoveg.stglib.Team;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public Context context;
    public static SmiteTeamGenerator stb = SplashActivity.stg;
    public static String player1God;
    public static String player1Build;
    public static String player2God;
    public static String player2Build;
    public static String player3God;
    public static String player3Build;
    public static String player4God;
    public static String player4Build;
    public static String player5God;
    public static String player5Build;
    public static Team generatedTeam;
    public static ArrayList<ArrayList<String>> players;

    public static int numPlayers;
    public Intent buildIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button onePlayerButton = findViewById(R.id.onePlayer);
        Button twoPlayerButton = findViewById(R.id.twoPlayer);
        Button threePlayerButton = findViewById(R.id.threePlayer);
        Button fourPlayerButton = findViewById(R.id.fourPlayer);
        Button fivePlayerButton = findViewById(R.id.fivePlayer);
        context = this;
        buildIntent = new Intent(getApplicationContext(), BuildActivity.class);
        System.out.println("Smite Team Builder: Android Edition");
        System.out.println("Version " + stb.getVersion());
        System.out.println("Proudly coded by Joshua Luce");
        System.out.println();
        onePlayerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                numPlayers = 1;
                runGenerator(1);
            }

        });
        twoPlayerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                numPlayers = 2;
                runGenerator(2);
            }
        });
        threePlayerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                numPlayers = 3;
                runGenerator(3);
            }
        });
        fourPlayerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                numPlayers = 4;
                runGenerator(4);
            }
        });
        fivePlayerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                numPlayers = 5;
                runGenerator(5);
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar, menu);
        return true;
    }

    public static void prepareBuildActivity(Team team) {

        //Reset strings
        players = new ArrayList<>();


        //Set strings
        //If loadout doesn't exist (is null), use blank string; otherwise set the string
        for (int i = 0; i < numPlayers; i++) {
            if (team.getPlayer(i) != null) {
                ArrayList<String> player = new ArrayList<>();
                player.add(team.getPlayer(i).getGod());
                player.add(team.getPlayer(i).getBuild().toString());
                players.add(player);
            }
        }
        /*if (stb.player2Loadout != null) {
            player2God = stb.player2Loadout.get(0);
            player2Build = stb.player2Loadout.get(1);
        } else {
            player2God = "";
            player2Build = "";
        }
        if (stb.player3Loadout != null) {
            player3God = stb.player3Loadout.get(0);
            player3Build = stb.player3Loadout.get(1);
        } else {
            player3God = "";
            player3Build = "";
        }
        if (stb.player4Loadout != null) {
            player4God = stb.player4Loadout.get(0);
            player4Build = stb.player4Loadout.get(1);
        } else {
            player4God = "";
            player4Build = "";
        }
        if (stb.player5Loadout != null) {
            player5God = stb.player5Loadout.get(0);
            player5Build = stb.player5Loadout.get(1);
        } else {
            player5God = "";
            player5Build = "";
        }*/
    }

    private void runGenerator(int teamSize) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean forcingOffensive = prefs.getBoolean("KEY_FORCE_OFFENSIVE", false);
        boolean forcingDefensive = prefs.getBoolean("KEY_FORCE_DEFENSIVE", false);
        boolean forcingBalanced = prefs.getBoolean("KEY_FORCE_BALANCED", true);
        generatedTeam = stb.generateTeam(teamSize, forcingOffensive, forcingDefensive, forcingBalanced);
        prepareBuildActivity(generatedTeam);
        startActivity(buildIntent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_donate:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://paypal.me/joshualuce"));
                startActivity(browserIntent);
                return true;

            case R.id.action_options:
                Intent optionsIntent = new Intent(getApplicationContext(), OptionsActivity.class);
                startActivity(optionsIntent);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}
