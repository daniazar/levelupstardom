/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mygame.model;

import com.jme3.asset.AssetManager;
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
public class Goal {

    public Vector3f pos;
    public Vector3f extent;
    public Geometry geom;

    public Goal()
    {

    }

    public Goal(Vector3f pos, Vector3f extent, float dot)
    {
        this.pos = pos;
        this.extent = extent;
    }

    public void init(GameStageEnvironment env)
    {
          Mesh mesh = new Box(pos, extent.x, extent.y, extent.z);
        geom = new Geometry("Box", mesh);
        AssetManager assman = env.getAssetManager();
        Material mat  = new Material(assman,  "Common/MatDefs/Misc/SolidColor.j3md");

        mat.setColor("m_Color", ColorRGBA.Orange);


        geom.setMaterial(mat);

    }

}
