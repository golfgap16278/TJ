package mainPackage;

public class Wall {
	
	public enum Position{RIGHT,BOTTOM};
	
	int cell1Index;
	int cell2Index;
	
	Position position;
	
	public Wall(int cell1Index,int cell2Index,Position p){
		
		this.cell1Index = cell1Index;
		this.cell2Index = cell2Index;
		this.position = p;
		
	}
}
