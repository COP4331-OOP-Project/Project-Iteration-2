package view;

import game.DebugMode;
import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import view.assets.AssetManager;
import view.game.*;
import view.screen.*;

import java.awt.*;
import java.util.ArrayList;

public class PanelManager {
	private ViewEnum currentViewMode;
	private MusicManager musicManager;
	private GameModelAdapter gameModelAdapter;
	private CivilizationPanel civilizationPanel;
	private SideBarPanel sideBarPanel;
	private CommandPanel commandPanel;
	private SubCommandPanel subCommandPanel;
	private ControlModePanel controlModePanel;
	private GamePanel gamePanel;
	private MakeDetailsPanel makeDetailsPanel;
	private MiniMapPanel miniMapPanel;
	private UnitOverviewPanel unitOverviewPanel;
	private StructureOverviewPanel structureOverviewPanel;
	private TechOverviewPanel humanTechOverviewPanel;
	private TechOverviewPanel pandaTechOverviewPanel;
	private ToggleBarPanel toggleBarPanel;
	private UnitDetailsPanel unitDetailsPanel;
	private StructureDetailsPanel structureDetailsPanel;
	private MainMenuPanel mainMenuPanel;
	private SplashPanel splashPanel;
	private IntroPanel introPanel;
	private MapMakerPanel mapMakerPanel;
	private SettingsPanel settingsPanel;
	private TileSelector tileSelector;
	private GraphicsContext g;
	private ArrayList<Panel> panels;
	
	public PanelManager(GameModelAdapter gameModelAdapter, AssetManager assets, Group group, GraphicsContext g, Camera camera) {
		if (!DebugMode.DEBUG_MODE) {
			currentViewMode = ViewEnum.SPLASH;
		} else {
			gameModelAdapter.startGame();
			currentViewMode = ViewEnum.MAIN_GAME;
		}
		this.gameModelAdapter = gameModelAdapter;
		this.g = g;
		musicManager = new MusicManager(assets, group);
		panels = new ArrayList<Panel>();
		gamePanel = new GamePanel(gameModelAdapter, assets, camera, ViewEnum.MAIN_GAME);
		panels.add(gamePanel);
		tileSelector = new TileSelector(gameModelAdapter, assets, currentViewMode, group, camera, gamePanel);
		panels.add(tileSelector);
		civilizationPanel = new CivilizationPanel(gameModelAdapter, assets, ViewEnum.MAIN_GAME);
		panels.add(civilizationPanel);
		controlModePanel = new ControlModePanel(gameModelAdapter, group, assets, ViewEnum.MAIN_GAME);
		panels.add(controlModePanel);
		sideBarPanel = new SideBarPanel(gameModelAdapter, assets, ViewEnum.MAIN_GAME, group, this);
		panels.add(sideBarPanel);
		commandPanel = new CommandPanel(gameModelAdapter, group, assets, ViewEnum.MAIN_GAME, tileSelector);
		panels.add(commandPanel);
		subCommandPanel = new SubCommandPanel(gameModelAdapter, group, assets, ViewEnum.MAIN_GAME, tileSelector);
		panels.add(subCommandPanel);
		makeDetailsPanel = new MakeDetailsPanel(gameModelAdapter, assets, ViewEnum.MAIN_GAME);
		panels.add(makeDetailsPanel);
		miniMapPanel = new MiniMapPanel(gameModelAdapter, assets, ViewEnum.MAIN_GAME);
		panels.add(miniMapPanel);
		structureDetailsPanel = new StructureDetailsPanel(gameModelAdapter, assets, ViewEnum.MAIN_GAME);
		panels.add(structureDetailsPanel);
		structureOverviewPanel = new StructureOverviewPanel(gameModelAdapter, assets, ViewEnum.MAIN_GAME, group);
		panels.add(structureOverviewPanel);
		unitDetailsPanel = new UnitDetailsPanel(gameModelAdapter, assets, ViewEnum.MAIN_GAME);
		panels.add(unitDetailsPanel);
		unitOverviewPanel = new UnitOverviewPanel(gameModelAdapter, assets, ViewEnum.MAIN_GAME, group);
		panels.add(unitOverviewPanel);
		humanTechOverviewPanel = new TechOverviewPanel(gameModelAdapter, assets, ViewEnum.MAIN_GAME, group, 0);
		panels.add(humanTechOverviewPanel);
		pandaTechOverviewPanel = new TechOverviewPanel(gameModelAdapter, assets, ViewEnum.MAIN_GAME, group, 1);
		panels.add(pandaTechOverviewPanel);
		toggleBarPanel = new ToggleBarPanel(gameModelAdapter, group, this, assets, ViewEnum.MAIN_GAME);
		panels.add(toggleBarPanel);
		splashPanel = new SplashPanel(gameModelAdapter, group, this, assets, ViewEnum.SPLASH);
		panels.add(splashPanel);
		introPanel = new IntroPanel(gameModelAdapter, group, this, assets, ViewEnum.INTRO);
		panels.add(introPanel);
		mainMenuPanel = new MainMenuPanel(gameModelAdapter, group, this, assets, ViewEnum.MAIN_MENU);
		panels.add(mainMenuPanel);
		mapMakerPanel = new MapMakerPanel(gameModelAdapter, group, this, assets, ViewEnum.MAP_MAKER);
		panels.add(mapMakerPanel);
		settingsPanel = new SettingsPanel(gameModelAdapter, group, this, assets, ViewEnum.SETTINGS);
		panels.add(settingsPanel);
	}
	
