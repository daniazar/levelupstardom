/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mygame;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.AnimEventListener;
import com.jme3.animation.LoopMode;
import com.jme3.asset.TextureKey;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.collision.shapes.SphereCollisionShape;
import com.jme3.bullet.nodes.PhysicsCharacterNode;
import com.jme3.bullet.nodes.PhysicsNode;
import com.jme3.effect.EmitterSphereShape;
import com.jme3.effect.ParticleEmitter;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial.CullHint;
import com.jme3.scene.shape.Sphere;
import mygame.stage.GameStageEnvironment;
import com.jme3.effect.ParticleMesh.Type;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Sphere.TextureMode;

import com.jme3.texture.Texture;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import mygame.util.ObjectUtil;
/**
 *
 * @author Dani
 */
public class Player implements ActionListener, PhysicsCollisionListener, AnimEventListener{


        //bullet
    Sphere bullet;
    SphereCollisionShape bulletCollisionShape;
    //explosion
    ParticleEmitter effect;

  Material stone_mat;

       //character
    public PhysicsCharacterNode character;
    public Node model;

    public static final Quaternion ROTATE_LEFT = new Quaternion();

    public boolean left = false, right = false, up = false, down = false;

    Material matBullet;
    //temp vectors
    public Vector3f walkDirection = new Vector3f();
    public Quaternion modelRotation = new Quaternion();
    public Vector3f modelDirection = new Vector3f();
    public Vector3f modelRight = new Vector3f();

        //animation
    public AnimChannel animationChannel;
    public AnimChannel shootingChannel;
    public AnimControl animationControl;
    public float airTime = 0;


    public Node root;
    GameStageEnvironment env;
    ObjectUtil util;
    public Player(GameStageEnvironment env, Node root, ObjectUtil util) {

        this.root = root;
        this.env = env;
        this.util = util;
        createCharacter();
        setupAnimationController();
        prepareBullet();
        prepareEffect();

          env.getInputManager().addMapping("CharLeft", new KeyTrigger(KeyInput.KEY_A));
        env.getInputManager().addMapping("CharRight", new KeyTrigger(KeyInput.KEY_D));
        env.getInputManager().addMapping("CharUp", new KeyTrigger(KeyInput.KEY_W));
        env.getInputManager().addMapping("CharDown", new KeyTrigger(KeyInput.KEY_S));
        env.getInputManager().addMapping("CharSpace", new KeyTrigger(KeyInput.KEY_RETURN));
        env.getInputManager().addMapping("CharShoot", new KeyTrigger(KeyInput.KEY_SPACE));
        env.getInputManager().addListener(this, "CharLeft");
        env.getInputManager().addListener(this, "CharRight");
        env.getInputManager().addListener(this, "CharUp");
        env.getInputManager().addListener(this, "CharDown");
        env.getInputManager().addListener(this, "CharSpace");
        env.getInputManager().addListener(this, "CharShoot");


    }


     private void prepareBullet() {
        stone_mat = new Material(env.getAssetManager(), "Common/MatDefs/Misc/SimpleTextured.j3md");
        TextureKey key2 = new TextureKey("Textures/Terrain/Rock/Rock.PNG");
        key2.setGenerateMips(true);
        Texture tex2 = env.getAssetManager().loadTexture(key2);
        stone_mat.setTexture("m_ColorMap", tex2);
        bullet = new Sphere(32, 32, 0.4f, true, false);
        bullet.setTextureMode(TextureMode.Projected);
        bulletCollisionShape = new SphereCollisionShape(0.4f);
        matBullet = new Material(env.getAssetManager(), "Common/MatDefs/Misc/WireColor.j3md");
        matBullet.setColor("m_Color", ColorRGBA.Green);
        env.getPhysicsSpace().addCollisionListener(this);
    }

