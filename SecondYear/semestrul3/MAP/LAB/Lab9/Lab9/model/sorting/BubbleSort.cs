namespace Lab9.model.sorting;

public class BubbleSort : AbstractSorter
{ 
    public BubbleSort() { }
    public override void Sort(int[] list)
    {
        int n = list.Length;
        for (int i = 0; i < n - 1; i++)
        {
            for (int j = 0; j < n - i - 1; j++)
            {
                if (list[j] > list[j + 1])
                {
                    int temp = list[j];
                    list[j] = list[j + 1];
                    list[j + 1] = temp;
                }
            }
        }
    }
}