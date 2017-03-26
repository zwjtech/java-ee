#!/usr/bin/python3
# coding=utf-8


# 接下来我们写一个简单的客户端实例连接到以上创建的服务。端口号为 12345。
# socket.connect(hosname, port ) 方法打开一个 TCP 连接到主机为 hostname 端口为 port 的服务商。连接后我们就可以从服务端后期数据，记住，操作完成后需要关闭连接。
# 完整代码如下：
import socket

# 创建 socket 对象
s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

host = socket.gethostname()  # 获取本地主机名
port = 1234  # 设置端口好

s.connect((host, port))  # 连接服务，指定主机和端口

msg = s.recv(1024)  # 接收小于 1024 字节的数据

s.close()

print(msg.decode('utf-8'))
