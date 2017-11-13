# 一级标题
    方式1
* 1
* 2
* 3.1
+ Candy.
+ Gum.
+ Booze.
- Candy.
- Gum.
- Booze.
1. Red
2. Green
3. Blue
> 这里是引用
  
  方式2
- 4.1
- 5
***嘻嘻嘻***
## 二级标题
 **粗体文本**
  
### 三级标题
  *斜体文本*
#### 四级标题

A First Level Header
====================
A Second Level Header
---------------------

Now is the time for all good men to come to
the aid of their country. This is just a
regular paragraph.

The quick brown fox jumped over the lazy
dog's back.
### Header 3

> This is a blockquote.
> 
> This is the second paragraph in the blockquote.
>
> ## This is an H2 in a blockquote

Some of these words *are emphasized*.
Some of these words _are emphasized also_.
Use two asterisks for **strong emphasis**.
Or, if you prefer, __use two underscores instead__.

* A list item.

    With multiple paragraphs.

* Another item in the list.

This is an [example link](http://example.com/).

This is an [example link](http://example.com/ "With a Title").

I get 10 times more traffic from [Google][1] than from
[Yahoo][2] or [MSN][3].

[1]: http://google.com/ "Google"
[2]: http://search.yahoo.com/ "Yahoo Search"
[3]: http://search.msn.com/ "MSN Search"

If you want your page to validate under XHTML 1.0 Strict,
you've got to put paragraph tags in your blockquotes:

I strongly recommend against using any `<blink> public BillingItemActionFactory(BillingItemRepository billingItemRepository) {
                                                        super(billingItemRepository);
                                                    }` tags.

I wish SmartyPants used named entities like `&mdash;`
instead of decimal-encoded entites like `&#8212;`.

<blockquote>
<p>For example.</p>
</blockquote>

 | Tables        | Are           | Cool  |
 | ------------- |:-------------:| -----:|
 | col 3 is      | right-aligned | $1600 |
 | col 2 is      | centered      |   $12 |
 | zebra stripes | are neat      |    $1 |
 
 
 # 集团云计费服务模块
 ## 一.业务模块
 >>1.商品定價模塊
 * 1.1 更新商品定價
   - 模块：CommodityPriceModule
   - 动作：putCommodityPriceClause
   - 参数：商品名称(String)，计费项(String)，商品定價列表(List)(可选)，商品屬性列表(List)(可选)
   - 返回：修改结果
   
 * 1.2 查詢定價條目列表
   - 模块：CommodityPriceModule
   - 动作：queryPriceClauseList
   - 参数：商品名称(String)，計費項編號(String)
   - 返回：定价条目列表
 * 1.3 查詢計費項列表
 
 
 >>2.帳單模塊
 
 >>3.商品計費模塊
 
 >>4.数据管理
 
 >>5.錢包模塊
 
 ## 二.持久化模块
 
 ## 三.持久化模块