package com.lars_wiegers.Battles;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class BattleFieldFile {
    static final String TEMP_FILE = "temp.txt";
    File file;

    public BattleFieldFile(String battleFilePath)
            throws
            BattleFieldFileDoesNotExistException,
            BattleFieldFileIsAdirectoryException,
            BattleFieldFileDoesNotHaveTheRightExtension {
        File file = new File(battleFilePath);

        if (!file.exists())
            throw new BattleFieldFileDoesNotExistException();
        if (file.isDirectory())
            throw new BattleFieldFileIsAdirectoryException();

        // Check if file is in the right format
        if (!getFileExtension(file).equals("battle")) {
            System.out.println(getFileExtension(file));
            throw new BattleFieldFileDoesNotHaveTheRightExtension();
        }

        this.setFile(file);
    }

    private void setFile(File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
    }

    private static String getFileExtension(File file) {
        String fileName = file.getName();
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        else return "";
    }

    @Override
    public String toString() {
        return this.file.getName();
    }
}
