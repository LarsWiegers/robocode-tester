package com.lars_wiegers.Battles;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ResultFile {
    private String resultFilePath;

    public ResultFile(String resultFilePath) {
        this.setResultFilePath(resultFilePath);
    }

    private void setResultFilePath(String resultFilePath) {
        this.resultFilePath = resultFilePath;
    }

    public ArrayList<Result> getResults() {
        ArrayList<Result> results = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(this.getResultFilePath());
            for (int i = 2; i < lines.size(); i++) {
                results.add(new Result(lines.get(i)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return results;
    }

    private Path getResultFilePath() {
        Path path = FileSystems.getDefault().getPath(this.resultFilePath);
        return path;
    }
}
