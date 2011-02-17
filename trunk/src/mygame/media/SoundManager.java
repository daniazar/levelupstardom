/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mygame.media;

import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioNode;
import com.jme3.audio.AudioNode.Status;
import com.jme3.audio.AudioRenderer;
import mygame.util.Preferences;

/**
 *
 * @author matiaspan
 */
public class SoundManager {

    private static AudioRenderer audioRenderer = null;
    private static AssetManager assetManager = null;

    private static AudioNode fallSound = null;
    private static AudioNode stepsSound = null;
    private static AudioNode gameOverSound = null;
    private static AudioNode objectPickUpSound = null;
    private static AudioNode menuBGMSound = null;
    private static AudioNode gameBGMSound = null;
    private static AudioNode damagesound = null;

    private static Float soundFXLevel = null;
    private static Float musicLevel = null;

    private static void initLevels() {
        if (musicLevel == null || soundFXLevel == null) {
            musicLevel = Preferences.getInstance().getMusicLevel() * 100;
            soundFXLevel = Preferences.getInstance().getSoundFXLevel() * 100;

            System.out.println("music " + musicLevel);
            System.out.println("soundFX " + soundFXLevel);
        }
    }

    public static void initSoundManager(AssetManager assetManager, AudioRenderer audioRenderer) {
        SoundManager.audioRenderer = audioRenderer;
        SoundManager.assetManager = assetManager;
    }

    public static void playGameBGM() {
        initLevels();
        try {
            if (gameBGMSound == null) {
                gameBGMSound = new AudioNode(assetManager, "sounds/wav/gameBGM.wav");
                gameBGMSound.setLooping(true);
                gameBGMSound.setVolume(musicLevel / 10);
            }

            if(gameBGMSound.getStatus() != Status.Playing && Preferences.getInstance().isMusicEnabled()) {
                audioRenderer.playSource(gameBGMSound);
            }

        } catch (Exception e) {
            System.out.println("Sound file not found, or something");
        }
    }

    public static void playMenuBGM() {
        initLevels();
        try {
            if (menuBGMSound == null) {
                menuBGMSound = new AudioNode(assetManager, "sounds/wav/menuBGM.wav");
                menuBGMSound.setLooping(true);
                menuBGMSound.setVolume(musicLevel / 10);
            }

            if(menuBGMSound.getStatus() != Status.Playing && Preferences.getInstance().isMusicEnabled()) {
                audioRenderer.playSource(menuBGMSound);
            }

        } catch (Exception e) {
            System.out.println("Sound file not found, or something");
        }
    }

    public static void playStepsSound() {
        initLevels();
        try {
            if (stepsSound == null) {
                stepsSound = new AudioNode(assetManager, "sounds/wav/steps.wav");
                stepsSound.setLooping(true);
                stepsSound.setVolume(soundFXLevel / 10);
            }

            if(menuBGMSound.getStatus() != Status.Playing && Preferences.getInstance().isSoundFXEnabled()) {
                audioRenderer.playSource(stepsSound);
            }

        } catch (Exception e) {
            System.out.println("Sound file not found, or something");
        }
    }

    public static void playJumpSound() {
        initLevels();
    }

    public static void playObjectPickUpSound() {
        initLevels();
        try {
            if (objectPickUpSound == null) {
                objectPickUpSound = new AudioNode(assetManager, "sounds/wav/objectPickUp.wav");
                objectPickUpSound.setLooping(false);
                objectPickUpSound.setVolume(soundFXLevel / 10);
            }

            if(objectPickUpSound.getStatus() != Status.Playing && Preferences.getInstance().isSoundFXEnabled()) {
                audioRenderer.playSource(objectPickUpSound);
            }

        } catch (Exception e) {
            System.out.println("Sound file not found, or something");
        }
    }

    public static void playFallSound() {
        initLevels();
        try {
            if (fallSound == null) {
                fallSound = new AudioNode(assetManager, "sounds/wav/fall.wav");
                fallSound.setLooping(false);
                fallSound.setVolume(soundFXLevel / 10);
            }

            if(fallSound.getStatus() != Status.Playing && Preferences.getInstance().isSoundFXEnabled()) {
                audioRenderer.playSource(fallSound);
            }

        } catch (Exception e) {
            System.out.println("Sound file not found, or something");
        }
    }

    public static void playDamageSound() {
        initLevels();
        try {
            if (fallSound == null) {
                fallSound = new AudioNode(assetManager, "sounds/wav/damage.wav");
                fallSound.setLooping(false);
                fallSound.setVolume(soundFXLevel / 10);
            }

            if(fallSound.getStatus() != Status.Playing && Preferences.getInstance().isSoundFXEnabled()) {
                audioRenderer.playSource(fallSound);
            }

        } catch (Exception e) {
            System.out.println("Sound file not found, or something");
        }
    }

    public static void playDeathSound() {
        initLevels();
        try {
            if (fallSound == null) {
                fallSound = new AudioNode(assetManager, "sounds/wav/damage.wav");
                fallSound.setLooping(false);
                fallSound.setVolume(soundFXLevel / 10);
            }

            if(fallSound.getStatus() != Status.Playing && Preferences.getInstance().isSoundFXEnabled()) {
                audioRenderer.playSource(fallSound);
            }

        } catch (Exception e) {
            System.out.println("Sound file not found, or something");
        }
    }

    public static void playVictorySound() {
        initLevels();
    }

    public static void playGameOverSound() {
        initLevels();
        try {
            if (gameOverSound == null) {
                gameOverSound = new AudioNode(assetManager, "sounds/wav/gameOver.wav");
                gameOverSound.setLooping(false);
                gameOverSound.setVolume(musicLevel / 10);
            }

            if(gameOverSound.getStatus() != Status.Playing && Preferences.getInstance().isMusicEnabled()) {
                audioRenderer.playSource(gameOverSound);
            }

        } catch (Exception e) {
            System.out.println("Sound file not found, or something");
        }
    }

    public static void stopGameBGM() {
        if (gameBGMSound != null && gameBGMSound.getStatus() == Status.Playing) audioRenderer.stopSource(gameBGMSound);
    }

    public static void stopMenuBGM() {
        if (menuBGMSound != null && menuBGMSound.getStatus() == Status.Playing) audioRenderer.stopSource(menuBGMSound);
    }

    public static void stopStepsSound() {
        if (stepsSound != null && stepsSound.getStatus() == Status.Playing) audioRenderer.stopSource(stepsSound);
    }

    public static void setSoundFXLevel(int level) {
        SoundManager.stopStepsSound();

        soundFXLevel = new Float(level);

        stepsSound = null;
        damagesound = null;
        fallSound = null;
        objectPickUpSound = null;
    }

    public static void setMusicLevel(int level) {
        SoundManager.stopGameBGM();
        SoundManager.stopMenuBGM();

        musicLevel = new Float(level);

        gameBGMSound = null;
        menuBGMSound = null;
        gameOverSound = null;

        SoundManager.playMenuBGM();
    }
}
