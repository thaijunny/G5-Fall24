
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
        <!-- Main Slider -->
        <div class="section-area section-sp1 ovpr-dark bg-fix online-cours" style="background-image:url(assets/images/background/bg1.jpg);">
            <div class="container">
                <div class="row">
                    <div class="col-md-12 text-center text-white" style="margin-top: 30px">
                        <h2>Online Courses To Learn</h2>
                        <h5>Own Your Feature Learning New Skills Online</h5>
                        <form class="cours-search" action="search" method="post">
                            <div class="input-group">
                                <input value="${txtS}" name="txt" type="text" class="form-control" placeholder="What do you want to learn today?" >
                            <div class="input-group-append">
                                <button class="btn" type="submit" >Search</button> 
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <div class="content-block">
        <!-- Popular Courses -->
        <div class="section-area section-sp2 popular-courses-bx">
            <div class="container">
                <div class="row">
                    <div class="col-md-3 heading-bx left">
                        <h2 class="title-head">All <span>Skill</span></h2>
                    </div>
                     <form class=" col-md-9" action="${pageContext.request.contextPath}/skill-list" method="get">
                            <div class="input-group">
                                <input value="${search}" name="search" type="text" class="form-control" placeholder="What do you want to learn today?" >
                            <div class="input-group-append">
                                <button class="btn" type="submit" >Search</button> 
                            </div>
                        </div>
                    </form>
                </div>
                <div class="row course">
                    <c:forEach items="${skills}" var="s">
                        <div class="item">
                            <div class="cours-bx">
                                <div class="action-box">
                                    <img src="${pageContext.request.contextPath}/image/${s.image}" alt="alt" />
                                    <a href="#" class="btn">Read More</a>
                                </div>
                                <div class="info-bx text-center">
                                    <h5><a href="#">${s.name}</a></h5>
                                    <span>${s.description}</span>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
                <nav class="mt-3" aria-label="Page navigation example">
                    <ul class="pagination justify-content-center">
                        <c:if test="${currentPage > 1}">
                            <li class="page-item">
                                <a class="btn btn-primary" href="?search=${param.search}&page=${currentPage - 1}">Previous</a>
                            </li>
                        </c:if>
                        <c:forEach var="pageNum" begin="1" end="${totalPages}">
                            <li class="page-item ${pageNum == currentPage ? 'active' : ''}">
                                <a class="btn btn-primary" href="?search=${param.search}&page=${pageNum}">${pageNum}</a>
                            </li>
                        </c:forEach>
                        <c:if test="${currentPage < totalPages}">
                            <li class="page-item">
                                <a class="btn btn-primary" href="?search=${param.search}&page=${currentPage + 1}">Next</a>
                            </li>
                        </c:if>
                    </ul>
                </nav>
            </div>

        </div>
    </div>
    <jsp:include page="footer.jsp"></jsp:include>