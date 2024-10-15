<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="header.jsp"></jsp:include>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="page-content bg-white">
    <div class="page-banner ovbl-dark" style="background-image:url(assets/images/banner/banner3.jpg);">
        <div class="container">
            <div class="page-banner-entry">
                <h1 class="text-white">CV Detail</h1>
            </div>
        </div>
    </div>

    <div class="breadcrumb-row">
        <div class="container">
            <ul class="list-inline">
                <li><a href="home">Home</a></li>
                <li>CV Detail</li>
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

                <!-- CV Paper Structure -->
                <div class="cv-paper" style="margin-top: 20px;">
                    <!-- CV Header -->
                    <div class="cv-header" style="text-align: center; margin-bottom: 30px;">
                        <img src="image/${mentor.avatar}" alt="Avatar" style="border-radius: 50%; width: 150px; height: 150px; margin-bottom: 20px;">
                        <h2>${mentor.user.fullname}</h2>
                        <p>${mentor.user.email} | ${mentor.user.phoneNumber}</p>
                    </div>

                    <!-- CV Body -->
                    <div class="cv-body">
                        <!-- Personal Information Section -->
                        <div class="cv-section">
                            <h3>Personal Information</h3>
                            <div class="row">
                                <div class="col-md-6">
                                    <label><strong>Date of Birth:</strong></label>
                                    <p><fmt:formatDate value="${mentor.user.dob}" pattern="dd/MM/yyyy" /></p>
                                </div>
                                <div class="col-md-6">
                                    <label><strong>Gender:</strong></label>
                                    <p>${mentor.user.gender}</p>
                                </div>
                            </div>
                        </div>

                        <!-- Description Section -->
                        <div class="cv-section">
                            <h3>About Me</h3>
                            <p>${cv.description}</p>
                        </div>

                        <!-- Skills Section -->
                        <div class="cv-section">
                            <h3>Skills</h3>
                            <ul>
                                <c:forEach var="skill" items="${skills}">
                                    <c:if test="${mentorSkills.contains(skill.id)}">
                                        <li>${skill.name}</li>
                                        </c:if>
                                    </c:forEach>
                            </ul>
                        </div>

                        <!-- Pricing Section -->
                        <div class="cv-section">
                            <h3>Service Price</h3>
                            <p>${cv.price} USD</p>
                        </div>

                        <button onclick="window.history.back()" class="btn-info">
                            <li class="fa fa-arrow-left"></li>
                        </button>


                    </div>
                </div>

            </div>
        </div>
    </div>
</div>

<jsp:include page="footer.jsp"></jsp:include>

<!-- Add CSS Styling for the CV Paper Effect -->
<style>
    .cv-paper {
        background-color: white;
        border: 1px solid #e0e0e0;
        border-radius: 10px;
        padding: 30px;
        max-width: 800px;
        margin: 0 auto;
        box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1); /* Paper shadow */
    }

    .cv-section {
        margin-bottom: 20px;
    }

    .cv-section h3 {
        font-size: 18px;
        color: #333;
        margin-bottom: 10px;
        text-transform: uppercase;
        font-weight: 700;
        border-bottom: 2px solid #ddd;
        padding-bottom: 5px;
    }

    .cv-section p, .cv-section ul {
        font-size: 16px;
        color: #555;
    }

    .cv-section ul {
        list-style-type: none;
        padding-left: 0;
    }

    .cv-section ul li {
        margin-bottom: 5px;
    }

    .cv-body {
        margin-top: 20px;
    }
</style>
