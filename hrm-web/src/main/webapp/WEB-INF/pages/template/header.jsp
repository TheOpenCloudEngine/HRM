<%@ page contentType="text/html; charset=UTF-8" language="java" trimDirectiveWhitespaces="true" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="uengine" uri="http://www.uengine.io/tags" %>

<!--=== Header ===-->
<div class="header">
    <div class="container">
        <!-- Logo -->
        <a class="logo" href="/index">
            <img src="/resources/images/oce-logo.png" alt="Header Logo">
        </a>
        <!-- End Logo -->

        <!-- Topbar -->
        <div class="topbar">
            <ul class="loginbar pull-right">
                <li class="hoverSelector">
                    <i class="fa fa-globe"></i>
                    <a>Languages</a>
                    <ul class="languages hoverSelectorBlock">
                        <li data-language="ko_KR" data-text="힌국어" class="active"><a href="/index?lang=ko_KR"><i
                                class="fa fa-check"></i></a></li>
                        <li data-language="en_US" data-text="English"><a href="/index?lang=en_US"></a></li>
                        <%--
                                                <li data-language="ja_JP" data-text="中國語"><a href="#"></a></li>
                                                <li data-language="zh_CN" data-text="日本語"><a href="#"></a></li>
                        --%>
                    </ul>
                </li>
                <script type="text/javascript">
                    $(function(){
                       if(SESSION.ID){
                           $('.onsession').show();$('.offsession').hide();
                       }else{
                           $('.onsession').hide();$('.offsession').show();$('#loginbar_last_devide').hide();
                       }
                    });
                </script>

                <li class="topbar-devider"></li>
                
                <li class="offsession" style="display: none"><a href="/auth/login">SIGN IN</a></li>
                <li class="topbar-devider offsession" style="display: none"></li>
                <li class="offsession" style="display: none"><a href="/registe/register">SIGN UP</a></li>
                <li class="topbar-devider offsession" id="loginbar_last_devide" style="display: none"></li>
                <li class="onsession" style="display: none"><a href="/my/overview">MY PAGE</a></li>
                <li class="topbar-devider onsession" id="mypage_devide" style="display: none"></li>
                <li class="onsession" style="display: none">
                    <a href="#" id="logoutbtn">LOG OUT</a>
                </li>
                <form id="logoutform" action="/auth/logout" method="post" style="display: none"></form>
            </ul>
        </div>
        <!-- End Topbar -->

        <!-- Toggle get grouped for better mobile display -->
        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-responsive-collapse">
            <span class="sr-only">Toggle navigation</span>
            <span class="fa fa-bars"></span>
        </button>
        <!-- End Toggle -->
    </div>
    <!--/end container-->

    <!-- Collect the nav links, forms, and other content for toggling -->
    <div class="collapse navbar-collapse mega-menu navbar-responsive-collapse">
        <div class="container">
            <ul class="nav navbar-nav" id="menu_definition">

            </ul>
        </div>
        <!--/end container-->
    </div>
    <!--/navbar-collapse-->
</div>
<!--=== End Header ===-->


<!-- 메시지 Modal -->
<div class="modal fade" id="messageBox" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button aria-hidden="true" data-dismiss="modal" class="close" type="button">×</button>
                <h4 id="myModalLabel1" class="modal-title">알림</h4>
            </div>
            <div class="modal-body">
                <p style="text-align: center" name="content"></p>
            </div>
            <div class="modal-footer">
                <button class="btn-u" type="button" name="close">확인</button>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    var msgBox = function (message) {
        $('#messageBox').find('[name=content]').html(message);
        $('#messageBox').modal({
            show: true
        });
    };
    $(function () {
        $('#logoutbtn').click(function(){
           $('#logoutform').submit();
        });
        $('#messageBox').find('[name=close]').click(function () {
            $('#messageBox').find('.close').click();
        });
    });
</script>

