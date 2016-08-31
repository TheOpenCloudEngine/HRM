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
      lang="en">
<!--<![endif]-->
<head>
    <meta charset="utf-8">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>OCE IAM | HOME</title>

    <%@include file="../template/header_js.jsp" %>

    <!-- CSS Page Style -->
    <link rel="stylesheet" href="/resources/assets/css/pages/page_faq1.css">
    <link rel="stylesheet" href="/resources/assets/css/pages/page_search_inner.css">

    <script src="https://cdn.datatables.net/1.10.11/js/jquery.dataTables.min.js"></script>
    <link href="https://cdn.datatables.net/1.10.11/css/jquery.dataTables.min.css" rel="stylesheet" type="text/css"/>

    <script src="/resources/js/hdfs.js"></script>
</head>


<div class="wrapper">
    <%@include file="../template/header.jsp" %>

    <!--=== Breadcrumbs ===-->
    <div class="breadcrumbs">
        <div class="container">
            <h1 class="pull-left">Hdfs Browser</h1>
            <ul class="pull-right breadcrumb">
                <li><a href="index.html">HOME</a></li>
                <li class="active">Hdfs Browser</li>
            </ul>
        </div>
    </div>
    <!--/breadcrumbs-->

    <!--=== Content Part ===-->

    <!-- Begin Table Search v1 -->
    <div class="container content profile">
        <div class="row">
            <div class="col-md-12">
                <div class="margin-bottom-10">
                    <div class="table-responsive">
                        <div id="path_shortcut"></div>
                        <div class="margin-bottom-10">
                            <p>
                                <button id="hdfs_newdir" class="btn-u btn-u-blue" type="button">New Folder</button>
                                <button id="hdfs_upload" class="btn-u btn-u-dark-blue" type="button">Upload</button>
                                <button id="hdfs_download" class="btn-u btn-u-default" type="button">Download</button>
                                <button id="hdfs_rename" class="btn-u btn-u-dark-blue" type="button">Rename</button>
                                <button id="hdfs_owner" class="btn-u btn-u-dark-blue" type="button">Owner</button>
                                <button id="hdfs_permission" class="btn-u btn-u-dark-blue" type="button">Permission
                                </button>
                                <button id="hdfs_delete" class="btn-u btn-u-red" type="button">Delete</button>
                            </p>
                        </div>

                        <div style="float: right"> Search : <input type="text" id="customSearch"
                                                                   onKeyDown="javascript: search()"/>
                        </div>
                        <table id="hdfs" class="display table table-bordered table-striped">
                            <thead>
                            <tr>
                                <th>Name</th>
                                <th>Size</th>
                                <th>Owner</th>
                                <th>Group</th>
                                <th>Permission</th>
                                <th>Status</th>
                            </tr>
                            </thead>
                            <tbody></tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!--=== End Content Part ===-->

    <%@include file="../template/footer.jsp" %>
</div>
<!--/wrapper-->
<%@include file="../template/footer_js.jsp" %>

<div class="modal fade" id="statusModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button aria-hidden="true" data-dismiss="modal" class="close" type="button">×</button>
                <h4 class="modal-title">Status</h4>
            </div>
            <div class="modal-body">
                <textarea rows="12" name="body" style="width: 100%;"></textarea>
            </div>
            <div class="modal-footer">
                <button class="btn-u" type="button" name="close">Close</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="newDirModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button aria-hidden="true" data-dismiss="modal" class="close" type="button">×</button>
                <h4 class="modal-title">New Folder</h4>
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
                <button class="btn-u" type="button" name="action">Create</button>
                <button class="btn-u" type="button" name="close">Cancle</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="uploadModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button aria-hidden="true" data-dismiss="modal" class="close" type="button">×</button>
                <h4 class="modal-title">Upload File</h4>
            </div>
            <div class="modal-body">
                <div id="progressPanel" class="col-md-6 md-margin-bottom-40" style="display: none">
                    <h3 class="heading-xs">Web Design - 88%</h3>

                    <div class="progress progress-u progress-xxs">
                        <div class="progress-bar progress-bar-dark"
                             role="progressbar" aria-valuenow="88" aria-valuemin="0" aria-valuemax="100"
                             style="width: 0%">
                        </div>
                    </div>
                </div>
                <form action="#" class="form-horizontal" role="form">
                    <div class="form-group">
                        <label class="col-md-2 control-label">File <span class="color-red">*</span></label>

                        <div class="col-md-6">
                            <input id="uploadfile" name="file" type="file" class="form-control" value="">
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button class="btn-u" type="button" name="action">Upload</button>
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
                <h4 class="modal-title">Rename</h4>
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


<div class="modal fade" id="ownerModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button aria-hidden="true" data-dismiss="modal" class="close" type="button">×</button>
                <h4 class="modal-title">Change Owner</h4>
            </div>
            <div class="modal-body">
                <form action="#" class="form-horizontal" role="form">
                    <div class="form-group">
                        <label class="col-md-2 control-label">Owner <span class="color-red">*</span></label>

                        <div class="col-md-6">
                            <input name="owner" type="text" class="form-control" value="">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-md-2 control-label">Group <span class="color-red">*</span></label>

                        <div class="col-md-6">
                            <input name="group" type="text" class="form-control" value="">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-md-2 control-label">Recursive </label>

                        <div class="col-md-6">
                            <label class="checkbox"><input type="checkbox" name="recursive" value="Y"
                                                           checked>recursive</label>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button class="btn-u" type="button" name="action">Change</button>
                <button class="btn-u" type="button" name="close">Cancle</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="permissionModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button aria-hidden="true" data-dismiss="modal" class="close" type="button">×</button>
                <h4 class="modal-title">Change Permission</h4>
            </div>
            <div class="modal-body">
                <form action="#" class="form-horizontal" role="form">
                    <div class="form-group">
                        <label class="col-md-2 control-label">Permission <span class="color-red">*</span></label>

                        <div class="col-md-6">
                            <input name="permission" type="number" class="form-control" value="">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-md-2 control-label">Recursive </label>

                        <div class="col-md-6">
                            <label class="checkbox"><input type="checkbox" name="recursive" value="Y"
                                                           checked>recursive</label>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button class="btn-u" type="button" name="action">Change</button>
                <button class="btn-u" type="button" name="close">Cancle</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="deleteModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button aria-hidden="true" data-dismiss="modal" class="close" type="button">×</button>
                <h4 class="modal-title">Caution</h4>
            </div>
            <div class="modal-body">
                <p style="text-align: center" name="content">Are you sure delete selected files?</p>
            </div>
            <div class="modal-footer">
                <button class="btn-u" type="button" name="action">Delete</button>
                <button class="btn-u" type="button" name="close">Cancle</button>
            </div>
        </div>
    </div>
</div>

</html>
