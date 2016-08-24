<%@ page contentType="text/html; charset=UTF-8" language="java" trimDirectiveWhitespaces="true" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="uengine" uri="http://www.uengine.io/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en"> <!--<![endif]-->
<head>
    <meta charset="utf-8">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>OCE IAM | HOME</title>
    <!-- Meta -->
    <%@include file="../../template/header_js.jsp" %>

    <!-- CSS Page Style -->
    <link rel="stylesheet" href="/resources/assets/css/pages/profile.css">

</head>


<div class="wrapper">
    <%@include file="../../template/header.jsp" %>

    <!--=== Breadcrumbs ===-->
    <div class="breadcrumbs">
        <div class="container">
            <h1 class="pull-left">Eco System</h1>
            <ul class="pull-right breadcrumb">
                <li><a href="/index">HOME</a></li>
                <li class="active">Eco System</li>
            </ul>
        </div>
    </div>
    <!--/breadcrumbs-->

    <%--<%@include file="./banner.jsp" %>--%>

    <!--=== Profile ===-->
    <div class="container content profile">
        <div class="row">
            <!--Left Sidebar-->
            <div class="col-md-3 md-margin-bottom-40">
                <%@include file="../menu.jsp" %>
                <script type="text/javascript">$('[name=list-menu-sysuser]').addClass('active');</script>
            </div>
            <!--End Left Sidebar-->

            <!-- Profile Content -->
            <div class="col-md-9">
                <div class="row">
                    <div class="col-md-12">
                        <form action="/eco/sysuser/create" class="form-horizontal"
                              role="form"
                              id="sysUserForm" method="post">
                            <h4>Create New System User </h4>

                            <c:choose>
                                <c:when test="${duplicate}">
                                    <h4 style="color: #ff0000">System user already exist. </h4>
                                </c:when>
                                <c:when test="${failed}">
                                    <h4 style="color: #ff0000">System user create failed. </h4>
                                </c:when>
                            </c:choose>

                            <div class="form-group">
                                <label class="col-md-2 control-label">Name <span class="color-red">*</span></label>

                                <div class="col-md-6">
                                    <input name="name" type="text" class="form-control" value="" required>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-md-2 control-label">Password <span class="color-red">*</span></label>

                                <div class="col-md-6">
                                    <input name="password" type="text" class="form-control" value="" required>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-md-2 control-label">Default User </label>

                                <div class="col-md-6">
                                    <label class="checkbox"><input type="checkbox" name="defaultUser" value="Y">Default User</label>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-md-2 control-label">Description </label>

                                <div class="col-md-6">
                                    <input name="description" type="text" class="form-control" value="">
                                </div>
                            </div>

                            <div class="form-group">
                                <div class="col-md-offset-2 col-md-10">
                                    <button id="submitBtn" type="submit" class="btn-u btn-u-primary">Create System User
                                    </button>
                                </div>
                            </div>
                        </form>

                    </div>
                </div>
            </div>
            <!-- End Profile Content -->

        </div>
        <!--/end row-->
    </div>
    <!--=== End Profile ===-->

    <%@include file="../../template/footer.jsp" %>
</div>
<!--/wrapper-->

<%@include file="../../template/footer_js.jsp" %>

<script type="text/javascript">
    $(function () {
        var form = $('#sysUserForm');

        //폼 발리데이션
        form.validate({

            focusInvalid: false, // do not focus the last invalid input
            ignore: "",
            rules: {

            },
            messages: {
                name: {
                    required: "<span style='color: red;'>Required filed</span>"
                },
                password: {
                    required: "<span style='color: red;'>Required filed</span>"
                }
            },
            invalidHandler: function () {
                blockStop();
            }
        });
    })
</script>
</html>