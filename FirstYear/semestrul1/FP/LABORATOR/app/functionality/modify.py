""""
from functionality.add import get_contest_number, set_score
from functionality.validation import number_validation, contest_validation

def replacement(competitors_list):
    contest_number = input("Introduceti numarul participantului: ").strip()
    score = input("Introduceti scorul: ").strip()
    number_validation(score)
    contest_validation(competitors_list, contest_number)
    contest_number = int(contest_number)
    score = int(score)
    for competitor in competitors_list:   
        if get_contest_number(competitor) == contest_number:
            set_score(competitor, score)


def delete_score(competitors_list):
    contest_number = input("Introduceti numarul participantului: ").strip()
    contest_validation(competitors_list, contest_number)
    for competitor in competitors_list:   
        if get_contest_number(competitor) == contest_number:
            set_score(competitor, None)


def delete_score_subset(competitors_list):
    contest_number_start = input("Introduceti numarul primului participant: ").strip()
    contest_number_finish = input("Introduceti numarul ultimului participant: ").strip()
    contest_validation(competitors_list,contest_number_start)
    contest_validation(competitors_list, contest_number_finish)
    for competitor in competitors_list:
        if get_contest_number(competitor) >= contest_number_start and get_contest_number(competitor) <= contest_number_finish:
            set_score(competitor, None)
"""