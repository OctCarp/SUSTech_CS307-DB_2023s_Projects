import time
import util as u
import jsonUI as ui
import requests
import square as sqr
import comm
from config import config
from urllib.parse import urljoin
from users import login_mode


def homepage_main(a_id):
    if a_id == -1:
        return

    flag = True
    while flag:
        u.clear()
        print("Hello! Author ID No. %s" % a_id)
        print("============================================================")
        print("|                          [Views]                         |")
        print("|        Please Enter Number To Select Your View           |")
        print("|----------------------------------------------------------|")
        print("|                     1. My Posts                          |")
        print("|                     2. My Replies                        |")
        print("|                     3. My Sub Replies                    |")
        print("|                     4. My Liked                          |")
        print("|                     5. My Shared                         |")
        print("|                     6. My Favorited                      |")
        print("|                     7. My Followed                       |")
        print("|                     8. My Blocked                        |")
        print("|                     9. Create Post                       |")
        print("|                     s. Square                            |")
        print("|----------------------------------------------------------|")
        print("|          x. Log Out                     q. Quit          |")
        print("============================================================")
        print(">>> ", end="")
        op = input()
        if op == '1':
            my_post(a_id)
        elif op == '2':
            my_reply_list(a_id)
        elif op == '3':
            my_reply2_list(a_id)
        elif op == '4':
            my_opt_post(a_id, "liked")
        elif op == '5':
            my_opt_post(a_id, "shared")
        elif op == '6':
            my_opt_post(a_id, "favorited")
        elif op == '7':
            my_opt_authors(a_id, "follow")
        elif op == '8':
            my_opt_authors(a_id, "block")
        elif op == '9':
            comm.send_post(a_id)
        elif op == 's':
            sqr.view_mode(a_id)
        elif op == 'x':
            flag = False
        elif op == 'q':
            u.exiting()
        else:
            print("<Wrong Selection>")


def homepage_main_anonymous():

    a_id = -2
    flag = True
    while flag:
        u.clear()
        print("Hello! Anonymous")
        print("============================================================")
        print("|                          [Views]                         |")
        print("|        Please Enter Number To Select Your View           |")
        print("|----------------------------------------------------------|")
        print("|                     1. My Replies                        |")
        print("|                     2. My Sub Replies                    |")
        print("|                     s. Square                            |")
        print("|----------------------------------------------------------|")
        print("|          x. back       l. Log in        q. Quit          |")
        print("============================================================")
        print(">>> ", end="")
        op = input()
        if op == '1':
            my_reply_list(a_id)
        elif op == '2':
            my_reply2_list(a_id)
        elif op == 's':
            sqr.view_mode(a_id)
        elif op == 'x':
            flag = False
        elif op == 'l':
            a_id = login_mode()
            homepage_main(a_id)
        elif op == 'q':
            u.exiting()
        else:
            print("<Wrong Selection>")


def my_post(a_id):
    result = requests.post(urljoin(config['base'], '/normal/own'), headers={
        'a_id': str(a_id),
        'type': "post"
    }).json()
    if result:
        print("This is your Posts List:")
        post_list(a_id, result)
    else:
        print("<Not Found> No such post at all")

    input("<End> Press Enter to continue...")


def my_opt_post(a_id, opt_type):
    result = requests.post(urljoin(config['base'], '/normal/own'), headers={
        'a_id': str(a_id),
        'type': opt_type
    }).json()

    if result:
        flag = True
        while flag:
            print("This is your %s List:" % opt_type)
            post_list(a_id, result)

            print("Input Post ID you want to trans, b for back")
            print(">>> ", end="")
            p_id = input()
            if p_id.isdigit():
                comm.post_op_trans(a_id, p_id, opt_type)
                flag = False
            elif p_id == 'b':
                flag = False
            else:
                print("<Wrong Format> Must be a number")
    else:
        print("<Not Found> No such post at all")

    input("<End> Press Enter to continue...")


def my_opt_authors(a_id, op_type):
    result = requests.post(urljoin(config['base'], '/normal/own'), headers={
        'a_id': str(a_id),
        'type': op_type
    }).json()
    if result:
        print("This is your %s List:" % op_type)

        flag = True
        while flag:
            print("=======================================")
            print("|Author ID|Name")
            print("----------+----------------------------")

            for res in result:
                ui.author_idn_ui(res)

            print("=======================================")
            print("Input author ID you want to undo, b for back")
            print(">>> ", end="")
            fa_id = input()
            if fa_id.isdigit():
                comm.author_opt_trans(a_id, fa_id, op_type)
                flag = False
            elif fa_id == 'b':
                flag = False
            else:
                print("<Wrong Format> Must be a number")
    else:
        print("<Not Found> No such user at all")

    input("<End> Press Enter to continue...")


def post_list(a_id, result):
    time.sleep(0.5)
    flag = True
    while flag:
        print("=======================================")
        print("|Post ID|Title")
        print("--------+------------------------------")

        for res in result:
            ui.post_idc_ui(res)

        print("=======================================")
        print("Input Post ID you want to view, b for back")
        print(">>> ", end="")
        p_id = input()
        if p_id.isdigit():
            comm.get_post(p_id, a_id)
        elif p_id == 'b':
            flag = False
        else:
            print("<Wrong Format> Must be a number")


def my_reply_list(a_id):
    result = requests.post(urljoin(config['base'], '/normal/own'), headers={
        'a_id': str(a_id),
        'type': "reply"
    }).json()
    if result:
        time.sleep(0.5)
        flag = True
        while flag:
            print("=======================================")
            print("|Reply ID|Content")
            print("--------------+------------------------")

            for res in result:
                ui.r_idc_ui(res)

            print("=======================================")
            print("Input Reply ID you want to view, b for back")
            print(">>> ", end="")
            p_id = input()
            if p_id.isdigit():
                comm.get_reply(p_id, a_id)
            elif p_id == 'b':
                flag = False
            else:
                print("<Wrong Format> Must be a number")
    else:
        print("<Not Found> No such reply at all")

    input("<End> Press Enter to continue...")


def my_reply2_list(a_id):
    result = requests.post(urljoin(config['base'], '/normal/own'), headers={
        'a_id': str(a_id),
        'type': "reply2"
    }).json()
    if result:
        time.sleep(0.5)
        flag = True
        while flag:
            print("=======================================")
            print("|Reply ID|Content")
            print("--------------+------------------------")

            for res in result:
                ui.r2_idc_ui(res)

            print("=======================================")
            print("Input Sub Reply ID you want to view, b for back")
            print(">>> ", end="")
            p_id = input()
            if p_id.isdigit():
                comm.get_reply2(p_id, a_id)
            elif p_id == 'b':
                flag = False
            else:
                print("<Wrong Format> Must be a number")
    else:
        print("<Not Found> No such sub reply at all")

    input("<End> Press Enter to continue...")
