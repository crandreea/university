using System.ComponentModel;
using Lab9.model.containers;
using IContainer = Lab9.model.containers.IContainer;

namespace Lab9.factory;

public class TaskContainerFactory : IFactory
{
    private static readonly TaskContainerFactory _instance = new TaskContainerFactory();
    private TaskContainerFactory()
    {
    }

    public static TaskContainerFactory Instance => _instance;
    
    public IContainer createContainer(EStrategy strategy)
    {
        return strategy switch
        {
            EStrategy.FIFO => new QueueContainer(),
            EStrategy.LIFO => new StackContainer(),
            _ => throw new ArgumentException("Invalid strategy", nameof(strategy))
        };
    }
}