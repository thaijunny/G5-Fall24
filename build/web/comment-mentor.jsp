<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="header.jsp"></jsp:include>
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
                    <li>Comment Mentor</li>
                </ul>
            </div>
        </div>
        <div class="container" style="margin-top: 50px;">
            <div class="card shadow-sm mb-5 bg-white rounded">
                <div class="card-header text-center bg-primary text-white">
                    <h2>Mentor Profile and Comments</h2>
                </div>

                <!-- Mentor Details Section -->
                <div class="card-body">
                    <div class="row">
                        <div class="col-md-4 text-center">
                            <img src="image/${mentor.avatar}" class="rounded-circle img-thumbnail" alt="Avatar" style="width: 150px; height: 150px;">
                    </div>
                    <div class="col-md-8">
                        <h4><strong>${mentor.user.fullname}</strong></h4>
                        <p><strong>Email:</strong> ${mentor.user.email}</p>
                        <p><strong>Account:</strong> ${mentor.user.account}</p>
                    </div>
                </div>
                <form action="comment-mentor" method="POST" style="margin-top: 30px;">
                    <input type="hidden" name="mentorId" value="${mentor.id}" />

                    <!-- Star Rating -->
                    <div class="form-group">
                        <label for="rate"><strong>Rate the Mentor:</strong></label>
                        <div class="star-rating">
                            <c:forEach var="i" begin="1" end="5">
                                <input type="radio" name="rate" value="${i}" id="rate-${i}" required />
                                <label for="rate-${i}" class="star">&#9733;</label>
                            </c:forEach>
                        </div>
                    </div>

                    <!-- Comment Box -->
                    <div class="form-group">
                        <label for="comment"><strong>Your Comment:</strong></label>
                        <textarea name="comment" id="comment" class="form-control" rows="4" placeholder="Write your comment here..." required></textarea>
                    </div>

                    <!-- Submit Button -->
                    <div class="form-group text-center">
                        <button type="submit" class="btn btn-success">Submit Comment</button>
                    </div>
                </form>
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
                <!-- Comment List -->
                <div class="cv-section" style="margin-top: 40px;">
                    <h3>Comments</h3>
                    <c:forEach var="rating" items="${ratings}">
                        <div class="comment-item mb-4">
                            <div class="comment-header d-flex justify-content-between">
                                <strong>${rating.mentee.user.fullname}</strong>
                                <div class="rating">
                                    <c:forEach var="i" begin="1" end="5">
                                        <span class="star ${i <= rating.rate ? 'filled' : ''}">&#9733;</span> <!-- Filled Star -->
                                    </c:forEach>
                                    <span>(${rating.rate} / 5)</span> <!-- Numeric value of the rating -->
                                </div>
                            </div>
                            <p>${rating.comment}</p>
                            <p class="text-muted">
                                <fmt:formatDate value="${rating.created}" pattern="dd/MM/yyyy HH:mm:ss" />
                            </p>
                        </div>
                    </c:forEach>

                    <!-- If no comments are available -->
                    <c:if test="${empty ratings}">
                        <p>No comments available.</p>
                    </c:if>
                </div>

                <!-- Add Rating and Comment Form -->

            </div>
        </div>

    </div>
</div>
<jsp:include page="footer.jsp"></jsp:include>

<!-- Add custom CSS for stars -->
<style>
    .star-rating {
        display: inline-block;
        font-size: 1.5em;
        direction: rtl;
        unicode-bidi: bidi-override;
    }

    .star-rating input[type="radio"] {
        display: none;
    }

    .star-rating label {
        color: #ccc;
        cursor: pointer;
    }

    .star-rating label:hover,
    .star-rating label:hover ~ label,
    .star-rating input[type="radio"]:checked ~ label {
        color: #ffc107; /* Star color when active */
    }

    .card {
        max-width: 800px;
        margin: 0 auto;
    }

    .comment-item {
        background-color: #f8f9fa;
        padding: 10px;
        border-radius: 8px;
        border: 1px solid #e0e0e0;
    }

    .comment-header {
        font-size: 16px;
        font-weight: bold;
    }

    .text-muted {
        font-size: 12px;
    }
    .rating {
        font-size: 18px; /* Adjust the size of the stars */
        color: gray; /* Default color for empty stars */
    }

    .star {
        display: inline-block;
    }

    .filled {
        color: gold; /* Color for filled stars */
    }

</style>
