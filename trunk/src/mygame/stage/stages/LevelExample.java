/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mygame.stage.stages;

import com.jme3.bounding.BoundingBox;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.shadow.BasicShadowRenderer;
import com.jme3.terrain.geomipmap.TerrainLodControl;
import com.jme3.terrain.geomipmap.TerrainQuad;
import com.jme3.terrain.heightmap.AbstractHeightMap;
import com.jme3.terrain.heightmap.HillHeightMap;
import com.jme3.terrain.jbullet.TerrainPhysicsShapeFactory;

import com.jme3.texture.Texture;
import com.jme3.texture.Texture.WrapMode;
import com.jme3.util.SkyFactory;
import java.util.ArrayList;
import java.util.List;
import mygame.ChaseCam;
import mygame.Player;
import mygame.stage.GameStage;
import mygame.stage.GameStageEnvironment;
import mygame.stage.gui.LevelController;
import mygame.stage.scene.SceneObjectImpl;
import mygame.util.ObjectUtil;

/**
 *
 * @author Dani
 */
public class LevelExample extends GameStage {

    /** Activate custom rendering of shadows */
    BasicShadowRenderer bsr;

    Material matRock;
    Material matWire;
    ChaseCam camera;
    Player player;

        //terrain
    Node terrainPhysicsNode;
    private TerrainQuad terrain;
    private GameStageEnvironment env;
    private Node node;
    private LevelController levelController;
    private static String levelName = "LevelExample";
    private ObjectUtil util;
    public LevelExample(GameStageEnvironment env) {
        super(env, levelName);
        this.env = env;
    }





    @Override
    public void start() {

        node = new Node(levelName);
        env.getFlyCamera().setEnabled(true);
        //env.getFlyCamera().setMoveSpeed(50);
        env.getRootNode().attachChild(node);

        createTerrain();
        createLight();
        createSky();

        levelController = new LevelController(env, new SceneObjectImpl(new Geometry()), new SceneObjectImpl(new Geometry()), 10);
        env.getGuiNode().attachChild(levelController);

        initializeCamera();


      
        /** Initialize the scene and physics space */
        util = new ObjectUtil(env);
        util.initMaterials();
         util.initWall(node);
        util.initFloor(node);
        env.getPhysicsSpace().setAccuracy(0.005f);

        player = new Player(env, node, util);
        /** Activate custom shadows */

        node.setShadowMode(ShadowMode.Off);
        bsr = new BasicShadowRenderer(env.getAssetManager(), 256);
        bsr.setDirection(new Vector3f(-1, -1, -1).normalizeLocal());
        env.getViewPort().addProcessor(bsr);


        camera = new ChaseCam(env, player);
    }

    private void initializeCamera(){
        env.getCamera().setLocation(new Vector3f(0, 6f, 6f));
        env.getCamera().lookAt(Vector3f.ZERO, new Vector3f(0, 1, 0));
        //env.getCamera().setFrustumFar(15);
        
    }


    private void createLight() {
        Vector3f direction = new Vector3f(-0.1f, -0.7f, -1).normalizeLocal();
        DirectionalLight dl = new DirectionalLight();
        dl.setDirection(direction);
        dl.setColor(new ColorRGBA(1f, 1f, 1f, 1.0f));
        node.addLight(dl);
    }

    private void createSky() {
        node.attachChild(SkyFactory.createSky(env.getAssetManager(), "Textures/Sky/Bright/BrightSky.dds", false));
    }


