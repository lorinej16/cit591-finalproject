public class Submarine extends Ship {
    /**
     * Constructs a new Submarine object.
     */
    public Submarine() {
        super(1);
    }

    /**
     * @return the type of this ship.
     */
    @Override
    public String getShipType() {
        return "submarine";
    }

    @Override
    public String toString(int row, int column) {
        return null;
    }

    @Override
    public String toString(int row, int column, boolean showShip) {
        if(showShip) {
            return "s";
        } else {
            return ".";
        }
    }
}