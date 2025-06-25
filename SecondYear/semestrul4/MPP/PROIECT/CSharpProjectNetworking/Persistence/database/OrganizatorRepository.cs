using System.Data;
using Model;
using log4net;

namespace Persistence.database;
public class OrganizatorRepository : AbstractRepository<int, Organizator>, IOrganizatorRepo
{
        private static readonly ILog logger = LogManager.GetLogger(typeof(OrganizatorRepository));
        private readonly IDbConnection connection;
        
        public OrganizatorRepository()
        {
            logger.Info("Initializing OrganizatorRepository...");
            connection = JdbcUtils.GetInstance().GetConnection();
        }

        protected override IDbCommand FindOneQuery(int id)
        {
            var command = connection.CreateCommand();
            command.CommandText = "SELECT * FROM organizatori WHERE org_id = @id";
            var param = command.CreateParameter();
            param.ParameterName = "@id";
            param.Value = id;
            command.Parameters.Add(param);
            return command;
        }

        protected override IDbCommand FindAllQuery()
        {
            var command = connection.CreateCommand();
            command.CommandText = "SELECT * FROM organizatori";
            return command;
        }

        protected override IDbCommand SaveQuery(Organizator entity)
        {
            var command = connection.CreateCommand();
            command.CommandText = "INSERT INTO organizatori (org_id, username, password) VALUES (@id, @username, @password)";
            
            var paramId = command.CreateParameter();
            paramId.ParameterName = "@id";
            paramId.Value = entity.Id;
            command.Parameters.Add(paramId);
            
            var paramUsername = command.CreateParameter();
            paramUsername.ParameterName = "@username";
            paramUsername.Value = entity.Username;
            command.Parameters.Add(paramUsername);
            
            var paramPassword = command.CreateParameter();
            paramPassword.ParameterName = "@password";
            paramPassword.Value = entity.Password;
            command.Parameters.Add(paramPassword);
            
            return command;
        }

        protected override Organizator BuildEntity(IDataReader reader)
        {
            var id = reader.GetInt32(reader.GetOrdinal("org_id"));
            var username = reader.GetString(reader.GetOrdinal("username"));
            var password = reader.GetString(reader.GetOrdinal("password"));
            
            return new Organizator(username, password) { Id = id };
        }
}
