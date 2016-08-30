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

    <script type="text/javascript">
        var srcPath = '/';
        function search(table, searchValue, srcPath) {
            if (event.keyCode == 13) {
                reload(table, searchValue, srcPath);
            }
        }
        ;

        function reload(table, searchValue, srcPath) {
            // limit and skip setting
            var tableAPI = table.api();
            var limit = tableAPI.settings()[0]._iDisplayLength
            var skip = tableAPI.settings()[0]._iDisplayStart;

            tableAPI.ajax.url('/hdfs/list?limit=' + limit + '&skip=' + skip + '&filter=' + searchValue + '&path=' + srcPath);
            tableAPI.ajax.reload();
        }
        ;

        $(document).ready(function () {
            var table = $('#hdfs').DataTable({
                serverSide: true,
                searching: false,
                ajax: {
                    url: '/hdfs/list',
                    dataSrc: function (dataObj) {
                        // change init page setting (_iDisplayStart )
                        table.settings()[0]._iDisplayStart = dataObj.displayStart;

                        // make id edit href
//                        for (var i = 0; i < dataObj.data.length; i++) {
//                            dataObj.data[i].managementName = '<a href=/management/session?_id=' + managements.data[i]._id + '>' + managements.data[i].managementName + '</a>';
//                            dataObj.data[i]._id = '<a href=/management/edit?_id=' + managements.data[i]._id + '>Edit</a>';
//                        }
                        return dataObj.data;
                    }
                },
                columns: [
                    {data: 'filename'},
                    {data: 'length'},
                    {data: 'owner'},
                    {data: 'group'},
                    {data: 'permission'},
                    {data: 'path'}
                ]
            });

            // page event
            $('#hdfs').on('page.dt', function () {
                reload($('#hdfs').dataTable(), $('#customSearch').val().trim());

                // page length event
            }).on('length.dt', function () {
                reload($('#hdfs').dataTable(), $('#customSearch').val().trim());

            });
        });
    </script>
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
                        <div style="float: right"> Search : <input type="text" id="customSearch"
                                                                   onKeyDown="javascript: search($('#hdfs').dataTable(), this.value)"/>
                        </div>
                        <table id="hdfs" class="display table table-bordered table-striped">
                            <thead>
                            <tr>
                                <th>Name</th>
                                <th>Size</th>
                                <th>Owner</th>
                                <th>Group</th>
                                <th>Permission</th>
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

    <!--=== End Content Part ===-->

    <%@include file="../template/footer.jsp" %>
</div>
<!--/wrapper-->
<%@include file="../template/footer_js.jsp" %>

</html>
