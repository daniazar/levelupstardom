/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mygame.util;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import mygame.media.SoundManager;

/**
 *
 * @author matiaspan
 */
public class Preferences {

    private static Preferences instance;

    private int soundFXLevel;
    private int musicLevel;

    private final static float MAXLEVEL = 100;

    private Preferences(int soundFXLevel, int musicLevel) {
        this.musicLevel = musicLevel;
        this.soundFXLevel = soundFXLevel;
    }
    
    private Preferences() {
        
    }

    public static void loadSavedPreferences(Preferences preferences) {
        File f = null;

        try {
            f = new File("Preferences.json");
            if(!f.exists()){
                f.createNewFile();

                Writer output = new BufferedWriter(new FileWriter(f));
                try {
                  output.write( new Gson().toJson(new Preferences(100, 100)) );
                } finally {
                  output.close();
                }

            }
        } catch (IOException e) {
            // Preferences can't be saved
        }

        try {
            FileReader fReader = new FileReader(f);
            BufferedReader reader = new BufferedReader(fReader);

            String line = null;
            StringBuilder content = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                content.append(line);
                content.append(System.getProperty("line.separator"));
            }

            Preferences savedPreferences = new Gson().fromJson(content.toString(), Preferences.class);
            preferences.soundFXLevel = savedPreferences.soundFXLevel;
            preferences.musicLevel = savedPreferences.musicLevel;
            reader.close();
        } catch (IOException e) {
            // Preferences can't be read
        }
    }

    public static Preferences getInstance() {
        if (instance == null) {
            instance = new Preferences();
            loadSavedPreferences(instance);
        }

        return instance;
    }

    public void syncWithFile() {
        File f = null;

        try {
            f = new File("Preferences.json");
            if(!f.exists()){
                f.createNewFile();
            }
        } catch (IOException e) {
            // Preferences can't be saved
        }

        try {
            Writer output = new BufferedWriter(new FileWriter(f));
            try {
              output.write( new Gson().toJson(this) );
            } finally {
              output.close();
            }
        } catch (IOException e) {
            // nothing
        }
    }

    public boolean isSoundFXEnabled() {
        return soundFXLevel != 0;
    }

    public boolean isMusicEnabled() {
        return musicLevel != 0;
    }

    public void upSoundFX() {
        if (soundFXLevel < MAXLEVEL) {
            soundFXLevel += 10;
        }
    }

    public void upMusic() {
        if (musicLevel < MAXLEVEL) {
            musicLevel += 10;
        }
    }

    public void lowerSoundFX() {
        if (soundFXLevel > 0) {
            soundFXLevel -= 10;
        }
    }

    public void lowerMusic() {
        if (musicLevel > 0) {
            musicLevel -= 10;
        }
    }

    public float getMusicLevel() {
        return ((float)musicLevel) / 100;
    }

    public float getSoundFXLevel() {
        return ((float)soundFXLevel) / 100;
    }

    public String getMusicLevelString() {
        return musicLevel + "%";
    }

    public String getSoundFXLevelString() {
        return soundFXLevel + "%";
    }
    
    public void saveInstance() {
        syncWithFile();
        SoundManager.setSoundFXLevel(soundFXLevel);
        SoundManager.setMusicLevel(musicLevel);
    }
}
