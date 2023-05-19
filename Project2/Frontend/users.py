import time
import util as u
import requests
from config import config
from urllib.parse import urljoin

from util import inputByType as iB
from util import checkResult as cR


def login_mode():
    u.clear()
    print("============================================================")
    print("|                        [Login]                           |")
    print("|        Please Enter Your Identity and Password           |")
    print("============================================================")
    print(">>> Identity: ", end="")
    username = input()
    print(">>> Password: ", end="")
    passwd = input()

    r = requests.post(urljoin(config['base'], '/login'), headers={
        "identity": username,
        "passwd": passwd
    })
    result = r.json()
    if result != -1:
        time.sleep(0.5)
        print("              =============================              ")
        print("              |    Login Successfully!    |              ")
        print("              =============================              ")
    else:
        print("<Wrong Identity or Password!>")
        time.sleep(0.5)

    return result


def reg_main():
    flag = True
    while flag:
        u.clear()
        print()
        print("============================================================")
        print("|                       [Register Mode]                    |")
        print("|                Please Enter Your Identity                |")
        print("|----------------------------------------------------------|")
        print("|                                         b. back          |")
        print("============================================================")
        print(">>> ", end="")
        identity = input()

        if identity != 'b':
            result = requests.post(urljoin(config['base'], '/reg/ck_id'), data={
                "identity": identity
            }).json()

            if result:
                print("The identity is already exist!")
            else:
                print("Identity OK")
                flag = not reg(identity)
        else:
            flag = False


def reg(identity):
    passwd = iB('Password')

    name = iB('Name')

    phone = iB('PhoneNumber, b for Quit, s for Skip')

    while len(phone) != 18 or (not phone.isdigit):
        if phone == 'b':
            return
        elif phone == 's':
            phone = None
            break
        else:
            print('<Wrong format> Length must be 18')
            phone = iB('PhoneNumber, q for Quit, s for Skip')

    result = requests.post(urljoin(config['base'], '/reg/regi'), data={
        "name": name,
        "identity": identity,
        "passwd": passwd,
        "phone": phone
    }).json()

    cR(result)
    print("")
    input("<End> Press Enter to continue...")

    return result
