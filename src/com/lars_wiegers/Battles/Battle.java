package com.lars_wiegers.Battles;

import com.sun.deploy.util.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Battle {
    private final String resultFilePath;
    private BattleFieldFile battleFile;
    private boolean startHasBeenCalled = false;
    public static boolean debugMode = false;

    public Battle(BattleFieldFile battleFile, String resultFileName) {
        this.setFile(battleFile);
        this.resultFilePath = "temp_results_" + resultFileName + ".txt";
    }

    public static ArrayList<Result> calcAverageOfResults(ArrayList<Battle> battles) {
        ArrayList<Integer> scores = new ArrayList<>();
        HashMap<String, ArrayList<Result>> results = new HashMap<>();
        for (Battle battle: battles ) {
            try {

                for (Result result : battle.getResults()) {
                    String score = result.getScore();
                    int percentageScore = Integer.parseInt(
                            score.substring(score.indexOf("(") + 1, score.indexOf("%)"))
                    );
                    ArrayList<Result> currentBattleResults = results.get(result.getName());
                    if(currentBattleResults == null) {
                        currentBattleResults = new ArrayList<>();
                    }
                    currentBattleResults.add(result);
                    results.put(result.getName(), currentBattleResults);
                    scores.add(percentageScore);
                }
            } catch (BattleHasNotBeenStartedException e) {
                e.printStackTrace();
            }
        }

        ArrayList<Result> test = new ArrayList<>();
        Iterator it = results.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            int totalScore = 0;
            int totalServival = 0;
            for (int i = 0; i < results.get(pair.getKey()).size(); i++) {
                String score = results.get(pair.getKey()).get(i).getScore();
                String survival = results.get(pair.getKey()).get(i).getSurvival();
                totalScore += Integer.parseInt(
                        score.substring(score.indexOf("(") + 1, score.indexOf("%)")));
                totalServival += Integer.parseInt(survival);
            }
            Result result = new Result("" + pair.getKey(),
                    "" + (totalScore/
                            results.get(pair.getKey()).size()) + "%",
                    "" + (totalServival/
                            results.get(pair.getKey()).size()));
            test.add(result);
            it.remove();
        }

        return test;
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
//        File file = new File(this.resultFilePath);
//        file.delete();
    }
}
