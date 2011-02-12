/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mygame;

import com.jme3.input.ChaseCamera;
import com.jme3.scene.Spatial;
import mygame.stage.GameStageEnvironment;

/**
 *
 * @author Dani
 */
public class ChaseCam {
    //camera
    boolean left = false, right = false, up = false, down = false;
    ChaseCamera chaseCam;

    GameStageEnvironment env;

    public ChaseCam(GameStageEnvironment env, Player player) {
        this.env = env;
        setupChaseCamera(player);
    }

    private void setupChaseCamera(Player player) {
        env.getFlyCamera().setEnabled(false);
        chaseCam = new  ChaseCamera(env.getCamera(), player.getCharacter(), env.getInputManager());
    }



}

