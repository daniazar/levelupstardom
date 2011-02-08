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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import mygame.model.Score;

/**
 *
 * @author matiaspan
 */
public class HighScores {

    private List<Score> timeScores;
    private List<Score> objectScores;

    private static HighScores instance;

    public enum ScoreType {
        SCORE_TIME, SCORE_OBJECT
    }

    private HighScores() {
        timeScores = new ArrayList<Score>();
        objectScores = new ArrayList<Score>();
    }

    public static HighScores getInstance() {
        if (instance == null) {
            instance = new HighScores();
            loadSavedHighscores(instance);
        }

        return instance;
    }

    private static void loadSavedHighscores(HighScores highscores) {
        File f = null;

        try {
            f = new File("Highscores.json");
            if(!f.exists()){
                f.createNewFile();

                Writer output = new BufferedWriter(new FileWriter(f));
                try {
                    HighScores highScores = new HighScores();

                    //Default HighScores
                    highScores.addHighScore("Dev Team", 10000.1f, ScoreType.SCORE_TIME);
                    highScores.addHighScore("Dev Team", 9000.1f, ScoreType.SCORE_TIME);
                    highScores.addHighScore("Dev Team", 8000.1f, ScoreType.SCORE_TIME);
                    highScores.addHighScore("Dev Team", 7000.1f, ScoreType.SCORE_TIME);
                    highScores.addHighScore("Dev Team", 6000.1f, ScoreType.SCORE_TIME);
                    highScores.addHighScore("Dev Team", 5000.1f, ScoreType.SCORE_TIME);
                    highScores.addHighScore("Dev Team", 4000.1f, ScoreType.SCORE_TIME);
                    highScores.addHighScore("Dev Team", 3000.1f, ScoreType.SCORE_TIME);
                    highScores.addHighScore("Dev Team", 2000.1f, ScoreType.SCORE_TIME);
                    highScores.addHighScore("Dev Team", 1000.1f, ScoreType.SCORE_TIME);

                    highScores.addHighScore("Dev Team", 10000, ScoreType.SCORE_OBJECT);
                    highScores.addHighScore("Dev Team", 9000, ScoreType.SCORE_OBJECT);
                    highScores.addHighScore("Dev Team", 8000, ScoreType.SCORE_OBJECT);
                    highScores.addHighScore("Dev Team", 7000, ScoreType.SCORE_OBJECT);
                    highScores.addHighScore("Dev Team", 6000, ScoreType.SCORE_OBJECT);
                    highScores.addHighScore("Dev Team", 5000, ScoreType.SCORE_OBJECT);
                    highScores.addHighScore("Dev Team", 4000, ScoreType.SCORE_OBJECT);
                    highScores.addHighScore("Dev Team", 3000, ScoreType.SCORE_OBJECT);
                    highScores.addHighScore("Dev Team", 2000, ScoreType.SCORE_OBJECT);
                    highScores.addHighScore("Dev Team", 1000, ScoreType.SCORE_OBJECT);
                    
                  output.write( new Gson().toJson(highScores) );
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

            HighScores savedHighScores = new Gson().fromJson(content.toString(), HighScores.class);
            highscores.timeScores = savedHighScores.timeScores;
            highscores.objectScores = savedHighScores.objectScores;
            reader.close();
        } catch (IOException e) {
            // Preferences can't be read
        }
    }

    public void addHighScore(String name, float score, ScoreType scoreType) {
        addHighScore(new Score(name, score), scoreType);
    }

    public void addHighScore(Score score, ScoreType scoreType) {
        if (scoreType == ScoreType.SCORE_TIME) {
            timeScores.add(score);
            Collections.sort(timeScores, new byScoreDescending());
        } else {
            objectScores.add(score);
            Collections.sort(objectScores, new byScoreDescending());
        }
    }

    public List<Score> getHighScore(ScoreType scoreType) {
        if (scoreType == scoreType.SCORE_TIME) {
            return timeScores;
        }
        return objectScores;
    }

    class byScoreDescending implements Comparator<Score> {

        public int compare(Score t, Score t1) {
            return (int)((t.score - t1.score) * 100);
        }

    }
}
