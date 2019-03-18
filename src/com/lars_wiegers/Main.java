package com.lars_wiegers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import com.lars_wiegers.Battles.*;

public class Main {
    static final int EXIT_MENU_OPTION = 3;

    public static void main(String[] args) {
        (new Main()).run(args);
    }

    private void run(String[] args) {
        if(args.length > 0) {
            this.handleArguments(args);
        }
        int menuOption;

        while (true) {
            displayMenu();
            menuOption = askForMenuChoice();
            switch (menuOption) {
                case 1:
                    System.out.print("Please give me a battle file: ");
                    Scanner scanner = new Scanner(System.in);
                    String battleFilePath = scanner.nextLine();
                    BattleFieldFile battleFile;
                    try {
                        battleFile = new BattleFieldFile(battleFilePath);
                    } catch (BattleFieldFileDoesNotExistException e) {
                        System.out.println("The file you gave does not exist!");
                        continue;
                    } catch (BattleFieldFileIsAdirectoryException e) {
                        System.out.println("The file you gave is a directory!");
                        continue;
                    } catch (BattleFieldFileDoesNotHaveTheRightExtension battleFieldFileDoesNotHaveTheRightExtension) {
                        System.out.println("The file you gave does not have the right extension (.battle)!");
                        continue;
                    }
                    System.out.print("would you like to run this battle ");
                    if(askForYesOrNo()) {
                        System.out.println("Running battle now!");
                        Battle battle = new Battle(battleFile, "single_battle");
                        battle.start();
                        printResults(battle);
                        battle.stop();
                    }else {
                        System.out.println("Okay, your file is correct!");
                    }
                    break;
                case 2:
                    ArrayList<BattleFieldFile> battleFiles = new ArrayList<>();
                    File folder = new File("./battles");
                    File[] listOfFiles = folder.listFiles();

                    assert listOfFiles != null;
                    for (File file : listOfFiles) {
                        if (file.isFile()) {
                            try {
                                BattleFieldFile battleFieldFile = new BattleFieldFile(file.getPath());
                                battleFiles.add(battleFieldFile);
                            } catch (
                                    BattleFieldFileDoesNotExistException |
                                    BattleFieldFileIsAdirectoryException |
                                    BattleFieldFileDoesNotHaveTheRightExtension ignored) {
                            }
                        }
                    }
                    for (BattleFieldFile file : battleFiles ) {
                        System.out.println(file);
                    }

                    System.out.print("would you like to run these battles ");
                    if(askForYesOrNo()) {
                        System.out.println("Running them now!");
                        ArrayList<Battle> battles = new ArrayList<>();
                        for (BattleFieldFile file : battleFiles ) {
                            battles.add(new Battle(file, file.toString()));
                        }
                        for (Battle battle: battles) {
                            battle.start();
                        }
                        for (Battle battle: battles) {
                                printResults(battle);
                        }
                        printAverages(Battle.calcAverageOfResults(battles));
                        for (Battle battle: battles) {
                            battle.stop();
                        }
                    }else {
                        System.out.println("Okay, that's fine.");
                    }

                    break;
                case 3:
                    System.out.println("Thank you for using this program!");
                    System.exit(0);
                    break;
            }
        }
    }

    private void handleArguments(String[] args) {
        if(args[0].equals("--debugMode=true")) {
            Battle.debugMode = true;
        }else if(args[0].equals("--debugMode=false")) {
            Battle.debugMode = false;
        }
    }

    /**
     *
     * @param results An ArrayList that contains 1 or more result objects
     */
    private void printAverages(ArrayList<Result> results) {
        String leftAlignFormat = "| %-25s | %-12s | %-8s |%n";
        System.out.format("+---------------------------+--------------+----------+%n");
        System.out.format("| %-51s |%n", "Averages");
        System.out.format("+---------------------------+--------------+----------+%n");
        System.out.format("| Robot name                | Score        | Survival |%n");
        System.out.format("+---------------------------+--------------+----------+%n");

        for (Result result: results) {
            System.out.format(leftAlignFormat,
                    result.getName(),
                    result.getScore(),
                    result.getSurvival()
            );
        }

        System.out.format("+---------------------------+--------------+----------+%n");
        System.out.println();
    }

    /**
     *
     * @param battle an Battle object
     */
    private void printResults(Battle battle) {
        String leftAlignFormat = "| %-25s | %-12s | %-8s |%n";
        System.out.format("+---------------------------+--------------+----------+%n");
        System.out.format("| %-51s |%n", battle.getBattleFile().getFile().getName());
        System.out.format("+---------------------------+--------------+----------+%n");
        System.out.format("| Robot name                | Score        | Survival |%n");
        System.out.format("+---------------------------+--------------+----------+%n");

        try {
            for (Result result: battle.getResults()) {
                System.out.format(leftAlignFormat,
                        result.getName(),
                        result.getScore(),
                        result.getSurvival()
                );
            }
        } catch (BattleHasNotBeenStartedException e) {
            System.out.format("| %-51s |%n", "It seems like the battle has not been started");
        }

        System.out.format("+---------------------------+--------------+----------+%n");
        System.out.println();
    }

    /**
     * Append 'yes or no?' to the system.out and check for a answer that we can accept
     *      *
     * @return bool if yes return true else return false
     */
    private boolean askForYesOrNo() {
        Scanner scanner = new Scanner(System.in);
        String yesOrNo;
        while (true) {
            System.out.print("yes or no?");
            yesOrNo = scanner.nextLine();

            // this assumes that the last option always is the exit option
            if (yesOrNo.equals("yes") || yesOrNo.equals("no")) {
                return yesOrNo.equals("yes");
            }else {
                System.out.println("huh?");
            }
        }
    }

    /**
     * Get the option of the menu items that the user wants to select
     *
     * @return int menu choice
     */
    private int askForMenuChoice() {
        Scanner scanner = new Scanner(System.in);
        int menuOption;
        while (true) {
            System.out.println("Please choice a option: ");
            menuOption = scanner.nextInt();

            // this assumes that the last option always is the exit option
            if (menuOption > 0 && menuOption < (EXIT_MENU_OPTION + 1)) {
                return menuOption;
            }
        }
    }

    /**
     * Write the menu to the system.out
     */
    private void displayMenu() {
        System.out.println("**********************************");
        System.out.println("***   Robocode Tester menu     ***");
        System.out.println("**********************************");
        System.out.println("*1)   Single Battle !!!        ***");
        System.out.println("**********************************");
        System.out.println("*2)   Multi Battle !!!         ***");
        System.out.println("**********************************");
        System.out.println("*3    Exit                     ***");
        System.out.println("**********************************");
    }
}
