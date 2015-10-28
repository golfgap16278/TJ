package mainPackage;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import mainPackage.GridCell.visibleObject;
import mainPackage.MazeGame.MyWorker;

public abstract class Jerry implements Movable {
	
	final int visionRange = 1;
	
	protected GridCell currentCell;
	protected GridCell tempCell;
	
	visibleObject visiblility[][]; 

	
	public Jerry(){
		
	}
	
	public Jerry(GridCell cell){
		
		currentCell = cell;
		
	}
	
	
	/// don't forget to change back to protected
	public visibleObject[][] getVisibility() {

		visibleObject visiblility[][] = new GridCell.visibleObject[2 * visionRange + 1][2 * visionRange + 1];
		GridCell[][] cellArray = MazeGame.worldGraph.cellArray;

		for (int i = (-visionRange); i < visionRange + 1; i++) {
			for (int j = (-visionRange); j < visionRange + 1; j++) {

				GridCell cell = MazeGame.worldGraph.cellArray[currentCell.rowIndex + i][currentCell.columnIndex + j];

				for (int k = currentCell.rowIndex - visionRange; k < currentCell.rowIndex + visionRange + 1; k++) {
					for (int l = (currentCell.columnIndex - visionRange); l < currentCell.columnIndex + visionRange
							+ 1; l++) {

						if (!cellArray[k][l].hasEastEdge) {

							Boolean isIntersected = isIntersected(
									currentCell.columnIndex + j + 0.5,
									currentCell.rowIndex + i + 0.5,
									currentCell.columnIndex + 0.5, 
									currentCell.rowIndex + 0.5, 
									cellArray[k][l].columnIndex + 1,
									cellArray[k][l].rowIndex, 
									cellArray[k][l].columnIndex + 1,
									cellArray[k][l].rowIndex + 1);

							if (isIntersected != null) {
								if (isIntersected.booleanValue()) {
									visiblility[i + visionRange][j + visionRange] = visibleObject.NULL;
								}

							}
						}
						
						if (!cellArray[k][l].hasSouthEdge) {

							Boolean isIntersected = isIntersected(currentCell.columnIndex + j + 0.5, currentCell.rowIndex + i + 0.5,
									currentCell.columnIndex + 0.5, currentCell.rowIndex + 0.5, cellArray[k][l].columnIndex,
									cellArray[k][l].rowIndex +1 , cellArray[k][l].columnIndex + 1,
									cellArray[k][l].rowIndex + 1);

							if (isIntersected != null) {
								if (isIntersected.booleanValue()) {
									visiblility[i + visionRange][j + visionRange] = visibleObject.NULL;
								}

							}
						}

					}
				}
				
//				if(visiblility[i + visionRange][j + visionRange] != visibleObject.NULL){
//
//					if (cell.cheese != null) {
//						if (cell.jerry != null) {
//							visiblility[i + visionRange][j + visionRange] = visibleObject.JERRY_AND_CHEESE;
//						} else if (cell.tom != null) {
//							visiblility[i + visionRange][j + visionRange] = visibleObject.TOM_AND_CHEESE;
//						} else {
//							visiblility[i + visionRange][j + visionRange] = visibleObject.CHEESE;
//						}
//					} else if (cell.jerry != null) {
//						visiblility[i + visionRange][j + visionRange] = visibleObject.JERRY;
//					} else if (cell.tom != null) {
//						visiblility[i + visionRange][j + visionRange] = visibleObject.TOM;
//					} else {
//						visiblility[i + visionRange][j + visionRange] = visibleObject.EMPTY_SPACE;
//					}
//				}
			}
		}

		this.visiblility = visiblility;
		return visiblility;
	}
	
	
	protected double getCheesePerceptionDegree(){
		
		return currentCell.cheesePerceptionDegree;
	}
	
	
	protected double getTomPerceptionDegree(){
		
		return currentCell.tomPerceptionDegree;
	}
	

	protected boolean stepToTheNorth() {
		
		if(currentCell.hasNorthEdge){
			
			tempCell = currentCell;
			currentCell = MazeGame.worldGraph.cellArray[currentCell.rowIndex-1][currentCell.columnIndex];
			return true;
		}

		return false;
	}
	

	protected boolean stepToTheSouth() {
		
		if(currentCell.hasSouthEdge){
			
			tempCell = currentCell;
			currentCell = MazeGame.worldGraph.cellArray[currentCell.rowIndex+1][currentCell.columnIndex];
			return true;
		}

		return false;
	}
	

	protected boolean stepToTheWest() {
		
		if(currentCell.hasWestEdge){
			
			tempCell = currentCell;
			currentCell = MazeGame.worldGraph.cellArray[currentCell.rowIndex][currentCell.columnIndex-1];
			return true;
		}

		return false;
	}
	

	protected boolean stepToTheEast() {
		
		if(currentCell.hasEastEdge){
			
			tempCell = currentCell;
			currentCell = MazeGame.worldGraph.cellArray[currentCell.rowIndex][currentCell.columnIndex+1];
			return true;
		}

		return false;
	}
	

