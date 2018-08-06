package com.baconatornoveg.stg.engine;


import android.content.Context;

import com.baconatornoveg.stg.R;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Scanner;


public class SmiteTeamBuilder {

    private final ArrayList<Mage> MAGES = new ArrayList<>();
    private final ArrayList<Guardian> GUARDIANS = new ArrayList<>();
    private final ArrayList<Warrior> WARRIORS = new ArrayList<>();
    private final ArrayList<Assassin> ASSASSINS = new ArrayList<>();
    private final ArrayList<Hunter> HUNTERS = new ArrayList<>();
    private final ArrayList<Item> ITEMS = new ArrayList<>();
    private final ArrayList<Item> BOOTS = new ArrayList<>();
    public ArrayList<String> player1Loadout;
    public ArrayList<String> player2Loadout;
    public ArrayList<String> player3Loadout;
    public ArrayList<String> player4Loadout;
    public ArrayList<String> player5Loadout;
    private Context context;
    private Random rand = new Random();
    private String[] typeRoll = {"Mage", "Guardian", "Warrior", "Assassin", "Hunter"};
    private boolean isForcingOffensive = false;
    private boolean isForcingDefensive = false;
    private boolean isForcingBalanced = true;

    public SmiteTeamBuilder(Context context) {
        this.context = context;
        registerLists();
    }

    public String getEngineVersion() {
        return "1.1.0";
    }

    private void registerLists() {

        Scanner in;

        //Begin parsing the boots CSV file
        in = new Scanner((context.getResources().openRawResource(R.raw.boots)));

        //Skip header row
        in.nextLine();

        while (in.hasNextLine()) {

            String line = in.nextLine();
            String[] values = line.split(",");
            if (values[1].toUpperCase().equals("TRUE")) {
                BOOTS.add(new Item(true, false, values[3], values[0]));
            } else {
                BOOTS.add(new Item(false, true, values[3], values[0]));
            }
        }

        in.close();

        //Begin parsing the gods CSV file
        in = new Scanner((context.getResources().openRawResource(R.raw.gods)));

        //Skip header row
        in.nextLine();

        while (in.hasNextLine()) {

            String line = in.nextLine();
            String[] values = line.split(",");
            switch (values[1]) {

                case "Assassin":
                    ASSASSINS.add(new Assassin(values[0]));
                    break;

                case "Guardian":
                    GUARDIANS.add(new Guardian(values[0]));
                    break;

                case "Hunter":
                    HUNTERS.add(new Hunter(values[0]));
                    break;

                case "Mage":
                    MAGES.add(new Mage(values[0]));
                    break;

                case "Warrior":
                    WARRIORS.add(new Warrior(values[0]));
                    break;

            }
        }


        in.close();

        //Begin parsing the items CSV file
        in = new Scanner(context.getResources().openRawResource(R.raw.items));

        //Skip header row
        in.nextLine();

        while (in.hasNextLine()) {
            String line = in.nextLine();
            String[] values = line.split(",");

            if (values[1].toUpperCase().equals("TRUE") && (values[2].toUpperCase().equals("FALSE"))) {
                ITEMS.add(new Item(true, false, values[3], values[0]));
            } else if (values[1].toUpperCase().equals("FALSE") && (values[2].toUpperCase().equals("TRUE"))) {
                ITEMS.add(new Item(false, true, values[3], values[0]));
            } else {
                ITEMS.add(new Item(true, true, values[3], values[0]));
            }
        }
        in.close();
    }

