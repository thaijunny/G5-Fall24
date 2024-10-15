<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="./gui/header.jsp"></jsp:include>

    <main class="ttr-wrapper">
        <div class="container-fluid">
            <!-- Breadcrumb, notification and other sections -->
            <div class="db-breadcrumb">
                <h4 class="breadcrumb-title">List Skills</h4>
                <ul class="db-breadcrumb-list">
                    <li><a href="${pageContext.request.contextPath}/admin/dashboard"><i class="fa fa-home"></i>Home</a></li>
                    <c:if test="${not empty param.subject}">
                    <li><a href="${pageContext.request.contextPath}/admin/manage-skill"></a></li>
                    </c:if>
                <li><a href="${pageContext.request.contextPath}/admin/manage-skill">Skills List</a></li>
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
        <form action="manage-skill" method="get">
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
                            <a href="manage-skill?search=${param.search}&status=&page=1"><span>All</span></a>
                        </li>
                        <li class="btn ${param.status eq 'ACTIVE' ? 'active' : ''}">
                            <a href="manage-skill?search=${param.search}&status=ACTIVE&page=1"><span>ACTIVE</span></a>
                        </li>
                        <li class="btn ${param.status eq 'INACTIVE' ? 'active' : ''}">
                            <a href="manage-skill?search=${param.search}&status=INACTIVE&page=1"><span>INACTIVE</span></a>
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
                            <th>Image</th>
                            <th>Description</th>
                            <th>Status</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="s" items="${skills}" varStatus="status">
                            <tr>
                                <td>${status.index + 1}</td>
                                <td>${s.name}</td>
                                <td><img src="${pageContext.request.contextPath}/image/${s.image}" width="100" height="100" alt="Skill Image"/></td>
                                <td>${s.description}</td>
                                <td style="color: ${s.status == 'ACTIVE' ? 'green' : 'red'};">
                                    ${s.status}
                                </td>
                                <td>
                                    <button type="button" class=" btn-info" data-toggle="modal" data-target="#editSkillModal-${s.id}">Edit</button>

                                    <!-- Activate/Deactivate Button -->
                                    <c:choose>
                                        <c:when test="${s.status == 'ACTIVE'}">
                                            <button type="button" class=" btn-danger" data-toggle="modal" data-target="#deactivateSkillModal-${s.id}">Deactivate</button>
                                        </c:when>
                                        <c:otherwise>
                                            <button type="button" class=" btn-success" data-toggle="modal" data-target="#activateSkillModal-${s.id}">Activate</button>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>

                            <!-- Edit Skill Modal -->
                        <div class="modal fade" id="editSkillModal-${s.id}" tabindex="-1" aria-labelledby="editSkillModal" aria-hidden="true">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title">Edit Skill</h5>
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                    <div class="modal-body">
                                        <form action="${pageContext.request.contextPath}/admin/manage-skill" method="post" enctype="multipart/form-data" onsubmit="return validateForm(false)">
                                            <div class="form-group">
                                                <label for="name">Skill Name</label>
                                                <input class="form-control" id="name" name="name" type="text" value="${s.name}" placeholder="Enter name">
                                                <span id="name_error" class="text-danger"></span><br>

                                                <label for="image">Upload New Image</label>
                                                <input class="form-control" id="image" name="image" type="file" accept="image/*">
                                                <span id="image_error" class="text-danger"></span><br>

                                                <!-- Display current image -->
                                                <label>Current Image</label><br>
                                                <img src="${pageContext.request.contextPath}/image/${s.image}" width="100" height="100" alt="Current Image"/><br>

                                                <label for="description">Description</label>
                                                <textarea class="form-control" id="description" name="description" placeholder="Enter description">${s.description}</textarea>
                                                <span id="description_error" class="text-danger"></span><br>

                                                <input type="hidden" name="skillId" value="${s.id}">
                                                <input type="hidden" name="action" value="update">
                                            </div>

                                            <button style="float: right" type="submit" class="btn btn-primary">Update</button>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
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
                                        <p>Are you sure you want to deactivate the skill: ${s.name}?</p>
                                    </div>
                                    <div class="modal-footer">
                                        <form action="${pageContext.request.contextPath}/admin/manage-skill" method="post">
                                            <input type="hidden" name="skillId" value="${s.id}">
                                            <input type="hidden" name="action" value="deactivate">
                                            <button type="submit" class=" btn-danger">Yes, Deactivate</button>
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
                                        <h5 class="modal-title">Activate Skill</h5>
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                    <div class="modal-body">
                                        <p>Are you sure you want to activate the skill: ${s.name}?</p>
                                    </div>
                                    <div class="modal-footer">
                                        <form action="${pageContext.request.contextPath}/admin/manage-skill" method="post">
                                            <input type="hidden" name="skillId" value="${s.id}">
                                            <input type="hidden" name="action" value="activate">
                                            <button type="submit" class=" btn-success">Yes, Activate</button>
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

