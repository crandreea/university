using System.Data;
using log4net;
using Model;

namespace Persistence.database;
public class ProbaRepository : AbstractRepository<int, Proba>, IProbaRepo
    {
        private static readonly ILog logger = LogManager.GetLogger(typeof(OrganizatorRepository));
        private readonly IDbConnection connection;

        public ProbaRepository()
        {
            logger.Info("Initializing ProbaRepository...");
            connection = JdbcUtils.GetInstance().GetConnection();
        }

        protected override IDbCommand FindOneQuery(int id)
        {
            string query = "SELECT * FROM probe " +
                           "INNER JOIN categorievarsta on probe.varsta = categorievarsta.cv_id " +
                           "WHERE proba_id = @id";
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
            string query = "SELECT * FROM probe INNER JOIN categorievarsta on probe.varsta = categorievarsta.cv_id";
            var command = connection.CreateCommand();
            command.CommandText = query;
            return command;
        }

        protected override IDbCommand SaveQuery(Proba entity)
        {
            string query = "INSERT INTO probe(tip, varsta) VALUES ( @tip, @varsta)";
            var command = connection.CreateCommand();
            command.CommandText = query;


            var paramTip = command.CreateParameter();
            paramTip.ParameterName = "@tip";
            paramTip.Value = entity.Tip;
            command.Parameters.Add(paramTip);

            var paramVarsta = command.CreateParameter();
            paramVarsta.ParameterName = "@varsta";
            paramVarsta.Value = entity.Varsta.Id;
            command.Parameters.Add(paramVarsta);

            return command;
        }

        protected override Proba BuildEntity(IDataReader reader)
        {
            int varstaID = reader.GetInt32(reader.GetOrdinal("varsta"));
            CategorieVarstaRepository repository = new CategorieVarstaRepository();
            CategorieVarsta varsta = repository.FindOne(varstaID);
            
            int id = reader.GetInt32(reader.GetOrdinal("proba_id"));
            string tip = reader.GetString(reader.GetOrdinal("tip"));

            Proba proba = new Proba(tip, varsta);
            proba.Id = id;

            return proba;
        }
        
        public IDbCommand GetProbaByName(string name)
        {
            // Add the join to categorievarsta table
            string query = "SELECT * FROM probe " +
                           "INNER JOIN categorievarsta on probe.varsta = categorievarsta.cv_id " +
                           "WHERE tip = @name";
            var command = connection.CreateCommand();
            command.CommandText = query;

            var param = command.CreateParameter();
            param.ParameterName = "@name";
            param.Value = name;
            command.Parameters.Add(param);

            return command;
        }

        public IDbCommand GetProbaByNameAndRange(string name, int range)
        {
            // Add the join to categorievarsta table
            string query = "SELECT * FROM probe " +
                           "INNER JOIN categorievarsta on probe.varsta = categorievarsta.cv_id " +
                           "WHERE tip = @name AND varsta = @range";
            var command = connection.CreateCommand();
            command.CommandText = query;

            var paramName = command.CreateParameter();
            paramName.ParameterName = "@name";
            paramName.Value = name;
            command.Parameters.Add(paramName);

            var paramRange = command.CreateParameter();
            paramRange.ParameterName = "@range";
            paramRange.Value = range;
            command.Parameters.Add(paramRange);

            return command;
        }

        public Proba FindProbaByName(string name)
        {
            try
            {
                using var command = GetProbaByName(name);
                using var reader = command.ExecuteReader();
                
                if (reader.Read())
                {
                    Proba entity = BuildEntity(reader);
                    entity.Id = reader.GetInt32(reader.GetOrdinal("proba_id"));
                    return entity;
                }
            }
            catch (Exception e)
            {
                logger.Error($"Error loading entity: {e.Message}");
                throw new Exception(e.Message);
            }

            return null;
        }

        public Proba FindProbaByNameAndRange(string name, int range)
        {
            try
            {
                using var command = GetProbaByNameAndRange(name, range);
                using var reader = command.ExecuteReader();
                
                if (reader.Read())
                {
                    Proba entity = BuildEntity(reader);
                    entity.Id = reader.GetInt32(reader.GetOrdinal("proba_id"));
                    return entity;
                }
            }
            catch (Exception e)
            {
                logger.Error($"Error loading entity: {e.Message}");
                throw new Exception(e.Message);
            }

            return null;
        }
    }