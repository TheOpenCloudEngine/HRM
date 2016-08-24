<%@ page contentType="text/html; charset=UTF-8" language="java" trimDirectiveWhitespaces="true" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="uengine" uri="http://www.uengine.io/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if !IE]><!-->
<html xmlns="http://www.w3.org/1999/xhtml"
      xmlns:th="http://www.thymeleaf.org"
      xmlns:sec="http://www.thymeleaf.org"
      lang="en">
<!--<![endif]-->
<head>
    <meta charset="utf-8">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>OCE IAM | HOME</title>

    <%@include file="template/header_js.jsp" %>


    <!-- CSS Page Style -->
    <link rel="stylesheet" href="/resources/assets/css/pages/page_contact.css">
</head>

<!-- body classes:
        "boxed": boxed layout mode e.g. <body class="boxed">
        "pattern-1 ... pattern-9": background patterns for boxed layout mode e.g. <body class="boxed pattern-1">
-->

<div class="wrapper">
    <%@include file="template/header.jsp" %>

    <!--=== Breadcrumbs ===-->
    <div class="breadcrumbs">
        <div class="container">
            <h1 class="pull-left"><uengine:message code="menu.contact"/></h1>
            <ul class="pull-right breadcrumb">
                <li><a href="index.html"><uengine:message code="menu.home"/></a></li>
                <li class="active"><uengine:message code="menu.contact"/></li>
            </ul>
        </div>
    </div>
    <!--/breadcrumbs-->

    <!--=== Content Part ===-->
    <div class="container content">
        <div class="row margin-bottom-30">
            <div class="col-md-9 mb-margin-bottom-30">
                <!-- Google Map -->
                <div id="map" class="map map-box map-box-space margin-bottom-40"></div>
                <!-- End Google Map -->

                <p><uengine:message code="text.contactus.navi"/></p><br/>

                <form:form commandName="contact" cssClass="sky-form contact-style">
                    <fieldset class="no-padding">
                        <label><uengine:message code="form.contactus.name"/> <span class="color-red">*</span></label>

                        <div class="row sky-space-20">
                            <div class="col-md-7 col-md-offset-0">
                                <div>
                                    <form:input path="name" cssClass="form-control"/>
                                </div>
                            </div>
                        </div>

                        <label><uengine:message code="form.contactus.email"/> <span class="color-red">*</span></label>

                        <div class="row sky-space-20">
                            <div class="col-md-7 col-md-offset-0">
                                <div>
                                    <form:input path="email" cssClass="form-control"/>
                                </div>
                            </div>
                        </div>

                        <label><uengine:message code="form.contactus.telephone"/> <span
                                class="color-red">*</span></label>

                        <div class="row sky-space-20">
                            <div class="col-md-7 col-md-offset-0">
                                <div>
                                    <form:input path="telephone" cssClass="form-control"/>
                                </div>
                            </div>
                        </div>

                        <label><uengine:message code="form.contactus.subject"/> <span
                                class="color-red">*</span></label>

                        <div class="row sky-space-20">
                            <div class="col-md-7 col-md-offset-0">
                                <div>
                                    <form:input path="subject" cssClass="form-control"/>
                                </div>
                            </div>
                        </div>

                        <label><uengine:message code="form.contactus.message"/> <span
                                class="color-red">*</span></label>

                        <div class="row sky-space-20">
                            <div class="col-md-11 col-md-offset-0">
                                <div>
                                    <form:textarea rows="8" path="message" cssClass="form-control"/>
                                </div>
                            </div>
                        </div>

                        <p>
                            <button class="btn-u"><uengine:message
                                    code="button.contactus.send"/></button>
                        </p>
                    </fieldset>
                </form:form>
            </div>
            <!--/col-md-9-->

            <div class="col-md-3">
                <!-- Contacts -->
                <div class="headline"><h2><uengine:message code="label.contactus.contact"/></h2></div>
                <ul class="list-unstyled who margin-bottom-30">
                    <li><a href="#"><i class="fa fa-home"></i><uengine:message code="address"/></a></li>
                    <li><a href="#"><i class="fa fa-envelope"></i><uengine:message code="email"/></a></li>
                    <li><a href="#"><i class="fa fa-phone"></i><uengine:message code="phone"/></a></li>
                    <li><a href="#"><i class="fa fa-fax"></i><uengine:message code="fax"/></a></li>
                    <li><a href="#"><i class="fa fa-globe"></i><uengine:message code="homepage"/></a></li>
                </ul>

                <!-- Business Hours -->
                <div class="headline"><h2><uengine:message code="label.working.0"/></h2></div>
                <ul class="list-unstyled margin-bottom-30">
                    <li><strong><uengine:message code="label.contactus.timezone"/>:</strong> <uengine:message
                            code="timezone"/></li>
                    <li><strong><uengine:message code="label.working.1"/>:</strong> <uengine:message
                            code="label.working.4"/></li>
                    <li><strong><uengine:message code="label.working.2"/>:</strong> <uengine:message
                            code="label.working.5"/></li>
                    <li><strong><uengine:message code="label.working.3"/>:</strong> <uengine:message
                            code="label.working.5"/></li>
                </ul>

            </div>
            <!--/col-md-3-->
        </div>
        <!--/row-->
    </div>
    <!--/container-->
    <!--=== End Content Part ===-->

    <%@include file="template/footer.jsp" %>
</div>
<!--/wrapper-->

<%@include file="template/footer_js.jsp" %>

<%@include file="template/footer_contact.jsp" %>

<div id="contact_success" class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog"
     aria-labelledby="myLargeModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button aria-hidden="true" data-dismiss="modal" class="close" type="button">×
                </button>
                <h4 class="modal-title"><uengine:message
                        code="common.title.alarm"/></h4>
            </div>
            <div class="modal-body">
                <p><uengine:message code="title.contactus.message.success"/></p>
            </div>
        </div>
    </div>
</div>
<div id="contact_failed" class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog"
     aria-labelledby="myLargeModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button aria-hidden="true" data-dismiss="modal" class="close" type="button">×
                </button>
                <h4 class="modal-title"><uengine:message
                        code="common.title.alarm"/></h4>
            </div>
            <div class="modal-body">
                <p><uengine:message code="title.contactus.message.fail"/></p>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    $(function () {
        $("#contact").validate({
            focusInvalid: false,
            ignore: "",
            rules: {
                name: "required",
                email: {
                    required: true,
                    email: true
                },
                telephone: "required",
                subject: "required",
                message: "required"
            },
            messages: {
                name: message.msg('form.invalid.common'),
                email: {
                    required: message.msg('form.invalid.common'),
                    email: message.msg('form.invalid.email')
                },
                telephone: message.msg('form.invalid.common'),
                subject: message.msg('form.invalid.common'),
                message: message.msg('form.invalid.common')
            },
            errorPlacement: function (error, element) {
                error.insertAfter(element.parent());
            },
            invalidHandler: function() {
                blockStop();
            },
            submitHandler: function (e) {
                var url = "/contact/post";
                $.ajax({

                    type: "post",
                    url: url,
                    dataType: "json",
                    data: $("#contact").serialize(),
                    success: function (result) {
                        if (result.success == true) {
                            $('#contact_success').modal({
                                show: true
                            });
                        } else if (result.success == false) {
                            $('#contact_failed').modal({
                                show: true
                            });
                        }
                        $('div').removeClass("state-success");
                    },
                    error: function (e) {
                        console.log(e.responseText);
                    }
                });
            }
        });
    });
</script>


</html>
