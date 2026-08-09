/**
 * Demonstrates the selection sort algorithm on an integer array.
 * The implementation sorts the array in ascending order in place.
 */
public class SelectionSort {

    /**
     * Returns the name of the sorting algorithm.
     *
     * @return name of the algorithm
     */
    public String sortAlgoName() {
        return "Selection Sort";
    }

    /**
     * Sorts the given integer array in ascending order using selection sort.
     *
     * @param values the array to sort; must not be null
     * @throws IllegalArgumentException if the input array is null
     */
    public void sort(int[] values) {
        if (values == null) {
            throw new IllegalArgumentException("Input array cannot be null.");
        }

        int size = values.length;

        for (int currentIndex = 0; currentIndex < size - 1; currentIndex++) {
            int minIndex = currentIndex;

            for (int searchIndex = currentIndex + 1; searchIndex < size; searchIndex++) {
                if (values[searchIndex] < values[minIndex]) {
                    minIndex = searchIndex;
                }
            }

            if (minIndex != currentIndex) {
                int temp = values[currentIndex];
                values[currentIndex] = values[minIndex];
                values[minIndex] = temp;
            }

            printIntermediateArray(currentIndex, values);
        }
    }

    /**
     * Prints the initial array before sorting begins.
     *
     * @param values the array to display
     */
    public void printInitialArray(int[] values) {
        System.out.print("Initial Array: ");
        printArrayValues(values);
        System.out.println();
    }

    /**
     * Prints the current state of the array during the sort.
     *
     * @param counter the current pass number
     * @param values the array to display
     */
    public void printIntermediateArray(int counter, int[] values) {
        System.out.print("Step - " + (counter + 1) + " --> ");
        printArrayValues(values);
        System.out.println();
    }

    /**
     * Prints the final sorted array.
     *
     * @param values the array to display
     */
    public void printFinalArray(int[] values) {
        System.out.print("\nFinal Array: ");
        printArrayValues(values);
        System.out.println();
    }

    /**
     * Prints the contents of the array in a readable format.
     *
     * @param values the array to display
     */
    private void printArrayValues(int[] values) {
        System.out.print("[ ");
        for (int index = 0; index < values.length; index++) {
            if (index > 0) {
                System.out.print(" ");
            }
            System.out.print(values[index]);
        }
        System.out.print(" ]");
    }

    /**
     * Runs a small demonstration of the selection sort algorithm.
     *
     * @param args command-line arguments (unused)
     */
    public static void main(String[] args) {
        SelectionSort sortDemo = new SelectionSort();
        int[] values = {64, 25, 12, 22, 11};

        sortDemo.printInitialArray(values);
        System.out.println("\nSorting using: " + sortDemo.sortAlgoName());
        sortDemo.sort(values);
        sortDemo.printFinalArray(values);
    }
}

