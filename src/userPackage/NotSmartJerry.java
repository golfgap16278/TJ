package userPackage;
import java.io.EOFException;
import java.util.List;
import java.util.Random;

import org.omg.CORBA.Current;

import mainPackage.GridCell;
import mainPackage.GridGraph;

public class NotSmartJerry {
	
	enum searchAlgorithm{BFS, DFS, BLINDSEARCH, RANDOMSEARCH};
	
	public int rowIndex;
	public int columnIndex;
	
	public GridCell currentCell;
	public GridCell oldCell;
	
	public GridGraph jerryWorld;
	
	public NotSmartJerry(int rowIndex, int columnIndex){
		
		this.rowIndex = rowIndex;
		this.columnIndex = columnIndex;
		
		//jerryWorld = new GridGraph();
		//jerryWorld.addNode(new GridCell(rowIndex, columnIndex));
		
	}
	
	public NotSmartJerry(GridCell cell){
		
		currentCell = cell;
		
	}
	
	public void move(){
		
	}
	
	public void updateJerryWorld(){
		
	}
	
//	public void updateJerryWorld(GridGraph worldGraph){
//		
//		List<GridCell> neighbor = worldGraph.getNeighbors(worldGraph.getNode(this.rowIndex, this.columnIndex));
//
//		for (GridCell node : neighbor) {
//
//			if (!jerryWorld.hasNode(jerryWorld.getNode(node.rowIndex, node.columnIndex))) {
//				jerryWorld.addNode(new GridCell(node.rowIndex, node.columnIndex));
//			}
//
//			jerryWorld.addEdge(jerryWorld.getNode(this.rowIndex, this.columnIndex),
//					jerryWorld.getNode(node.rowIndex, node.columnIndex));
//
//		}
//	}
	
	
//	public void move(){
//		
//		GridCell tempNode = new GridCell(this.rowIndex, this.columnIndex);
//		
//		List<GridCell> neighbor = jerryWorld.getNeighbors(jerryWorld.getNode(this.rowIndex, this.columnIndex));
//		
//		if(oldNode == null){
//			
//			Random rand = new Random();
//			int nextCell = rand.nextInt(neighbor.size());
//			
//			this.rowIndex = neighbor.get(nextCell).rowIndex;
//			this.columnIndex = neighbor.get(nextCell).columnIndex;
//			
//			oldNode = tempNode;
//			return;
//			
//		}else if(neighbor.size() == 1){// dead end
//			
//			this.rowIndex = neighbor.get(0).rowIndex;
//			this.columnIndex = neighbor.get(0).columnIndex;
//			
//			oldNode = tempNode;
//			return;
//			
//		}else{
//
//			Random rand = new Random();
//			int nextCell = rand.nextInt(neighbor.size());
//
//			while (oldNode.rowIndex == neighbor.get(nextCell).rowIndex
//					& oldNode.columnIndex == neighbor.get(nextCell).columnIndex) {
//
//				nextCell = rand.nextInt(neighbor.size());
//			}
//			
//			if(this.rowIndex == oldNode.rowIndex & this.columnIndex == oldNode.columnIndex+1){
//				//going to the right
//				if(jerryWorld.getNode(this.rowIndex, this.columnIndex).hasEastEdge){
//					
//					if(neighbor.size()>2){
//						this.rowIndex = neighbor.get(nextCell).rowIndex;
//						this.columnIndex = neighbor.get(nextCell).columnIndex;
//					}else{
//						this.columnIndex++;
//					}
//					
//					oldNode = tempNode;
//					return;
//				}
//				
//			}else if(this.rowIndex == oldNode.rowIndex & this.columnIndex == oldNode.columnIndex-1){
//				//going to the left
//				if(jerryWorld.getNode(this.rowIndex, this.columnIndex).hasWestEdge){
//					
//					if(neighbor.size()>2){
//						this.rowIndex = neighbor.get(nextCell).rowIndex;
//						this.columnIndex = neighbor.get(nextCell).columnIndex;
//					}else{
//						this.columnIndex--;
//					}
//					
//					oldNode = tempNode;
//					return;
//				}
//				
//			}else if(this.rowIndex == oldNode.rowIndex+1 & this.columnIndex == oldNode.columnIndex){
//				//going down
//				if(jerryWorld.getNode(this.rowIndex, this.columnIndex).hasSouthEdge){
//					
//					if(neighbor.size()>2){
//						this.rowIndex = neighbor.get(nextCell).rowIndex;
//						this.columnIndex = neighbor.get(nextCell).columnIndex;
//					}else{
//						this.rowIndex++;
//					}
//					
//					oldNode = tempNode;
//					return;
//				}
//				
//			}else if(this.rowIndex == oldNode.rowIndex-1 & this.columnIndex == oldNode.columnIndex){
//				//going up
//				if(jerryWorld.getNode(this.rowIndex, this.columnIndex).hasNorthEdge){
//					
//					if(neighbor.size()>2){
//						this.rowIndex = neighbor.get(nextCell).rowIndex;
//						this.columnIndex = neighbor.get(nextCell).columnIndex;
//					}else{
//						this.rowIndex--;
//					}
//					
//					oldNode = tempNode;
//					return;
//				}
//			}
//
//			this.rowIndex = neighbor.get(nextCell).rowIndex;
//			this.columnIndex = neighbor.get(nextCell).columnIndex;
//
//			oldNode = tempNode;
//		
//		}
//
//	}
	
	
	void breadthFirstSearch(GridGraph g, GridCell v){
		
		
		
		
		
		
	}
	
	
	void blindSearch(){
		
		
		
		
		
	}
	
//	void randomSearch(){
//		
//		GridCell tempNode = new GridCell(this.rowIndex, this.columnIndex);
//		
//		List<GridCell> neighbor = jerryWorld.getNeighbors(jerryWorld.getNode(this.rowIndex, this.columnIndex));
//
//		Random rand = new Random();
//		int nextCell = rand.nextInt(neighbor.size());
//		
//		this.rowIndex = neighbor.get(nextCell).rowIndex;
//		this.columnIndex = neighbor.get(nextCell).columnIndex;
//		
//		oldNode = tempNode;
//		
//	}
	
	
	
	
	

}
