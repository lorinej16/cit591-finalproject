import java.util.Arrays;

abstract class Ship {
    /**
     * The row (0 to 9) which contains the bow (front) of the ship.
     */
    private int bowRow;

    /**
     * The column (0 to 9) which contains the bow (front) of the ship.
     */
    private int bowColumn;

    /**
     * The number of squares occupied by the ship. An "empty sea" location has length
     * 1.
     */
    protected int length;

    /**
     * {@literal true} if the ship occupies a single row, {@literal false} otherwise.
     */
    private boolean horizontal;

    /**
     * An array of booleans telling whether that part of the ship has been hit.
     * {@literal false} if the corresponding location either does not contain a part
     * of the ship, or if it has not been hit. Once the ship has been sunk, all
     * values in the array will be {@literal true}.
     */
    protected boolean[] hit;

    public Ship(int length) {
        this.length = length;
        this.hit = new boolean[length];

        Arrays.fill(this.hit, false);

        this.bowRow = -1;
        this.bowColumn = -1;
        this.horizontal = false;
    }

    /**
     * @return the length of this particular ship. An "empty sea" location has
     * length 1.
     */
    public int getLength() {
        return this.length;
    }

    /**
     * @return the row (0 to 9) which contains the bow (front) of the ship.
     */
    public int getBowRow() {
        return this.bowRow;
    }

    /**
     * @return the column (0 to 9) which contains the bow (front) of the ship.
     */
    public int getBowColumn() {
        return this.bowColumn;
    }

    /**
     * @return {@literal true} if the ship occupies a single row, {@literal false}
     * otherwise.
     */
    public boolean isHorizontal() {
        return this.horizontal;
    }

    /**
     * @param row the row (0 to 9) which contains the bow (front) of the ship.
     */
    public void setBowRow(int row) {
        this.bowRow = row;
    }

    /**
     * @param column the column (0 to 9) which contains the bow (front) of the ship.
     */
    public void setBowColumn(int column) {
        this.bowColumn = column;
    }

    /**
     * @param horizontal {@literal true} if the ship occupies a single row,
     *                   {@literal false} otherwise.
     */
    public void setHorizontal(boolean horizontal) {
        this.horizontal = horizontal;
    }

    /**
     * @return the type of this ship.
     */
    abstract public String getShipType();

    /**
     * Checks if it is okay to put a ship of this length with its bow in this
     * location, with the given orientation.
     *
     * @param row        the row (0 to 9) which contains the bow (front) of the ship.
     * @param column     the column (0 to 9) which contains the bow (front) of the
     *                   ship.
     * @param horizontal {@literal true} if the ship occupies a single row,
     *                   {@literal false} otherwise.
     * @param ocean      the ocean in which the ship is to be placed.
     * @return {@literal true} if it is okay to put a ship of this length with its
     * bow in this location, with the given orientation, {@literal false}
     * otherwise.
     */
    public boolean okToPlaceShipAt(int row, int column, boolean horizontal, Ocean ocean) {
        // no horizontal, vertical, or diagonal adjacency
        if (horizontal) {
            for (int i = row - 1; i <= row + 1; i++) {
                for (int j = column - 1; j <= column + this.getLength(); j++) {
                    if (i >= 0 && i < 10 && j >= 0 && j < 10) {
                        if (ocean.isOccupied(i, j)) {
                            return false;
                        }
                    }
                }
            }
        } else {
            for (int i = row - 1; i <= row + this.getLength(); i++) {
                for (int j = column - 1; j <= column + 1; j++) {
                    if (i >= 0 && i < 10 && j >= 0 && j < 10) {
                        if (ocean.isOccupied(i, j)) {
                            return false;
                        }
                    }
                }
            }
        }

        // no out of bounds
        if (horizontal) {
            if (column + this.getLength() > 10) {
                return false;
            }
        } else {
            if (row + this.getLength() > 10) {
                return false;
            }
        }

        return true;
    }

    /**
     * "Puts" the ship in the ocean. This involves giving values to the bowRow,
     * bowColumn, and horizontal instance variables in the ship, and it also
     * involves putting a reference to the ship in each of 1 or more locations
     * (up to 8) in the ships array in the Ocean object. (Note: This will be as many
     * as eight identical references; you can't refer to a "part" of a ship, only to
     * the whole ship.)
     *
     * @param row        the row (0 to 9) which contains the bow (front) of the ship.
     * @param column     the column (0 to 9) which contains the bow (front) of the
     *                   ship.
     * @param horizontal {@literal true} if the ship occupies a single row,
     *                   {@literal false} otherwise.
     * @param ocean      the ocean in which the ship is to be placed.
     */
    public void placeShipAt(int row, int column, boolean horizontal, Ocean ocean) {
        this.setBowRow(row);
        this.setBowColumn(column);
        this.setHorizontal(horizontal);
        for (int i = 0; i < this.getLength(); i++) {
            if (horizontal) {
                ocean.getShipArray()[row][column + i] = this;
            } else {
                ocean.getShipArray()[row + i][column] = this;
            }
        }
    }

    /**
     * If a part of the ship occupies the given row and column, and the ship hasn't
     * been sunk, mark that part of the ship as "hit" (in the hit array, 0 indicates
     * the bow) and return {@literal true}, otherwise return {@literal false}.
     *
     * @param row    the row (0 to 9) which contains the bow (front) of the ship.
     * @param column the column (0 to 9) which contains the bow (front) of the ship.
     * @return {@literal true} if a part of the ship occupies the given row and
     * column, and the ship hasn't been sunk, {@literal false} otherwise.
     */
    public boolean shootAt(int row, int column) {
        if (this.isSunk()) {
            return false;
        }

        if (this.isHorizontal()) {
            if (row != this.getBowRow()) {
                return false;
            }
            if (column < this.getBowColumn() || column >= this.getBowColumn() + this.getLength()) {
                return false;
            }
            this.hit[column - this.getBowColumn()] = true;
        } else {
            if (column != this.getBowColumn()) {
                return false;
            }
            if (row < this.getBowRow() || row >= this.getBowRow() + this.getLength()) {
                return false;
            }
            this.hit[row - this.getBowRow()] = true;
        }

        return true;
    }

    /**
     * @return {@literal true} if this is a real ship, {@literal false} otherwise.
     */
    public boolean isRealShip() {
        return true;
    }

    /**
     * @return {@literal true} if every part of the ship has been hit, {@literal
     * false} otherwise.
     */
    public boolean isSunk() {
        for (boolean hit : this.hit) {
            if (!hit) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns a single-character {@literal String} to use in the Ocean's print
     * method (see below).
     *
     * @return "x" if the ship has been sunk, "S" if it has not been sunk.
     */
    @Override
    public String toString() {
        return this.isSunk() ? "x" : "S";
    }

    /**
     * Returns a single-character {@literal String} to use in the Ocean's print
     * method (see below).
     *
     * @return "x" if the ship has been sunk, "S" if it has not been sunk.
     */
    public String toString(int row, int column) {
        return this.toString(row, column, false);
    }

    /**
     * Returns a single-character {@literal String} to use in the Ocean's print
     * method (see below).
     *
     * @return "x" if the ship has been sunk, "S" if it has not been sunk.
     */
    public String toString(int row, int column, boolean showShip) {
        if (showShip) {
            return this.toString();
        } else {
            return ".";
        }
    }
}