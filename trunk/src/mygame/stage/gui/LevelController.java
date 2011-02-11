/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mygame.stage.gui;

import com.jme3.font.BitmapText;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import mygame.stage.GameStageEnvironment;
import mygame.stage.scene.SceneObject;

/**
 *
 * @author Dani
 */
public class LevelController extends Node{

    private SceneObject player, levelEnd;
    public Timer timer;
    private BitmapText objects, distance;
    private int objectCounter = 0 ,totalObjects;

    public LevelController(GameStageEnvironment env, SceneObject player, SceneObject levelEnd, int totalObjects){

        this.levelEnd = levelEnd;
        this.player = player;
        this.totalObjects = totalObjects;


        objects  = new BitmapText(env.getAssetManager().loadFont("Interface/Fonts/Default.fnt"), false);
        distance = new BitmapText(env.getAssetManager().loadFont("Interface/Fonts/Default.fnt"), false);
        updateObjectsText();
        objects.setLocalTranslation( 40, env.getScreenSize().height - 40, 0);
        distance.setLocalTranslation( 40, env.getScreenSize().height , 0);
        timer = new Timer(env, this);
        attachChild(objects);
        attachChild(timer);
        attachChild(distance);

    }

    public void objectCollected(){
        objectCounter++;
        updateObjectsText();
    }

    private void updateObjectsText(){
        objects.setText("Objects Collected " + objectCounter + "/" + totalObjects);
    }


    public void updateDistanceText(){
        distance.setText("Distance to lvl end " + player.getWrappedSpatial().getLocalTranslation().distance(levelEnd.getWrappedSpatial().getLocalTranslation()) + "meters.");
    }





}
