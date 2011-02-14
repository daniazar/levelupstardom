/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mygame.media;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 *
 * @author matiaspan
 */
public class SoundManager {

    private static Clip fallClip = null;
    private static Clip stepsClip = null;
    private static Clip gameOverClip = null;
    private static Clip objectPickUpClip = null;
    private static Clip menuBGMClip = null;
    private static Clip gameBGMClip = null;
    private static Clip damageClip = null;


    public static void playGameBGM() {
        try {
            if (gameBGMClip == null) {
                File soundFile = new File("sounds/wav/gameBGM.wav");
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
                gameBGMClip = AudioSystem.getClip();
                gameBGMClip.open(audioIn);
            }

            if(!gameBGMClip.isRunning()) {
                gameBGMClip.loop(Clip.LOOP_CONTINUOUSLY);
            }


        } catch (Exception e) {
            System.out.println("Sound file not found, or something");
        }
    }

    public static void playMenuBGM() {
        try {
            if (menuBGMClip == null) {
                File soundFile = new File("sounds/wav/menuBGM.wav");
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
                menuBGMClip = AudioSystem.getClip();
                menuBGMClip.open(audioIn);
            }

            if(!menuBGMClip.isRunning()) {
                menuBGMClip.loop(Clip.LOOP_CONTINUOUSLY);
            }


        } catch (Exception e) {
            System.out.println("Sound file not found, or something");
        }
    }

    public static void playStepsSound() {
        try {
            if (stepsClip == null) {
                File soundFile = new File("sounds/wav/steps.wav");
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
                stepsClip = AudioSystem.getClip();
                stepsClip.open(audioIn);
            }

            if(!stepsClip.isRunning()) {
                stepsClip.loop(Clip.LOOP_CONTINUOUSLY);
            }
            

        } catch (Exception e) {
            System.out.println("Sound file not found, or something");
        }
    }

    public static void playJumpSound() {

    }

    public static void playObjectPickUpSound() {
        try {
            if (objectPickUpClip == null) {
                File soundFile = new File("sounds/wav/objectPickUp.wav");
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
                objectPickUpClip = AudioSystem.getClip();
                objectPickUpClip.open(audioIn);
            }

            if(!objectPickUpClip.isRunning()) {
                objectPickUpClip.start();
            }


        } catch (Exception e) {
            System.out.println("Sound file not found, or something");
        }
    }

    public static void playFallSound() {
        try {
            if (fallClip == null) {
                File soundFile = new File("sounds/wav/fall.wav");
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
                fallClip = AudioSystem.getClip();
                fallClip.open(audioIn);
            }

            if(!fallClip.isRunning()) {
                fallClip.start();
            }


        } catch (Exception e) {
            System.out.println("Sound file not found, or something");
        }
    }

    public static void playDamageSound() {
        try {
            if (damageClip == null) {
                File soundFile = new File("sounds/wav/damage.wav");
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
                damageClip = AudioSystem.getClip();
                damageClip.open(audioIn);
            }

            if(!damageClip.isRunning()) {
                damageClip.start();
            }


        } catch (Exception e) {
            System.out.println("Sound file not found, or something");
        }
    }

    public static void playDeathSound() {
        try {
            if (damageClip == null) {
                File soundFile = new File("sounds/wav/damage.wav");
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
                damageClip = AudioSystem.getClip();
                damageClip.open(audioIn);
            }

            if(!damageClip.isRunning()) {
                damageClip.start();
            }


        } catch (Exception e) {
            System.out.println("Sound file not found, or something");
        }
    }

    public static void playVictorySound() {

    }

    public static void playGameOverSound() {
        try {
            if (gameOverClip == null) {
                File soundFile = new File("sounds/wav/gameOver.wav");
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
                gameOverClip = AudioSystem.getClip();
                gameOverClip.open(audioIn);
            }

            if(!gameOverClip.isRunning()) {
                gameOverClip.start();
            }


        } catch (Exception e) {
            System.out.println("Sound file not found, or something");
        }
    }

    public static void stopGameBGM() {
        if (gameBGMClip != null && gameBGMClip.isRunning()) gameBGMClip.stop();
    }

    public static void stopMenuBGM() {
        if (menuBGMClip != null && menuBGMClip.isRunning()) menuBGMClip.stop();
    }

    public static void stopStepsSound() {
        if (stepsClip != null && stepsClip.isRunning()) stepsClip.stop();
    }

}
