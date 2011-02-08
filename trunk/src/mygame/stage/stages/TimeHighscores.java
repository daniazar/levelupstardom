/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mygame.stage.stages;

import java.awt.Dimension;
import jme3ui.event.action.UIAction;
import jme3ui.event.action.UIActionListener;
import jme3ui.layouts.UIGridLayout;
import jme3ui.widgets.UIButton;
import jme3ui.widgets.UIFrame;
import jme3ui.widgets.UILabel;
import jme3ui.widgets.UIPanel;
import mygame.model.Score;
import mygame.stage.GameStage;
import mygame.stage.GameStageEnvironment;
import mygame.util.HighScores;
import mygame.util.HighScores.ScoreType;

/**
 *
 * @author matiaspan
 */
public class TimeHighscores extends GameStage {

    private final UIFrame FRAME;
    UIPanel panel;

    public TimeHighscores(GameStageEnvironment env) {
	super(env, TimeHighscores.class.getName());

	UIFrame frame = new UIFrame();
	FRAME = frame;
    }

    @Override
    public void start() {
        final UIButton backButton = new UIButton("Back");

	UIActionListener listener = new UIActionListener() {

	    public void onUIAction(UIAction e) {
		if(e.getSource() == backButton) {
                    jumpTo(HighscoreMenu.class.getName());
		}
	    }
	};

	backButton.addUIActionListener(listener);

	panel = new UIPanel();
	panel.setLayout(new UIGridLayout(11, 1));

        HighScores highScores = HighScores.getInstance();

        int i = 0;

        for (Score score : highScores.getHighScore(ScoreType.SCORE_TIME)) {
            if (i < 10) {
                panel.add(new UILabel(score.name + " " + score.score));
            } else {
                break;
            }

            i++;
        }

        panel.add(backButton);
        FRAME.setContents(panel);

        getGameStageEnvironment().getInputManager().setCursorVisible(true);
	FRAME.resizeAndCenter(new Dimension(240, 400),
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

}
