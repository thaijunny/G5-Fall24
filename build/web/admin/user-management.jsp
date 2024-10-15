<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">

<title>${title}</title>
<jsp:include page="./gui/header.jsp"></jsp:include>
    <main class="ttr-wrapper">
        <div class="container-fluid">
            <!-- Breadcrumb, notification and other sections -->
            <div class="db-breadcrumb">
                <h4 class="breadcrumb-title">List User</h4>
                <ul class="db-breadcrumb-list">
                    <li><a href="${pageContext.request.contextPath}/admin/dashboard"><i class="fa fa-home"></i>Home</a></li>
                    <c:if test="${not empty param.subject}">
                    <li><a href="${pageContext.request.contextPath}/admin/manage-user"></a></li>
                    </c:if>
                <li><a href="${pageContext.request.contextPath}/admin/manage-user">List User</a></li>
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
        <form action="manage-user" method="get">
            <div class="row">
                <div class="col-md-5">
                    <div class="form-group">
                        <div class="input-group" style="width: 80%">
                            <input type="text" class="form-control" name="search" placeholder="Enter name to search" value="${param.search}">
                        </div>
                    </div>
                </div>

                <!-- Status Filter -->
                <div class="feature-filters clearfix center m-b40 col-md-4" style="text-align: right; margin-top: 9px">
                    <ul class="filters">
                        <li class="btn ${param.status == null || param.status == '' ? 'active' : ''}">
                            <a href="manage-user?search=${param.search}&status=&role=${param.role}&page=1"><span>All</span></a>
                        </li>
                        <li class="btn ${param.status eq 'ACTIVE' ? 'active' : ''}">
                            <a href="manage-user?search=${param.search}&status=ACTIVE&role=${param.role}&page=1"><span>ACTIVE</span></a>
                        </li>
                        <li class="btn ${param.status eq 'INACTIVE' ? 'active' : ''}">
                            <a href="manage-user?search=${param.search}&status=INACTIVE&role=${param.role}&page=1"><span>INACTIVE</span></a>
                        </li>
                        <li class="btn ${param.status eq 'BLOCKED' ? 'active' : ''}">
                            <a href="manage-user?search=${param.search}&status=BLOCKED&role=${param.role}&page=1"><span>BLOCKED</span></a>
                        </li>
                    </ul>
                </div>

                <!-- Role Filter -->
                <div class="col-md-3">
                    <div class="form-group">
                        <select id="role" name="role" class="form-control" onchange="this.form.submit()">
                            <option value="" ${param.role == null || param.role == '' ? 'selected' : ''}>All Role</option>
                            <option value="ADMIN" ${param.role == 'ADMIN' ? 'selected' : ''}>ADMIN</option>
                            <option value="MANAGER" ${param.role == 'MANAGER' ? 'selected' : ''}>MANAGER</option>
                            <option value="MENTOR" ${param.role == 'MENTOR' ? 'selected' : ''}>MENTOR</option>
                            <option value="MENTEE" ${param.role == 'MENTEE' ? 'selected' : ''}>MENTEE</option>
                        </select>
                    </div>
                </div>
            </div>
            <input type="hidden" name="status" value="${param.status}">
            <input type="hidden" name="page" value="1">
        </form>


        <!-- Button to add a new skill -->
        <button type="button" class="btn btn-warning" data-toggle="modal" data-target="#addSkillModal">Add User</button>

        <!-- Skills List -->
        <div class="row">
            <div class="col-12">
                <table class="table table-striped">
                    <thead>
                        <tr>
                            <th>No</th>
                            <th>Name</th>
                            <th>Email</th>
                            <th>Account</th>
                            <th>Role</th>
                            <th>Total star</th>
                            <th>Status</th>
                            <th>Total Current Request</th>
                            <th>% Complete</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="s" items="${skills}" varStatus="status">
                            <tr>
                                <td>${status.index + 1}</td>
                                <td>${s.fullname}</td>
                                <td>${s.email}</td>
                                <td>${s.account}</td>
                                <td>${s.role}</td>

                                <!-- Display star rating for mentor -->
                                <c:if test="${s.mentor != null}">
                                    <td>
                                        <!-- Display whole stars -->
                                        <c:forEach var="i" begin="1" end="${s.wholeStars}">
                                            <i class="fas fa-star" style="color: orange;"></i> <!-- Filled star -->
                                        </c:forEach>

                                        <!-- Display half star if applicable -->
                                        <c:if test="${s.hasHalfStar}">
                                            <i class="fas fa-star-half-alt" style="color: orange;"></i> <!-- Half star -->
                                        </c:if>

                                        <!-- Display empty stars -->
                                        <c:forEach var="i" begin="${s.wholeStars + (s.hasHalfStar ? 1 : 0)}" end="4">
                                            <i class="far fa-star" style="color: orange;"></i> <!-- Empty star -->
                                        </c:forEach>
                                    </td>
                                </c:if>

                                <!-- Display "Not a mentor" message for non-mentors -->
                                <c:if test="${s.mentor == null}">
                                    <td style="color: red">Not a mentor</td>
                                </c:if>

                                <!-- Status with color coding -->
                                <td>
                                    <c:choose>
                                        <c:when test="${s.status == 'ACTIVE'}">
                                            <span style="color: green;">${s.status}</span>
                                        </c:when>
                                        <c:when test="${s.status == 'BLOCKED'}">
                                            <span style="color: red;">${s.status}</span>
                                        </c:when>
                                        <c:when test="${s.status == 'INACTIVE'}">
                                            <span style="color: orange;">${s.status}</span>
                                        </c:when>
                                    </c:choose>
                                </td>
                                <c:if test="${s.mentor != null}">
                                    <td>
                                        ${s.currentlyAcceptedRequests}
                                    </td> 
                                </c:if>
                                <c:if test="${s.mentor == null}">
                                    <td style="color: red">Not a mentor</td>
                                </c:if>
                                <c:if test="${s.mentor == null}">
                                    <td style="color: red">Not a mentor</td>
                                </c:if>
                                <c:if test="${s.mentor != null}">
                                    <td>
                                        <div class="progress" style="width: 150px;">
                                            <div class="progress-bar" role="progressbar" 
                                                 style="width: ${s.completedPercentage}%;"
                                                 aria-valuenow="${s.completedPercentage}" 
                                                 aria-valuemin="0" 
                                                 aria-valuemax="100">
                                                ${s.completedPercentage}%
                                            </div>
                                        </div>
                                    </td>
                                </c:if>
                                <td>
                                    <c:choose>
                                        <c:when test="${s.status == 'ACTIVE'}">
                                            <button type="button" class=" btn-danger" data-toggle="modal" data-target="#blockUserModal-${s.id}">Block</button>
                                        </c:when>
                                        <c:when test="${s.status == 'BLOCKED'}">
                                            <button type="button" class=" btn-success" data-toggle="modal" data-target="#unblockUserModal-${s.id}">Unblock</button>
                                        </c:when>
                                    </c:choose>
                                </td>
                            </tr>
                            <!-- Block User Modal -->
                        <div class="modal fade" id="blockUserModal-${s.id}" tabindex="-1" aria-labelledby="blockUserModalLabel-${s.id}" aria-hidden="true">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="blockUserModalLabel-${s.id}">Confirm Block User</h5>
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                    <div class="modal-body">
                                        Are you sure you want to block ${s.fullname}?
                                    </div>
                                    <div class="modal-footer">
                                        <form action="${pageContext.request.contextPath}/admin/manage-user" method="post">
                                            <input type="hidden" name="userId" value="${s.id}">
                                            <input type="hidden" name="action" value="block">
                                            <button type="submit" class=" btn-danger">Yes, Block</button>
                                        </form>
                                        <button type="button" class=" btn-secondary" data-dismiss="modal">Cancel</button>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!-- Unblock User Modal -->
                        <div class="modal fade" id="unblockUserModal-${s.id}" tabindex="-1" aria-labelledby="unblockUserModalLabel-${s.id}" aria-hidden="true">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="unblockUserModalLabel-${s.id}">Confirm Unblock User</h5>
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                    <div class="modal-body">
                                        Are you sure you want to unblock ${s.fullname}?
                                    </div>
                                    <div class="modal-footer">
                                        <form action="${pageContext.request.contextPath}/admin/manage-user" method="post">
                                            <input type="hidden" name="userId" value="${s.id}">
                                            <input type="hidden" name="action" value="unblock">
                                            <button type="submit" class=" btn-success">Yes, Unblock</button>
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
        <div class="pagination-bx rounded-sm gray clearfix">

            <ul class="pagination justify-content-center">
                <c:if test="${currentPage > 1}">
                    <li class="page-item">
                        <a class="previous" href="?search=${param.search}&role=${param.role}&status=${param.status}&page=${currentPage - 1}"><i class="ti-arrow-left"></i> Prev</a>
                    </li>
                </c:if>
                <c:forEach var="pageNum" begin="1" end="${totalPages}">
                    <li class="${pageNum == currentPage ? 'active' : ''}">
                        <a class="btn btn-primary" href="?search=${param.search}&role=${param.role}&status=${param.status}&page=${pageNum}">${pageNum}</a>
                    </li>
                </c:forEach>
                <c:if test="${currentPage < totalPages}">
                    <li class="page-item">
                        <a class="next" href="?search=${param.search}&role=${param.role}&status=${param.status}&page=${currentPage + 1}">Next <i class="ti-arrow-right"></i></a>
                    </li>
                </c:if>
            </ul>
        </div>
    </div>
