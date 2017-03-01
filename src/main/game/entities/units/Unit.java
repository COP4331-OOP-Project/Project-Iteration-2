package game.entities.units;

import game.entities.*;
import game.entities.Entity;
import game.entities.stats.UnitStats;
import game.gameboard.Location;

// TODO: Fix damage taking to account for defense

public class Unit extends Entity implements iAttacker, iDefender, iMoveable {
    protected UnitStats stats;
    protected int orientation;

    public Unit(UnitStats stats, Location location, EntityId entityId){
        super(location, entityId);

        this.stats = stats;
        this.health = stats.getHealth();
        this.healthPercent = new Percentage();
        this.orientation = 1;
        standby();
    }

    /* Accessors */
    public UnitStats getStats() { return stats; }
    public int getOrientation() { return orientation; }

    /* Mutators */
    public void setStats(UnitStats stats) { this.stats = stats; }
    public void setOrientation(int orientation) { this.orientation = orientation; }

    /* iAttacker */
    public double getDamage(){ return (double)stats.getOffPow(); }
    public int getRange(){ return stats.getRange(); }

    /* iMoveable */
    public int getMoveDistance(){ return stats.getSpeed(); }
}
