using Task = Lab9.model.Task;

namespace Lab9.decorator;

public interface ITaskRunner
{
    void executeOneTask();
    void executeAll();
    void addTask(Task t);
    bool hasTask();
}