import java.util.Arrays;
import java.util.Objects;

/**
 * This class manages the game state by keeping track of what entity is
 * contained in each position on the game board.
 * 
 * @author harry
 *
 */
public class Ocean implements OceanInterface {

	/**
	 * A 10x10 2D array of Ships, which can be used to quickly determine which ship
	 * is in any given location.
	 */
	protected Ship[][] ships;

	/**
	 * The total number of shots fired by the user
	 */
	protected int shotsFired;

	/**
	 * The number of times a shot hit a ship. If the user shoots the same part of a
	 * ship more than once, every hit is counted, even though the additional "hits"
	 * don't do the user any good.
	 */
	protected int hitCount;

	/**
	 * The number of ships totally sunk.
	 * 
	 */
	protected int shipsSunk;

	/**
	 * Records the results of shots fired at the ocean.
	 */
	protected boolean[][] shots;

	/**
	 * Creates an "empty" ocean, filling every space in the <code>ships</code> array
	 * with EmptySea objects. Should also initialize the other instance variables
	 * appropriately.
	 */
	public Ocean() {
		this.shotsFired = 0;
		this.hitCount = 0;
		this.shipsSunk = 0;
		this.ships = new Ship[10][10];
		for (int row = 0; row < this.ships.length; row++) {
			for (int column = 0; column < this.ships[row].length; column++) {
				this.ships[row][column] = new EmptySea();
			}
		}
		this.shots = new boolean[10][10];
        for (boolean[] shot : this.shots) {
            Arrays.fill(shot, false);
        }
	}

	/**
	 * Place all ten ships randomly on the (initially empty) ocean. Larger ships
	 * must be placed before smaller ones to avoid cases where it may be impossible
	 * to place the larger ships.
	 * 
	 * @see java.util.Random
	 */
	public void placeAllShipsRandomly() {
		Ship[] ships = new Ship[10];
		ships[0] = new Battleship();
		ships[1] = new Cruiser();
		ships[2] = new Cruiser();
		ships[3] = new Destroyer();
		ships[4] = new Destroyer();
		ships[5] = new Destroyer();
		ships[6] = new Submarine();
		ships[7] = new Submarine();
		ships[8] = new Submarine();
		ships[9] = new Submarine();
		for (Ship ship : ships) {
			boolean shipPlaced = false;
			while (!shipPlaced) {
				int row = (int) (Math.random() * 10);
				int column = (int) (Math.random() * 10);
				boolean horizontal = Math.random() < 0.5;
				if (ship.okToPlaceShipAt(row, column, horizontal, this)) {
					ship.placeShipAt(row, column, horizontal, this);
					shipPlaced = true;
				}
			}
		}

	}

	/**
	 * Checks if this coordinate is not empty; that is, if this coordinate does not
	 * contain an EmptySea reference.
	 * 
	 * @param row    the row (0 to 9) in which to check for a floating ship
	 * @param column the column (0 to 9) in which to check for a floating ship
	 * @return {@literal true} if the given location contains a ship, and
	 *         {@literal false} otherwise.
	 */
	public boolean isOccupied(int row, int column) {
		return !Objects.equals(this.ships[row][column].getShipType(), "empty");
	}

	/**
	 * Fires a shot at this coordinate. This will update the number of shots that
	 * have been fired (and potentially the number of hits, as well). If a location
	 * contains a real, not sunk ship, this method should return {@literal true}
	 * every time the user shoots at that location. If the ship has been sunk,
	 * additional shots at this location should return {@literal false}.
	 * 
	 * @param row    the row (0 to 9) in which to shoot
	 * @param column the column (0 to 9) in which to shoot
	 * @return {@literal true} if the given location contains an afloat ship (not an
	 *         EmptySea), {@literal false} if it does not.
	 */
	public boolean shootAt(int row, int column) {
		this.shotsFired++;
		this.shots[row][column] = true;
		if (this.ships[row][column].shootAt(row, column)) {
			this.hitCount++;
			if (this.ships[row][column].isSunk()) {
				this.shipsSunk++;
			}

			return true;
		}
		return false;
	}

	/**
	 * @return the number of shots fired in this game.
	 */
	public int getShotsFired() {
		return this.shotsFired;
	}

	/**
	 * @return the number of hits recorded in this game.
	 */
	public int getHitCount() {
		return this.shotsFired;
	}

	/**
	 * @return the number of ships sunk in this game.
	 */
	public int getShipsSunk() {
		return this.shipsSunk;
	}

	/**
	 * @return {@literal true} if all ships have been sunk, otherwise
	 *         {@literal false}.
	 */
	public boolean isGameOver() {
		return areAllShipsSunk();
	}

	/**
	 * @return the 10x10 array of shots.
	 */
	public boolean[][] getShots() {
		return this.shots;
	}

	/**
	 * Provides access to the grid of ships in this Ocean. The methods in the Ship
	 * class that take an Ocean parameter must be able to read and even modify the
	 * contents of this array. While it is generally undesirable to allow methods in
	 * one class to directly access instancce variables in another class, in this
	 * case there is no clear and elegant alternatives.
	 * 
	 * @return the 10x10 array of ships.
	 */
	public Ship[][] getShipArray() {
		return this.ships;
	}

	/**
	 * Prints the ocean. To aid the user, row numbers should be displayed along the
	 * left edge of the array, and column numbers should be displayed along the top.
	 * Numbers should be 0 to 9, not 1 to 10. The top left corner square should be
	 * 0, 0.
	 * <ul>
	 * <li>Use 'S' to indicate a location that you have fired upon and hit a (real)
	 * ship</li>
	 * <li>'-' to indicate a location that you have fired upon and found nothing
	 * there</li>
	 * <li>'x' to indicate a location containing a sunken ship</li>
	 * <li>'.' (a period) to indicate a location that you have never fired
	 * upon.</li>
	 * </ul>
	 * 
	 * This is the only method in Ocean that has any printing capability, and it
	 * should never be called from within the Ocean class except for the purposes of
	 * debugging.
	 * 
	 */
	public void print() {
		System.out.print("  "); // Initial spacing for row numbers
        for (int col = 0; col < 10; col++) {
            System.out.print(col + " ");
        }
        System.out.println();

		for (int row = 0; row < this.ships.length; row++) {
			System.out.print(row + " "); // Row numbers
			for (int column = 0; column < this.ships[row].length; column++) {
				if (this.shots[row][column]) {
					if (this.ships[row][column].isRealShip()) {
						if (this.ships[row][column].isSunk()) {
							System.out.print("x ");
						} else {
							System.out.print("S ");
						}
					} else {
						System.out.print("- ");
					}
				} else {
					System.out.print(". ");
				}
			}
			System.out.println();
		}
	}

	/**
	 * @return the number of shots fired in this game.
	 */

	/**
	 * @return {@literal true} if all ships have been sunk, otherwise
	 *         {@literal false}.
	 */
	@Override
	public boolean areAllShipsSunk() {
		boolean allShipsSunk = true;
		for (Ship[] shipRow : this.ships) {
			for (Ship ship : shipRow) {
				if (ship.isRealShip() && !ship.isSunk()) {
					allShipsSunk = false;
					break;
				}
			}
		}
		return allShipsSunk;
	}


}
