<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
<%@ include file="./base/header.jsp"%>
<title>Tìm kiếm</title>
</head>
<body>
	<img alt="" id="bg" src='<c:url value="/resources/img/workplace.jpg"></c:url>'>
	<div class="container">
		<div class="row mt-4 justify-content-center">
			<div class="col col-md alert">
				<c:if test="${error != null}">
					<div class="alert alert-danger">${error}</div>
				</c:if>
				<div class="row justify-content-end alert-info">
					<div class="col-sm-12">
						1/Chào mừng các bạn đến với website nơi bạn có thể thỏa thích xem
						review nơi làm việc <br> 2/Để tránh review bị xóa các bạn
						không được sử dụng từ ngữ thiếu văn hóa <br>
					</div>
					<div class="col-xl-auto col-lg-auto col-md-auto col-sm">
						<%@ include file="./base/intro.jsp"%>
					</div>
					<%@ include file="./base/login-form.jsp"%>
				</div>
			</div>
		</div>
		<div class="row justify-content-center slogan-page">
			<div class="col-6 col-12" style="text-align: center">
				<h4 class="slogan">< Hãy cho mọi người biết nơi làm việc của
					bạn như thế nào? /></h4>
			</div>
		</div>
		<%@include file="./base/search.jsp"%>
		<div class="row my-3">
			<div class="col rounded" style="background-color: rgb(248, 248, 248)">
				<h4 style="font-weight: bold;">${message}</h4>
				<c:forEach var="company" items="${companies}">
					<div class="row pt-1 my-1">
						<div class="col-auto">
							<a	href="/ReviewingTechCompanyWorkplace/company/view/${company.getId()}.html">
							<img src="<c:url value="/files/company/${company.getPhotoPath()}"></c:url>"
								alt="" class="rounded company-logo"></a>
						</div>
						<div class="col-6 col-md">
							<div class="row">
								<div class="col">
									<a href="/ReviewingTechCompanyWorkplace/company/view/${company.getId()}.html">${company.getName()}</a>
								</div>
							</div>
							<div
								class="row justify-content-center no-gutter company-info-brief">
								<div class="col-sm-12">
									<i class="fa fa-map-marker" aria-hidden="true"></i>
									${company.getAddr()}
								</div>
								<div class="col">
									<i class="fa fa-industry" aria-hidden="true"></i> Product
								</div>
								<div class="col">
									<i class="fa fa-users" aria-hidden="true"></i>
									${company.getNum_employee()}
								</div>
								<div class="col">
									<i class="fa fa-globe" aria-hidden="true"></i>
									${company.getCountry()}
								</div>
							</div>
							<div class="row justify-content-left company-info-brief">
								<div class="col pt-3">
									<i class="fa fa-eye" aria-hidden="true"></i> <span></span>
								</div>
							</div>
						</div>
					</div>
				</c:forEach>
			</div>
		</div>
	</div>
</body>
<footer class="pt-5">
	<div class="container text-center fixed-bottom">
		<div class="row">
			<div class="col-12 bg-info shadow-lg bg-secondary text-light">
				@2019 Tech Worplace Review.</div>
		</div>
	</div>
</footer>
</html>