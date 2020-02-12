package inf112.app.objects;

public enum Direction {
    EAST, WEST, NORTH, SOUTH;
    public static Direction fromString(String a){
        switch(a){
            case "East":
                return EAST;
            case "West":
                return WEST;
            case "North":
                return NORTH;
            case "South":
                return SOUTH;
        }
        return null;
    }
}