</main>

<!-- Add Skill Modal -->
<div class="modal fade" id="addSkillModal" tabindex="-1" aria-labelledby="addSkillModal" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Add New Manager</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form id="addManagerForm" action="${pageContext.request.contextPath}/admin/manage-user" method="post" onsubmit="return validateForm()">
                    <input type="hidden" name="action" value="add">
                    <div class="form-group">
                        <label for="fullName">Full Name</label>
                        <input class="form-control" id="fullName" name="fullName" type="text" required>
                        <span id="fullNameError" class="text-danger"></span>
                    </div>
                    <div class="form-group">
                        <label for="email">Email</label>
                        <input class="form-control" id="email" name="email" type="email" required>
                        <span id="emailError" class="text-danger"></span>
                    </div>
                    <div class="form-group">
                        <label for="phoneNumber">Phone Number</label>
                        <input class="form-control" id="phoneNumber" name="phoneNumber" type="text" required>
                        <span id="phoneNumberError" class="text-danger"></span>
                    </div>
                    <div class="form-group">
                        <label for="gender">Gender</label>
                        <select class="form-control" id="gender" name="gender" required>
                            <option value="MALE">Male</option>
                            <option value="FEMALE">Female</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="role">Role</label>
                        <select class="form-control" id="role" name="role" required>
                            <option value="MANAGER">Manager</option>
                            <option value="MENTEE">Mentee</option>
                            <option value="MENTOR">Mentor</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="address">Address</label>
                        <input class="form-control" id="address" name="address" type="text" required>
                        <span id="addressError" class="text-danger"></span>
                    </div>
                    <button type="submit" class="btn btn-primary">Add Manager</button>
                </form>
            </div>
        </div>
    </div>
