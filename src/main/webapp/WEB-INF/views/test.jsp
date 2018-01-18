<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout title="${dashboard.name}" icon="${dashboard.icon.toString()}">
    
    <jsp:body>
        <a href="$\{mvc.uri('MyController#myMethod' {'foo': 'bar', 'id': 42})}">test</a>
        
    </jsp:body>
</t:layout>
