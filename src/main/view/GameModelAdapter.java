package view;

import game.GameModel;
import game.gameboard.SimpleTile;
import game.mode.ControlMode;
import game.mode.Mode;
import game.mode.Submode;

public class GameModelAdapter {
	GameModel gameModel;
	ControlMode controlMode;
	
	public GameModelAdapter(GameModel gameModel, ControlMode controlMode) {
		this.gameModel = gameModel;
		this.controlMode = controlMode;
	}

	public void startGame() {
		gameModel.initializeGame();
	}
	
	public SimpleTile[][] getCurrentTiles() {
		return gameModel.getCurrentPlayer().getSimpleTiles();
	}

	public Mode getCurrentMode() {
		return controlMode.getGameMode();
	}

	public Submode getCurrentSubmode() {
		return controlMode.getGameSubmode();
	}

	public void setMode(Mode mode) {
		controlMode.setMode(mode);
	}
	
	public void setSubmode(Submode submode) {
		controlMode.setSubmode(submode);
	}
	
}
