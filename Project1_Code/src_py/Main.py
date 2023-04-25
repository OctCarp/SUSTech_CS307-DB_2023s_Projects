import json
import psycopg2
import random
from datetime import datetime, timedelta
import time

init_sql = open('../src_sql/drop_create.sql').read()
dis_triggers = open('../src_sql/disable_triggers.sql').read()
en_triggers = open('../src_sql/enable_triggers.sql').read()
with open('../resource/posts.json') as f:
    posts = json.load(f)
with open('../resource/replies.json') as f:
    replies = json.load(f)

db = ['localhost', '5432', 'checker', '123456', 'CS307_Project1_P']

ins_author = """INSERT INTO authors (a_id, author_name, author_registration_time, author_id, author_phone_number) 
        VALUES (%s, %s, %s, %s, %s)"""
ins_post = """INSERT INTO posts (p_id, a_id, title, content, posting_time, posting_city)
        VALUES (%s, %s, %s, %s, %s, %s)"""
ins_city = "INSERT INTO cities (city_name, city_country) VALUES (%s, %s)"
ins_cate = "INSERT INTO category (c_id, category_name) VALUES (%s, %s)"
ins_post_cate = "INSERT INTO post_category (p_id, c_id) VALUES (%s, %s)"
ins_followed = "INSERT INTO followed (a_id, followed_id) VALUES (%s, %s)"
ins_favorited = "INSERT INTO favorited (p_id, favorited_id) VALUES (%s, %s)"
ins_liked = "INSERT INTO liked (p_id, liked_id) VALUES (%s, %s)"
ins_share = "INSERT INTO shared (p_id, shared_id) VALUES (%s, %s)"
ins_reply = """INSERT INTO replies (r_id1, p_id, reply_content, reply_stars, reply_author_id)
        VALUES (%s, %s, %s, %s, %s)"""
ins_sec_reply = """INSERT INTO secondary_replies (r_id2, r_id1,secondary_reply_content, secondary_reply_stars,
        secondary_reply_author_id) VALUES (%s, %s, %s, %s, %s)"""

Authors = []
Cities = []
Category = []
Author_id = []
Author_phone = []

reply1 = []
reply2 = []

Start_Date = datetime.strptime('2000-01-01 00:00:00', '%Y-%m-%d %H:%M:%S')
End_Date = datetime.strptime('2022-12-31 23:59:59', '%Y-%m-%d %H:%M:%S')


def init_table():
    init_conn = psycopg2.connect(host=db[0], port=db[1], user=db[2], password=db[3], database=db[4])
    init_cur = init_conn.cursor()
    init_cur.execute(init_sql)
    init_conn.commit()
    init_cur.close()
    init_conn.close()


def generate_random_length(length):
    rand_num = ''.join([str(random.randint(0, 9)) for _ in range(length)])
    return rand_num


def generate_random_time(start_date, end_date):
    time_format = '%Y-%m-%d %H:%M:%S'
    time_delta = (end_date - start_date).total_seconds()
    rand_num = random.randint(0, int(time_delta))
    rand_time = start_date + timedelta(seconds=rand_num)
    return rand_time.strftime(time_format)


def generate_author(in_name):
    Authors.append(in_name)
    in_a_id = Authors.index(in_name)
    in_random_id = generate_random_length(18)
    in_random_number = generate_random_length(11)

    while in_random_id in Author_id:
        in_random_id = generate_random_length(18)

    while in_random_number in Author_phone:
        in_random_number = generate_random_length(11)

    in_random_time = generate_random_time(Start_Date, End_Date)
    cur.execute(ins_author, (in_a_id, in_name, in_random_time, in_random_id, in_random_number))


def import_post_author():
    for post in posts:
        post_id = post['Post_ID']
        title = post['Title']
        content = post['Content']
        posting_time = post['Posting_Time']
        author = post['Author']
        author_registration_time = post['Author_Registration_Time']
        author_id = post['Author_ID']
        author_phone = post['Author_Phone']
        posting_city = post['Posting_City']

        last_comma_index = posting_city.rfind(',')
        city_name = posting_city[:last_comma_index]
        city_country = posting_city[last_comma_index + 2:]

        if author not in Authors:
            Authors.append(author)
            Author_id.append(author_id)
            Author_phone.append(author_phone)
        a_id = Authors.index(author)

        if city_name not in Cities:
            Cities.append(city_name)
            cur.execute(ins_city, (city_name, city_country))

        cur.execute(ins_author, (a_id, author, author_registration_time, author_id, author_phone))
        cur.execute(ins_post,
                    (str(post_id), str(a_id), title, content, posting_time, city_name))

        cates = post['Category']
        for cate in cates:
            if cate not in Category:
                Category.append(cate)
                c_id = Category.index(cate)
                cur.execute(ins_cate, (str(c_id), cate))
            else:
                c_id = Category.index(cate)

            cur.execute(ins_post_cate, (str(post_id), str(c_id)))


def import_post_misc():
    for post in posts:
        author = post['Author']
        post_id = post['Post_ID']

        followed = post['Author_Followed_By']
        for name in followed:
            if name not in Authors:
                generate_author(name)
            a_id = Authors.index(author)
            followed_id = Authors.index(name)

            cur.execute(ins_followed, (a_id, followed_id))

        favorite = post['Author_Favorited']
        for name in favorite:
            if name not in Authors:
                generate_author(name)
            favorited_id = Authors.index(name)

            cur.execute(ins_favorited, (post_id, favorited_id))

        share = post['Author_Shared']
        for name in share:
            if name not in Authors:
                generate_author(name)
            shared_id = Authors.index(name)

            cur.execute(ins_share, (post_id, shared_id))

        like = post['Author_Liked']
        for name in like:
            if name not in Authors:
                generate_author(name)
            liked_id = Authors.index(name)

            cur.execute(ins_liked, (post_id, liked_id))


def import_reply():
    for reply in replies:
        post_id = reply['Post_ID']
        reply_content = reply['Reply_Content']
        reply_stars = reply['Reply_Stars']
        reply_author = reply['Reply_Author']
        secondary_reply_content = reply['Secondary_Reply_Content']
        secondary_reply_stars = reply['Secondary_Reply_Stars']
        secondary_reply_author = reply['Secondary_Reply_Author']

        if reply_author not in Authors:
            generate_author(reply_author)

        if secondary_reply_author not in Authors:
            generate_author(secondary_reply_author)

        if reply_content not in reply1:
            reply1.append(reply_content)
            r_id1 = reply1.index(reply_content)
            reply_author_id = Authors.index(reply_author)

            cur.execute(ins_reply, (r_id1, post_id, reply_content, reply_stars, reply_author_id))

        r_id1 = reply1.index(reply_content)

        if secondary_reply_content not in reply2:
            reply2.append(secondary_reply_content)
            r_id2 = reply2.index(secondary_reply_content)
            secondary_reply_author_id = Authors.index(secondary_reply_author)

            cur.execute(ins_sec_reply, (r_id2, r_id1, secondary_reply_content,
                                        secondary_reply_stars, secondary_reply_author_id))


if __name__ == '__main__':
    init_table()

    start_time = time.time()

    conn = psycopg2.connect(host=db[0], port=db[1], user=db[2], password=db[3], database=db[4])
    cur = conn.cursor()

    # cur.execute(dis_triggers)
    import_post_author()
    import_post_misc()
    import_reply()
    # cur.execute(en_triggers)

    conn.commit()
    cur.close()
    conn.close()

    end_time = time.time()
    print(end_time - start_time)
