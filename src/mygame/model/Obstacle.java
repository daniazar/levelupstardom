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
    public ObjectUtil util;

    public Obstacle() {
    }

    public Obstacle(Vector3f pos, Vector3f extent) {
        this.pos = pos;
        this.extent = extent;
    }

    public void init(GameStageEnvironment env) {

        root = new Node();

        root.setLocalTransform(new Transform(pos));
        util = new ObjectUtil(env);

        util.initDoor(root, extent);


    }

    public void removeMyself() {
        //   env.getRootNode().detachChild(physicsNode);
    }
}
