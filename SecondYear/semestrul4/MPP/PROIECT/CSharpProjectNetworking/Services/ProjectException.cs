
using System.Runtime.Serialization;

namespace Services;

public class ProjectException : Exception
{
    public ProjectException() : base()
    {
    }

    public ProjectException(string message) : base(message)
    {
    }

    public ProjectException(string message, Exception innerException) : base(message, innerException)
    {
    }
    
    public ProjectException(SerializationInfo info, StreamingContext context) : base(info, context)
    {
    }
}
