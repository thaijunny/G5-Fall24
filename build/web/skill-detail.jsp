
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style>
    /* Style for the mentor boxes layout */
    .course2{
        justify-content: center;
        text-align: center;
    }
    .row.course {
        display: grid;
        grid-template-columns: repeat(2, 1fr); /* 2 items per row */
        gap: 20px; /* Adds space between the items */
    }

    /* Responsive design for smaller screens */
    @media (max-width: 768px) {
        .row.course {
            grid-template-columns: repeat(1, 1fr); /* 1 item per row on small screens */
        }
    }

    .mentor-box {
        display: flex;
        justify-content: space-between;
        border: 1px solid #ccc;
        padding: 10px;
        margin-bottom: 10px;
    }

    .mentor-avatar img {
        border-radius: 50%;
        max-width: 100px;
    }

    .mentor-info h5 {
        margin: 0;
        font-size: 18px;
        font-weight: bold;
    }

    .mentor-info span {
        font-size: 14px;
        color: #888;
    }

    .mentor-skills .skill-label {
        display: inline-block;
        background-color: #f0f0f0;
        padding: 5px;
        margin-right: 5px;
        border-radius: 3px;
    }

    .rating-section {
        display: flex;
        align-items: center;
    }

    .stars i {
        color: #FFD700; /* Gold color for stars */
        margin-left: 2px;
    }

    .mentor-action {
        text-align: center;
    }

    .mentor-action .btn {
        margin-top: 10px;
    }
</style>
<jsp:include page="header.jsp"></jsp:include>
    <div class="page-content bg-white" >
        <!-- Main Slider -->
        <div class="section-area section-sp1 ovpr-dark bg-fix online-cours" style="background-image:url(assets/images/background/bg1.jpg);">
            <div class="container">
                <div class="row" style="margin-top: 20px">
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
                    <div class="col-md-5 heading-bx left">
                        <h2 class="title-head">Detail for <span>${skill.name}</span></h2>
                    </div>
                </div>
                <div class="row course2" >
                    <div class="item" >
                        <div class="cours-bx">
                            <div class="action-box">
                                <img src="${pageContext.request.contextPath}/image/${skill.image}" alt="alt" style="max-height: 300px;" />
                            </div>
                            <div class="info-bx text-center">
                                <h5><a href="#">${skill.name}</a></h5>
                                <span>${skill.description}</span>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row"style="margin-top: 40px">
                    <div class="col-md-5 heading-bx left">
                        <h2  class="title-head">Mentor of  <span>${skill.name}</span></h2>
                    </div>
                    <form class=" col-md-7" action="${pageContext.request.contextPath}/skill-detail" method="get">
                        <div class="input-group">
                            <input value="${search}" name="search" type="text" class="form-control" placeholder="Enter to search Mentor" >
                            <input type="hidden" name="id" value="${skill.id}">
                            <div class="input-group-append">
                                <button class="btn" type="submit" >Search</button> 
                            </div>
                        </div>
                    </form>
                </div>
                <div class="row course">
                    <c:if test="${empty skills}">
                        <span style="color: red;">Have no mentor teach this skill</span>
                    </c:if>
                    <c:forEach items="${skills}" var="s">
                        <div class="mentor-item">
                            <div class="mentor-box">
                                <div class="mentor-avatar"><a href="cv-detail?id=${s.cv.id}">


                                        <img src="${pageContext.request.contextPath}/image/${s.mentor.avatar}" style="max-height: 200px" alt="Mentor Avatar" />
                                    </a>
                                </div>
                                <div class="mentor-info">
                                    <!-- Mentor Full Name and Account -->
                                    <h5>${s.mentor.user.fullname}</h5>
                                    <span>${s.mentor.user.account}</span>

                                    <!-- Display Skill List -->
                                    <div class="mentor-skills">
                                        <c:forEach items="${s.skills}" var="skill">
                                            <span class="skill-label">${skill.name}</span>
                                        </c:forEach>
                                    </div>
                                </div>

                                <div class="mentor-details">
                                    <!-- Display Rating -->
                                    <div class="rating-section">

                                        <div class="stars">
                                            <c:forEach begin="1" end="5" var="star">
                                                <i class="fa fa-star${s.rating >= star ? '' : '-o'}"></i>
                                            </c:forEach>
                                        </div>
                                    </div>

                                    <!-- Request Button and Number of Requests -->
                                    <div class="mentor-action">
                                        <a href="create-request?mid=${s.mentor.id}&sid=${s.skill.id}" class="btn btn-primary">Request</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>

                <nav class="mt-3" aria-label="Page navigation example">
                    <ul class="pagination justify-content-center">
                        <c:if test="${currentPage > 1}">
                            <li class="page-item">
                                <a class="btn btn-primary" href="?id=${skill.id}&search=${param.search}&page=${currentPage - 1}">Previous</a>
                            </li>
                        </c:if>
                        <c:forEach var="pageNum" begin="1" end="${totalPages}">
                            <li class="page-item ${pageNum == currentPage ? 'active' : ''}">
                                <a class="btn btn-primary" href="?id=${skill.id}&search=${param.search}&page=${pageNum}">${pageNum}</a>
                            </li>
                        </c:forEach>
                        <c:if test="${currentPage < totalPages}">
                            <li class="page-item">
                                <a class="btn btn-primary" href="?id=${skill.id}&search=${param.search}&page=${currentPage + 1}">Next</a>
                            </li>
                        </c:if>
                    </ul>
                </nav>

                <div class="section-area section-sp2 popular-courses-bx">
                    <div class="container">
                        <div class="row">
                            <div class="col-md-12 heading-bx left">
                                <h2 class="title-head">Our newest <span>Skills</span></h2>
                            </div>
                        </div>
                        <div class="row">
                            <div class="courses-carousel owl-carousel owl-btn-1 col-12 p-lr0">


                                <c:forEach items="${top10Skill}" var="s" >


                                    <div class="item" >
                                        <div class="cours-bx">
                                            <div class="action-box" style="max-height: 150px">

                                                <img src="${pageContext.request.contextPath}/image/${s.image}" alt="alt" />
                                                <a href="skill-detail?id=${s.id}" class="btn">Read More</a>
                                            </div>
                                            <div class="info-bx text-center">
                                                <h5><a href="#">${s.name}</a></h5>
                                                <span>${s.description}</span>
                                            </div>

                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>

                    </div>
                </div>
                <button  onclick="location.href = '${pageContext.request.contextPath}/skill-list'" class="btn-info"><li class="fa fa-arrow-left"></li></button>
            </div> 

        </div>
    </div>
    <jsp:include page="footer.jsp"></jsp:include>