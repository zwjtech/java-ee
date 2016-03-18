<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Insert title here</title>
</head>
<body>

<a href="springMVC/testRedirect">com.changwen.springmvc.crud2.SpringMVCTest Redirect</a>
<br><br>

<a href="springMVC/testView">Test View</a>
<br><br>

<a href="springMVC/testViewAndViewResolver">com.changwen.springmvc.crud2.SpringMVCTest ViewAndViewResolver</a>
<br><br>

<!--这是测试testModelAttribute
模拟修改操作
1. 原始数据为: 1, Tom, 123456,tom@atguigu.com,12
2. 密码不能被修改.
3. 表单回显, 模拟操作直接在表单填写对应的属性值
-->
<form action="springMVC/testModelAttribute" method="Post">
    <input type="hidden" name="id" value="1"/>

    username: <input type="text" name="username" value="Tom"/>
    <br>
    email: <input type="text" name="email" value="tom@atguigu.com"/>
    <br>
    age: <input type="text" name="age" value="12"/>
    <br>
    <input type="submit" value="Submit"/>
</form>
<br><br>



---------------------------------------------
<br>
<a href="springMVC/testSessionAttributes">com.changwen.springmvc.crud2.SpringMVCTest SessionAttributes</a>
<br><br>

<a href="springMVC/testMap">com.changwen.springmvc.crud2.SpringMVCTest Map</a>
<br><br>

<a href="springMVC/testModelAndView">com.changwen.springmvc.crud2.SpringMVCTest ModelAndView</a>

<br><br>
-------------------------------------------------
<a href="springMVC/testServletAPI">com.changwen.springmvc.crud2.SpringMVCTest ServletAPI</a>
<br><br>
<form action="springMVC/testPojo" method="post">
    username: <input type="text" name="username"/>
    <br>
    password: <input type="password" name="password"/>
    <br>
    email: <input type="text" name="email"/>
    <br>
    age: <input type="text" name="age"/>
    <br>
    city: <input type="text" name="address.city"/>
    <br>
    province: <input type="text" name="address.province"/>
    <br>
    <input type="submit" value="Submit"/>
</form>

<br><br>
<!--**************************************88-->
<a href="springMVC/testCookieValue">com.changwen.springmvc.crud2.SpringMVCTest CookieValue</a>
<br><br>

<a href="springMVC/testRequestHeader">com.changwen.springmvc.crud2.SpringMVCTest RequestHeader</a>
<br><br>

<a href="springMVC/testRequestParam?username=atguigu&age=11">Test RequestParam</a>
<br><br>

<%-------------------------restfu----------------------------------->--%>

<form action="springMVC/testRest/1" method="post">
    <input type="hidden" name="_method" value="PUT"/>
    <input type="submit" value="TestRest PUT"/>
</form>
<br><br>

<form action="springMVC/testRest/1" method="post">
    <input type="hidden" name="_method" value="DELETE"/>
    <input type="submit" value="TestRest DELETE"/>
</form>
<br><br>

<form action="springMVC/testRest" method="post">
    <input type="submit" value="TestRest POST"/>
</form>
<br><br>

<a href="springMVC/testRest/1">Test Rest Get</a>


--------------------------------------------------------------
    <br><br>
    <a href="springMVC/testPathVariable/1">Test7. RequestMapping_PathVariable注解</a>

    <br><br>
    <a href="springMVC/testAntPath/mnxyz/abc">Test AntPath</a>

    <br><br>
    <a href="springMVC/testParamsAndHeaders?username=changWen&age=10">Test5. RequestMapping_请求参数&请求头</a>


    下面两个是测试RequestMapping_请求方式，一个是POST请求，一个是GET请求
    <br><br>
    <form action="springMVC/testPostMethod" method="POST">
        username: <label><input type="text" name="username"/></label>
        <input type="submit" value="submit"/>
    </form>
    <br><br>
    <a href="springMVC/testGetMethod">测试 GET请求</a>

    <br><br>
    <a href="springMVC/testRequestMapping">Test RequestMapping</a>

</body>
</html>