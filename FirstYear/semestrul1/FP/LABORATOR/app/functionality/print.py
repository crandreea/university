""""
from functionality.filter import filter_by_score_less
from user_interface.print import *
from functionality.validation import number_validation
from functionality.add import get_score

def print_score_less(competitors_list):
    score = input("Introduceti scorul: ").split()
    number_validation(score)
    result = []
    result = filter_by_score_less(competitors_list, score)
    return result

def print_score_decrease(competitors_list):
    sorted_competitors = sorted(competitors_list, key= lambda x: x[1], reverse = True)
    return sorted_competitors

def print_score_more_decrease(competitors_list):
    score = input("Introduceti scorul: ").split()
    number_validation(score)
    result = []
    for competitor in competitors_list:
        if get_score(competitor) > score:
            result.append(competitor)
    return result
"""""
