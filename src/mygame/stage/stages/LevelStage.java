 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mygame.stage.stages;


import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.util.SkyFactory;

import mygame.level.SceneLoader;
import mygame.media.SoundManager;
import mygame.model.GameInstanceManager;
import mygame.model.PickupGameInstanceManager;
import mygame.stage.GameStage;
import mygame.stage.GameStageEnvironment;
import mygame.stage.gui.LevelController;

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

   private int currentlevel;

  //  private Timer timer;
    private BitmapText sysout;//dubug to player screen
    public LevelController levelController;
    public LevelSelect lselect;
    public LevelStage(GameStageEnvironment env, LevelSelect lselect) {
        super(env, "LevelStage");
        this.env = env;
        levels = new LevelsConfig("levels.json");
        gameInstanceManager = PickupGameInstanceManager.getNewInstance();
        this.lselect = lselect;

        enableHud();



      
    }

    //Call this to load a level before jump to
    public void loadLevel(String filename, int index)
  {
      sceneLoader = new SceneLoader();
      LevelFoundation foundation = new LevelFoundation(filename);
      System.out.println(foundation.scenefile);

      this.level = new Node();
      //timer = new Timer(env, foundation.time);
      currentlevel = index;
      env.getRootNode().attachChild(this.level);
      sceneLoader.init(foundation,this.level, env);
         levelController = new LevelController(env, foundation.spheres.size());
         env.getGuiNode().attachChild(levelController);

  }
    @Override
    public void start() {

        initializeCamera();
        SoundManager.stopMenuBGM();
        SoundManager.playGameBGM();
        createSky();
    }

    private void createSky() {
        int attachChild = level.attachChild(SkyFactory.createSky(env.getAssetManager(), "Textures/Sky/Bright/BrightSky.dds", false));
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
        
        sceneLoader.stop();
        env.getRootNode().detachChild(level);
        env.getGuiNode().detachAllChildren();
	getGameStageEnvironment().getInputManager().setCursorVisible(false);

    }

    @Override
    public void update(float tpf) {
        env.getRootNode().updateLogicalState(tpf);
        env.getRootNode().updateGeometricState();

        levelController.updateText("Health:"+gameInstanceManager.getEnergyLeft()+" Score:"+gameInstanceManager.getScore());
        sceneLoader.update(tpf);

        if (gameInstanceManager.isGameOver()) {
            gameOver();
        }
    }

    public void gameOver() {
        SoundManager.playGameOverSound();
        System.out.println("GAME OVER");
        jumpTo(GameOverStage.class.getName());
    }

    public void victory() {
        SoundManager.playVictorySound();
        System.out.println("Victory");
        lselect.changeLevel(currentlevel);

    }
    private void updateHud(int life, float time, int collected, int all)
    {
        sysout.setText("ohla Danila");
    }
        private void enableHud() {
//For debugging
        env.getGuiNode().detachAllChildren();
        BitmapFont guiFont = env.getGuiFont();
        guiFont = env.getAssetManager().loadFont("Interface/Fonts/Default.fnt");
        sysout = new BitmapText(guiFont, false);
        sysout.setSize(guiFont.getCharSet().getRenderedSize());
        sysout.setLocalTranslation( 40, env.getScreenSize().height - 40, 0);
        env.getGuiNode().attachChild(sysout);
    }

}
