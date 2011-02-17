/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.model;


import com.bulletphysics.collision.shapes.CollisionShape;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.collision.shapes.CompoundCollisionShape;
import com.jme3.bullet.collision.shapes.MeshCollisionShape;
import com.jme3.bullet.nodes.PhysicsNode;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Transform;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import mygame.stage.GameStageEnvironment;
import mygame.util.ObjectUtil;

/**
 *
 * @author dgrandes
 */
public class Obstacle {

    public Vector3f pos;
    public Vector3f extent;
    public Geometry geom;
    public Node root;
       public PhysicsNode physicsNode;
       public GameStageEnvironment env;
    public Obstacle() {
    }

    public Obstacle(Vector3f pos, Vector3f extent) {
        this.pos = pos;
        this.extent = extent;
    }

    public void init(GameStageEnvironment env) {

//         this.env = env;
      root = new Node();
//            root.setLocalTranslation(pos);
//                 Mesh mesh = new Box(Vector3f.ZERO, extent);
//        geom = new Geometry("Box", mesh);
//                AssetManager assman = env.getAssetManager();
//        Material mat = new Material( assman, "Common/MatDefs/Misc/SimpleTextured.j3md");
//        mat.setTexture("m_ColorMap", assman.loadTexture("Textures/door.jpg"));
//
//        geom.setMaterial(mat);
//                MeshCollisionShape shape = new MeshCollisionShape(mesh);
//        physicsNode = new PhysicsNode(geom,shape,20);
//
//
//        physicsNode.attachDebugShape(assman);
//        physicsNode.setShadowMode(ShadowMode.Receive);
//
//        root.attachChild(physicsNode);
          root.setLocalTransform(new Transform(pos));
        ObjectUtil util = new ObjectUtil(env);

        util.initDoor(root,extent);
        

//        //physicsNode.setCollideWithGroups(physicsNode.COLLISION_GROUP_01);
//       physicsNode.attachDebugShape(assman);
//
//        physicsNode.setLocalTranslation(pos.x, pos.y, pos.z);
//        env.getRootNode().attachChild(physicsNode);
//      //  env.getRootNode().attachChild(geom);
//

        //env.getPhysicsSpace().add(physicsNode);

//            Geometry box_geo = new Geometry("brick", brick);
//    box_geo.setMaterial(wall_mat);
//    PhysicsNode brickNode = new PhysicsNode(
//     box_geo,      // geometry
//     boxCollisionShape, // collision shape
//     1.5f);       // mass
//    /** position the brick and activate shadows */
//    brickNode.setLocalTranslation(ori);
//    brickNode.setShadowMode(ShadowMode.CastAndReceive);
//    rootNode.attachChild(brickNode);
//    env.getPhysicsSpace().add(brickNode);
        


//

    }

    public void removeMyself()
    {
     //   env.getRootNode().detachChild(physicsNode);
    }
}
