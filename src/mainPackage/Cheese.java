package mainPackage;

public class Cheese {
	
	int rowIndex;
	int columnIndex;
	
	
	public Cheese(int row, int column) {
		
		this.rowIndex = row;
		this.columnIndex = column;
		
	}
	
	@Override
    public boolean equals(Object o) {
        
		Cheese c = (Cheese)o;
		
		if(c.rowIndex == this.rowIndex & c.columnIndex == this.columnIndex)
			return true;

        return false;
    }
	
	@Override
    public int hashCode() {
       return 100*rowIndex + columnIndex;
    }

}
