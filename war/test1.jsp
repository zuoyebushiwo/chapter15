<%@ page language="java" contentType="text/html; charset=gbk" pageEncoding="gbk"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- �����ǩ������ -->
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<body>
	<form:form modelAttribute="user">
		�û�����<form:input path="userName"/><br>
		��     �룺<form:password path="password"/><br>
		Email: <form:input path="email"/><br>
		<input type="submit" value="ע��" name="testSumbit"/>
		<input type="reset" value="����"/>
	</form:form>
</body>
</html>