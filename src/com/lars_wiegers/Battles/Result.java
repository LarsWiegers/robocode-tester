package com.lars_wiegers.Battles;

public class Result {
    private String name;
    private String score;
    private String survival;

    public Result(String line) {
        this(
            line.split(" ")[1].split("\t")[0],
            line.split("\t")[1],
            line.split("\t")[2]
        );
    }
    public Result(String name, String score, String surival) {
        this.setName(name);
        this.setScore(score);
        this.setSurvival(surival);
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
