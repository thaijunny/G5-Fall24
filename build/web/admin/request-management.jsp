<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">

<title>${title}</title>
<jsp:include page="./gui/header.jsp"></jsp:include>
    <main class="ttr-wrapper">
        <div class="container-fluid">
            <!-- Breadcrumb, notification and other sections -->
            <div class="db-breadcrumb">
                <h4 class="breadcrumb-title">List Request</h4>
                <ul class="db-breadcrumb-list">
                    <li><a href="${pageContext.request.contextPath}/admin/dashboard"><i class="fa fa-home"></i>Home</a></li>
                    <c:if test="${not empty param.subject}">
                    <li><a href="${pageContext.request.contextPath}/admin/manage-request"></a></li>
                    </c:if>
                <li><a href="${pageContext.request.contextPath}/admin/manage-request">List Management</a></li>
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
        <form action="manage-request" method="get">
            <div class="row">
                <!-- Search by Name -->
                <div class="col-md-2">
                    <div class="form-group">
                        <div class="input-group">
                            <input type="text" class="form-control" name="search" placeholder="Enter name to search" value="${param.search}">
                        </div>
                    </div>
                </div>



                <!-- Status Filter -->
                <div class="feature-filters clearfix center m-b40 col-md-5" style=" margin-top: 9px">
                    <ul class="filters">
                        <li class="btn ${param.status == null || param.status == '' ? 'active' : ''}">
                            <a href="manage-request?search=${param.search}&status=&page=1&startDate=${param.startDate}&endDate=${param.endDate}">
                                <span>All</span>
                            </a>
                        </li>
                        <li class="btn ${param.status eq 'OPEN' ? 'active' : ''}">
                            <a href="manage-request?search=${param.search}&status=OPEN&page=1&startDate=${param.startDate}&endDate=${param.endDate}">
                                <span>OPEN</span>
                            </a>
                        </li>
                        <li class="btn ${param.status eq 'PROCESSING' ? 'active' : ''}">
                            <a href="manage-request?search=${param.search}&status=PROCESSING&page=1&startDate=${param.startDate}&endDate=${param.endDate}">
                                <span>PROCESSING</span>
                            </a>
                        </li>
                        <li class="btn ${param.status eq 'CANCEL' ? 'active' : ''}">
                            <a href="manage-request?search=${param.search}&status=CANCEL&page=1&startDate=${param.startDate}&endDate=${param.endDate}">
                                <span>BLOCKED</span>
                            </a>
                        </li>
                        <li class="btn ${param.status eq 'CLOSE' ? 'active' : ''}">
                            <a href="manage-request?search=${param.search}&status=CLOSE&page=1&startDate=${param.startDate}&endDate=${param.endDate}">
                                <span>CLOSE</span>
                            </a>
                        </li>
                    </ul>
                </div>
                <!-- Date Range Filters -->
                <div class="col-md-2">
                    <div class="form-group">
                        <input type="date" class="form-control" name="startDate" value="${param.startDate}" placeholder="Start Date">
                    </div>
                </div>
                <div class="col-md-2">
                    <div class="form-group">
                        <input type="date" class="form-control" name="endDate" value="${param.endDate}" placeholder="End Date">
                    </div>
                </div>
                <div class="col-md-1">
                    <div class="form-group">
                        <button type="submit" style="margin-top: 2px" class="btn btn-primary"><i class="fa fa-search"></i></button>
                    </div>
                </div>
            </div>


            <!-- Hidden Inputs -->
            <input type="hidden" name="status" value="${param.status}">
            <input type="hidden" name="page" value="1">
        </form>




        <!-- Skills List -->
        <div class="row">
            <div class="col-12">
                <table class="table table-striped">
                    <thead>
                        <tr>
                            <th>No</th>
                            <th>Title</th>
                            <th>Content</th>
                            <th>Skill</th>
                            <th>Price</th>
                            <th>Mentee</th>
                            <th>Mentor</th>
                            <th>Start Date</th>
                            <th>End Date</th>
                            <th>Status</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="s" items="${requests}" varStatus="status">
                            <tr>
                                <td>${status.index + 1}</td>
                                <td>${s.subject}</td>
                                <td>${s.content}</td>
                                <td>${s.skill.name}</td>
                                <td>${s.price}</td>
                                <td>${s.mentee.user.fullname}</td>
                                <td>${s.mentor.user.fullname}</td>
                                <td>${s.startDate}</td>
                                <td>${s.endDate}</td>
                                <td>${s.status}</td>

                                <td>
                                    <button onclick="window.location.href='request-detail?id=${s.requestId}'" type="button" class=" btn-info"><i class="fa fa-info-circle"></i></button>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>

        <!-- Pagination -->
        <div class="pagination-bx rounded-sm gray clearfix">

            <ul class="pagination justify-content-center">
                <c:if test="${currentPage > 1}">
                    <li class="page-item">
                        <a class="previous" href="?search=${param.search}&status=${param.status}&startDate=${param.startDate}&endDate=${param.endDate}&page=${currentPage - 1}"><i class="ti-arrow-left"></i> Prev</a>
                    </li>
                </c:if>
                <c:forEach var="pageNum" begin="1" end="${totalPages}">
                    <li class="${pageNum == currentPage ? 'active' : ''}">
                        <a class="btn btn-primary" href="?search=${param.search}&status=${param.status}&startDate=${param.startDate}&endDate=${param.endDate}&&page=${pageNum}">${pageNum}</a>
                    </li>
                </c:forEach>
                <c:if test="${currentPage < totalPages}">
                    <li class="page-item">
                        <a class="next" href="?search=${param.search}&status=${param.status}&startDate=${param.startDate}&endDate=${param.endDate}&&page=${currentPage + 1}">Next <i class="ti-arrow-right"></i></a>
                    </li>
                </c:if>
            </ul>
        </div>
    </div>
</main>
<jsp:include page="./gui/footer.jsp"></jsp:include>
