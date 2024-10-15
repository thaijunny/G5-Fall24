<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="header.jsp"></jsp:include>
<title>${title}</title>

<div class="page-content bg-white">
    <div class="page-banner ovbl-dark" style="background-image:url(assets/images/banner/banner3.jpg);">
        <div class="container">
            <div class="page-banner-entry">
                <h1 class="text-white">Create Request</h1>
            </div>
        </div>
    </div>

    <div class="breadcrumb-row">
        <div class="container">
            <ul class="list-inline">
                <li><a href="home">Home</a></li>
                <li>Create Request</li>
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

    <!-- Form Start -->
    <div class="container">
        <form action="create-request" method="POST" class="form-horizontal" onsubmit="return validateForm()">
            <div class="row">
                <!-- Select Skill (Dropdown) -->
                <div class="form-group col-md-6">
                    <label for="skill">Select Skill</label>
                    <select id="skill" name="skillId" class="form-control"  onchange="updatePage()">
                        <option value="">Select Skill</option>
                        <c:forEach var="skill" items="${skills}">
                            <option value="${skill.id}" 
                                    <c:if test="${selectedSkill != null && selectedSkill.id == skill.id}">
                                        selected
                                    </c:if>
                                    >${skill.name}</option>
                        </c:forEach>
                    </select>
                    <span id="skillError" class="text-danger"></span> <!-- Error message for skill -->

                </div>

                <!-- Select Mentor (Dropdown) -->
                <div class="form-group col-md-6">
                    <label for="mentor">Select Mentor</label> <c:if test="${not empty price}">- <span style="font-weight: bold; color: red">Price: ${price} VND</span></c:if>
                        <select id="mentor" name="mentorId" class="form-control"  onchange="updatePage()">
                            <option value="">Select Mentor</option>
                        <c:forEach var="mentor" items="${mentors}">
                            <option value="${mentor.id}" 
                                    <c:if test="${selectedMentor != null && selectedMentor.id == mentor.id}">
                                        selected
                                    </c:if>
                                    >${mentor.user.fullname}</option>
                        </c:forEach>
                    </select>
                    <span id="mentorError" class="text-danger"></span> <!-- Error message for mentor -->

                </div>
            </div>
            <div class="row">
                <!-- Title/Subject -->
                <div class="form-group col-md-6">
                    <label for="subject">Request Title (Subject)</label>
                    <input type="text" id="subject" name="subject" class="form-control" placeholder="Enter the title of your request">
                    <span id="titleError" class="text-danger"></span> <!-- Error message for title -->
                </div>

                <!-- Deadline Date -->
                <div class="form-group col-md-6">
                    <label for="deadlineDate">Deadline Date</label>
                    <input type="date" id="deadlineDate" name="deadlineDate" class="form-control">
                    <span id="deadlineDateError" class="text-danger"></span> <!-- Error message for date -->
                </div>

                <!-- Start Date -->
                <div class="form-group col-md-6">
                    <label for="startDate">Start Date</label>
                    <input type="date" id="startDate" name="startDate" class="form-control">
                    <span id="startDateError" class="text-danger"></span> <!-- Error message for date -->
                </div>

                <!-- End Date -->
                <div class="form-group col-md-6">
                    <label for="endDate">End Date</label>
                    <input type="date" id="endDate" name="endDate" class="form-control">
                    <input type="hidden" id="basePrice" value="${price}">
                    <span id="endDateError" class="text-danger"></span> <!-- Error message for date -->
                </div>

                <!-- Content of Request -->
                <div class="form-group col-md-12">
                    <label for="content">Request Content</label>
                    <textarea id="content" name="content" class="form-control" rows="4" placeholder="Describe your request in detail"></textarea>
                    <span id="contentError" class="text-danger"></span> <!-- Error message for content -->
                </div>

                <!-- Schedule Table -->
                <div class="form-group col-md-12">
                    <label for="schedule">Available Time Slots</label>
                    <table class="table table-bordered">
                        <thead>
                            <tr>
                                <th>Day of Week</th>
                                <th>Start Time</th>
                                <th>End Time</th>
                                <th>Status</th>
                                <th>Select</th>
                            </tr>
                        </thead>
                        <tbody id="scheduleTable">
                            <!-- Hardcoded schedule data -->
                            <c:forEach items="${listMentorSchedules}" var="l">
                                <tr>
                                    <td>${l.slot.weekDay}</td>
                                    <td>${l.slot.timeStart}</td>
                                    <td>${l.slot.timeEnd}</td>
                                    <td>${l.sheduleStatus}</td>
                                    <td>
                                        <input class="slot-checkbox" type="checkbox" name="slotIds" value="${l.id}"  data-day="${l.slot.weekDay}" data-start="${l.slot.timeStart}" data-end="${l.slot.timeEnd}"
                                               <c:if test="${l.sheduleStatus.name() == 'Not_avaliable'}">disabled</c:if>>
                                        </td>
                                    </tr>
                            </c:forEach>


                        </tbody>
                    </table>
                </div>

                <!-- Price Calculation -->
                <div class="form-group col-md-12">
                    <label for="totalPrice">Total Price</label>
                    <input type="text" id="totalPrice" name="totalPrice" class="form-control" readonly>
                </div>
            </div>

            <script>
                const pricePerHour = parseFloat(document.getElementById('basePrice').value);
                const checkboxes = document.querySelectorAll('.slot-checkbox');
                const totalPriceField = document.getElementById('totalPrice');
                const startDateField = document.getElementById('startDate');
                const endDateField = document.getElementById('endDate');

                // Function to calculate total price based on selected slots and days between startDate and endDate
                checkboxes.forEach(checkbox => {
                    checkbox.addEventListener('change', calculateTotalPrice);
                });

                function calculateTotalPrice() {
                    let totalPrice = 0;
                    let totalHours = 0;

                    // Get start and end dates
                    const startDate = new Date(startDateField.value);
                    const endDate = new Date(endDateField.value);

                    checkboxes.forEach(checkbox => {
                        if (checkbox.checked) {
                            const dayOfWeek = checkbox.getAttribute('data-day');
                            const startTime = checkbox.getAttribute('data-start');
                            const endTime = checkbox.getAttribute('data-end');
                            const hours = calculateHours(startTime, endTime);

                            // Get number of occurrences of this day of the week between start and end date
                            const occurrences = countDayOccurrences(dayOfWeek, startDate, endDate);

                            totalHours += hours * occurrences; // Multiply hours by the number of occurrences
                            totalPrice += hours * pricePerHour * occurrences;
                        }
                    });

                    // Update the total price field
                    totalPriceField.value = totalPrice.toFixed(0); // Show price with 2 decimal places
                    console.log(`Total Price: ${totalPrice}, Total Hours: ${totalHours}`);
                }

                // Function to calculate the difference in hours between start and end times
                function calculateHours(start, end) {
                    const [startHours, startMinutes] = start.split(':').map(Number);
                    const [endHours, endMinutes] = end.split(':').map(Number);

                    const startTime = new Date(0, 0, 0, startHours, startMinutes, 0);
                    const endTime = new Date(0, 0, 0, endHours, endMinutes, 0);

                    const diff = endTime - startTime;
                    return diff / (1000 * 60 * 60); // Convert milliseconds to hours
                }

                // Function to count occurrences of a specific day of the week between two dates
                function countDayOccurrences(dayOfWeek, startDate, endDate) {
                    const dayMap = {
                        'Monday': 1,
                        'Tuesday': 2,
                        'Wednesday': 3,
                        'Thursday': 4,
                        'Friday': 5,
                        'Saturday': 6,
                        'Sunday': 0
                    };

                    const targetDay = dayMap[dayOfWeek];
                    let count = 0;

                    // Loop through all dates between startDate and endDate
                    for (let currentDate = new Date(startDate); currentDate <= endDate; currentDate.setDate(currentDate.getDate() + 1)) {
                        if (currentDate.getDay() === targetDay) {
                            count++;
                        }
                    }

                    return count; // Return the total number of occurrences
                }
            </script>


            <!-- Status (Hidden field, default Open) -->
            <input type="hidden" name="status" value="Open">

            <div class="row">
                <!-- Submit Button -->
                <div class="form-group col-md-12 text-center">
                    <button type="submit" class="btn btn-primary">Create Request</button>
                </div>
            </div>
        </form>
    </div>
    <!-- Form End -->
