<%@ page contentType="text/html; charset=UTF-8" language="java" trimDirectiveWhitespaces="true" %>

<%@ taglib prefix="uengine" uri="http://www.uengine.io/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if !IE]><!-->
<html xmlns="http://www.w3.org/1999/xhtml"
      lang="en">
<!--<![endif]-->
<head>
    <%@include file="../../template/header_js.jsp" %>

    <link rel="stylesheet" href="/resources/assets/plugins/sky-forms-pro/skyforms/css/sky-forms.css">
    <link rel="stylesheet" href="/resources/assets/plugins/sky-forms-pro/skyforms/custom/custom-sky-forms.css">
    <!--[if lt IE 9]>
    <link rel="stylesheet" href="/resources/assets/plugins/sky-forms-pro/skyforms/css/sky-forms-ie8.css"><![endif]-->

    <!-- CSS Page Style -->
    <link rel="stylesheet" href="/resources/assets/css/pages/page_faq1.css">
    <link rel="stylesheet" href="/resources/assets/css/pages/page_search_inner.css">
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

    <!--=== Content Part ===-->
    <div class="container content profile">
        <div class="row">
            <!--Left Sidebar-->
            <div class="col-md-3 md-margin-bottom-40">
                <%@include file="../menu.jsp" %>
                <script type="text/javascript">$('[name=list-menu-configuration]').addClass('active');</script>
            </div>
            <!--End Left Sidebar-->

            <!-- Profile Content -->
            <div class="col-md-9">
                <form action="/eco/configuration/edit" class="form-horizontal"
                      role="form"
                      id="iamForm" method="post">
                    <h4>Edit Configuration </h4>

                    <c:choose>
                        <c:when test="${failed}">
                            <h4 style="color: #ff0000">Configuration edit failed. </h4>
                        </c:when>
                    </c:choose>

                    <div class="form-group">
                        <label class="col-md-2 control-label">Hdfs Super User <span class="color-red">*</span></label>

                        <div class="col-md-6">
                            <input name="hdfsSuperUser" type="text" class="form-control" value="${ecoConf.hdfsSuperUser}">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-md-2 control-label">Hadoop Home <span class="color-red">*</span></label>

                        <div class="col-md-6">
                            <input name="hadoopHome" type="text" class="form-control" value="${ecoConf.hadoopHome}">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-md-2 control-label">Hive Home <span class="color-red">*</span></label>

                        <div class="col-md-6">
                            <input name="hiveHome" type="text" class="form-control" value="${ecoConf.hiveHome}">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-md-2 control-label">Pig Home <span class="color-red">*</span></label>

                        <div class="col-md-6">
                            <input name="pigHome" type="text" class="form-control" value="${ecoConf.pigHome}">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-md-2 control-label">Spark Home <span class="color-red">*</span></label>

                        <div class="col-md-6">
                            <input name="sparkHome" type="text" class="form-control" value="${ecoConf.sparkHome}">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-md-2 control-label">Hdfs Home <span class="color-red">*</span></label>

                        <div class="col-md-6">
                            <input name="hdfsHome" type="text" class="form-control" value="${ecoConf.hdfsHome}">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-md-2 control-label">Mapreduce Home <span class="color-red">*</span></label>

                        <div class="col-md-6">
                            <input name="mapreduceHome" type="text" class="form-control" value="${ecoConf.mapreduceHome}">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-md-2 control-label">Yarn Home <span class="color-red">*</span></label>

                        <div class="col-md-6">
                            <input name="yarnHome" type="text" class="form-control" value="${ecoConf.yarnHome}">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-md-2 control-label">Hbase Home <span class="color-red">*</span></label>

                        <div class="col-md-6">
                            <input name="hbaseHome" type="text" class="form-control" value="${ecoConf.hbaseHome}">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-md-2 control-label">Phoenix Home <span class="color-red">*</span></label>

                        <div class="col-md-6">
                            <input name="phoenixHome" type="text" class="form-control" value="${ecoConf.phoenixHome}">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-md-2 control-label">Java Home <span class="color-red">*</span></label>

                        <div class="col-md-6">
                            <input name="javaHome" type="text" class="form-control" value="${ecoConf.javaHome}">
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="col-md-offset-2 col-md-10">
                            <button id="submitBtn" type="submit" class="btn-u btn-u-primary">Edit
                            </button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <!--=== End Content Part ===-->

    <%@include file="../../template/footer.jsp" %>
</div>
<!--/wrapper-->
<%@include file="../../template/footer_js.jsp" %>

<script type="text/javascript">
    $(function () {
        var form = $('#iamForm');

        //폼 발리데이션
        form.validate({

            focusInvalid: false, // do not focus the last invalid input
            ignore: "",
            rules: {

            },
            invalidHandler: function () {
                blockStop();
            }
        });
    })
</script>
</html>
