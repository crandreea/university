
using System.Text.Json.Serialization;

namespace Networking.rpcprotocol;

[Serializable]
public class Request
{
    [JsonPropertyName("type")]
    public RequestType Type { get;  set; }
    public object Data { get;  set; }

    public override string ToString()
    {
        return $"Request{{ type={Type}, data={Data} }}";
    }
}
