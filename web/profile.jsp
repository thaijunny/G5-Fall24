
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="header.jsp"></jsp:include>


    <div class="page-content bg-white">
        <!-- inner page banner -->
        <div class="page-banner ovbl-dark" style="background-image:url(assets/images/banner/banner1.jpg);">
            <div class="container">
                <div class="page-banner-entry">
                    <h1 class="text-white">Profile</h1>
                </div>
            </div>
        </div>
        <!-- Breadcrumb row -->
        <div class="breadcrumb-row">
            <div class="container">
                <ul class="list-inline">
                    <li><a href="#">Home</a></li>
                    <li>Profile</li>
                </ul>
            </div>
        </div>
        <!-- Breadcrumb row END -->
        <!-- inner page banner END -->
        <div class="content-block">
            <!-- About Us -->
            <div class="section-area section-sp1">
                <div class="container">
                    <div class="row">
                        <div class="col-lg-3 col-md-4 col-sm-12 m-b30">
                            <div class="profile-bx text-center">
                                <div class="user-profile-thumb">
                                    <img src="assets/images/profile/pic1.jpg" alt=""/>
                                </div>
                                <div class="profile-info">
                                    <h4>${user.account}</h4>
                                <span>${user.email}</span>
                            </div>

                            <div class="profile-tabnav">
                                <ul class="nav nav-tabs">

                                    <li class="nav-item">
                                        <a class="nav-link active" data-toggle="tab" href="#edit-profile"><i class="ti-pencil-alt"></i>Edit Profile</a>
                                    </li>
                                    <li class="nav-item">
                                        <a class="nav-link" href="change-password"><i class="ti-lock"></i>Change Password</a>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-9 col-md-8 col-sm-12 m-b30">
                        <div class="profile-content-bx">
                            <div class="tab-content">
                                <div class="tab-pane active" id="edit-profile">
                                    <div class="profile-head">
                                        <h3>Edit Profile</h3>
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

                                    <form class="edit-profile" method="POST" action="profile">
                                        <div class="">
                                            <span class="help"></span>
                                            <div class="form-group row">
                                                <label class="col-12 col-sm-3 col-md-3 col-lg-2 col-form-label">Email</label>
                                                <div class="col-12 col-sm-9 col-md-9 col-lg-7">
                                                    <input class="form-control" name="action" type="hidden"  value="update">
                                                    <input class="form-control" name="email" type="text" readonly value="${user.email}">
                                                    <span class="error-message text-danger" id="emailError"></span>
                                                </div>
                                            </div>
                                            <div class="form-group row">
                                                <label class="col-12 col-sm-3 col-md-3 col-lg-2 col-form-label">Account</label>
                                                <div class="col-12 col-sm-9 col-md-9 col-lg-7">
                                                    <input class="form-control" name="account" type="text" value="${user.account}" disabled="">
                                                    <span class="error-message text-danger" id="userNameError"></span>
                                                </div>
                                            </div>
                                            <div class="form-group row">
                                                <label class="col-12 col-sm-3 col-md-3 col-lg-2 col-form-label">Full Name</label>
                                                <div class="col-12 col-sm-9 col-md-9 col-lg-7">
                                                    <input class="form-control" name="fullName" type="text" value="${user.fullname}" required>
                                                    <span class="error-message text-danger" id="fullNameError"></span>
                                                </div>
                                            </div>
                                            <div class="form-group row">
                                                <label class="col-12 col-sm-3 col-md-3 col-lg-2 col-form-label">Date of birth</label>
                                                <div class="col-12 col-sm-9 col-md-9 col-lg-7">
                                                    <input class="form-control" name="dob" type="date" value="${user.dob}" required>
                                                    <span class="error-message text-danger" id="dobError"></span>
                                                </div>
                                            </div>
                                            <div class="form-group row">
                                                <label class="col-6 col-sm-3 col-md-3 col-lg-2 col-form-label">Gender</label>
                                                <div class="col-6 col-sm-9 col-md-9 col-lg-7">
                                                    <select id="gender" class="form-inline" name="gender">
                                                        <option value="">Select Gender</option>
                                                        <option value="MALE" ${user.gender.name() eq 'MALE' ? 'selected' : ''}>Male</option>
                                                        <option value="FEMALE" ${user.gender.name() eq 'FEMALE' ? 'selected' : ''}>Female</option>
                                                        <option value="OTHER" ${user.gender.name() eq 'OTHER' ? 'selected' : ''}>Other</option>
                                                    </select>
                                                    <span class="error-message text-danger" id="schoolError"></span>
                                                </div>
                                            </div>
                                            <div class="form-group row">
                                                <label class="col-6 col-sm-3 col-md-3 col-lg-2 col-form-label">Phone No.</label>
                                                <div class="col-6 col-sm-9 col-md-9 col-lg-7">
                                                    <input class="form-control" name="phone" type="text" value="${user.phoneNumber}" required>
                                                    <span class="error-message text-danger" id="phoneError"></span>
                                                </div>
                                            </div>
                                            <div class="form-group row">
                                                <label class="col-6 col-sm-3 col-md-3 col-lg-2 col-form-label">Address.</label>
                                                <div class="col-6 col-sm-9 col-md-9 col-lg-7">
                                                    <input class="form-control" name="address" type="text" value="${user.address}" required>
                                                    <span class="error-message text-danger" id="addressError"></span>
                                                </div>
                                            </div>
                                            <div class="seperator"></div>
                                            <div class="m-form__seperator m-form__seperator--dashed m-form__seperator--space-2x"></div>

                                        </div>
                                        <div class="">
                                            <div class="">
                                                <div class="row">
                                                    <div class="col-12 col-sm-3 col-md-3 col-lg-2">
                                                    </div>
                                                    <div class="col-12 col-sm-9 col-md-9 col-lg-7">
                                                        <button type="submit" class="btn">Save changes</button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                             
                            </div> 
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- contact area END -->
</div>
<script>
    function validateProfileForm() {
        let isValid = true;

        // Get form fields
        let fullName = document.getElementsByName("fullName")[0].value.trim();
        let dob = document.getElementsByName("dob")[0].value.trim();
        let gender = document.getElementsByName("gender")[0].value;
        let phone = document.getElementsByName("phone")[0].value.trim();
        let address = document.getElementsByName("address")[0].value.trim();

        // Reset error messages
        document.getElementById("fullNameError").textContent = "";
        document.getElementById("dobError").textContent = "";
        document.getElementById("schoolError").textContent = "";
        document.getElementById("phoneError").textContent = "";
        document.getElementById("addressError").textContent = "";

        // Full Name validation
        if (fullName === "") {
            document.getElementById("fullNameError").textContent = "Full Name is required.";
            isValid = false;
        }

        // Date of birth validation
        if (dob === "") {
            document.getElementById("dobError").textContent = "Date of birth is required.";
            isValid = false;
        }

        // Gender validation
        if (gender === "") {
            document.getElementById("schoolError").textContent = "Gender is required.";
            isValid = false;
        }

        // Phone number validation (10 digits and starts with 0)
        const phonePattern = /^0\d{9}$/; // Regex for phone number
        if (phone === "") {
            document.getElementById("phoneError").textContent = "Phone number is required.";
            isValid = false;
        } else if (!phonePattern.test(phone)) {
            document.getElementById("phoneError").textContent = "Phone number must be 10 digits and start with 0.";
            isValid = false;
        }

        // Address validation
        if (address === "") {
            document.getElementById("addressError").textContent = "Address is required.";
            isValid = false;
        }

        return isValid;
    }

  

    // Attach validation to the forms
    document.querySelector('.edit-profile').onsubmit = validateProfileForm;
</script>

<jsp:include page="footer.jsp"></jsp:include>