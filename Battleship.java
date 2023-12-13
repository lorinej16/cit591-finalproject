public class Battleship extends Ship {
    /**
     * Constructs a new Battleship object.
     */
    public Battleship() {
        super(4);
    }

    /**
     * @return the type of this ship.
     */
    @Override
    public String getShipType() {
        return "battleship";
    }


    @Override
    public String toString(int row, int column) {
        return null;
    }

    @Override
    public String toString(int row, int column, boolean showShip) {
        if(showShip) {
            return "b";
        } else {
            return ".";
        }
    }
}
