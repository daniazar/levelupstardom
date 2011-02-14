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
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.nodes.PhysicsCharacterNode;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.input.ChaseCamera;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.material.Material;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import java.nio.channels.Channel;
import mygame.level.SceneLoader;
import mygame.stage.GameStageEnvironment;

/**
 *
 * @author dgrandes
 */
public class PlayerController implements AnimEventListener, ActionListener, PhysicsCollisionListener{

    private Node placeholder, player;
    private GameStageEnvironment env;
    private SceneLoader scLoader;
    private BitmapText sysout;
    static final Quaternion ROTATE_LEFT = new Quaternion();
        //animation
    AnimChannel animationChannel;
    AnimChannel shootingChannel;
    AnimControl animationControl;

    private PhysicsCharacterNode character;
    private boolean play = false;//play or stop the animation

    Vector3f walkDirection = new Vector3f();
    Quaternion modelRotation = new Quaternion();
    Vector3f modelDirection = new Vector3f();
    Vector3f modelRight = new Vector3f();

        float airTime = 0;
    //camera
    boolean left = false, right = false, up = false, down = false;
    ChaseCamera chaseCam;

    static {
        ROTATE_LEFT.fromAngleAxis(-FastMath.HALF_PI, Vector3f.UNIT_Y);
    }

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
    
        player.setMaterial(mat_default);
    centerModel();
           
        //Player Physics

        BoundingBox  bbox = ((BoundingBox)player.getWorldBound());
        CapsuleCollisionShape capsule = new CapsuleCollisionShape(2,10, 1);

        character = new PhysicsCharacterNode(capsule, 0.5f);

                   character.setJumpSpeed(20);
        character.setFallSpeed(30);
        character.setGravity(30);
        character.attachDebugShape(env.getAssetManager());
        character.attachChild(player);

                       reorient();
        //placeholder.attachChild(character);

        Vector3f spawn = scLoader.getSpawnPoint();
        spawn.z -= 20;
        character.setLocalTranslation(new Vector3f(0,40,0));
       
