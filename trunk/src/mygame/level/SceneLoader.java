/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.level;

import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.dynamics.RigidBodyConstructionInfo;
import com.jme3.bounding.BoundingBox;
import com.jme3.bounding.BoundingVolume;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.collision.shapes.CompoundCollisionShape;
import com.jme3.bullet.collision.shapes.MeshCollisionShape;
import com.jme3.bullet.collision.shapes.SphereCollisionShape;
import com.jme3.bullet.nodes.PhysicsNode;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.math.Vector3f;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Transform;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import mygame.PlayerController;
import mygame.stage.GameStageEnvironment;

/**
 *
 * @author dgrandes
 */
public class SceneLoader {

    private Node scene, floor, goal, levelNode;
    private Spatial spawn;
    private GameStageEnvironment env;
    private PlayerController player;
    

    public void init(String levelObj, Node level, GameStageEnvironment env) {
        this.env = env;

        levelNode = level;

        scene = (Node) env.getAssetManager().loadModel(levelObj);
  
        spawn = scene.getChild("Spawn");
//        floor = (Node) scene.getChild("level");
//        Material mat_default = new Material(
//                env.getAssetManager(), "Common/MatDefs/Misc/ShowNormals.j3md");
        //floor.setMaterial(mat_default);
        goal = (Node) scene.getChild("Goal");
        spawn.lookAt(goal.getWorldTranslation(), Vector3f.UNIT_X);
        env.getCamera().setFrame(spawn.getWorldTranslation(), spawn.getWorldRotation());

        reorient();
        //Physics

        //env.getPhysicsSpace().setGravity(new Vector3f(0, 0, 1));
        CompoundCollisionShape levelShape = CollisionShapeFactory.createMeshCompoundShape((Node) scene);

        PhysicsNode levelNode = new PhysicsNode(scene, levelShape, 0);
        levelNode.attachDebugShape(env.getAssetManager());
//
        level.attachChild(levelNode);


        env.getPhysicsSpace().add(levelNode);



        //Player
        loadPlayer();
//
//
//        PhysicsNode physicsnode = new PhysicsNode(shape, 0);
//        physicsnode.setLocalTranslation(scene.getLocalTranslation());
//        physicsnode.setCollisionGroup(physicsnode.COLLISION_GROUP_02);
//        env.getPhysicsSpace().add(physicsnode);
//
//        Material mat = new Material(env.getAssetManager(), "Common/MatDefs/Misc/WireColor.j3md");
//        mat.setColor("m_Color", ColorRGBA.Red);
//        Material mat2 = new Material(env.getAssetManager(), "Common/MatDefs/Misc/WireColor.j3md");
//        mat2.setColor("m_Color", ColorRGBA.Magenta);
//
        // Add a physics sphere to the world
        PhysicsNode physicsSphere = new PhysicsNode(new SphereCollisionShape(1), 1);

        physicsSphere.setLocalTranslation(new Vector3f(0, 0, -10));
        physicsSphere.attachDebugShape(env.getAssetManager());
        physicsSphere.addCollideWithGroup(PhysicsNode.COLLISION_GROUP_02);
        level.attachChild(physicsSphere);
        env.getPhysicsSpace().add(physicsSphere);
//
//        // Add a physics sphere to the world using the collision shape from sphere one
////        PhysicsNode physicsSphere2=new PhysicsNode(physicsSphere.getCollisionShape(),1);
////        physicsSphere2.setLocalTranslation(Vector3f.ZERO);
////        physicsSphere2.attachDebugShape(mat2);
////        physicsSphere2.addCollideWithGroup(PhysicsNode.COLLISION_GROUP_02);
////        env.getRootNode().attachChild(physicsSphere2);
////        env.getPhysicsSpace().add(physicsSphere2);
//

        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(new Vector3f(-0.1f, -0.7f, -1.0f));
        env.getRootNode().addLight(sun);
    }

    public Vector3f getSpawnPoint() {
        return spawn.getLocalTranslation();
    }

    public Vector3f getGoalPos() {
        return goal.getLocalTranslation();
    }

    public GameStageEnvironment getGameStageEnv() {
        return env;
    }

    public Node getLevel() {
        return levelNode;

    }

    private void loadPlayer()
    {
        player = new PlayerController(this);
        player.init();
    }

    private void reorient() {

        levelNode.rotate((float) Math.toRadians(90.0f), (float) Math.toRadians(
                0.0f), (float) Math.toRadians(0.0f));
        //levelNode.setLocalTranslation(new Vector3f(0.0f, -2.5f, 0.0f));

    }

    private void setCollisionDetection() {
        CollisionShape shape = CollisionShapeFactory.createMeshShape(scene);
        //landscape = new RigidBodyControl(shape, 0);
        //  scene.addControl(landscape);
        //  env.getPhysicsSpace().add(landscape);

    }
}
