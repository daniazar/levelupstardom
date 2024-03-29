/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mygame.stage.stages;

import mygame.model.Hazard;
import com.google.gson.Gson;
import com.jme3.math.Vector3f;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import mygame.model.BrickWall;
import mygame.model.Goal;
import mygame.model.Interruptor;

/**
 *
 * @author dgrandes
 */
public class LevelFoundation {

    public String scenefile;
    public String scenetype;
    public String playermesh;
    public float capsuleradius;
    public float playerspeed;
    public Vector3f spawnpoint;
    public Goal goal;
    public List<Vector3f> pointLightPositions;
    public HashMap<Integer, PickableSpheres> spheres;
    public ArrayList<Integer> spheresToRemove = new ArrayList<Integer>();
    public HashMap<Integer, Hazard> hazards;
    public float time;
    public HashMap<Integer, Interruptor> interruptors = new HashMap<Integer, Interruptor>();
    public HashMap<Integer, BrickWall> brickWalls = new HashMap<Integer, BrickWall>();

    public LevelFoundation(){
    }

    public LevelFoundation(String filename)
    {
        initWith(filename);
    }

    public LevelFoundation(String scenefile, String scenetype, String playermesh,
            float capsuleradius, float playerspeed, Vector3f spawnpoint, Goal goal,
            HashMap<Integer, PickableSpheres> spheres,
            HashMap<Integer, Hazard> hazards, float time,
            HashMap<Integer, Interruptor> interruptors, HashMap<Integer, BrickWall> brickWalls)
    {
        this.scenefile = scenefile;
        this.scenetype = scenetype;
        this.playermesh = playermesh;
        this.time = time;
        this.capsuleradius = capsuleradius;
        this.playerspeed = playerspeed;
        this.spawnpoint = new Vector3f(spawnpoint);
        this.goal = goal;
        this.spheres = new HashMap<Integer, PickableSpheres>(spheres);
        this.hazards = new HashMap<Integer, Hazard>(hazards);
        this.interruptors = interruptors;
        this.brickWalls = brickWalls;
    }
    
    public static LevelFoundation loadFromFile(String filename)
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
            LevelFoundation levelF = new Gson().fromJson(levelsJSON.toString(), LevelFoundation.class);
            return levelF;

        }
        catch(Exception e)
        {
            System.err.println("Level Foundation failed to load "+ e.getMessage());
        }
        return null;

    }

     public void initWith(String filename)
    {
        LevelFoundation levelsF = loadFromFile(filename);
        this.capsuleradius = levelsF.capsuleradius;
        this.goal = levelsF.goal;
        this.playermesh = levelsF.playermesh;
        this.playerspeed = levelsF.playerspeed;
        this.scenefile = levelsF.scenefile;
        this.scenetype = levelsF.scenetype;
        this.spawnpoint = levelsF.spawnpoint;
        this.pointLightPositions = levelsF.pointLightPositions;
        this.hazards = new HashMap<Integer, Hazard>(levelsF.hazards);
        this.spheres = new HashMap<Integer, PickableSpheres>(levelsF.spheres);
        this.interruptors = new HashMap<Integer, Interruptor>(levelsF.interruptors);
        this.brickWalls = new HashMap<Integer, BrickWall>(levelsF.brickWalls);

        this.time = time;
    }

     public void markSphereforRemoval(Integer index)
    {
        spheresToRemove.add(index);
     }
     public void updatePickableSpheres()
    {
       for(Integer i : spheresToRemove)
       {
        spheres.remove(i);
       }
       spheresToRemove.clear();
        
     }


}
