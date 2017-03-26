#!/usr/bin/python
# coding=utf-8


# 异常对象如果没有被处理或捕捉，程序就会回溯(traceback, 一种错误信息)终止执行
# 1、抛出异常
# raise Exception
# raise Exception('not null')

# 2、自定义异常
class someCustomException(Exception): pass


# 3.捕获异常
try:
    print 1 / 0
except ZeroDivisionError:
    print 'The second number can\'t be zero!'


class MeffledCalculator:
    muffled = False

    def calc(self, expr):
        try:
            print eval(expr)
        except ZeroDivisionError:
            if self.muffled:
                print 'the second is not be zero'
            else:
                raise
        except TypeError:
            print 'That isn\'t a number'


calculator = MeffledCalculator()
calculator.calc('10/2')  # 5
# calculator.calc('10/0')  # ZeroDivisionError: integer division or modulo by zero
calculator.muffled = True
calculator.calc('10/0')  # the second is not be zero

calculator.calc('hello world')  # SyntaxError: unexpected EOF while parsing  需要加一个类型异常

# 一个块捕捉多个异常
try:
    print 1 / 0
except (ZeroDivisionError, TypeError, NameError), e:  # python3.0   except (ZeroDivisionError) as e:
    print 'has error:', e

# 全捕捉异常
try:
    print 1 / 0
except Exception, e:  # python3.0   except (ZeroDivisionError) as e:
    print 'has error:', e
else:
    print 'It went as planned'  # 如果是循环可以加个 break
finally:
    print 'Cleaning up'  # 不管是否有异常，这个一定会实现

