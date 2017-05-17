#!/usr/bin/python
# coding=utf-8
import MySQLdb

conn = MySQLdb.connect(host='583bff7834bbd.gz.cdb.myqcloud.com',  # 远程主机的ip地址，
                       user='fsreaduser',  # MySQL用户名
                       db='fastschool',  # database名
                       passwd='Fastschool@1#pwd',  # 数据库密码
                       port=17049,  # 数据库监听端口，默认3306
                       charset="utf8")  # 指定utf8编码的连接
cursor = conn.cursor()  # 创建一个光标，然后通过光标执行sql语句
cursor.execute("select * from user where username='13699244586'")
values = cursor.fetchall()  # 取出cursor得到的数据
print values
cursor.close()
conn.close()  # 最后记得关闭光标和连接，防止数据泄露
