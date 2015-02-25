<%@ page language="java" contentType="text/html; charset=gbk" pageEncoding="gbk"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt"  uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
<title>�����û�</title>
  <style>
     .errorClass{color:red}
  </style>
</head>
<body>
  <form:form modelAttribute="user"  action="/user/handle91.html">
     <form:errors path="*" cssClass="errorClass" element="div"/>
    <table>
	    <tr>
	       <td>�û�����</td>
	       <td>
	          <form:errors path="userName" cssClass="errorClass" element="div"/>
	          <form:input path="userName"  />
	       </td>
	    </tr>
	    <tr>
	     <td>���룺</td>
	       <td>
	          <form:errors path="password" cssClass="errorClass"  element="div"/>
	          <form:password path="password" />  
	       </td>
	    </tr>
	    <tr>
	     <td>������</td>
	       <td>
	          <form:errors  path="realName" cssClass="errorClass" element="div"/>
	          <form:input path="realName" />
	          
	       </td>
	    </tr>
	    <tr>
	     <td>���գ�</td>
	       <td>
	         <form:errors path="birthday" cssClass="errorClass" element="div"/>
	         <form:input path="birthday" />    
	       </td>
	    </tr>
	    <tr>
	     <td>���ʣ�</td>
	       <td>
	         <form:errors path="salary" cssClass="errorClass" element="div"/>
	         <form:input path="salary" />
	       </td>
	    </tr>
	    <tr>
	     <td colspan="2"><input type="submit" name="�ύ"/></td>
	    </tr>	    
    </table>
  </form:form>
</body>
</html>