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
        <a href="/eco/clientJob/editor/hive"><i class="fa fa-group"></i> Hive </a>
    </li>
    <li class="list-group-item" name="list-menu-spark">
        <a href="/eco/clientJob/editor/spark"><i class="fa fa-group"></i> Spark </a>
    </li>
    <li class="list-group-item" name="list-menu-mr">
        <a href="/eco/clientJob/editor/mr"><i class="fa fa-cubes"></i> Map reduce </a>
    </li>
    <li class="list-group-item" name="list-menu-pig">
        <a href="/eco/clientJob/editor/pig"><i class="fa fa-cubes"></i> Pig </a>
    </li>
    <li class="list-group-item" name="list-menu-java">
        <a href="/eco/clientJob/editor/java"><i class="fa fa-cubes"></i> Java </a>
    </li>
    <li class="list-group-item" name="list-menu-python">
        <a href="/eco/clientJob/editor/python"><i class="fa fa-cubes"></i> Python </a>
    </li>
    <li class="list-group-item" name="list-menu-shell">
        <a href="/eco/clientJob/editor/shell"><i class="fa fa-cubes"></i> Shell </a>
    </li>
    <li class="list-group-item" name="list-menu-hbaseShell">
        <a href="/eco/clientJob/editor/hbaseShell"><i class="fa fa-cubes"></i> Hbase - Shell </a>
    </li>
    <li class="list-group-item" name="list-menu-hbaseClass">
        <a href="/eco/clientJob/editor/hbaseClass"><i class="fa fa-cubes"></i> Hbase - Class </a>
    </li>
    <li class="list-group-item" name="list-menu-phoenix">
        <a href="/eco/clientJob/editor/phoenix"><i class="fa fa-cubes"></i> Phoenix </a>
    </li>
</ul>