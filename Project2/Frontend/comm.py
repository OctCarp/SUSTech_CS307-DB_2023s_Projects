import json
import time
import square as sqr
import jsonUI as ui
import requests
from config import config
from urllib.parse import urljoin
from util import inputByType as iB
from util import inputNotBlank as nB


def send_post(a_id):
    a_id = str(a_id)

    title = nB("title")
    content = nB("content")
    city = nB("location")
    tag = iB("tags, split by ',',word by word, s for skip")
    if tag == 's':
        tag = None
    cate = [word.strip().capitalize() for word in tag.split(",")]

    confirm = input("Enter to confirm, b for back")
    if confirm == 'b':
        return

    result = requests.post(
        urljoin(config['base'], '/normal/send/post'), headers={
            'a_id': a_id,
            'title': title,
            'content': content,
            'city': city,
            'cate': json.dumps(cate, ensure_ascii=False)
        }
    ).json()

    if result:
        print("Post Success!")
    else:
        print("Some thing Wrong!")

    input("<End> Press Enter to continue...")


def send_reply(a_id, p_id):
    a_id = str(a_id)
    p_id = str(p_id)

    content = nB("content")
    confirm = input("Enter to confirm, b for back")
    if confirm == 'b':
        return

    result = requests.post(
        urljoin(config['base'], '/normal/send/reply'), headers={
            'a_id': a_id,
            'p_id': p_id,
            'content': content
        }
    ).json()

    if result:
        print("Reply Success!")
    else:
        print("Some thing Wrong!")

    input("<End> Press Enter to continue...")


def send_reply2(a_id, r_id1):
    a_id = str(a_id)
    r_id1 = str(r_id1)

    content = nB("content")
    confirm = input("Enter to confirm, b for back")
    if confirm == 'b':
        return

    result = requests.post(
        urljoin(config['base'], '/normal/send/reply2'), headers={
            'a_id': a_id,
            'r_id1': r_id1,
            'content': content
        }
    ).json()

    if result:
        print("Reply Success!")
    else:
        print("Some thing Wrong!")

    input("<End> Press Enter to continue...")


def get_post(p_id, va_id):
    p_id = str(p_id)
    va_id = str(va_id)
    if p_id.isdigit():
        result = requests.post(
            urljoin(config['base'], '/view/post'), headers={
                'p_id': p_id,
                'va_id': va_id
            }
        ).json()

        if result:
            ui.post_ui(result)
            print("<Select> Press 1 to operation or Enter to continue...")
            print(">>> ", end="")

            op = input()
            if op == '1':
                if va_id == "-1":
                    print("<No Permission> Please login")
                else:
                    sqr.post_op(p_id, result['a_id'], va_id)
            else:
                return

        else:
            print("<Not Found> No such Post ID")

    else:
        print("<Wrong Format> p_id is not valid")

    input("<End> Press Enter to continue...")


def get_reply(r_id1, va_id):
    r_id1 = str(r_id1)
    va_id = str(va_id)
    if r_id1.isdigit():
        result = requests.post(
            urljoin(config['base'], '/view/reply'), headers={
                'r_id1': r_id1,
                'va_id': va_id
            }
        ).json()

        if result:
            ui.reply_ui(result)
            print("<Select> Press 1 to operation or Enter to continue...")
            print(">>> ", end="")

            op = input()
            if op == '1':
                if va_id == "-1":
                    print("<No Permission> Please login")
                else:
                    sqr.reply_op(result['ori_id'], r_id1, result['a_id'], va_id)
            else:
                return

        else:
            print("<Not Found> No such Post ID")
    else:
        print("<Wrong Format> Reply ID is not valid")

    input("<End> Press Enter to continue...")


def get_reply2(r_id2, va_id):
    r_id2 = str(r_id2)
    va_id = str(va_id)
    if r_id2.isdigit():
        result = requests.post(
            urljoin(config['base'], '/view/reply2'), headers={
                'r_id2': r_id2,
                'va_id': va_id
            }
        ).json()

        if result:
            ui.reply2_ui(result)
            print("<Select> Press 1 to operation or Enter to continue...")
            print(">>> ", end="")

            op = input()
            if op == '1':
                if va_id == "-1":
                    print("<No Permission> Please login")
                else:
                    sqr.reply2_op(result['ori_id'], r_id2, result['a_id'], va_id)

        else:
            print("<Not Found> No such Post ID")
    else:
        print("<Wrong Format> Sub Reply ID is not valid")

    input("<End> Press Enter to continue...")


def author_opt_trans(va_id, pa_id, op_type):
    va_id = str(va_id)
    pa_id = str(pa_id)
    if va_id == pa_id:
        print("<Wrong> You can't do this for your self!")
        input("Press Enter to continue...")
        time.sleep(0.5)
        return

    has = requests.post(
        urljoin(config['base'], '/normal/authorOpt/ck'), headers={
            'a_id': va_id,
            'a_id2': pa_id,
            'type': op_type
        }
    ).json()

    if has:
        print("You have done %s on author ID: %s, do you want do undo? [b for back]" % (op_type, pa_id))
        print(">>> ", end="")

        confirm = input()
        if confirm == 'b':
            input("Withdraw, Press  Enter to Continue")
            return

        rec = requests.post(
            urljoin(config['base'], '/normal/authorOpt/un'), headers={
                'a_id': va_id,
                'a_id2': pa_id,
                'type': op_type
            }
        ).json()
        if rec:
            print("undo %s author id: %s Success!" % (op_type, pa_id))
        else:
            print("undo %s author id: %s Failed!" % (op_type, pa_id))
    else:
        print("Do you want to %s author ID: %s? [b for back]" % (op_type, pa_id))
        print(">>> ", end="")

        confirm = input()
        if confirm == 'b':
            input("Withdraw, Press  Enter to Continue")
            return

        rec = requests.post(
            urljoin(config['base'], '/normal/authorOpt/do'), headers={
                'a_id': va_id,
                'a_id2': pa_id,
                'type': op_type
            }
        ).json()
        if rec:
            print("do %s author id: %s Success!" % (op_type, pa_id))
        else:
            print("do %s author id: %s Failed!" % (op_type, pa_id))


def post_op_trans(va_id, p_id, op_type):
    va_id = str(va_id)
    p_id = str(p_id)
    has = requests.post(
        urljoin(config['base'], '/normal/postOpt/ck'), headers={
            'p_id': p_id,
            'a_id': va_id,
            'type': op_type
        }
    ).json()
    if has:
        print("You have done %s on Post ID: %s, do you want do undo? [b for back]" % (op_type, p_id))
        print(">>> ", end="")

        confirm = input()
        if confirm == 'b':
            input("Withdraw, Press  Enter to Continue")
            return

        rec = requests.post(
            urljoin(config['base'], '/normal/postOpt/un'), headers={
                'p_id': p_id,
                'a_id': va_id,
                'type': op_type
            }
        ).json()
        if rec:
            print("Post ID %s undo %s Success!" % (p_id, op_type))
        else:
            print("Post ID %s undo %s Failed!" % (p_id, op_type))
    else:
        print("Do you want to %s Post ID: %s? [b for back]" % (op_type, p_id))
        print(">>> ", end="")

        confirm = input()
        if confirm == 'b':
            input("Withdraw, Press  Enter to Continue")
            return

        rec = requests.post(
            urljoin(config['base'], '/normal/postOpt/do'), headers={
                'p_id': p_id,
                'a_id': va_id,
                'type': op_type
            }
        ).json()
        if rec:
            print("Post ID %s do %s Success!" % (p_id, op_type))
        else:
            print("Post ID %s do %s Failed!" % (p_id, op_type))
