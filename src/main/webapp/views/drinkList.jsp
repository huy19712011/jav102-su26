<%--
  Created by IntelliJ IDEA.
  User: ADMIN
  Date: 18/07/2026
  Time: 10:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<html>
<head>
    <title>Drinks</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet" crossorigin="anonymous">
    <style>
        .thumb { width: 80px; height: 60px; object-fit: cover; border-radius: 4px; }
    </style>
</head>
<body>
<%--<jsp:include page="/views/navbar.jsp" />--%>
<div class="container mt-4">
    <div class="d-flex justify-content-between align-items-center mb-3">
        <h3 class="mb-0">Danh sách đồ uống</h3>
        <a href="${pageContext.request.contextPath}/drinks/add" class="btn btn-success">Thêm mới</a>
    </div>

    <div class="card">
        <div class="card-body p-0">
            <div class="table-responsive">
                <table class="table table-striped table-hover mb-0 align-middle">
                    <thead class="table-light">
                    <tr>
                        <th style="width:60px">ID</th>
                        <th>Tên</th>
                        <th>Mô tả</th>
                        <th style="width:100px">Ảnh</th>
                        <th style="width:120px">Giá (VNĐ)</th>
                        <th style="width:100px">Trạng thái</th>
                        <th style="width:180px">Hành động</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${drinks}" var="d">
                        <tr>
                            <td>${d.id}</td>
                            <td>${d.name}</td>
                            <td>${d.description}</td>
                            <td><img src="${pageContext.request.contextPath}/uploads/${d.image}" alt="${d.name}" class="thumb"/></td>
                            <td><fmt:formatNumber value="${d.price}" pattern="#,##0"/></td>
                            <td>
                                <c:choose>
                                    <c:when test="${d.active}"><span class="badge bg-success">Đang bán</span></c:when>
                                    <c:otherwise><span class="badge bg-secondary">Ngưng bán</span></c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <a href="${pageContext.request.contextPath}/quan-ly/drinks/edit?id=${d.id}" class="btn btn-sm btn-warning">Sửa</a>
                                <form method="post" action="${pageContext.request.contextPath}/quan-ly/drinks/delete" style="display:inline">
                                    <input type="hidden" name="drinkId" value="${d.id}">
                                    <c:choose>
                                        <c:when test="${d.active}">
                                            <button type="submit" class="btn btn-sm btn-danger">Ngưng hoạt động</button>
                                        </c:when>
                                        <c:otherwise>
                                            <button type="submit" class="btn btn-sm btn-success">Kích hoạt</button>
                                        </c:otherwise>
                                    </c:choose>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty drinks}">
                        <tr><td colspan="7" class="text-muted">Chưa có đồ uống nào.</td></tr>
                    </c:if>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
</body>
</html>
