/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mygame.stage.gui;

import com.jme3.font.BitmapText;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import mygame.level.SceneLoader;
import mygame.model.Level;
import mygame.stage.GameStageEnvironment;
import mygame.stage.scene.SceneObject;

/**
 *
 * @author Dani
 */
public class LevelController extends Node{

    private SceneObject player, levelEnd;
   // public Timer timer;
    private BitmapText objects, text;
    private int objectCounter = 0 ,totalObjects;
    private GameStageEnvironment env;


    public LevelController(GameStageEnvironment env, int totalObjects){

        this.levelEnd = levelEnd;
        this.player = player;
        this.totalObjects = totalObjects;
        this.env = env;

        objects  = new BitmapText(env.getAssetManager().loadFont("Interface/Fonts/Default.fnt"), false);
        text = new BitmapText(env.getAssetManager().loadFont("Interface/Fonts/Default.fnt"), false);
        updateObjectsText();
        objects.setLocalTranslation( 40, env.getScreenSize().height - 40, 0);
        text.setLocalTranslation( 40, env.getScreenSize().height , 0);
      //  timer = new Timer(env, 200);
        attachChild(objects);
    //    attachChild(timer);
        attachChild(text);
       // initCrossHairs();
    }

    public void objectCollected(){
        objectCounter++;
        updateObjectsText();
    }

    private void updateObjectsText(){
        objects.setText("Objects Collected " + objectCounter + "/" + totalObjects);
    }


    public void updateText(String string){
        text.setText(string);
    }
    
    public void clearText(){
        text.setText("");
    }



 /** A plus sign used as crosshairs to help the player with aiming.*/
  protected void initCrossHairs() {
   BitmapText ch = new BitmapText(env.getGuiFont(), false);
    ch.setSize(env.getGuiFont().getCharSet().getRenderedSize() * 2);
    ch.setText("+"); // crosshairs
    ch.setLocalTranslation( // center
      env.getScreenSize().width / 2 - env.getGuiFont().getCharSet().getRenderedSize() / 3 * 2,
      env.getScreenSize().height / 2 + ch.getLineHeight() / 2, 0);
    attachChild(ch);

  }






}