</div>


<script>
    function validateForm() {
        let isValid = true;

        // Get form field values
        let fullName = document.getElementById('fullName').value.trim();
        let email = document.getElementById('email').value.trim();
        let password = document.getElementById('password').value.trim();
        let phoneNumber = document.getElementById('phoneNumber').value.trim();
        let address = document.getElementById('address').value.trim();

        // Reset error messages
        document.getElementById('fullNameError').textContent = "";
        document.getElementById('emailError').textContent = "";
        document.getElementById('passwordError').textContent = "";
        document.getElementById('phoneNumberError').textContent = "";
        document.getElementById('addressError').textContent = "";

        // Full name validation
        if (fullName === "") {
            document.getElementById('fullNameError').textContent = "Full Name is required.";
            isValid = false;
        }

        // Email validation
        if (email === "") {
            document.getElementById('emailError').textContent = "Email is required.";
            isValid = false;
        } else if (!validateEmail(email)) {
            document.getElementById('emailError').textContent = "Invalid email format.";
            isValid = false;
        }

        // Password validation
        if (password === "") {
            document.getElementById('passwordError').textContent = "Password is required.";
            isValid = false;
        }

        // Phone number validation
        if (phoneNumber === "") {
            document.getElementById('phoneNumberError').textContent = "Phone number is required.";
            isValid = false;
        } else if (!/^\d+$/.test(phoneNumber)) { // Allow only digits
            document.getElementById('phoneNumberError').textContent = "Phone number must be digits only.";
            isValid = false;
        }

        // Address validation
        if (address === "") {
            document.getElementById('addressError').textContent = "Address is required.";
            isValid = false;
        }

        return isValid;
    }

    // Email validation helper function
    function validateEmail(email) {
        const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        return re.test(String(email).toLowerCase());
    }
</script>

<jsp:include page="./gui/footer.jsp"></jsp:include>
