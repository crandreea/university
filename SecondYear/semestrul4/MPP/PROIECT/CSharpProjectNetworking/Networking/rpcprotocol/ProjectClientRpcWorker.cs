
using System.Net.Sockets;
using System.Text;
using System.Text.Json;
using System.Text.Json.Serialization;
using log4net;
using Model;
using Services;

namespace Networking.rpcprotocol;

public class ProjectClientRpcWorker : IProjectObserver
{
    private static readonly ILog logger = LogManager.GetLogger(typeof(ProjectClientRpcWorker));
    private readonly IProjectServices server;
    private readonly TcpClient connection;
    
    private readonly NetworkStream stream;
    private readonly StreamReader input;
    private readonly StreamWriter output;
    
    private volatile bool connected;
    private JsonSerializerOptions options;

    public ProjectClientRpcWorker(IProjectServices server, TcpClient connection)
    {
        this.server = server;
        this.connection = connection;
        stream = connection.GetStream();
        
        input = new StreamReader(stream);
        output = new StreamWriter(stream);
        
        connected = true;
        options = new JsonSerializerOptions
        {
            Converters = { new JsonStringEnumConverter() }
        };
        
    }

    public virtual void Run()
    {
        while (connected)
        {
            try
            {
                string line = input.ReadLine();
                Request request = JsonSerializer.Deserialize<Request>(line, options);  
                logger.Debug($"Received request from client: {request}");
                Response response = HandleRequest(request);
                if (response != null)
                {
                    SendResponse(response);
                    logger.Debug("Sent response to client.");
                }
            }
            catch (Exception e)
            {
                logger.Error("Error while handling client request: ", e);
                Console.Out.WriteLine("Error while handling request(Run loop)" + e.Message);
                connected = false;
            }

            try
            {
                Thread.Sleep(1000); 
            }catch (Exception e)
            {
                logger.Error(e.StackTrace);
            }
            
        }

        try
        {
            stream.Close();
            input.Close();
            output.Close();
            connection.Close();
            logger.Info($"Connection  closed.");
        }
        catch (Exception e)
        {
            logger.Warn("Error closing connection: ", e);
        }
    }
    
    private  void SendResponse(Response response)
    {
        string json = JsonSerializer.Serialize(response, options);
        lock (output)
        {
            try
            {
                output.WriteLine(json);
                output.Flush();
            }
            catch (Exception e)
            {
                logger.Error("Error writing response to client: ", e);
            }
        }
    }

    public virtual void InscriereAdded(Inscriere inscriere)
    {
        logger.Info($"Notifying client about participant inscription update.");
        Response response = new Response { Type = ResponseType.UPDATE_INSCRIERE, Data = inscriere };
        try
        {
            SendResponse(response);
        }
        catch (Exception e)
        {
            logger.Error("Error sending update notification: ", e);
        }
    }

