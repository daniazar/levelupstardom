/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mygame.util;

import com.jme3.asset.TextureKey;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.collision.shapes.SphereCollisionShape;
import com.jme3.bullet.nodes.PhysicsNode;
import com.jme3.material.Material;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Sphere;
import mygame.stage.GameStageEnvironment;
import com.jme3.scene.shape.Sphere.TextureMode;
import com.jme3.texture.Texture.WrapMode;
import com.jme3.texture.Texture;
/**
 *
 * @author Dani
 */
public class ObjectUtil {

    private GameStageEnvironment env;
  /** brick dimensions */
  private  float brickLength = 5f;
  private  float brickWidth = 80f;
  private  float brickHeight = 10f;

  private float doorLength = 5f;
  private  float doorWidth = 80f;
  private  float doorHeight = 10f;
  /** geometries and collisions shapes for bricks and cannon balls. */
  private BulletAppState bulletAppState;
  private static  Box  brick;
   private static  Box  door;
  private static  BoxCollisionShape boxCollisionShape;
  private static  BoxCollisionShape doorCollisionShape;
  private static final Sphere cannonball;
  private static final SphereCollisionShape cannonballCollisionShape;

  /** Materials */
  Material wall_mat;
  Material stone_mat;
  Material floor_mat;
  Material door_mat;

   public PhysicsNode doorNode;
    public ObjectUtil(GameStageEnvironment env) {
        this.env = env;
        initMaterials();
    }


  static {
    /** initializing the cannon ball geometry that is reused later */
    cannonball = new Sphere(32, 32, 0.4f, true, false);
    cannonball.setTextureMode(TextureMode.Projected);
    cannonballCollisionShape=new SphereCollisionShape(0.4f);
    /** initializing the brick geometry that is reused later */
    }

/** Initialize the materials used in this scene. */
  public void initMaterials() {
    wall_mat = new Material(env.getAssetManager(), "Common/MatDefs/Misc/SimpleTextured.j3md");
    TextureKey key = new TextureKey("Textures/Terrain/BrickWall/BrickWall.jpg");
    key.setGenerateMips(true);
    Texture tex = env.getAssetManager().loadTexture(key);
    wall_mat.setTexture("m_ColorMap", tex);

    stone_mat = new Material(env.getAssetManager(), "Common/MatDefs/Misc/SimpleTextured.j3md");
    TextureKey key2 = new TextureKey("Textures/Terrain/Rock/Rock.PNG");
    key2.setGenerateMips(true);
    Texture tex2 = env.getAssetManager().loadTexture(key2);
    stone_mat.setTexture("m_ColorMap", tex2);

    floor_mat = new Material(env.getAssetManager(), "Common/MatDefs/Misc/SimpleTextured.j3md");
    TextureKey key3 = new TextureKey("Textures/Terrain/Pond/Pond.png");
    key3.setGenerateMips(true);
    Texture tex3 = env.getAssetManager().loadTexture(key3);
    tex3.setWrap(WrapMode.Repeat);
    floor_mat.setTexture("m_ColorMap", tex3);

        door_mat = new Material( env.getAssetManager(), "Common/MatDefs/Misc/SimpleTextured.j3md");
       TextureKey key4 = new TextureKey("Textures/door.jpg");
       key4.setGenerateMips(true);
       Texture tex4 = env.getAssetManager().loadTexture(key4);
       tex4.setWrap(WrapMode.BorderClamp);

       door_mat.setTexture("m_ColorMap", tex4);
//
  }


      /** This method creates one individual physical brick. */
  public void makeBrick(Vector3f ori, Node rootNode) {
    /** create a new brick */
    Geometry box_geo = new Geometry("brick", brick);
    box_geo.setMaterial(wall_mat);
    PhysicsNode brickNode = new PhysicsNode(
     box_geo,      // geometry
     boxCollisionShape, // collision shape
     1.5f);       // mass
    /** position the brick and activate shadows */
    brickNode.setLocalTranslation(ori);
    brickNode.setShadowMode(ShadowMode.CastAndReceive);
    rootNode.attachChild(brickNode);
    env.getPhysicsSpace().add(brickNode);
  }

