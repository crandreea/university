using Lab9.factory;
using Lab9.model.containers;
using Task = Lab9.model.Task;

namespace Lab9.decorator;

public class StrategyTaskRunner : ITaskRunner
{
    private readonly IContainer _container;
    
    public StrategyTaskRunner(EStrategy strategy) {
        _container = TaskContainerFactory.Instance.createContainer(strategy);
    }
    public void executeOneTask()
    {
        model.Task task = _container.Remove();
        task?.Execute();
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
        _container.Add(t);
    }

    public bool hasTask()
    {
        return !_container.IsEmpty();
    }
}