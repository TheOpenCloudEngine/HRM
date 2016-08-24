<%@ page import="org.opencloudengine.garuda.util.ApplicationContextRegistry" %>
<%@ page import="org.opencloudengine.garuda.util.NetworkUtils" %>
<%@ page import="org.opencloudengine.garuda.model.User" %>
<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="org.springframework.context.i18n.LocaleContextHolder" %>
<%@ page import="java.util.Locale" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" trimDirectiveWhitespaces="true" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="uengine" uri="http://www.uengine.io/tags" %>
<!-- Meta -->
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>

<!-- 타이틀 -->
<meta name="title" content="uengine Inc"/>
<!-- 주제 -->
<meta name="subject" content="Big Data Platform"/>
<!-- 요약 설명 -->
<meta name="description" content="Hadoop 기반 빅데이터 플랫폼 - Hadoop을 이용한 빅데이터 환경에서 데이터를 처리,분석,시각화할 수 있는 기반환경을 제공합니다.">
<!-- 검색 키워드 -->
<meta name="keywords" content="Hadoop,Platform,하둡,플랫폼,빅데이터,Big Data,ExtJS,분석,Cloud,클라우드"/>
<!-- 웹 페이지(사이트) © Copyright 저작권 -->
<meta name="copyright" content="uengine Inc"/>
<!-- 웹 페이지(사이트) 검색사이트 검색로봇의 접근 허용 -->
<meta name="robots" content="ALL"/>
<!-- 웹 페이지(사이트) 내의 링크를 포함한 검색(수집) 허용 -->
<meta name="robots" content="INDEX,FOLLOW"/>
<!-- 웹 페이지(사이트) 캐쉬(Cache) 미저장 -->
<meta http-equiv="cache-control" content="No-Cache"/>
<meta http-equiv="pragma" content="No-Cache"/>
<meta http-equiv="expires" content="MON, 01 apr 2002 00:00:01 GMT">
<meta name="Location" content="South Korea"/>
<meta name="Distribution" content="Global"/>
<meta name="author" content="클라우다인">

<!-- Favicon -->
<link rel="shortcut icon" href="/resources/images/favicon.ico">

<!-- CSS Global Compulsory -->
<link rel="stylesheet" href="/resources/assets/plugins/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="/resources/assets/css/style.css">

<!-- CSS Header and Footer -->
<link rel="stylesheet" href="/resources/assets/css/headers/header-default.css">
<link rel="stylesheet" href="/resources/assets/css/footers/footer-v1.css">

<!-- CSS Implementing Plugins -->
<link rel="stylesheet" href="/resources/assets/plugins/animate.css">
<link rel="stylesheet" href="/resources/assets/plugins/line-icons/line-icons.css">
<link rel="stylesheet" href="/resources/assets/plugins/font-awesome/css/font-awesome.min.css">
<link rel="stylesheet" href="/resources/assets/plugins/cube-portfolio/cubeportfolio/css/cubeportfolio.min.css">
<link rel="stylesheet" href="/resources/assets/plugins/cube-portfolio/cubeportfolio/custom/custom-cubeportfolio.css">
<link rel="stylesheet" href="/resources/assets/plugins/parallax-slider/css/parallax-slider.css">
<link rel="stylesheet" href="/resources/assets/plugins/owl-carousel/owl-carousel/owl.carousel.css">
<link rel="stylesheet" href="/resources/assets/css/pages/pricing/pricing-light.css">

<link rel="stylesheet" href="/resources/assets/plugins/scrollbar/css/jquery.mCustomScrollbar.css">
<link rel="stylesheet" href="/resources/assets/plugins/sky-forms-pro/skyforms/css/sky-forms.css">
<link rel="stylesheet" href="/resources/assets/plugins/sky-forms-pro/skyforms/custom/custom-sky-forms.css">
<!-- CSS Customization -->
<link rel="stylesheet" href="/resources/assets/css/custom.css">

<!-- JS Global Compulsory -->
<script type="text/javascript" src="/resources/assets/plugins/jquery/jquery.min.js"></script>
<script type="text/javascript" src="/resources/assets/plugins/jquery/jquery-migrate.min.js"></script>
<script type="text/javascript" src="/resources/assets/plugins/bootstrap/js/bootstrap.min.js"></script>

<!-- Spring JS -->
<script type="text/javascript" th:src="@{/resources/spring/Spring.js}"></script>
<script type="text/javascript" th:src="@{/resources/spring/Spring-Dojo.js}"></script>


