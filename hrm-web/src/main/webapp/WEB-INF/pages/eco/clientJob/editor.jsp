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
<script type="text/javascript" src="/resources/js/jobEditor.js"></script>

<div class="wrapper">
    <%@include file="../../template/header.jsp" %>

    <!--=== Breadcrumbs ===-->
    <div class="breadcrumbs">
        <div class="container">
            <h1 class="pull-left">Client Job Editor</h1>
            <ul class="pull-right breadcrumb">
                <li><a href="/index">HOME</a></li>
                <li class="active">Client Job Editor</li>
            </ul>
        </div>
    </div>
    <!--/breadcrumbs-->

    <!--=== Profile ===-->
    <div class="container content profile">
        <div class="row">
            <!--Left Sidebar-->
            <div class="col-md-3 md-margin-bottom-40">
                <%@include file="../menu.jsp" %>
                <script type="text/javascript">
                    var jobType = '${jobType}';
                    $('[name=list-menu-' + jobType + ']').addClass('active');
                </script>
            </div>
            <!--End Left Sidebar-->

            <div class="col-md-7">
                <div class="row">
                    <div class="col-md-12">
                        <div class="tab-v1">
                            <ul class="nav nav-tabs">
                                <li id="history-tab-plus" class=""><a href="#">+</a></li>
                            </ul>
                            <div class="tab-content">
                                <div class="tab-pane fade active in" id="editorPanel">
                                    <div class="tag-box tag-box-v2 margin-bottom-10" style="padding: 10px;">
                                        <p id="jobTitle"></p>
                                    </div>
                                    <div class="margin-bottom-10">
                                        <button id="curlBtn" class="btn btn-default pull-right margin-left-10"
                                                type="button">Curl
                                        </button>
                                        <button id="saveAsBtn" class="btn btn-default pull-right margin-left-10"
                                                type="button">Save as
                                        </button>
                                        <button id="saveBtn" class="btn btn-default pull-right margin-left-10"
                                                type="button">Save
                                        </button>
                                        <button id="sendBtn" class="btn btn-primary pull-right margin-left-10"
                                                type="button">Send
                                        </button>
                                    </div>

                                    <div class="panel panel-grey">
                                        <div class="panel-heading">
                                            <h3 class="panel-title"><i class="fa fa-tasks"></i> Parameters</h3>
                                        </div>
                                        <div class="panel-body">
                                            <div class="panel-group acc-v1" id="accordion-1">
                                                <div class="panel panel-default">
                                                    <div class="panel-heading">
                                                        <h4 class="panel-title">
                                                            <a class="accordion-toggle collapsed" data-toggle="collapse"
                                                               data-parent="#accordion-1" href="#basic-params"
                                                               aria-expanded="false">
                                                                Basic Parameters
                                                            </a>
                                                        </h4>
                                                    </div>
                                                    <div id="basic-params" class="panel-collapse collapse"
                                                         aria-expanded="false" style="height: 0px;">
                                                        <div class="panel-body">
                                                            <form action="#" class="form-horizontal margin-bottom-10"
                                                                  role="form" id="basic-params-form">

                                                            </form>
                                                        </div>
                                                    </div>
                                                </div>

                                                <div class="panel panel-default">
                                                    <div class="panel-heading">
                                                        <h4 class="panel-title">
                                                            <a class="accordion-toggle collapsed" data-toggle="collapse"
                                                               data-parent="#accordion-1" href="#native-params"
                                                               aria-expanded="false">
                                                                Native Client Parameters
                                                            </a>
                                                        </h4>
                                                    </div>
                                                    <div id="native-params" class="panel-collapse collapse"
                                                         aria-expanded="false">
                                                        <div class="panel-body">
                                                            <form action="#" class="form-horizontal margin-bottom-10"
                                                                  role="form" id="native-params-form">

                                                            </form>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="margin-bottom-10">
                                        <button class="active btn btn-default" type="button" id="job-log-btn">Log</button>
                                        <button class="btn btn-default" type="button" id="job-detail-btn">Detail</button>
                                        <button class="btn btn-default" type="button" id="job-kill-btn">Kill Log</button>

                                        <button id="status-kill" class="btn btn-default pull-right margin-left-10 job-status" type="button">KILL
                                        </button>
                                        <button id="status-kill-failed" class="btn btn-danger pull-right margin-left-10 job-status" type="button">KILL FAILED
                                        </button>
                                        <button id="status-running" class="btn btn-warning pull-right margin-left-10 job-status" type="button">
                                            RUNNING
                                        </button>
                                        <button id="status-finished" class="btn btn-success pull-right margin-left-10 job-status" type="button">
                                            FINISHED
                                        </button>
                                        <button id="status-failed" class="btn btn-danger pull-right margin-left-10 job-status" type="button">FAILED
                                        </button>
                                        <button id="status-killed" class="btn btn-default pull-right margin-left-10 job-status" type="button">KILLED
                                        </button>
                                        <button id="status-stopping" class="btn btn-warning pull-right margin-left-10 job-status" type="button">
                                            STOPPING
                                        </button>
                                        <button id="status-standby" class="btn btn-warning pull-right margin-left-10 job-status" type="button">
                                            STANDBY
                                        </button>
                                    </div>

                                    <form action="#" class="form-horizontal" role="form" id="logForm" method="post">
                                        <div class="form-group">
                                            <div class="col-md-12">
                                                <textarea id="logArea" name="body" rows="12" class="form-control"
                                                          readonly></textarea>
                                            </div>
                                        </div>

                                        <div class="form-group" style="display: none">
                                            <div class="col-md-12">
                                                <textarea id="detailArea" name="body" rows="12" class="form-control"
                                                          readonly></textarea>
                                            </div>
                                        </div>

                                        <div class="form-group" style="display: none">
                                            <div class="col-md-12">
                                                <textarea id="killArea" name="body" rows="12" class="form-control"
                                                          readonly></textarea>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-md-2">
                <div class="headline margin-bottom-10"><h4>Collections </h4></div>

                <ul class="list-group sidebar-nav-v1" id="collection-list">

                </ul>
            </div>

        </div>
        <!--/end row-->
    </div>
    <!--=== End Profile ===-->

    <%@include file="../../template/footer.jsp" %>
