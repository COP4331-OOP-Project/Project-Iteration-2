package view.game;

import java.awt.Point;

import game.entities.structures.Structure;
import game.entities.units.Unit;
import game.gameboard.SimpleTile;
import game.gameboard.TerrainEnum;
import game.gameboard.TileVisibilityEnum;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Rotate;
import view.Animation;
import view.GameModelAdapter;
import view.Panel;
import view.ViewEnum;
import view.assets.AssetManager;
import view.game.drawers.ArmyDrawer;
import view.game.drawers.ResourceDrawer;
import view.game.drawers.SelectedDrawer;
import view.game.drawers.StructureDrawer;
import view.game.drawers.TileDrawer;
import view.game.drawers.UnitDrawer;

public class GamePanel extends Panel {
    private static final int TILE_PIXEL_SIZE = 130;
    private Camera camera;
    private long currentPulse;
    private TileDrawer tileDrawer;
    private UnitDrawer unitDrawer;
    private ArmyDrawer armyDrawer;
    private StructureDrawer structureDrawer;
    private SelectedDrawer selectedDrawer;
    private ResourceDrawer resourceDrawer;
    private GraphicsContext g;
	private Point screenDimensions;
	private AssetManager assets;
	private ViewEnum view;
	private boolean resourcesVisible = false;

    public GamePanel(GameModelAdapter gameModelAdapter, AssetManager assets, Camera camera, ViewEnum view) {
    	super(gameModelAdapter, assets, view);
    	this.camera = camera;
    	this.assets = assets;
    	this.view = view;
        screenDimensions = new Point();
        tileDrawer = new TileDrawer(this, assets);
        unitDrawer = new UnitDrawer(this, gameModelAdapter, assets);
        armyDrawer = new ArmyDrawer(this, gameModelAdapter, assets);
        structureDrawer = new StructureDrawer(this, gameModelAdapter, assets);
        selectedDrawer = new SelectedDrawer(this, gameModelAdapter, assets);
        resourceDrawer = new ResourceDrawer(gameModelAdapter, assets, camera);
    }

    public void draw(GraphicsContext g, Point screenDimensions, long currentPulse) {
    	this.currentPulse = currentPulse;
		g.drawImage(assets.getImage("GAME_BACKGROUND"), 0, 0, screenDimensions.x, screenDimensions.y);
    	this.screenDimensions = screenDimensions;
        this.g = g;
        /*
        Point selected = new Point(game.getCenterCoordinates().getX(),
				   game.getCenterCoordinates().getY());
		*/
        camera.adjustZoom(screenDimensions);
        //camera.centerToSelected(selected, screenDimensions);
        drawAllItems();
        //selectedDrawer.drawSelectedItemOutline();
        //tileDrawer.drawMovingTiles();
    }

	private void drawAllItems() {
		SimpleTile[][] currentTiles = getAdapter().getCurrentTiles();
        for (int i = 0; i < currentTiles.length; i++) {
            for (int j = 0; j < currentTiles[i].length; j++) {
                SimpleTile tile = currentTiles[i][j];
                if (tile.getTileType() != TerrainEnum.NON_TILE) {
	                Point p = new Point(i, j);
	
	                //Draw Tiles
	                tileDrawer.drawTile(p, tile.getTileType(), tile.getVisibility());
	                if (tile.getVisibility() != TileVisibilityEnum.INVISIBLE && resourcesVisible) {
	                	resourceDrawer.drawResources(tile, p, g);
	                }
	                if (tile.getUnitCount() > 0) {
	                    for (Unit unit : tile.getUnits()) {
	                    	unitDrawer.drawUnit(p, unit.getEntityId(), unit.getType());
	                    }
	                }
	           
	                if (tile.getStructure() != null) {
	                    Structure structure = tile.getStructure();
	                    structureDrawer.drawStructure(p, structure.getOwnerID(), structure.getType());
	                }
	                /*
	                //Draw Armies
	                if (tile.containsArmy) {
	                    for (Army army : tile.getArmies()) {
	                        armyDrawer.drawArmy(p, army.getOwnerID(),
	                                army.getRotation(), army.getAllUnits().size());
	                    }
	                } else if (tile.containsArmy && tile.getUnits().size() > 0) {
	                    // this is so wrong but might work for demo
	                    armyDrawer.drawArmy(p,
	                            tile.getUnits().get(0).getOwnerID(), 0, tile.getUnits().size()); // lol
	                }
	                
	            */
                }
            }
         }   
    }

    public void drawStaticTileElement(Point p, String image) {
    	Image img = getAssets().getImage(image);
    	g.drawImage(img, camera.offset(p).x, camera.offset(p).y, camera.getScale() * img.getWidth(), 
        		camera.getScale() * img.getHeight());
    }

    public void drawStaticTileElement(Point p, int rotation, String image) {
        Image img = getAssets().getImage(image);
    	Affine currentRotation = g.getTransform();
        rotateOnTile(p, rotation);
        g.drawImage(img, camera.offset(p).x, camera.offset(p).y, camera.getScale() * img.getWidth(), 
        		camera.getScale() * img.getHeight());
        g.setTransform(currentRotation);
    }

    private void rotateOnTile(Point p, int degrees) {
        Rotate rotate = new Rotate(degrees,
                (double) (camera.getTileCenter(p).x),
                (double) (camera.getTileCenter(p).y));
        g.setTransform(rotate.getMxx(), rotate.getMyx(), rotate.getMxy(), rotate.getMyy(), 
        		rotate.getTx(), rotate.getTy());
    }

    public void drawAnimatedTileElement(Point p, Animation a) {
    	a.draw(g, camera.offset(p).x, camera.offset(p).y, camera.getScale(), 
        		camera.getScale(), currentPulse);
     }

    public Camera getCamera() {
        return camera;
    }

    public GraphicsContext getGC() {
        return g;
    }

    public int getTileSize() {
        return TILE_PIXEL_SIZE;
    }

    public void toggleResources() {
    	resourcesVisible = !resourcesVisible;
    }
    
	public void hideGUIElements() {
	}

	public void showGUIElements() {
	}
}