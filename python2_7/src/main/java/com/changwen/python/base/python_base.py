#!/usr/bin/python
# coding=utf-8

# 或者来中文编码
# -*- coding: UTF-8 -*-


def python_base():
    # print在python3.0中，不再是语句，而是函数
    print "你好 ，世界"
    print 'Age:', 22
    print 1, 2, 3  # 1 2 3 这不是元组
    print (1, 2, 3)  # 元组要这样表示

    # import: 把某件事作为另一件事导入
    # 从模块导入函数时，可以这样用
    # 1).import somemodule
    # 2).from somemodule import somefunction
    # 2).from somemodule import somefunction, anotherfunction
    # 4).from somemodule import *   # 只有确定自己想要从给定的模块导入所有功能时，才应该使用这个
    # 如果两个模块都有open函数，只需要使用第一种方式导入，然后像下面这样使用函数
    # module1.open(...)
    # module2.open(...)
    # 也可以这么处理
    # from module1 import open as open
    # from module2 import open as open2

    # 也可以在语句末尾增加一个as, 别名
    # import math as foobar
    # foobar.sqrt(4)
    # 或
    # from math import sqrt as foobar
    # foobar(4)

    # 一、赋值
    # 1.序列解包/递归解包： 将多个值的序列解开，然后放到变量的序列中。
    x, y, z = 1, 2, 3
    print x, y, z  # 1 2 3
    x, y = y, x
    print x, y, z  # 2 1 3

    values = 4, 5, 6
    print values  # (4, 5, 6)
    x, y, z = values
    print z  # 6

    # 当函数或方法返回元组(或其他序列或或迭代对象)时，这个特性尤其重要
    d = {'title': 'Python Web Site', 'url': 'http://www.python.org', 'spam': 0}
    key, value = d.popitem()
    print key, value  # url http://www.python.org

    # 注意左右变量数量要完全一致
#    x, y, z = 1, 2, 3, 4  # ValueError: too many values to unpack
    # python3的新功能
    # a,b,*rest = [1,2,3,4]  其中rest=[3,4]

    # 2.链式赋值
    # x = y = 1 与 x = 1  y=x效果一样
    # 但与x=somefunction() y=somefunction()不一定等价


python_base()
