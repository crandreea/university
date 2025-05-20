from domain import *

def add_new(competitors, nr, score):
    """
    Adauga un nou participant in lista
    :param competitors: lista de participanti
    :param nr: numarul participantului introdus
    :param score: scorul introdus 
    :return: nothing
    """
    competitor = creat_competitor(nr, score)
    competitors.append(competitor)


def delete_score(competitors, contest_number):
    """
    Sterge scorului unui participant 
    :param competitors: lista de participanti
    :param contes_number: numarul participantului caruia i se sterge scorul
    :return: nothing
    """
    for competitor in competitors:   
        if get_contest_number(competitor) == contest_number:
            set_score(competitor, None) 

def delete_score_subset(competitors, start, finish):
    """
    Sterge scorului unui set de participanti 
    :param competitors: lista de participanti
    :param start: numarul primului participant caruia i se sterge scorul
    :param finish: numarul ultimului participant caruia i se sterge scorul
    :return: nothing
    """
    for competitor in competitors:
        if get_contest_number(competitor) >= start and get_contest_number(competitor) <= finish:
            set_score(competitor, None)

def replacement(competitors, contest_number, score):
    """
    Modifica scorul unui participant 
    :param competitors: lista de participanti
    :param contest_number: numarul participantului caruia i se modifica scorul
    :param score: noul scor
    :return: nothing
    """
    contest_number = int(contest_number)
    score = int(score)
    for competitor in competitors:   
        if get_contest_number(competitor) == contest_number:
                set_score(competitor, score)  
        
def add_score(competitors, nr, score):
    """
    Adauga un nou scor pentru participantul a carui numar se citeste de la tastatura doar daca acesta nu are un scor
    :param competitors: lista de participanti
    :param nr: numarul participantului
    :param score: noul scor
    """
    nr = int(nr)
    score = int(score)
    for competitor in competitors:
        if get_contest_number(competitor) == nr:
            if get_score(competitor) == None:
                set_score(competitor) == score
            else:
                print("Participantul are deja un scor...")
                decision = input("Doriti sa il modificati (Yes/No)?  ").lower()
                if decision == 'yes':
                    replacement(competitors, nr, score)

def filter_by_score_less(competitors, score):
    """
    Filtreaza participantii care au scorul mai mic decat scorul dat 
    :param competitors: lista de participanti
    :param score: scorul dupa care sunt filtrati participantii
    :return: lista de participanti ce au scorul mai mic decat scorul dat
    """
    result = []
    for competitor in competitors:
        if get_score(competitor) < score:
            result.append(competitor)
    return result

def print_score_less(competitors, score):
    """
    Salveaza participantii cu un scor mai mic decat scorul citit de la consola
    :param competitors: lista de participanti
    :param score: scorul cu care se compara scorul participantilor
    :return: lista participantilor cu scorul mai mic decat scorul dat
    """
    result = []
    result = filter_by_score_less(competitors, score)
    return result

def print_score_decrease(competitors):
    """
    Sorteaza lista de participanti
    :param competitors: lista de participanti
    :return: lista de participanti sortata descrescator
    """
    sorted_competitors = sorted(competitors, key= lambda x: x['contest_score'], reverse = True)
    return sorted_competitors

def print_score_more_decrease(competitors, score):
    """
    Sorteaza lista de participanti care au scorul mai mare decat un scor dat
    :param competitors: lista de participanti
    :param score: scorul dupa care sunt sortati participantii
    :return: lista de participanti sortata
    """
    result = []
    for competitor in competitors:
        if get_score(competitor) >= score:
            result.append(competitor)
    sorted_competitors = sorted(result, key= lambda x: x['contest_score'], reverse = True)
    return sorted_competitors

def subset_range(competitors, start, finish):
    """
    Calculeaza media scorurilor unui subset de participanti
    :param competitors: lista de participanti
    :param start: numarul primului participant caruia i se sterge scorul
    :param finish: numarul ultimului participant caruia i se sterge scorul
    :return: media scorurilor
    """
    sum = 0
    cnt = 0
    for competitor in competitors:
        if get_contest_number(competitor) >= start and get_contest_number(competitor) <= finish:
            sum += get_score(competitor)
            cnt += 1
    return sum // cnt

def subset_minim(competitors, start, finish):
    """
    Calculeaza scorul minim pentru un subset de participanti
    :param competitors: lista de participanti
    :param start: numarul primului participant caruia i se sterge scorul
    :param finish: numarul ultimului participant caruia i se sterge scorul
    :return: participantul cu scorul minim
    """
    Min = 101
    for competitor in competitors:
        if get_contest_number(competitor) >= start and get_contest_number(competitor) <= finish:
            if get_score(competitor) <= Min:
                Min = get_score(competitor)
    for competitor in competitors:
        if get_score(competitor) == Min:
            return competitor
    
def subset_multiple(competitors, start, finish):
    """
    Filtreaza un subset de participanti care au scorul multiplu de 10
    :param competitors: lista de participanti
    :param start: numarul primului participant caruia i se sterge scorul
    :param finish: numarul ultimului participant caruia i se sterge scorul
    :return: participantii din subset cu scorul multiplu de 10
    """
    result = []
    for competitor in competitors:
        if get_contest_number(competitor) >= start and get_contest_number(competitor) <= finish:
            if get_score(competitor) % 10 == 0:
                result.append(competitor)
    return result

