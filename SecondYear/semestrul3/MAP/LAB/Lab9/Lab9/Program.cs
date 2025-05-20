using Lab9.decorator;
using Lab9.factory;
using Lab9.model;
using Lab9.model.sorting;

public class Test2
    {
        public static MessageTask[] CreateMessages()
        {
            var msg1 = new MessageTask("1", "feedback lab 2", "Te-ai descurcat bine", "teacher", "student", DateTime.Now);
            var msg2 = new MessageTask("2", "feedback lab 3", "Te-ai descurcat bine", "teacher", "student", DateTime.Now);
            var msg3 = new MessageTask("3", "feedback lab 4", "Te-ai descurcat bine", "teacher", "student", DateTime.Now);
            var msg4 = new MessageTask("4", "feedback lab 5", "Te-ai descurcat bine", "teacher", "student", DateTime.Now);
            var msg5 = new MessageTask("5", "feedback lab 6", "Te-ai descurcat bine", "teacher", "student", DateTime.Now);

            return new[] { msg1, msg2, msg3, msg4, msg5 };
        }

        public static void Main(string[] args)
        {
            var messageTasks = CreateMessages();
            foreach (var messageTask in messageTasks)
            {
                Console.WriteLine(messageTask);
            }

            if (args.Length == 0)
            {
                Console.WriteLine("Please provide a strategy (FIFO or LIFO) as a command-line argument.");
                return;
            }

            var strategy = Enum.Parse<EStrategy>(args[0], true);
            ITaskRunner strategyTaskRunner = new StrategyTaskRunner(strategy);
            
            foreach (var m in messageTasks)
            {
                strategyTaskRunner.addTask(m);
            }
            
            ITaskRunner delayTaskRunner = new DelayTaskRunner(strategyTaskRunner);
            //delayTaskRunner.executeAll();
            ITaskRunner printerTaskRunner = new PrinterTaskRunner(delayTaskRunner);
            printerTaskRunner.executeAll();
        }
    }