</div>
<!--/wrapper-->

<%@include file="../../template/footer_js.jsp" %>

<div class="modal fade" id="deleteConfirm" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button aria-hidden="true" data-dismiss="modal" class="close" type="button">×</button>
                <h4 class="modal-title">Caution</h4>
            </div>
            <div class="modal-body">
                <p style="text-align: center" name="content">Are you sure delete system user?</p>
            </div>
            <div class="modal-footer">
                <button class="btn-u" type="button" name="delete">Delete</button>
                <button class="btn-u" type="button" name="close">Cancle</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="saveAsModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button aria-hidden="true" data-dismiss="modal" class="close" type="button">×</button>
                <h4 class="modal-title">Save As (collection)</h4>
            </div>
            <div class="modal-body">
                <form action="#" class="form-horizontal" role="form">
                    <div class="form-group">
                        <label class="col-md-2 control-label">Name <span class="color-red">*</span></label>

                        <div class="col-md-6">
                            <input name="name" type="text" class="form-control" value="">
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button class="btn-u" type="button" name="action">Save</button>
                <button class="btn-u" type="button" name="close">Cancle</button>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="renameModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button aria-hidden="true" data-dismiss="modal" class="close" type="button">×</button>
                <h4 class="modal-title">Rename (collection)</h4>
            </div>
            <div class="modal-body">
                <form action="#" class="form-horizontal" role="form">
                    <div class="form-group">
                        <label class="col-md-2 control-label">Name <span class="color-red">*</span></label>

                        <div class="col-md-6">
                            <input name="name" type="text" class="form-control" value="">
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button class="btn-u" type="button" name="action">Rename</button>
                <button class="btn-u" type="button" name="close">Cancle</button>
            </div>
        </div>
    </div>
</div>
</html>