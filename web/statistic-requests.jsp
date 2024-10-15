<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="header.jsp"></jsp:include>

    <title>${title}</title>

<div class="page-content bg-white">
    <div class="page-banner ovbl-dark" style="background-image:url(assets/images/banner/banner3.jpg);">
        <div class="container">
            <div class="page-banner-entry">
                <h1 class="text-white">Statistic of Requests</h1>
            </div>
        </div>
    </div>

    <div class="breadcrumb-row">
        <div class="container">
            <ul class="list-inline">
                <li><a href="home">Home</a></li>
                <li>Statistic of Requests</li>
            </ul>
        </div>
    </div>

    <div class="container">
        <!-- Statistics Summary -->
       <div class="row">
    <!-- Total Requests Card -->
    <div class="col-md-6">
        <div class="card">
            <div class="card-body">
                <h5 class="card-title">Total Requests</h5>
                <p class="card-text" style="font-size: 24px; font-weight: bold;">${totalRequests}</p>
            </div>
        </div>
    </div>

    <!-- Total Mentors Involved Card -->
    <div class="col-md-6">
        <div class="card">
            <div class="card-body">
                <h5 class="card-title">Total Mentors Involved</h5>
                <p class="card-text" style="font-size: 24px; font-weight: bold;">${totalMentors}</p>
            </div>
        </div>
    </div>
</div>


        <!-- Search Form -->
        <form class="mt-3" action="request-statistic" method="get">
            <div class="input-group mb-3">
                <input type="text" class="form-control" name="search" placeholder="Search by subject or mentor" value="${search}">
                <button class="btn btn-outline-secondary" type="submit">Search</button>
            </div>
        </form>

        <!-- Filter by Status -->
        <ul class="nav nav-pills mb-3">
            <li class="nav-item">
                <a class="nav-link ${status == null || status == '' ? 'active' : ''}" 
                   style="${status == null || status == '' ? 'background-color: #007bff; color: white;' : ''}"
                   href="request-statistic?status=&page=1">All</a>
            </li>
            <li class="nav-item">
                <a class="nav-link ${status == 'OPEN' ? 'active' : ''}" 
                   style="${status == 'OPEN' ? 'background-color: green; color: white;' : ''}"
                   href="request-statistic?status=OPEN&page=1">Open</a>
            </li>
            <li class="nav-item">
                <a class="nav-link ${status == 'PROCESSING' ? 'active' : ''}" 
                   style="${status == 'PROCESSING' ? 'background-color: orange; color: white;' : ''}"
                   href="request-statistic?status=PROCESSING&page=1">Processing</a>
            </li>
            <li class="nav-item">
                <a class="nav-link ${status == 'CLOSE' ? 'active' : ''}" 
                   style="${status == 'CLOSE' ? 'background-color: gray; color: white;' : ''}"
                   href="request-statistic?status=CLOSE&page=1">Closed</a>
            </li>
            <li class="nav-item">
                <a class="nav-link ${status == 'CANCEL' ? 'active' : ''}" 
                   style="${status == 'CANCEL' ? 'background-color: red; color: white;' : ''}"
                   href="request-statistic?status=CANCEL&page=1">Cancel</a>
            </li>
        </ul>


        <!-- Table of Requests -->
        <table class="table table-bordered">
            <thead>
                <tr>
                    <th>#</th>
                    <th>Title</th>
                    <th>Mentor</th>
                    <th>Deadline</th>
                    <th>Status</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="request" items="${requests}" varStatus="status">
                    <tr>
                        <td>${status.index + 1}</td>
                        <td>${request.subject}</td>
                        <td>${request.mentor.user.fullname}</td>
                        <td>${request.deadline}</td>
                        <td>
                            <span style="
                                  ${request.status == 'OPEN' ? 'color: green;' : ''}
                                  ${request.status == 'PROCESSING' ? 'color: orange;' : ''}
                                  ${request.status == 'CLOSE' ? 'color: gray;' : ''}
                                  ${request.status == 'CANCEL' ? 'color: red;' : ''}">
                                ${request.status}
                            </span>
                        </td>

                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <!-- Pagination -->
        <nav aria-label="Page navigation">
            <ul class="pagination justify-content-center">
                <c:if test="${currentPage > 1}">
                    <li class="page-item">
                        <a class="page-link" href="request-statistic?search=${search}&status=${status}&page=${currentPage - 1}">Previous</a>
                    </li>
                </c:if>
                <c:forEach var="pageNum" begin="1" end="${totalPages}">
                    <li class="page-item ${pageNum == currentPage ? 'active' : ''}">
                        <a class="page-link" href="request-statistic?search=${search}&status=${status}&page=${pageNum}">${pageNum}</a>
                    </li>
                </c:forEach>
                <c:if test="${currentPage < totalPages}">
                    <li class="page-item">
                        <a class="page-link" href="request-statistic?search=${search}&status=${status}&page=${currentPage + 1}">Next</a>
                    </li>
                </c:if>
            </ul>
        </nav>
    </div>
</div>

<jsp:include page="footer.jsp"></jsp:include>
