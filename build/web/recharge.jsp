<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="header.jsp"></jsp:include>

<div class="page-content bg-white">
    <div class="page-banner ovbl-dark" style="background-image:url(assets/images/banner/banner3.jpg);">
        <div class="container">
            <div class="page-banner-entry">
                <h1 class="text-white">Recharge</h1>
            </div>
        </div>
    </div>

    <div class="breadcrumb-row">
        <div class="container">
            <ul class="list-inline">
                <li><a href="home">Home</a></li>
                <li>Recharge</li>
            </ul>
        </div>
    </div>

    <!-- Notification Messages -->
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

    <!-- Recharge Form and QR Code Display -->
    <div class="container mt-5">
        <div class="row">
            <!-- Form to enter amount and confirm payment (Left Column) -->
            <div class="col-md-6">
                <form action="recharge" method="GET" class="form-horizontal">
                    <div class="form-group">
                        <label for="amount">Enter Amount (VND)</label>
                        <input type="number" id="amount" name="amount" min="10000" max="100000000" step="10000"value="${amount}" class="form-control" placeholder="Enter amount to recharge" required>
                    </div>
                    <button type="submit" class="btn btn-primary">Generate QR Code</button>
                </form>
            </div>

            <!-- QR Code Display (Right Column) -->
            <div class="col-md-6 text-center">
                <c:if test="${not empty param.amount}">
                    <img  src="https://img.vietqr.io/image/BIDV-2153040329-print.png?amount=${amount}&addInfo=HappyPrograming" alt="QR Code" style="max-width: 100%; height: 400px;" />
                    <!-- Trigger Modal -->
                    <button type="button" class="btn btn-success mt-4" data-toggle="modal" data-target="#confirmPaymentModal">
                        Confirm Payment
                    </button>
                </c:if>
            </div>
        </div>
    </div>
</div>

<!-- Bootstrap Modal for Payment Confirmation -->
<div class="modal fade" id="confirmPaymentModal" tabindex="-1" role="dialog" aria-labelledby="confirmPaymentLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="confirmPaymentLabel">Confirm Payment</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                Are you sure you have completed the payment?
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                <!-- Form to confirm the payment -->
                <form action="recharge" method="POST">
                    <input type="hidden" name="amount" value="${amount}">
                    <button type="submit" class="btn btn-success">Confirm</button>
                </form>
            </div>
        </div>
    </div>
</div>

<jsp:include page="footer.jsp"></jsp:include>
