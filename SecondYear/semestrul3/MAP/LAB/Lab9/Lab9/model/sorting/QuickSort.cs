namespace Lab9.model.sorting;

public class QuickSort : AbstractSorter
{
    public override void Sort(int[] list)
    {
        QuickSortHelper(list, 0, list.Length - 1);
    }

    private void QuickSortHelper(int[] list, int low, int high)
    {
        if (low < high)
        {
            int pivotIndex = Partition(list, low, high);
            QuickSortHelper(list, low, pivotIndex - 1);
            QuickSortHelper(list, pivotIndex + 1, high);
        }
    }

    private int Partition(int[] list, int low, int high)
    {
        int pivot = list[high];
        int i = low - 1;

        for (int j = low; j < high; j++)
        {
            if (list[j] >= pivot) continue;
            i++;
            int temp = list[i];
            list[i] = list[j];
            list[j] = temp;
        }
        
        int temp1 = list[i + 1];
        list[i + 1] = list[high];
        list[high] = temp1;

        return i + 1;
    }
}