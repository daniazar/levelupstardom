/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.AnimEventListener;
import com.jme3.app.SimpleApplication;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.collision.shapes.SphereCollisionShape;
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
import com.jme3.math.Transform;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import mygame.level.SceneLoader;
import mygame.media.SoundManager;
import mygame.stage.GameStageEnvironment;
import mygame.stage.stages.LevelFoundation;

/**
 *
 * @author dgrandes
 */
public class PlayerController implements AnimEventListener, ActionListener, PhysicsCollisionListener{

    public Node  player, COGplaceholder, baseplaceholder;
    public Node placeholder;
    private GameStageEnvironment env;
    private SceneLoader scLoader;
    private BitmapText sysout;
    static final Quaternion ROTATE_LEFT = new Quaternion();
        //animation
        private AnimChannel channel;
    private AnimControl control;

    private PhysicsCharacterNode playerPhysics;
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

        float[] myangles = {(float) Math.toRadians(90.0f), (float) Math.toRadians(90.0f), 0.0f};
    Quaternion modelStandUpRotation = new Quaternion(myangles);

    public PlayerController(SceneLoader loader) {
        env = loader.getGameStageEnv();
        scLoader = loader;
    }

    public PlayerController(SimpleApplication app)
    {
        env = (GameStageEnvironment)app;
    }
    public void init() {
        enableDebug();

        playerPhysics = new PhysicsCharacterNode(new SphereCollisionShape(scLoader.foundation.capsuleradius), .1f);
        playerPhysics.attachDebugShape(env.getAssetManager());
        playerPhysics.setCollisionGroup(playerPhysics.COLLISION_GROUP_01);
        playerPhysics.setJumpSpeed(70);
        playerPhysics.setFallSpeed(90);
        playerPhysics.setGravity(100);

        playerPhysics.setLocalTranslation(scLoader.foundation.spawnpoint);

                //Mesh
        enableDebug();
        Material mat_default = new Material(env.getAssetManager(), "Common/MatDefs/Misc/ShowNormals.j3md");
        player = (Node) env.getAssetManager().loadModel(scLoader.foundation.playermesh);

        placeholder = new Node();
        
        placeholder.attachChild(player);


   //     placeholder.attachChild(player);
//        player.getWorldBound().
        playerPhysics.attachChild(placeholder);

        placeholder.setLocalTransform(new Transform(new Vector3f(0,0,0)));
        
        player.setMaterial(mat_default);

   

        COGplaceholder = new Node("COG placeholder");
     //   COGplaceholder.attachChild(boxDebug);
        COGplaceholder.setLocalTranslation(new Vector3f(-2.56f, 9.0f, -7.318f));

        baseplaceholder = new Node("base plholder");
       // baseplaceholder.attachChild(baseDebug);
        baseplaceholder.setLocalTranslation(new Vector3f(-2.56f, 9.0f, -1.0f));
        player.attachChild(COGplaceholder);
        player.attachChild(baseplaceholder);
        reorient();

        placeholder.rotate(0.0f, (float)Math.toRadians(0.0f), 0.0f);
      //  playerPhysics.attachChild(placeholder);

        control = player.getControl(AnimControl.class);
        control.addListener(this);
        channel = control.createChannel();
        channel.setAnim("Start");
        setupKeys();


        env.getRootNode().attachChild(playerPhysics);
        env.getPhysicsSpace().add(playerPhysics);
        

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
             player.rotate(modelStandUpRotation);

             centerOnPlaceholder(baseplaceholder);
    }

    public void stop()
    {
        
        env.getRootNode().detachChild(playerPhysics);
        env.getRootNode().detachChild(player);

    }
    public void onAnimCycleDone(AnimControl control, AnimChannel channel, String animName) {
        play = false;
     //   if("Jump".equals(channel.getAnimationName()))
     //       SoundManager.playFallSound();
        channel.setAnim("Start", 1.0f);
    }

    public void onAnimChange(AnimControl control, AnimChannel channel, String animName) {
    }

    private void debug(String str) {
        sysout.setText(str);
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

        LevelFoundation lf = scLoader.foundation;
        Vector3f camDir = env.getCamera().getDirection().clone().multLocal(lf.playerspeed);
        Vector3f camLeft = env.getCamera().getLeft().clone().multLocal(lf.playerspeed);
               camDir.y = 0;
        camLeft.y = 0;
        walkDirection.set(0, 0, 0);
        modelDirection.set(0, 0, 2);
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
        if (!playerPhysics.onGround()) {
            airTime = airTime + tpf;
        } else {
            airTime = 0;
        }





        if (walkDirection.length() == 0) {
            SoundManager.stopStepsSound();
            if (!"Start".equals(channel.getAnimationName()) && !play) {
                channel.setAnim("Start", 0.0f);

            }
        } else {
            if (playerPhysics.onGround()) {
                SoundManager.playStepsSound();
            } else {
                SoundManager.stopStepsSound();
            }
            
            modelRotation.lookAt(walkDirection, Vector3f.UNIT_Y);
            if (airTime > .3f) {
                if (!"Start".equals(channel.getAnimationName()) && !play) {
                    channel.setAnim("Start", 0.0f);
                }
            } else if (!"Walk".equals(channel.getAnimationName())&& !play) {
                channel.setAnim("Walk", 0.0f);
            }
        }


        

        placeholder.setLocalRotation(modelRotation);
        //Gracias a Daniel AZAR por tremendo dato!!!!!!!
        placeholder.rotate(new Quaternion(new float[]{0.0f,(float)Math.toRadians(90),0.0f}));
        modelRotation.multLocal(modelDirection);
        modelRight.set(modelDirection);
        ROTATE_LEFT.multLocal(modelRight);
        playerPhysics.setWalkDirection(walkDirection);
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
        } else if (binding.equals("CharKick") && !play) {
            play = true;
            channel.setAnim("Kick");
        }
        else if (binding.equals("CharKneel") && !play) {
            play = true;
            channel.setAnim("Kneel");
        }

        if (binding.equals("CharSpace")&& !play) {
            play = true;
            channel.setAnim("Jump");
            playerPhysics.jump();
            SoundManager.stopStepsSound();
            SoundManager.playJumpSound();
        }
    }



//     private void setupAnimationController() {
//        animationControl = player.getControl(AnimControl.class);
//        animationControl.addListener(this);
//        animationChannel = animationControl.createChannel();
//    }


    public void collision(PhysicsCollisionEvent event) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void setupChaseCamera() {
          env.getCamera().setAxes(Vector3f.UNIT_X.negate(), Vector3f.UNIT_Y, Vector3f.UNIT_Z.negate());
        env.getFlyCamera().setEnabled(false);
        chaseCam = new ChaseCamera(env.getCamera(), playerPhysics, env.getInputManager());
      


    }

        private void centerOnPlaceholder(Node placeholder) {



        Vector3f offset = new Vector3f(placeholder.getWorldTranslation());

        Vector3f origin = playerPhysics.getWorldTranslation();
//        System.out.println(origin);

        origin.y -= scLoader.foundation.capsuleradius;

        offset = offset.subtract(origin);
//
         if(offset.length() < 1)
             return;
//        System.out.println(placeholder.getWorldTranslation());
//        System.out.println(origin);
//        System.out.println(offset);
        player.move(offset.negate());

//        System.out.println(player.getWorldTranslation());


    }


}
