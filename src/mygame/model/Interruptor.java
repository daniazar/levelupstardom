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
import com.jme3.scene.shape.Sphere;
import mygame.stage.GameStageEnvironment;

/**
 *
 * @author dgrandes
 */
public class Interruptor {

    public Vector3f pos;
    public Obstacle obstacle;
    public Geometry geom;
    public Material mat_deactivated;
    public Material mat_activated;
    private boolean activated = false;

    public Interruptor() {
    }

    public Interruptor(Vector3f pos, Obstacle obstacle) {
        this.pos = pos;
        this.obstacle = obstacle;
    }

    public void init(GameStageEnvironment env) {
        Mesh mesh = new Sphere(4, 4, 2);
        geom = new Geometry("Sphere", mesh);
        AssetManager assman = env.getAssetManager();
        mat_activated = new Material(assman, "Common/MatDefs/Misc/SolidColor.j3md");
        mat_deactivated = new Material(assman, "Common/MatDefs/Misc/SolidColor.j3md");
        mat_activated.setColor("m_Color", ColorRGBA.Gray);
        mat_deactivated.setColor("m_Color", ColorRGBA.Yellow);

        geom.setMaterial(mat_deactivated);
        geom.move(pos);
    }

    public void activate() {
        if (activated) {
            return;
        }
        activated = true;
        obstacle.geom.removeFromParent();
        geom.setMaterial(mat_activated);
    }

    public boolean isActivated() {
        return activated;
    }
}
