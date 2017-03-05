package game.gameboard;

import game.entities.EntityId;
import game.entities.EntitySubtypeEnum;
import game.entities.EntityTypeEnum;
import game.entities.stats.StructureStats;
import game.entities.stats.UnitStats;
import game.entities.structures.Capitol;
import game.entities.structures.Farm;
import game.entities.structures.StructureType;
import game.entities.structures.exceptions.StructureNotFoundException;
import game.entities.units.Colonist;
import game.entities.units.Melee;
import game.entities.units.Ranged;
import game.entities.units.UnitType;
import game.entities.units.exceptions.UnitNotFoundException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TileTest {
    int playerId;

    private Tile tileWater;
    private Tile tileSand;
    private Tile tileGrass;
    private Tile tileMountain;

    private Location tileWaterLoc;
    private Location tileSandLoc;
    private Location tileGrassLoc;
    private Location tileMountainLoc;

    private UnitStats meleeStats;
    private UnitStats rangedStats;
    private UnitStats colonistStats;
    private StructureStats capitolStat;
    private StructureStats farmStat;

    private Melee melee1;
    private EntityId melee1Id;
    private Melee melee2;
    private EntityId melee2Id;
    private Ranged range1;
    private EntityId range1Id;
    private Ranged range2;
    private EntityId range2Id;
    private Colonist colonist1;
    private EntityId colonistId;

    private Capitol capitol;
    private EntityId capitolId;
    private Farm farm;
    private EntityId farmId;

    private Location unitLocation;
    private Location capitolLocation;
    private Location farmLocation;

    @Before
    public void setUp() {
        try {
            playerId = 1;
            meleeStats = new UnitStats(UnitType.MELEE);
            rangedStats = new UnitStats(UnitType.RANGED);
            colonistStats = new UnitStats((UnitType.COLONIST));
            capitolStat = new StructureStats(StructureType.CAPITOL);
            farmStat = new StructureStats(StructureType.FARM);


            melee1Id = new EntityId(this.playerId, EntityTypeEnum.UNIT, EntitySubtypeEnum.MELEE, 1);
            melee2Id = new EntityId(this.playerId, EntityTypeEnum.UNIT, EntitySubtypeEnum.MELEE, 2);
            range1Id = new EntityId(this.playerId, EntityTypeEnum.UNIT, EntitySubtypeEnum.RANGE, 1);
            range2Id = new EntityId(this.playerId, EntityTypeEnum.UNIT, EntitySubtypeEnum.RANGE, 2);
            colonistId = new EntityId(this.playerId, EntityTypeEnum.UNIT, EntitySubtypeEnum.COLONIST, 1);

            capitolId = new EntityId(this.playerId, EntityTypeEnum.STRUCTURE, EntitySubtypeEnum.CAPITOL, 1);
            farmId = new EntityId(this.playerId, EntityTypeEnum.STRUCTURE, EntitySubtypeEnum.FARM, 2);

            unitLocation = new Location(3, 3);
            capitolLocation = new Location(12, 16);
            farmLocation = new Location(11, 16);

            melee1 = new Melee(meleeStats, unitLocation, melee1Id);
            melee2 = new Melee(meleeStats, unitLocation, melee2Id);
            range1 = new Ranged(rangedStats, unitLocation, range1Id);
            range2 = new Ranged(rangedStats, unitLocation, range2Id);
            colonist1 = new Colonist(colonistStats, unitLocation, colonistId);

            capitol = new Capitol(capitolStat, capitolLocation, capitolId);
            farm = new Farm(farmStat, farmLocation, farmId);

            tileWaterLoc = new Location(20, 20);
            tileSandLoc = new Location(20, 21);
            tileGrassLoc = new Location(20, 22);
            tileMountainLoc = new Location(19, 20);

            tileWater = new Tile(TerrainEnum.WATER, tileWaterLoc);
            tileSand = new Tile(TerrainEnum.SAND, tileSandLoc);
            tileGrass = new Tile(TerrainEnum.GRASS, tileGrassLoc);
            tileMountain = new Tile(TerrainEnum.MOUNTAIN, tileMountainLoc);
        } catch (UnitNotFoundException e) {
            Assert.fail();
        } catch (StructureNotFoundException e) {
            Assert.fail();
        }
    }

    @Test //Test ownership of the tile
    public void testOwnerShipOfTile(){
        //If no unit, return -1
        Assert.assertEquals(tileGrass.getOwner(), -1);

        tileGrass.addUnit(melee1);
        tileGrass.addUnit(melee2);

        Assert.assertEquals(tileGrass.getOwner(), playerId);
    }

    @Test //Test the enemy blcoker
    public void invalidAddUnitOnTile(){
        EntityId enemyEntityMeleeId = new EntityId(2, EntityTypeEnum.UNIT, EntitySubtypeEnum.MELEE, 1);
        Melee melee3 = new Melee(meleeStats, unitLocation, enemyEntityMeleeId);

        tileGrass.addUnit(melee3);
        tileGrass.addUnit(melee1);

        Assert.assertEquals(this.tileGrass.getUnits().size(), 1);
    }

    @Test //Test on impassible tiles
    public void testInpassableTile(){
        Assert.assertEquals(this.tileWater.isImpassable(), true);
    }

    @Test //Check contains Structure()
    public void testContainsStructure(){
        tileGrass.addStructure(this.capitol);
        Assert.assertEquals(this.tileGrass.containsStructure(), true);
        Assert.assertEquals(this.tileGrass.getStructure(), capitol);
    }


}