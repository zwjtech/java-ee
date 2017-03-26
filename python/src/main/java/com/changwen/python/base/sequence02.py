#!/usr/bin/python
# coding=utf-8
from string import maketrans


# 序列(sequence)
# python 包含6种内建的序列，有列表、元组、字符串、Unicode字符串、buffer对象和xrange对象。其中第一个元素为1，最后一个为-1

# ---------------通用序列操作------
# 通用操作包括：索引(indexing)、分片(slicing)、加(adding)、乘和检查某个元素是否属于序列的成员(成员资格)、计算序列长度、找出最大元素和最小元素的内建函数
def test_sequence_common():
    # 1.索引：序列中所有元素都是从0开始递增的, 负数比较从右边也就是最后一个数开始计数
    greeting = 'hello'
    print greeting[0]  # h
    print greeting[-1]  # o

    # 2.分片：访问一定范围的元素
    x = [1, 2, 3, 4, 5]
    print x[1:2]  # [2]   就是[1：2)

    print x[-2:0]  # []
    print x[-2:]  # [4, 5]  访问最后最后2个数
    print x[:2]  # [1, 2] 获取前2个数
    y = x[:]
    print y  # [1, 2, 3, 4, 5]  复制整个序列

    # 3.步长: 默认是1，
    numbers = [1, 2, 3, 4, 5, 6]
    print numbers[0:6:2]  # [1, 3, 5]
#    print numbers[0:6:0]  # ValueError: slice step cannot be zero
    print numbers[6:0:-1]  # [6, 5, 4, 3, 2]  如果为负数，分片从右到左提取元素

    # 4.序列相加: 同种类型才能相加
    print [1, 2] + [3, 4]  # [1, 2, 3, 4]
    print [1, 2] + ['a']  # [1, 2, 'a']
#   print [1, 2] + 'a'  # TypeError: can only concatenate list (not "str") to list

    # 5.乘法
    print 'python' * 5  # pythonpythonpythonpythonpython

    # 6.None、空列表和初始化
    print [] * 5  # [] 空列表可以用[]表示

    # 创建一个占用5个元素空间，却不包括任何有用内容的列表
    print [None] * 5  # [None, None, None, None, None]

    # 7.成员资格：使用in运算符判断一个值是否在序列中
    test1 = 'python'
    print 'y' in test1  # True

    # 8.长度、最小值和最大值
    test2 = [1, 2, 8, 3]
    print len(test2)  # 4
    print max(test2)  # 8
    print min(test2)  # 1


# test_sequence_common()


# 其中列表和元组的主要区别在于：列表可以修改，元组则不能。
# 以下两个原因表明，元组不可用list替代
#   1.元组可以在映射(和集合的成员)中当作键使用 ---- 而列表不行
#   2.元组作为很多内建函数和方法的返回值存在，也就是说你必须对元组进行处理。
# 一般情况下，列表可能更能满足对序列的所有需要

# 1.1.列表(list) ---- 是可变的
# ------------- 1.1.1.基本操作 -----------------
def test_list_base_operation():
    # 1.list函数：适用于所有类型的序列，而不是只有字符串
    print (list("hello"))  # ['h', 'e', 'l', 'l', 'o']

    # 2.改变列表：元素赋值
    x = [1, 2, 3]
    x[1] = 4
    print x  # [1, 4, 3]

    #    x[6] = 4
    #    print x  # IndexError: list assignment index out of range

    # 3.删除元素
    # 注意：删除是彻底删除，列表长度也变化了
    del x[1]  # del语句还有删除其他元素
    print x  # [1, 3]

    # 4.分片赋值
    name = list("changWenSir")
    name[5:7] = list("Liu")  # 从5开始替换，包括5，不包括7
    print name
    name[5:] = list("Liu")  # 5后面的全部替换
    print name

    numbers = [1, 5]
    numbers[1:1] = [2, 3, 4]  # 插入操作
    print numbers  # [1, 2, 3, 4, 5]
    numbers[1:4] = []  # 删除操作，这个和del number[1:4]结果一样
    print numbers  # [1, 5]


# test_list_base_operation()


