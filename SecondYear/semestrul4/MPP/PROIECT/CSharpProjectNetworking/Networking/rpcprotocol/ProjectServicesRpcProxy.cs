
using System.Collections.Concurrent;
using System.Net.Sockets;
using System.Text.Json;
using System.Text.Json.Serialization;
using log4net;
using Model;
using Services;

namespace Networking.rpcprotocol
{
    public class ProjectServicesRpcProxy : IProjectServices
    {
        private static readonly ILog logger = LogManager.GetLogger(typeof(ProjectServicesRpcProxy));

        private readonly string host;
        private readonly int port;
        private TcpClient connection;
        
        private NetworkStream stream;
        private StreamReader input;
        private StreamWriter output;
        
        private JsonSerializerOptions options;

        private BlockingCollection<Response> qresponses;
        private EventWaitHandle _waitHandle;
        
        private IProjectObserver client;

        private volatile bool connected;


        public ProjectServicesRpcProxy(string host, int port)
        {
            this.host = host;
            this.port = port;
            options = new JsonSerializerOptions
            {
                Converters = { new JsonStringEnumConverter() },
                PropertyNameCaseInsensitive = true
            };
            qresponses = new BlockingCollection<Response>();
            logger.Info($"RPC Proxy created for server {host}:{port}");
            InitializeConnection();
        }

        private void InitializeConnection()
        {
            try
            {
                connection = new TcpClient(host, port);
                stream = connection.GetStream();
                input = new StreamReader(stream);
                output = new StreamWriter(stream);
                _waitHandle = new AutoResetEvent(false);
                connected = true;
                StartReader();
                logger.Info($"Connected to server {host}:{port}");
            }
            catch (Exception e)
            {
                logger.Error("Error connecting to server: " + e.Message, e);
                throw new ProjectException("Cannot connect to server: " + e.Message, e);
            }
        }
        
        private void CloseConnection()
        {
            connected = false;
            try
            {
                input.Close();
                output.Close();
                stream.Close();
                connection.Close();
                _waitHandle.Close();
                client = null;
            }
            catch (Exception e)
            {
                Console.WriteLine(e.StackTrace);
            }
        }
        
        private void StartReader()
        {
            Thread tw =new Thread(Run);
            tw.Start();
        }

        public virtual void Run()
        {
            while(connected)
            {
                try
                {
                    
                    string responseJson = input.ReadLine();
                    if (string.IsNullOrEmpty(responseJson)) 
                        continue; 
                    Response response = JsonSerializer.Deserialize<Response>(responseJson, options);
                    logger.Debug("response received "+response);
                    if (IsUpdate(response))
                    {
                        HandleUpdate(response);
                    }
                    else
                    {
                        lock (qresponses)
                        {
                            qresponses.Add(response);
                        }
                        _waitHandle.Set();
                    }
                }
                catch (Exception e)
                {
                    logger.Error("Reading error "+e);
                }
				
            }
        }
        private bool IsUpdate(Response response)
        {
            return response.Type == ResponseType.UPDATE_INSCRIERE;
        }
        private void HandleUpdate(Response response)
        {
            try
            {
                logger.Info("Handling update response");
                if (IsUpdate(response))
                {
                    Inscriere inscriere = DeserializeResponse<Inscriere>(response.Data, "HandleUpdate");
                    client.InscriereAdded(inscriere);
                    logger.Info("Client updated with inscription info");
                    
                }
            }
            catch (Exception e)
            {
                logger.Error("Error handling update", e);
            }
        }

        private void sendRequest(Request request)
        {
            try
            {
                string jsonRequest = JsonSerializer.Serialize(request, options);
                output.WriteLine(jsonRequest);
                output.Flush();
                
            }
            catch (Exception e)
            {
                throw new ProjectException("Error handling request", e);
            }
        }

        private Response readResponse()
        {
            Response response = null;
            try
            {
                _waitHandle.WaitOne();
                response = qresponses.Take();
            }catch (Exception e) {
                Console.WriteLine(e.StackTrace);
            }
            return response;
        }
        
        private T DeserializeResponse<T>(object responseData, string methodName) where T : class, new()
        {
            try
            {
                if (responseData is JsonElement jsonElement)
                {
                    var options = new JsonSerializerOptions
                    {
                        PropertyNameCaseInsensitive = true
                    };
            
                    return JsonSerializer.Deserialize<T>(jsonElement.GetRawText(), options) ?? new T();
                }
                else if (responseData is T result)
                {
                    return result;
                }
                else
                {
                    if (typeof(T) == typeof(string) && responseData != null)
                    {
                        return responseData.ToString() as T;
                    }
            
                    throw new ProjectException($"Tip de date neașteptat: {responseData.GetType().Name}");
                }
            }
            catch (JsonException jsonEx)
            {
                logger.Error($"[{methodName}] Eroare de deserializare: {jsonEx.Message}", jsonEx);
                Console.Out.WriteLine($"[{methodName}] Eroare de deserializare: {jsonEx.Message}");
                return new T();
            }
        }
        
        public virtual Organizator Login(string username, string password, IProjectObserver observer)
        {
            InitializeConnection();
            Organizator org = new Organizator(username, password);
            Request request = new Request { Type = RequestType.LOGIN, Data = org };
            sendRequest(request);
            Response response = readResponse();
            this.client = observer;
            if (response.Type == ResponseType.ERROR)
            {
                CloseConnection();
                throw new ProjectException("Eroare in Login");
            }

            return DeserializeResponse<Organizator>(response.Data, "Login");
        }

