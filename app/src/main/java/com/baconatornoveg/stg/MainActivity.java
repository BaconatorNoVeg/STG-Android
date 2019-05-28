package com.baconatornoveg.stg;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.baconatornoveg.stg.database.BuildDatabase;
import com.baconatornoveg.stglib.SmiteTeamGenerator;
import com.baconatornoveg.stglib.Team;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    public static BuildDatabase buildDatabase;
    public static SmiteTeamGenerator stg = SplashActivity.stg;
    public static Team generatedTeam;
    public static ArrayList<ArrayList<String>> players;
    public static int numPlayers;
    public Context context;
    public Intent buildIntent;

    public static void prepareBuildActivity(Team team) {

        //Reset strings
        players = new ArrayList<>();
        //Set strings
        //If loadout doesn't exist (is null), use blank string; otherwise set the string
        for (int i = 0; i < numPlayers; i++) {
            if (team.getPlayer(i) != null) {
                ArrayList<String> player = new ArrayList<>();
                player.add(team.getPlayer(i).getGod().getName());
                player.add(team.getPlayer(i).getBuild().toString());
                players.add(player);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buildDatabase = Room.databaseBuilder(getApplicationContext(), BuildDatabase.class, "builddb").allowMainThreadQueries().fallbackToDestructiveMigration().build();
        Button onePlayerButton = findViewById(R.id.onePlayer);
        Button twoPlayerButton = findViewById(R.id.twoPlayer);
        Button threePlayerButton = findViewById(R.id.threePlayer);
        Button fourPlayerButton = findViewById(R.id.fourPlayer);
        Button fivePlayerButton = findViewById(R.id.fivePlayer);
        context = this;
        buildIntent = new Intent(getApplicationContext(), BuildActivity.class);
        System.out.println("Smite Team Generator: Android Front-End");
        System.out.println("STG-Lib Version " + stg.getVersion());
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
        inflater.inflate(R.menu.action_main, menu);
        return true;
    }

    private void runGenerator(int teamSize) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        int buildType = Integer.parseInt(Objects.requireNonNull(prefs.getString("KEY_BUILD_TYPE", "0")));
        boolean forcingBalanced = prefs.getBoolean("KEY_FORCE_BALANCED", true);
        boolean forcingBoots = prefs.getBoolean("KEY_FORCE_BOOTS", true);
        stg.warriorsOffensive = prefs.getBoolean("KEY_WARRIORS_OFF", false);
        generatedTeam = stg.generateTeam(teamSize, forcingBalanced, forcingBoots, buildType);
        prepareBuildActivity(generatedTeam);
        startActivity(buildIntent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_donate:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://buymeacoff.ee/baconatornoveg"));
                startActivity(browserIntent);
                return true;

            case R.id.action_options:
                Intent optionsIntent = new Intent(getApplicationContext(), OptionsActivity.class);
                startActivity(optionsIntent);
                return true;

            case R.id.action_view_saved:
                Intent savedIntent = new Intent(getApplicationContext(), SavedActivity.class);
                startActivity(savedIntent);
                return true;

            case R.id.action_roadmap:
                Intent roadmapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://trello.com/b/jWwfWeOs/smite-team-generator"));
                startActivity(roadmapIntent);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}
