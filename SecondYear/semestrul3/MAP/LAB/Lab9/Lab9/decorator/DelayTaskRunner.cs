namespace Lab9.decorator;

public class DelayTaskRunner : AbstractTaskRunner
{
    public DelayTaskRunner(ITaskRunner taskRunner) : base(taskRunner)
    {
    }

    public override void executeOneTask()
    {
        try
        {
            Thread.Sleep(3000); 
            base.executeOneTask(); 
        }
        catch (ThreadInterruptedException e)
        {
            Console.WriteLine($"Task execution was interrupted: {e.Message}");
        }
    }
}