using System.Globalization;

namespace Lab9.model;

public class MessageTask : Task
{
    private string message;
    private string from;
    private string to;
    private DateTime date;

    private static readonly string dateFormat = "yyyy-MM-dd HH:mm";

    public MessageTask(string taskId, string description, string message, string from, string to, DateTime date)
        : base(taskId, description)
    {
        this.message = message ?? throw new ArgumentNullException(nameof(message));
        this.from = from ?? throw new ArgumentNullException(nameof(from));
        this.to = to ?? throw new ArgumentNullException(nameof(to));
        this.date = date;
    }

    public override void Execute()
    {
        Console.WriteLine($"{date.ToString(dateFormat)}: {message}");
    }

    public override string ToString()
    {
        return $"id = {Id} | description = {Description} | message = {message} | from = {from} | to = {to} | date = {date.ToString(dateFormat)}";
    }

}