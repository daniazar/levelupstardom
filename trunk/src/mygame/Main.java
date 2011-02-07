package mygame;


import com.jme3.app.SimpleBulletApplication;
import com.jme3.renderer.RenderManager;
import java.awt.Dimension;
import java.util.HashMap;
import java.util.Map;
import jme3ui.core.UISystem;
import jme3ui.theme.UIThemeManager;
import jme3ui.theme.orange.OrangeUITheme;
import mygame.stage.GameStageEnvironment;
import mygame.stage.stages.TimeHighscores;
import mygame.stage.stages.HighscoreMenu;
import mygame.stage.stages.LevelStage;
import mygame.stage.stages.MainMenu;
import mygame.stage.stages.ObjectHighscores;
import mygame.stage.stages.Settings;
import mygame.util.HighScores.ScoreType;

/**
 * test
 * @author normenhansen
 */
public class Main extends SimpleBulletApplication implements GameStageEnvironment {

    private final Map<Object, Object> properties = new HashMap<Object, Object>();
   // private final TriggerSystem triggerSystem = new TriggerSystem();
   // private MouseCamera mcam;

    public static void main(String[] args) {
        Main main = new Main();
	main.start();
    }

    @Override
    public void simpleInitApp() {
        UIThemeManager.setDefaultTheme(new OrangeUITheme());

        //mcam = new MouseCamera(cam, inputManager);

        flyCam.setEnabled(false);
	statsView.removeFromParent();

        UISystem.initialize(this);

	try {
//	    properties.put(PropertyKey.TRACK_INFO_SEQUENCE,
//		    new TrackInfoSequence(getClass().
//		    getResource("/rollmadness/formats/tracklist.xml")));
	} catch(Exception ex) {
//	    Logger.getLogger(getClass().getName()).log(Level.SEVERE, "", ex);
	    return;
	}

	//ColladaLoader.addTextureBase("rollmadness/textures/");
	//assetManager.registerLocator(getClass().getResource("/rollmadness/textures").toString(), UrlLocator.class.getName());
	//assetManager.registerLoader(ColladaLoader.class.getName(), "dae");

        //rootNode.addControl(triggerSystem);

        LevelStage levelStage = new LevelStage(this);
	//GameOver gameOver = new GameOver(this);
        //WinStage trackWin = new WinStage(this);
	//levelStage.addChild(gameOver);
	//levelStage.addChild(trackWin);

        HighscoreMenu highScoreMenu = new HighscoreMenu(this);
        highScoreMenu.addChild(new TimeHighscores(this));
        highScoreMenu.addChild(new ObjectHighscores(this));

        MainMenu mainMenu = new MainMenu(this);
	mainMenu.addChild(new Settings(this));
        mainMenu.addChild(highScoreMenu);
//	mainMenu.addChild(new GameStart(this));
	mainMenu.addChild(levelStage);
//	mainMenu.addChild(new PlayerHud(this));
	mainMenu.jumpTo(MainMenu.class.getName());
    }

    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }

    public Dimension getScreenSize() {
	return new Dimension(settings.getWidth(), settings.getHeight());
    }

    public void setDefaultInputState(boolean enabled) {
	//mcam.setEnabled(enabled);
	//flyCam.setEnabled(enabled);
    }

    public <T> T getGlobalProperty(Object key, Class<T> type) {
	return type.cast(properties.get(key));
    }

    public void setGlobalProperty(Object key, Object value) {
	properties.put(key, value);
    }
}
