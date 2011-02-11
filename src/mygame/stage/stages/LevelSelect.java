/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mygame.stage.stages;

import java.awt.Dimension;
import java.util.ArrayList;
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

public class LevelSelect extends GameStage {
    private final UIFrame FRAME;

    private List<Level> levels;

    class LevelButtonListener implements UIActionListener {

        String levelFilename;

        public LevelButtonListener(String levelFilename) {
            this.levelFilename = levelFilename;
        }

        public void onUIAction(UIAction action) {
            System.out.println("Selected level " + levelFilename);
            if(levelFilename.equals("nivel1.lvl"))
                jumpTo("LevelExample");
        }

    }
    public LevelSelect(GameStageEnvironment env) {
	super(env, LevelSelect.class.getName());

        levels = new ArrayList<Level>();
        levels.add(new Level("Nivel 1", "nivel1.lvl"));
        levels.add(new Level("Nivel 2", "nivel2.lvl"));
        levels.add(new Level("Nivel 3", "nivel3.lvl"));

	UIPanel panel = new UIPanel();
	panel.setLayout(new UIGridLayout(levels.size(), 1));

        for (Level level : levels) {
            UIButton levelButton = new UIButton(level.getName());
            levelButton.addUIActionListener(new LevelButtonListener(level.getFilename()));
            panel.add(levelButton);
        }


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
}