</div>
<style>
    .not-available {
        background-color: #f8d7da;
        pointer-events: none;
    }
</style>

<script>
    // Client-side form validation

    function validateForm() {
        let isValid = true;

        // Skill validation
        const skill = document.getElementById('skill').value;
        const skillError = document.getElementById('skillError');
        if (!skill) {
            skillError.textContent = "Please select a skill.";
            isValid = false;
        } else {
            skillError.textContent = "";
        }

        // Mentor validation
        const mentor = document.getElementById('mentor').value;
        const mentorError = document.getElementById('mentorError');
        if (!mentor) {
            mentorError.textContent = "Please select a mentor.";
            isValid = false;
        } else {
            mentorError.textContent = "";
        }

        // Title validation
        const title = document.getElementById('subject').value.trim();
        const titleError = document.getElementById('titleError');
        if (!title) {
            titleError.textContent = "Please enter a title for your request.";
            isValid = false;
        } else {
            titleError.textContent = "";
        }

        // Deadline date validation
        const deadlineDate = document.getElementById('deadlineDate').value;
        const deadlineDateError = document.getElementById('deadlineDateError');
        const today = new Date().toISOString().split('T')[0]; // Get today's date in 'yyyy-mm-dd'
        if (!deadlineDate || deadlineDate < today) {
            deadlineDateError.textContent = "Please select a valid deadline date in the future.";
            isValid = false;
        } else {
            deadlineDateError.textContent = "";
        }

        // Deadline time validation
        const deadlineHour = document.getElementById('deadlineHour').value;
        const deadlineHourError = document.getElementById('deadlineHourError');
        if (!deadlineHour) {
            deadlineHourError.textContent = "Please select a deadline hour.";
            isValid = false;
        } else {
            deadlineHourError.textContent = "";
        }

        // Content validation
        const content = document.getElementById('content').value.trim();
        const contentError = document.getElementById('contentError');
        if (!content) {
            contentError.textContent = "Please describe your request.";
            isValid = false;
        } else {
            contentError.textContent = "";
        }

        return isValid;
    }

    function updatePage() {
        const selectedSkill = document.getElementById('skill').value;
        const selectedMentor = document.getElementById('mentor').value;

        // Initialize an empty array for query parameters
        let queryParams = [];

        // Only add the sid parameter if a skill is selected
        if (selectedSkill) {
            queryParams.push('sid=' + selectedSkill);
        }

        // Only add the mid parameter if a mentor is selected
        if (selectedMentor) {
            queryParams.push('mid=' + selectedMentor);
        }

        // Construct the URL with only the relevant query parameters
        let queryString = queryParams.length > 0 ? '?' + queryParams.join('&') : '';

        // Redirect to the new URL
        window.location.href = 'create-request' + queryString;
    }

</script>

<jsp:include page="footer.jsp"></jsp:include>
