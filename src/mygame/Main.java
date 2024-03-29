package mygame;


import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.font.BitmapFont;
import com.jme3.input.FlyByCamera;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.system.AppSettings;
import java.awt.Dimension;
import java.util.HashMap;
import java.util.Map;
import jme3ui.core.UISystem;
import jme3ui.theme.UIThemeManager;
import jme3ui.theme.orange.OrangeUITheme;
import mygame.media.SoundManager;
import mygame.stage.GameStage;
import mygame.stage.GameStageEnvironment;
import mygame.stage.stages.TimeHighscores;
import mygame.stage.stages.HighscoreMenu;
import mygame.stage.stages.LevelExample;
import mygame.stage.stages.LevelSelect;
import mygame.stage.stages.LevelStage;
import mygame.stage.stages.MainMenu;
import mygame.stage.stages.ObjectHighscores;
import mygame.stage.stages.Settings;
import mygame.util.HighScores.ScoreType;
import mygame.stage.GameStage;
import mygame.stage.stages.GameOverStage;

/**
 * test
 * @author normenhansen
 */
public class Main extends /*SimpleBulletApplication */ SimpleApplication implements GameStageEnvironment {

//    private static class Stage {
//
//        public Stage() {
//        }
//    }

    private BulletAppState bulletAppState;
    private final Map<Object, Object> properties = new HashMap<Object, Object>();
   // private final TriggerSystem triggerSystem = new TriggerSystem();
   // private MouseCamera mcam;
    private GameStage levelStage;
    private MainMenu mainMenu;
    public static GameStage CURRENT;

    public static void main(String[] args) {

        Main main = new Main();
	AppSettings settings = new AppSettings(true);
        settings.setRenderer(AppSettings.LWJGL_OPENGL2);
        settings.setSettingsDialogImage("/Textures/Logo.png");
        main.setSettings(settings);
        main.start();

    }



    @Override
    public void simpleInitApp() {

        reset();
    }

    @Override
    public void simpleUpdate(float tpf) {
        if(CURRENT != null)
        CURRENT.update(tpf);
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

    public PhysicsSpace getPhysicsSpace() {
       return bulletAppState.getPhysicsSpace();
    }


    public FlyByCamera getFlyCamera(){
        return flyCam;
    }

    public BitmapFont getGuiFont(){
        return guiFont;
    }

    public ViewPort getViewPort(){
        return viewPort;
    }


    public GameStage getLevelStage() {
        return levelStage;
    }

    public void GameOver() {
        getLevelStage().stop();
        mainMenu.jumpTo(GameOverStage.class.getName());
    }

    public void jumpToMainMenu() {
        mainMenu.jumpTo(MainMenu.class.getName());
    }

    public void setLevelStage(LevelStage stage) {
       levelStage = stage;
    }

    public void reset() {
                getRootNode().detachAllChildren();
        UIThemeManager.setDefaultTheme(new OrangeUITheme());
        bulletAppState = new BulletAppState();
        bulletAppState.setThreadingType(BulletAppState.ThreadingType.PARALLEL);

        stateManager.attach(bulletAppState);
        bulletAppState.getPhysicsSpace().setAccuracy(0.005f);

        //mcam = new MouseCamera(cam, inputManager);


        //flyCam.setUpVector(Vector3f.UNIT_Y);
//        flyCam.setDragToRotate(true);
//        flyCam.setMoveSpeed(20);
//        flyCam.setEnabled(true);
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
        LevelSelect lselect = new LevelSelect(this);
        levelStage = new LevelStage(this, lselect);
	//GameOver gameOver = new GameOver(this);
        //WinStage trackWin = new WinStage(this);
	//levelStage.addChild(gameOver);
	//levelStage.addChild(trackWin);

        HighscoreMenu highScoreMenu = new HighscoreMenu(this);
        highScoreMenu.addChild(new TimeHighscores(this));
        highScoreMenu.addChild(new ObjectHighscores(this));

        GameOverStage gameOverStage = new GameOverStage(this);

        SoundManager.initSoundManager(this.getAssetManager(), this.getAudioRenderer());

         mainMenu = new MainMenu(this);
	mainMenu.addChild(new Settings(this));
        mainMenu.addChild(lselect);
        mainMenu.addChild(highScoreMenu);
//	mainMenu.addChild(new GameStart(this));
	mainMenu.addChild(levelStage);
        mainMenu.addChild(gameOverStage);
//	mainMenu.addChild(new PlayerHud(this));
        mainMenu.addChild(new LevelExample(this));
	mainMenu.jumpTo(MainMenu.class.getName());
    }


}