        public virtual void Logout(Organizator user, IProjectObserver observer)
        {
            this.client = observer;
            Request request = new Request { Type = RequestType.LOGOUT, Data = null };
            sendRequest(request);
            Response response = readResponse();
            CloseConnection();

            if (response.Type == ResponseType.ERROR)
            {
                throw new ProjectException("Eroare in LOGOUT");
            }
        }
        
        
        public virtual IEnumerable<Proba> GetAllProba()
        {
            Request request = new Request { Type = RequestType.GET_PROBE, Data = null };
            sendRequest(request);
            Response response = readResponse();

            if (response.Type == ResponseType.ERROR)
            {
                throw new ProjectException("Eroare in GetAllProba");
            }

            return DeserializeResponse<List<Proba>>(response.Data, "GetAllProba" );
        }

        public IEnumerable<Organizator> GetAllOrganizatori()
        {
            Request request = new Request { Type = RequestType.GET_ORGANIZATORI, Data = null };
            sendRequest(request);
            Response response = readResponse();

            if (response.Type == ResponseType.ERROR)
            {
                throw new ProjectException("Eroare in GetAllOrganizatori");
            }
            
            
            return DeserializeResponse<List<Organizator>>(response.Data, "GetAllOrganizatori");
        }


        public IList<ProbaDTO> GetAllProbaDto()
        {
            Request request = new Request { Type = RequestType.GET_PROBE_DTO, Data = null };
            sendRequest(request);
            Response response = readResponse();

            if (response.Type == ResponseType.ERROR)
            {
                string errorMessage = response.Data?.ToString() ?? "Unknown error";
                throw new ProjectException(errorMessage);
            }
           
            return DeserializeResponse<List<ProbaDTO>>(response.Data, "GetAllOrganizatori");

        }

        public Proba GetProbaByName(string name)
        {
            Request request = new Request { Type = RequestType.GET_PROBA_BY_NAME, Data = name };
            sendRequest(request);
            Response response = readResponse();

            if (response.Type == ResponseType.ERROR)
            {
                throw new ProjectException("Eroare in GetProbeByName");
            }

            return DeserializeResponse<Proba>(response.Data, "GetAllOrganizatori");

        }

        public Proba GetProbaByNameAndRange(string name, int range)
        {
            System.Object[] data = new System.Object[2];
            data[0] = name;
            data[1] = range;
            
            Request request = new Request { Type = RequestType.GET_PROBA_BY_NAME_AND_RANGE, Data = data };
            sendRequest(request);
            Response response = readResponse();

            if (response.Type == ResponseType.ERROR)
            {
                throw new ProjectException("Eroare in GetProbeByNameandRange");
            }

            return DeserializeResponse<Proba>(response.Data, "GetAllOrganizatori");

        }

        public CategorieVarsta GetVarstaByRange(string selectedCategory)
        {
            Request request = new Request { Type = RequestType.GET_VARSTA_BY_RANGE, Data = selectedCategory };
            sendRequest(request);
            Response response = readResponse();

            if (response.Type == ResponseType.ERROR)
            {
                throw new ProjectException("Eroare in GetVarstaByRange");
            }

            return DeserializeResponse<CategorieVarsta>(response.Data, "GetAllOrganizatori");

        }

        public IList<ParticipantDTO> SearchParticipants(Proba selectedProba, CategorieVarsta selectedCategory)
        {
            Object[] data = new System.Object[2];
            data[0] = selectedProba;
            data[1] = selectedCategory;
            
            Request request = new Request { Type = RequestType.SEARCH_PARTICIPANTS, Data = data };
            sendRequest(request);
            Response response = readResponse();

            if (response.Type == ResponseType.ERROR)
            {
                throw new ProjectException("Error in SearchParticipants");
            }

            return DeserializeResponse<List<ParticipantDTO>>(response.Data, "GetAllOrganizatori");
            
        }
        
        private string DeserializeResponseToString(object responseData, string methodName)
        {
            if (responseData is JsonElement jsonElement)
            {
                return jsonElement.GetString() ?? string.Empty;
            }

            if (responseData is string str)
            {
                return str;
            }

            throw new ProjectException($"[{methodName}] Nu se poate converti obiectul în string. Tip primit: {responseData.GetType().Name}");
        }


        public string RegisterParticipant(string name, string cnp, string event1, string event2, string range)
        {
            System.Object[] data = new System.Object[6];
            data[0] = name;
            data[1] = cnp;
            data[2] = event1;
            data[3] = event2;
            data[4] = range;
            
            Request request = new Request { Type = RequestType.REGISTER, Data = data };
            sendRequest(request);
            Response response = readResponse();

            if (response.Type == ResponseType.ERROR)
            {
                throw new ProjectException("Eroare in RegisterParticipant");
            }

            return DeserializeResponseToString(response.Data, "RegisterParticipant");
        }

        public Participant? FindParticipantByCNP(string cnp)
        {
            Request request = new Request { Type = RequestType.FIND_PARTICIPANT_BY_CNP, Data = cnp };
            sendRequest(request);
            Response response = readResponse();

            if (response.Type == ResponseType.ERROR)
            {
                throw new ProjectException("Eroare in FindParticipantByCNP");
            }

            return DeserializeResponse<Participant>(response.Data, "GetAllOrganizatori");

        }

        public void RefreshProbeStatistics()
        {
            Request request = new Request { Type = RequestType.REFRESH_PROBE_STATISTICS, Data = null };
            sendRequest(request);
            Response response = readResponse();

            if (response.Type == ResponseType.ERROR)
            {
                throw new ProjectException("Eroare in RefreshProbeStatistics");
            }
        }
    }
}