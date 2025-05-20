""""
from functionality.add import get_score
from functionality.validation import *

def filter_by_score_multiple(competitors_list):
    
    Filtreaza participantii care au scorul multiplul numarului 
    :param competitors_list: lista de participanti
    :param number: numarul a carui multiplu trebuie sa fie scorul participantului
    :return: lista de participanti ce au scorul multiplu al numarului number
    
    number = input("Introduceti numarul: ").strip()
    number_validation(number)
    result = []
    for competitor in competitors_list:
        if get_score(competitor) % number == 0:
            result.append(competitor)
    return result


def filter_by_score_less(competitors_list):
    
    Filtreaza participantii care au scorul mai mic decat scorul dat 
    :param competitors_list: lista de participanti
    :param score: scorul dupa care sunt filtrati participantii
    :return: lista de participanti ce au scorul mai mic decat scorul dat
    
    score = input("Introduceti numarul: ").strip()
    number_validation(score)
    result = []
    for competitor in competitors_list:
        if get_score(competitor) < score:
            result.append(competitor)
    return result

"""
