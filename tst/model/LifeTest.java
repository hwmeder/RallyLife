package model;

import java.security.InvalidParameterException;

import junit.framework.TestCase;

/**
 * @author hwmeder
 * 
 *         Test the grid of Cells implementing Conway's Game of Life.
 */
public class LifeTest extends TestCase {

	/**
	 * Test Cell
	 */
	final Cell cell = new Cell();

	/**
	 * Verify the construction of a Cell.
	 */
	public void testCreateFirstCell() {
		verify(this.cell);
	}

	/**
	 * Test ability to manage the state of Components.
	 */
	public void testIsAlive() {
		assertFalse(this.cell.isAlive());
		this.cell.setAlive(true);
		assertTrue(this.cell.isAlive());
		this.cell.setAlive(false);
		assertFalse(this.cell.isAlive());

		final Component nwCorner = this.cell.getNeighbor(Direction.nw);
		assertFalse(nwCorner.isAlive());
		try {
			nwCorner.setAlive(true);
			fail("Cannot set edges to be alive.");
		} catch (final InvalidParameterException ipe) { // Expected exception.
		}
	}

	/**
	 * Test ability to add Cells in all directions.
	 */
	public void testAddingCells() {
		Direction dir = Direction.up;
		do {
			final Component rightCorner = this.cell.getNeighbor(dir.right());
			final Component leftCorner = this.cell.getNeighbor(dir.left());
			final Component edge = this.cell.getNeighbor(dir);
			final Component newCell = this.cell.getNeighborCell(dir);
			assertEquals(Cell.class, newCell.getClass());
			verify(this.cell);
			verify(newCell);
			assertSame(this.cell, newCell.getNeighbor(dir.opposite()));
			assertSame(newCell, this.cell.getNeighbor(dir));
			assertSame(this.cell.getNeighbor(dir.right().right()).getNeighbor(
					dir), newCell.getNeighbor(dir.right().right()));
			assertSame(this.cell.getNeighbor(dir.left().left())
					.getNeighbor(dir), newCell.getNeighbor(dir.left().left()));
			assertSame(rightCorner, newCell.getNeighbor(dir.right()));
			assertSame(leftCorner, newCell.getNeighbor(dir.left()));
			assertSame(edge, newCell.getNeighbor(dir));
			dir = dir.right().right();
		} while (dir != Direction.up);
		assertEquals("0 0 0 \n0 0 0 \n0 0 0 \n", this.cell
				.buildCurrentCharGrid());
	}

	/**
	 * Confirm ability to flexibly read in configurations and calculating
	 * generations.
	 */
	public void testInput() {
		final String line1 = "111";
		final String line2 = "001";
		final String line3 = "10";

		final Cell cell3 = this.cell.getNeighborCell(Direction.down);
		final Cell cell2 = this.cell.getNeighborCell(Direction.left);
		final Cell cell1 = cell2.getNeighborCell(Direction.up);

		cell3.readLine(line3);
		cell2.readLine(line2);
		cell1.readLine(line1);

		verify(cell1);
		verify(cell2);
		verify(cell3);

		String expectation = "1 1 1 \n0 0 1 \n0 1 0 \n";
		assertEquals(expectation, this.cell.buildCurrentCharGrid());
		this.cell.cycleToNexGeneration();
		expectation = "0 1 0 \n0 1 1 \n1 0 1 \n0 0 0 \n";
		assertEquals(expectation, this.cell.buildCurrentCharGrid());
		this.cell.cycleToNexGeneration();
		expectation = "0 1 1 \n1 0 1 \n0 0 1 \n0 0 0 \n";
		assertEquals(expectation, this.cell.buildCurrentCharGrid());
		this.cell.cycleToNexGeneration();
		expectation = "0 1 1 0 \n0 0 1 1 \n0 1 0 0 \n0 0 0 0 \n";
		assertEquals(expectation, this.cell.buildCurrentCharGrid());
		this.cell.cycleToNexGeneration();
		expectation = "0 1 1 1 \n0 0 0 1 \n0 0 1 0 \n0 0 0 0 \n";
		assertEquals(expectation, this.cell.buildCurrentCharGrid());

		final Cell cell4 = cell3.getNeighborCell(Direction.down);
		cell4.readLine("");
		verify(cell4);
		expectation = "0 1 1 1 \n0 0 0 1 \n0 0 1 0 \n0 0 0 0 \n0 0 0 0 \n";
		assertEquals(expectation, this.cell.buildCurrentCharGrid());

	}

	/**
	 * Confirm ability to flexibly read in configurations and calculating
	 * generations.
	 */
	public void test2Input() {
		final String line1 = "0 1 0 0 0";
		final String line2 = "1 0 0 1 1";
		final String line3 = "1 1 0 0 1";
		final String line4 = "0 1 0 0 0";
		final String line5 = "1 0 0 0 1";

		final Cell cell1 = this.cell;
		final Cell cell2 = cell1.getNeighborCell(Direction.down);
		final Cell cell3 = cell2.getNeighborCell(Direction.down);
		final Cell cell4 = cell3.getNeighborCell(Direction.down);
		final Cell cell5 = cell4.getNeighborCell(Direction.down);

		cell1.readLine(line1);
		cell2.readLine(line2);
		cell3.readLine(line3);
		cell4.readLine(line4);
		cell5.readLine(line5);

		verify(cell1);
		verify(cell2);
		verify(cell3);
		verify(cell4);
		verify(cell5);

		this.cell.cycleToNexGeneration();
		final String expectation = "0 0 0 0 0 \n1 0 1 1 1 \n1 1 1 1 1 \n0 1 0 0 0 \n0 0 0 0 0 \n";
		assertEquals(expectation, this.cell.buildCurrentCharGrid());
	}

	private void verify(final Component p_cell) {
		Direction dir = Direction.up;
		do {
			assertNotNull(p_cell.getNeighbor(dir));
			dir = dir.left();
		} while (!dir.equals(Direction.up));
	}
}
