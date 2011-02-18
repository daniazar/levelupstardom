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
import mygame.stage.GameStage;
import mygame.stage.GameStageEnvironment;

public class GameOverStage extends GameStage {
    private final UIFrame FRAME;
    private GameStageEnvironment env;
   public MainMenu mainMenu;

    public GameOverStage(GameStageEnvironment env) {
	super(env, GameOverStage.class.getName());

        UIButton button = new UIButton("Back to main menu");
        button.addUIActionListener(new UIActionListener() {

            public void onUIAction(UIAction action) {
                stop();
                GameOverStage.this.env.reset();



            }
        });

	UIPanel panel = new UIPanel();
	panel.setLayout(new UIGridLayout(2, 1));

        panel.add(new UILabel("Game Over"));
        panel.add(button);
        
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
}