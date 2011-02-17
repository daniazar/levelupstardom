 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mygame.stage.stages;


import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.scene.Node;
import com.jme3.util.SkyFactory;

import mygame.level.SceneLoader;
import mygame.media.SoundManager;
import mygame.model.GameInstanceManager;
import mygame.model.PickupGameInstanceManager;
import mygame.stage.GameStage;
import mygame.stage.GameStageEnvironment;
import mygame.stage.gui.Timer;

/**
 *
 * @author matiaspan
 */
public class LevelStage extends GameStage {

    private GameStageEnvironment env;
    private Node level;
    private SceneLoader sceneLoader;
    private LevelsConfig levels;
    private GameInstanceManager gameInstanceManager;
  //  private Timer timer;
    private BitmapText sysout;//dubug to player screen

    public LevelStage(GameStageEnvironment env) {
        super(env, "LevelStage");
        this.env = env;
        levels = new LevelsConfig("levels.json");
        gameInstanceManager = new PickupGameInstanceManager();

    }

    //Call this to load a level before jump to
    public void loadLevel(String filename)
  {
      sceneLoader = new SceneLoader();
      LevelFoundation foundation = new LevelFoundation(filename);
      System.out.println(foundation.scenefile);

      this.level = new Node();
      //timer = new Timer(env, foundation.time);

      env.getRootNode().attachChild(this.level);
      sceneLoader.init(foundation,this.level, env);
 
  }
    @Override
    public void start() {
        initializeCamera();
                SoundManager.stopMenuBGM();
        SoundManager.playGameBGM();
        createSky();
    }

    private void createSky() {
        env.getRootNode().attachChild(SkyFactory.createSky(env.getAssetManager(), "Textures/Sky/Bright/BrightSky.dds", false));
    }

    private void initializeCamera(){
        //env.getCamera().setLocation(sceneLoader.getSpawnPoint());
       // env.getCamera().lookAt(sceneLoader.getGoalPos(), new Vector3f(0, 1, 0));
        //env.getCamera().setFrustumFar(15);

    }

    @Override
    public void pause() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void stop() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void update(float tpf) {
        env.getRootNode().updateLogicalState(tpf);
        env.getRootNode().updateGeometricState();


        sceneLoader.update(tpf);
    }


    private void updateHud(int life, float time, int collected, int all)
    {
        
    }
        private void enableHud() {
//For debugging
        env.getGuiNode().detachAllChildren();
        BitmapFont guiFont = env.getGuiFont();
        guiFont = env.getAssetManager().loadFont("Interface/Fonts/Default.fnt");
        sysout = new BitmapText(guiFont, false);
        sysout.setSize(guiFont.getCharSet().getRenderedSize());
        sysout.setLocalTranslation(100, 0, 0);
        env.getGuiNode().attachChild(sysout);
    }

}
