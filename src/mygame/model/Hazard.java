/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mygame.model;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.nodes.PhysicsNode;
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
public class Hazard {

    public Vector3f pos;
    public Vector3f extent;
    //Damage over Time
    public float dot;
    public Geometry geom;


    public Hazard()
    {

    }

    public Hazard(Vector3f pos, Vector3f extent, float dot)
    {
        this.pos = pos;
        this.extent = extent;
        this.dot = dot;
    }

    public void init(GameStageEnvironment env)
    {
          Mesh mesh = new Box(pos, extent.x, extent.y, extent.z);
        geom = new Geometry("Box", mesh);
        AssetManager assman = env.getAssetManager();
       Material mat = new Material( assman, "Common/MatDefs/Misc/SimpleTextured.j3md");
        mat.setTexture("m_ColorMap", assman.loadTexture("Textures/waste.gif"));


        geom.setMaterial(mat);

    }

}
