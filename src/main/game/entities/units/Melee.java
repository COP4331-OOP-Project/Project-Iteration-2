package game.entities.units;

import game.gameboard.Location;
import game.entities.EntityId;
/**
 * Created by David on 2/22/2017.
 */
public class Melee extends Unit {

    public Melee(UnitStats stats, Location location, EntityId entityId){
        super(stats, location, entityId);
    }
}
