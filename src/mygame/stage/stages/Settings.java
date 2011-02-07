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
import mygame.util.Preferences;

public class Settings extends GameStage {
    private final UIFrame FRAME;

    public Settings(GameStageEnvironment env) {
	super(env, Settings.class.getName());

	final UIButton musicButton = new UIButton();
	final UIButton soundFXButton = new UIButton();
	final UIButton backButton = new UIButton("Back");

        if(Preferences.getInstance().isMusicEnabled()) {
            musicButton.setText("Music ON");
        } else {
            musicButton.setText("Music OFF");
        }

        if(Preferences.getInstance().isSoundFXEnabled()) {
            soundFXButton.setText("Sound FX ON");
        } else {
            soundFXButton.setText("Sound FX OFF");
        }
        
	UIActionListener listener = new UIActionListener() {

	    public void onUIAction(UIAction e) {
		if(e.getSource() == musicButton) {
                    toggleMusic(musicButton);
		} else if(e.getSource() == soundFXButton) {
		    toggleSoundFX(soundFXButton);
                } else if(e.getSource() == backButton) {
                    jumpTo(MainMenu.class.getName());
		}
	    }
	};

	musicButton.addUIActionListener(listener);
	soundFXButton.addUIActionListener(listener);
	backButton.addUIActionListener(listener);

	UIPanel panel = new UIPanel();
	panel.setLayout(new UIGridLayout(3, 1));
	panel.add(musicButton);
	panel.add(soundFXButton);
        panel.add(backButton);

	UIFrame frame = new UIFrame();
	frame.setContents(panel);
	FRAME = frame;
    }

    private void toggleMusic(UIButton button) {
        if (button.getText().equals("Music ON")) {
            button.setText("Music OFF");
        } else {
            button.setText("Music ON");
        }

        Preferences.getInstance().toggleMusic();
    }

    private void toggleSoundFX(UIButton button) {
        if (button.getText().equals("Sound FX ON")) {
            button.setText("Sound FX OFF");
        } else {
            button.setText("Sound FX ON");
        }
        Preferences.getInstance().toggleSoundFX();
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