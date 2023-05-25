from comm import *
from util import inputByType as iB
from util import choose_post_page
from util import inputNotBlank as nB



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
        print("|                   4. View All Posts                      |")
        print("|          5. View All Posts ordered by posting time       |")
        print("|            6. View TOP 10 hottest search list            |")
        print("|                7. Multi-parameter search                 |")
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
        elif op == '4':
            page_num = choose_post_page()
            get_all_post(page_num, va_id)
        elif op == '5':
            page_num = choose_post_page()
            get_all_post_obt(page_num, va_id)
        elif op == '6':
            get_hot_list(va_id)
        elif op == '7':
            Multi_parameter_search(va_id)
        elif op == 'b':
            flag = False
        else:
            print("<Wrong Selection>")


def change_page(page_num, va_id):
    print("=============================================================")
    print("|        Please Enter Character To Select Next Step         |")
    print("|-----------------------------------------------------------|")
    print("|    n. next page    l. last page    j. jump to ___ page    |")
    print("|                                                           |")
    print("|      c. check content of post which PostID is ___         |")
    print("|                                                           |")
    print("|                                       b. back             |")
    print("=============================================================")
    print(">>> ", end="")
    next_step = input()
    if next_step == 'n':
        get_all_post(int(page_num) + 1, va_id)
    elif next_step == 'l':
        get_all_post(max(int(page_num) - 1, 1), va_id)
    elif next_step == 'j':
        print("jump to ___ page, input a positive number")
        jump_page = input()
        if jump_page.isdigit():
            get_all_post(jump_page, va_id)
        else:
            print("<Wrong input format, please input positive number>")
            change_page(page_num, va_id)
    elif next_step == 'c':
        print("check content of post which PostID is ___, input a positive number")
        post_id = input()
        if post_id.isdigit():
            get_post(post_id, va_id)
        else:
            print("<Wrong input format, please input positive number>")
            change_page(page_num, va_id)

    elif next_step == 'b':
        view_mode(va_id)
    else:
        print("<Wrong Selection>")


def change_page_obt(page_num, va_id):
    print("=============================================================")
    print("|        Please Enter Character To Select Next Step         |")
    print("|-----------------------------------------------------------|")
    print("|    n. next page    l. last page    j. jump to ___ page    |")
    print("|                                                           |")
    print("|      c. check content of post which PostID is ___         |")
    print("|                                                           |")
    print("|                                       b. back             |")
    print("=============================================================")
    print(">>> ", end="")
    next_step = input()
    if next_step == 'n':
        get_all_post_obt(int(page_num) + 1, va_id)
    elif next_step == 'l':
        get_all_post_obt(max(int(page_num) - 1, 1), va_id)
    elif next_step == 'j':
        print("jump to ___ page, input a positive number")
        jump_page = input()
        if jump_page.isdigit():
            get_all_post_obt(jump_page, va_id)
        else:
            print("<Wrong input format, please input positive number>")
            change_page(page_num, va_id)
    elif next_step == 'c':
        print("check content of post which PostID is ___, input a positive number")
        post_id = input()
        if post_id.isdigit():
            get_post(post_id, va_id)
        else:
            print("<Wrong input format, please input positive number>")
            change_page(page_num, va_id)

    elif next_step == 'b':
        view_mode(va_id)
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
        print("|  6.View all replies                                      |")
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
        elif op == '6':
            get_post_replies(va_id, p_id)
        elif op == 'b':
            flag = False
        else:
            print("<Wrong selection>")


def post_op_anonymous(p_id, va_id):
    flag = True
    while flag:
        print("|----------------------------------------------------------|")
        print("      Do you want to reply Post ID: {0}?".format(p_id))
        print("|----------------------------------------------------------|")
        print("|                      c. confirm                          |")
        print("|----------------------------------------------------------|")
        print("|                        b. back                           |")
        print("============================================================")
        print(">>> ", end="")
        op = input()
        if op == 'c':
            send_reply(va_id, p_id)
            flag = False
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
        print("| 4.View all sub replies                                   |")
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
        elif op == '4':
            get_reply_replies(va_id, r_id1)
        elif op == 'b':
            flag = False
        else:
            print("<Wrong selection>")


def reply_op_anonymous(r_id1, va_id):
    flag = True
    while flag:
        print("|----------------------------------------------------------|")
        print("       Do you want to reply Reply ID: {0}?".format(r_id1))
        print("|----------------------------------------------------------|")
        print("|                       c. confirm                         |")
        print("|----------------------------------------------------------|")
        print("|                         b.Back                           |")
        print("============================================================")
        print(">>> ", end="")
        op = input()
        if op == 'c':
            send_reply2(va_id, r_id1)
            flag = False
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


def Multi_parameter_search(va_id):
    flag = True

    while flag:
        print()
        print("============================================================")
        print("|                [Multi_parameter_search]                  |")
        print("|----------------------------------------------------------|")
        print("|        c. confirm                       b. back          |")
        print("============================================================")
        print(">>> ", end="")
        op = input()
        if op == 'c':
            print("Please input keyword and category inorder")
            print(">>> ", end="")
            keyword = nB("key word")
            print(">>> ", end="")
            category = nB("category")
            multi_parameter_search(va_id, keyword, category)
            print("Input Post ID you want to view, b for back")
            print(">>> ", end="")
            p_id = input()
            if p_id.isdigit():
                get_post(p_id, va_id)
            elif p_id == 'b':
                flag = False
            else:
                print("<Wrong Format> Must be a number")

        elif op == 'b':
            flag = False
        else:
            print("<Wrong Selection>")
