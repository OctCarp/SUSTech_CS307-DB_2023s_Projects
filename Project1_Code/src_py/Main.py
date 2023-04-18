import json
import psycopg2
import random
from datetime import datetime, timedelta
import time

start_time = time.time()


def generate_random_18():
    rand_num = ''.join([str(random.randint(0, 9)) for _ in range(18)])
    return rand_num


def generate_random_11():
    rand_num = ''.join([str(random.randint(0, 9)) for _ in range(11)])
    return rand_num


def generate_random_time(start_date, end_date):
    time_format = '%Y-%m-%d %H:%M:%S'
    time_delta = (end_date - start_date).total_seconds()
    rand_num = random.randint(0, int(time_delta))
    rand_time = start_date + timedelta(seconds=rand_num)
    return rand_time.strftime(time_format)


Start_Date = datetime.strptime('2000-01-01 00:00:00', '%Y-%m-%d %H:%M:%S')
End_Date = datetime.strptime('2022-12-31 23:59:59', '%Y-%m-%d %H:%M:%S')


filename1 = 'D:/各科常用/数据库/project data and scripts/posts.json'  # the path of json file
with open(filename1) as f:
    posts = json.load(f)

conn = psycopg2.connect(database="project1", user="checker", password="123456", host="localhost", port="5432")
cur = conn.cursor()

Authors = []

for post in posts:
    # print(type(post))  # it is a dist type
    # print(post['Post ID'])
    # print(json.dumps(post, indent=1))

    Post_ID = post['Post ID']+1
    Title = post['Title']
    Content = post['Content']
    Posting_Time = post['Posting Time']
    Posting_City = post['Posting City']
    Author = post['Author']
    Author_Registration_Time = post['Author Registration Time']
    Author_ID = post['Author\'s ID']
    Author_Phone = post['Author\'s Phone']

    if Author not in Authors:
        Authors.append(Author)

    A_id = Authors.index(Author)+1

    cur.execute("INSERT INTO Authors (A_id, Author_name, Author_Registration_Time, Author_id, Author_Phone_number) "
                "VALUES (%s, %s, %s, %s, %s)",
                (str(A_id), Author, Author_Registration_Time, Author_ID, Author_Phone))

    cur.execute("INSERT INTO Posts (P_id, A_id, Title, Content, Author_Registration_Time, Posting_Time, Posting_City) "
                "VALUES (%s, %s, %s, %s, %s, %s, %s)",
                (str(Post_ID), str(A_id), Title, Content, Author_Registration_Time, Posting_Time, Posting_City))

    Cate = post['Category']
    for cate in Cate:
        cur.execute("INSERT INTO Category (P_id, P_Category) VALUES (%s, %s)", (Post_ID, cate))


for post in posts:
    Author = post['Author']
    Post_ID = post['Post ID']+1

    Follow = post['Authors Followed By']
    for name in Follow:
        if name not in Authors:
            Authors.append(name)
            A_id = Authors.index(name)+1
            random_id = generate_random_18()
            random_number = generate_random_11()
            random_time = generate_random_time(Start_Date, End_Date)
            cur.execute(
                "INSERT INTO Authors (A_id, Author_name, Author_Registration_Time, Author_id, Author_Phone_number) "
                "VALUES (%s, %s, %s, %s, %s)",
                (A_id, name, random_time, random_id, random_number))

        A_id = Authors.index(Author)+1
        cur.execute(
            "INSERT INTO Follower (A_id, Follower_name) "
            "VALUES (%s, %s)",
            (A_id, name))

    Favorite = post['Authors Who Favorited the Post']
    for name in Favorite:
        if name not in Authors:
            Authors.append(name)
            A_id = Authors.index(name)+1
            random_id = generate_random_18()
            random_number = generate_random_11()
            random_time = generate_random_time(Start_Date, End_Date)
            cur.execute(
                "INSERT INTO Authors (A_id, Author_name, Author_Registration_Time, Author_id, Author_Phone_number) "
                "VALUES (%s, %s, %s, %s, %s)",
                (A_id, name, random_time, random_id, random_number))

        cur.execute(
            "INSERT INTO Favorited (P_id, Favorited_name) "
            "VALUES (%s, %s)",
            (Post_ID, name))

    Share = post['Authors Who Shared the Post']
    for name in Share:

        if name not in Authors:
            Authors.append(name)
            A_id = Authors.index(name)+1
            random_id = generate_random_18()
            random_number = generate_random_11()
            random_time = generate_random_time(Start_Date, End_Date)
            cur.execute(
                "INSERT INTO Authors (A_id, Author_name, Author_Registration_Time, Author_id, Author_Phone_number) "
                "VALUES (%s, %s, %s, %s, %s)",
                (A_id, name, random_time, random_id, random_number))

        cur.execute(
            "INSERT INTO Shared (P_id, Shared_name) "
            "VALUES (%s, %s)",
            (Post_ID, name))

    Like = post['Authors Who Shared the Post']
    for name in Like:
        if name not in Authors:
            Authors.append(name)
            A_id = Authors.index(name)+1
            random_id = generate_random_18()
            random_number = generate_random_11()
            random_time = generate_random_time(Start_Date, End_Date)
            cur.execute(
                "INSERT INTO Authors (A_id, Author_name, Author_Registration_Time, Author_id, Author_Phone_number) "
                "VALUES (%s, %s, %s, %s, %s)",
                (A_id, name, random_time, random_id, random_number))

        cur.execute(
            "INSERT INTO Liked (P_id, Liked_name) "
            "VALUES (%s, %s)",
            (Post_ID, name))

