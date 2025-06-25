using System.Data;
using log4net;
using Model;

namespace Persistence.database;
public class CategorieVarstaRepository : AbstractRepository<int, CategorieVarsta>, ICategorieVarstaRepo
    {
        private static readonly ILog logger = LogManager.GetLogger(typeof(OrganizatorRepository));
        private readonly IDbConnection connection;
        
        public CategorieVarstaRepository()
        {
            logger.Info("Initializing CategorieVarstaRepository...");
            connection = JdbcUtils.GetInstance().GetConnection();
        }

        public IEnumerable<CategorieVarsta> FindAll()
        {
            List<CategorieVarsta> entities = new List<CategorieVarsta>();
            string query = "SELECT * FROM categorievarsta";
            var command = connection.CreateCommand();
            command.CommandText = query;

            using (var reader = command.ExecuteReader())
            {
                while (reader.Read())
                {
                    int id = reader.GetInt32(reader.GetOrdinal("cv_id"));
                    int minAge = reader.GetInt32(reader.GetOrdinal("varstamin"));
                    int maxAge = reader.GetInt32(reader.GetOrdinal("varstamax"));

                    CategorieVarsta varsta = new CategorieVarsta(minAge, maxAge);
                    varsta.Id = id;

                    entities.Add(varsta);
                }
            }

            return entities;
        }

        protected override IDbCommand FindOneQuery(int id)
        {
            throw new NotImplementedException();
        }

        protected override IDbCommand FindAllQuery()
        {
            throw new NotImplementedException();
        }

        protected override IDbCommand SaveQuery(CategorieVarsta entity)
        {
            throw new NotImplementedException();
        }

        protected override CategorieVarsta BuildEntity(IDataReader reader)
        {
            throw new NotImplementedException();
        }

        public new CategorieVarsta FindOne(int id)
        {
            string query = "SELECT * FROM categorievarsta WHERE cv_id = @id";
            var command = connection.CreateCommand();
            command.CommandText = query;

            var param = command.CreateParameter();
            param.ParameterName = "@id";
            param.Value = id;
            command.Parameters.Add(param);

            using (var reader = command.ExecuteReader())
            {
                if (reader.Read())
                {
                    int minAge = reader.GetInt32(reader.GetOrdinal("varstamin"));
                    int maxAge = reader.GetInt32(reader.GetOrdinal("varstamax"));
                    CategorieVarsta varsta = new CategorieVarsta(minAge, maxAge);
                    varsta.Id = reader.GetInt32(reader.GetOrdinal("cv_id"));

                    return varsta;
                }
            }

            return null;
        }
        
        public CategorieVarsta FindVarstaByRange(string selectedCategory)
        {
            string[] ages = selectedCategory.Split('-');
            int minAge = int.Parse(ages[0]);
            int maxAge = int.Parse(ages[1]);

            string query = "SELECT * FROM categorievarsta WHERE varstamin = @min AND varstamax = @max";
            var command = connection.CreateCommand();
            command.CommandText = query;

            var paramMin = command.CreateParameter();
            paramMin.ParameterName = "@min";
            paramMin.Value = minAge;
            command.Parameters.Add(paramMin);

            var paramMax = command.CreateParameter();
            paramMax.ParameterName = "@max";
            paramMax.Value = maxAge;
            command.Parameters.Add(paramMax);

            try
            {
                using (var reader = command.ExecuteReader())
                {
                    if (reader.Read())
                    {
                        CategorieVarsta varsta = new CategorieVarsta(minAge, maxAge);
                        varsta.Id = reader.GetInt32(reader.GetOrdinal("cv_id"));
                        logger.Info($"Loaded entity: {varsta}");
                        return varsta;
                    }
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