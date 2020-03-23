<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>添加用户</title>
</head>
<body>
<form action="/contracts/123/create" method="post">
    <input type="file" id="j-upload-input" name="file"/><br><br>
    合同标题：<input type="text" name="title"/> <br><br>
    乙方姓名：<input type="text" name="partBName"/> <br><br>
    乙方身份证号:<input type="text" name="partBIDCard" /> <br><br>
    <input type="submit" value="提交"/>
</form>
</body>
</html>
