

using System.Text.Json.Serialization;

namespace Networking.rpcprotocol;

[Serializable]
public class Response
{
    [JsonPropertyName("type")]
    public ResponseType Type { get;  set; }
    public object Data { get;  set; }

    public override string ToString()
    {
        return $"Response{{ type={Type}, data={Data} }}";
    }
}
