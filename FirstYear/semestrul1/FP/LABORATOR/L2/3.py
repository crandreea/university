year = int(input())
nr_ord = int(input())

def leap_year(year):
    if (year % 4 == 0 and year % 100 !=0) or year % 400 == 0: return True
    return False

def date(year, nr_ord):
    month_days = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31]
    month = 1
    if leap_year(year): month_days[1] += 1
    
    while nr_ord > month_days[month - 1]:
        nr_ord -= month_days[month - 1]
        month += 1

    return "The date is: {}/{}/{}".format(nr_ord, month, year)

print(date(year, nr_ord))