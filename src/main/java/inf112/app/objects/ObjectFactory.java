package inf112.app.objects;

import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;

public class ObjectFactory {
    //private IDTranslator translator;
    public ObjectFactory(){
        //translator = new IDTranslator();
    }

    public static IBoardElement generateObject(int id, StaticTiledMapTile tile){
        ElemEnum name = translator.findNameFromId(id);
        switch(name){
            case ElemEnum.BELTX1: return null;
            case ElemEnum.BELTX2: return null;
            case ElemEnum.COG: return null;
            case ElemEnum.WALL: return null;
            case ElemEnum.LASER: return null;
            case ElemEnum.REPAIRSTATION: return null;
            default: throw new IllegalArgumentException("Something is wrong");
        }
        return null;
    }
}
