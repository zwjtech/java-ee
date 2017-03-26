#!/usr/bin/python3
# coding=utf-8
import urllib.request
import re

p = re.compile('[baidu]')
url = "http://www.baidu.com"
text = urllib.request.urlopen(url).read().decode('utf-8')
print(text)
for a in p.findall(text):
    print('%s' % a)

# 上面代码的缺点：
# 1.正则表达式并不是完全可读的，如果太复杂了，不好维护
# 2.程序对CDATA部分和字符实体(比如&amp;)之类的HTML特性是无法处理的。如果遇到了这类特性，程序很可能会失败
# 3.正则表达式被HTMl源码约束，而不是取决于更抽象的结构。这意味着网页结构中很小的改变就会导致程序中断。
