from square import view_mode
from users import reg_main
from users import login_mode
from homepage import homepage_main
import util as u


def client_main():
    while True:
        u.clear()

        print("============================================================")
        print("|                         [Menu]                           |")
        print("|        Please Enter Number To Select Your Target         |")
        print("|----------------------------------------------------------|")
        print("|  1. View           2.Login            3. Register        |")
        print("|                                       q. Exit            |")
        print("============================================================")
        print(">>> ", end="")
        op = input()

        if op == '1':
            view_mode("-1")
        elif op == '2':
            a_id = login_mode()
            homepage_main(a_id)
        elif op == '3':
            reg_main()
        elif op == 'q':
            u.exiting()
        else:
            continue


if __name__ == '__main__':
    print("")
    print("┏=====================================================================================┓")
    print("|      ________________ ____  _____   ____             __          _____              |")
    print("|     / ____/ ___/__  // __ \/__  /  / __ \____  _____/ /______   / ___/__  _______   |")
    print("|    / /    \__ \ /_ </ / / /  / /  / /_/ / __ \/ ___/ __/ ___/   \__ \/ / / / ___/   |")
    print("|   / /___ ___/ /__/ / /_/ /  / /  / ____/ /_/ (__  ) /_(__  )   ___/ / /_/ (__  )    |")
    print("|   \____//____/____/\____/  /_/  /_/    \____/____/\__/____/   /____/\__, /____/     |")
    print("|                                                                   /____/            |")
    print("┗=====================================================================================┛")
    print("")

    client_main()
