package com.baconatornoveg.stg;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baconatornoveg.stglib.SmiteTeamGenerator;
import com.baconatornoveg.stglib.Team;

public class BuildActivity extends AppCompatActivity {

    private SmiteTeamGenerator stb = MainActivity.stb;
    private int playerCount = MainActivity.numPlayers;
    LinearLayout[] loadouts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_build);
        final Context context = this;
        loadouts = new LinearLayout[]{findViewById(R.id.player1Loadout), findViewById(R.id.player2Loadout), findViewById(R.id.player3Loadout), findViewById(R.id.player4Loadout), findViewById(R.id.player5Loadout)};
        for (int i = 0; i < 5; i++) {
            final int currentPlayer = i;
            loadouts[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, "Long press player to show options", Toast.LENGTH_SHORT).show();
                }
            });
            loadouts[i].setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Player Options")
                            .setItems(new String[]{"Share"}, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    if (which == 0) {
                                        Intent sendIntent = new Intent();
                                        sendIntent.setAction(Intent.ACTION_SEND);
                                        String[] build = MainActivity.players.get(currentPlayer).get(1).substring(1, MainActivity.players.get(currentPlayer).get(1).length()-1).split(", ");
                                        String formattedPlayer = MainActivity.players.get(currentPlayer).get(0) + "\n";
                                        for (int i = 0; i < 6; i++) {
                                            formattedPlayer += "- " + build[i];
                                            if (i != 5) { formattedPlayer += "\n"; }
                                        }
                                        sendIntent.putExtra(Intent.EXTRA_TEXT, formattedPlayer);
                                        sendIntent.setType("text/plain");
                                        startActivity(sendIntent);
                                    }
                                }
                            });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    return true;
                }
            });
        }
        setTextViews();
    }

    private void setTextViews() {
        // Reset visibility and status of loadout placeholders
        for (int i = 0; i < 5; i++) {
            loadouts[i].setEnabled(true);
            loadouts[i].setVisibility(View.VISIBLE);
        }

        //Initialize TextViews
        TextView[] player1 = {findViewById(R.id.player1God), findViewById(R.id.player1Build)};
        TextView[] player2 = {findViewById(R.id.player2God), findViewById(R.id.player2Build)};
        TextView[] player3 = {findViewById(R.id.player3God), findViewById(R.id.player3Build)};
        TextView[] player4 = {findViewById(R.id.player4God), findViewById(R.id.player4Build)};
        TextView[] player5 = {findViewById(R.id.player5God), findViewById(R.id.player5Build)};
        TextView[][] players = {player1, player2, player3, player4, player5};

        // Reset the text in the TextViews
        for (int i =0; i < 5; i++) {
            players[i][0].setText("");
            players[i][1].setText("");
        }

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

        // Disable unused loadout placeholders
        for (int i = playerCount; i < 5; i++) {
            loadouts[i].setEnabled(false);
            loadouts[i].setVisibility(View.INVISIBLE);
        }
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
                Team rerolledTeam = stb.generateTeam(playerCount, prefs.getBoolean("KEY_FORCE_OFFENSIVE", false), prefs.getBoolean("KEY_FORCE_DEFENSIVE", false), prefs.getBoolean("KEY_FORCE_BALANCED", true));
                MainActivity.prepareBuildActivity(rerolledTeam);
                setTextViews();
                return true;

            case R.id.action_share:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                String formattedPlayer = "Generated Team:\n";
                for (int i = 0; i < playerCount; i++) {
                    formattedPlayer += "\n" + MainActivity.players.get(i).get(0) + "\n";
                    String[] build = MainActivity.players.get(i).get(1).substring(1, MainActivity.players.get(i).get(1).length()-1).split(", ");
                    for (int j = 0; j < 6; j++) {
                        formattedPlayer += "- " + build[j];
                        if (j != 5) { formattedPlayer += "\n"; }
                    }
                    formattedPlayer += "\n";
                }
                sendIntent.putExtra(Intent.EXTRA_TEXT, formattedPlayer);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

}
