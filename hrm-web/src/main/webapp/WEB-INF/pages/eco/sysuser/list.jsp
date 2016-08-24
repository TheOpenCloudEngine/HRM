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

    <script src="https://cdn.datatables.net/1.10.11/js/jquery.dataTables.min.js"></script>
    <link href="https://cdn.datatables.net/1.10.11/css/jquery.dataTables.min.css" rel="stylesheet" type="text/css"/>

    <!-- CSS Page Style -->
    <link rel="stylesheet" href="/resources/assets/css/pages/profile.css">

    <script type="text/javascript">
        function search(table, searchValue) {
            if (event.keyCode == 13) {
                reload(table, searchValue);
            }
        }
        ;

        function reload(table, searchValue) {
            // limit and skip setting
            var tableAPI = table.api();
            var limit = tableAPI.settings()[0]._iDisplayLength
            var skip = tableAPI.settings()[0]._iDisplayStart;

            tableAPI.ajax.url('/eco/sysuser/list?limit=' + limit + '&skip=' + skip + '&name=' + searchValue);
            tableAPI.ajax.reload();
        }
        ;

        $(document).ready(function () {
            var table = $('#sysuser').DataTable({
                serverSide: true,
                searching: false,
                ajax: {
                    url: '/eco/sysuser/list',
                    dataSrc: function (sysusers) {
                        // change init page setting (_iDisplayStart )
                        table.settings()[0]._iDisplayStart = sysusers.displayStart;

                        // make id edit href
                        for (var i = 0; i < sysusers.data.length; i++) {
                            if (sysusers.data[i]['defaultUser'] == 'Y') {
                                sysusers.data[i]['defaultUser'] = '<p style="color: red">Y</p>'
                            } else {
                                sysusers.data[i]['defaultUser'] = 'N'
                            }
                            sysusers.data[i]._id = '<a href=/eco/sysuser/edit?_id=' + sysusers.data[i]._id + '>Edit</a>';
                        }
                        return sysusers.data;
                    }
                },
                columns: [
                    {data: 'name'},
                    {data: 'defaultUser'},
                    {data: 'description'},
                    {data: '_id'}
                ]
            });

            // page event
            $('#sysuser').on('page.dt', function () {
                reload($('#sysuser').dataTable(), $('#customSearch').val().trim());

                // page length event
            }).on('length.dt', function () {
                reload($('#sysuser').dataTable(), $('#customSearch').val().trim());

            });
        });
    </script>

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
                        <div class="headline margin-bottom-10"><h4>System Users </h4></div>

                        <a class="btn-u btn-u-primary" href="/eco/sysuser/new">Create New System User</a>

                        <br>
                        <br>

                        <div class="margin-bottom-10">
                            <div class="table-responsive">
                                <div style="float: right"> Search : <input type="text" id="customSearch"
                                                                           onKeyDown="javascript: search($('#scopes').dataTable(), this.value)"/>
                                </div>
                                <table id="sysuser" class="display table table-bordered table-striped">
                                    <thead>
                                    <tr>
                                        <th>User Name</th>
                                        <th>Default</th>
                                        <th>Description</th>
                                        <th></th>
                                    </tr>
                                    </thead>
                                    <tbody></tbody>
                                </table>
                            </div>
                        </div>

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
</html>