import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import model.Cell;
import model.Direction;

/**
 * @author hwmeder
 * 
 *         Currently reads the file in resources/pulsar.txt and then processes
 *         the pattern according to rules of Conway's Game of Life.
 * 
 *         http://en.wikipedia.org/wiki/Conway's_Game_of_Life
 */
public class Life {
	/**
	 * @param args are currently ignored.
	 * @throws IOException if file cannot be found or read
	 * @throws InterruptedException if a sleep is interrupted
	 */
	public static void main(final String[] args)
			throws IOException,
			InterruptedException {

		// TODO Potential command enhancements.
		// System.out.println(
		// "Life [-b] [-i<#Iterations] [-t<PauseSeconds>] [-c+-] [filePath]");
		// -b causes Edges to retract when they contain only dead Cells
		// -i to specify the number of iterations
		// -t to specify the length of the time delay between iterations
		// -c to specify the characters to use for alive and dead cells
		// an alternate filePath can be specified

		final InputStream in = Life.class.getClassLoader().getResourceAsStream(
				"Pulsar.txt");
		final Reader reader = new InputStreamReader(in);
		final BufferedReader bf = new BufferedReader(reader);
		String line;
		Cell cell = new Cell();
		while ((line = bf.readLine()) != null) {
			cell = cell.getNeighborCell(Direction.down);
			cell.readLine(line);
		}
		for (int i = 0; i < 10; i++) {
			System.out.println(cell.buildCurrentCharGrid());
			Thread.sleep(3000);
			cell.cycleToNexGeneration();
		}
	}
}