    private void prepareEffect() {
        int COUNT_FACTOR = 1;
        float COUNT_FACTOR_F = 1f;
        effect = new ParticleEmitter("Flame", Type.Triangle, 32 * COUNT_FACTOR);
        effect.setSelectRandomImage(true);
        effect.setStartColor(new ColorRGBA(1f, 0.4f, 0.05f, (float) (1f / COUNT_FACTOR_F)));
        effect.setEndColor(new ColorRGBA(.4f, .22f, .12f, 0f));
        effect.setStartSize(1.3f);
        effect.setEndSize(2f);
        effect.setShape(new EmitterSphereShape(Vector3f.ZERO, 1f));
        effect.setParticlesPerSec(0);
        effect.setGravity(-5f);
        effect.setLowLife(.4f);
        effect.setHighLife(.5f);
        effect.setStartVel(new Vector3f(0, 7, 0));
        effect.setVariation(1f);
        effect.setImagesX(2);
        effect.setImagesY(2);
        Material mat = new Material(env.getAssetManager(), "Common/MatDefs/Misc/Particle.j3md");
        mat.setTexture("m_Texture", env.getAssetManager().loadTexture("Effects/Explosion/flame.png"));
        effect.setMaterial(mat);
        effect.setLocalScale(100);
        effect.setCullHint(CullHint.Never);
        root.attachChild(effect);
    }

    private void createCharacter() {
        CapsuleCollisionShape capsule = new CapsuleCollisionShape(1.5f, 2f);
        character = new PhysicsCharacterNode(capsule, 0.01f);
        model = (Node) env.getAssetManager().loadModel("Models/Oto/Oto.mesh.xml");
        model.setLocalScale(0.5f);
        character.attachChild(model);
        character.setLocalTranslation(new Vector3f(-140, 10, -10));
        root.attachChild(character);
        env.getPhysicsSpace().add(character);
    }

    public PhysicsCharacterNode getCharacter() {
        return character;
    }

    private void setupAnimationController() {
        animationControl = model.getControl(AnimControl.class);
        animationControl.addListener(this);
        animationChannel = animationControl.createChannel();
        shootingChannel = animationControl.createChannel();
//        System.out.println(animationControl.getSkeleton());
        shootingChannel.addBone(animationControl.getSkeleton().getBone("uparm.right"));
        shootingChannel.addBone(animationControl.getSkeleton().getBone("arm.right"));
        shootingChannel.addBone(animationControl.getSkeleton().getBone("hand.right"));
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
        } else if (binding.equals("CharShoot") && !value) {
            shootBullet();
            //util.makeCannonBall(root);
        }
    }

    private void shootBullet() {

        shootingChannel.setAnim("Dodge", 0.1f);
        shootingChannel.setLoopMode(LoopMode.DontLoop);
        Geometry bulletg = new Geometry("bullet", bullet);
        bulletg.setMaterial(stone_mat);
        PhysicsNode bulletNode = new PhysicsNode(bulletg, bulletCollisionShape, 1);
        bulletNode.setCcdMotionThreshold(0.1f);
        bulletNode.setName("bullet");
        bulletNode.setLocalTranslation(character.getLocalTranslation().add(modelDirection.mult(1.8f).addLocal(modelRight.mult(0.9f))));
        bulletNode.setShadowMode(ShadowMode.CastAndReceive);
        bulletNode.setLinearVelocity(modelDirection.mult(40));
        root.attachChild(bulletNode);
        env.getPhysicsSpace().add(bulletNode);
    }

    public void collision(PhysicsCollisionEvent event) {
        if ("bullet".equals(event.getNodeA().getName())) {
            final Node node = event.getNodeA();
            env.getPhysicsSpace().remove(node);
            node.removeFromParent();
            effect.killAllParticles();
            effect.setLocalTranslation(node.getLocalTranslation());
            effect.emitAllParticles();
        } else if ("bullet".equals(event.getNodeB().getName())) {
            final Node node = event.getNodeB();
            env.getPhysicsSpace().remove(node);
            node.removeFromParent();
            effect.killAllParticles();
            effect.setLocalTranslation(node.getLocalTranslation());
            effect.emitAllParticles();
        }
    }

    public void onAnimCycleDone(AnimControl control, AnimChannel channel, String animName) {
        if (channel == shootingChannel) {
            channel.setAnim("stand");
        }
    }

    public void onAnimChange(AnimControl control, AnimChannel channel, String animName) {
    }
}