    public static T? Deserialize<T>(object? data)
    {
        if (data is JsonElement element)
            return JsonSerializer.Deserialize<T>(element.GetRawText());
        
        return (T?)data;
    }
    
    
    private static Response okResponse = new Response{Type = ResponseType.OK, Data = null};
    private Response HandleRequest(Request request)
    {
        Response response;
        try
        {
            switch (request.Type)
            {
                case RequestType.LOGIN:
                    logger.Info("Processing LOGIN request");
                    var org = Deserialize<Organizator>(request.Data);
                    var loggedOrg = server.Login(org.Username, org.Password, this);
                    response = new Response { Type = ResponseType.OK, Data = loggedOrg };
                    break;

                case RequestType.LOGOUT:
                    logger.Info("Processing LOGOUT request");
                    var orgLogout = Deserialize<Organizator>(request.Data);
                    server.Logout(orgLogout, this);
                    response = okResponse;
                    break;

                case RequestType.GET_PROBE:
                    logger.Info("Processing GET_PROBE request");
                    var probes = server.GetAllProba();
                    response = new Response { Type = ResponseType.GET_PROBE, Data = probes };
                    break;

                case RequestType.GET_ORGANIZATORI:
                    logger.Info("Processing GET_ORGANIZATORI request");
                    var organizatori = server.GetAllOrganizatori();
                    response = new Response { Type = ResponseType.GET_ORGANIZATORI, Data = organizatori };
                    break;
                    
                case RequestType.SEARCH_PARTICIPANTS:
                    logger.Info("Processing SEARCH_PARTICIPANTS request");
    
                    var data = Deserialize<JsonElement[]>(request.Data);
                    Proba selectedProba = Deserialize<Proba>(data[0]);
                    CategorieVarsta selectedCategory = Deserialize<CategorieVarsta>(data[1]);
                    IList<ParticipantDTO> participants = server.SearchParticipants(selectedProba, selectedCategory);
                    response = new Response { Type = ResponseType.SEARCH_PARTICIPANTS, Data = participants };
                    break;

                case RequestType.GET_PROBE_DTO:
                    logger.Info("Processing GET_PROBE_DTO request");
                    Console.Out.WriteLine("Processing GET_PROBE_DTO request");
                    IList<ProbaDTO> probesDto = server.GetAllProbaDto();
                    response = new Response { Type = ResponseType.GET_PROBE_DTO, Data = probesDto };
                    break;

                case RequestType.GET_PROBA_BY_NAME:
                    logger.Info("Processing GET_PROBA_BY_NAME request");
                    
                    string probaName = Deserialize<string>(request.Data);
                    var proba = server.GetProbaByName(probaName);
                    response = new Response { Type = ResponseType.GET_PROBA_BY_NAME, Data = proba };
                    
                    break; 
                
                case RequestType.GET_PROBA_BY_NAME_AND_RANGE:
                    logger.Info("Processing GET_PROBA_BY_NAME_AND_RANGE request");
                    
                    var datas = Deserialize<JsonElement[]>(request.Data);
                    string  probaNameRange = datas[0].GetString();
                    int rangeee = datas[0].GetInt32();
                    
                    Proba probaByNameAndRange = server.GetProbaByNameAndRange(probaNameRange, rangeee);
                    response = new Response { Type = ResponseType.GET_PROBA_BY_NAME_AND_RANGE, Data = probaByNameAndRange };
                    break;

                case RequestType.GET_VARSTA_BY_RANGE:
                    logger.Info("Processing GET_VARSTA_BY_RANGE request");
                    var range = Deserialize<string>(request.Data);
                    var ageCategory = server.GetVarstaByRange(range);
                    response = new Response { Type = ResponseType.GET_VARSTA_BY_RANGE, Data = ageCategory };
                    break;

                case RequestType.FIND_PARTICIPANT_BY_CNP:
                    logger.Info("Processing FIND_PARTICIPANT_BY_CNP request");
                    String cnp = Deserialize<string>(request.Data);
                    Participant participant = server.FindParticipantByCNP(cnp);
                    response = new Response { Type = ResponseType.FIND_PARTICIPANT_BY_CNP, Data = participant };
                    break;

                case RequestType.REFRESH_PROBE_STATISTICS:
                    logger.Info("Processing REFRESH_PROBE_STATISTICS request");
                    server.RefreshProbeStatistics();
                    response = okResponse;
                    break;
            
                case RequestType.REGISTER:
                    logger.Info("Processing REGISTER request");
                    var registerData = Deserialize<JsonElement[]>(request.Data);
                    string namee = registerData[0].GetString();
                    string cnpp = registerData[1].GetString();
                    string event1 = registerData[2].GetString();
                    string event2 = registerData[3].GetString();
                    string rangee = registerData[4].GetString();

                    string registrationResult = server.RegisterParticipant(namee, cnpp, event1, event2, rangee);
                    response = new Response { Type = ResponseType.REGISTER, Data = registrationResult };
                    break;
                
                default:
                    logger.Warn($"Unknown request type: {request.Type}");
                    response = new Response{Type = ResponseType.ERROR, Data = "Unsopported request type: " + request.Type};
                    break;
            }

            return response;
        }
        catch (Exception e)
        {
            logger.Error($"Unexpected error handling request {request.Type}: {e.Message}", e);
            return new Response {Type = ResponseType.ERROR, Data = e.Message};
        }
    }
}
