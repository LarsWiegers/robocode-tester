package com.lars_wiegers.Battles;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Battle {
    private final String resultFilePath;
    private BattleFieldFile battleFile;
    private boolean startHasBeenCalled = false;
    public static boolean debugMode = false;

    public Battle(BattleFieldFile battleFile, String resultFileName) {
        this.setFile(battleFile);
        this.resultFilePath = "temp_results_" + resultFileName + ".txt";
    }

    private void setFile(BattleFieldFile battleFile) {
        this.battleFile = battleFile;
    }

    public BattleFieldFile getBattleFile() {
        return this.battleFile;
    }


    public void start() {
        this.startHasBeenCalled = true;
        // TODO clean this up
        ProcessBuilder builder = new ProcessBuilder(
                "cmd.exe", "/c", "cd " + System.getProperty("user.dir") +
                " && java -Xmx512M -Ddebug=true -Dsun.io.useCanonCaches=false -cp libs/robocode.jar"+
                " robocode.Robocode -battle " + this.getBattleFile().getFile() + " -nodisplay -results " + this.resultFilePath);
        builder.redirectErrorStream(true);
        Process p = null;

        try {
            p = builder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }


        BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line = null;
        while (true) {
            try {
                line = r.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (line == null) {
                break;
            }
            if(debugMode) {
                System.out.println(line);
            }
        }
    }

    public ArrayList<Result> getResults() throws BattleHasNotBeenStartedException {
        if(!this.startHasBeenCalled){
            throw new BattleHasNotBeenStartedException();
        }
        ResultFile resultFile = new ResultFile(this.resultFilePath);
        return resultFile.getResults();
    }

    public void stop() {
        File file = new File(this.resultFilePath);
        file.delete();
    }
}
