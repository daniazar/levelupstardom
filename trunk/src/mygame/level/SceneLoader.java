/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.level;

import com.jme3.asset.plugins.HttpZipLocator;
import com.jme3.asset.plugins.ZipLocator;
import com.jme3.bounding.BoundingVolume;
import com.jme3.bullet.collision.shapes.CompoundCollisionShape;
import com.jme3.bullet.collision.shapes.SphereCollisionShape;
import com.jme3.bullet.nodes.PhysicsNode;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.collision.CollisionResults;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.math.Vector3f;
import com.jme3.light.DirectionalLight;
import com.jme3.light.PointLight;
import com.jme3.material.Material;
import com.jme3.material.MaterialList;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Transform;
import com.jme3.scene.Geometry;
import com.jme3.scene.plugins.ogre.OgreMeshKey;
import java.util.ArrayList;
import mygame.PlayerController;
import mygame.media.SoundManager;
import mygame.model.BrickWall;
import mygame.model.GameInstanceManager;
import mygame.model.PickupGameInstanceManager;
import mygame.stage.GameStageEnvironment;
import mygame.model.Hazard;
import mygame.model.Interruptor;
import mygame.stage.stages.LevelFoundation;
import mygame.stage.stages.LevelStage;
import mygame.stage.stages.PickableSpheres;
import mygame.util.ObjectUtil;

/**
 *
 * @author dgrandes
 */
public class SceneLoader {

    public Node scene, floor, goal, levelNode;
    public PhysicsNode levelPhyNode;
    public Spatial spawn;
    public GameStageEnvironment env;
    public PlayerController player;
    public LevelFoundation foundation;
    private ArrayList<Geometry> pickups;

    public void init(LevelFoundation foundation, Node level, GameStageEnvironment env) {
        this.env = env;
        this.foundation = foundation;

        levelNode = level;
        env.getRootNode().attachChild(levelNode);
        if (foundation.scenetype.equals("zip")) {
            loadZipScene();
        } else {
            loadObjScene();
        }

        addPickupObjects();
        addHazardAreas();
        addInterruptors();
        addGoal();
        addBricks();
        //   spawn.lookAt(goal.getWorldTranslation(), Vector3f.UNIT_X);
        //     env.getCamera().setFrame(spawn.getWorldTranslation(), spawn.getWorldRotation());

        //    reorient();
        //Physics

        //env.getPhysicsSpace().setGravity(new Vector3f(0, 0, 1));

        //      levelNode.attachDebugShape(env.getAssetManager());
//



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

        physicsSphere.setLocalTranslation(new Vector3f(60, -14, 60));
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
////        levelNode.attachChild(physicsSphere2);
////        env.getPhysicsSpace().add(physicsSphere2);
//

        env.getFlyCamera().setMoveSpeed(50);



        env.getCamera().setFrustumFar(2000);

        DirectionalLight dl = new DirectionalLight();
        dl.setColor(ColorRGBA.White.clone().multLocal(2));
        dl.setDirection(new Vector3f(-1, -1, -1).normalize());
        levelNode.addLight(dl);

        for (Vector3f plPosition : foundation.pointLightPositions) {
            PointLight pl = new PointLight();
            pl.setColor(ColorRGBA.randomColor().clone().multLocal(2));
            pl.setPosition(plPosition);
            pl.setRadius(40);
            levelNode.addLight(pl);
        }
    }

    private void loadZipScene() {

        if ("quake3level.zip".equals(foundation.scenefile)) {
            env.getAssetManager().registerLocator("quake3level.zip", ZipLocator.class.getName());

            // create the geometry and attach it
            MaterialList matList = (MaterialList) env.getAssetManager().loadAsset("Scene.material");
            OgreMeshKey key = new OgreMeshKey("main.meshxml", matList);
            levelNode = (Node) env.getAssetManager().loadAsset(key);
            levelNode.setLocalScale(0.1f);

            CompoundCollisionShape levelShape = CollisionShapeFactory.createMeshCompoundShape((Node) levelNode);

            levelPhyNode = new PhysicsNode((Spatial) levelNode, levelShape, 0);
            env.getRootNode().attachChild(levelPhyNode);


            env.getPhysicsSpace().add(levelPhyNode);
        } else if ("town.zip".equals(foundation.scenefile)) {
            //  env.getAssetManager().registerLocator("http://jmonkeyengine.googlecode.com/files/town.zip", HttpZipLocator.class.getName());

            env.getAssetManager().registerLocator("town.zip", ZipLocator.class.getName());

            Spatial sceneModel;
      
//
//            sceneModel = env.getAssetManager().loadModel("town/main.scene");
//            sceneModel.setLocalScale(2f);

                        // create the geometry and attach it
            MaterialList matList = (MaterialList) env.getAssetManager().loadAsset("town/main.material");
            OgreMeshKey key = new OgreMeshKey("town/level.meshxml", matList);
            levelNode = (Node) env.getAssetManager().loadAsset(key);
            levelNode.setLocalScale(2f);

            CompoundCollisionShape levelShape = CollisionShapeFactory.createMeshCompoundShape((Node) levelNode);

            levelPhyNode = new PhysicsNode((Spatial) levelNode, levelShape, 0);
      //      levelPhyNode.attachDebugShape(env.getAssetManager());
            env.getRootNode().attachChild(levelPhyNode);


            env.getPhysicsSpace().add(levelPhyNode);

            BrickWall b = new BrickWall(new Vector3f(94, 1, -18), new Vector3f(5, 2, 1));
            levelPhyNode.attachChild(b.node);
            b.init(env);



        }
    }

