/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mygame.stage;
import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioRenderer;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.font.BitmapFont;
import com.jme3.input.FlyByCamera;
import com.jme3.input.InputManager;
import com.jme3.renderer.Camera;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import java.awt.Dimension;
import mygame.stage.stages.LevelStage;
//import jme3clogic.TriggerSystem;

public interface GameStageEnvironment {

    <T> T getGlobalProperty(Object key, Class<T> type);

    void setGlobalProperty(Object key, Object value);

    Node getRootNode();

    PhysicsSpace getPhysicsSpace();

    Camera getCamera();

    FlyByCamera getFlyCamera();

    //TriggerSystem getTriggerSystem();

    AssetManager getAssetManager();

    Node getGuiNode();

    InputManager getInputManager();

    Dimension getScreenSize();

    AudioRenderer getAudioRenderer();

    void setDefaultInputState(boolean enabled);

    BitmapFont getGuiFont();

    public ViewPort getViewPort();

    GameStage getLevelStage();

    void jumpToMainMenu();

    void GameOver();

    void setLevelStage(LevelStage stage);

    void reset();

}
