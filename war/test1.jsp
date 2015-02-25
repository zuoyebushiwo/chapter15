<%@ page language="java" contentType="text/html; charset=gbk" pageEncoding="gbk"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- 引入标签的声明 -->
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<body>
	<form:form modelAttribute="user">
		用户名：<form:input path="userName"/><br>
		密     码：<form:password path="password"/><br>
		Email: <form:input path="email"/><br>
		<input type="submit" value="注册" name="testSumbit"/>
		<input type="reset" value="重置"/>
	</form:form>
</body>
</html>