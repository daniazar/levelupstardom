/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.scene.Node;
import mygame.menu.MainMenuController;

/**
 *
 * @author dgrandes
 */
public class GameController extends Node{

    private static GameController instance;
    private SimpleApplication app;

    private GameController(SimpleApplication app)
    {
        this.app = app;
        
    }

    public static GameController  getInstance(SimpleApplication app)
    {
        if(instance == null)
        {
            instance = new GameController(app);
            app.getRootNode().attachChild(instance);
        }

        return instance;

    }
    public static GameController getInstance()
    {
        return instance;
    }

    public SimpleApplication getApp()
    {
        return app;
    }
    public void showMenu() {

        MainMenuController mainMenuController = new MainMenuController();
        attachChild(mainMenuController);
        
    }

    public void showHighScore(){

    }

    public void showLevelSelectionMenu(){

    }

    public void showOptions(){

    }

    public void showGameOver(){

    }

    public void startGameplay(){

    }

    public void clearScreen()
    {
        detachAllChildren();
    }
}
