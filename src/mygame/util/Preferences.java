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

/**
 *
 * @author matiaspan
 */
public class Preferences {

    private static Preferences instance;

    private boolean soundFXEnabled;
    private boolean musicEnabled;

    private Preferences(boolean musicEnabled, boolean soundFXEnabled) {
        this.musicEnabled = musicEnabled;
        this.soundFXEnabled = soundFXEnabled;
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
                  output.write( new Gson().toJson(new Preferences(false, false)) );
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
            preferences.musicEnabled = savedPreferences.musicEnabled;
            preferences.soundFXEnabled = savedPreferences.soundFXEnabled;
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

    public void setSoundFXEnabled(boolean enabled) {
        soundFXEnabled = enabled;
        syncWithFile();
    }

    public boolean isSoundFXEnabled() {
        return soundFXEnabled;
    }

    public void setMusicEnabled(boolean  enabled) {
        musicEnabled = enabled;
        syncWithFile();
    }

    public boolean isMusicEnabled() {
        return musicEnabled;
    }

    public void toggleMusic() {
        musicEnabled = !musicEnabled;
        syncWithFile();
    }

    public void toggleSoundFX() {
        soundFXEnabled = !soundFXEnabled;
        syncWithFile();
    }
}