<!-- Add Skill Modal -->
<div class="modal fade" id="addSkillModal" tabindex="-1" aria-labelledby="addSkillModal" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Add New Skill</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form id="addSkillForm" action="${pageContext.request.contextPath}/admin/manage-skill" method="post" enctype="multipart/form-data" onsubmit="return validateAddForm()">
                    <div class="form-group">
                        <label for="addName">Skill Name</label>
                        <input class="form-control" id="addName" name="name" type="text" placeholder="Enter name">
                        <span id="add_name_error" class="text-danger"></span><br>

                        <label for="addImage">Upload Image</label>
                        <input class="form-control" id="addImage" name="image" type="file" accept="image/*">
                        <span id="add_image_error" class="text-danger"></span><br>

                        <label for="addDescription">Description</label>
                        <textarea class="form-control" id="addDescription" name="description" placeholder="Enter description"></textarea>
                        <span id="add_description_error" class="text-danger"></span><br>

                        <input type="hidden" name="action" value="add">
                    </div>

                    <button style="float: right" type="submit" class="btn btn-primary">Add</button>
                </form>
            </div>
        </div>
    </div>
</div>


<script>
    function validateForm(isAddAction) {
        let isValid = true;
        let name = document.getElementById("name").value.trim();
        let description = document.getElementById("description").value.trim();
        let image = document.getElementById("image").value.trim();

        // Reset error messages
        document.getElementById("name_error").textContent = "";
        document.getElementById("description_error").textContent = "";
        document.getElementById("image_error").textContent = "";

        if (name === "") {
            document.getElementById("name_error").textContent = "Name is required.";
            isValid = false;
        }

        if (description === "") {
            document.getElementById("description_error").textContent = "Description is required.";
            isValid = false;
        }

        if (isAddAction && image === "") {
            document.getElementById("image_error").textContent = "Please upload an image.";
            isValid = false;
        }

        return isValid;
    }
    function validateAddForm() {
        let isValid = true;

        // Get the values for Add Form
        let name = document.getElementById("addName").value.trim();
        let description = document.getElementById("addDescription").value.trim();
        let image = document.getElementById("addImage").value.trim();

        // Reset error messages
        document.getElementById("add_name_error").textContent = "";
        document.getElementById("add_description_error").textContent = "";
        document.getElementById("add_image_error").textContent = "";

        // Validate name
        if (name === "") {
            document.getElementById("add_name_error").textContent = "Name is required.";
            isValid = false;
        }

        // Validate description
        if (description === "") {
            document.getElementById("add_description_error").textContent = "Description is required.";
            isValid = false;
        }

        // Validate image (required for adding)
        if (image === "") {
            document.getElementById("add_image_error").textContent = "Please upload an image.";
            isValid = false;
        }

        return isValid;
    }
</script>

<jsp:include page="./gui/footer.jsp"></jsp:include>
