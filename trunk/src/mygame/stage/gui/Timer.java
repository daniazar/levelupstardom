/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mygame.stage.gui;

import com.jme3.font.BitmapText;
import java.util.TimerTask;
import mygame.stage.GameStageEnvironment;

/**
 *
 * @author Dani
 */
public class Timer extends BitmapText{


 
    Float time;
    java.util.Timer timer = new java.util.Timer (  ) ;;
    LevelController level;
    public Timer(GameStageEnvironment env,  float seconds) {
        super( env.getAssetManager().loadFont("Interface/Fonts/Default.fnt"), false);
        time = seconds;

    timer.scheduleAtFixedRate(new  ToDoTask() , 0,100 ) ;
    setLocalTranslation( 40, env.getScreenSize().height - 20, 0);
    this.level = level;
    }

    public void pause(){
        timer.cancel();
    }

    public void unpause(){
        timer.scheduleAtFixedRate(new  ToDoTask() , 0,100 );
    
    }
    
    public float getTime(){
        return time;
    }


 private class  ToDoTask extends TimerTask  {

    public void run (  )   {
      //System.out.println ( "OK, It's time to do something!" + time ) ;
      if(time == 0.0f)
      {
          //Aca termina el nivel.

      }
      time-= 0.1f;

      setText("Time left: " + time.toString().substring(0, 5));

  }

 }


}
