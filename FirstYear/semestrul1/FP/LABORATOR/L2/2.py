from datetime import date

d_born = int(input())
m_born = int(input())
y_born = int(input())

def leap_year(year):
    if (year % 4 == 0 and year % 100 !=0) or (year % 400 == 0): return True
    return False

def varsta(d_born, m_born, y_born):
    month_days = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31]
    nr_days = 0
    date_curr = date.today()
    d_curr = date_curr.day
    m_curr = date_curr.month
    y_curr = date_curr.year

    if leap_year(y_born) and m_born < 3: 
        nr_days = 1

    nr_days += month_days[m_born - 1] - d_born

    for i in range(m_born + 1, 13):
        nr_days += month_days[i - 1]

    if leap_year(y_curr) and m_curr > 2: 
        nr_days += 1

    nr_days += d_curr

    for i in range(1, m_curr):
        nr_days += month_days[i - 1]

    if y_curr == y_born: nr_days -= 365

    for i in range(y_born + 1, y_curr):
        if leap_year(i) : nr_days += 366
        else: nr_days += 365

    
    return "Age to days : {}".format(nr_days)


print(varsta(d_born, m_born, y_born))


    