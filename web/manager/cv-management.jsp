<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="./ui/head.jsp"></jsp:include>

    <main class="ttr-wrapper">
        <div class="container-fluid">
            <!-- Breadcrumb, notification and other sections -->
            <div class="db-breadcrumb">
                <h4 class="breadcrumb-title">List Skills</h4>
                <ul class="db-breadcrumb-list">
                    <li><a href="${pageContext.request.contextPath}/admin/dashboard"><i class="fa fa-home"></i>Home</a></li>
                    <c:if test="${not empty param.subject}">
                    <li><a href="${pageContext.request.contextPath}/admin/manage-cv"></a></li>
                    </c:if>
                <li><a href="${pageContext.request.contextPath}/admin/manage-cv">Skills List</a></li>
            </ul>
        </div>

        <!-- Notifications -->
        <c:if test="${not empty sessionScope.notification}">
            <div class="alert alert-success alert-dismissible fade show" role="alert" style="text-align: center">
                ${sessionScope.notification}
                <button type="button" class="btn-danger" data-dismiss="alert" aria-label="Close">X</button>
            </div>
            <%
                session.removeAttribute("notification");
            %>
        </c:if>

        <c:if test="${not empty sessionScope.notificationErr}">
            <div class="alert alert-danger alert-dismissible fade show" role="alert" style="text-align: center">
                ${sessionScope.notificationErr}
                <button type="button" class="btn-danger" data-dismiss="alert">X</button>
            </div>
            <%
                session.removeAttribute("notificationErr");
            %>
        </c:if>

        <!-- Filter Section -->
        <form action="manage-cv" method="get">
            <div class="row">
                <div class="col-md-6">
                    <div class="form-group">
                        <div class="input-group" style="width: 80%">
                            <input type="text" class="form-control" name="search" placeholder="Enter skill name to search" value="${param.search}">
                        </div>
                    </div>
                </div>
                <div class="feature-filters clearfix center m-b40 col-md-4" style="text-align: right; margin-top: 9px">
                    <ul class="filters">
                        <li class="btn ${param.status == null || param.status == '' ? 'active' : ''}">
                            <a href="manage-cv?search=${param.search}&status=&page=1"><span>All</span></a>
                        </li>
                        <li class="btn ${param.status eq 'PENDING' ? 'active' : ''}">
                            <a href="manage-cv?search=${param.search}&status=PENDING&page=1"><span>PENDING</span></a>
                        </li>
                        <li class="btn ${param.status eq 'REJECTED' ? 'active' : ''}">
                            <a href="manage-cv?search=${param.search}&status=REJECTED&page=1"><span>REJECTED</span></a>
                        </li>
                        <li class="btn ${param.status eq 'APPROVED' ? 'active' : ''}">
                            <a href="manage-cv?search=${param.search}&status=APPROVED&page=1"><span>APPROVED</span></a>
                        </li>
                    </ul>
                </div>
            </div>
            <input type="hidden" name="status" value="${param.status}">
            <input type="hidden" name="page" value="1">
        </form>

        <!-- Button to add a new skill -->
        <button type="button" class="btn btn-warning" data-toggle="modal" data-target="#addSkillModal">Add Skill</button>

        <!-- Skills List -->
        <div class="row">
            <div class="col-12">
                <table class="table table-striped">
                    <thead>
                        <tr>
                            <th>No</th>
                            <th>Name</th>
                            <th>Account</th>
                            <th>Email</th>
                            <th>Price</th>
                            <th>Status</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="s" items="${cv}" varStatus="status">
                            <tr>
                                <td>${status.index + 1}</td>
                                <td>${s.mentor.user.fullname}</td>
                                <td>${s.mentor.user.account}</td>
                                <td>${s.mentor.user.email}</td>
                                <td>${s.price}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${s.status == 'PENDING'}">
                                            <span style="color: orange;">${s.status}</span>
                                        </c:when>
                                        <c:when test="${s.status == 'APPROVED'}">
                                            <span style="color: green;">${s.status}</span>
                                        </c:when>
                                        <c:when test="${s.status == 'REJECTED'}">
                                            <span style="color: red;">${s.status}</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span>${s.status}</span> <!-- Default behavior if status is something else -->
                                        </c:otherwise>
                                    </c:choose>
                                </td>


                                <td>
                                    <c:choose>
                                        <c:when test="${s.status == 'PENDING'}">
                                            <button  onclick="location.href = '${pageContext.request.contextPath}/cv-detail?id=${s.id}'"type="button" class=" btn-warning" >View</button>
                                            <button type="button" class=" btn-danger" data-toggle="modal" data-target="#activateSkillModal-${s.id}">Reject</button>
                                            <button type="button" class=" btn-success" data-toggle="modal" data-target="#deactivateSkillModal-${s.id}">Approve</button>
                                        </c:when>
                                        <c:otherwise>
                                            <button  onclick="location.href = '${pageContext.request.contextPath}/cv-detail?id=${s.id}'"type="button" class=" btn-warning" >View</button>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>


                            <!-- Deactivate Skill Modal -->
                        <div class="modal fade" id="deactivateSkillModal-${s.id}" tabindex="-1" aria-labelledby="deactivateSkillModal" aria-hidden="true">
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
                                            <input type="hidden" name="id" value="${s.id}">
                                            <input type="hidden" name="action" value="approve">
                                            <button type="submit" class=" btn-danger">Yes, Approve</button>
                                        </form>
                                        <button type="button" class=" btn-secondary" data-dismiss="modal">Cancel</button>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!-- Activate Skill Modal -->
                        <div class="modal fade" id="activateSkillModal-${s.id}" tabindex="-1" aria-labelledby="activateSkillModal" aria-hidden="true">
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
                                            <input type="hidden" name="id" value="${s.id}">
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

        <!-- Pagination -->
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
</main>

<jsp:include page="./ui/foot.jsp"></jsp:include>
