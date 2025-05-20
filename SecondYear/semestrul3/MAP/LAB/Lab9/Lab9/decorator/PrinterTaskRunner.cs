namespace Lab9.decorator;

public class PrinterTaskRunner : AbstractTaskRunner
{
    private static readonly string TimeFormat = "HH:mm";
    
    public PrinterTaskRunner(ITaskRunner taskRunner) : base(taskRunner)
    { }
    
    public override void executeOneTask()
    {
        base.executeOneTask();
        Console.WriteLine($"Task executed at: {DateTime.Now.ToString(TimeFormat)}");
    }
}