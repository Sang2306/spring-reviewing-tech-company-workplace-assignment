<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="./base/header.jsp"%>
<title>${company.getName()}</title>
</head>
<body>
	<img alt="" id="bg"
		src='<c:url value="/resources/img/workplace.jpg"></c:url>'>
	<div class="container">
		<div class="row mt-4 justify-content-center">
			<div class="col col-md alert">
				<div class="row justify-content-end alert-info">
					<div class="col-sm-12">
						1/Chào mừng các bạn đến với website nơi bạn có thể thỏa thích xem
						review nơi làm việc <br> 2/Để tránh review bị xóa các bạn
						không được sử dụng từ ngữ thiếu văn hóa <br>
					</div>
					<div class="col-xl-auto col-lg-auto col-md-auto">
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
		<%@include file="./base/search.jsp"%>>
		<div class="row my-3 ml-1 mr-1">
			<div class="col-sm-12 col-md-8"
				style="background-color: rgb(248, 248, 248)">
				<div class="row pt-1">
					<c:set var="url" scope="session"
						value="/ReviewingTechCompanyWorkplace/company/view/${company.getId()}.html"></c:set>
					<div class="col-sm-12">
						<a href="${url }"><img
							src="<c:url value="/files/company/${company.getPhotoPath()}"></c:url>"
							alt="" class="rounded company-logo"></a>
					</div>
					<div class="col-sm-12 col-md">
						<div class="row justify-content-left">
							<div class="col">
								<a href="${url}">${company.getName()}</a>
							</div>
						</div>
						<div
							class="row justify-content-center no-gutter company-info-brief">
							<div class="col-sm-12">
								<i class="fa fa-map-marker" aria-hidden="true"></i>
								${company.getAddr()}
							</div>
							<div class="col">
								<i class="fa fa-industry" aria-hidden="true"></i>
								${company.getIndustryField()}
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
								<i class="fa fa-comments" aria-hidden="true"></i> <span>${comments.size()}</span>
							</div>
						</div>
						<div class="mt-5">
							<c:forEach var="comment" varStatus="forloop" items="${comments}">
								<div class="row m-2">
									<div class="col">
										<h6>
											<fmt:formatDate value="${comment.getComment_id()}"
												pattern="yyyy-MM-dd HH:mm:ss" />
											bởi: <i
												style="color: #ff6c00; font-variant: super; font-family: initial; font-size: 1.5em;">${comment.getReviewer().getAlias()}</i>
											- Lương ${comment.getReviewer().getSalary()} <i
												style="color: green;" class="fa fa-money" aria-hidden="true"></i>
											- Vị trí: ${comment.getReviewer().getPosition()}
										</h6>
										<div class="row">
											<div class="col">
												<span id="cmt-${forloop.getIndex() }"
													style="font-style: italic;">${comment.getContent()}</span>
											</div>
											<div class="col-2">
												<c:if
													test='${session.getAttribute("username") == comment.getReviewer().getAlias() || session.getAttribute("role") == "admin"}'>
													<form
														action="/ReviewingTechCompanyWorkplace/comment/delete/${comment.getComment_id()}.html"
														method="post">
														<input type="hidden" name="companyId"
															value="${comment.getCompany().getId()}">
														<button type="submit" class="btn btn-sm btn-light"
															title="Xóa">
															<i class="fa fa-trash-o text-danger" aria-hidden="true"></i>
														</button>
													</form>
												</c:if>
												<c:if
													test='${session.getAttribute("username") == comment.getReviewer().getAlias()}'>
													<button type="submit" value="${forloop.getIndex()}" name="${comment.getComment_id() }"
														onclick="editComment(this)" class="btn btn-sm btn-primary"
														title="Sửa">
														<i class="fa fa-pencil-square-o" style="color: white"
															aria-hidden="true"></i>
													</button>
												</c:if>
											</div>
										</div>
									</div>
								</div>
								<hr>
							</c:forEach>
						</div>
					</div>
				</div>
				<div class="row pt-1"></div>
			</div>
			<div class="col-sm-12 col-md-4">
				<div class="row flex-wrap my-3">
					<div class="col">
						<div class=row>
							<div class="col">
								<form
									action="/ReviewingTechCompanyWorkplace/comment/add/${company.getId()}.html"
									id="inputComment" method="post" onsubmit="return standardlizeCmt();">
									<input type="text" name="comment-id" value="" hidden>
									<textarea id="comment" class="form-control" name="comment"
										cols="33" placeholder="Nhập bình luận ở đây..."
										spellcheck="true" required="required"></textarea>
								</form>
							</div>
						</div>
						<div class="row">
							<div class="col mt-2">
								<button type="submit" form="inputComment"
									class="btn btn-sm btn-success">
									Gửi<i class="m-1 fa fa-paper-plane" aria-hidden="true"></i>
								</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script>
		function editComment(button) {
			let selector = "#cmt-" + button.value;
			let comment = $(selector).text();
			//move comment to comment box
			$("#comment").val(comment);
			$("input[name=comment-id]").val(button.name);
		}
		function standardlizeCmt(){
			let standardlized = $("#comment").val();
			if(standardlized.trim().length === 0){
				alert("Comment rỗng!");
				return false;
			}
			$("#comment").val(standardlized.trim());
			return true;
		}
	</script>
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