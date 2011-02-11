/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mygame.stage.stages;

import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.material.Material;
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

import com.jme3.texture.Texture;
import com.jme3.texture.Texture.WrapMode;
import java.util.ArrayList;
import java.util.List;
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
  
    private TerrainQuad terrain;
    private Material mat_terrain;
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
        loadLevelTerrain();
        env.getRootNode().attachChild(node);

        levelController = new LevelController(env, new SceneObjectImpl(new Geometry()), new SceneObjectImpl(new Geometry()), 10);
        env.getGuiNode().attachChild(levelController);

        initializeCamera();


            /** Add shooting action */
    env.getInputManager().addMapping("shoot", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
    env.getInputManager().addListener(actionListener, "shoot");

    /** Initialize the scene and physics space */
    util = new ObjectUtil(env);
    util.initMaterials();
    util.initWall(node);
    util.initFloor(node);
    env.getPhysicsSpace().setAccuracy(0.005f);
    /** Activate custom shadows */
    node.setShadowMode(ShadowMode.Off);
    bsr = new BasicShadowRenderer(env.getAssetManager(), 256);
    bsr.setDirection(new Vector3f(-1, -1, -1).normalizeLocal());
    env.getViewPort().addProcessor(bsr);

    }

    private void initializeCamera(){
        env.getCamera().setLocation(new Vector3f(0, 6f, 6f));
        env.getCamera().lookAt(Vector3f.ZERO, new Vector3f(0, 1, 0));
        //env.getCamera().setFrustumFar(15);
        
    }

      /**
   * Every time the shoot action is triggered, a new cannon ball is produced.
   * The ball is set up to fly from the camera position in the camera direction.
   */
  private ActionListener actionListener = new ActionListener() {
    public void onAction(String name, boolean keyPressed, float tpf) {
      if (name.equals("shoot") && !keyPressed) {
        util.makeCannonBall(node);
      }
    }
  };

    private void loadLevelTerrain(){
        /** 1. Create terrain material and load four textures into it. */
    mat_terrain = new Material(env.getAssetManager(), "Common/MatDefs/Terrain/Terrain.j3md");

    /** 1.1) Add ALPHA map (for red-blue-green coded splat textures) */
    mat_terrain.setTexture("m_Alpha", env.getAssetManager().loadTexture("Textures/Terrain/splat/alphamap.png"));

    /** 1.2) Add GRASS texture into the red layer (m_Tex1). */
    Texture grass = env.getAssetManager().loadTexture("Textures/Terrain/splat/grass.jpg");
    grass.setWrap(WrapMode.Repeat);
    mat_terrain.setTexture("m_Tex1", grass);
    mat_terrain.setFloat("m_Tex1Scale", 64f);

    /** 1.3) Add DIRT texture into the green layer (m_Tex2) */
    Texture dirt = env.getAssetManager().loadTexture("Textures/Terrain/splat/dirt.jpg");
    dirt.setWrap(WrapMode.Repeat);
    mat_terrain.setTexture("m_Tex2", dirt);
    mat_terrain.setFloat("m_Tex2Scale", 32f);

    /** 1.4) Add ROAD texture into the blue layer (m_Tex3) */
    Texture rock = env.getAssetManager().loadTexture("Textures/Terrain/splat/road.jpg");
    rock.setWrap(WrapMode.Repeat);
    mat_terrain.setTexture("m_Tex3", rock);
    mat_terrain.setFloat("m_Tex3Scale", 128f);

    /** 2. Create the height map */
    AbstractHeightMap heightmap = null;
    /*Texture heightMapImage = env.getAssetManager().loadTexture("Textures/Terrain/splat/mountains512.png");
    heightmap = new ImageBasedHeightMap(
      ImageToAwt.convert(heightMapImage.getImage(), false, true, 0));

     */
     /**Con esto deberia ser random y no con una imagen*/
    try { 
        heightmap = new HillHeightMap(1025, 1000, 50, 100, (byte) 3);

    } catch (Exception ex){ 
        ex.printStackTrace();
    }
    heightmap.load();

    /** 3. We have prepared material and heightmap. Now we create the actual terrain:
     * 3.1) We create a TerrainQuad and name it "my terrain".
     * 3.2) A good value for terrain tiles is 64x64 -- so we supply 64+1=65.
     * 3.3) We prepared a heightmap of size 512x512 -- so we supply 512+1=513.
     * 3.4) As LOD step scale we supply Vector3f(1,1,1).
     * 3.5) At last, we supply the prepared heightmap itself.
     */
    terrain = new TerrainQuad("my terrain", 65, 513, heightmap.getHeightMap());

    /** 4. We give the terrain its material, position & scale it, and attach it. */
    terrain.setMaterial(mat_terrain);
    terrain.setLocalTranslation(0, -1, 0);
    terrain.setLocalScale(2f, 1f, 2f);
    node.attachChild(terrain);
    //env.getPhysicsSpace().add(terrain);


    /** 5. The LOD (level of detail) depends on were the camera is: */
    List<Camera> cameras = new ArrayList<Camera>();
    cameras.add(env.getCamera());
    TerrainLodControl control = new TerrainLodControl(terrain, cameras);
    terrain.addControl(control);

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



}