conn.commit()
cur.close()
conn.close()

filename2 = 'D:/各科常用/数据库/project data and scripts/replies.json'  # the path of json file
with open(filename2) as f:
    replies = json.load(f)

conn = psycopg2.connect(database="project1", user="checker", password="123456", host="localhost", port="5432")
cur = conn.cursor()

reply1 = []
reply2 = []
recordpostid = 1
recordreplyid = 1

for reply in replies:
    Post_ID = reply['Post ID']+1
    if Post_ID == recordpostid:
        Reply_Content = reply['Reply Content']
        Reply_Stars = reply['Reply Stars']
        Reply_Author = reply['Reply Author']
        Secondary_Reply_Content = reply['Secondary Reply Content']
        Secondary_Reply_Stars = reply['Secondary Reply Stars']
        Secondary_Reply_Author = reply['Secondary Reply Author']

        if Reply_Author not in Authors:
            Authors.append(Reply_Author)
            A_id = Authors.index(Reply_Author)+1
            random_id = generate_random_18()
            random_number = generate_random_11()
            random_time = generate_random_time(Start_Date, End_Date)
            cur.execute(
                "INSERT INTO Authors (A_id, Author_name, Author_Registration_Time, Author_id, Author_Phone_number) "
                "VALUES (%s, %s, %s, %s, %s)",
                (A_id, Reply_Author, random_time, random_id, random_number))

        if Secondary_Reply_Author not in Authors:
            Authors.append(Secondary_Reply_Author)
            A_id = Authors.index(Secondary_Reply_Author)+1
            random_id = generate_random_18()
            random_number = generate_random_11()
            random_time = generate_random_time(Start_Date, End_Date)
            cur.execute(
                "INSERT INTO Authors (A_id, Author_name, Author_Registration_Time, Author_id, Author_Phone_number) "
                "VALUES (%s, %s, %s, %s, %s)",
                (A_id, Secondary_Reply_Author, random_time, random_id, random_number))

        if Reply_Content not in reply1:
            reply1.append(Reply_Content)
            R_id1 = reply1.index(Reply_Content)+1

            cur.execute(
                "INSERT INTO Replies (P_id, R_id1, Reply_Content, Reply_Stars, Reply_Author) "
                "VALUES (%s, %s, %s, %s, %s)",
                (Post_ID, R_id1, Reply_Content, Reply_Stars, Reply_Author))

        R_id1 = reply1.index(Reply_Content)+1
        if R_id1 == recordreplyid:
            if Secondary_Reply_Content not in reply2:
                reply2.append(Secondary_Reply_Content)
                R_id2 = reply2.index(Secondary_Reply_Content)+1
                cur.execute(
                    "INSERT INTO Secondary_Replies (P_id, R_id1, R_id2, "
                    "Secondary_Reply_Content, Secondary_Reply_Stars, Secondary_Reply_Author) "
                    "VALUES (%s, %s, %s, %s, %s, %s)",
                    (Post_ID, R_id1, R_id2, Secondary_Reply_Content, Secondary_Reply_Stars, Secondary_Reply_Author))
        else:
            recordreplyid += 1
            if Secondary_Reply_Content not in reply2:
                reply2.append(Secondary_Reply_Content)
                R_id2 = reply2.index(Secondary_Reply_Content)+1
                cur.execute(
                    "INSERT INTO Secondary_Replies (P_id, R_id1, R_id2, "
                    "Secondary_Reply_Content, Secondary_Reply_Stars, Secondary_Reply_Author) "
                    "VALUES (%s, %s, %s, %s, %s, %s)",
                    (Post_ID, R_id1, R_id2, Secondary_Reply_Content, Secondary_Reply_Stars, Secondary_Reply_Author))

    else:
        reply1.clear()
        reply2.clear()
        recordpostid += 1
        recordreplyid = 1
        Reply_Content = reply['Reply Content']
        Reply_Stars = reply['Reply Stars']
        Reply_Author = reply['Reply Author']
        Secondary_Reply_Content = reply['Secondary Reply Content']
        Secondary_Reply_Stars = reply['Secondary Reply Stars']
        Secondary_Reply_Author = reply['Secondary Reply Author']

        if Reply_Author not in Authors:
            Authors.append(Reply_Author)
            A_id = Authors.index(Reply_Author)+1
            random_id = generate_random_18()
            random_number = generate_random_11()
            random_time = generate_random_time(Start_Date, End_Date)
            cur.execute(
                "INSERT INTO Authors (A_id, Author_name, Author_Registration_Time, Author_id, Author_Phone_number) "
                "VALUES (%s, %s, %s, %s, %s)",
                (A_id, Reply_Author, random_time, random_id, random_number))

        if Secondary_Reply_Author not in Authors:
            Authors.append(Secondary_Reply_Author)
            A_id = Authors.index(Secondary_Reply_Author)+1
            random_id = generate_random_18()
            random_number = generate_random_11()
            random_time = generate_random_time(Start_Date, End_Date)
            cur.execute(
                "INSERT INTO Authors (A_id, Author_name, Author_Registration_Time, Author_id, Author_Phone_number) "
                "VALUES (%s, %s, %s, %s, %s)",
                (A_id, Secondary_Reply_Author, random_time, random_id, random_number))

        if Reply_Content not in reply1:
            reply1.append(Reply_Content)
            R_id1 = reply1.index(Reply_Content)+1
            cur.execute(
                "INSERT INTO Replies (P_id, R_id1, Reply_Content, Reply_Stars, Reply_Author) "
                "VALUES (%s, %s, %s, %s, %s)",
                (Post_ID, R_id1, Reply_Content, Reply_Stars, Reply_Author))

        R_id1 = reply1.index(Reply_Content)+1
        if R_id1 == recordreplyid:
            if Secondary_Reply_Content not in reply2:
                reply2.append(Secondary_Reply_Content)
                R_id2 = reply2.index(Secondary_Reply_Content)+1
                cur.execute(
                    "INSERT INTO Secondary_Replies (P_id, R_id1, R_id2, "
                    "Secondary_Reply_Content, Secondary_Reply_Stars, Secondary_Reply_Author) "
                    "VALUES (%s, %s, %s, %s, %s, %s)",
                    (Post_ID, R_id1, R_id2, Secondary_Reply_Content, Secondary_Reply_Stars, Secondary_Reply_Author))
        else:
            recordreplyid += 1
            if Secondary_Reply_Content not in reply2:
                reply2.append(Secondary_Reply_Content)
                R_id2 = reply2.index(Secondary_Reply_Content)+1
                cur.execute(
                    "INSERT INTO Secondary_Replies (P_id, R_id1, R_id2, "
                    "Secondary_Reply_Content, Secondary_Reply_Stars, Secondary_Reply_Author) "
                    "VALUES (%s, %s, %s, %s, %s, %s)",
                    (Post_ID, R_id1, R_id2, Secondary_Reply_Content, Secondary_Reply_Stars, Secondary_Reply_Author))

conn.commit()
cur.close()
conn.close()

end_time = time.time()
cost_time = end_time - start_time
print(cost_time)
