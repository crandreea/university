namespace Lab9.model.containers;

public class StackContainer : AbstractContainer
{
    public override Task Remove()
    {
        if (!IsEmpty())
        {
            Task lastTask = Tasks[--size];
            Tasks[size] = null; 
            return lastTask;
        }
        else
        {
            return null;
        }
    }
}