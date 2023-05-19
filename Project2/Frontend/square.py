from comm import *
from util import inputByType as iB


def view_mode(va_id):
    flag = True

    while flag:
        print()
        print("Hello! It's view mode")
        print("============================================================")
        print("|                       [View Mode]                        |")
        print("|----------------------------------------------------------|")
        print("|                  1. View Posts by ID                     |")
        print("|                  2. View Reply by ID                     |")
        print("|                 3. View Sub Reply by ID                  |")
        print("|----------------------------------------------------------|")
        print("|                                         b. back          |")
        print("============================================================")
        print(">>> ", end="")
        op = input()
        if op == '1':
            p_id = iB("Post ID")
            get_post(p_id, va_id)
        elif op == '2':
            r_id1 = iB("Reply ID")
            get_reply(r_id1, va_id)
        elif op == '3':
            r_id2 = iB("Sub Reply ID")
            get_reply2(r_id2, va_id)
        elif op == 'b':
            flag = False
        else:
            print("<Wrong Selection>")


def post_op(p_id, pa_id, va_id):
    flag = True
    while flag:
        print("|----------------------------------------------------------|")
        print("     What do you want to do to Post ID: {0}?".format(p_id))
        print("|----------------------------------------------------------|")
        print("|  1.trans like      2.trans favorited       3.trans share |")
        print("|  4.Post author operation                   5.reply       |")
        print("|----------------------------------------------------------|")
        print("|                                         b. back          |")
        print("============================================================")
        print(">>> ", end="")
        op = input()
        if op == '1':
            post_op_trans(va_id, p_id, "liked")
        elif op == '2':
            post_op_trans(va_id, p_id, "favorited")
        elif op == '3':
            post_op_trans(va_id, p_id, "shared")
        elif op == '4':
            author_ops(va_id, pa_id)
        elif op == '5':
            send_reply(va_id, p_id)
        elif op == 'b':
            flag = False
        else:
            print("<Wrong selection>")


def reply_op(p_id, r_id1, ra_id, va_id):
    flag = True
    while flag:
        print("|----------------------------------------------------------|")
        print("      What do you want to do to Reply ID: {0}?".format(r_id1))
        print("|----------------------------------------------------------|")
        print("| 1.Upper Post      2.Follow Author       3.Reply          |")
        print("|----------------------------------------------------------|")
        print("|                                         b.Back           |")
        print("============================================================")
        print(">>> ", end="")
        op = input()
        if op == '1':
            get_post(p_id, va_id)
            return
        elif op == '2':
            author_opt_trans(va_id, ra_id, "follow")
        elif op == '3':
            send_reply2(va_id, r_id1)
        elif op == 'b':
            flag = False
        else:
            print("<Wrong selection>")


def reply2_op(r_id1, r_id2, ra_id, va_id):
    flag = True
    while flag:
        print("|----------------------------------------------------------|")
        print("      What do you want to do to Sub Reply ID: {0}?".format(r_id2))
        print("|----------------------------------------------------------|")
        print("|  1.Upper Reply                       2.Follow Author     |")
        print("|----------------------------------------------------------|")
        print("|                                          b. back         |")
        print("============================================================")
        print(">>> ", end="")
        op = input()

        if op == '1':
            get_reply(r_id1, va_id)
            return
        elif op == '2':
            author_opt_trans(va_id, ra_id, "follow")
        elif op == 'b':
            flag = False
        else:
            print("<Wrong selection>")


def author_ops(va_id, pa_id):
    flag = True
    while flag:
        print("|----------------------------------------------------------|")
        print("     What do you want to do to Author ID: {0}?".format(pa_id))
        print("|----------------------------------------------------------|")
        print("|  1.block                            2.trans follow       |")
        print("|----------------------------------------------------------|")
        print("|                                         b. back          |")
        print("============================================================")
        print(">>> ", end="")
        op = input()
        if op == '1':
            author_opt_trans(va_id, pa_id, "block")
        elif op == '2':
            author_opt_trans(va_id, pa_id, "follow")
        elif op == 'b':
            flag = False
        else:
            print("<Wrong selection>")
