/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mygame.stage.stages;


import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import mygame.level.SceneLoader;
import mygame.media.SoundManager;
import mygame.model.Level;
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


    public LevelStage(GameStageEnvironment env) {
        super(env, "LevelStage");
        this.env = env;
    }

    //Call this to load a level before jump to
    public void loadLevel(Level level)
  {
      sceneLoader = new SceneLoader();
      String fname = level.getFilename();
      //Retrieve the .obj filename from level filename
      String objFile = "/Scenes/level1/level1.j3o";
      this.level = new Node();
      
      env.getRootNode().attachChild(this.level);
      sceneLoader.init(objFile,this.level, env);
 
  }
    @Override
    public void start() {
        initializeCamera();
                SoundManager.stopMenuBGM();
        SoundManager.playGameBGM();
    }


    private void initializeCamera(){
        env.getCamera().setLocation(sceneLoader.getSpawnPoint());
        env.getCamera().lookAt(sceneLoader.getGoalPos(), new Vector3f(0, 1, 0));
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
