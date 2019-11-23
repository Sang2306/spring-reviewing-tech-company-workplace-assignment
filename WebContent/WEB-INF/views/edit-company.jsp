<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="vi">

<head>
<%@ include file="./base/header.jsp"%>
<title>Thêm thông tin</title>
</head>

<body>
	<img alt="" id="bg"
		src='<c:url value="/resources/img/workplace.jpg"></c:url>'>
	<div class="container">
		<div class="row mt-3 mb-3 p-3 rounded"
			style="background-color: #17a2b8 !important; color: white; position: sticky">
			<div class="col">
				<div class="row">
					<div class="col">
						<a href="/ReviewingTechCompanyWorkplace/home/index.html"
							style="color: white;"><i class="fa fa-home"
							aria-hidden="true"></i>Trang chủ</a>
					</div>
				</div>
			</div>
			<div class="col text-right">
				<i class="fa fa-user-o" aria-hidden="true"></i> Xin chào
				<%
					out.print(session.getAttribute("username"));
				%>
			</div>
		</div>
		<div class="row">
			<div class="col bg-light">
				<form action="/ReviewingTechCompanyWorkplace/company/edit/${company.getId()}.html" method="POST" id="editCompany" enctype="multipart/form-data">
					<div class="row">
						<div class="col-sm-12 col-md mt-2">
							<div class="input-group">
								<div class="input-group-prepend">
									<span class="input-group-text text-light bg-info">Tên
										công ty</span>
								</div>
								<input type="text" name="name" class="form-control"
									placeholder="ABC" aria-label="Tên công ty"
									value="${company.getName() }" required>
							</div>
						</div>
						<div class="col-sm-12 col-md mt-2">
							<div class="input-group">
								<div class="input-group-prepend">
									<span class="input-group-text text-light bg-info">Địa
										chỉ</span>
								</div>
								<input type="text" name="address" class="form-control"
									placeholder="113, Prison St, BattleField" aria-label="Địa chỉ"
									value="${company.getAddr() }" required>
							</div>
						</div>
					</div>
					<div class="row mt-4 align-items-center">
						<div
							class="col-sm-12 m-3 col-md text-light bg-info rounded no-gutters">
							<div class="row p-2">
								<div class="col col-md">Lĩnh vực:</div>
								<div class="col col-md">
									<div class="custom-control custom-radio custom-control-inline">
										<input type="radio" id="industryField1"
											class="custom-control-input" name="industryField"
											value="product"
											${company.getIndustryField()=="product"?"checked='checked'":"" }
											required> <label class="custom-control-label"
											for="industryField1">Product</label>
									</div>
									<div class="custom-control custom-radio custom-control-inline">
										<input type="radio" id="industryField2"
											class="custom-control-input" name="industryField"
											value="outsourcing"
											${company.getIndustryField()=="outsourcing"?"checked='checked'":"" }
											required> <label class="custom-control-label"
											for="industryField2">Out sourcing</label>
									</div>
								</div>
							</div>
						</div>
						<div class="col-sm-12 col-md-6">
							<div class="input-group">
								<div class="input-group-prepend">
									<span class="input-group-text text-light bg-info">Số
										lượng nhân viên:</span>
								</div>
								<input class="form-control" name="num_employee" type="number"
									min="10" value="${company.getNum_employee() }" required>
							</div>
						</div>
					</div>
					<div class="row mt-4">
						<div class="col col-md-6">
							<label for="country" class="p-1 rounded text-light bg-info">Quốc
								gia<span
								style="color: red !important; display: inline; float: none;">*</span>
							</label> <select id="country" name="country" class="form-control"
								required>
								<c:forEach var="country" items="${countries}">
									<option value="${country.getName()}"
										${company.getCountry()==country.getName()?"selected='selected'":"" }>${country.getName()}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="row mt-4">
						<div class="col-sm-3 my-5">
							<img
								src="<c:url value="/files/company/${company.getPhotoPath()}"></c:url>"
								id="companyImg" class="card-img-top border text-center"
								alt="logo công ty">
						</div>
						<div class="col-sm-auto my-5">
							<div class="card">
								<div class="card-body">
									<div class="input-group mb-3">
										<div class="custom-file">
											<input type="file"
												accept="image/gif, image/png, image/jpeg, image/bmp, image/webp, image/vnd.microsoft.icon"
												name="photo" class="custom-file-input"
												id="photo" aria-describedby="logo"> <label
												class="custom-file-label" for="photo">${company.getPhotoPath()}</label>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="row my-3 justify-content-center">
						<div class="col text-left my-2">
							<button class="btn btn-success" type="submit" form="editCompany">
								Cập nhật <i class="fa fa-chevron-up" aria-hidden="true"></i>
							</button>
						</div>
					</div>
				</form>
			</div>
		</div>
		<div class="row p-5"></div>
	</div>
	<script type="text/javascript">
		function readURL(input) {
			if (input.files && input.files[0]) {
				var reader = new FileReader();

				reader.onload = function(e) {
					$('#companyImg').attr('src', e.target.result);
				}

				reader.readAsDataURL(input.files[0]);
			}
		}

		$("#photo").change(function(event) {
			readURL(this);
			$('.custom-file-label').text(event.currentTarget.value);
		});
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