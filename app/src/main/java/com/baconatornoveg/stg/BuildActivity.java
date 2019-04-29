package com.baconatornoveg.stg;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.baconatornoveg.stg.components.LoadoutLongClickListener;
import com.baconatornoveg.stg.components.PlayerBuildTextView;
import com.baconatornoveg.stg.components.PlayerLoadout;
import com.baconatornoveg.stg.components.PlayerTextView;
import com.baconatornoveg.stg.components.RelicsLayout;
import com.baconatornoveg.stglib.SmiteTeamGenerator;
import com.baconatornoveg.stglib.Team;

public class BuildActivity extends AppCompatActivity {

    private SmiteTeamGenerator stb = MainActivity.stg;
    private int playerCount = MainActivity.numPlayers;
    LinearLayout loadouts;
    private Team team = MainActivity.generatedTeam;
    boolean relicsEnabled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_build);
        loadouts = findViewById(R.id.loadouts);
        relicsEnabled = PreferenceManager.getDefaultSharedPreferences(this).getBoolean("KEY_RELICS_ENABLED", true);
        setTextViews();
    }

    public void setTextViews() {
        // Reset visibility and status of loadout placeholders
        loadouts.removeAllViews();

        //Set up the TextViews
        for (int i = 0; i < playerCount; i++) {
            PlayerLoadout playerLoadout = new PlayerLoadout(this);
            PlayerTextView playerTextView = new PlayerTextView(this, MainActivity.players.get(i).get(0));
            playerLoadout.addView(playerTextView);
            if (relicsEnabled) {
                RelicsLayout relicsLayout = new RelicsLayout(this);
                for (int j = 0; j < 2; j++) {
                    PlayerBuildTextView playerBuildTextView = new PlayerBuildTextView(this, team.getPlayer(i).getRelics().get(j), 1);
                    relicsLayout.addView(playerBuildTextView);
                }
                playerLoadout.addView(relicsLayout);
            }
            for (int j = 0; j < 6; j++) {
                PlayerBuildTextView playerBuildTextView = new PlayerBuildTextView(this, team.getPlayer(i).getBuild().get(j), 0);
                playerLoadout.addView(playerBuildTextView);
            }
            playerLoadout.setOnLongClickListener(new LoadoutLongClickListener(this, team.getPlayer(i), team, i, this, relicsEnabled));
            loadouts.addView(playerLoadout);
        }
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
                int buildType = Integer.parseInt(prefs.getString("KEY_BUILD_TYPE", "0"));
                boolean forcingBalanced = prefs.getBoolean("KEY_FORCE_BALANCED", true);
                boolean forcingBoots = prefs.getBoolean("KEY_FORCE_BOOTS", true);
                stb.warriorsOffensive = prefs.getBoolean("KEY_WARRIORS_OFF", false);
                Team rerolledTeam = stb.generateTeam(playerCount, forcingBalanced, forcingBoots, buildType);
                team = rerolledTeam;
                MainActivity.prepareBuildActivity(rerolledTeam);
                setTextViews();
                return true;

            case R.id.action_share:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                String formattedPlayer = "Generated Team:\n";
                for (int i = 0; i < playerCount; i++) {
                    formattedPlayer += "\n" + team.getPlayer(i).getGod().toString() + "\n";
                    String[] build = team.getPlayer(i).getBuildAsStringArray();
                    for (int j = 0; j < 6; j++) {
                        formattedPlayer += "- " + build[j];
                        if (j != 5) { formattedPlayer += "\n"; }
                    }
                    if (relicsEnabled) {
                        String[] relics = team.getPlayer(i).getRelicsAsStringArray();
                        formattedPlayer += "\n\nRelics: ";
                        for (int j = 0; j < 2; j++) {
                            formattedPlayer += relics[j];
                            if (j != 1) { formattedPlayer += "\n"; }
                        }
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
