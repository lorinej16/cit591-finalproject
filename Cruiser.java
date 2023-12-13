public class Cruiser extends Ship {
    /**
     * Constructs a new Cruiser object.
     */
    public Cruiser() {
        super(3);
    }

    /**
     * @return the type of this ship.
     */
    @Override
    public String getShipType() {
        return "cruiser";
    }

    @Override
    public String toString(int row, int column) {
        return null;
    }

    @Override
    public String toString(int row, int column, boolean showShip) {
        if(showShip) {
            return "c";
        } else {
            return ".";
        }
    }

}