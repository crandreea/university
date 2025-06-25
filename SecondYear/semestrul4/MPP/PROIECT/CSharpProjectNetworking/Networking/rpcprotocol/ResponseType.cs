
namespace Networking.rpcprotocol;

[Serializable]
public enum ResponseType
{
    OK, 
    ERROR,
    GET_PROBE, 
    GET_ORGANIZATORI, 
    SEARCH_PARTICIPANTS, 
    GET_PROBE_DTO,
    GET_PROBA_BY_NAME,
    GET_PROBA_BY_NAME_AND_RANGE, 
    GET_VARSTA_BY_RANGE,
    FIND_PARTICIPANT_BY_CNP, 
    REGISTER,
    UPDATE_INSCRIERE
}
