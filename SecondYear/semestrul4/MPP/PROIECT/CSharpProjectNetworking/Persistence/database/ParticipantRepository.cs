using System.Data;
using Model;
using log4net;

namespace Persistence.database;
public class ParticipantRepository : AbstractRepository<int, Participant>, IParticipantRepo
    {
        private static readonly ILog logger = LogManager.GetLogger(typeof(OrganizatorRepository));

        private readonly IDbConnection connection;

        public ParticipantRepository()
        {
            logger.Info("Initializing ParticipantRepository...");
            connection = JdbcUtils.GetInstance().GetConnection();
        }

        protected override IDbCommand FindOneQuery(int id)
        {
            string query = "SELECT * FROM participanti WHERE part_id = @id";
            var command = connection.CreateCommand();
            command.CommandText = query;
            var param = command.CreateParameter();
            param.ParameterName = "@id";
            param.Value = id;
            command.Parameters.Add(param);
            return command;
        }

        protected override IDbCommand FindAllQuery()
        {
            string query = "SELECT * FROM participanti";
            var command = connection.CreateCommand();
            command.CommandText = query;
            return command;
        }

        protected override IDbCommand SaveQuery(Participant entity)
        {
            string query = "INSERT INTO participanti(name, cnp) VALUES ( @name, @cnp)";
            var command = connection.CreateCommand();
            command.CommandText = query;

            var paramNume = command.CreateParameter();
            paramNume.ParameterName = "@name";
            paramNume.Value = entity.Nume;
            command.Parameters.Add(paramNume);

            var paramCnp = command.CreateParameter();
            paramCnp.ParameterName = "@cnp";
            paramCnp.Value = entity.Cnp;
            command.Parameters.Add(paramCnp);

            return command;
        }

        protected override Participant BuildEntity(IDataReader reader)
        {
            int id = reader.GetInt32(reader.GetOrdinal("part_id"));
            string nume = reader.GetString(reader.GetOrdinal("name"));
            string cnp = reader.GetString(reader.GetOrdinal("cnp"));

            Participant participant = new Participant(nume, cnp);
            participant.Id = id;

            return participant;
        }
        
        public new Participant Save(Participant participant)
        {
            using (var command = SaveQuery(participant))
            {
                command.ExecuteNonQuery(); 
                using (var idCommand = connection.CreateCommand())
                {
                    idCommand.CommandText = "SELECT last_insert_rowid();";
                    var id = (long)idCommand.ExecuteScalar();
                    participant.Id = (int)id; 
                }
        
            }
            return participant;
        }
        

    }