# ----------------1.1.2.列表方法----------------
def test_list_method():
    # 1.append：用于在列表末尾追加新的对象
    lst = [1, 2, 3]  # 为什么不用list来表示一个列表了？因为list是一个内建函数，如果使用它作为变量名，就无法调用list函数了。
    lst.append(4)
    print lst

    # 2.count：用于统计某个元素在列表中出现的次数
    print [1, 2, 3, 1, [1, 2]].count(1)  # 2

    # 3.extend: 在列表的末尾一次性追加另一个序列中的多个值。也就是说，可以用新列表扩展原有的列表。
    list_a = [1, 2, 3]
    list_b = [4, 5, 6]
    list_a.extend(list_b)
    print list_a  # [1, 2, 3, 4, 5, 6]

    list_c = [1, 2, 3]
    list_d = [4, 5, 6]
    list_e = list_c + list_d
    print list_e  # [1, 2, 3, 4, 5, 6] 这种效率不如extend
    print list_c  # [1, 2, 3]

    list_c[len(list_c):] = list_d
    print list_c  # [1, 2, 3, 4, 5, 6] 用分片实现可读性不如extend

    # 4.index: 用于从列表中找出某个值第一个匹配的索引位置
    str_list = ['java', 'c', 'python']
    print str_list.index('python')  # 2
    #    print str_list.index("math")  # ValueError: 'math' is not in list
    print str_list[2]  # python

    # 5.insert: 用于将对象插入到列表中
    list_f = [1, 2, 3, 4]
    list_f.insert(2, 'four')
    print list_f  # [1, 2, 'four', 3, 4], 也可用分片来处理list_f[2:2] = ['four'] 不建议用

    # 6.pop: 移除表中的一个元素（默认是最后一个），并返回该元素的值，类似栈的出栈
    # 该方法是唯一一个既能修改列表又返回元素值（除了None）的列表方法
    list_g = [1, 2, 3]
    print list_g.pop()  # 3
    print list_g  # [1, 2]
    print list_g.pop(0)  # 1

    # 7.remove: 用于移除表中某个值的第一个匹配项
    # 它修改了列表却没有返回值，pop有返回值
    list_h = [1, 2, 1, 3]
    list_h.remove(1)
    print list_h  # [2, 1, 3] 只有第一次出身的值被移除了
    #    list_h.remove(5)  # ValueError: list.remove(x): x not in list
    print list_h

    # 8.reverse: 将列表中的元素反转，注意，会改变列表但不返回值
    list_i = [1, 2, 3, 4]
    list_i.reverse()
    print list_i  # [4, 3, 2, 1]

    # 9.sort: 对列表进行排序，会改变原来的列表
    list_j = [2, 5, 1, 4]
    list_j.sort()
    print list_j  # [1, 2, 4, 5]

    list_k = list_j.sort()  # Don't do this
    print list_k  # None sort修改了返回了空值！！！

    # 如何得到一个排序好的列表副本，但原来的列表不变
    x = [2, 5, 1, 4]
    y = x[:]  # x[:]得到的是包含了x所有元素的分片，这是一种很有效率的复制整个列表的方法
    y.sort()
    print x  # [2, 5, 1, 4]
    print y  # [1, 2, 4, 5]

    # 只是简单的把x赋值给y是没有用的
    y = x
    print y  # [2, 5, 1, 4]
    y.sort()
    print x  # [1, 2, 4, 5]
    print y  # [1, 2, 4, 5]

    x = [2, 5, 1, 4]
    y = sorted(x)
    print x  # [2, 5, 1, 4]
    print y  # [1, 2, 4, 5]
    # 如果想把元素按相反的顺序排序，可以先sort或sorted，再reverse

    # 10.高级排序


# test_list_method()

# 1.2.元组(tuple) ---- 不可变序列 --------------------
# 元组不能修改，如果你用逗号分隔了一些值，那么你就自动创建了元组。
def test_tuple_base():
    print 1, 2, 3
    print (1, 2, 3)  # 元组大部分是通过圆括号括起来的

    print 1,  # 1  实现包括一个值的元组：必须加个逗号，即使只有一个值
    print (1,)

    print 3 * (40 + 2)  # 126
    print 3 * (40 + 2,)  # (42, 42, 42)

    # tuple函数: 是一种类型，而不是函数。与list函数基本上是一致的。
    print tuple([1, 2, 3])  # (1, 2, 3)
    print tuple(('abc'))  # ('a', 'b', 'c')
    print tuple((1, 2, 3))  # (1, 2, 3)

    # 元组主要是创建和访问操作
    x = 1, 2, 3
    print x[1]  # 2
    print x[0:2]  # (1, 2) 元组的分片还是元组

# test_tuple_base()


# 1.3.字符串方法
def test_string_method():
    test2 = ('world', 'python')
    print "hello %s, my %s" % test2  # hello world, my python 打日志时会用到

    # 1.find: 在字符串中查找子串。它返回子串所在位置的最左端索引，如果没有返回-1
    # 与in的区别：in只能查找单个字符
    print 'hello, world'.find('ll')  # 2
    print 'hello, world'.find('wen')  # -1

    print 'hello, world'.find('ll', 0, 4)  # 2
    print 'hello, world'.find('ll', 0, 3)  # -1 ，不包括第二个索引

    # 2.join: 是split方法的逆方法，用来连接序列中的元素.
    # 注意：需要被连接的序列元素都必须是字符串
    dirs = "", 'usr', 'bin', 'python'
    print '/'.join(dirs)  # /usr/bin/python
    #    print dirs.join('/')  # AttributeError: 'tuple' object has no attribute 'join'

    seq = [1, 2, 3]
    #    print '+'.join(seq)  # TypeError: sequence item 0: expected string, int found

    # 3.spilt: 将字符串分割成序列
    # 注意：如果不提供任何分隔符，程序会把所有空格作为分隔符(空格、制表。换行等)
    print '1+2+3+4'.split("+")  # ['1', '2', '3', '4']

    # 4.lower upper与 title
    print 'aBC'.lower()  # abc
    print 'abc'.upper()  # ABC
    print 'abc'.title()  # Abc  单词首字母大写

    # 5.replace 与 translate: 后者只处理单个字符，但能同时进行多个替换。
    print "hello world, my world".replace("world", "python")  # hello python, my python

    #    1.from string import maketrans
    table1 = maketrans('world', "pytho")  # 这里的长度必须一样
    print "hello world, my world".translate(table1)  # hehhy pytho, my pytho

    # 6.strip: 去除两边(不包括内部)空格的 字符串
    print ' abc   '.strip()  # abc
    print ' / abc / '.strip('/')  # / abc /
    print ' / abc / '.strip(' /')  # abc


# test_string_method()
