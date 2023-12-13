import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class OceanTest {

    private Ocean ocean;

    @Before
    public void setUp() {
        ocean = new Ocean();
        ocean.placeAllShipsRandomly();
    }

    private int[][] getAdjacentCoordinates(Ship ship) {
        int[][] coordinates = new int[ship.getLength()][2];
        int row = ship.getBowRow();
        int column = ship.getBowColumn();
        boolean horizontal = ship.isHorizontal();
        // get the coordinates of the ship, not including itself
        for (int i = 0; i < ship.getLength(); i++) {
            if (horizontal) {
                coordinates[i][0] = row;
                coordinates[i][1] = column + i;
            } else {
                coordinates[i][0] = row + i;
                coordinates[i][1] = column;
            }
        }
        return coordinates;
    }

    private int[][] generateRandomCoordinates() {
        int[][] coordinates = new int[10][2];
        for (int i = 0; i < 10; i++) {
            coordinates[i][0] = (int) (Math.random() * 10);
            coordinates[i][1] = (int) (Math.random() * 10);
        }
        return coordinates;
    }


    @Test
    public void testShipPlacement() {
        // 1. Check that all ships are placed
        int battleshipCount = 0;
        int cruiserCount = 0;
        int destroyerCount = 0;
        int submarineCount = 0;
        for (Ship[] row : ocean.getShipArray()) {
            for (Ship ship : row) {
                if (ship.getShipType().equals("battleship")) {
                    battleshipCount++;
                } else if (ship.getShipType().equals("cruiser")) {
                    cruiserCount++;
                } else if (ship.getShipType().equals("destroyer")) {
                    destroyerCount++;
                } else if (ship.getShipType().equals("submarine")) {
                    submarineCount++;
                }
            }
        }
        battleshipCount /= 4;
        cruiserCount /= 3;
        destroyerCount /= 2;

        assertEquals(1, battleshipCount);
        assertEquals(2, cruiserCount);
        assertEquals(3, destroyerCount);
        assertEquals(4, submarineCount);

        // 2. Check that there are no adjacent ships
        for (Ship[] row : ocean.getShipArray()) {
            for (Ship ship : row) {
                if (ship.isRealShip()) {
                    int[][] coordinates = getAdjacentCoordinates(ship);
                    for (int[] coordinate : coordinates) {
                        int rowCoordinate = coordinate[0];
                        int columnCoordinate = coordinate[1];
                        assertFalse(
                                ocean.isOccupied(rowCoordinate, columnCoordinate)
                                        && !ship.equals(ocean.getShipArray()[rowCoordinate][columnCoordinate])
                        );
                    }
                }
            }
        }

        // 3. Check that there are no out of bounds ships
        for (Ship[] row : ocean.getShipArray()) {
            for (Ship ship : row) {
                if (ship.isRealShip()) {
                    int[][] coordinates = getAdjacentCoordinates(ship);
                    for (int[] coordinate : coordinates) {
                        int rowCoordinate = coordinate[0];
                        int columnCoordinate = coordinate[1];
                        assertTrue(
                                rowCoordinate >= 0 && rowCoordinate < 10
                                        && columnCoordinate >= 0 && columnCoordinate < 10
                        );
                    }
                }
            }
        }
    }

    @Test
    public void testShootAt() {
        // 1. Check the initial state
        assertEquals(0, ocean.getShotsFired());
        assertEquals(0, ocean.getHitCount());
        assertEquals(0, ocean.getShipsSunk());

        // 2. Check the state after random shots
        int[][] coordinates = generateRandomCoordinates();
        for (int i = 0; i < 10; i++) {
            int row = coordinates[i][0];
            int column = coordinates[i][1];
            ocean.shootAt(row, column);

            assertEquals(i + 1, ocean.getShotsFired());
            assertEquals(ocean.getShots()[row][column], true);
        }
    }

    @Test
    public void testIsGameOver() {
        // 1. Check the initial state
        assertFalse(ocean.isGameOver());

        // 2. Check the state after full shots
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                ocean.shootAt(i, j);
            }
        }
        assertTrue(ocean.isGameOver());
    }

    @Test
    public void testGetShipArray() {
        // 1. Check the initial state
        Ship[][] ships = ocean.getShipArray();
        assertEquals(10, ships.length);
        assertEquals(10, ships[0].length);

        // 2. Check the state after full shots
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                ocean.shootAt(i, j);
            }
        }
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                Ship ship = ocean.getShipArray()[i][j];
                if (ship.isRealShip()) {
                    assertTrue(ship.isSunk());
                }
            }
        }


    }

}
