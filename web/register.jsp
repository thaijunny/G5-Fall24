<!DOCTYPE html>
<html lang="en">
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <head>
        <!-- META -->
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="keywords" content="" />
        <meta name="author" content="" />
        <meta name="robots" content="" />
        <meta name="description" content="EduChamp : Education HTML Template" />
        <meta property="og:title" content="EduChamp : Education HTML Template" />
        <meta property="og:description" content="EduChamp : Education HTML Template" />
        <meta property="og:image" content="" />
        <meta name="format-detection" content="telephone=no">
        <link rel="icon" href="assets/images/favicon.ico" type="image/x-icon" />
        <link rel="shortcut icon" type="image/x-icon" href="assets/images/favicon.png" />
        <title>Register </title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" type="text/css" href="assets/css/assets.css">
        <link rel="stylesheet" type="text/css" href="assets/css/typography.css">
        <link rel="stylesheet" type="text/css" href="assets/css/shortcodes/shortcodes.css">
        <link rel="stylesheet" type="text/css" href="assets/css/style.css">
        <link class="skin" rel="stylesheet" type="text/css" href="assets/css/color/color-1.css">
    </head>
    <body id="bg">
        <div class="page-wraper">
            <div id="loading-icon-bx"></div>
            <div class="account-form">
                <div class="account-head" style="background-image:url(assets/images/background/bg2.jpg);">
                    <a href="home"><img src="assets/images/logo-white-2.png" alt=""></a>
                </div>
                <div class="account-form-inner">
                    <div class="account-container">
                        <div class="heading-bx left">
                            <h2 class="title-head">Sign Up <span>Now</span></h2>
                            <p>Login Your Account <a href="login">Click here</a></p>
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
                        <form class="contact-bx" action="register" method="post" onsubmit="return validateForm()">
                            <div class="row placeani">
                                <!-- Full Name Field -->
                                <div class="col-lg-6">
                                    <div class="form-group">
                                        <div class="input-group">
                                            <label>Your Full Name</label>
                                            <input id="fullname" name="fullname" type="text" class="form-control" 
                                                   value="${fullname != null ? fullname : ''}">
                                            <small id="fullname-error" style="color:red; display:none;">Full name is required.</small>
                                        </div>
                                    </div>
                                </div>

                                <!-- Email Field -->
                                <div class="col-lg-6">
                                    <div class="form-group">
                                        <div class="input-group">
                                            <label>Your Email Address</label>
                                            <input id="email" name="email" type="email" class="form-control" 
                                                   value="${email != null ? email : ''}">
                                            <small id="email-error" style="color:red; display:none;">Please enter a valid email address.</small>
                                        </div>
                                    </div>
                                </div>

                                <!-- Password Field -->
                                <div class="col-lg-6">
                                    <div class="form-group">
                                        <div class="input-group">
                                            <label>Your Password</label>
                                            <input id="password" name="password" type="password" class="form-control" 
                                                   value="${password != null ? password : ''}">
                                        </div>
                                    </div>
                                </div>

                                <!-- Confirm Password Field -->
                                <div class="col-lg-6">
                                    <div class="form-group">
                                        <div class="input-group">
                                            <label>Confirm Password</label>
                                            <input id="confirmPassword" name="confirmPassword" type="password" class="form-control" 
                                                   value="${confirmPassword != null ? confirmPassword : ''}">
                                            <small id="password-error" style="color:red; display:none;">Passwords do not match.</small>
                                        </div>
                                    </div>
                                </div>

                                <!-- Role Dropdown -->
                                <div class="col-lg-4">
                                    <div class="form-group">
                                        <div class="input-group">
                                            <label>Role</label>
                                            <select id="role" class="form-inline" name="role">
                                                <option value="">Select Role</option>
                                                <option value="MENTOR" ${role == 'MENTOR' ? 'selected' : ''}>Mentor</option>
                                                <option value="MENTEE" ${role == 'MENTEE' ? 'selected' : ''}>Mentee</option>
                                            </select>
                                            <small id="role-error" style="color:red; display:none;">Please select a role.</small>
                                        </div>
                                    </div>
                                </div>

                                <!-- Phone Number Field -->
                                <div class="col-lg-4">
                                    <div class="form-group">
                                        <div class="input-group">
                                            <label>Phone number</label>
                                            <input id="phone" name="phone" type="text" class="form-control" 
                                                   value="${phone != null ? phone : ''}">
                                            <small id="phone-error" style="color:red; display:none;">Phone number must be 10 digits and start with 0.</small>
                                        </div>
                                    </div>
                                </div>

                                <!-- Gender Dropdown -->
                                <div class="col-lg-4">
                                    <div class="form-group">
                                        <div class="input-group">
                                            <label>Gender</label>
                                            <select id="gender" class="form-inline" name="gender">
                                                <option value="">Select Gender</option>
                                                <option value="MALE" ${gender == 'MALE' ? 'selected' : ''}>Male</option>
                                                <option value="FEMALE" ${gender == 'FEMALE' ? 'selected' : ''}>Female</option>
                                                <option value="OTHER" ${gender == 'OTHER' ? 'selected' : ''}>Other</option>
                                            </select>
                                            <small id="gender-error" style="color:red; display:none;">Please select a gender.</small>
                                        </div>
                                    </div>
                                </div>

                                <!-- Address Field -->
                                <div class="col-lg-12">
                                    <div class="form-group">
                                        <div class="input-group">
                                            <label>Address</label>
                                            <input id="address" name="address" type="text" class="form-control"
                                                   value="${address != null ? address : ''}">
                                            <small id="address-error" style="color:red; display:none;">Address is required.</small>
                                        </div>
                                    </div>
                                </div>

                                <!-- Submit Button -->
                                <div class="col-lg-12 m-b30">
                                    <button name="submit" type="submit" value="Submit" class="btn button-md">Sign Up</button>
                                </div>
                            </div>
                        </form>


                    </div>
                </div>
            </div>
        </div>
        <script>
            function validateForm() {
                var isValid = true;

                // Validate Full Name
                var fullname = document.getElementById('fullname').value;
                var fullnamePattern = /^[A-Za-z\s]+$/;
                if (!fullname || !fullnamePattern.test(fullname)) {
                    document.getElementById('fullname-error').style.display = 'block';
                    isValid = false;
                } else {
                    document.getElementById('fullname-error').style.display = 'none';
                }

                // Validate Email
                var email = document.getElementById('email').value;
                var emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
                if (!emailPattern.test(email)) {
                    document.getElementById('email-error').style.display = 'block';
                    isValid = false;
                } else {
                    document.getElementById('email-error').style.display = 'none';
                }

                // Validate Phone Number
                var phone = document.getElementById('phone').value;
                var phonePattern = /^0\d{9}$/;
                if (!phonePattern.test(phone)) {
                    document.getElementById('phone-error').style.display = 'block';
                    isValid = false;
                } else {
                    document.getElementById('phone-error').style.display = 'none';
                }

                // Validate Password Match
                var password = document.getElementById('password').value;
                var confirmPassword = document.getElementById('confirmPassword').value;
                if (password !== confirmPassword) {
                    document.getElementById('password-error').style.display = 'block';
                    isValid = false;
                } else {
                    document.getElementById('password-error').style.display = 'none';
                }

                // Validate Role
                var role = document.getElementById('role').value;
                if (!role) {
                    document.getElementById('role-error').style.display = 'block';
                    isValid = false;
                } else {
                    document.getElementById('role-error').style.display = 'none';
                }

                // Validate Gender
                var gender = document.getElementById('gender').value;
                if (!gender) {
                    document.getElementById('gender-error').style.display = 'block';
                    isValid = false;
                } else {
                    document.getElementById('gender-error').style.display = 'none';
                }

                // Validate Address
                var address = document.getElementById('address').value;
                if (!address) {
                    document.getElementById('address-error').style.display = 'block';
                    isValid = false;
                } else {
                    document.getElementById('address-error').style.display = 'none';
                }

                return isValid;
            }

        </script>
        <script src="assets/js/jquery.min.js"></script>
        <script src="assets/vendors/bootstrap/js/popper.min.js"></script>
        <script src="assets/vendors/bootstrap/js/bootstrap.min.js"></script>
        <script src="assets/vendors/bootstrap-select/bootstrap-select.min.js"></script>
        <script src="assets/vendors/bootstrap-touchspin/jquery.bootstrap-touchspin.js"></script>
        <script src="assets/vendors/magnific-popup/magnific-popup.js"></script>
        <script src="assets/vendors/counter/waypoints-min.js"></script>
        <script src="assets/vendors/counter/counterup.min.js"></script>
        <script src="assets/vendors/imagesloaded/imagesloaded.js"></script>
        <script src="assets/vendors/masonry/masonry.js"></script>
        <script src="assets/vendors/masonry/filter.js"></script>
        <script src="assets/vendors/owl-carousel/owl.carousel.js"></script>
        <script src="assets/js/functions.js"></script>
        <script src="assets/js/contact.js"></script>
        <script src='assets/vendors/switcher/switcher.js'></script>
    </body>
</html>
