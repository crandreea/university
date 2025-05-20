namespace Lab9.model.containers;

public class QueueContainer : AbstractContainer
{
    public QueueContainer() : base()
    {
    }
    
    public override Task Remove()
    {
        if (IsEmpty())
        {
            throw new InvalidOperationException("The container is empty.");
        }

        Task first = Tasks[0];

        for (int i = 1; i < size; i++)
        {
            Tasks[i - 1] = Tasks[i];
        }

        Tasks[--size] = null; 
        return first;
    }
    
}