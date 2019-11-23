<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<form action="/ReviewingTechCompanyWorkplace/search.html" method="get">
	<div class="row mt-5">
		<div class="col-md-8 col-sm-12 my-1">
			<input type="text" class="form-control rounded-pill" name="q"
				placeholder="searchBy('Tên công ty')">
		</div>
		<div class="col-md-4 col-sm-12 my-1">
			<button class="btn btn-info rounded-pill" type="submit">
				<i class="fa fa-search" aria-hidden="true"></i> Tìm kiếm
			</button>
		</div>
	</div>
	<c:if test="${countries != null}">
		<div class="row mt-2">
			<div class="col-md-4 col-sm-12 rounded">
				<select id="country" name="country" class="form-control" required>
					<option value="null" disabled="disabled">---Lọc theo quốc gia---</option>
					<c:forEach var="country" items="${countries}">
						<option value="${country}">${country}</option>
					</c:forEach>
				</select>
			</div>
		</div>
	</c:if>
</form>