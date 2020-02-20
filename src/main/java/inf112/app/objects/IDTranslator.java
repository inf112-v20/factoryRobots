package inf112.app.objects;

/*
Issue:
Make Enum that maps objects name to the number the object has in the tileset /assets/tiles.png
 */

/**
 * IDTranslator checks id and matches it with the right ElementEnum
 */
public class IDTranslator{
    public ElemEnum findNameFromId(int id){
        if (id == 5) {
            return ElemEnum.BOARD;
        } else if (id == 6) {
            return ElemEnum.HOLE;
        } else if (id == 7 || id == 15) {
            return ElemEnum.REPAIRSTATION;
        }  else if ((id >= 9 && id <= 12)) {
            return ElemEnum.PUSHPANEL;
        }  else if (id == 8 || id == 16 || id == 23 || id == 24 || (id >= 29 && id >= 32) ||
                id == 37 || id == 38 || id == 45 || id == 46 || id == 87 || (id >= 93 && id <= 95)) {
            return ElemEnum.WALL;
        } else if (id == 13 || id == 14 || id >= 17 || id <= 22 || (id >= 25 && id <= 28) || (id >= 73 && id <= 78) ||
                (id >= 81 && id <= 86)) {
            return ElemEnum.BELTX2;
        } else if ((id >= 33 && id <= 36) || (id >= 41 && id <= 44) || (id >= 49 && id <= 52) ||
                (id >= 57 && id <= 62) || (id >= 65 && id <= 70)) {
            return ElemEnum.BELTX1;
        } else if (id == 39 || id == 40 || id == 47 || (id >= 101 && id <= 103)) {
            return ElemEnum.LASER;
        } else if (id == 53 || id == 54) {
            return ElemEnum.COG;
        } else if (id == 55 || id == 63 || id == 71 || id == 79) {
            return ElemEnum.FLAG;
        } else if (id == 89 || id == 90) {
            return ElemEnum.FIREPIT;
        } else if (id == 91 || id == 92 || (id >= 105 && id <= 110) || (id >= 113 && id <= 118)) {
            return ElemEnum.BORDERWALL;
        } else if ((id >= 97 && id <= 100)) {
            return ElemEnum.FLAGPLATFORM;
        } else if ((id >= 121 && id <= 124) || (id >= 126 && id <= 130)) {
            return ElemEnum.STARTINGPLATFORM;
        }
        throw new IllegalArgumentException("ID range not valid");
    }

    /**
     * Here we label the tiles found in /assets/tiles.png to names
     */
    public enum ElemEnum {
        BOARD,
        HOLE,
        REPAIRSTATION,
        PUSHPANEL,
        WALL,
        BELTX2,
        BELTX1,
        LASER,
        COG,
        FLAG,
        FIREPIT,
        BORDERWALL,
        FLAGPLATFORM,
        STARTINGPLATFORM;
    }
}