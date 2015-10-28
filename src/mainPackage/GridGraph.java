package mainPackage;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;

public class GridGraph {
	
	GridCell[][] cellArray;
	

	public GridGraph(int rows, int columns) {
		
		cellArray = new GridCell[rows][columns];
		
	}
	
	public void addNode(int rowIndex, int columnIndex) {
		
		cellArray[rowIndex][columnIndex] = new GridCell(rowIndex, columnIndex);
		
	}

	public boolean hasNode(int rowIndex, int columnIndex) {

			if (cellArray[rowIndex][columnIndex] != null) {
				return true;
			}

		return false;
	}

	public void addEdge(int row1, int column1, int row2, int column2) {

		if (row1 - 1 == row2 & column1 == column2) {
			
			cellArray[row1][column1].hasNorthEdge = true;
			cellArray[row2][column2].hasSouthEdge = true;
			
		} else if (row1 + 1 == row2 & column1 == column2) {
			
			cellArray[row1][column1].hasSouthEdge = true;
			cellArray[row2][column2].hasNorthEdge = true;
			
		} else if (row1 == row2 & column1 - 1 == column2) {
			
			cellArray[row1][column1].hasWestEdge = true;
			cellArray[row2][column2].hasEastEdge = true;
			
		} else if (row1 == row2 & column1 + 1 == column2) {
			
			cellArray[row1][column1].hasEastEdge = true;
			cellArray[row2][column2].hasWestEdge = true;
			
		}
	}

//	public boolean hasEdge(GridCell node1, GridCell node2) {
//
//		if (node1.rowIndex - 1 == node2.rowIndex & node1.columnIndex == node2.columnIndex) {
//			return node1.hasNorthEdge;
//		} else if (node1.rowIndex + 1 == node2.rowIndex & node1.columnIndex == node2.columnIndex) {
//			return node1.hasSouthEdge;
//		} else if (node1.rowIndex == node2.rowIndex & node1.columnIndex - 1 == node2.columnIndex) {
//			return node1.hasWestEdge;
//		} else if (node1.rowIndex == node2.rowIndex & node1.columnIndex + 1 == node2.columnIndex) {
//			return node1.hasEastEdge;
//		}
//
//		return false;
//	}

//	public List<GridCell> getNeighbors(GridCell cell) {
//
//		if (cell.adjList == null) {
//
//			List<GridCell> adjList = new LinkedList<GridCell>();
//
//			if (cell.hasNorthEdge == true)
//				adjList.add(cellArray[cell.rowIndex-1][cell.columnIndex]);
//
//			if (cell.hasSouthEdge == true)
//				adjList.add(cellArray[cell.rowIndex+1][cell.columnIndex]);
//
//			if (cell.hasWestEdge == true)
//				adjList.add(cellArray[cell.rowIndex][cell.columnIndex-1]);
//
//			if (cell.hasEastEdge == true)
//				adjList.add(cellArray[cell.rowIndex][cell.columnIndex+1]);
//
//			cell.adjList = adjList;
//
//		}
//		
//		return cell.adjList;
//	}

}
