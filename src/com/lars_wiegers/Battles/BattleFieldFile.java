package com.lars_wiegers.Battles;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class BattleFieldFile {
    public BattleFieldFile(String battleFilePath)
            throws
            BattleFieldFileDoesNotExistException,
            BattleFieldFileIsAdirectoryException,
            BattleFieldFileDoesNotHaveTheRightExtension
    {
        File file = new File(battleFilePath);

        if(!file.exists())
            throw new BattleFieldFileDoesNotExistException();
        if(file.isDirectory())
            throw new BattleFieldFileIsAdirectoryException();

        // check if file is in the right format
        if(!getFileExtension(file).equals("battle")) {
            System.out.println(getFileExtension(file));
            throw new BattleFieldFileDoesNotHaveTheRightExtension();
        }
        // TODO clean this up
        ProcessBuilder builder = new ProcessBuilder(
                "cmd.exe", "/c", "cd \"D:\\Code\\RobocodeTester\"" +
                "&& java -Xmx512M -Ddebug=true -Dsun.io.useCanonCaches=false -cp libs/robocode.jar robocode.Robocode -battle " + file.toPath() + " -nodisplay -results temp\\temp.txt");
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
            if (line == null) { break; }
            System.out.println(line);
        }

        // throw an exception if it is not right
    }
    private static String getFileExtension(File file)
    {
        String fileName = file.getName();
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            return fileName.substring(fileName.lastIndexOf(".")+1);
        else return "";
    }
}
