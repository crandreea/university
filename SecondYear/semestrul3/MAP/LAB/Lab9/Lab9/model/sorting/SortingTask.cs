namespace Lab9.model.sorting;

public class SortingTask : Task
{
    private readonly int[] list;
    private readonly SortingStrategy strategy;
    private AbstractSorter sorter;

    public SortingTask(string id, string description, int[] list, SortingStrategy strategy)
        : base(id, description)
    {
        this.list = list ?? throw new ArgumentNullException(nameof(list));
        this.strategy = strategy;
    }

    public override void Execute()
    {
        switch (strategy)
        {
            case SortingStrategy.Bubblesort:
                sorter = new BubbleSort();
                break;
            case SortingStrategy.Quicksort:
                sorter = new QuickSort();
                break;
            default:
                Console.WriteLine("Invalid sorting strategy");
                return;
        }

        sorter.Sort(list);
        Console.WriteLine("Sorted list: " + string.Join(", ", list));
    }

}