import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ShipTest {
    EmptySea emptySea;
    Submarine submarine;
    Destroyer destroyer;
    Battleship battleship;
    Cruiser cruiser;


    @Before
    public void setUp() {
        emptySea = new EmptySea();
        submarine = new Submarine();
        destroyer = new Destroyer();
        battleship = new Battleship();
        cruiser = new Cruiser();
    }

    @Test
    public void testGetLength() {
        assertEquals(1, submarine.getLength());
        assertEquals(2, destroyer.getLength());
        assertEquals(3, cruiser.getLength());
        assertEquals(4, battleship.getLength());
        assertEquals(1, emptySea.getLength());
    }

    @Test
    public void testGetBowRow() {
        submarine.placeShipAt(1, 2, true, new Ocean());
        assertEquals(1, submarine.getBowRow());

        destroyer.placeShipAt(3, 4, false, new Ocean());
        assertEquals(3, destroyer.getBowRow());

        battleship.placeShipAt(5, 6, true, new Ocean());
        assertEquals(5, battleship.getBowRow());

        cruiser.placeShipAt(7, 8, false, new Ocean());
        assertEquals(7, cruiser.getBowRow());
    }

    @Test
    public void testGetBowColumn() {
        submarine.placeShipAt(1, 2, true, new Ocean());
        assertEquals(2, submarine.getBowColumn());

        destroyer.placeShipAt(3, 4, false, new Ocean());
        assertEquals(4, destroyer.getBowColumn());

        battleship.placeShipAt(5, 6, true, new Ocean());
        assertEquals(6, battleship.getBowColumn());

        cruiser.placeShipAt(7, 8, false, new Ocean());
        assertEquals(8, cruiser.getBowColumn());
    }

    @Test
    public void testIsHorizontal() {
        submarine.placeShipAt(1, 2, true, new Ocean());
        assertTrue(submarine.isHorizontal());

        destroyer.placeShipAt(3, 4, false, new Ocean());
        assertFalse(destroyer.isHorizontal());

        battleship.placeShipAt(5, 6, true, new Ocean());
        assertTrue(battleship.isHorizontal());

        cruiser.placeShipAt(7, 8, false, new Ocean());
        assertFalse(cruiser.isHorizontal());
    }

    @Test
    public void testSetBowRow() {
        submarine.setBowRow(1);
        assertEquals(1, submarine.getBowRow());

        destroyer.setBowRow(3);
        assertEquals(3, destroyer.getBowRow());

        battleship.setBowRow(5);
        assertEquals(5, battleship.getBowRow());

        cruiser.setBowRow(7);
        assertEquals(7, cruiser.getBowRow());

        emptySea.setBowRow(9);
        assertEquals(9, emptySea.getBowRow());
    }

    @Test
    public void testSetBowColumn() {
        submarine.setBowColumn(2);
        assertEquals(2, submarine.getBowColumn());

        destroyer.setBowColumn(4);
        assertEquals(4, destroyer.getBowColumn());

        battleship.setBowColumn(6);
        assertEquals(6, battleship.getBowColumn());

        cruiser.setBowColumn(8);
        assertEquals(8, cruiser.getBowColumn());

        emptySea.setBowColumn(0);
        assertEquals(0, emptySea.getBowColumn());
    }

    @Test
    public void testSetHorizontal() {
        submarine.setHorizontal(true);
        assertTrue(submarine.isHorizontal());

        destroyer.setHorizontal(false);
        assertFalse(destroyer.isHorizontal());

        battleship.setHorizontal(true);
        assertTrue(battleship.isHorizontal());

        cruiser.setHorizontal(false);
        assertFalse(cruiser.isHorizontal());

        emptySea.setHorizontal(true);
        assertTrue(emptySea.isHorizontal());
    }

    @Test
    public void testGetShipType() {
        assertEquals("submarine", submarine.getShipType());
        assertEquals("destroyer", destroyer.getShipType());
        assertEquals("cruiser", cruiser.getShipType());
        assertEquals("battleship", battleship.getShipType());
        assertEquals("empty", emptySea.getShipType());
    }

    @Test
    public void testOkToPlaceShipAt() {
        Ocean ocean = new Ocean();
        assertTrue(submarine.okToPlaceShipAt(0, 0, true, ocean));
        assertTrue(submarine.okToPlaceShipAt(0, 0, false, ocean));
        assertTrue(submarine.okToPlaceShipAt(0, 9, true, ocean));
        assertTrue(submarine.okToPlaceShipAt(9, 0, false, ocean));
        assertTrue(submarine.okToPlaceShipAt(9, 9, true, ocean));
        assertTrue(submarine.okToPlaceShipAt(9, 9, false, ocean));
        assertTrue(submarine.okToPlaceShipAt(5, 5, true, ocean));
        assertTrue(submarine.okToPlaceShipAt(5, 5, false, ocean));
        assertTrue(submarine.okToPlaceShipAt(5, 6, true, ocean));
        assertTrue(submarine.okToPlaceShipAt(5, 6, false, ocean));
        assertTrue(submarine.okToPlaceShipAt(6, 5, true, ocean));
        assertTrue(submarine.okToPlaceShipAt(6, 5, false, ocean));
        assertTrue(submarine.okToPlaceShipAt(6, 6, true, ocean));
        assertTrue(submarine.okToPlaceShipAt(6, 6, false, ocean));
        assertTrue(submarine.okToPlaceShipAt(0, 9, true, ocean));
        assertTrue(submarine.okToPlaceShipAt(9, 0, false, ocean));
        assertTrue(submarine.okToPlaceShipAt(9, 9, true, ocean));
        assertTrue(submarine.okToPlaceShipAt(9, 9, false, ocean));
        assertTrue(submarine.okToPlaceShipAt(5, 5, true, ocean));
        assertTrue(submarine.okToPlaceShipAt(5, 5, false, ocean));
        assertTrue(submarine.okToPlaceShipAt(5, 6, true, ocean));
        assertTrue(submarine.okToPlaceShipAt(5, 6, false, ocean));
        assertTrue(submarine.okToPlaceShipAt(6, 5, true, ocean));
        assertTrue(submarine.okToPlaceShipAt(6, 5, false, ocean));
        assertTrue(submarine.okToPlaceShipAt(6, 6, true, ocean));
    }

    @Test
    public void testPlaceShipAt() {
        Ocean ocean = new Ocean();
        submarine.placeShipAt(0, 0, true, ocean);
        assertEquals(0, submarine.getBowRow());
        assertEquals(0, submarine.getBowColumn());
        assertTrue(submarine.isHorizontal());
        assertEquals(submarine, ocean.getShipArray()[0][0]);


        destroyer.placeShipAt(2, 3, false, ocean);
        assertEquals(2, destroyer.getBowRow());
        assertEquals(3, destroyer.getBowColumn());
        assertFalse(destroyer.isHorizontal());
        assertEquals(destroyer, ocean.getShipArray()[2][3]);
        assertEquals(destroyer, ocean.getShipArray()[3][3]);


        battleship.placeShipAt(4, 5, true, ocean);
        assertEquals(4, battleship.getBowRow());
        assertEquals(5, battleship.getBowColumn());
        assertTrue(battleship.isHorizontal());
        assertEquals(battleship, ocean.getShipArray()[4][5]);
        assertEquals(battleship, ocean.getShipArray()[4][6]);
        assertEquals(battleship, ocean.getShipArray()[4][7]);
        assertEquals(battleship, ocean.getShipArray()[4][8]);


        cruiser.placeShipAt(6, 7, false, ocean);
        assertEquals(6, cruiser.getBowRow());
        assertEquals(7, cruiser.getBowColumn());
        assertFalse(cruiser.isHorizontal());
        assertEquals(cruiser, ocean.getShipArray()[6][7]);
        assertEquals(cruiser, ocean.getShipArray()[7][7]);
        assertEquals(cruiser, ocean.getShipArray()[8][7]);
    }

    @Test
    public void testShootAt() {
        Ocean ocean = new Ocean();
        submarine.placeShipAt(0, 0, true, ocean);
        assertTrue(submarine.shootAt(0, 0));

        destroyer.placeShipAt(2, 3, false, ocean);
        assertTrue(destroyer.shootAt(2, 3));
        assertTrue(destroyer.shootAt(3, 3));
        assertFalse(destroyer.shootAt(4, 3));

        battleship.placeShipAt(4, 5, true, ocean);
        assertTrue(battleship.shootAt(4, 5));
        assertTrue(battleship.shootAt(4, 6));
        assertTrue(battleship.shootAt(4, 7));
        assertTrue(battleship.shootAt(4, 8));
        assertFalse(battleship.shootAt(4, 9));
        assertFalse(battleship.shootAt(5, 5));

        cruiser.placeShipAt(6, 7, false, ocean);
        assertTrue(cruiser.shootAt(6, 7));
        assertTrue(cruiser.shootAt(7, 7));
        assertTrue(cruiser.shootAt(8, 7));
        assertFalse(cruiser.shootAt(9, 7));
        assertFalse(cruiser.shootAt(6, 8));
    }

    @Test
    public void testIsSunk() {
        Ocean ocean = new Ocean();
        submarine.placeShipAt(0, 0, true, ocean);
        assertFalse(submarine.isSunk());
        submarine.shootAt(0, 0);
        assertTrue(submarine.isSunk());

        destroyer.placeShipAt(2, 3, false, ocean);
        assertFalse(destroyer.isSunk());
        destroyer.shootAt(2, 3);
        assertFalse(destroyer.isSunk());
        destroyer.shootAt(3, 3);
        assertTrue(destroyer.isSunk());

        battleship.placeShipAt(4, 5, true, ocean);
        assertFalse(battleship.isSunk());
        battleship.shootAt(4, 5);
        assertFalse(battleship.isSunk());
        battleship.shootAt(4, 6);
        assertFalse(battleship.isSunk());
        battleship.shootAt(4, 7);
        assertFalse(battleship.isSunk());
        battleship.shootAt(4, 8);
        assertTrue(battleship.isSunk());

        cruiser.placeShipAt(6, 7, false, ocean);
        assertFalse(cruiser.isSunk());
        cruiser.shootAt(6, 7);
        assertFalse(cruiser.isSunk());
        cruiser.shootAt(7, 7);
        assertFalse(cruiser.isSunk());
        cruiser.shootAt(8, 7);
        assertTrue(cruiser.isSunk());
    }

    @Test
    public void testToString() {
        Ocean ocean = new Ocean();
        submarine.placeShipAt(0, 0, true, ocean);
        assertEquals("S", submarine.toString());
        submarine.shootAt(0, 0);
        assertEquals("x", submarine.toString());

        destroyer.placeShipAt(2, 3, false, ocean);
        assertEquals("S", destroyer.toString());
        destroyer.shootAt(2, 3);
        assertEquals("S", destroyer.toString());
        destroyer.shootAt(3, 3);
        assertEquals("x", destroyer.toString());

        battleship.placeShipAt(4, 5, true, ocean);
        assertEquals("S", battleship.toString());
        battleship.shootAt(4, 5);
        assertEquals("S", battleship.toString());
        battleship.shootAt(4, 6);
        assertEquals("S", battleship.toString());
        battleship.shootAt(4, 7);
        assertEquals("S", battleship.toString());
        battleship.shootAt(4, 8);
        assertEquals("x", battleship.toString());

        cruiser.placeShipAt(6, 7, false, ocean);
        assertEquals("S", cruiser.toString());
        cruiser.shootAt(6, 7);
        assertEquals("S", cruiser.toString());
        cruiser.shootAt(7, 7);
        assertEquals("S", cruiser.toString());
        cruiser.shootAt(8, 7);
        assertEquals("x", cruiser.toString());
    }
}