    private void loadObjScene() {
        scene = (Node) env.getAssetManager().loadModel(foundation.scenefile);
        spawn = scene.getChild("Spawn");
        floor = (Node) scene.getChild("level");
        Material mat_default = new Material(
                env.getAssetManager(), "Common/MatDefs/Misc/ShowNormals.j3md");
        floor.setMaterial(mat_default);
        goal = (Node) scene.getChild("Goal");
        CompoundCollisionShape levelShape = CollisionShapeFactory.createMeshCompoundShape((Node) scene);

        levelPhyNode = new PhysicsNode(scene, levelShape, 0);
        env.getRootNode().attachChild(levelPhyNode);


        env.getPhysicsSpace().add(levelPhyNode);

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

    private void loadPlayer() {
        player = new PlayerController(this);
        player.init();
    }

    public void update(float tpf) {
        player.update(tpf);
        updatePickUpCollisions(tpf);
        updateHazardCollisions(tpf);
        updateGoalCollisions(tpf);
        updateInterruptorCollisions(tpf);
    }

    public void stop() {
        player.stop();
        env.getRootNode().detachChild(levelPhyNode);
        env.getRootNode().detachChild(levelNode);
    }

    private void addPickupObjects() {
        for (PickableSpheres sphere : foundation.spheres.values()) {
            sphere.init(env);
            levelPhyNode.attachChild(sphere.geom);
        }

    }

    private void addHazardAreas() {
        for (Hazard hazard : foundation.hazards.values()) {
            hazard.init(env);
            levelPhyNode.attachChild(hazard.geom);
        }
    }

    private void addInterruptors() {
        for (Interruptor interruptor : foundation.interruptors.values()) {
            interruptor.init(env);
            interruptor.obstacle.init(env);

            levelPhyNode.attachChild(interruptor.geom);

        }
    }


    private void addBricks()
    {
        for(BrickWall brickWall : foundation.brickWalls.values())
        {
            env.getRootNode().attachChild(brickWall.node);
            brickWall.init(env);

        }
    }

    private void addGoal()
    {

        foundation.goal.init(env);
        levelPhyNode.attachChild(foundation.goal.geom);
    }

    private void updatePickUpCollisions(float tpf) {
        env.getRootNode().updateGeometricState();
        CollisionResults results;

        for (PickableSpheres sphere : foundation.spheres.values()) {
            if (sphere.isActivated()) {
                continue;
            }

            results = new CollisionResults();
            BoundingVolume bv = sphere.geom.getWorldBound();
            player.player.collideWith(bv, results);

            if (results.size() > 0) {
                sphere.activate();
                ((LevelStage) env.getLevelStage()).levelController.objectCollected();
                SoundManager.playObjectPickUpSound();
                PickupGameInstanceManager.getInstance().addPoints(100);
                System.out.println(PickupGameInstanceManager.getInstance().getScore() + " Score");
            }

        }

    }

    private void updateHazardCollisions(float tpf) {
        env.getRootNode().updateGeometricState();
        CollisionResults results;

        for (Hazard hazard : foundation.hazards.values()) {

            results = new CollisionResults();
            BoundingVolume bv = hazard.geom.getWorldBound();
            player.player.collideWith(bv, results);

            if (results.size() > 0) {
                SoundManager.playDamageSound();
                PickupGameInstanceManager.getInstance().damageCharacter(tpf * hazard.dot);
                System.out.println(PickupGameInstanceManager.getInstance().getEnergyLeft() + " HP");
            }
        }
    }

    private void updateGoalCollisions(float tpf) {
        CollisionResults results = new CollisionResults();
        BoundingVolume bv = foundation.goal.geom.getWorldBound();
        player.player.collideWith(bv, results);

        if (results.size() > 0) {
            ((LevelStage) env.getLevelStage()).victory();
        }
    }

    private void updateInterruptorCollisions(float tpf) {
        env.getRootNode().updateGeometricState();
        CollisionResults results;

        for (Interruptor interruptor : foundation.interruptors.values()) {

            results = new CollisionResults();
            BoundingVolume bv = interruptor.geom.getWorldBound();
            player.player.collideWith(bv, results);

            if (results.size() > 0) {


                interruptor.activate();
            }
        }
    }
}
