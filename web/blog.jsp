<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="header.jsp"></jsp:include>
    <style>
        .course {
            display: grid;
            grid-template-columns: repeat(4, 1fr); /* 4 items per row */
            gap: 20px; /* Space between the items */
        }

        .item {
            background-color: #f8f9fa;
            border: 1px solid #dee2e6;
            border-radius: 8px;
            overflow: hidden;
        }

        .cours-bx {
            padding: 15px;
            text-align: center;
        }

        .action-box {
            width: 100%;
            height: 150px; /* Set a fixed height for uniformity */
            position: relative;
            overflow: hidden;
        }

        .action-box img {
            width: 100%;
            height: 100%;
            object-fit: cover; /* Ensures the image maintains aspect ratio */
        }
        /* Responsive design for smaller screens */
        @media (max-width: 1200px) {
            .row {
                grid-template-columns: repeat(3, 1fr); /* 3 items per row */
            }
        }

        @media (max-width: 768px) {
            .row {
                grid-template-columns: repeat(2, 1fr); /* 2 items per row */
            }
        }

        @media (max-width: 576px) {
            .row {
                grid-template-columns: 1fr; /* 1 item per row */
            }
        }
    </style>
    <div class="page-content bg-white">
        <!-- Page Heading Box ==== -->
        <div class="page-banner ovbl-dark" style="background-image:url(assets/images/banner/banner1.jpg);">
            <div class="container">
                <div class="page-banner-entry">
                    <h1 class="text-white">Blog Classic</h1>
                </div>
            </div>
        </div>
        <div class="breadcrumb-row">
            <div class="container">
                <ul class="list-inline">
                    <li><a href="#">Home</a></li>
                    <li>Blog Classic</li>
                </ul>
            </div>
        </div>
        <!-- Page Heading Box END ==== -->
        <!-- Page Content Box ==== -->
        <div class="content-block">
            <!-- Blog Grid ==== -->
            <div class="section-area section-sp1">
                <div class="container">
                    <div class="ttr-blog-grid-3 row" id="masonry">
                    <c:forEach items="${blogs}" var="b">

                        <div class="post action-card col-lg-4 col-md-6 col-sm-12 col-xs-12 m-b40">
                            <div class="recent-news">
                                <div class="action-box">
                                    <img src="assets/images/blog/latest-blog/pic1.jpg" alt="">
                                </div>
                                <div class="info-bx">
                                    <ul class="media-post">
                                       <li><a href="#"><i class="fa fa-calendar"></i><fmt:formatDate value="${b.created_at}" pattern="dd/MM/yyyy HH:mm:ss" /></a></li>
                                        <li><a href="#"><i class="fa fa-user"></i>By ${b.user.fullname}</a></li>
                                    </ul>
                                    <h5 class="post-title"><a href="blog-details.html">${b.title}</a></h5>
                                    <p>${b.content}</p>
                                    <div class="post-extra">
                                        <a href="#" class="btn-link">READ MORE</a>
                                        <a href="#" class="comments-bx"><i class="fa fa-comments-o"></i>20 Comment</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>

                </div>
                <!-- Pagination ==== -->
                <div class="pagination-bx rounded-sm gray clearfix">

                    <ul class="pagination">
                        <c:if test="${currentPage > 1}">
                            <li class="page-item">
                                <a class="previous" href="?search=${param.search}&status=${param.status}&page=${currentPage - 1}"><i class="ti-arrow-left"></i> Prev</a>
                            </li>
                        </c:if>
                        <c:forEach var="pageNum" begin="1" end="${totalPages}">
                            <li class="${pageNum == currentPage ? 'active' : ''}">
                                <a class="btn btn-primary" href="?search=${param.search}&status=${param.status}&page=${pageNum}">${pageNum}</a>
                            </li>
                        </c:forEach>
                        <c:if test="${currentPage < totalPages}">
                            <li class="page-item">
                                <a class="next" href="?search=${param.search}&status=${param.status}&page=${currentPage + 1}">Next <i class="ti-arrow-right"></i></a>
                            </li>
                        </c:if>
                    </ul>
                </div>
                <!-- Pagination END ==== -->
            </div>
        </div>
        <!-- Blog Grid END ==== -->
    </div>
    <!-- Page Content Box END ==== -->
</div>
<jsp:include page="footer.jsp"></jsp:include>