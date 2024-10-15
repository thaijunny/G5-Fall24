
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="header.jsp"></jsp:include>

<!---->
    <div class="page-content bg-white">
        <!-- inner page banner -->
        <div class="page-banner ovbl-dark" style="background-image:url(assets/images/banner/banner1.jpg);">
            <div class="container">
                <div class="page-banner-entry">
                    <h1 class="text-white">Change Password</h1>
                </div>
            </div>
        </div>
        <!-- Breadcrumb row -->
        <div class="breadcrumb-row">
            <div class="container">
                <ul class="list-inline">
                    <li><a href="#">Home</a></li>
                    <li>Change Password</li>
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
                                        <a class="nav-link " href="profile"><i class="ti-pencil-alt"></i>Edit Profile</a>
                                    </li>
                                    <li class="nav-item">
                                        <a class="nav-link active" data-toggle="tab" href="#change-password"><i class="ti-lock"></i>Change Password</a>
                                    </li>

                                </ul>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-9 col-md-8 col-sm-12 m-b30">
                        <div class="profile-content-bx">
                            <div class="tab-content">

                                <div class="tab-pane active" id="change-password">
                                    <div class="profile-head">
                                        <h3>Please enter your user name and password</h3>
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
                                    <form class="edit-profile" method="POST" action="change-password" onsubmit="return validatePasswordForm()">
                                        <div class="">
                                            <div class="form-group row">
                                                <label class="col-12 col-sm-4 col-md-4 col-lg-3 col-form-label">Current Password</label>
                                                <div class="col-12 col-sm-8 col-md-8 col-lg-7">
                                                    <input class="form-control" name="oldPass" type="password" value="">
                                                    <input class="form-control" name="action" type="hidden" value="change-pass">
                                                    <span class="error-message text-danger" id="oldPassError"></span> <!-- Error message for old password -->
                                                </div>
                                            </div>
                                            <div class="form-group row">
                                                <label class="col-12 col-sm-4 col-md-4 col-lg-3 col-form-label">New Password</label>
                                                <div class="col-12 col-sm-8 col-md-8 col-lg-7">
                                                    <input class="form-control" name="newPass" type="password" value="">
                                                    <span class="error-message text-danger" id="newPassError"></span> <!-- Error message for new password -->
                                                </div>
                                            </div>
                                            <div class="form-group row">
                                                <label class="col-12 col-sm-4 col-md-4 col-lg-3 col-form-label">Re Type New Password</label>
                                                <div class="col-12 col-sm-8 col-md-8 col-lg-7">
                                                    <input class="form-control" name="confirmPass" type="password" value="">
                                                    <span class="error-message text-danger" id="confirmPassError"></span> <!-- Error message for confirm password -->
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-12 col-sm-4 col-md-4 col-lg-3">
                                            </div>
                                            <div class="col-12 col-sm-8 col-md-8 col-lg-7">
                                                <button type="submit" class="btn">Save changes</button>
                                                <button type="reset" class="btn-secondry">Cancel</button>
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

    function validatePasswordForm() {
        let isValid = true;

        // Get password fields
        let oldPass = document.getElementsByName("oldPass")[0].value.trim();
        let newPass = document.getElementsByName("newPass")[0].value.trim();
        let confirmPass = document.getElementsByName("confirmPass")[0].value.trim();

        // Reset error messages
        document.getElementById("oldPassError").textContent = "";
        document.getElementById("newPassError").textContent = "";
        document.getElementById("confirmPassError").textContent = "";
        
        // Old password validation
        if (oldPass === "") {
            document.getElementById("oldPassError").textContent = "Old Password is required.";
            isValid = false;
        }

        // New password validation
        if (newPass === "") {
            document.getElementById("newPassError").textContent = "New Password is required.";
            isValid = false;
        }

        // Confirm Password validation and Match validation
        if (confirmPass === "") {
            document.getElementById("confirmPassError").textContent = "Re-enter New Password is required.";
            isValid = false;
        }else if (newPass !== confirmPass) {
            document.getElementById("confirmPassError").textContent = "Passwords do not match.";
            isValid = false;
        }

        return isValid;
    }

    // Attach validation to the forms
    document.querySelector('.profile').onsubmit = validateProfileForm;
    document.querySelector('.edit-profile').onsubmit = validatePasswordForm;
</script>

<jsp:include page="footer.jsp"></jsp:include>