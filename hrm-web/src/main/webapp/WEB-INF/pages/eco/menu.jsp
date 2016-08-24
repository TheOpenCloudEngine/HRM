<%@ page contentType="text/html; charset=UTF-8" language="java" trimDirectiveWhitespaces="true" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="uengine" uri="http://www.uengine.io/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="headline margin-bottom-30"><h4>Eco System Console </h4></div>

<ul class="list-group sidebar-nav-v1 margin-bottom-40" id="sidebar-nav-1">
    <li class="list-group-item" name="list-menu-configuration">
        <a href="/eco/configuration"><i class="fa fa-bar-chart-o"></i> Configuration </a>
    </li>
    <li class="list-group-item" name="list-menu-sysuser">
        <a href="/eco/sysuser"><i class="fa fa-bar-chart-o"></i> System Users </a>
    </li>
    <li class="list-group-item" name="list-menu-hive">
        <a href="/eco/hive"><i class="fa fa-group"></i> Hive </a>
    </li>
    <li class="list-group-item" name="list-menu-spark">
        <a href="/eco/spark"><i class="fa fa-group"></i> Spark </a>
    </li>
    <li class="list-group-item" name="list-menu-mapreduce">
        <a href="/eco/reduce"><i class="fa fa-cubes"></i> Map reduce </a>
    </li>
    <li class="list-group-item" name="list-menu-pig">
        <a href="/eco/pig"><i class="fa fa-cubes"></i> Pig </a>
    </li>
</ul>