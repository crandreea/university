using System.ComponentModel;
using Task = Lab9.model.Task;

namespace Lab9.decorator;

public abstract class AbstractTaskRunner : ITaskRunner
{
    private readonly ITaskRunner _taskRunner;

    protected AbstractTaskRunner(ITaskRunner taskRunner)
    {
        _taskRunner = taskRunner;
    }

    public virtual void executeOneTask()
    {
        _taskRunner.executeOneTask();
    }

    public void executeAll()
    {
        while (hasTask())
        {
            executeOneTask();
        }
    }

    public void addTask(Task t)
    {
        _taskRunner.addTask(t);
    }

    public bool hasTask()
    {
        return _taskRunner.hasTask();
    }
    
}