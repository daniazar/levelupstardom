/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mygame.model;

import com.jme3.asset.AssetManager;
import com.jme3.math.Transform;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import mygame.stage.GameStageEnvironment;
import mygame.util.ObjectUtil;

/**
 *
 * @author Dani
 */
public class BrickWall {


    public Vector3f pos;
    public Vector3f brickDimension;
    public Node node;


    public BrickWall(Vector3f pos, Vector3f brickDimension )
    {
        this.pos = pos;
        this.brickDimension = brickDimension;
        node = new Node();

    }

    public BrickWall() {
        node = new Node();

    }



    public void init(GameStageEnvironment env)
    {
        node.setLocalTransform(new Transform(pos));
        ObjectUtil util = new ObjectUtil(env);
        System.out.println("bricks" + pos + brickDimension );
        util.initWall(node, brickDimension);

    }


    

}
