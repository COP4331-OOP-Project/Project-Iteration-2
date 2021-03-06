package view.game;

import java.awt.Point;
import java.util.ArrayList;

import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import view.GameModelAdapter;
import view.Panel;
import view.ViewEnum;
import view.assets.AssetManager;

public class TileSelector extends Panel{
	StackPane tileSelector = new StackPane();
	private boolean waitingForTile = false;
	private Point selectedTile;
	private Group root;
	private GamePanel gamePanel;
	private Point clickedPixel = new Point();
	private Point clickedTile = new Point();
	private ArrayList<Point> points = new ArrayList<>();
	
	public TileSelector(GameModelAdapter gameModelAdapter, AssetManager assets,
			ViewEnum viewEnum, Group root, Camera camera, GamePanel gamePanel) {
		super(gameModelAdapter, assets, viewEnum);
		this.root = root;
		this.gamePanel = gamePanel;
		tileSelector.setOnMouseClicked(event -> {
			clickedPixel.x = (int)event.getX();
			clickedPixel.y = (int)event.getY();
			clickedTile = camera.getTileLocation(clickedPixel);
			if (waitingForTile) {
				for (Point point : points) {
					if (clickedTile.equals(point)) {
						selectedTile = new Point(clickedTile.x, clickedTile.y);
						waitingForTile = false;
					}
				}
			}
		});
	}
	
	public void startWaitingForTile(ArrayList<Point> points) {
		this.points = points;
		waitingForTile = true;
	}
	
	public Point getSelectedTile() {
		return selectedTile;
	}
	
	public boolean getWaitingForTile() {
		return waitingForTile;
	}
	
	private void drawTiles() {
		for (Point point : points) {
			gamePanel.drawStaticTileElement(point, "MOVE_SELECTED");
		}
	} 

	public void draw(GraphicsContext g, Point screenDimensions, long currentPulse) {
		if (waitingForTile) {
			drawTiles();
		}
	}
	
	public void hideGUIElements() {
		root.getChildren().remove(tileSelector);
	}

	public void showGUIElements() {
		root.getChildren().add(tileSelector);
	}
	
}
