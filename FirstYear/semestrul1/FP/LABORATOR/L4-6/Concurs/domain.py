competitors = []

def get_contest_number(competitor):
    #return competitor[0]
    return competitor['competitor_nr']

def get_score(competitor):
    #return competitor[1]
    return competitor['contest_score']

def set_score(competitor, score):
    #competitor[1] = score
    competitor['contest_score'] = score

def creat_competitor(contest_nr, score):
    """
    Creeaza un nou participant
    :param contest_nr: numaurl participantului
    :param score: scorul participantului
    """
    #return [contest_nr, score]
    return dict(competitor_nr = contest_nr, contest_score = score)