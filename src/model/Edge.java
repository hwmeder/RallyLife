package model;

import java.security.InvalidParameterException;
import java.util.HashSet;
import java.util.Set;

/**
 * @author hwmeder
 * 
 *         An Edge around the grid of Cells supporting Life.
 * 
 *         When counting alive neighbors, this Component lets neighbors know
 *         that the Cells outside the edge are not alive.
 * 
 *         When processing Rows, this Component marks the end of the Row.
 */
public class Edge implements Component {

	final private Direction dir;
	private Set<Cell> adjacentCells = new HashSet<Cell>();

	/**
	 * @param p_dir of Edge (or corner) relative to the grid of Cells
	 */
	public Edge(final Direction p_dir) {
		this.dir = p_dir;
	}

	public Component getNeighbor(final Direction p_dir) {
		if (this.dir.equals(p_dir.opposite())) {
			return null;
		}
		return this;
	}

	public void addAdjacentCell(final Direction p_dir, final Cell p_newCell) {
		this.adjacentCells.add(p_newCell);
		p_newCell.point(p_dir, this);
	}

	public Component getNeighborCell(final Direction p_dir) {
		return getNeighbor(p_dir);
	}

	/**
	 * Add a new layer of adjacent Cells along the Edge.
	 */
	public void grow() {
		// Collect a set of the new Cells.
		final Set<Cell> newCells = new HashSet<Cell>();

		// Add a new Cell for each Cell along the Edge.
		for (final Cell cell : this.adjacentCells) {
			final Cell newCell = new Cell();
			newCells.add(newCell);

			// Point old Cell at the new Cell.
			cell.point(this.dir, newCell);

			// Point the new Cell at this Edge and adjacent Corners.
			newCell.point(this.dir.right(), cell.getNeighbor(this.dir.right()));
			newCell.point(this.dir, this);
			newCell.point(this.dir.left(), cell.getNeighbor(this.dir.left()));

			// Point the new Cell at the old Cells and adjacent Edges.
			newCell.point(this.dir.opposite().right(), cell
					.getNeighbor(this.dir.left().left()));
			newCell.point(this.dir.opposite(), cell);
			newCell.point(this.dir.opposite().left(), cell.getNeighbor(this.dir
					.right().right()));
		}
		// Finish pointing Cells.
		for (final Cell cell : this.adjacentCells) {
			// Point old Cells at new Cells and adjacent Edges on the diagonal.
			cell.point(this.dir.right(), cell.getNeighbor(
					this.dir.right().right()).getNeighbor(this.dir));
			cell.point(this.dir.left(), cell
					.getNeighbor(this.dir.left().left()).getNeighbor(this.dir));
			// Point new Cells at adjacent Cells and Edges.
			final Cell newCell = (Cell) cell.getNeighbor(this.dir);
			newCell.point(this.dir.right().right(), cell.getNeighbor(
					this.dir.right().right()).getNeighbor(this.dir));
			newCell.point(this.dir.left().left(), cell.getNeighbor(
					this.dir.left().left()).getNeighbor(this.dir));
			{
				final Component edge = newCell.getNeighbor(this.dir.opposite()
						.right());
				if (!edge.isCell()) { // if this is the edge on the right,
					// add the new edge Cell to the Edge's adjacent Cell list.
					((Edge) edge).addAdjacentCell(this.dir.left().left(),
							newCell);
				}
			}
			{
				final Component edge = newCell.getNeighbor(this.dir.opposite()
						.left());
				edge.addAdjacentCell(this.dir.right().right(), newCell);
			}
			// The corner adjacent Cell lists do not need to be maintained.
		}
		// Update the list of adjacent Cells for this Edge.
		this.adjacentCells = newCells;
	}

	public String buildCharRow() {
		// We have reached the far edge.
		// Just return and go back to the beginning of the chain.
		return "";
	}

	public void calculateNextState() {
		// We have reached the far edge.
		// Just return and go back to the beginning of the chain.
	}

	public void updateState() {
		// We have reached the far edge.
		// Just return and go back to the beginning of the chain.
	}

	public boolean isAlive() {
		// Edges are never alive.
		return false;
	}

	public void setAlive(final boolean p_isAlive) {
		throw new InvalidParameterException(
				"Who dares to bring an Edge to life?");
	}

	public boolean isCell() {
		return false;
	}
}
