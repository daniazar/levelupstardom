/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mygame.level;

import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import mygame.PlayerController;

/**
 *
 * @author dgrandes
 */
public class ModelLoader {

    private SceneLoader scLoader;
    private PlayerController player;
    public void init(SceneLoader sceneLoader)
    {
        scLoader = sceneLoader;
        Box box = new Box(new Vector3f(0.0f, 0.0f, 0.0f), 0.5f, 0.5f, 0.5f);
        Geometry aBox = new Geometry("aBox", box);
                  Material mat_default = new Material(
            sceneLoader.getGameStageEnv().getAssetManager()
                          , "Common/MatDefs/Misc/ShowNormals.j3md");
                  aBox.setMaterial(mat_default);
                  aBox.setLocalTranslation(sceneLoader.getSpawnPoint());
                  aBox.lookAt(sceneLoader.getGoalPos(), Vector3f.UNIT_Y);
                  sceneLoader.getLevel().attachChild(aBox);
           loadPlayer();
    }

    private void loadPlayer()
    {
        player = new PlayerController(scLoader);
        player.init();
        
    }

}

