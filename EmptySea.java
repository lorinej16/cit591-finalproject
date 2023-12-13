public class EmptySea extends Ship {
    /**
     * The length of this particular ship. An EmptySea has a length of 1.
     */
    private static final int LENGTH = 1;

    /**
     * Constructs a new EmptySea object.
     */
    public EmptySea() {
        super(LENGTH);
    }

    @Override
    public boolean isRealShip() {
        return false;
    }

    /**
     * Returns {@literal false} to indicate that this ship is not sunk.
     *
     * @return {@literal false}
     */
    @Override
    public boolean isSunk() {
        return false;
    }

    @Override
    public String getShipType() {
        return "empty";
    }

    @Override
    public boolean okToPlaceShipAt(int row, int column, boolean horizontal, Ocean ocean) {
        return false;
    }

    @Override
    public void placeShipAt(int row, int column, boolean horizontal, Ocean ocean) {

    }

    @Override
    public boolean shootAt(int row, int column) {
        return false;
    }

    /**
     * Returns a single-character {@code String} representation of this ship. This
     * method returns {@code "-"} to indicate that this ship is not real.
     *
     * @return {@code "-"}
     */
    @Override
    public String toString() {
        return "-";
    }

    @Override
    public String toString(int row, int column) {
        return null;
    }

    @Override
    public String toString(int row, int column, boolean showShip) {
        if (showShip) {
            return "-";
        } else {
            return ".";
        }
    }
}