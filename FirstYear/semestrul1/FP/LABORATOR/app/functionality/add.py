"""
from functionality.validation import *

def read_competitor():
    return input("Introduceti numarul de identificare si scorul participantului(separate prin virgula): ")

def creat_competitors_list(competitors_list):
    
    Creeaza o lista dintr-un string
    :param competitors_list: lista de participanti separati prin virgula
    :return: lista transformata din string
    
    competitor = competitors_list.split(",")
    competitor[0] = int(competitor[0])
    contest_validation(competitors_list, competitor[0])
    competitor[1] = int(competitor[1])
    return competitor

def get_contest_number(competitor):
    return competitor[0]

def get_score(competitor):
    return competitor[1]

def set_contest_number(competitor, id_number):
    competitor[0] = id_number

def set_score(competitor, score):
    competitor[1] = score

def add_new(competitors_list, competitor):
    
    Adauga un nou participant in lista
    :param competitors_list: lista de participanti
    :param competitor: competitor entity
    :return: nothing
   
    competitors_list.append(competitor)

def add_new_user(competitors_list):
    
    Citeste un concurent de la tastatura si il introduce in lista
    :param competitors_list: list de participanti 
    :return: nothing
    
    raw_list = read_competitor()
    competitor = creat_competitors_list(raw_list)
    add_new(competitors_list, competitor)
    print(f'Concurentul cu numarul {get_contest_number(competitor)} si scorul {get_score(competitor)} a fost adaugat cu succes ')

"""