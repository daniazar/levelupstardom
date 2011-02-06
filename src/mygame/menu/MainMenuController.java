/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mygame.menu;

import com.jme3.scene.Node;
import com.jme3.system.JmeCanvasContext;
import com.jme3.system.JmeSystem;
import java.awt.Canvas;
import java.awt.Color;
import mygame.GameController;

/**
 *
 * @author dgrandes
 */
public class MainMenuController extends Node {


    public MainMenuController()
    {
        JmeSystem.setLowPermissions(true);

        JmeCanvasContext context = (JmeCanvasContext) GameController.getInstance().getApp().getContext();
        Canvas canvas =  context.getCanvas();
        canvas.setSize(200, 200);
        canvas.setBackground(Color.red);
    }
}
