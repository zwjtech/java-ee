#!/usr/bin/python
# coding=utf-8


def test_if():
    # 1.布尔变量
    # 这些结果是false: False, None, 0, "", (), [], {}。也就是说Python中的所有值都能被解释为直值。
    print True  # True
    print True == 1, False == 0  # True  True
    print True + False + 42  # 43  #

    # if elif  else
    number = input('Enter a number:')
    if number > 0:
        print 'The number is positive'
    elif number < 0:
        print 'The number is negative'
    else:
        print 'The number is zero'

    # is / is not: 同一性运算符
    x = y = [1, 2, 3]
    z = [1, 2, 3]
    print x == y, x == z  # True True
    print x is y, x is z  # True False


# test_if()


# 循环
def test_while_for():
    # 1.while
    x = 1
    while x <= 10:
        print x
        x += 1

    name = ""
    while not name:
        name = raw_input('Please enter your name:')
    print 'Hello , %s!' % name

    # 2.for
    word_list = ['this', 'is', 'an', 'ex', 'parrot']
    for word in word_list:
        print word

    for number in range(0, 2):
        print number

    # 3.循环遍历字典元素
    d = {'x': 1, 'y': 2, 'z': 3}
    for key in d:
        print key, 'corresponds to', d[key]

    for key, value in d.items():
        print key, 'corresponds to', value

    # 4.并行迭代：程序可以同时迭代两个序列
    names = ['liu', 'chang', 'wen']
    age = [20, 21, 22]
    for i in range(len(names)):
        print names[i], ' is ', age[i], 'years old'

    # 内建的zip函数：可以把两个序列'压缩'在一起，然后返回一个元组的列表
    print zip(names, age)  # [('liu', 20), ('chang', 21), ('wen', 22)]
    for name, age in zip(names, age):
        print name, ' is ', age, 'years old'

    # zip可以处理不等长的序列
    # 这里后面那个要使用xrange,它只会计算前5个数字，而如果改成range，就会计算所有的数字
    print zip(range(5), xrange(10000))  # [(0, 0), (1, 1), (2, 2), (3, 3), (4, 4)]

    # 列表推导式：轻量级循环
    # 是利用其它列表创建新列表的方法，类似于for
    print [(x, y) for x in range(2) for y in range(2)]
    # 等同于
    result = []
    for x in range(2):
        for y in range(2):
            result.append((x, y))


# test_while_for()



