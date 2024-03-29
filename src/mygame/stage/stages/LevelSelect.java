/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mygame.stage.stages;

import java.awt.Dimension;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import jme3ui.event.action.UIAction;
import jme3ui.event.action.UIActionListener;
import jme3ui.layouts.UIGridLayout;
import jme3ui.widgets.UIButton;
import jme3ui.widgets.UIFrame;
import jme3ui.widgets.UIPanel;
import mygame.model.Level;
import mygame.stage.GameStage;
import mygame.stage.GameStageEnvironment;
import mygame.util.HighScores;

public class LevelSelect extends GameStage {
    private final UIFrame FRAME;
    private GameStageEnvironment env;
    //private List<Level> levels;
    HashMap<Integer, Level> levels = new HashMap(new LevelsConfig("levels.json").levels);

    class LevelButtonListener implements UIActionListener {

        String levelFilename;
        int index;

        public LevelButtonListener(String levelFilename, int indx) {
            this.levelFilename = levelFilename;
            index = indx;
        }

        public void onUIAction(UIAction action) {
            System.out.println("Selected level " + levelFilename);
            LevelStage levelStage = (LevelStage) env.getLevelStage();
            levelStage.loadLevel(levelFilename, index);
            System.out.println(levelFilename);
            jumpTo("LevelStage");

        }

    }
    public LevelSelect(GameStageEnvironment env) {
	super(env, LevelSelect.class.getName());

	UIPanel panel = new UIPanel();
	panel.setLayout(new UIGridLayout(levels.size(), 1));

        int index = 0;
        for (Level level : levels.values()) {
            UIButton levelButton = new UIButton(level.getName());
            levelButton.addUIActionListener(new LevelButtonListener(level.getFilename(), index));
            panel.add(levelButton);
            index++;
        }

        this.env = env;
	UIFrame frame = new UIFrame();
	frame.setContents(panel);

	FRAME = frame;
    }

    @Override
    public void start() {
	getGameStageEnvironment().getInputManager().setCursorVisible(true);
	FRAME.resizeAndCenter(new Dimension(240, 200),
		getGameStageEnvironment().getScreenSize());
	FRAME.setVisible(true);

    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {
	FRAME.setVisible(false);
	getGameStageEnvironment().getInputManager().setCursorVisible(false);
    }

    public void changeLevel(int index)
    {
        index++;
        if(index >= levels.size())
        {
            System.out.println("Game FINISHED");
            env.getLevelStage().stop();
            String DATE_FORMAT_NOW = "yyyy-MM-dd";
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);

            HighScores.getInstance().addHighScore(sdf.format(cal.getTime()), ((LevelStage) env.getLevelStage()).gameInstanceManager.getScore(), HighScores.ScoreType.SCORE_OBJECT);
             jumpTo(GameOverStage.class.getName());
        }
        else
        {
            env.getLevelStage().stop();
            
            ((LevelStage) env.getLevelStage()).loadLevel("level2.json", 1);
            jumpTo("LevelStage");

        }

    }
}