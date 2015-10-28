package mainPackage;

public class DisjointSet {
	
	
	int[] set;
	int[] sizes;
	int size;
	
	
	public DisjointSet(int size){
		
		this.set = new int[size];
		for(int i=0 ; i<size ; i++){ this.set[i] = i;}
		this.sizes = new int[size];
		for(int i=0 ; i<size ; i++){ this.sizes[i] = 1;}
		this.size = size;
		
	}
	
	public int find(int item){
		
		int root = item;
		while(set[root] != root){
			root = set[root];
		}
		
		int current = item;
		while(set[current] != root){
			set[current] = root;
		}

		return root;
	}
	
	public int join(int item1, int item2){
		
		int group1 = find(item1);
		int group2 = find(item2);
		size--;
		
		if(sizes[group1] > sizes[group2]){
			set[group2] = group1;
			sizes[group1] = sizes[group1] + sizes[group2];
			return group1;
		}else{
			set[group1] = group2;
			sizes[group2] = sizes[group2] + sizes[group1];
			return group2;
		}
	}
}






























