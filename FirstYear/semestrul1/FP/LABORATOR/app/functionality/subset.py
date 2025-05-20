""""
from functionality.validation import contest_validation
from functionality.add import get_contest_number, get_score


def subset_multiple(competitors_list):
    result = []
    contest_number_start = input("Introduceti numarul primului participant: ").strip()
    contest_number_finish = input("Introduceti numarul ultimului participant: ").strip()
    contest_validation(competitors_list,contest_number_start)
    contest_validation(competitors_list, contest_number_finish)
    for competitor in competitors_list:
        if get_contest_number(competitor) >= contest_number_start and get_contest_number(competitor) <= contest_number_finish:
            if get_score(competitor) % 10 == 0:
                result.append(competitor)
    return result


def subset_range(competitors_list):
    contest_number_start = input("Introduceti numarul primului participant: ").strip()
    contest_number_finish = input("Introduceti numarul ultimului participant: ").strip()
    contest_validation(competitors_list,contest_number_start)
    contest_validation(competitors_list, contest_number_finish)
    sum = 0
    cnt = 0
    for competitor in competitors_list:
        if get_contest_number(competitor) >= contest_number_start and get_contest_number(competitor) <= contest_number_finish:
            sum += get_score(competitor)
            cnt += 1
    return sum // cnt

def subset_minim(competitors_list):
    contest_number_start = input("Introduceti numarul primului participant: ").strip()
    contest_number_finish = input("Introduceti numarul ultimului participant: ").strip()
    contest_validation(competitors_list,contest_number_start)
    contest_validation(competitors_list, contest_number_finish)
    Min = 101
    for competitor in competitors_list:
        if get_contest_number(competitor) >= contest_number_start and get_contest_number(competitor) <= contest_number_finish:
            if get_score(competitor) <= Min:
                Min = get_score(competitor)
    return Min
"""