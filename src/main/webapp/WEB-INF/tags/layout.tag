<%@taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@tag description="layout" pageEncoding="UTF-8"%>
<%@attribute name="title" fragment="false" %>
<%@attribute name="icon" fragment="false" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta charset="utf-8">

        <link rel='stylesheet' type='text/css' href='${mvc.contextPath}/webjars/semantic-ui/${semantic-ui.version}/dist/semantic.css'>
        <link rel='stylesheet' type='text/css' href='${mvc.contextPath}/webjars/font-awesome/${font-awesome.version}/css/font-awesome.min.css'>

        <title>${title}</title>
    </head>
    <body>
        
        <div class="ui top hidden menu">
            <div class="ui container">
                <c:if test="${not empty icon}">
                    <div class="item">
                        <img src="${icon}" style="cursor: pointer;" onclick="window.open('${mvc.contextPath}/view', '_self');">
                    </div>
                </c:if>
                <a class="item header" href="${mvc.contextPath}/view">${title}</a>
                
                <div class="right menu">
                    <div class="item">
                        <a class="ui basic button" href="https://www.mvc-spec.org/" target="_blank">
                            <i class="info circle icon"></i> MVC 1.0
                        </a>
                    </div>
                    
                    <div class="item">
                        <a class="ui button" href="https://github.com/phillip-kruger/mvc-example" target="_blank">
                            <i class="github icon"></i> Source code
                        </a>
                    </div>
                </div>
            </div>
        </div>
        
        <jsp:doBody/>
        
        <script type='text/javascript' src='${mvc.contextPath}/webjars/jquery/${jquery.version}/dist/jquery.js'></script>
        <script type='text/javascript' src='${mvc.contextPath}/webjars/semantic-ui/${semantic-ui.version}/dist/semantic.js'></script>
    </body>
</html>