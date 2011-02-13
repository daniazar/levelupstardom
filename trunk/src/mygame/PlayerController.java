/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.AnimEventListener;
import com.jme3.animation.LoopMode;
import com.jme3.bounding.BoundingBox;
import com.jme3.bounding.BoundingVolume;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.nodes.PhysicsCharacterNode;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import mygame.level.SceneLoader;
import mygame.stage.GameStageEnvironment;

/**
 *
 * @author dgrandes
 */
public class PlayerController implements AnimEventListener {

    private Node placeholder, player;
    private GameStageEnvironment env;
    private SceneLoader scLoader;
    private BitmapText sysout;
    private AnimChannel channel;
    private AnimControl control;
    private PhysicsCharacterNode character;
    private boolean play = false;//play or stop the animation

    public PlayerController(SceneLoader loader) {
        env = loader.getGameStageEnv();
        scLoader = loader;
    }

    public void init() {
        enableDebug();

        placeholder = new Node();
       
     


        //Player Node
        Material mat_default = new Material(
                env.getAssetManager(), "Common/MatDefs/Misc/ShowNormals.j3md");
        player = (Node) env.getAssetManager().loadModel("Models/Player.j3o");
        player.setLocalTranslation(new Vector3f(0,0,0f));
        player.setMaterial(mat_default);

        //Player Physics

        BoundingBox  bbox = ((BoundingBox)player.getWorldBound());
        CapsuleCollisionShape capsule = new CapsuleCollisionShape(2,4, 2);

        character = new PhysicsCharacterNode(capsule, 0.01f);

        character.attachDebugShape(env.getAssetManager());
        character.attachChild(player);

                reorient();
                
        placeholder.attachChild(character);

        placeholder.setLocalTranslation(scLoader.getSpawnPoint());
      
        scLoader.getLevel().attachChild(placeholder);
        scLoader.getGameStageEnv().getPhysicsSpace().add(character);


        initKeys();
        control = player.getControl(AnimControl.class);
        control.addListener(this);
        channel = control.createChannel();
        channel.setAnim("Start");
    }

    private void initKeys() {
        InputManager inputManager = env.getInputManager();
        inputManager.addMapping("ccw", new KeyTrigger(KeyInput.KEY_LEFT));
        inputManager.addMapping("cw", new KeyTrigger(KeyInput.KEY_RIGHT));
        inputManager.addMapping("play", new KeyTrigger(KeyInput.KEY_P));

        inputManager.addListener(actionListener, "ccw");
        inputManager.addListener(actionListener, "cw");
        inputManager.addListener(actionListener, "play");
    }
    private ActionListener actionListener = new ActionListener() {

        public void onAction(String name, boolean keyPressed, float tpf) {
            debug(name);
            if (name.equals("ccw")) {
                placeholder.rotate(0.0f, 0.0f, -0.05f);
            }
            if (name.equals("cw")) {
                placeholder.rotate(0.0f, 0.0f, 0.05f);
            }
            if (name.equals("play") && !keyPressed) {
                if (!play) {
                    channel.setAnim("Walk", 0.5f);
                    channel.setLoopMode(LoopMode.Loop);
                    play = true;
                } else {
                    channel.setAnim("Start", 0.5f);
                    play = false;
                }
            }
        }
    };

    private void reorient() {
        character.scale(0.1f);
    }

    public void onAnimCycleDone(AnimControl control, AnimChannel channel, String animName) {
    }

    public void onAnimChange(AnimControl control, AnimChannel channel, String animName) {
    }

    private void debug(String str) {
        sysout.setText(str);
    }

    public BoundingVolume getBoundingVolume() {
        return player.getWorldBound();
    }

    private void enableDebug() {
//For debugging
        env.getFlyCamera().setMoveSpeed(40.0f);
        env.getGuiNode().detachAllChildren();
        BitmapFont guiFont = env.getGuiFont();
        guiFont = env.getAssetManager().loadFont("Interface/Fonts/Default.fnt");
        sysout = new BitmapText(guiFont, false);
        sysout.setSize(guiFont.getCharSet().getRenderedSize());
        sysout.setLocalTranslation(300, sysout.getLineHeight(), 0);
        env.getGuiNode().attachChild(sysout);
    }
}
