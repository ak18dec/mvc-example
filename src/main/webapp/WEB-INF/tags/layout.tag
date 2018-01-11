<%@tag description="layout" pageEncoding="UTF-8"%>
<%@attribute name="title" fragment="true" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta charset="utf-8">

        <link rel='stylesheet' type='text/css' href='${mvc.contextPath}/webjars/semantic-ui/${semantic-ui.version}/dist/semantic.css'>
        <link rel='stylesheet' type='text/css' href='${mvc.contextPath}/webjars/font-awesome/${font-awesome.version}/css/font-awesome.min.css'>

        <title><jsp:invoke fragment="title"/></title>
    </head>
    <body>
        
        <div class="ui top hidden menu">
            <div class="ui container">
                <div class="header item">
                    <jsp:invoke fragment="title"/>
                </div>
                <div class="right menu">
                    <div class="item">
                        <a class="ui button" href="https://github.com/phillip-kruger/mvc-example" target="_blank">
                            <i class="github icon"></i>
                            Source code
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