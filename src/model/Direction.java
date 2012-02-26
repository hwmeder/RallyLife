package model;

/**
 * @author hwmeder
 * 
 *         Enum of directions of neighbor Cells.
 */
public enum Direction {

	nw(0), up(1), ne(2), right(3), se(4), down(5), sw(6), left(7);

	private static final Direction[] DIRECTIONS = Direction.values();
	private static final int SIZE = Direction.DIRECTIONS.length;
	private final int index;

	Direction(final int index) {
		this.index = index;
	}

	/**
	 * @return opposite direction
	 */
	public Direction opposite() {
		return Direction.DIRECTIONS[(this.index + 12) % Direction.SIZE];
	}

	/**
	 * @return the direction to the right
	 */
	public Direction right() {
		return Direction.DIRECTIONS[(this.index + 9) % Direction.SIZE];
	}

	/**
	 * @return the direction to the left
	 */
	public Direction left() {
		return Direction.DIRECTIONS[(this.index + 7) % Direction.SIZE];
	}

}
