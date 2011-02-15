/*
 * Copyright (c) 2009-2010 jMonkeyEngine
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 * * Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 *
 * * Neither the name of 'jMonkeyEngine' nor the names of its contributors
 *   may be used to endorse or promote products derived from this software
 *   without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package mygame.stage;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.AnimEventListener;
import com.jme3.animation.LoopMode;
import com.jme3.bullet.BulletAppState;
import com.jme3.app.SimpleApplication;
import com.jme3.asset.plugins.HttpZipLocator;
import com.jme3.asset.plugins.ZipLocator;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.shapes.CompoundCollisionShape;
import com.jme3.bullet.collision.shapes.SphereCollisionShape;
import com.jme3.bullet.nodes.PhysicsCharacterNode;
import com.jme3.bullet.nodes.PhysicsNode;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.font.BitmapText;
import com.jme3.input.ChaseCamera;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.material.MaterialList;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.plugins.ogre.OgreMeshKey;
import com.jme3.scene.shape.Box;
import java.io.File;
import rollmadness.util.SceneIterable;

public class TestQ3 extends SimpleApplication implements ActionListener, AnimEventListener {

    private BulletAppState bulletAppState;
    private Spatial gameLevel;
    private PhysicsCharacterNode playerPhysics;
    private Vector3f walkDirection = new Vector3f();
    private static boolean useHttp = false;
    private boolean left = false, right = false, up = false, down = false;
    //Player
    private BitmapText sysout;//dubug to player screen
    private Node root, COGplaceholder, baseplaceholder, player;
    private AnimChannel channel;
    private AnimControl control;
    private boolean play = false;//play or stop the animation
    private float capsuleRadius = 5f;

    public static void main(String[] args) {
        File file = new File("quake3level.zip");
        if (!file.exists()) {
            useHttp = true;
        }
        TestQ3 app = new TestQ3();
        app.start();
    }
    private ChaseCamera chaseCam;

    public void simpleInitApp() {
        bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);
        // flyCam.setMoveSpeed(100);

        setupKeys();

        this.cam.setFrustumFar(2000);

        DirectionalLight dl = new DirectionalLight();
        dl.setColor(ColorRGBA.White.clone().multLocal(2));
        dl.setDirection(new Vector3f(-1, -1, -1).normalize());
        rootNode.addLight(dl);

        // load the level from zip or http zip
        if (useHttp) {
            assetManager.registerLocator("http://jmonkeyengine.googlecode.com/files/quake3level.zip", HttpZipLocator.class.getName());
        } else {
            assetManager.registerLocator("quake3level.zip", ZipLocator.class.getName());
        }

        // create the geometry and attach it
        MaterialList matList = (MaterialList) assetManager.loadAsset("Scene.material");
        OgreMeshKey key = new OgreMeshKey("main.meshxml", matList);
        gameLevel = (Spatial) assetManager.loadAsset(key);
        gameLevel.setLocalScale(0.1f);

        CompoundCollisionShape levelShape = CollisionShapeFactory.createMeshCompoundShape((Node) gameLevel);

        PhysicsNode levelNode = new PhysicsNode(gameLevel, levelShape, 0);
        playerPhysics = new PhysicsCharacterNode(new SphereCollisionShape(capsuleRadius), .01f);
        playerPhysics.attachDebugShape(getAssetManager());
        playerPhysics.setJumpSpeed(20);
        playerPhysics.setFallSpeed(30);
        playerPhysics.setGravity(30);

        playerPhysics.setLocalTranslation(new Vector3f(60, 10, -60));


        //Mesh
        enableDebug();
        Material mat_default = new Material(assetManager, "Common/MatDefs/Misc/ShowNormals.j3md");
        player = (Node) assetManager.loadModel("Models/Chest.j3o");

//        player.getWorldBound().

        player.setMaterial(mat_default);

        playerPhysics.attachChild(player);

        //DEBUGGING AND PLACEHOLDING OFFSET


        //Debug sphere
        Box box = new Box(0.1f, 0.1f, 0.1f);
        Spatial boxDebug = new Geometry("box", box);
        Spatial baseDebug = new Geometry("baseBox", box);

        boxDebug.setMaterial(mat_default);
        baseDebug.setMaterial(mat_default);

        COGplaceholder = new Node("COG placeholder");
     //   COGplaceholder.attachChild(boxDebug);
        COGplaceholder.setLocalTranslation(new Vector3f(-2.56f, 9.0f, -7.318f));

        baseplaceholder = new Node("base plholder");
       // baseplaceholder.attachChild(baseDebug);
        baseplaceholder.setLocalTranslation(new Vector3f(-2.56f, 9.0f, -2f));
        player.attachChild(COGplaceholder);
        player.attachChild(baseplaceholder);
        reorient();


        initKeys();

        //setup animation and set to Start animation as named in Ogre exporter

        control = player.getControl(AnimControl.class);
        control.addListener(this);
        channel = control.createChannel();
        channel.setAnim("Start");

        rootNode.attachChild(levelNode);
        rootNode.attachChild(playerPhysics);
        setupChaseCamera();
        getPhysicsSpace().add(levelNode);
        getPhysicsSpace().add(playerPhysics);
    }

    private PhysicsSpace getPhysicsSpace() {
        return bulletAppState.getPhysicsSpace();
    }

    @Override
    public void simpleUpdate(float tpf) {
        Vector3f camDir = cam.getDirection().clone().multLocal(0.6f);
        Vector3f camLeft = cam.getLeft().clone().multLocal(0.4f);
        walkDirection.set(0, 0, 0);
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
        playerPhysics.setWalkDirection(walkDirection);
        cam.setLocation(playerPhysics.getLocalTranslation());
    }

    private void setupKeys() {
        inputManager.addMapping("Lefts", new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping("Rights", new KeyTrigger(KeyInput.KEY_D));
        inputManager.addMapping("Ups", new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping("Downs", new KeyTrigger(KeyInput.KEY_S));
        inputManager.addMapping("Space", new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addListener(this, "Lefts");
        inputManager.addListener(this, "Rights");
        inputManager.addListener(this, "Ups");
        inputManager.addListener(this, "Downs");
        inputManager.addListener(this, "Space");
    }

    private void setupChaseCamera() {
        flyCam.setEnabled(false);
        chaseCam = new ChaseCamera(cam, playerPhysics, inputManager);

    }

    public void onAction(String binding, boolean value, float tpf) {

        if (binding.equals("Lefts")) {
            if (value) {
                left = true;
            } else {
                left = false;
            }
        } else if (binding.equals("Rights")) {
            if (value) {
                right = true;
            } else {
                right = false;
            }
        } else if (binding.equals("Ups")) {
            if (value) {
                up = true;
            } else {
                up = false;
            }
        } else if (binding.equals("Downs")) {
            if (value) {
                down = true;
            } else {
                down = false;
            }
        } else if (binding.equals("Space")) {
            playerPhysics.jump();
        }
    }

    private void initKeys() {
        inputManager.addMapping("ccw", new KeyTrigger(KeyInput.KEY_LEFT));
        inputManager.addMapping("cw", new KeyTrigger(KeyInput.KEY_RIGHT));
        inputManager.addMapping("play", new KeyTrigger(KeyInput.KEY_P));
        inputManager.addMapping("kneel", new KeyTrigger(KeyInput.KEY_O));
        inputManager.addMapping("kick", new KeyTrigger(KeyInput.KEY_L));

        inputManager.addListener(actionListener, "ccw");
        inputManager.addListener(actionListener, "cw");
        inputManager.addListener(actionListener, "play");
        inputManager.addListener(actionListener, "kneel");
        inputManager.addListener(actionListener, "kick");

    }
    private ActionListener actionListener = new ActionListener() {

        public void onAction(String name, boolean keyPressed, float tpf) {
            debug(name);
            if (name.equals("ccw")) {
                COGplaceholder.rotate(90.0f, 0.0f, 0f);
            }
            if (name.equals("cw")) {
                COGplaceholder.rotate(-90.0f, 0.0f, 0f);
            }
            if (name.equals("play") && !keyPressed) {
                if (!play) {
                    channel.setAnim("Walk", 0.0f);
                    channel.setLoopMode(LoopMode.Loop);
                    play = true;
                } else {
                    channel.setAnim("Start", 0.0f);
                    play = false;
                }
            } else if (name.equals("kneel")) {
                if (!play) {
                    channel.setAnim("Kneel", 0.0f);
                   // channel.setLoopMode(LoopMode.Loop);
                    play = true;
                } else {
                    channel.setAnim("Start", 0f);
                    play = false;
                }
            } else if (name.equals("kick")) {
                if (!play) {
                    channel.setAnim("Kick", 0f);
                 //   channel.setLoopMode(LoopMode.Loop);
                    play = true;
                } else {
                    channel.setAnim("Start", 0.0f);
                    play = false;
                }
            }

        }
    };

//need to implement listener methods
    public void onAnimCycleDone(AnimControl control, AnimChannel channel,
            String name) {
    }

    public void onAnimChange(AnimControl control, AnimChannel channel, String name) {
    }

    private void enableDebug() {
//For debugging
        guiNode.detachAllChildren();
        guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
        sysout = new BitmapText(guiFont, false);
        sysout.setSize(guiFont.getCharSet().getRenderedSize());
        sysout.setLocalTranslation(300, sysout.getLineHeight(), 0);
        guiNode.attachChild(sysout);
    }

    private void debug(String str) {
        sysout.setText(str);
    }

    private void reorient() {

        player.rotate((float) Math.toRadians(90.0f), (float) Math.toRadians(90.0f), 0.0f);

        centerOnPlaceholder(baseplaceholder);

        //root.setLocalTranslation(new Vector3f(0.0f, -2.5f, 0.0f));
        // rootNode.attachChild(root);
//disable standard settings
        //  flyCam.setEnabled(true);
    }

    private void centerOnPlaceholder(Node placeholder) {

        
    
        Vector3f offset = new Vector3f(placeholder.getWorldTranslation());
      
        Vector3f origin = player.getParent().getWorldTranslation();
        System.out.println(origin);
        origin.y -= capsuleRadius;
 
        offset = offset.subtract(origin);

        System.out.println(placeholder.getWorldTranslation());
        System.out.println(origin);
        System.out.println(offset);
        player.move(offset.negate());
       
        System.out.println(player.getWorldTranslation());


    }


}
