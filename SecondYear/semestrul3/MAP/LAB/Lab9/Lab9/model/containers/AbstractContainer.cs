using System.ComponentModel;

namespace Lab9.model.containers;

public abstract class AbstractContainer : IContainer
{
    protected List<Task> myTasks = new();
    protected Task[] Tasks;
    protected int size;

    public AbstractContainer()
    {
        size = 0;
        Tasks = new Task[10];
    }

    public abstract Task Remove();

    public void Add(Task task)
    {
        if (size == Tasks.Length)
        {
            Task[] newTasks = new Task[size * 2];
            Array.Copy(Tasks, newTasks, size);
            Tasks = newTasks;
        }
        Tasks[size] = task ?? throw new ArgumentNullException(nameof(task));
        size++;
    }

    public int Size()
    {
        return size;
    }

    public bool IsEmpty()
    {
        return size == 0;
    }
}