        scLoader.getLevel().attachChild(character);
        scLoader.getGameStageEnv().getPhysicsSpace().add(character);

        
        setupKeys();
        setupAnimationController();
      setupChaseCamera();
//        initKeys();
//        control = player.getControl(AnimControl.class);
//        control.addListener(this);
//        channel = control.createChannel();
//        channel.setAnim("Start");
    }

        private void setupKeys() {
        InputManager inputManager = env.getInputManager();
        inputManager.addMapping("wireframe", new KeyTrigger(KeyInput.KEY_T));
        inputManager.addListener(this, "wireframe");
        inputManager.addMapping("CharLeft", new KeyTrigger(KeyInput.KEY_J));
        inputManager.addMapping("CharRight", new KeyTrigger(KeyInput.KEY_L));
        inputManager.addMapping("CharUp", new KeyTrigger(KeyInput.KEY_I));
        inputManager.addMapping("CharDown", new KeyTrigger(KeyInput.KEY_K));
        inputManager.addMapping("CharSpace", new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addMapping("CharKneel", new KeyTrigger(KeyInput.KEY_N));
        inputManager.addMapping("CharKick", new KeyTrigger(KeyInput.KEY_M));

        inputManager.addListener(this, "CharLeft");
        inputManager.addListener(this, "CharRight");
        inputManager.addListener(this, "CharUp");
        inputManager.addListener(this, "CharDown");
        inputManager.addListener(this, "CharSpace");
        inputManager.addListener(this, "CharKneel");
        inputManager.addListener(this, "CharKick");
    }



//    private ActionListener actionListener = new ActionListener() {
//
//        public void onAction(String name, boolean keyPressed, float tpf) {
//            debug(name);
//            if (name.equals("ccw")) {
//                placeholder.rotate(0.0f, 0.0f, -0.05f);
//            }
//            if (name.equals("cw")) {
//                placeholder.rotate(0.0f, 0.0f, 0.05f);
//            }
//            if (name.equals("play") && !keyPressed) {
//                if (!play) {
//                    channel.setAnim("Walk", 0.5f);
//                    channel.setLoopMode(LoopMode.Loop);
//                    play = true;
//                } else {
//                    channel.setAnim("Start", 0.5f);
//                    play = false;
//                }
//            }
//        }
//    };

    private void reorient() {
       // character.scale(0.1f);
   //   character.rotate((float) Math.toRadians(180.0f), (float) Math.toRadians(90.0f), 0.0f);
    }

    public void onAnimCycleDone(AnimControl control, AnimChannel channel, String animName) {
        play = false;
        channel.setAnim("Start");
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

    public void update(float tpf)
    {
        if(play)
            return;

        Vector3f camDir = env.getCamera().getDirection().clone().multLocal(0.2f);
        Vector3f camLeft = env.getCamera().getLeft().clone().multLocal(0.2f);
               camDir.y = 0;
        camLeft.y = 0;
        walkDirection.set(0, 0, 0);
        modelDirection.set(0, 0, 0);
        if (left) {
            walkDirection.addLocal(camLeft);
        }
        if (right) {
            walkDirection.addLocal(camLeft.negate());
        }
        if (up) {
            walkDirection.addLocal(camDir);
        }
        if (down) {
            walkDirection.addLocal(camDir.negate());
        }
        if (!character.onGround()) {
            airTime = airTime + tpf;
        } else {
            airTime = 0;
        }

        int airtime = (int) airTime;



        if (walkDirection.length() == 0) {
            if (!"Start".equals(animationChannel.getAnimationName()) && !play) {
                animationChannel.setAnim("Start", 1f);
            }
        } else {
            modelRotation.lookAt(walkDirection.negate(), Vector3f.UNIT_Y);
            if (airTime > .3f) {
                if (!"Start".equals(animationChannel.getAnimationName()) && !play) {
                    animationChannel.setAnim("Start");
                }
            } else if (!"Walk".equals(animationChannel.getAnimationName())&& !play) {
                animationChannel.setAnim("Walk", 0.7f);
            }
        }
        player.setLocalRotation(modelRotation);
        modelRotation.multLocal(modelDirection);
        modelRight.set(modelDirection);
        ROTATE_LEFT.multLocal(modelRight);
        character.setWalkDirection(walkDirection);
    }

    public void onAction(String binding, boolean value, float tpf) {
        if (binding.equals("CharLeft")) {
            if (value) {
                left = true;
            } else {
                left = false;
            }
        } else if (binding.equals("CharRight")) {
            if (value) {
                right = true;
            } else {
                right = false;
            }
        } else if (binding.equals("CharUp")) {
            if (value) {
                up = true;
            } else {
                up = false;
            }
        } else if (binding.equals("CharDown")) {
            if (value) {
                down = true;
            } else {
                down = false;
            }
        } else if (binding.equals("CharSpace")) {
            character.jump();
        } else if (binding.equals("CharKick") && !play) {
            play = true;
            animationChannel.setAnim("Kick");
        }
        else if (binding.equals("CharKneel") && !play) {
            play = true;
            animationChannel.setAnim("Kneel");
        }
    }

    private void centerModel()
    {
   //     player.setLocalTranslation(new Vector3f(3f,4,8.0f));
        System.out.println("Centering");
        System.out.println(player.getLocalRotation());

        //PORQUE NO ROTAAAAAAAAAAAAAAAAAAAAAAAAA!!!!!!!!!!!!!!!
        player.rotate((float)Math.toRadians(90.0f),(float)Math.toRadians(0.0f),(float)Math.toRadians(0.0f));
        System.out.println(player.getLocalRotation());
    }

     private void setupAnimationController() {
        animationControl = player.getControl(AnimControl.class);
        animationControl.addListener(this);
        animationChannel = animationControl.createChannel();
//        shootingChannel = animationControl.createChannel();
////        System.out.println(animationControl.getSkeleton());
//        shootingChannel.addBone(animationControl.getSkeleton().getBone("uparm.right"));
//        shootingChannel.addBone(animationControl.getSkeleton().getBone("arm.right"));
//        shootingChannel.addBone(animationControl.getSkeleton().getBone("hand.right"));
    }


    public void collision(PhysicsCollisionEvent event) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void setupChaseCamera() {
        env.getFlyCamera().setEnabled(false);
        chaseCam = new ChaseCamera(env.getCamera(), character, env.getInputManager());

    }

}
