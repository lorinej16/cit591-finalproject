public class Destroyer extends Ship {
    /**
     * Constructs a new Destroyer object.
     */
    public Destroyer() {
        super(2);
    }

    /**
     * @return the type of this ship.
     */
    @Override
    public String getShipType() {
        return "destroyer";
    }

    @Override
    public String toString(int row, int column, boolean showShip) {
        if(showShip) {
            return "d";
        } else {
            return ".";
        }
    }
}