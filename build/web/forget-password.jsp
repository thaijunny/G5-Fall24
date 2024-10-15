<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<!---->
    <head>

        <!-- META ============================================= -->
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="keywords" content="" />
        <meta name="author" content="" />
        <meta name="robots" content="" />

        <!-- DESCRIPTION -->
        <meta name="description" content="EduChamp : Education HTML Template" />

        <!-- OG -->
        <meta property="og:title" content="EduChamp : Education HTML Template" />
        <meta property="og:description" content="EduChamp : Education HTML Template" />
        <meta property="og:image" content="" />
        <meta name="format-detection" content="telephone=no">

        <!-- FAVICONS ICON ============================================= -->
        <link rel="icon" href="assets/images/favicon.ico" type="image/x-icon" />
        <link rel="shortcut icon" type="image/x-icon" href="assets/images/favicon.png" />

        <!-- PAGE TITLE HERE ============================================= -->
        <title>EduChamp : Education HTML Template </title>

        <!-- MOBILE SPECIFIC ============================================= -->
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <!--[if lt IE 9]>
        <script src="assets/js/html5shiv.min.js"></script>
        <script src="assets/js/respond.min.js"></script>
        <![endif]-->

        <!-- All PLUGINS CSS ============================================= -->
        <link rel="stylesheet" type="text/css" href="assets/css/assets.css">

        <!-- TYPOGRAPHY ============================================= -->
        <link rel="stylesheet" type="text/css" href="assets/css/typography.css">

        <!-- SHORTCODES ============================================= -->
        <link rel="stylesheet" type="text/css" href="assets/css/shortcodes/shortcodes.css">

        <!-- STYLESHEETS ============================================= -->
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
                            <h2 class="title-head">If you've forgotten your password, enter your account and email</h2>
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
                        <c:if test="${not empty sessionScope.notification}">
                            <div class="alert alert-success alert-dismissible fade show" role="alert" style="text-align: center">
                                ${sessionScope.notification}
                                <button type="button" class="btn-sm delete" data-dismiss="alert">x</button>
                            </div>
                            <%
                                session.removeAttribute("notification");
                            %>
                        </c:if>
                        <form class="contact-bx" action="forget-password" method="post" onsubmit="return validateForm()">
                            <div class="row placeani">

                                <!-- Input Account -->
                                <div class="col-lg-12">
                                    <div class="form-group">
                                        <div class="input-group">
                                            <label>Your Account</label>
                                            <input id="account" name="account" type="text" class="form-control" >
                                            <small id="account-error" style="color:red; display:none;">Please enter your account.</small>
                                        </div>
                                    </div>
                                </div>

                                <!-- Input Email -->
                                <div class="col-lg-12">
                                    <div class="form-group">
                                        <div class="input-group">
                                            <label>Your Email Address</label>
                                            <input id="email" name="email" type="email" class="form-control" >
                                            <small id="email-error" style="color:red; display:none;">Please enter a valid email address.</small>
                                        </div>
                                    </div>
                                </div>

                                <div class="col-lg-12 m-b30">
                                    <button name="submit" type="submit" value="Submit" class="btn button-md">Submit</button>
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

                // Validate account field
                var account = document.getElementById('account').value.trim();
                if (account === '') {
                    document.getElementById('account-error').style.display = 'block';
                    isValid = false;
                } else {
                    document.getElementById('account-error').style.display = 'none';
                }

                // Validate email field
                var email = document.getElementById('email').value.trim();
                var emailPattern = /^[^\s@]+@gmail.com/;
                if (!emailPattern.test(email)) {
                    document.getElementById('email-error').style.display = 'block';
                    isValid = false;
                } else {
                    document.getElementById('email-error').style.display = 'none';
                }

                return isValid;
            }
        </script>
        <!-- External JavaScripts -->
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
