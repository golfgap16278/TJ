package mainPackage;
import java.util.List;

public class GridCell {
	
	public enum visibleObject{TOM, JERRY, CHEESE, TOM_AND_CHEESE, JERRY_AND_CHEESE, EMPTY_SPACE, NULL};
	
	int rowIndex;
	int columnIndex;
	
	boolean hasNorthEdge;
	boolean hasEastEdge;
	boolean hasWestEdge;
	boolean hasSouthEdge;
	
	double cheesePerceptionDegree;
	double tomPerceptionDegree;
	
	Cheese cheese;
	Jerry jerry;
	Tom tom;
	
	
	//List<GridCell> adjList;
	
	public GridCell(int rowIndex, int columnIndex){
		
		this.rowIndex = rowIndex;
		this.columnIndex = columnIndex;
	}
	
	public GridCell(int rowIndex, int columnIndex, int lastRowIndex, int lastColumnIndex){
		
		this.rowIndex = rowIndex;
		this.columnIndex = columnIndex;
		this.hasNorthEdge = true;
		this.hasEastEdge = true;
		this.hasWestEdge = true;
		this.hasSouthEdge = true;
		
		if(rowIndex == 0)
			this.hasNorthEdge = false;
		if(rowIndex == lastRowIndex)
			this.hasSouthEdge = false;
		if(columnIndex == 0)
			this.hasWestEdge = false;
		if(columnIndex == lastColumnIndex)
			this.hasEastEdge = false;
		
	}
	
//	public boolean hasNorthEdge(){
//		return hasNorthEdge;
//	}
//	
//	public boolean hasSouthEdge(){
//		return hasSouthEdge;
//	}
//	
//	public boolean hasWestEdge(){
//		return hasWestEdge;
//	}
//	
//	public boolean hasEastEdge(){
//		return hasEastEdge;
//	}
}
