<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="./base/header.jsp" %>
<title>Lỗi</title>
</head>
<body>
	<img alt="" id="bg" src='<c:url value="/resources/img/workplace.jpg"></c:url>'>
	<script type="text/javascript">
		alert("${error}");
		window.location = "/ReviewingTechCompanyWorkplace/home/index.html";
	</script>
</body>
</html>