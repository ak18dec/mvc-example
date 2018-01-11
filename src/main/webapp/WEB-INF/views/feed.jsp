<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout>
    <jsp:attribute name="title">${feed.title}</jsp:attribute>
    <jsp:body>
        <div class="ui grid container">
            
            <div class="ui link cards">
            
                <c:forEach var="entry" items="${feed.entries}">
                    <div class="card">
                        
                        <div class="image">
                            <img src="${feed.image.url}" title="${feed.image.title}" onclick="window.open('feed/${feed.uri.hashCode()}', '_self');">
                        </div>
                        
                        <div class="content">
                            <a class="header" href="${entry.link}" target="_blank">${entry.title}</a>
                            <div class="meta">
                                <span class="date">${entry.publishedDate}</span>
                            </div>
                            <div class="description">
                                ${entry.description}
                            </div>
                        </div>
                        <div class="extra content">
                            <a href="${entry.uri}" target="_blank"><i class="feed icon"></i></a>
                            Read
                        </div>
                    </div>
                
                </c:forEach>
            </div>
        </div>
        
        
    </jsp:body>
</t:layout>





