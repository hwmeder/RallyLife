package model;

/**
 * @author hwmeder
 * 
 *         Cells and Edges are the components of the Model.
 * 
 *         Edges fill in for both edges and corners.
 * 
 */
public interface Component {

	/**
	 * @param p_dir of the desired Component relative to this Component
	 * @return Component in the specified Direction
	 */
	Component getNeighbor(Direction p_dir);

	/**
	 * Guarantees that a Cell is returned as opposed to an Edge or corner.
	 * 
	 * A new layer of Cells will be added if needed.
	 * 
	 * @param p_dir of the desired Component relative to this Component
	 * @return Component in the specified Direction
	 */
	Component getNeighborCell(Direction p_dir);

	/**
	 * Add a new layer of Cells along an Edge.
	 */
	void grow();

	/**
	 * Calculate the next state for the Component.
	 */
	void calculateNextState();

	/**
	 * Update Components according to calculated state changes.
	 */
	void updateState();

	/**
	 * @return String representing the tail of the row to the right of this
	 *         Component, including this Cell.
	 */
	String buildCharRow();

	/**
	 * @return true if this Component is a Cell, false if it is an Edge.
	 */
	boolean isCell();

	/**
	 * Update the state of the Component.
	 * 
	 * @param p_isAlive is the new state
	 */
	void setAlive(boolean p_isAlive);

	/**
	 * @return true if this Component is currently alive.
	 */
	boolean isAlive();

	/**
	 * Only Edges keep a list of adjacent Components (Cells).
	 * 
	 * @param p_dir direction of new Cell
	 * @param p_neighbor adjacent to this Component
	 */
	void addAdjacentCell(Direction p_dir, Cell p_neighbor);
}