	@Override
	public abstract void move();
	
	
	
	
	
	public static double[] findIntersectPoint(Point P1, Point P2, Point P3, Point P4){
		
		double A1 = P2.y - P1.y; 
		double B1 = P1.x - P2.x;
		double C1 = A1*P1.x + B1*P1.y;
		
		double A2 = P4.y - P3.y; 
		double B2 = P3.x - P4.x;
		double C2 = A1*P3.x + B1*P3.y;
		
		double det = A1*B2 - A2*B1;
		
		if(det == 0){ //Lines are parallel
			return null;
		}else{
			double intersectPoint[] = new double[2]; 
			intersectPoint[0] = (B2*C1 - B1*C2)/det;
			intersectPoint[1] = (A1*C2 - A2*C1)/det;
			return intersectPoint;
		}
	}
	
	
	public static Boolean isIntersected(double x1, double y1, double x2, double y2, double x3, double y3, double x4,
			double y4) {

		// sorting point x1<=x2, x3<=x4, y1<=y2, y3<=y4

//		if (x1 > x2) {
//			double temp = x1;
//			x1 = x2;
//			x2 = temp;
//		}
//
//		if (x3 > x4) {
//			double temp = x3;
//			x3 = x4;
//			x4 = temp;
//		}
//
//		if (y1 > y2) {
//			double temp = y1;
//			y1 = y2;
//			y2 = temp;
//		}
//
//		if (y3 > y4) {
//			double temp = y3;
//			y3 = y4;
//			y4 = temp;
//		}

		// now we check whether these 2 lines are vertical, horizontal, or diagonal

		if (x1 == x2) { // vertical
			if (x3 == x4) { // both are vertical
				// it will never touch each other
				return false;
			} else if (y3 == y4) { // vertical vs horizontal

				// possible
				// maybe we don't need to construct the equation
				
				if (x3 > x4) {
					double temp = x3;
					x3 = x4;
					x4 = temp;
				}
				
				if (y1 > y2) {
					double temp = y1;
					y1 = y2;
					y2 = temp;
				}
				
				
				if(x1 >= x3 & x1 <= x4){
					if(y3 >= y1 & y3 <= y2)
						return true;
				}
				
				return false;
				
			} else { // vertical vs diagonal
				
				

				double m = (y3 - y4) / (x3 - x4);
				double c = y3 - m * x3;
				
				if (x3 > x4) {
					double temp = x3;
					x3 = x4;
					x4 = temp;
				}
				
				if (y1 > y2) {
					double temp = y1;
					y1 = y2;
					y2 = temp;
				}

				if (x1 >= x3 & x1 <= x4) {

					double y = m * x1 + c;

					if (y >= y1 & y <= y2)
						return true;

				} else {
					return false;
				}

				return false;
			}

		} else if (y1 == y2) { // horizontal
			if(x3 == x4){  //horizontal vs vertical
				
				if (x1 > x2) {
					double temp = x1;
					x1 = x2;
					x2 = temp;
				}
				
				if (y3 > y4) {
					double temp = y3;
					y3 = y4;
					y4 = temp;
				}
				
				if(x3 >= x1 & x3 <= x2){
					if(y1 >= y3 & y1 <= y4)
						return true;
				}
				
				return false;
			}else if(y3 == y4){ //horizontal vs horizontal
				//never touch
				return false;
			}else{ //horizontal vs diagonal
				//impossible
				return null;
			}

		} else { // diagonal

			if (x3 == x4) { // diagonal vs vertical

				double m = (y1 - y2) / (x1 - x2);
				double c = y1 - m * x1;
				
				if (x1 > x2) {
					double temp = x1;
					x1 = x2;
					x2 = temp;
				}
				
				if (y3 > y4) {
					double temp = y3;
					y3 = y4;
					y4 = temp;
				}

				if (x3 >= x1 & x3 <= x2) {

					double y = m * x3 + c;

					if (y >= y3 & y <= y4)
						return true;

				}else
					return false;
				
				return false;

			} else if (y3 == y4) { // diagonal vs horizontal
				
				
				double m = (y1 - y2) / (x1 - x2);
				double c = y1 - m * x1;
				
				if (y1 > y2) {
					double temp = y1;
					y1 = y2;
					y2 = temp;
				}
				
				if (x3 > x4) {
					double temp = x3;
					x3 = x4;
					x4 = temp;
				}

				
				if (y3 >= y1 & y3 <= y2) {

					double x = (y3-c)/m;

					if (x >= x3 & x <= x4)
						return true;

				} else {
					return false;
				}

				return false;

			} else { //diagonal vs diagonal
				
				//impossible case
				return null;
			}

		}

		// construct an equation of the first line(x1,y1,x2,y2)
		// y = mx + c

//		double m = (y1 - y2) / (x1 - x2);
//		double c = y1 - m * x1;
//
//			if (x3 >= x1 & x3 <= x2) {
//
//				double y = m * x3 + c;
//
//				if (y >= y3 & y <= y4)
//					return true;
//
//			} else {
//				return false;
//			}
//
//
//		return null;
	}

}
