<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="header.jsp"></jsp:include>

    <!-- Content -->
    <div class="page-content bg-white">
    <jsp:include page="slider.jsp"></jsp:include>
        <div class="content-block">



            <!-- ALl Skill -->
            <div class="section-area section-sp2 popular-courses-bx">
                <div class="container">
                    <div class="row">
                        <div class="col-md-12 heading-bx left">
                            <h2 class="title-head">Popular <span>Skills</span></h2>
                            <p>It is a long established fact that a reader will be distracted by the readable content of a page</p>
                        </div>
                    </div>
                    <div class="row">
                        <div class="courses-carousel owl-carousel owl-btn-1 col-12 p-lr0">


                            <c:forEach items="${top10Skill}" var="s" >

                            
                                <div class="item" >
                                    <div class="cours-bx">
                                        <div class="action-box" style="max-height: 150px">
                                           
                                            <img src="${pageContext.request.contextPath}/image/${s.image}" alt="alt" />
                                             <a href="skill-detail?id=${s.id}" class="btn">Read More</a>
                                        </div>
                                        <div class="info-bx text-center">
                                            <h5><a href="skill-detail?id=${s.id}">${s.name}</a></h5>
                                            <span>${s.description}</span>
                                        </div>
                                        
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                    
                </div>
            </div>


            <div class="section-area section-sp2 bg-fix ovbl-dark" style="background-image:url(assets/images/background/bg1.jpg);">
                <div class="container">
                    <div class="row">
                        <div class="col-md-12 text-white heading-bx left">
                            <h2 class="title-head text-uppercase">what people <span>say</span></h2>
                            <p>It is a long established fact that a reader will be distracted by the readable content of a page</p>
                        </div>
                    </div>
                    <div class="testimonial-carousel owl-carousel owl-btn-1 col-12 p-lr0">
                        <div class="item">
                            <div class="testimonial-bx">
                                <div class="testimonial-thumb">
                                    <img src="assets/images/testimonials/pic1.jpg" alt="">
                                </div>
                                <div class="testimonial-info">
                                    <h5 class="name">Peter Packer</h5>
                                    <p>-Art Director</p>
                                </div>
                                <div class="testimonial-content">
                                    <p>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type...</p>
                                </div>
                            </div>
                        </div>
                        <div class="item">
                            <div class="testimonial-bx">
                                <div class="testimonial-thumb">
                                    <img src="assets/images/testimonials/pic2.jpg" alt="">
                                </div>
                                <div class="testimonial-info">
                                    <h5 class="name">Peter Packer</h5>
                                    <p>-Art Director</p>
                                </div>
                                <div class="testimonial-content">
                                    <p>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type...</p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- Testimonials END -->

            <!-- Recent News -->
            <div class="section-area section-sp2">
                <div class="container">
                    <div class="row">
                        <div class="col-md-12 heading-bx left">
                            <h2 class="title-head">Recent <span>News</span></h2>
                            <p>It is a long established fact that a reader will be distracted by the readable content of a page</p>
                        </div>
                    </div>
                    <div class="recent-news-carousel owl-carousel owl-btn-1 col-12 p-lr0">
                        <div class="item">
                            <div class="recent-news">
                                <div class="action-box">
                                    <img src="assets/images/blog/latest-blog/pic1.jpg" alt="">
                                </div>
                                <div class="info-bx">
                                    <ul class="media-post">
                                        <li><a href="#"><i class="fa fa-calendar"></i>Jan 02 2019</a></li>
                                        <li><a href="#"><i class="fa fa-user"></i>By William</a></li>
                                    </ul>
                                    <h5 class="post-title"><a href="blog-details.html">This Story Behind Education Will Haunt You Forever.</a></h5>
                                    <p>Knowing that, you’ve optimised your pages countless amount of times, written tons.</p>
                                    <div class="post-extra">
                                        <a href="#" class="btn-link">READ MORE</a>
                                        <a href="#" class="comments-bx"><i class="fa fa-comments-o"></i>20 Comment</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="item">
                            <div class="recent-news">
                                <div class="action-box">
                                    <img src="assets/images/blog/latest-blog/pic2.jpg" alt="">
                                </div>
                                <div class="info-bx">
                                    <ul class="media-post">
                                        <li><a href="#"><i class="fa fa-calendar"></i>Feb 05 2019</a></li>
                                        <li><a href="#"><i class="fa fa-user"></i>By John</a></li>
                                    </ul>
                                    <h5 class="post-title"><a href="blog-details.html">What Will Education Be Like In The Next 50 Years?</a></h5>
                                    <p>As desperate as you are right now, you have done everything you can on your.</p>
                                    <div class="post-extra">
                                        <a href="#" class="btn-link">READ MORE</a>
                                        <a href="#" class="comments-bx"><i class="fa fa-comments-o"></i>14 Comment</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="item">
                            <div class="recent-news">
                                <div class="action-box">
                                    <img src="assets/images/blog/latest-blog/pic3.jpg" alt="">
                                </div>
                                <div class="info-bx">
                                    <ul class="media-post">
                                        <li><a href="#"><i class="fa fa-calendar"></i>April 14 2019</a></li>
                                        <li><a href="#"><i class="fa fa-user"></i>By George</a></li>
                                    </ul>
                                    <h5 class="post-title"><a href="blog-details.html">Master The Skills Of Education And Be.</a></h5>
                                    <p>You will see in the guide all my years of valuable experience together with.</p>
                                    <div class="post-extra">
                                        <a href="#" class="btn-link">READ MORE</a>
                                        <a href="#" class="comments-bx"><i class="fa fa-comments-o"></i>23 Comment</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- Recent News End -->

        </div>
        <!-- contact area END -->
    </div>
    <!-- Content END-->
    <!-- Footer ==== -->
<jsp:include page="footer.jsp"></jsp:include>