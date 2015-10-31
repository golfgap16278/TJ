package mainPackage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import javax.swing.SwingWorker;

import mainPackage.GridCell.visibleObject;
import userPackage.NotSmartJerry;
import userPackage.SmartJerry;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class MazeGame {
	
	class MyWorker extends SwingWorker<Integer, Void> {
		
		protected Integer doInBackground() throws Exception {
			// Do a time-consuming task.
			while (cheeseSet.size() > 0) {

				// jerry.move();
				// workerTime = System.currentTimeMillis();
				// jerry.updateJerryWorld();
				// stepCount++;
				//
				// Cheese foundCheese = doesJerryFindCheese();
				//
				// if(foundCheese != null){
				// cheeseSet.remove(foundCheese);
				// }
				// Thread.sleep(sleepDuration);
			}
			// jerry.oldCell = new GridCell(jerry.rowIndex, jerry.columnIndex);
			return -1;
		}

		protected void done() {
			// Do some task after finished the job
			isEnd = true;

		}
	}

	enum Difficulty {
		FORBABY, EASY, NORMAL, HARD, UNSOLVABLE
	};

	int numberOfRows;
	int numberOfColumns;

	int sleepDuration;
	int stepCount = 0;
	long workerTime = 0;
	boolean isEnd;

	static GridGraph worldGraph;
	Set<Cheese> cheeseSet;
	Jerry jerry;
	Tom tom;

	private ArrayList<Wall> worldWalls;

	public MazeGame() {

	}

	public void createRandomMaze(int rows, int columns, Difficulty level) {

		numberOfRows = rows;
		numberOfColumns = columns;

		worldGraph = new GridGraph(rows, columns);

		ArrayList<Wall> tempWalls = new ArrayList<Wall>();

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {

				worldGraph.addNode(i, j);

				if (j != columns - 1)
					tempWalls.add(new Wall(i * columns + j, i * columns + j + 1, Wall.Position.RIGHT));
				if (i != rows - 1)
					tempWalls.add(new Wall(i * columns + j, (i + 1) * columns + j, Wall.Position.BOTTOM));
			}
		}

		worldWalls = new ArrayList<Wall>(tempWalls);

		DisjointSet DJset = new DisjointSet(rows * columns);

		while (DJset.size > 1) {

			Random rand = new Random();
			int wallIndex = rand.nextInt(tempWalls.size());
			int cell1Index = tempWalls.get(wallIndex).cell1Index;
			int cell2Index = tempWalls.get(wallIndex).cell2Index;

			if (DJset.find(cell1Index) != DJset.find(cell2Index)) { // no path
																	// form
																	// cell1 to
																	// cell2

				worldGraph.addEdge(cell1Index / columns, cell1Index % columns, cell2Index / columns,
						cell2Index % columns);

				DJset.join(cell1Index, cell2Index);
				//worldWalls.remove(wallIndex);
				
				for(Wall i: worldWalls){
					if(cell1Index == i.cell1Index & cell2Index == i.cell2Index){
						worldWalls.remove(i);
						break;
					}
				}
				
			}

			tempWalls.remove(wallIndex);
		}

		// Remove more walls
		int numOfRemainingWalls = worldWalls.size();
		Random rand = new Random();

		if (level == Difficulty.FORBABY) {
			for (int i = numOfRemainingWalls; i > (int) numOfRemainingWalls * 0.05; i--) {
				int wallIndex = rand.nextInt(worldWalls.size());
				int cell1Index = worldWalls.get(wallIndex).cell1Index;
				int cell2Index = worldWalls.get(wallIndex).cell2Index;
				worldGraph.addEdge(cell1Index / columns, cell1Index % columns, cell2Index / columns,
						cell2Index % columns);
				worldWalls.remove(wallIndex);
			}
		} else if (level == Difficulty.EASY) {
			for (int i = numOfRemainingWalls; i > (int) numOfRemainingWalls * 0.5; i--) {
				int wallIndex = rand.nextInt(worldWalls.size());
				int cell1Index = worldWalls.get(wallIndex).cell1Index;
				int cell2Index = worldWalls.get(wallIndex).cell2Index;
				worldGraph.addEdge(cell1Index / columns, cell1Index % columns, cell2Index / columns,
						cell2Index % columns);
				worldWalls.remove(wallIndex);
			}
		} else if (level == Difficulty.NORMAL) {
			for (int i = numOfRemainingWalls; i > (int) numOfRemainingWalls * 0.7; i--) {
				int wallIndex = rand.nextInt(worldWalls.size());
				int cell1Index = worldWalls.get(wallIndex).cell1Index;
				int cell2Index = worldWalls.get(wallIndex).cell2Index;
				worldGraph.addEdge(cell1Index / columns, cell1Index % columns, cell2Index / columns,
						cell2Index % columns);
				worldWalls.remove(wallIndex);
			}
		} else if (level == Difficulty.HARD) {
			for (int i = numOfRemainingWalls; i > (int) numOfRemainingWalls * 0.9; i--) {
				int wallIndex = rand.nextInt(worldWalls.size());
				int cell1Index = worldWalls.get(wallIndex).cell1Index;
				int cell2Index = worldWalls.get(wallIndex).cell2Index;
				worldGraph.addEdge(cell1Index / columns, cell1Index % columns, cell2Index / columns,
						cell2Index % columns);
				worldWalls.remove(wallIndex);
			}
		}
	}

	public void writeJSON() throws FileNotFoundException, UnsupportedEncodingException {

		JSONArray wallList = new JSONArray();

		for (Wall w : worldWalls) {
			JSONObject obj = new JSONObject();
			obj.put("cell1Index", w.cell1Index);
			obj.put("cell2Index", w.cell2Index);
			obj.put("Position", w.position.toString());

			wallList.add(obj);
		}

		JSONObject worldDimension = new JSONObject();

		worldDimension.put("row", numberOfRows);
		worldDimension.put("column", numberOfColumns);

		PrintWriter writer = new PrintWriter("WorldJSON.txt", "UTF-8");
		writer.println(worldDimension);
		writer.print(wallList);
		writer.close();
	}

	public void readJSON() throws FileNotFoundException {

		Scanner scanner = new Scanner(new File("WorldJSON.txt"));

		String dimension = scanner.nextLine();
		String wallArray = scanner.nextLine();

		Object obj = JSONValue.parse(dimension);
		JSONObject js = (JSONObject) obj;
		numberOfRows = new Integer(js.get("row").toString());
		numberOfColumns = new Integer(js.get("column").toString());

		obj = JSONValue.parse(wallArray);
		JSONArray wallList = (JSONArray) obj;
		ArrayList<Wall> tempWall = new ArrayList<>();

		for (Object j : wallList) {
			js = (JSONObject) j;
			int c1 = Integer.parseInt(js.get("cell1Index").toString());
			int c2 = Integer.parseInt(js.get("cell2Index").toString());
			String pos = js.get("Position").toString();

			Wall w;

			if (pos.equals("RIGHT")) {
				w = new Wall(c1, c2, Wall.Position.RIGHT);
			} else {
				w = new Wall(c1, c2, Wall.Position.BOTTOM);
			}

			tempWall.add(w);
		}

		worldWalls = tempWall;
		updateMazeWorld();
	}

	public void updateMazeWorld() {

		GridGraph tempGraph = new GridGraph(numberOfRows, numberOfColumns);

		tempGraph.createCompleteGraph();

		for (Wall i : worldWalls) {

			System.out.println("Cell1: " + i.cell1Index);
			System.out.println("Cell2: " + i.cell2Index);
			System.out.println("Position: " + i.position);
			
			tempGraph.removeEdge(i.cell1Index / numberOfColumns, i.cell1Index % numberOfColumns,
					i.cell2Index / numberOfColumns,i.cell2Index % numberOfColumns);

		}
		
		worldGraph = tempGraph;

	}

	public void generateRandomCheese(int numberOfCheeses, int rows, int columns) {

		cheeseSet = new HashSet<Cheese>();

		while (cheeseSet.size() < numberOfCheeses) {

			Random rand = new Random();
			int i = rand.nextInt(rows);
			int j = rand.nextInt(columns);

			Cheese randomedCheese = new Cheese(i, j);

			if (cheeseSet.add(randomedCheese) == true) {

				worldGraph.cellArray[i][j].cheese = randomedCheese;
				addCheesePerceptionDegree(randomedCheese);

			}
		}
	}

	public void createJerry(int rowIndex, int columnIndex, Jerry concreteJerry) {

		jerry = concreteJerry;
		worldGraph.cellArray[rowIndex][columnIndex].jerry = concreteJerry;

		jerry.currentCell = worldGraph.cellArray[rowIndex][columnIndex];

	}

	public void addCheesePerceptionDegree(Cheese cheese) {

		final int perceptionRange = 5;
		final double maxDistance = Math.sqrt(Math.pow(perceptionRange, 2) + Math.pow(perceptionRange, 2));

		int lowerBoundaryOfRowIndex = (-perceptionRange);
		int upperBoundaryOfRowIndex = perceptionRange + 1;

		int lowerBoundaryOfColumnIndex = (-perceptionRange);
		int upperBoundaryOfColumnIndex = perceptionRange + 1;

		if (perceptionRange > cheese.rowIndex)
			lowerBoundaryOfRowIndex = (-cheese.rowIndex);

		if (perceptionRange + cheese.rowIndex >= numberOfRows)
			upperBoundaryOfRowIndex = numberOfRows - cheese.rowIndex;

		if (perceptionRange > cheese.columnIndex)
			lowerBoundaryOfColumnIndex = (-cheese.columnIndex);

		if (perceptionRange + cheese.columnIndex >= numberOfColumns)
			upperBoundaryOfColumnIndex = numberOfColumns - cheese.columnIndex;

		for (int i = lowerBoundaryOfRowIndex; i < upperBoundaryOfRowIndex; i++) {
			for (int j = lowerBoundaryOfColumnIndex; j < upperBoundaryOfColumnIndex; j++) {

				GridCell targetCell = worldGraph.cellArray[cheese.rowIndex + i][cheese.columnIndex + j];

				double cellDistance = Math.sqrt(Math.pow(targetCell.rowIndex - cheese.rowIndex, 2)
						+ Math.pow(targetCell.columnIndex - cheese.columnIndex, 2));

				targetCell.cheesePerceptionDegree += (maxDistance - cellDistance);

			}
		}
	}

	/// need to modify
	public void removeCheesePerceptionDegree(Cheese cheese) {

		final int perceptionRange = 5;
		final double maxDistance = Math.sqrt(Math.pow(perceptionRange, 2) + Math.pow(perceptionRange, 2));

		for (int i = (-perceptionRange); i < perceptionRange + 1; i++) {
			for (int j = (-perceptionRange); j < perceptionRange + 1; j++) {

				GridCell targetCell = worldGraph.cellArray[cheese.rowIndex + i][cheese.columnIndex + j];

				double cellDistance = Math.sqrt(Math.pow(targetCell.rowIndex - cheese.rowIndex, 2)
						+ Math.pow(targetCell.columnIndex - cheese.columnIndex, 2));

				targetCell.cheesePerceptionDegree -= (maxDistance - cellDistance);

			}
		}
	}

}
