 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mygame.stage.stages;


import com.jme3.scene.Node;
import com.jme3.util.SkyFactory;
import mygame.level.SceneLoader;
import mygame.media.SoundManager;
import mygame.stage.GameStage;
import mygame.stage.GameStageEnvironment;

/**
 *
 * @author matiaspan
 */
public class LevelStage extends GameStage {

    private GameStageEnvironment env;
    private Node level;
    private SceneLoader sceneLoader;
    private LevelsConfig levels;

    public LevelStage(GameStageEnvironment env) {
        super(env, "LevelStage");
        this.env = env;
        levels = new LevelsConfig("levels.json");
    }

    //Call this to load a level before jump to
    public void loadLevel(String filename)
  {
      sceneLoader = new SceneLoader();
      LevelFoundation foundation = new LevelFoundation(filename);
      System.out.println(foundation.scenefile);

      this.level = new Node();
      
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



}
