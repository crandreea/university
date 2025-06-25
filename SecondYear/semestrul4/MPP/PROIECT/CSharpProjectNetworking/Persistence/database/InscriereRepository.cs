using System.Data;
using Model;
using log4net;


namespace Persistence.database;
public class InscriereRepository : AbstractRepository<System.Tuple<Participant, Proba>, Inscriere>, IInscriereRepo
    {
        private static readonly ILog logger = LogManager.GetLogger(typeof(OrganizatorRepository));
        private readonly IDbConnection connection;
        
        public InscriereRepository()
        {
            logger.Info("Initializing InscriereRepository...");
            connection = JdbcUtils.GetInstance().GetConnection();
        }

        protected override IDbCommand FindOneQuery(System.Tuple<Participant, Proba> id)
        {
            string query = "SELECT * FROM inscrieri" +
                           " INNER JOIN main.participanti p ON p.part_id = inscrieri.participant" +
                           " INNER JOIN main.probe p2 ON p2.proba_id = inscrieri.proba" +
                           " INNER JOIN main.categorievarsta c ON c.cv_id = p2.varsta" +
                           " WHERE proba = @proba_id AND participant = @participant_id";
            var command = connection.CreateCommand();
            command.CommandText = query;
            var paramProba = command.CreateParameter();
            paramProba.ParameterName = "@proba_id";
            paramProba.Value = id.Item1.Id;
            command.Parameters.Add(paramProba);

            var paramParticipant = command.CreateParameter();
            paramParticipant.ParameterName = "@participant_id";
            paramParticipant.Value = id.Item2.Id;
            command.Parameters.Add(paramParticipant);

            return command;
        }

        protected override IDbCommand FindAllQuery()
        {
            string query = "SELECT * FROM inscrieri" +
                           " INNER JOIN main.participanti p ON p.part_id = inscrieri.participant" +
                           " INNER JOIN main.probe p2 ON p2.proba_id = inscrieri.proba" +
                           " INNER JOIN main.categorievarsta c ON c.cv_id = p2.varsta";
            var command = connection.CreateCommand();
            command.CommandText = query;
            return command;
        }

        protected override IDbCommand SaveQuery(Inscriere entity)
        {
            string query = "INSERT INTO inscrieri (participant, proba) VALUES (@participant, @proba)";
            var command = connection.CreateCommand();
            command.CommandText = query;

            var paramParticipant = command.CreateParameter();
            paramParticipant.ParameterName = "@participant";
            paramParticipant.Value = entity.IDParticipant.Id;
            command.Parameters.Add(paramParticipant);

            var paramProba = command.CreateParameter();
            paramProba.ParameterName = "@proba";
            paramProba.Value = entity.IDProba.Id;
            command.Parameters.Add(paramProba);

            return command;
        }

        protected override Inscriere BuildEntity(IDataReader reader)
        {
            int part_id = reader.GetInt32(reader.GetOrdinal("participant"));

            ParticipantRepository repositoryPart = new ParticipantRepository();
            Participant participant = repositoryPart.FindOne(part_id);

            int proba_id = reader.GetInt32(reader.GetOrdinal("proba"));
           
            ProbaRepository probaRepository = new ProbaRepository();
            Proba proba = probaRepository.FindOne(proba_id);

            return new Inscriere(participant, proba);
        }

        public Inscriere Save(Inscriere entity)
        {
            if (entity == null)
            {
                throw new ArgumentNullException(nameof(entity), "Entity cannot be null!");
            }

            logger.InfoFormat("Saving entity");

            try
            {
                using (var command = SaveQuery(entity))
                {
                    int result = command.ExecuteNonQuery();
                    if (result == 0)
                    {
                        logger.Error("Failed to save entity: No rows affected");
                        return null;
                    }
                    return entity;
                }
            }
            catch (Exception ex)
            {
                logger.Error($"Error saving entity: {ex.Message}");
                throw new Exception("Database save error", ex);
            }
        }
    }