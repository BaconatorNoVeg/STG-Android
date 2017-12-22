package com.baconatornoveg.stg;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static Context context;
    private SmiteTeamBuilder stb;

    public SmiteTeamBuilder getStb() {
        return stb;
    }
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
        final Intent i = new Intent(getApplicationContext(), BuildActivity.class);
        stb = new SmiteTeamBuilder(context);
        System.out.println("Smite Team Builder: Android Edition");
        System.out.println("Version " + stb.getVersion());
        System.out.println("Proudly coded by Joshua Luce");
        System.out.println("");
        stb.registerLists();
        onePlayerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                stb.generateTeam(1);
                prepareBuildActivity();
                startActivity(i);
            }

        });
        twoPlayerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                stb.generateTeam(2);
                prepareBuildActivity();
                startActivity(i);
            }
        });
        threePlayerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                stb.generateTeam(3);
                prepareBuildActivity();
                startActivity(i);
            }
        });
        fourPlayerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                stb.generateTeam(4);
                prepareBuildActivity();
                startActivity(i);
            }
        });
        fivePlayerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                stb.generateTeam(5);
                prepareBuildActivity();
                startActivity(i);
            }
        });
    }

    private void prepareBuildActivity() {

        //Reset strings
        player1God = "";
        player1Build = "";
        player2God = "";
        player2Build = "";
        player3God = "";
        player3Build = "";
        player4God = "";
        player4Build = "";
        player5God = "";
        player5Build = "";

        //Set strings
        player1God = stb.player1Loadout.get(0);
        player1Build = stb.player1Loadout.get(1);

        //If loadout doesn't exist (is null), use blank string; otherwise set the string
        if (stb.player2Loadout != null) {
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
        }
    }
}