    private void createTerrain() {
        matRock = new Material(env.getAssetManager(), "Common/MatDefs/Terrain/Terrain.j3md");
        matRock.setTexture("m_Alpha", env.getAssetManager().loadTexture("Textures/Terrain/splat/alphamap.png"));
        Texture heightMapImage = env.getAssetManager().loadTexture("Textures/Terrain/splat/mountains512.png");
        Texture grass = env.getAssetManager().loadTexture("Textures/Terrain/splat/grass.jpg");
        grass.setWrap(WrapMode.Repeat);
        matRock.setTexture("m_Tex1", grass);
        matRock.setFloat("m_Tex1Scale", 64f);
        Texture dirt = env.getAssetManager().loadTexture("Textures/Terrain/splat/dirt.jpg");
        dirt.setWrap(WrapMode.Repeat);
        matRock.setTexture("m_Tex2", dirt);
        matRock.setFloat("m_Tex2Scale", 32f);
        Texture rock = env.getAssetManager().loadTexture("Textures/Terrain/splat/road.jpg");
        rock.setWrap(WrapMode.Repeat);
        matRock.setTexture("m_Tex3", rock);
        matRock.setFloat("m_Tex3Scale", 128f);
        matWire = new Material(env.getAssetManager(), "Common/MatDefs/Misc/WireColor.j3md");
        matWire.setColor("m_Color", ColorRGBA.Green);

        AbstractHeightMap heightmap = null;
        /*try {
            heightmap = new ImageBasedHeightMap(ImageToAwt.convert(heightMapImage.getImage(), false, true, 0), 0.25f);
            heightmap.load();

        } catch (Exception e) {
            e.printStackTrace();
        }*/


        try {
            heightmap = new HillHeightMap(1025, 1000, 50, 100, (byte) 3);
            heightmap.load();
        } catch (Exception ex){
            ex.printStackTrace();
        }


        terrain = new TerrainQuad("terrain", 65, 513, heightmap.getHeightMap());
        List<Camera> cameras = new ArrayList<Camera>();
        cameras.add(env.getCamera());
        TerrainLodControl control = new TerrainLodControl(terrain, cameras);
        terrain.addControl(control);
        terrain.setMaterial(matRock);
        terrain.setModelBound(new BoundingBox());
        terrain.updateModelBound();
        terrain.setLocalScale(new Vector3f(2, 2, 2));
        terrain.setLocalTranslation(0, -10, 0);

        TerrainPhysicsShapeFactory factory = new TerrainPhysicsShapeFactory();
        terrainPhysicsNode = factory.createPhysicsMesh(terrain);
        terrainPhysicsNode.attachChild(terrain);
        node.attachChild(terrainPhysicsNode);
        env.getPhysicsSpace().add(terrainPhysicsNode);
    }
    
    @Override
    public void pause() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void stop() {
        env.getRootNode().detachChild(node);
        env.getGuiNode().detachChild(levelController);

    }

    @Override
    public void update(float tpf) {
        //aca hago el codigo de update.
        node.updateLogicalState(tpf);
        node.updateGeometricState();
        Vector3f camDir = env.getCamera().getDirection().clone().multLocal(0.2f);
        Vector3f camLeft = env.getCamera().getLeft().clone().multLocal(0.2f);
        camDir.y = 0;
        camLeft.y = 0;
        player.walkDirection.set(0, 0, 0);
        player.modelDirection.set(0, 0, 2);
        if (player.left) {
            player.walkDirection.addLocal(camLeft);
        }
        if (player.right) {
            player.walkDirection.addLocal(camLeft.negate());
        }
        if (player.up) {
            player.walkDirection.addLocal(camDir);
        }
        if (player.down) {
            player.walkDirection.addLocal(camDir.negate());
        }
        if (!player.character.onGround()) {
            player.airTime = player.airTime + tpf;
        } else {
            player.airTime = 0;
        }
        if (player.walkDirection.length() == 0) {
            if (!"stand".equals(player.animationChannel.getAnimationName())) {
                player.animationChannel.setAnim("stand", 1f);
            }
        } else {
            player.modelRotation.lookAt(player.walkDirection, Vector3f.UNIT_Y);
            if (player.airTime > .3f) {
                if (!"stand".equals(player.animationChannel.getAnimationName())) {
                    player.animationChannel.setAnim("stand");
                }
            } else if (!"Walk".equals(player.animationChannel.getAnimationName())) {
                player.animationChannel.setAnim("Walk", 0.7f);
            }
        }
        player.model.setLocalRotation(player.modelRotation);
        player.modelRotation.multLocal(player.modelDirection);
        player.modelRight.set(player.modelDirection);
        player.ROTATE_LEFT.multLocal(player.modelRight);
        player.character.setWalkDirection(player.walkDirection);
    }




}
