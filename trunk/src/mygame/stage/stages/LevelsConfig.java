/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mygame.stage.stages;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import mygame.model.Level;

/**
 *
 * @author dgrandes
 */
public class LevelsConfig {

    public HashMap<Integer, Level> levels;

    public LevelsConfig()
    {
    }

    public LevelsConfig(String filename)
    {
        initWith(filename);
    }

    public static LevelsConfig loadFromFile(String filename)
    {
        StringBuilder levelsJSON = new StringBuilder();
        String read;
        try{
            FileReader fstream;
            fstream = new FileReader(filename);
            BufferedReader in = new BufferedReader(fstream);
            while((read = in.readLine()) != null){
                levelsJSON.append(read);
            }
            LevelsConfig levelsconfig = new Gson().fromJson(levelsJSON.toString(), LevelsConfig.class);
            return levelsconfig;

        }
        catch(Exception e)
        {
            System.err.println("Levels Config failed to load "+ e.getMessage());
        }
        return null;

    }

    public void initWith(String filename)
    {
        LevelsConfig levelsConfig = loadFromFile(filename);
        this.levels = new HashMap<Integer, Level>(levelsConfig.levels);
    }

}
