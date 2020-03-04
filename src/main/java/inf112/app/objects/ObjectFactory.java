package inf112.app.objects;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import inf112.app.objects.IDTranslator.ElemEnum;
import inf112.app.objects.Direction.Rotation;

/**
 * Class for generating {@link IBoardElement} for
 * {@link inf112.app.map.MapCellList} to keep track of the
 * game logic of the elements
 */
public class ObjectFactory {
    private IDTranslator translator;

    public ObjectFactory(){
        translator = new IDTranslator();
    }

    /**
     * Method for generating objects from tiles in the map
     * @param tile The tile held in the Cell in the layer
     * @return An object of the correct class with parameters based
     * on the tiles custom properties
     */
    public IBoardElement generateObject(TiledMapTile tile, int x, int y){
        int id = tile.getId();
        ElemEnum name = translator.findNameFromId(id);
        MapProperties properties = tile.getProperties();
        switch(name){
            case BELTX1:
                return createConveyor(properties,1);
            case BELTX2:
                return createConveyor(properties,2);
            case COG:
                return createCog(properties);
            case WALL:
                return createWall(properties, x, y);
            case LASER: return null;
            case REPAIRSTATION:
                return createRepairStation(properties);
            case FLAG:
                return createFlag(properties);
            default: throw new IllegalArgumentException("Enum obtained from translator could not be placed");
        }
    }

    private Flag createFlag(MapProperties props) {
        int num = (int) props.get("flagNum");
        return new Flag(num);
    }

    private RepairStation createRepairStation(MapProperties props) {
        int speed = (int) props.get("speed");
        return new RepairStation(speed);
    }

    private Cog createCog(MapProperties props) {
        String direction = (String) props.get("direction");
        Rotation rot;
        if(direction.equals("left")){
            rot = Rotation.LEFT;
        } else if (direction.equals("right")){
            rot = Rotation.RIGHT;
        } else {
            throw new IllegalArgumentException("Custom direction property is invalid");
        }
        return new Cog(rot);
    }

    private Wall createWall(MapProperties props, int x, int y){
        int face0 = (int) props.get("block0");
        int face1 = (int) props.get("block1");
        boolean laser = (boolean) props.get("laser");
        boolean doubleLaser = false;
        if(laser){
            doubleLaser = (boolean) props.get("doubleLaser");
        }
        return new Wall(face0,face1,laser,doubleLaser,x ,y);
    }

    private Conveyor createConveyor(MapProperties props, int speed){
        int entry0 = (int) props.get("ent0");
        int entry1 = (int) props.get("ent1");
        int exit = (int) props.get("exit");
        return new Conveyor(entry0, entry1, exit, speed);
    }
}
