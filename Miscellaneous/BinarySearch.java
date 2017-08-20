public class BinarySearch {

	public static int search(int[] item, int findThis){
		if(item == null) 
			throw new IllegalArgumentException();
		int i = 0;
		int j = item.length -1;
		while(i < j){
			int m = (i+j) / 2; //tear them into half
			if(findThis > item[m])
				i = m + 1; //Go through the other half.
			else{
				j = m;
			}
		}
		int location;
		if(findThis == item[i]){
			location = i;
		}
		else{
			location = 0;
		}
		
		
		return location;
	}
	
	
	public static void main(String[] args) {
		int [] searchListSorted = {11,23,45,78,90,95,103,108,109,200,205,250};
		int i = BinarySearch.search(searchListSorted,23);
		System.out.println(i);
		
	}

}
