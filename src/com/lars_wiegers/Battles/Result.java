package com.lars_wiegers.Battles;

public class Result {
    private String name;
    private String score;
    private String survival;

    public Result(String line) {
        String[] sections = line.split("\t");
        this.setName(sections[0]);
        this.setScore(sections[1]);
        this.setSurvival(sections[2]);
    }

    private void setScore(String score) {
        this.score = score;
    }

    private void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getScore() {
        return score;
    }

    public void setSurvival(String survival) {
        this.survival = survival;
    }

    public String getSurvival() {
        return survival;
    }
}
