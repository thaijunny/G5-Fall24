
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
                    <div class="col-md-3 heading-bx left">
                        <h2 class="title-head">My <span>Request</span></h2>
                    </div>
                    <form class=" col-md-9" action="${pageContext.request.contextPath}/my-request" method="get">
                        <div class="input-group">
                            <input value="${search}" name="search" type="text" class="form-control" placeholder="What do you want to learn today?" >
                            <div class="input-group-append">
                                <button class="btn" type="submit" >Search</button> 
                            </div>
                        </div>
                    </form>
                </div>
                <div class="row">
                    <c:if test="${not empty sessionScope.notificationErr}">
                        <div class="alert alert-danger alert-dismissible fade show" role="alert" style="text-align: center">
                            ${sessionScope.notificationErr}
                            <button type="button" class="btn-sm delete" data-dismiss="alert">x</button>
                        </div>
                        <%
                            session.removeAttribute("notificationErr");
                        %>
                    </c:if>
                    <c:if test="${not empty sessionScope.notification}">
                        <div class="alert alert-success alert-dismissible fade show" role="alert" style="text-align: center">
                            ${sessionScope.notification}
                            <button type="button" class="btn-sm delete" data-dismiss="alert">x</button>
                        </div>
                        <%
                            session.removeAttribute("notification");
                        %>
                    </c:if>
                    <div class="col-12">
                        <table class="table table-striped">
                            <thead>
                                <tr>
                                    <th>No</th>
                                    <th>Subject</th>
                                    <th>Deadline</th>
                                    <th>Mentor </th>
                                    <th>Skill </th>
                                    <th>Description </th>
                                    <th>Status</th>
                                    <th>Action</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="s" items="${request}" varStatus="status">
                                    <tr>
                                        <td>${status.index + 1}</td>
                                        <td>${s.subject}</td>
                                        <td>${s.mentor.user.fullname}</td>
                                        <td>${s.skill.name}</td>
                                        <td>${s.content}</td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${s.status == 'PROCESSING'}">
                                                    <span style="color: orange;">${s.status}</span>
                                                </c:when>
                                                <c:when test="${s.status == 'OPEN'}">
                                                    <span style="color: green;">${s.status}</span>
                                                </c:when>
                                                <c:when test="${s.status == 'CANCEL'}">
                                                    <span style="color: red;">${s.status}</span>
                                                </c:when>
                                                <c:when test="${s.status == 'CLOSE'}">
                                                    <span style="color: gray;">${s.status}</span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span>${s.status}</span> <!-- Default behavior if status is something else -->
                                                </c:otherwise>
                                            </c:choose>
                                        </td>


                                        <td>
                                            <c:choose>
                                                <c:when test="${s.status == 'OPEN'}">
                                                    <button  onclick="location.href = '${pageContext.request.contextPath}/update-request?id=${s.requestId}'"type="button" class=" btn-warning" >Update</button>
                                                    <button type="button" class=" btn-success" data-toggle="modal" data-target="#deactivateSkillModal-${s.requestId}">Cancel</button>

                                                </c:when>
                                                <c:when test="${s.status == 'CLOSE'}">
                                                    <button  onclick="location.href = '${pageContext.request.contextPath}/comment-mentor?id=${s.mentor.id}'"type="button" class=" btn-warning" >Comment</button>
                                                </c:when> 
                                                <c:otherwise>
                                                    <!--<button  onclick="location.href = '${pageContext.request.contextPath}/update-request?id=${s.requestId}''"type="button" class=" btn-warning" >Update</button>-->
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                    </tr>


                                    <!-- Deactivate Skill Modal -->
                                <div class="modal fade" id="deactivateSkillModal-${s.requestId}" tabindex="-1" aria-labelledby="deactivateSkillModal" aria-hidden="true">
                                    <div class="modal-dialog">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h5 class="modal-title">Deactivate Skill</h5>
                                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                    <span aria-hidden="true">&times;</span>
                                                </button>
                                            </div>
                                            <div class="modal-body">
                                                <p>Are you sure you want to approve this CV?</p>
                                            </div>
                                            <div class="modal-footer">
                                                <form action="${pageContext.request.contextPath}/manager/manage-cv" method="post">
                                                    <input type="hidden" name="id" value="${s.requestId}">
                                                    <input type="hidden" name="action" value="approve">
                                                    <button type="submit" class=" btn-danger">Yes, Approve</button>
                                                </form>
                                                <button type="button" class=" btn-secondary" data-dismiss="modal">Cancel</button>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <!-- Activate Skill Modal -->
                                <div class="modal fade" id="activateSkillModal-${s.requestId}" tabindex="-1" aria-labelledby="activateSkillModal" aria-hidden="true">
                                    <div class="modal-dialog">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h5 class="modal-title">Reject Skill</h5>
                                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                    <span aria-hidden="true">&times;</span>
                                                </button>
                                            </div>
                                            <div class="modal-body">
                                                <p>Are you sure you want to reject this CV?</p>
                                            </div>
                                            <div class="modal-footer">
                                                <form action="${pageContext.request.contextPath}/manager/manage-cv" method="post">
                                                    <input type="hidden" name="id" value="${s.requestId}">
                                                    <input type="hidden" name="action" value="reject">
                                                    <button type="submit" class=" btn-success">Yes, Reject</button>
                                                </form>
                                                <button type="button" class=" btn-secondary" data-dismiss="modal">Cancel</button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
                <nav class="mt-3" aria-label="Page navigation example">
                    <ul class="pagination justify-content-center">
                        <c:if test="${currentPage > 1}">
                            <li class="page-item">
                                <a class="btn btn-primary" href="?search=${param.search}&status=${param.status}&page=${currentPage - 1}">Previous</a>
                            </li>
                        </c:if>
                        <c:forEach var="pageNum" begin="1" end="${totalPages}">
                            <li class="page-item ${pageNum == currentPage ? 'active' : ''}">
                                <a class="btn btn-primary" href="?search=${param.search}&status=${param.status}&page=${pageNum}">${pageNum}</a>
                            </li>
                        </c:forEach>
                        <c:if test="${currentPage < totalPages}">
                            <li class="page-item">
                                <a class="btn btn-primary" href="?search=${param.search}&status=${param.status}&page=${currentPage + 1}">Next</a>
                            </li>
                        </c:if>
                    </ul>
                </nav>
            </div>

        </div>
    </div>
    <jsp:include page="footer.jsp"></jsp:include>