#!/usr/bin/python
# coding:utf-8
from copy import deepcopy


# 字典
# 映射：通过名字来引用值的数据结构。字典是Python中唯一内建的映射类型。
# 字典中的值并没有特殊的顺序，但都存储在一个特定的键下。键可以是数字、字符串、元组。类似map

# 字典与序列的相同点
# len(d) 返回d中项的数量
# d[k]：返回关联到键k上的值
# d[k]=v：将值v关联到键k上
# del d[k]：删除键为k的项
# k in d：检查d中是否有含有键为k的项

# 区别
# 键类型：字典的键不一定为整形数据，可以是任意不可变类型
# 自动添加：字典中的键不存在也可以为它赋值
# 成员资格：字典中k in d 查找的是键而不是值。列表中 v in l查找的是值，而不是索引。
def test_base():
    # 创建字典，字典中的键是唯一的，值并不唯一。空字典{}
    phone_book = {'Alice': '0101-111', 'Beth': '1232', 'wen': '135'}

    # dict：并不是真正的函数，是个类型，就像list, tuple和str
    items = [('name', 'changwen'), ('age', 22)]
    d = dict(items)
    print d  # {'age': 22, 'name': 'changwen'}
    print dict(name='changwen', age=22)  # {'age': 22, 'name': 'changwen'}

    x = {}
    x[42] = 'Foobar'
    print x  # {42: 'Foobar'}

    # 字典的格式化字符串
    print "wen phone number is %(wen)s" % phone_book  # wen phone number is 135


# test_base()


def test_method():
    # 1.clear：清除字典中所有的项，无返回值或说返回Nono
    d = {}
    d['name'] = 'liu'
    d['age'] = 22
    returned_value = d.clear()
    print d  # {}
    print returned_value  # None

    # 这个方法有什么作用
    x = {}
    y = x
    x['key'] = 'value'
    x = {}
    print y  # {'key': 'value'}

    x = {}
    y = x
    x['key'] = 'value'
    x.clear()
    print y  # {}

    # 2.copy：返回一个具有相同键值对的新字典，是浅复制(shallow copy),因为值本身就是相同的，而不是副本
    x = {'username': 'admin', 'machines': ['foo', 'bar', 'baz']}
    y = x.copy()
    y['username'] = 'user'
    y['machines'].remove('bar')
    print y  # {'username': 'user', 'machines': ['foo', 'baz']}  # 替换/更新 原字典不受影响
    print x  # {'username': 'admin', 'machines': ['foo', 'baz']}  删除/增加 原字典受影响

    # 避免这个问题方法之一：使用深复制(deep copy)
    # from copy import deepcopy
    d = {'names': ['wen', 'chang']}
    c = d.copy()
    dc = deepcopy(d)
    d['names'].append('liu')
    print c  # {'names': ['wen', 'chang', 'liu']}
    print dc  # {'names': ['wen', 'chang']}

    # 3.fromkeys: 使用给定的键建立新的字典，每个键都对应一个默认的值None
    print {}.fromkeys(['name', 'age'])  # {'age': None, 'name': None}

    # 上面使用一个空{}会有些多余，直接用dict调用该方法(dict是所有字典的类型)
    print dict.fromkeys(['name', 'age'])  # {'age': None, 'name': None}

    # 使用默认值
    print dict.fromkeys(['name', 'age'], '(unknown)')  # {'age': '(unknown)', 'name': '(unknown)'}

    # 4.get: 更宽松的访问字典项的方法。该方法访问字典中不存在的项时不会出错
    d = {}
    #    print d['name']  # KeyError: 'name'
    print d.get('name')  # None
    print d.get('name', {})  # {}

    d['name'] = 'liu'
    print d.get('name', {})  # liu

    # 5.has_key 相当于 in d。Python3.0不包括这个函数
    d = {}
    print d.has_key('name')  # False

    # 6.items和iteritems: 很多情况下使用iteritems
    # items以列表方式返回，iteritems返回一个迭代器对象
    d = {'title': 'Python Web Site', 'url': 'http://www.python.org', 'spam': 0}
    print d.items()  # [('url', 'http://www.python.org'), ('spam', 0), ('title', 'Python Web Site')]

    it = d.iteritems()
    print it  # <dictionary-itemiterator object at 0x1098a0310>
    print list(it)  # [('url', 'http://www.python.org'), ('spam', 0), ('title', 'Python Web Site')]

    # 7.keys方法将字典中的键以列表形式返回。iterkeys:返回针对键的迭代器
    # 8.pop: 将这个键-值从字典中移除，返回值是给定键的值
    d = {'x': 1, 'y': 2}
    r = d.pop('x')
    print r  # 1
    print d  # {'y': 2}

    # 9.popitem: 弹出随机的项
    # 类似list.pop(弹出列表的最后一个元素)，
    print d.popitem()  # ('y', 2)
    print d  # {}

    # 10.setdefault: 类似get方法，能够获得与给定键相关联的值。除些之外，还能在字典中不含有给定键的情况下设定相应的键值。
    d = {}
    print d.setdefault('name', 'username')  # username
    print d.setdefault('age')  # None

    d['birth'] = '0803'
    print d.setdefault('birth', '{}')  # 0803

    # 11.update: 利用一个字典更新另外一个字典, 原字典跟新的相比，没有则增加，有则覆盖
    d = {'x': 1, 'y': 2}
    re = {'x': 5, 'z': 6}
    d.update(re)
    print d  # {'y': 2, 'x': 5, 'z': 6}

    # 12.values方法以列表的形式返回字典中的值，itervalues返回的是迭代器
    d = {'x': 1, 'y': 2, 'z': 1}
    print d.values()  # [2, 1, 1]  返回值可以有重复的元素



test_method()
