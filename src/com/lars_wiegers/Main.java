package com.lars_wiegers;

import java.util.Scanner;
import com.lars_wiegers.Battles.BattleFieldFile;
import com.lars_wiegers.Battles.BattleFieldFileDoesNotExistException;
import com.lars_wiegers.Battles.BattleFieldFileDoesNotHaveTheRightExtension;
import com.lars_wiegers.Battles.BattleFieldFileIsAdirectoryException;

public class Main {
    private int exitMenuoption = 6;
    public static void main(String[] args) {
        (new Main()).run();
    }
    private void run() {
        int menuOption = 0;

        while(menuOption != this.exitMenuoption) {
            displayMenu();
            menuOption = askForMenuChoice();
            switch(menuOption) {
                case 1:
                    System.out.print("Please give me a battle file: ");
                    Scanner scanner = new Scanner(System.in);
                    String battleFilePath = scanner.nextLine();
                    try {
                        BattleFieldFile file = new BattleFieldFile(battleFilePath);
                    } catch (BattleFieldFileDoesNotExistException e) {
                        System.out.println("The file you gave does not exist!");
                    } catch (BattleFieldFileIsAdirectoryException e) {
                        System.out.println("The file you gave is a directory!");
                    } catch (BattleFieldFileDoesNotHaveTheRightExtension battleFieldFileDoesNotHaveTheRightExtension) {
                        System.out.println("The file you gave does not have the right extension (.battle)!");
                    }
                    break;
                case 2:
                    break;
                case 3:
                    System.out.println("Thank you for using this program!");
                    System.exit(0);
                    break;
            }
        }
    }

    private int askForMenuChoice() {
        Scanner scanner = new Scanner(System.in);
        int menuOption;
        while(true) {
            System.out.println("Please choice a option: ");
            menuOption = scanner.nextInt();

            // this assumes that the last option always is the exit option
            if(menuOption > 0 && menuOption < (this.exitMenuoption + 1)) {
                return menuOption;
            }
        }
    }

    private void displayMenu() {
        System.out.println("**********************************");
        System.out.println("***   Robocode Tester menu     ***");
        System.out.println("**********************************");
        System.out.println("*1)   Battlefield file check   ***");
        System.out.println("**********************************");
        System.out.println("*2)   Battle !!!               ***");
        System.out.println("**********************************");
        System.out.println("*3    Exit                     ***");
        System.out.println("**********************************");
    }
}
