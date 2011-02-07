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
import jme3ui.widgets.UIPanel;
import mygame.stage.GameStage;
import mygame.stage.GameStageEnvironment;

/**
 *
 * @author matiaspan
 */
public class HighscoreMenu extends GameStage {

    private final UIFrame FRAME;

    public HighscoreMenu(final GameStageEnvironment env) {
	super(env, HighscoreMenu.class.getName());

	final UIButton timeAttackButton = new UIButton("Time Attack");
	final UIButton objectPickUpButton = new UIButton("Object Pick Up");
	final UIButton backButton = new UIButton("Back");

	UIActionListener listener = new UIActionListener() {

	    public void onUIAction(UIAction e) {                
		if(e.getSource() == timeAttackButton) {
                    jumpTo(TimeHighscores.class.getName());
		} else if(e.getSource() == objectPickUpButton) {
		    jumpTo(ObjectHighscores.class.getName());
		} else if(e.getSource() == backButton) {
                    jumpTo(MainMenu.class.getName());
		}
	    }
	};
        
	timeAttackButton.addUIActionListener(listener);
	objectPickUpButton.addUIActionListener(listener);
	backButton.addUIActionListener(listener);

	UIPanel panel = new UIPanel();
	panel.setLayout(new UIGridLayout(3, 1));
	panel.add(timeAttackButton);
	panel.add(objectPickUpButton);
        panel.add(backButton);

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
