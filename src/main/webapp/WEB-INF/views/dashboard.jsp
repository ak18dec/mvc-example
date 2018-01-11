<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout>
    <jsp:attribute name="title">${dashboard.name}</jsp:attribute>
    <jsp:body>
        <div class="ui grid container">
            
            <div class="ui link cards">
            
                <c:forEach var="feed" items="${dashboard.feeds}">
                    <div class="card">
                        
                        <div class="image">
                            <img src="${feed.image.url}" title="${feed.image.title}" onclick="window.open('feed/${feed.uri.hashCode()}', '_self');">
                        </div>
                        
                        <div class="content">
                            <a class="header" href="${feed.link}" target="_blank">${feed.title}</a>
                            <div class="meta">
                                <span class="date">${feed.publishedDate}</span>
                            </div>
                            <div class="description">
                                ${feed.description}
                            </div>
                        </div>
                        <div class="extra content">
                            <a href="${feed.uri}" target="_blank"><i class="feed icon"></i></a>
                            ${feed.entries.size()} Articles
                        </div>
                    </div>
                
                </c:forEach>
            </div>
        </div>
    </jsp:body>
</t:layout>