    public void generateTeam(int size, boolean forceOffensive, boolean forceDefensive, boolean forceBalanced) {
        isForcingOffensive = forceOffensive;
        isForcingDefensive = forceDefensive;
        isForcingBalanced = forceBalanced;
        try {
            resetLoadouts();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        shufflePositions(typeRoll);
        ArrayList<String> loadout;
        Random random = new Random();

        switch (size) {

            case 1:
                loadout = makePlayerLoadout(typeRoll[0]);
                player1Loadout = loadout;
                System.out.println(loadout.get(0) + ": " + loadout.get(1));
                break;

            case 2:
                if (isForcingBalanced) { loadout = makePlayerLoadout(typeRoll[0]); } else { loadout = makePlayerLoadout(typeRoll[random.nextInt(5)]); }
                player1Loadout = loadout;
                System.out.println(loadout.get(0) + ": " + loadout.get(1));
                if (isForcingBalanced) { loadout = makePlayerLoadout(typeRoll[1]); } else {
                    loadout = makePlayerLoadout(typeRoll[random.nextInt(5)]);
                    while (loadout.get(0).equals(player1Loadout.get(0))) {
                        loadout = makePlayerLoadout(typeRoll[random.nextInt(5)]);
                    }
                }
                player2Loadout = loadout;
                System.out.println(loadout.get(0) + ": " + loadout.get(1));
                break;

            case 3:
                if (isForcingBalanced) { loadout = makePlayerLoadout(typeRoll[0]); } else { loadout = makePlayerLoadout(typeRoll[random.nextInt(5)]); }
                player1Loadout = loadout;
                System.out.println(loadout.get(0) + ": " + loadout.get(1));
                if (isForcingBalanced) { loadout = makePlayerLoadout(typeRoll[1]); } else {
                    loadout = makePlayerLoadout(typeRoll[random.nextInt(5)]);
                    while (loadout.get(0).equals(player1Loadout.get(0))) {
                        loadout = makePlayerLoadout(typeRoll[random.nextInt(5)]);
                    }
                }
                player2Loadout = loadout;
                System.out.println(loadout.get(0) + ": " + loadout.get(1));
                if (isForcingBalanced) { loadout = makePlayerLoadout(typeRoll[2]); } else {
                    loadout = makePlayerLoadout(typeRoll[random.nextInt(5)]);
                    while (loadout.get(0).equals(player1Loadout.get(0)) || loadout.get(0).equals(player2Loadout.get(0))) {
                        loadout = makePlayerLoadout(typeRoll[random.nextInt(5)]);
                    }
                }
                player3Loadout = loadout;
                System.out.println(loadout.get(0) + ": " + loadout.get(1));
                break;

            case 4:
                if (isForcingBalanced) { loadout = makePlayerLoadout(typeRoll[0]); } else { loadout = makePlayerLoadout(typeRoll[random.nextInt(5)]); }
                player1Loadout = loadout;
                System.out.println(loadout.get(0) + ": " + loadout.get(1));
                if (isForcingBalanced) { loadout = makePlayerLoadout(typeRoll[1]); } else {
                    loadout = makePlayerLoadout(typeRoll[random.nextInt(5)]);
                    while (loadout.get(0).equals(player1Loadout.get(0))) {
                        loadout = makePlayerLoadout(typeRoll[random.nextInt(5)]);
                    }
                }
                player2Loadout = loadout;
                System.out.println(loadout.get(0) + ": " + loadout.get(1));
                if (isForcingBalanced) { loadout = makePlayerLoadout(typeRoll[2]); } else {
                    loadout = makePlayerLoadout(typeRoll[random.nextInt(5)]);
                    while (loadout.get(0).equals(player1Loadout.get(0)) || loadout.get(0).equals(player2Loadout.get(0))) {
                        loadout = makePlayerLoadout(typeRoll[random.nextInt(5)]);
                    }
                }
                player3Loadout = loadout;
                System.out.println(loadout.get(0) + ": " + loadout.get(1));
                if (isForcingBalanced) { loadout = makePlayerLoadout(typeRoll[3]); } else {
                    loadout = makePlayerLoadout(typeRoll[random.nextInt(5)]);
                    while (loadout.get(0).equals(player1Loadout.get(0)) || loadout.get(0).equals(player2Loadout.get(0)) || loadout.get(0).equals(player3Loadout.get(0))) {
                        loadout = makePlayerLoadout(typeRoll[random.nextInt(5)]);
                    }
                }
                player4Loadout = loadout;
                System.out.println(loadout.get(0) + ": " + loadout.get(1));
                break;

            case 5:
                if (isForcingBalanced) { loadout = makePlayerLoadout(typeRoll[0]); } else { loadout = makePlayerLoadout(typeRoll[random.nextInt(5)]); }
                player1Loadout = loadout;
                System.out.println(loadout.get(0) + ": " + loadout.get(1));
                if (isForcingBalanced) { loadout = makePlayerLoadout(typeRoll[1]); } else {
                    loadout = makePlayerLoadout(typeRoll[random.nextInt(5)]);
                    while (loadout.get(0).equals(player1Loadout.get(0))) {
                        loadout = makePlayerLoadout(typeRoll[random.nextInt(5)]);
                    }
                }
                player2Loadout = loadout;
                System.out.println(loadout.get(0) + ": " + loadout.get(1));
                if (isForcingBalanced) { loadout = makePlayerLoadout(typeRoll[2]); } else {
                    loadout = makePlayerLoadout(typeRoll[random.nextInt(5)]);
                    while (loadout.get(0).equals(player1Loadout.get(0)) || loadout.get(0).equals(player2Loadout.get(0))) {
                        loadout = makePlayerLoadout(typeRoll[random.nextInt(5)]);
                    }
                }
                player3Loadout = loadout;
                System.out.println(loadout.get(0) + ": " + loadout.get(1));
                if (isForcingBalanced) { loadout = makePlayerLoadout(typeRoll[3]); } else {
                    loadout = makePlayerLoadout(typeRoll[random.nextInt(5)]);
                    while (loadout.get(0).equals(player1Loadout.get(0)) || loadout.get(0).equals(player2Loadout.get(0)) || loadout.get(0).equals(player3Loadout.get(0))) {
                        loadout = makePlayerLoadout(typeRoll[random.nextInt(5)]);
                    }
                }
                player4Loadout = loadout;
                System.out.println(loadout.get(0) + ": " + loadout.get(1));
                if (isForcingBalanced) { loadout = makePlayerLoadout(typeRoll[4]); } else {
                    loadout = makePlayerLoadout(typeRoll[random.nextInt(5)]);
                    while (loadout.get(0).equals(player1Loadout.get(0)) || loadout.get(0).equals(player2Loadout.get(0)) || loadout.get(0).equals(player3Loadout.get(0)) || loadout.get(0).equals(player4Loadout.get(0))) {
                        loadout = makePlayerLoadout(typeRoll[random.nextInt(5)]);
                    }
                }
                player5Loadout = loadout;
                System.out.println(loadout.get(0) + ": " + loadout.get(1));
                break;
        }

    }

    private void resetLoadouts() {
        player1Loadout = null;
        player2Loadout = null;
        player3Loadout = null;
        player4Loadout = null;
        player5Loadout = null;
    }

    private void shufflePositions(String[] positions) {

        int n = positions.length;
        Random random = new Random();
        random.nextInt();
        for (int i = 0; i < n; i++) {
            int change = i + random.nextInt(n - i);
            swap(positions, i, change);
        }

    }

    private void swap(String[] a, int i, int change) {
        String helper = a[i];
        a[i] = a[change];
        a[change] = helper;
    }

    private ArrayList<String> makePlayerLoadout(String position) {
        String player = null;
        String playerBuild = null;
        ArrayList<Item> build;

        switch (position) {

            case "Mage":
                player = MAGES.get(rand.nextInt(MAGES.size() - 1)).toString();
                build = generateBuild("mage", "magical", false);
                if (isForcingOffensive) {
                    while (true) {
                        int offensiveCount = 0;
                        for (Item i : build) {
                            if (i.isOffensive()) {
                                offensiveCount++;
                            }
                        }
                        if (offensiveCount < 5) {
                            build = generateBuild("mage", "magical", false);
                        } else {
                            break;
                        }
                    }
                }
                playerBuild = build.toString();
                break;

            case "Guardian":
                player = GUARDIANS.get(rand.nextInt(GUARDIANS.size() - 1)).toString();
                build = generateBuild("guardian", "magical", false);
                if (isForcingDefensive) {
                    while (true) {
                        int defensiveCount = 0;
                        for (Item i : build) {
                            if (i.isDefensive()) {
                                defensiveCount++;
                            }
                        }
                        if (defensiveCount < 5) {
                            build = generateBuild("guardian", "magical", false);
                        } else {
                            break;
                        }
                    }
                }
                playerBuild = build.toString();
                break;

            case "Warrior":
                player = WARRIORS.get(rand.nextInt(WARRIORS.size() - 1)).toString();
                build = generateBuild("warrior", "physical", false);
                if (isForcingOffensive) {
                    while (true) {
                        int offensiveCount = 0;
                        for (Item i : build) {
                            if (i.isOffensive()) {
                                offensiveCount++;
                            }
                        }
                        if (offensiveCount < 3) {
                            build = generateBuild("warrior", "physical", false);
                        } else {
                            break;
                        }
                    }
                }
                playerBuild = build.toString();
                break;

            case "Assassin":
                player = ASSASSINS.get(rand.nextInt(ASSASSINS.size() - 1)).toString();
                build = generateBuild("assassin", "physical", (player.equals("Ratatoskr")));
                if (isForcingOffensive) {
                    while (true) {
                        int offensiveCount = 0;
                        for (Item i : build) {
                            if (i.isOffensive()) {
                                offensiveCount++;
                            }
                        }
                        if (offensiveCount < 5) {
                            build = generateBuild("assassin", "physical", (player.equals("Ratatoskr")));
                        } else {
                            break;
                        }
                    }
                }
                playerBuild = build.toString();
                break;

            case "Hunter":
                player = HUNTERS.get(rand.nextInt(HUNTERS.size() - 1)).toString();
                build = generateBuild("hunter", "physical", false);
                if (isForcingOffensive) {
                    while (true) {
                        int offensiveCount = 0;
                        for (Item i : build) {
                            if (i.isOffensive()) {
                                offensiveCount++;
                            }
                        }
                        if (offensiveCount < 5) {
                            build = generateBuild("hunter", "physical", false);
                        } else {
                            break;
                        }
                    }
                }
                playerBuild = build.toString();
                break;

        }

        ArrayList<String> loadout = new ArrayList<>();
        loadout.add(player);
        loadout.add(playerBuild);

        return loadout;

    }

    private ArrayList<Item> generateBuild(String god, String type, boolean isRatatoskr) {
        ArrayList<Item> build = new ArrayList<>();
        LinkedHashSet<Item> generation = new LinkedHashSet<>();
        Boolean matches;
        Item newItem;
        if (type.equals("physical")) {
            switch (god) {
                case "assassin":
                case "hunter":
                    if (isRatatoskr) {
                        generation.add(new Item(true, false, "OFFENSE", "Acorn of Yggdrasil"));
                    } else {
                        generation.add(getPhysicalBoot(isForcingOffensive));
                    }
                    break;
                case "warrior":
                    generation.add(getPhysicalBoot(false));
                    break;
            }
            for (int i = 0; i < 5; i++) {
                newItem = getPhysicalItem();
                generation.add(newItem);
            }

            while (generation.size() < 6) {
                newItem = getPhysicalItem();
                generation.add(newItem);
            }

            build.addAll(0, generation);
        } else {
            switch (god) {
                case "mage":
                    generation.add(getMagicalBoot(isForcingOffensive, false));
                    break;
                case "guardian":
                    generation.add(getMagicalBoot(false, true));
                    break;
            }
            for (int i = 0; i < 5; i++) {
                newItem = getMagicalItem();
                generation.add(newItem);
            }

            while (generation.size() < 6) {
                newItem = getMagicalItem();
                generation.add(newItem);
            }
            build.addAll(0, generation);
        }
        return build;
    }

    private Item getPhysicalBoot(boolean isOffensive) {
        Item boot;
        boot = BOOTS.get((rand.nextInt(BOOTS.size())));
        if (isOffensive) {
            while (boot.isMagical() || !boot.isOffensive()) {
                boot = BOOTS.get((rand.nextInt(BOOTS.size())));
            }
        } else {
            while (boot.isMagical()) {
                boot = BOOTS.get((rand.nextInt(BOOTS.size())));
            }
        }
        return boot;
    }

    private Item getMagicalBoot(boolean isOffensive, boolean isDefensive) {
        Item boot;
        boot = BOOTS.get((rand.nextInt(BOOTS.size())));
        if (isOffensive) {
            while (boot.isPhysical() || !boot.isOffensive()) {
                boot = BOOTS.get((rand.nextInt(BOOTS.size())));
            }
        } else if (isDefensive) {
            while (boot.isPhysical() || !boot.isDefensive()) {
                boot = BOOTS.get((rand.nextInt(BOOTS.size())));
            }
        } else {
            while (boot.isPhysical()) {
                boot = BOOTS.get((rand.nextInt(BOOTS.size())));
            }
        }
        return boot;
    }

    private Item getPhysicalItem() {
        Item item = ITEMS.get((int) (Math.random() * (ITEMS.size() - 1) + 1));
        while (item.isMagical()) {
            item = ITEMS.get((int) (Math.random() * (ITEMS.size() - 1) + 1));
        }
        return item;
    }

    private Item getMagicalItem() {
        Item item = ITEMS.get((int) (Math.random() * (ITEMS.size() - 1) + 1));
        while (item.isPhysical()) {
            item = ITEMS.get((int) (Math.random() * (ITEMS.size() - 1) + 1));
        }
        return item;
    }

}
