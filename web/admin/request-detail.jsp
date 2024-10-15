<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- CSS -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>

<!-- CSS -->
<link href="https://cdnjs.cloudflare.com/ajax/libs/select2/4.1.0/css/select2.min.css" rel="stylesheet">

<!-- JavaScript -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/select2/4.1.0/js/select2.min.js"></script>
<title>${title}</title>
<jsp:include page="./gui/header.jsp"></jsp:include>
    <main class="ttr-wrapper">
        <div class="container-fluid">
            <div class="db-breadcrumb">
                <h4 class="breadcrumb-title">Request Detail</h4>
                <ul class="db-breadcrumb-list">
                    <li><a href="${pageContext.request.contextPath}/admin/dashboard"><i class="fa fa-home"></i>Home</a></li>
                    <li><a href="${pageContext.request.contextPath}/admin/manage-request"><i class="fa fa-yen"></i>Request Management</a></li>
                <li>Request Detail</li>
            </ul>
        </div>

        <!-- Notification Section -->
        <c:if test="${not empty sessionScope.notification}">
            <div class="alert alert-success alert-dismissible fade show" role="alert" style="text-align: center">
                ${sessionScope.notification}
                <button type="button" class="btn-sm delete" data-dismiss="alert">x</button>
            </div>
            <%
                session.removeAttribute("notification");
            %>
        </c:if>
        <c:if test="${not empty sessionScope.notificationErr}">
            <div class="alert alert-danger alert-dismissible fade show" role="alert" style="text-align: center">
                ${sessionScope.notificationErr}
                <button type="button" class="btn-sm delete" data-dismiss="alert">x</button>
            </div>
            <%
                session.removeAttribute("notificationErr");
            %>
        </c:if>

        <div class="row">
            <div class="col-lg-12 m-b30">
                <div class="widget-box">
                    <div class="widget-inner">
                        <!-- Form 1: Edit Lesson Details -->
                        <form class="edit-profile m-b30" action="${pageContext.request.contextPath}/admin/request-detail" method="post" onsubmit="return validateForm()">
                            <div class="row"> 
                                <div class="form-group col-3">
                                    <label class="col-form-label">Title</label>
                                    <div>
                                        <input class="form-control" readonly disabled value="${request.subject}">
                                    </div>
                                </div>
                                <div class="form-group col-3">
                                    <label class="col-form-label">Mentor</label>
                                    <div>
                                        <input class="form-control" readonly disabled value="${request.mentor.user.fullname}">
                                    </div>
                                </div>
                                <div class="form-group col-3">
                                    <label class="col-form-label">Mentee</label>
                                    <div>
                                        <input class="form-control" readonly disabled value="${request.mentee.user.fullname}">
                                    </div>
                                </div>
                                <div class="form-group col-3">
                                    <label class="col-form-label">Skill</label>
                                    <div>
                                        <input class="form-control" readonly disabled value="${request.skill.name}">
                                    </div>
                                </div>
                                <div class="form-group col-3">
                                    <label class="col-form-label">Created at</label>
                                    <div>
                                        <input class="form-control" readonly disabled value="${request.createDate}">
                                    </div>
                                </div>
                                <div class="form-group col-2">
                                    <label class="col-form-label">Start Date</label>
                                    <div>
                                        <input class="form-control" readonly disabled value="${request.startDate}">
                                    </div>
                                </div>
                                <div class="form-group col-2">
                                    <label class="col-form-label">End Date</label>
                                    <div>
                                        <input class="form-control" readonly disabled value="${request.endDate}">
                                    </div>
                                </div>
                                <div class="form-group col-2">
                                    <label class="col-form-label">Total Price</label>
                                    <div>
                                        <input class="form-control" readonly disabled value="${request.price}">
                                    </div>
                                </div>
                                <div class="form-group col-3">
                                    <label class="col-form-label">Status</label>
                                    <div>
                                        <input class="form-control" readonly disabled value="${request.status}">
                                    </div>
                                </div>
                                <div class="form-group col-md-12">
                                    <label for="schedule">Schedule</label>
                                    <table class="table table-bordered">
                                        <thead>
                                            <tr>
                                                <th>Day of Week</th>
                                                <th>Start Time</th>
                                                <th>End Time</th>
                                            </tr>
                                        </thead>
                                        <tbody id="scheduleTable">
                                            <!-- Hardcoded schedule data -->
                                            <c:forEach items="${slot}" var="l">
                                                <tr>
                                                    <td>${l.mentorSchedule.slot.weekDay}</td>
                                                    <td>${l.mentorSchedule.slot.timeStart}</td>
                                                    <td>${l.mentorSchedule.slot.timeEnd}</td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </form>
                        <!-- End of Form 1 -->
                    </div>
                </div>
            </div>         
        </div>
    </div>
</main>
<jsp:include page="./gui/footer.jsp"></jsp:include>
