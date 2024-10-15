<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="header.jsp"></jsp:include>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="page-content bg-white">
    <div class="page-banner ovbl-dark" style="background-image:url(assets/images/banner/banner3.jpg);">
        <div class="container">
            <div class="page-banner-entry">
                <h1 class="text-white">My CV</h1>
            </div>
        </div>
    </div>

    <div class="breadcrumb-row">
        <div class="container">
            <ul class="list-inline">
                <li><a href="home">Home</a></li>
                <li>My CV</li>
            </ul>
        </div>
    </div>

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

    <div class="container">
        <div class="row">
            <div class="col-lg-12">
                <c:choose>
                    <c:when test="${cvExists}">
                        <c:if test="${cvStatus == 'PENDING'}">
                            <div class="alert alert-warning" role="alert" style="text-align: center">
                                Your CV is currently pending approval. You cannot edit it at this time. Please wait for approval.
                            </div>
                        </c:if>
                        <c:if test="${cvStatus == 'APPROVED'}">
                            <div class="alert alert-danger" role="alert" style="text-align: center">
                                Your CV have been Approved!.
                            </div>
                        </c:if>
                        <c:if test="${cvStatus == 'REJECTED'}">
                            <div class="alert alert-danger" role="alert" style="text-align: center">
                                Your CV have been Rejected!.
                            </div>
                        </c:if>
                        <h2 style="text-align: center">Edit Your CV</h2> 
                        <div class="row">
                            <div class="form-group col-md-12"style="text-align: center">

                                <img src="image/${mentor.avatar}" alt="Current Avatar" width="200px" height="200px"/>
                            </div>
                            <div class="form-group col-md-6">
                                <label for="avatar">Name</label>
                                <h3 class="form-control" >${account.fullname}</h3>
                            </div>

                            <div class="form-group col-md-6">
                                <label for="avatar">Email</label>
                                <h3 class="form-control" >${account.email}</h3>
                            </div>
                            <div class="form-group col-md-4">
                                <label for="avatar">Date of Birth</label>
                                <h3 class="form-control">
                                    <fmt:formatDate value="${account.dob}" pattern="dd/MM/yyyy" />
                                </h3>
                            </div>
                            <div class="form-group col-md-4">
                                <label for="avatar">Phone no</label>
                                <h3 class="form-control" >${account.phoneNumber}</h3>
                            </div>
                            <div class="form-group col-md-4">
                                <label for="avatar">Sex</label>
                                <h3 class="form-control" >${account.gender}</h3>
                            </div>
                        </div>

                        <form id="editCvForm" action="my-cv" method="POST" enctype="multipart/form-data" onsubmit="return validateForm('editCvForm')" <c:if test="${cvStatus != null}">style="pointer-events: none; opacity: 0.6;"</c:if>>
        <div class="form-group">
                                <label for="avatar">Profile Picture</label>
                                <input type="file" class="form-control" id="avatar" name="avatar">
                            </div>
                            <div class="form-group">
                                <label for="description">Description</label>
                                <textarea class="form-control" id="description" name="description" rows="3">${cv.description}</textarea>
                                <span id="descriptionError" class="text-danger"></span>
                            </div>
                            <div class="form-group">
                                <label for="price">Price</label>
                                <input type="number" class="form-control" id="price" name="price" value="${cv.price}" />
                                <span id="priceError" class="text-danger"></span>
                            </div>
                            <div class="form-group">
                                <label for="skills">Select Your Skills</label><br>
                                <div class="row">
                                    <c:forEach var="skill" items="${skills}" varStatus="status">
                                        <c:if test="${status.index % 3 == 0 && status.index != 0}">
                                        </div><div class="row"> <!-- Close previous row and start a new one -->
                                        </c:if>
                                        <div class="col-md-4">
                                            <label>
                                                <input type="checkbox" name="skillIds" value="${skill.id}" 
                                                       <c:if test="${mentorSkills.contains(skill.id)}">checked</c:if> /> 
                                                ${skill.name}
                                            </label>
                                        </div>
                                    </c:forEach>
                                </div>
                                <span id="skillError" class="text-danger"></span>
                            </div>
                            <button style="margin-bottom: 20px" type="submit" class="btn btn-primary">Update CV</button>
                        </form>
                    </c:when>
                    <c:otherwise>
                        <h2 style="text-align: center">Create Your CV</h2>
                        <div class="row">
                            <div class="form-group col-md-6">
                                <label for="avatar">Name</label>
                                <h3 class="form-control" >${account.fullname}</h3>
                            </div>

                            <div class="form-group col-md-6">
                                <label for="avatar">Email</label>
                                <h3 class="form-control" >${account.email}</h3>
                            </div>
                            <div class="form-group col-md-4">
                                <label for="avatar">Date of Birth</label>
                                <h3 class="form-control">
                                    <fmt:formatDate value="${account.dob}" pattern="dd/MM/yyyy" />
                                </h3>
                            </div>
                            <div class="form-group col-md-4">
                                <label for="avatar">Phone no</label>
                                <h3 class="form-control" >${account.phoneNumber}</h3>
                            </div>
                            <div class="form-group col-md-4">
                                <label for="avatar">Sex</label>
                                <h3 class="form-control" >${account.gender}</h3>
                            </div>
                        </div>

                        <form id="createCvForm" action="my-cv" method="POST" enctype="multipart/form-data" onsubmit="return validateForm('createCvForm')">
                            <div class="form-group">
                                <label for="avatar">Profile Picture</label>
                                <input type="file" class="form-control" id="avatar" name="avatar">
                            </div>
                            <div class="form-group">
                                <label for="description">Description</label>
                                <textarea class="form-control" id="description" name="description" rows="3"></textarea>
                                <span id="descriptionError" class="text-danger"></span>
                            </div>
                            <div class="form-group">
                                <label for="price">Price</label>
                                <input type="number" class="form-control" id="price" name="price" />
                                <span id="priceError" class="text-danger"></span>
                            </div>
                            <div class="form-group">
                                <label for="skills">Select Your Skills</label><br>
                                <div class="row">
                                    <c:forEach var="skill" items="${skills}" varStatus="status">
                                        <c:if test="${status.index % 3 == 0 && status.index != 0}">
                                        </div><div class="row"> <!-- Close previous row and start a new one -->
                                        </c:if>
                                        <div class="col-md-4">
                                            <label>
                                                <input type="checkbox" name="skillIds" value="${skill.id}" /> 
                                                ${skill.name}
                                            </label>
                                        </div>
                                    </c:forEach>
                                </div>
                                <span id="skillError" class="text-danger"></span>
                            </div>
                            <button style="margin-bottom: 20px" type="submit" class="btn btn-primary">Create CV</button>
                        </form>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</div>
<script>
    function validateForm(formId) {
        let isValid = true;

        const form = document.getElementById(formId);

        // Validate description
        const description = form.querySelector("#description").value.trim();
        const descriptionError = form.querySelector("#descriptionError");
        if (description === "") {
            descriptionError.textContent = "Description cannot be empty.";
            isValid = false;
        } else {
            descriptionError.textContent = "";
        }

        // Validate price
        const price = form.querySelector("#price").value;
        const priceError = form.querySelector("#priceError");
        if (price <= 0 || isNaN(price)) {
            priceError.textContent = "Price must be greater than 0.";
            isValid = false;
        } else {
            priceError.textContent = "";
        }

        // Validate skill selection
        const skills = form.querySelectorAll("input[name='skillIds']");
        let skillSelected = false;
        skills.forEach(skill => {
            if (skill.checked) {
                skillSelected = true;
            }
        });

        const skillError = form.querySelector("#skillError");
        if (!skillSelected) {
            skillError.textContent = "You must select at least one skill.";
            isValid = false;
        } else {
            skillError.textContent = "";
        }

        return isValid;
    }
</script>
<jsp:include page="footer.jsp"></jsp:include>