<!-- _csrf -->
<meta name="_csrf" content="${_csrf.token}"/>
<!-- default header name is X-CSRF-TOKEN -->
<meta name="_csrf_header" content="${_csrf.headerName}"/>
<!-- AJAX_csrf 오버라이드  &&  더블 submit 방지-->
<script type="text/javascript">
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function(e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });
    $(function(){
        $('form').each(function(){
            var form = $(this);
            form.append('<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>');
            if(form.find('[type=submit]').length > 0){
                form.submit(function(){
                    blockSubmitStart();
                });
            }
        });
    })
</script>

<!-- config && message -->
<script type="text/javascript" src="/config/js.json"></script>
<script type="text/javascript">var lang = "${lang}";
lang = (lang.length > 0) ? lang : config['default.locale'];</script>
<script type="text/javascript" src="/resources/js/bundle.js"></script>

<!-- session -->
<%
    User user = (User) session.getAttribute("user");
    String ipAddress = NetworkUtils.getIpAddr(request);

    //해당 세션의 첫 접속인경우, ISOCode 와 언어가 일치하지 않을 경우 맞는언어페이지로 리다이렉트.
    if (session.getAttribute("ISOCode") == null) {
        String ISOCode = NetworkUtils.getCountryCode(ipAddress);
        session.setAttribute("ISOCode", ISOCode);
        String localeByIpaddr = NetworkUtils.getLocaleFromISOCode(ISOCode);
        Locale locale = LocaleContextHolder.getLocale();
        if (!localeByIpaddr.equals(locale.toString()))
            response.sendRedirect("/index?lang=" + localeByIpaddr);
    }

%>
<script type="text/javascript">
    function isEmpty(str) {
        if (typeof str == 'string' && str.length > 0)return false;
        return true;
    }
    var SESSION = {};
    SESSION.ISOCode = !isEmpty('${ISOCode}') ? '${ISOCode}' : undefined;
    SESSION.ID = !isEmpty('${user._id}') ? '${user._id}' : undefined;
    SESSION.NAME = !isEmpty('${user.name}') ? '${user.name}' : undefined;
    SESSION.EMAIL = !isEmpty('${user.email}') ? '${user.email}' : undefined;
    SESSION.DESCRIPTION = !isEmpty('${user.description}') ? '${user.description}' : undefined;
    SESSION.LEVEL = !isEmpty('${user.level}') ? '${user.level}' : undefined;

</script>

<!-- CSS Theme -->
<link rel="stylesheet" href="/resources/assets/css/theme-colors/dark-blue.css">

<!-- uengine CSS -->
<link rel="stylesheet" href="/resources/assets/css/uengine.css">

<!-- google-analytics -->
<script>
    (function (i, s, o, g, r, a, m) {
        i['GoogleAnalyticsObject'] = r;
        i[r] = i[r] || function () {
            (i[r].q = i[r].q || []).push(arguments)
        }, i[r].l = 1 * new Date();
        a = s.createElement(o),
                m = s.getElementsByTagName(o)[0];
        a.async = 1;
        a.src = g;
        m.parentNode.insertBefore(a, m)
    })(window, document, 'script', '//www.google-analytics.com/analytics.js', 'ga');

    ga('create', 'UA-63092719-1', 'auto');
    ga('send', 'pageview');

</script>

<!-- Start of uenginesupport Zendesk Widget script -->
<script>/*<![CDATA[*/window.zEmbed||function(e,t){var n,o,d,i,s,a=[],r=document.createElement("iframe");window.zEmbed=function(){a.push(arguments)},window.zE=window.zE||window.zEmbed,r.src="javascript:false",r.title="",r.role="presentation",(r.frameElement||r).style.cssText="display: none",d=document.getElementsByTagName("script"),d=d[d.length-1],d.parentNode.insertBefore(r,d),i=r.contentWindow,s=i.document;try{o=s}catch(c){n=document.domain,r.src='javascript:var d=document.open();d.domain="'+n+'";void(0);',o=s}o.open()._l=function(){var o=this.createElement("script");n&&(this.domain=n),o.id="js-iframe-async",o.src=e,this.t=+new Date,this.zendeskHost=t,this.zEQueue=a,this.body.appendChild(o)},o.write('<body onload="document._l();">'),o.close()}("https://assets.zendesk.com/embeddable_framework/main.js","uenginesupport.zendesk.com");/*]]>*/</script>
<!-- End of uenginesupport Zendesk Widget script -->
