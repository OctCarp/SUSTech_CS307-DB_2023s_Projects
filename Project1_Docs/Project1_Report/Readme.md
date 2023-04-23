## SUSTech_CS307-DB_2023s_Project1

#### 小组成员：徐春晖 12110304，郭健阳 12111506

> 源码托管于 GitHub，将在项目 ddl 结束后基于 **MIT License** 协议开源，访问链接：
>
> https://github.com/OctCarp/SUSTech_CS307-DB_2023s_Projects

------

### 成员分工及贡献百分比 (平均分配)

徐春晖：

- Java 数据导入框架与编写

- Java 数据导入比较测试
- 项目相关的 SQL 语句编写
- 项目报告写作

郭健阳：

- Python 数据导入框架与编写
- 项目建表 SQL 语句框架与编写
- 项目 ER 图的绘制
- 项目报告写作

贡献百分比**相同，均为 50%**。

------

### Task 1: E-R Diagram

本小组使用 [drawio](https://www.diagrams.net/) 绘图工具，绘制本项目的 E-R 图，截图如下：

![Project1_ER.drawio](img\Project1_ER.drawio.png)




------

### Task 2: Relational Database Design

本项目使用 ` createtable.sql` 文件创建数据表，`createtable.sql` 文件使用 `postgresql` `DDL` 语法编写。

#### 数据库设计

使用 `DataGrip` 创建数据表并全选后通过右键 `Diagram > Show Visualization` 显示如下数据表设计及关系。并通过导入 `drawio` 调整后再导出为 `png` 文件。 

图片如下：

![Project1_DataGrip.drawio](img\Project1_DataGrip.drawio.png)



#### 设计思路及说明

##### 数据表及其各列含义说明

在整个项目中共创建了 11 个数据表，数据表和其中各列、外键的含义如下：

- `authors` 存储作者信息，包括为每位作者的编号 `a_id` （主键）、作者的名字 `author_name` 、作者注册的时间 `author_registration_time` 、作者的身份 ID `author_id` (18位)、作者的手机号码 `author_phone_number` （11位）。
- `posts` 存储帖子信息，包括帖子的 ID `p_id` （主键）、发帖作者的 ID `a_id` （外键，来自 `authors` 中的 `a_id` ）、帖子的标题 `title` 、帖子的内容 `content` 、帖子的发表时间 `posting_time` 、帖子发表的城市 `posting_city` （外键，来自 `cities` 中的 `city_name` ）
- `cities` 存储城市以及所在的国家信息，包括城市名 `city_name` （主键）、城市对应的国家名 `country_name` 
- `category` 存储类别名信息，包括每个类别的编号 `c_id` （主键）、类别的名字 `category_name` 
- `post_category` 存储帖子 ID  `p_id` （外键，来自 `posts` 中的 `p_id` ）和对应的类别编号 `c_id` （外键，来自 `category` 中的 `c_id` ），同时 `p_id` 和 `c_id` 联合作为主键
- `followed` 存储作者 ID `a_id` （外键，来自 `authors` 中的 `a_id` ）每位作者关注的人的 ID `followed_id` （外键，来自 `authors` 中的 `a_id` ），同时  `a_id` 和 `followed_id` 联合作为主键
- `favorited` 存储每个帖子 ID `p_id` （外键，来自 `posts` 中的 `p_id` ）和收藏该帖子的人的 ID `favorited_id` （外键，来自 `authors` 中的 `a_id` ），同时  `p_id` 和 `favorited_id` 联合作为主键
- `shared` 存储每个帖子 ID `p_id` （外键，来自 `posts` 中的 `p_id` ）和分享该帖子的人的 ID `shared_id` （外键，来自 `authors` 中的 `a_id` ），同时  `p_id` 和 `shared_id` 联合作为主键
- `liked` 存储每个帖子 ID `p_id` （外键，来自 `posts` 中的 `p_id` ）和点赞该帖子的人的 ID `liked_id` （外键，来自 `authors` 中的 `a_id` ），同时  `p_id` 和 `liked_id` 联合作为主键
- `replies` 存储每个帖子对应的一级回复，包括一级回复的编号 `r_id1` （主键）、帖子的 ID `p_id` （外键，来自 `posts` 中的 `p_id` ）、一级回复的内容 `reply_content` 、一级回复的星数 `reply_stars` 、一级回复的作者编号 `reply_author_id` 
- `secondary_replies` 存储每个一级回复对应的二级回复，包括二级回复的编号 `r_id2` （主键）、一级回复的编号 `r_id1` （外键，来自 `replies` 中的 `r_id1` ）、二级回复的内容 `secondary_reply_content` 、二级回复的星数 `secondary_reply_stars` 、二级回复的作者编号 `secondary_reply_author_id` 

##### 数据库构建的合理性

- 满足三大范式

  通过示意图可以看到，每个数据表的每一列都是不可分割的，仅有一个值。每个数据表都有主关键字，且主关键字都是 `unique` 的，其它数据元素能和主关键字一一对应。通过设计外键连接，我们将同一数据表中具有“传递”关系的数据列设计成不同的表格进行设计，不存在非关键字段对任一候选关键字段的传递函数依赖。可见，按以上设计思想设计的数据库满足三大范式的要求。

- 满足项目要求文档 `CS307-spring23-project1.pdf` 所要求的其它详细注意点。

------

### Task 3: Data Import



------

### Misc



------


### 项目报告到此结束，感谢您的阅读！
