"""
from functionality.add import *
from functionality.filter import *
from functionality.modify import *
from functionality.print import *

def test_add_new():
    competitors_list = []
    competitor = [1, 23]
    competitor2 = [2, 45]
    add_new(competitors_list, competitor)
    assert competitors_list[0] == competitor
    assert len(competitors_list) == 1
    add_new(competitors_list, competitor2)
    assert competitors_list[0] == competitor
    assert competitors_list[1] == competitor2
    assert len(competitors_list) == 2

def test_filter_by_score_multiple():
    competitors_list = [[1, 12], [2, 2], [3, 9]]
    assert filter_by_score_multiple(competitors_list, 3) == [[1, 12], [3, 9]]
    competitors_list.append([4, 12])
    assert filter_by_score_multiple(competitors_list, 3) == [[1, 12], [3, 9], [4, 12]]

def test_filter_by_score_less():
    competitors_list = [[1, 12], [2, 2], [3, 9]]
    assert filter_by_score_less(competitors_list, 10) == [[2, 2], [3, 9]]

def test_print_score_decrease():
    competitors_list = [[1, 12], [2, 2], [3, 9]]
    assert print_score_decrease(competitors_list) == [[1, 12], [3, 9], [2, 2]]
    competitors_list.append([4, 7])
    assert print_score_decrease(competitors_list) == [[1, 12], [3, 9], [4, 7], [2, 2]]

"""