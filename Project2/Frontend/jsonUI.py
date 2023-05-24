from util import newline


def post_ui(p):
    print("======================================================")
    print("|Post ID: %d" % p['p_id'])
    print("├-----------------------------------------------------")
    print("|Title: %s" % p['title'])
    print("|Author: %s" % p['a_name'])
    print("├-----------------------------------------------------")
    print('|' + newline(p['content']))
    print("├-----------------------------------------------------")
    print("|Time: %s" % p['time'])
    print("|City: %s" % p['city'])
    print('|' + ', '.join(p['tags']))
    print("======================================================")


def reply_ui(r):
    print("======================================================")
    print("|Reply ID: %d" % r['rs_id'])
    print("├-----------------------------------------------------")
    print("|Post ID: %d" % r['ori_id'])
    print("|Post Title: %s" % r['ori_c'])
    print("├-----------------------------------------------------")
    print("|Author: %s" % r['a_name'])
    print('|' + newline(r['content']))
    print("├-----------------------------------------------------")
    print("|Stars: %s" % r['stars'])
    print("======================================================")


def reply2_ui(r2):
    print("======================================================")
    print("|Sub Reply ID: %d" % r2['rs_id'])
    print("├-----------------------------------------------------")
    print("|Reply ID: %d" % r2['ori_id'])
    print("|Reply Content: %s" % r2['ori_c'])
    print("├-----------------------------------------------------")
    print("|Author: %s" % r2['a_name'])
    print('|' + newline(r2['content']))
    print("├-----------------------------------------------------")
    print("|Stars: %s" % r2['stars'])
    print("======================================================")


def post_idc_ui(p):
    print("|%-7s|%s" % (p['id'], p['con']))


def r_idc_ui(r):
    print("|%-8s|%s" % (r['id'], r['con']))


def r2_idc_ui(r2):
    print("|%-12s|%s" % (r2['id'], r2['con']))


def author_idn_ui(a):
    print("|%-9s|%s" % (a['id'], a['con']))



