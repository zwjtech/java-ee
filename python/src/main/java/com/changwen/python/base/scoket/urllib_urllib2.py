#!/usr/bin/python3
# coding=utf-8


# ------------------urllib和urllib2模块---------------------
# 在能使用的各种网络函数为库中，功能最强大的可能是urllib和urllib2模块。
# 如果只使用简单的下载，urllib就足够了。如果需要使用HTTP验证和cookie, 或者要为自己的协议编写扩展程序的话，那么urllib2最好一些
# 1.打开远程文件
# from urllib import urlopen   # python3之前的写法
from urllib.request import urlopen  # python3的写法

# from urllib.request import urlretrieve  # python3的写法


# webpage = urlopen("http://www.baidu.com")
localfile = urlopen("file:/Users/changwen/Documents/test.txt")  # 如果没有网，可以用本地的文件测试
# urlopen返回的类文件对象支持close、read、readline和readlines方法，也支持迭代
localtext = localfile.read()
print(localtext)

# 2.获取远程文件
# urlretrieve: urllib为你下载文件并在本地文件中存储一个文件的副本
# urlretrieve('http://www.baidu.com', '/Users/changwen/Documents/baidu_webpage.html')


# ----------------SocketServer和它的朋友们--------------
# socketServer模块是标准库中很多服务器框架的基础，这些服务器框架包括BaseHTTPServer、SimpleHTTPServer等等，这些服务器框架都为基础服务器增加了特定的功能
# SocketServer包含了4个基本的类：针对TCP流式套接字的TCPServer; 针对UDP数据报套接字的UDPServer;
# 以及针对性不强的UnixStreamServer和UnixDatagramServer。TCPServer使用最多
