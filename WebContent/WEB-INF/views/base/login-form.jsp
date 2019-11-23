<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div class="modal fade" id="login" tabindex="-1" role="dialog"
	aria-labelledby="adminLoginTitle" aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="adminLoginTitle">
					<i class="fa fa-user-o" aria-hidden="true"></i> <span id="title">Ẩn danh</span>
				</h5>
				<button class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body">

				<ul class="nav nav-tabs" id="myTab" role="tablist">
					<li class="nav-item" onclick="tabAnonymous()"><a
						class="nav-link active" id="anonymous-tab" data-toggle="tab"
						href="#anonymous" role="tab" aria-controls="anonymous"
						aria-selected="true">Ẩn danh</a></li>
					<li class="nav-item" onclick="tabAdmin()"><a class="nav-link"
						id="admin-tab" data-toggle="tab" href="#admin" role="tab"
						aria-controls="admin" aria-selected="false">Admin</a></li>
				</ul>
				<div class="tab-content">
					<div class="tab-pane fade show active" id="anonymous"
						role="tabpanel" aria-labelledby="anonymous-tab">
						<form action="/ReviewingTechCompanyWorkplace/reviewer/add.html"
							method="POST" id="anonymous-form" class="mt-2">
							<div class="form-group row was-validated">
								<label for="alias" class="col col-form-label">Bí danh</label>
								<div class="col-sm-8">
									<input type="text" class="form-control is-invalid"
										id="alias" name="alias"
										placeholder="Nhập tên ẩn danh" required>
								</div>
							</div>
							<div class="form-group row">
								<label for="position" class="col col-form-label">Vị trí công việc<i style="font-weight: bold; font-size: 12px;">(không bắt buộc)</i></label>
								<div class="col-sm-8">
									<input type="text" class="form-control"
										id="position" name="position"
										placeholder="Vị trí công việc">
								</div>
							</div>
							<div class="form-group row">
								<label for="salary" class="col col-form-label">Lương<i style="font-weight: bold; font-size: 12px;">(không bắt buộc)</i></label>
								<div class="col-sm-8">
									<input type="number" min="500" class="form-control"
										id="salary" name="salary"
										placeholder="Lương">
								</div>
							</div>
							<div class="form-group row was-validated">
								<label for="adminpassword" class="col col-form-label">Mật khẩu</label>
								<div class="col-sm-8">
									<input type="password" class="form-control is-invalid"
										id="password" name="password" placeholder="Nhập mật khẩu"
										required>
								</div>
							</div>
						</form>
					</div>

					<div class="tab-pane fade" id="admin" role="tabpanel"
						aria-labelledby="admin-tab">
						<form action="/ReviewingTechCompanyWorkplace/admin/login.html"
							method="POST" id="admin-form" class="mt-2">
							<div class="form-group row was-validated">
								<label for="adminname" class="col col-form-label">Tên
									tài khoản</label>
								<div class="col-sm-8">
									<input type="text" class="form-control is-invalid"
										id="adminname" name="username"
										placeholder="Nhập tên người dùng" required>
								</div>
							</div>
							<div class="form-group row was-validated">
								<label for="adminpassword" class="col col-form-label">Mật
									khẩu</label>
								<div class="col-sm-8">
									<input type="password" class="form-control is-invalid"
										id="adminpassword" name="password" placeholder="Nhập mật khẩu"
										required>
								</div>
							</div>
						</form>

					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="submit" form="anonymous-form" class="btn btn-success">
					<i class="fa fa-sign-in" aria-hidden="true"></i> Đăng nhập
				</button>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
	var title = document.getElementById("title");
	function tabAnonymous() {
		title.innerText = "Ẩn danh";
		document.querySelector("button[form='admin-form']").setAttribute("form", "anonymous-form");
	}
	function tabAdmin() {
		title.innerText = "Admin";
		document.querySelector("button[form='anonymous-form']").setAttribute("form", "admin-form");
	}
</script>