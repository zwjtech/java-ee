#!/usr/bin/python
# coding=utf-8
__metaclass__ = type  # 确保类是新型的


# 下面这两个类中，NewStyle是新式的类，OldStyle是旧式的类。如果文件以__metaclass__ = type开始，那么两个类都是新式类。建议用新式类
# class NewStyle(object):
#    more_code_here
# class OldStyle:
#    more_code_here

# 1.构造方法
# 1.__init__ 使用最多
class FooBar:
    def __init__(self, value=42):
        self.somvar = value


print FooBar().somvar  # 42
print FooBar('This is a constructor argument.').somvar  # This is a constructor argument.


# A类定义了一个hello的方法，被B类继承
class A:
    def hello(self):
        print 'Hello, I\'m A'


class B(A):
    pass


A().hello()  # Hello, I'm A
B().hello()  # Hello, I'm A


# C类重写hello
class C(A):
    def hello(self):
        print 'Hello, I\'m B'


C().hello()  # Hello, I'm B


# 2.调用未绑定的超类构造方法
# 如果一个类的构造方法被重写，那就需要调用超类的构造方法，否则对象可能不会被正确的初始化
class Bird:
    def __init__(self):
        self.hungry = True

    def eat(self):
        if self.hungry:
            print 'hungry！ need eat'
            self.hungry = False
        else:
            print 'have eat, thanks'


class SongBird(Bird):
    def __init__(self):
        super(SongBird, self).__init__()  # 这是python3新版本的
        # Bird.__init__(self)  # 这个是python3之前的老版本。如果没写这个，下面sb.eat()会有异常
        self.sound = 'Squawk!'

    def sing(self):
        print self.sound


b = Bird()
b.eat()  # hungry！ need eat
b.eat()  # have eat, thanks

sb = SongBird()
sb.sing()  # Squawk!
sb.eat()  # AttributeError: 'SongBird' object has no attribute 'hungry'  因为在SongBird中，构造方法被重写


# -----------------属性-----------
class Rectangle:
    def __init__(self):
        self.width = 0
        self.height = 0

    def setSize(self, size):
        self.width, self.height = size

    def getSize(self):
        return self.width, self.height

        #   size = property(getSize(), setSize())


r = Rectangle()
r.width = 10
r.height = 5
print r.getSize()  # (10, 5)
# r.size

r.setSize((150, 100))
print r.width, r.height  # 150 100


# 1.静态方法和类成员方法
# 分别在创建时被装入staticmethod类型和classmethod类型的对象中。
# 静态方法的定义没有self参数，且能够被类本身直接调用。
# 类方法在定义时需要名为cls的类似于self的参数，类成员方法可以直接用类的具体对象调用。但cls参数是自动被绑定到类的。
class MyClass:
    @staticmethod  # 在python2.4中引入'装饰器'
    def smeth():
        print 'This is a static method'

    #    smeth = staticmethod(smeth)

    @classmethod
    def cmeth(cls):
        print 'This is a class method of', cls


# cmeth = classmethod(cmeth)


# 例子中没有实例类
MyClass.smeth()  # This is a static method
MyClass.cmeth()  # This is a class method of <class '__main__.MyClass'>


# ---------------------6.迭代器-----------------
# __iter__ 这个方法是迭代器规则的基础。只要该对象实现了这个方法，就能对这个对象进行迭代。
# __iter__ 方法会返回一个迭代器，所谓的迭代器就是具有next方法(该方法在调用时不需要任何参数)的对象。
#           在调用next方法时，迭代器会返回它的下一个值。如果next方法被调用，但迭代器没有值可以返回，就会引发一个StopIteration异常
# 注意：迭代器在Python3.0中有一些变化。在新的规则中，迭代器对象应该实现__next__方法，而不是next。next(it)等同于3之前的it.next()

# 为什么不使用列表？如果有一个函数可以一个接一个地计算值，那么可以在使用计算一个值时获取一个值，而列表是一次性获取所有值。
#  如果有很多值，列表会占用太多的内存。使用迭代器更通用、更简单、更优雅
class Fibs:
    def __init__(self):
        self.a = 0
        self.b = 1

    def next(self):
        self.a, self.b = self.b, self.a + self.b
        return self.a

    def __iter__(self):
        return self


# 正式的说法：一个实现了__iter__方法的对象是可迭代的，一个实现了next方法的对象则是迭代器
fibs = Fibs()
for f in fibs:
    if f > 5:
        print f
        break


# 2.从迭代器得到序列
# 大部分使用序列的情况下(除了在索引或者分片等操作中)，都能使用迭代器(或者可迭代对象)替换
class TestIterator:
    value = 0

    def next(self):
        self.value += 1
        if self.value > 10:
            raise StopIteration
        return self.value

    def __iter__(self):
        return self


ti = TestIterator()
# 使用list构造方法显式地将迭代器转化为列表
print list(ti)  # [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]


# -------------------------7.生成器----------------
# 任何包含yield语句的函数称为生成器。
# 它不像return那样返回值，而是每次产生多个值。
# 每次产生一个值(使用yield语句)，函数就会被冻结：即函数停在那点等待被重新唤醒。函数被重新唤醒后就从停止的那点开始执行。
def flatten(nested1):
    for sublist in nested1:
        for element in sublist:
            yield element  # 注意这里的 yield


nested = [[1, 2], [3, 4], [5]]  # 按顺序打印出列表中的数字
for num in flatten(nested):
    print num
# 或者
print list(flatten(nested))  # [1, 2, 3, 4, 5]


# 2.递归生成器
# 上面的方法只能处理两层嵌套，要处理多层怎么办？
def flatten2(nested1):
    try:
        try:
            nested1 + ''
        except TypeError:
            pass
        else:
            raise TypeError

        for sublist in nested1:
            for element in flatten2(sublist):  # 这里递归
                yield element
    except TypeError:
        yield nested1


print list(flatten2([[[1], 2], 3, 4, [5, [6, 7]], 8]))  # [1, 2, 3, 4, 5, 6, 7, 8]


# 生成器是一个包含yield关键字的函数。当它被调用时，在函数体中的代码不会被执行，而返回一个迭代器。
# 每次请求一个值，就会执行生成器中的代码，直到遇到一个yield或者return语句。
# yield语句意味着应该生成一个值。return语句意味着生成器要停止执行。
# 生成器由两部分组成：生成器的函数和生成器的迭代器。
# 生成器的函数是用def语句定义的，包含yield的部分，生成器的迭代器是这个函数返回的部分。

# -----------------生成器的方法-------------
# 生成器的新特性是在运行后为生成器提供值的能力。表现为生成器和'外部世界'进行交流的渠道，要注意下面两点：
# 1.外部作用域访问生成器的send方法，就像访问next方法一样，只不过前者使用一个参数(要发送的'消息' ---- 任意对象)
# 2.在内部则挂起生成器，yield现在作为表达式而不是语句使用，
#   也就是说，当生成器重新运行的时候，yield方法返回一个值，也就是外部通过send方法发送的值。如果next方法被使用，那么yield方法返回None
# 注意：使用send方法(而不是next方法)只有在生成器挂起之后才有意义(也就是说在yield函数第一次执行之后)。
#       如果在些之前需要给生成器提供更多信息，那么只需使用生成器函数的参数
def repeater(value):
    while True:
        new = (yield value)  # 这里最好用()
        if new is not None:
            value = new


r = repeater(42)
print r.next()
print r.send('Hello, world')
