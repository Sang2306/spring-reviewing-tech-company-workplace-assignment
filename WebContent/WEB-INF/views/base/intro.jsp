<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div class="row">
	<div class="col">

		<a href="/ReviewingTechCompanyWorkplace/home/index.html"
			style="color: black;">
			<button class="btn btn-sm btn-dark">
				<i class="fa fa-home" aria-hidden="true"></i>Trang chủ
			</button>
		</a>

		<c:if test="${username == null}">
			<button class="btn btn-sm btn-dark" data-toggle="modal"
				data-target="#login">
				<i class="fa fa-sign-in" aria-hidden="true"></i>
				<c:out value="Đăng nhập"></c:out>
			</button>
		</c:if>
		<c:if test="${username != null}">
			<c:choose>
				<c:when test="${role == 'admin'}">
					<a href="/ReviewingTechCompanyWorkplace/company/add.html">
						<button class="btn btn-sm btn-warning m-1">
							<c:out value="Xin chào ${username}"></c:out>
						</button>
					</a>
					<a href="/ReviewingTechCompanyWorkplace/admin/logout.html">
						<button class="btn btn-sm btn-dark m-1">
							Đăng xuất <i class="fa fa-sign-out" aria-hidden="true"></i>
						</button>
					</a>
				</c:when>
				<c:otherwise>
					<a href="#">
						<button class="btn btn-sm btn-warning m-1">
							<c:out value="Xin chào ${username}"></c:out>
						</button>
					</a>
					<a href="/ReviewingTechCompanyWorkplace/reviewer/logout.html">
						<button class="btn btn-sm btn-dark m-1">
							Đăng xuất <i class="fa fa-sign-out" aria-hidden="true"></i>
						</button>
					</a>
				</c:otherwise>
			</c:choose>
		</c:if>
	</div>
</div>