	public void drawPanels(Point screenDimensions, long currentPulse) {
		musicManager.updateMusic(currentViewMode);
    	commandPanel.setIsVisible(gameModelAdapter.getSelectedEntity() != null);
    	subCommandPanel.setIsVisible(gameModelAdapter.getSelectedCommand() != null);
		for (Panel panel : panels) {
			panel.drawPanel(g, screenDimensions, currentViewMode, currentPulse);
		}
	}

	public void setMode(ViewEnum currentViewMode) {
		this.currentViewMode = currentViewMode;
	}

	public void tileClicked(double x, double y) {
		if (currentViewMode == ViewEnum.MAP_MAKER) {
			mapMakerPanel.tileClicked(new Point((int)x, (int)y));
		}
	}

	public void toggleUnitOverview() {
		if (currentViewMode == ViewEnum.MAIN_GAME) {
	        structureOverviewPanel.hideIfVisible();
	        humanTechOverviewPanel.hideIfVisible();
	        pandaTechOverviewPanel.hideIfVisible();
	        unitOverviewPanel.toggle();
		}
	}

	public void toggleStructureOverview() {
		if (currentViewMode == ViewEnum.MAIN_GAME) {
	    	unitOverviewPanel.hideIfVisible();
	    	humanTechOverviewPanel.hideIfVisible();
	    	pandaTechOverviewPanel.hideIfVisible();
	        structureOverviewPanel.toggle();
		}
	}
	
	public void toggleTechOverview() {
		if (currentViewMode == ViewEnum.MAIN_GAME) {
	    	unitOverviewPanel.hideIfVisible();
	    	structureOverviewPanel.hideIfVisible();
	        if (gameModelAdapter.getPlayerId() == 0) {
	        	humanTechOverviewPanel.toggle();
	        } else {
	        	pandaTechOverviewPanel.toggle();
	        }
	    	
		}
	}

	public void toggleToggler() {
		toggleBarPanel.toggle();
	}

	public void toggleMiniMap() {
		miniMapPanel.toggle();
	}

	public void toggleResources() {
		gamePanel.toggleResources();
	}
	
	public void toggleUnits() {
		gamePanel.toggleUnits();
	}
	
	public void toggleStructures() {
		gamePanel.toggleStructures();
	}

	public void centerOnSelected() {
		gamePanel.centerOnSelected();
	}

	public void endTurn() {
		gamePanel.endTurn();
	}
}
