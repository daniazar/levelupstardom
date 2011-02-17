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
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.shape.Box;
import mygame.stage.GameStageEnvironment;

/**
 *
 * @author dgrandes
 */
public class Obstacle {

    public Vector3f pos;
    public Vector3f extent;
    public Geometry geom;
       public PhysicsNode physicsNode;
       public GameStageEnvironment env;
    public Obstacle() {
    }

    public Obstacle(Vector3f pos, Vector3f extent) {
        this.pos = pos;
        this.extent = extent;
    }

    public void init(GameStageEnvironment env) {
        this.env = env;
        Mesh mesh = new Box(pos, extent.x, extent.y, extent.z);
        geom = new Geometry("Box", mesh);
                AssetManager assman = env.getAssetManager();
        Material mat = new Material( assman, "Common/MatDefs/Misc/SimpleTextured.j3md");
        mat.setTexture("m_ColorMap", assman.loadTexture("Textures/door.jpg"));

        geom.setMaterial(mat);

        //geom.addControl(new RigidBody).setMaterial(mat);

        Vector3f extenthalf = new Vector3f(extent.x / 2, extent.y / 2, extent.z / 2);
        BoxCollisionShape shape = new BoxCollisionShape(extenthalf);
        physicsNode = new PhysicsNode(geom,shape,0);
        //physicsNode.setCollideWithGroups(physicsNode.COLLISION_GROUP_01);
       physicsNode.attachDebugShape(assman);

        physicsNode.setLocalTranslation(pos.x, pos.y, pos.z);
        env.getRootNode().attachChild(physicsNode);
      //  env.getRootNode().attachChild(geom);

        env.getPhysicsSpace().add(physicsNode);
        


//

    }

    public void removeMyself()
    {
        env.getRootNode().detachChild(physicsNode);
    }
}
