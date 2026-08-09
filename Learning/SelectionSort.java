public class SelectionSort
{
	
	public String sortAlgoName()
	{
		return "Selection Sort";
	}
	
	public void sort(int arr[])
	{
		int size = arr.length;

		//loop in through the complete array
		for (int loop = 0; loop < size-1; loop++)
		{
			// Find the minimum element in unsorted array
			int min_element = loop;
			for (int i = loop+1; i < size; i++)
				if (arr[i] < arr[min_element])
					min_element = i;

			// Swap the found minimum element with the first element
			int temp = arr[min_element];
			arr[min_element] = arr[loop];
			arr[loop] = temp;
			
			printIntermediateArray(loop, arr);
		}
	}

	public void printInitialArray(int arr[])
	{
		System.out.print("Initial Array: [ ");
		int n = arr.length;
		for (int i=0; i<n; ++i)
			System.out.print(arr[i]+" ");
		System.out.println("]");
	}
	
	public void printIntermediateArray(int counter, int arr[])
	{
		int n = arr.length;
		System.out.print("Step - " + (counter+1) + " --> " );
		for (int i=0; i<n; ++i)
			System.out.print(arr[i]+" ");
		System.out.println();
	}
	public void printFinalArray(int arr[])
	{
		System.out.print("\nFinal Array: [ ");
		int n = arr.length;
		for (int i=0; i<n; ++i)
			System.out.print(arr[i]+" ");
		System.out.println("]");
	}

	public static void main( String[] args)
	{
		SelectionSort ob = new SelectionSort();
		int arr[] = {64,25,12,22,11};
		ob.printInitialArray(arr);
		System.out.println("\nSorting using: " + ob.sortAlgoName());
		ob.sort(arr);
		ob.printFinalArray(arr);
	}
}

