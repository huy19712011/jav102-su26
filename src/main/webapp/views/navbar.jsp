<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<div class="container">
	<header>
		<h1>
			<img alt="" src="${pageContext.request.contextPath}/images/logo.png" width="150">
		</h1>
		<hr>
	</header>

	<nav class="navbar navbar-expand-lg bg-body-tertiary">
		<div class="container-fluid">
			<a class="navbar-brand" href="#"></a>
			<button class="navbar-toggler" type="button"
				data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
				aria-controls="navbarSupportedContent" aria-expanded="false"
				aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarSupportedContent">
				<ul class="navbar-nav me-auto mb-2 mb-lg-0">
					<li class="nav-item">
						<a class="nav-link" aria-current="page" href="${pageContext.request.contextPath}/trang-chu">Trang chủ</a>
					</li>
					<c:if test="${sessionScope.user != null}">
						<li class="nav-item">
							<a class="nav-link" href="${pageContext.request.contextPath}/employee/pos">Phiếu bán hàng</a>
						</li>
						<li class="nav-item">
							<a class="nav-link" href="${pageContext.request.contextPath}/quan-ly/categories">Quản lý loại đồ uống</a>
						</li>
						<li class="nav-item">
							<a class="nav-link" href="${pageContext.request.contextPath}/drinks">Quản lý đồ uống</a>
						</li>
						<li class="nav-item">
							<a class="nav-link" href="${pageContext.request.contextPath}/bills">Quản lý hóa đơn</a>
						</li>
						<c:if test="${sessionScope.user.role}">
							<li class="nav-item">
								<a class="nav-link" href="${pageContext.request.contextPath}/quan-ly/staff">Quản lý nhân viên</a>
							</li>
						</c:if>
					</c:if>
					<li class="nav-item dropdown">
						<a	class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false"> ${sessionScope.user!= null? sessionScope.user.fullName :"Tài Khoản"} </a>
						<ul class="dropdown-menu">
							<c:if test="${sessionScope.user== null}">
								<li><a class="dropdown-item" href="${pageContext.request.contextPath}/dang-nhap">Đăng nhập</a></li>
								<li><a class="dropdown-item" href="${pageContext.request.contextPath}/quen-mat-khau">Quên mật khẩu</a></li>
							</c:if>

							<c:if test="${sessionScope.user!= null}">
								<li><a class="dropdown-item" href="${pageContext.request.contextPath}/edit-profile">Thông tin cá nhân</a></li>
								<li><a class="dropdown-item" href="${pageContext.request.contextPath}/change-pass">Đổi mật khẩu</a></li>
								<li><hr class="dropdown-divider"></li>
								<li><a class="dropdown-item" href="${pageContext.request.contextPath}/logout">Đăng xuất</a></li>
							</c:if>
						</ul>
					</li>

				</ul>

			</div>
		</div>
	</nav>
</div>
