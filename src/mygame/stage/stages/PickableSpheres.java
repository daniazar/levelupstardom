/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mygame.stage.stages;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.shape.Sphere;
import mygame.GameController;
import mygame.stage.GameStageEnvironment;

/**
 *
 * @author dgrandes
 */
public class PickableSpheres {

    public Vector3f pos;
    public float radius;
    public Geometry geom;
    public Material mat_deactivated;
    public Material mat_activated;
    private boolean activated = false;
    public PickableSpheres()
    {
    }

    public PickableSpheres(Vector3f pos, float radius)
    {
        this.pos = new Vector3f(pos);
        this.radius = radius;
 
    }

    public void init(GameStageEnvironment env)
    {
        Mesh mesh = new Sphere(16, 16, radius);
        geom = new Geometry("Sphere", mesh);
        AssetManager assman = env.getAssetManager();
        mat_activated = new Material(assman,  "Common/MatDefs/Misc/SolidColor.j3md");
        mat_deactivated = new Material(assman,  "Common/MatDefs/Misc/SolidColor.j3md");
        mat_activated.setColor("m_Color", ColorRGBA.Red);
        mat_deactivated.setColor("m_Color", ColorRGBA.Blue);

        geom.setMaterial(mat_deactivated);
        geom.move(pos);
    }

    public void activate()
    {
        if(activated)
            return;
        activated = true;
        geom.setMaterial(mat_activated);
    }

}
