package model;

/**
 * @author hwmeder
 * 
 *         A Cell in the grid of Cells supporting Life.
 * 
 */
public class Cell implements Component {

	private final Component[] neighbors = new Component[8];
	private boolean isAlive = false;
	private boolean willLive;

	public Component getNeighbor(final Direction p_dir) {
		Component neighbor = this.neighbors[p_dir.ordinal()];
		if (neighbor == null) { // This is the first Cell.
			initEdgesAndCorners();
			neighbor = this.neighbors[p_dir.ordinal()];
		}
		return neighbor;
	}

	/**
	 * Add Edges and corners all around this Cell.
	 */
	private void initEdgesAndCorners() {
		for (final Direction l_dir : Direction.values()) {
			new Edge(l_dir).addAdjacentCell(l_dir, this);
		}
	}

	public void addAdjacentCell(final Direction p_dir, final Cell p_newCell) {
		// Cells do not keep a list of adjacent Cells.
		// A directional list of neighbors is kept instead.
	}

	/**
	 * Point at the neighbor in the specified direction.
	 * 
	 * @param p_dir is the direction of the neighbor
	 * @param p_neighbor is the neighbor to be pointed at
	 */
	public void point(final Direction p_dir, final Component p_neighbor) {
		this.neighbors[p_dir.ordinal()] = p_neighbor;
	}

	public boolean isAlive() {
		return this.isAlive;
	}

	public void setAlive(final boolean p_isAlive) {
		this.isAlive = p_isAlive;
		this.willLive = p_isAlive;
	}

	public Cell getNeighborCell(final Direction p_dir) {
		getNeighbor(p_dir).grow();
		return (Cell) getNeighbor(p_dir);
	}

	public void grow() {
		// Cells do not create new adjacent Cells.
	}

	/**
	 * @return String representing grid of living and dead Cells.
	 */
	public String buildCurrentCharGrid() {
		Component leftCellInRow = findTopLeftCorner();

		String stream = "";
		do {
			stream += leftCellInRow.buildCharRow() + '\n';
			leftCellInRow = leftCellInRow.getNeighbor(Direction.down);
		} while (leftCellInRow.isCell());
		return stream;
	}

	private Component findTopLeftCorner() {
		Component corner = this;
		Component next = this;
		do {
			corner = next;
			next = next.getNeighbor(Direction.up);
		} while (next.isCell());
		next = corner;
		do {
			corner = next;
			next = next.getNeighbor(Direction.left);
		} while (next.isCell());
		return corner;
	}

	public String buildCharRow() {
		return (isAlive() ? "1 " : "0 ")
				+ getNeighbor(Direction.right).buildCharRow();
	}

	/**
	 * Set a row of Cells starting from this Cell and moving to the right.
	 * 
	 * @param p_string to be parsed
	 */
	public void readLine(final String p_string) {
		if (p_string.length() > 0) {
			final String string = p_string.trim();
			setAlive(string.substring(0, 1).equals("1"));
			if (string.length() > 1) {
				final Cell cell = getNeighborCell(Direction.right);
				cell.readLine(string.substring(1));
			}
		}
	}

	/**
	 * Update Cells to status of the next generation.
	 */
	public void cycleToNexGeneration() {
		// Visit all Cells to calculate their state for the next Generation.
		final Component topLeftCornerCell = findTopLeftCorner();
		Component next = topLeftCornerCell;
		do {
			next.calculateNextState();
			next = next.getNeighbor(Direction.down);
		} while (next.isCell());

		// Visit edge Cells to see if there is life beyond the edges.
		Direction dir = Direction.right;
		Component last = topLeftCornerCell; // 
		next = last.getNeighbor(dir);
		Component current;
		do {
			current = next;
			next = current.getNeighbor(dir);
			if (current.isAlive() && last.isAlive() && next.isAlive()) {
				current.getNeighborCell(dir.left().left()).setAlive(true);
			}
			last = current;
			if (!next.isCell()) {
				dir = dir.right().right();
				next = last.getNeighbor(dir);
			}
		} while (!next.equals(topLeftCornerCell));

		next = topLeftCornerCell;
		do {
			next.updateState();
			next = next.getNeighbor(Direction.down);
		} while (next.getClass().equals(Cell.class));
	}

	@SuppressWarnings("fallthrough")
	public void calculateNextState() {
		this.willLive = this.isAlive;
		int count = 0;
		for (final Component cell : this.neighbors) {
			if (cell.isAlive()) {
				count++;
			}
		}
		switch (count) {
		case 3:
			this.willLive = true;
		case 2:
			break;
		default:
			this.willLive = false;
		}
		getNeighbor(Direction.right).calculateNextState();
	}

	public void updateState() {
		this.isAlive = this.willLive;
		getNeighbor(Direction.right).updateState();
	}

	public boolean isCell() {
		return true;
	}

}
