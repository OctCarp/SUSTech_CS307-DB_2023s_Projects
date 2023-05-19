import os
import platform
import time


def newline(input_str):
    words = input_str.split(' ')
    result = []
    line_len = 0
    for word in words:
        if line_len + len(word) > 50:
            result.append('\n|')
            line_len = 0
        elif result:
            result.append(' ')
            line_len += 1
        result.append(word)
        line_len += len(word)
    return ''.join(result)


def clear():
    if platform.system() == "Windows":
        os.system("cls")
    else:
        os.system("clear")


def inputByType(itemType):
    print("Please Enter the %s:" % itemType)
    print(">>> ", end="")
    return input()


def inputNotBlank(itemType):
    flag = True
    s = ""
    while flag:
        print("Please Enter the %s:" % itemType)
        print(">>> ", end="")
        s = input()
        if not s.strip():
            print("<Wrong Format> Can not be blank, try again")
        else:
            flag = False
    return s


def checkResult(result):
    if result:
        print("Success!")
    else:
        print("Failed!")
    time.sleep(1)


def exiting():
    print("Exiting...")
    time.sleep(1)
    exit()


def body(result):
    return result.content.decode('ascii')
