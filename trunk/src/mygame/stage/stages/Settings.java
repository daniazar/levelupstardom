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
import mygame.util.Preferences;

public class Settings extends GameStage {
    private final UIFrame FRAME;

    public Settings(GameStageEnvironment env) {
	super(env, Settings.class.getName());

        final UIButton musicUpButton = new UIButton("+");
        final UIButton musicDownButton = new UIButton("-");

        final UIButton soundFXUpButton = new UIButton("+");
        final UIButton soundFXDownButton = new UIButton("-");

	final UIButton backButton = new UIButton("Back");
        final UIButton saveButton = new UIButton("Save");

        final UILabel SoundFXLabel = new UILabel("Sound FX");
        final UILabel SoundFXStatusLabel = new UILabel(Preferences.getInstance().getSoundFXLevelString());

        final UILabel MusicLabel = new UILabel("Music");
        final UILabel MusicStatusLabel = new UILabel(Preferences.getInstance().getMusicLevelString());
        
	UIActionListener listener = new UIActionListener() {

	    public void onUIAction(UIAction e) {
		if(e.getSource() == musicUpButton) {
                    musicUp();
                    MusicStatusLabel.setText(Preferences.getInstance().getMusicLevelString());
		} else if(e.getSource() == musicDownButton) {
		    musicDown();
                    MusicStatusLabel.setText(Preferences.getInstance().getMusicLevelString());
                } else if(e.getSource() == soundFXUpButton) {
		    soundFXUp();
                    SoundFXStatusLabel.setText(Preferences.getInstance().getSoundFXLevelString());
                } else if(e.getSource() == soundFXDownButton) {
		    soundFXDown();
                    SoundFXStatusLabel.setText(Preferences.getInstance().getSoundFXLevelString());
                } else if(e.getSource() == backButton) {
                    jumpTo(MainMenu.class.getName());
		} else if(e.getSource() == saveButton) {
                    saveSettings();
                }
	    }
	};

	musicUpButton.addUIActionListener(listener);
        musicDownButton.addUIActionListener(listener);

	soundFXUpButton.addUIActionListener(listener);
        soundFXDownButton.addUIActionListener(listener);

        saveButton.addUIActionListener(listener);
	backButton.addUIActionListener(listener);

	UIPanel panel = new UIPanel();
	panel.setLayout(new UIGridLayout(3, 4));

        panel.add(SoundFXLabel);
        panel.add(MusicLabel);
        panel.add(new UILabel(""));

        panel.add(SoundFXStatusLabel);
        panel.add(MusicStatusLabel);
        panel.add(backButton);

        panel.add(soundFXDownButton);
        panel.add(musicDownButton);
        panel.add(saveButton);

        panel.add(soundFXUpButton);
        panel.add(musicUpButton);

	UIFrame frame = new UIFrame();
	frame.setContents(panel);
	FRAME = frame;
    }

    private void saveSettings() {
        Preferences.getInstance().saveInstance();
        jumpTo(MainMenu.class.getName());
    }

    private void musicUp() {
        Preferences.getInstance().upMusic();

    }

    private void musicDown() {
        Preferences.getInstance().lowerMusic();
    }

    private void soundFXUp() {
        Preferences.getInstance().upSoundFX();
    }

    private void soundFXDown() {
        Preferences.getInstance().lowerSoundFX();
    }

    @Override
    public void start() {
	getGameStageEnvironment().getInputManager().setCursorVisible(true);
	FRAME.resizeAndCenter(new Dimension(400, 200),
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