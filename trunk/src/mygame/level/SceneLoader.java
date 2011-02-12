/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mygame.level;


import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.math.Vector3f;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import mygame.stage.GameStageEnvironment;

/**
 *
 * @author dgrandes
 */
public class SceneLoader {

    private Node scene, floor, goal, levelNode;
    private Spatial spawn;
    private GameStageEnvironment env;
    public void init(String levelObj, Node level, GameStageEnvironment env)
    {
         levelNode = level;
         reorient();
        scene = (Node)env.getAssetManager().loadModel(levelObj);
        spawn = scene.getChild("Spawn");
        floor = (Node)scene.getChild("level");
                Material mat_default = new Material(
            env.getAssetManager(), "Common/MatDefs/Misc/ShowNormals.j3md");
        //floor.setMaterial(mat_default);
        goal = (Node)scene.getChild("Goal");
        level.attachChild(floor);
        level.attachChild(goal);
        this.env = env;
        Vector3f spawnVector = spawn.getLocalTranslation();
        System.out.println(spawnVector);
       
        DirectionalLight sun=new DirectionalLight();
        sun.setDirection(new Vector3f(-0.1f,-0.7f,-1.0f));
        env.getRootNode().addLight(sun);
    }

    public Vector3f getSpawnPoint()
    {
        return spawn.getLocalTranslation();
    }

    public Vector3f getGoalPos()
    {
        return goal.getLocalTranslation();
    }

    public GameStageEnvironment getGameStageEnv()
    {
        return env;
    }


    public Node getLevel()
    {
        return levelNode;

    }
    private void reorient() {

    levelNode.rotate((float) Math.toRadians(90.0f), (float) Math.toRadians(
            90.0f),(float) Math.toRadians(0.0f) );
    //levelNode.setLocalTranslation(new Vector3f(0.0f, -2.5f, 0.0f));

}

}