def filter_by_score_multiple(competitors, number):
    """
    Filtreaza participantii care au scorul multiplul numarului 
    :param competitors_list: lista de participanti
    :param number: numarul a carui multiplu trebuie sa fie scorul participantului
    :return: lista de participanti ce au scorul multiplu al numarului number
    """
    result = []
    for competitor in competitors:
        if get_score(competitor) % number == 0:
            result.append(competitor)
    return result

def copy_competitors(competitors):
    result = []
    for competitor in competitors:
        result.append(creat_competitor(get_contest_number(competitor), get_score(competitor)))
    return result

def register_for_undo(undo_list, current_competitors):
    undo_list.append(copy_competitors(current_competitors))

def pop_undo_list(undo_list):
    if not undo_list:
        raise ValueError("Undo nu se poate realiza...")
    return undo_list.pop()

def test_add_new():
    competitors = []
    add_new(competitors, 2, 45)
    assert competitors[0] == {'competitor_nr': 2, 'contest_score': 45}
    assert len(competitors) == 1
    add_new(competitors, 1, 34)
    assert get_score(competitors[1]) == 34
    assert len(competitors) == 2

def test_replacement():
    competitors = []
    add_new(competitors, 1, 34)
    add_new(competitors, 2, 45)
    add_new(competitors, 3, 64)
    replacement(competitors, 2, 50)
    assert get_score(competitors[1]) == 50

def test_delete_score():
    competitors = []
    add_new(competitors, 1, 34)
    add_new(competitors, 2, 45)
    add_new(competitors, 3, 64)
    delete_score(competitors, 2)
    assert get_score(competitors[1]) == None

def test_delete_score_subset():
    competitors = []
    add_new(competitors, 1, 34)
    add_new(competitors, 2, 45)
    add_new(competitors, 3, 64)
    delete_score_subset(competitors, 2, 3)
    assert competitors == [{'competitor_nr': 1, 'contest_score': 34}, {'competitor_nr': 2, 'contest_score': None}, {'competitor_nr': 3, 'contest_score': None}]

def test_filter_by_score_less():
    competitors_list = [{'competitor_nr': 1, 'contest_score': 12}, {'competitor_nr': 2, 'contest_score': 2}, {'competitor_nr': 3, 'contest_score': 9}]
    assert filter_by_score_less(competitors_list, 10) == [{'competitor_nr': 2, 'contest_score': 2}, {'competitor_nr': 3, 'contest_score': 9}]

def test_print_score_decrease():
    #competitors_list = [[1, 12], [2, 2], [3, 9]]
    competitors_list = [{'competitor_nr': 1, 'contest_score': 12}, {'competitor_nr': 2, 'contest_score': 2}, {'competitor_nr': 3, 'contest_score': 9}]
    assert print_score_decrease(competitors_list) == [{'competitor_nr': 1, 'contest_score': 12}, {'competitor_nr': 3, 'contest_score': 9}, {'competitor_nr': 2, 'contest_score': 2}]

def test_print_score_more_decrease():
    #competitors_list = [[1, 12], [2, 2], [3, 9], [4, 37]]
    competitors_list = [{'competitor_nr': 1, 'contest_score': 12}, {'competitor_nr': 2, 'contest_score': 2}, {'competitor_nr': 3, 'contest_score': 9}, {'competitor_nr': 4, 'contest_score': 37}]
    assert print_score_more_decrease(competitors_list, 10) == [{'competitor_nr': 4, 'contest_score': 37}, {'competitor_nr': 1, 'contest_score': 12}]

def test_subset_range():
    #competitors_list = [[1, 12], [2, 2], [3, 8], [4, 8]]
    competitors_list = [{'competitor_nr': 1, 'contest_score': 12}, {'competitor_nr': 2, 'contest_score': 2}, {'competitor_nr': 3, 'contest_score': 8}, {'competitor_nr': 4, 'contest_score': 8}]
    assert subset_range(competitors_list, 2, 3) == 5
    assert subset_range(competitors_list, 3, 4) == 8

def test_subset_minim():
    #competitors_list = [[1, 12], [2, 2], [3, 8], [4, 8]]
    competitors_list = [{'competitor_nr': 1, 'contest_score': 12}, {'competitor_nr': 2, 'contest_score': 2}, {'competitor_nr': 3, 'contest_score': 8}, {'competitor_nr': 4, 'contest_score': 8}]
    assert subset_minim(competitors_list, 1, 3) == {'competitor_nr': 2, 'contest_score' : 2}
    
def test_subset_multiple():
    competitors_list = [{'competitor_nr': 1, 'contest_score': 12}, {'competitor_nr': 2, 'contest_score': 20}, {'competitor_nr': 3, 'contest_score': 8}, {'competitor_nr': 4, 'contest_score': 80}]
    assert subset_multiple(competitors_list, 2, 4) == [{'competitor_nr': 2, 'contest_score': 20}, {'competitor_nr': 4, 'contest_score': 80}]

def test_filter_by_score_multiple():
    competitors_list = [{'competitor_nr': 1, 'contest_score': 12}, {'competitor_nr': 2, 'contest_score': 20}, {'competitor_nr': 3, 'contest_score': 8}, {'competitor_nr': 4, 'contest_score': 80}]
    assert filter_by_score_multiple(competitors_list, 10) == [{'competitor_nr': 2, 'contest_score': 20}, {'competitor_nr': 4, 'contest_score': 80}]

if __name__ == '__main__':
    test_add_new()
    test_replacement()
    test_delete_score()
    test_delete_score_subset()
    test_filter_by_score_less()
    test_print_score_decrease()
    test_print_score_more_decrease()
    test_subset_range()
    test_subset_minim()
    test_subset_multiple()
    test_filter_by_score_multiple()