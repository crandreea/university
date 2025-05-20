import sys

def print_menu():
    print(
        """
    1 Citirea unei liste de numere intregi  
    2 Gasirea secventei de lungime maxima cu proprietatea ca reprezinta o secventa 
      sub forma de munte (valorile cresc pana la un moment dat si apoi descresc)
    3 Gasirea secventei de lungime maxima cu proprietatea ca in oricare trei 
      elemente consecutive exista o valoarea care se repeta
    4 Gasirea secventei de lungime maxima cu proprietatea ca contine cel mult trei 
      valori distincte
    5 Iesirea din aplicatie
        """
         )

def function_exit():
    print ('Exit')
    exit()

def read_list():
    raw_list = input("Enter list: ")
    string_list = raw_list.split(' ')
    numbers = []

    for number in string_list:
        numbers.append(int(number)) 

    print('List read')
    return numbers
    
def mountain_sequence(numbers):
    
    if len(numbers) < 3: 
        print("The maximum lenght sequence was not found")
        sys.exit(0)

    right = left = 0
    max_right = max_left = 0
    max_len = 0

    while right < len(numbers) - 1:
        peak = right 

        while peak < len(numbers) - 1 and numbers[peak] < numbers[peak + 1]:
            peak += 1

        if right == peak:
            right = left = peak + 1
        else:
            m_end = peak

            while m_end < len(numbers) - 1 and numbers[m_end] > numbers[m_end + 1]:
                m_end += 1

            if m_end != peak:
                if (m_end - left + 1) > max_len:
                    max_len = m_end - left + 1
                    max_right = m_end
                    max_left = left
            right = left = m_end
    
    if max_len > 0:
        return numbers[max_left : max_right + 1]
        #print("The maximum lenght sequence is: {}".format(numbers[max_left : max_right + 1]))
    else:
        print("The maximum lenght sequence was not found")
    

def consecutive_equals(numbers):
    if len(numbers) < 3:
        print("The maximum lenght sequence was not found")
        sys.exit(0)
    left = 0
    max_left = max_right = 0
    max_len = 0

    for right in range(2, len(numbers)):
        if numbers[right] == numbers[right - 1] or numbers[right] == numbers[right - 2] or numbers[right - 1] == numbers[right - 2]:
            if (right - left + 1) > max_len:
                max_len = right - left + 1
                max_right = right
                max_left = left
        else:
            left = right - 1

    if max_len > 0 :
        return numbers[max_left : max_right + 1]
        #print("The maximum lenght sequence is: {}".format(numbers[max_left : max_right + 1]))
    else:
        print("The maximum lenght sequence was not found")


def not_equals(numbers):
    seen = dict()
    left = 0
    max_len = 1
    max_left = max_right = 0
    
    for right in range(0, len(numbers)):
        if numbers[right] not in seen and len(seen) == 3:
            while left < len(numbers) and len(seen) == 3:
                seen[numbers[left]] -= 1
                if seen[numbers[left]] == 0:
                    seen.pop(numbers[left])
                left += 1
        if numbers[right] not in seen:
            seen[numbers[right]] = 1
        else:
            seen[numbers[right]] += 1

        if (right - left + 1 > max_len):
            max_len = (right - left + 1)
            max_left = left
            max_right = right
    
    return numbers[max_left : max_right + 1]
    #print("The maximum lenght sequence is: {}".format(numbers[max_left : max_right + 1]))

def test_mountain_sequence():
    assert mountain_sequence([1, 2, 3, 2, 4]) == [1, 2, 3, 2]

def test_consecutive_equals():
    assert consecutive_equals([1, 2, 1, 3, 3, 2, 3]) == [1, 3, 3, 2, 3]

def test_not_equals():
    assert not_equals([1, 2, 3, 4]) == [1, 2, 3]

def function_option(numbers):
    option = int(input("Chose an option: "))

    optionList = {1:read_list, 2:mountain_sequence, 3:consecutive_equals, 4:not_equals, 5:function_exit}

    if option < 0 or option > 5: 
        print ('Invalid option')
        option = int(input("Chose an option: "))
    elif option == 3:
         print(consecutive_equals(numbers))
    elif option == 2:
        print(mountain_sequence(numbers))
    elif option == 4:
        print(not_equals(numbers))

    elif option == 5 or option == 1: 
        return optionList[option]()
    else: 
        return optionList[option](numbers)

def main():
    print_menu()
    test_mountain_sequence()
    test_consecutive_equals()
    test_not_equals()
    option = int(input("Chose an option: "))
    numbers = read_list()
    while True:
        function_option(numbers)

main()
