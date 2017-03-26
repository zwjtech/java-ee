#!/usr/bin/python
# coding=utf-8
import math

__metaclass__ = type


# 1.方法/函数
def test_method():
    # 内建函数判断函数是否可调用
    x = 1
    y = math.sqrt
    print callable(x)  # False
    print callable(y)  # True


test_method()


# -----------1.位置参数
def fib(n):
    '斐波那契数列的实现'  # 如果在函数的开头写下字符串，它就会作为函数的一部分进行存储，这称为文档字符串
    result = [0, 1]
    for i in range(n - 2):
        result.append(result[-2] + result[-1])
    return result


print fib(3)
print fib.__doc__  # 斐波那契数列的实现


# ----------- 2.关键字参数--------
def hell0_1(name, greeting):
    print '%s, %s' % (name, greeting)


def hell0_2(greeting, name):
    print '%s, %s' % (greeting, name)


# 这么写跟位置没有关系
hell0_1(greeting='world', name='hello')  # hello, world
hell0_2(greeting='hello', name='world')  # hello, world


def hello_3(greeting='Hello', name='world'):
    print '%s, %s' % (greeting, name)


hello_3()  # hello, python
hello_3('nihao')  # nihao, world
hello_3('hello', 'python')  # hello, python


# 位置参数和关键字参数联合使用，把位置参数放在前面。少用这种方式

# -----------3，收集参数-------------
# * 号的意思就是"收集其余的位置参数"。
def print_params(title, *params):  # 类似 ...
    print title
    print params


print_params('Params:', 1, 2, 3)  # Params:  (1, 2, 3)
print_params('Params')  # Params ()  如果不提供任何收集的元素，params是一个空元组


# ** 可以处理关键字参数，返回的是一个字典
def print_params2(**params1):
    print params1


print_params2(x=1, y=2, z=3)  # {'y': 2, 'x': 1, 'z': 3}


# 参数收集的逆过程
def my_add(x, y): return x + y


params = (1, 2)
print my_add(*params)  # 3

params = {'name': 'world', 'greeting': 'hello'}
hell0_1(**params)  # world, hello
hell0_2(**params)  # hello, world


# ------------------------对象-------------------------
# 对象的优点：多态、封装、继承
# 创建自己的类
# __metaclass__ = type  # 确定使用新式类
class Person:
    def setName(self, name):
        self.name = name

    def getName(self):
        return self.name

    def greet(self):
        print 'Hello, world! I\'m %s.' % self.name


foo = Person()
# 在调用下面的函数时，foo自动将自己作为第一个参数传入函数中----因此形象地命名为self。
foo.setName('liu')
foo.greet()  # Hello, world! I'm liu.

# ------- 函数和方法
# self参数事实上正是方法和函数的区别。方法(更专业一点可以称绑定方法)将它们的第一个参数绑定到所属的实例上，因此无需显示提供该参数。