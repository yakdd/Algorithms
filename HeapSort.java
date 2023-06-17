import java.util.Arrays;
import java.util.Random;

public class HeapSort {
    public static void main(String[] args) {

        int[] array = new int[new Random().nextInt(10, 20)];
        for (int i = 0; i < array.length; i++) {
            array[i] = new Random().nextInt(20);
        }
        System.out.println(Arrays.toString(array));

        heapSort(array);
        System.out.println(Arrays.toString(array));
    }

    public static void heapSort(int[] array) {
        int iteration = 0;
        heapSorting(array, iteration);
    }

    public static void heapSorting(int[] array, int iteration) {
        int arrLength = array.length - iteration;
        int lastIndex = arrLength - 1;
        if (lastIndex == 0)
            return;

        int lastParentIndex = arrLength / 2 - 1;

        for (int i = lastParentIndex; i >= 0; i--) {
            int firstChildIndex = 2 * i + 1;
            int secondChildIndex = 2 * i + 2;
            int changeElementIndex; // индекс элемента для возможного обмена с родителем
            if (secondChildIndex > lastIndex) // если второго ребенка нет, то:
                changeElementIndex = firstChildIndex;
            else {
                changeElementIndex = array[firstChildIndex] > array[secondChildIndex]
                        ? firstChildIndex
                        : secondChildIndex;
            }

            if (array[changeElementIndex] > array[i]) {
                int temp = array[i];
                array[i] = array[changeElementIndex];
                array[changeElementIndex] = temp;
            }
        }

        int temp2 = array[0];
        array[0] = array[lastIndex];
        array[lastIndex] = temp2;
        iteration++;

        heapSorting(array, iteration);
    }
}