  public void makeDoor(Vector3f ori, Node rootNode)
    {
          Geometry box_geo = new Geometry("door", door);
    box_geo.setMaterial(door_mat);
    doorNode = new PhysicsNode(
     box_geo,      // geometry
     doorCollisionShape, // collision shape
     1.5f);       // mass
    /** position the brick and activate shadows */
    doorNode.setLocalTranslation(ori);
    doorNode.setShadowMode(ShadowMode.CastAndReceive);
    rootNode.attachChild(doorNode);
    env.getPhysicsSpace().add(doorNode);
  }


  /** This method creates one individual physical cannon ball.
   * By defaul, the ball is accelerated and flies
   * from the camera position in the camera direction.*/
   public void makeCannonBall(Node rootNode) {
    /** create a new cannon ball. */
    Geometry ball_geo = new Geometry("cannon ball", cannonball);
    ball_geo.setMaterial(stone_mat);
    PhysicsNode cannonballNode = new PhysicsNode(
       ball_geo,         // geometry
       cannonballCollisionShape, // collision shape
       1.0f);          // mass
    /** position the cannon ball and activate shadows */
    cannonballNode.setLocalTranslation(env.getCamera().getLocation());
    cannonballNode.setShadowMode(ShadowMode.CastAndReceive);
    /** Attach the cannon call to the scene and accelerate it. */
    rootNode.attachChild(cannonballNode);
    env.getPhysicsSpace().add(cannonballNode);
    cannonballNode.setLinearVelocity(env.getCamera().getDirection().mult(25));
  }



     /** Make a solid floor and add it to the scene. */
  public void initFloor(Node rootNode) {
    Box floorBox = new Box(Vector3f.ZERO, 10f, 0.1f, 5f);
    floorBox.scaleTextureCoordinates(new Vector2f(3, 6));
    Geometry floor = new Geometry("floor", floorBox);
    floor.setMaterial(floor_mat);
    floor.setShadowMode(ShadowMode.Receive);
    PhysicsNode floorNode = new PhysicsNode(
     floor,
     new BoxCollisionShape(new Vector3f(10f, 0.1f, 5f)),
     0);
    floorNode.setLocalTranslation(0, -0.1f, 0);
    rootNode.attachChild(floorNode);
    env.getPhysicsSpace().add(floorNode);
  }

  /** A loop that builds a wall out of individual bricks. */
  public void initWall(Node rootNode, Vector3f dim) {
        brickLength = dim.x;
        brickWidth = dim.z;
        brickHeight = dim.y;
        brick = new Box(Vector3f.ZERO, brickLength, brickHeight, brickWidth);
    brick.scaleTextureCoordinates(new Vector2f(1f, .5f));
    boxCollisionShape = new BoxCollisionShape(new Vector3f(brickLength, brickHeight, brickWidth));
    float startpt = brickLength / 4;
    float height = 0;
    for (int j = 0; j < 5; j++) {
      for (int i = 0; i < 4; i++) {
        Vector3f vt =
         new Vector3f(i * brickLength * 2 + startpt, brickHeight + height, 0);
        makeBrick(vt, rootNode);
      }
      startpt = -startpt;
      height += 2 * brickHeight;
    }
  }

  public void initDoor(Node rootNode, Vector3f dim)
    {
              doorLength = dim.x;
        doorWidth = dim.z;
        doorHeight = dim.y;
             door = new Box(Vector3f.ZERO, dim.x   , dim.y, dim.z);
    door.scaleTextureCoordinates(new Vector2f(1f, .5f));
    doorCollisionShape = new BoxCollisionShape(new Vector3f(dim.x, dim.y, dim.z));

       Vector3f vt =  new Vector3f( doorLength, doorHeight, 0);
        makeDoor(vt, rootNode);
    }
}
