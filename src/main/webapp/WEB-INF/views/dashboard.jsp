<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout title="${dashboard.name}" icon="${dashboard.icon.toString()}">
    
    <jsp:body>
        <div class="ui container">
            
            <div class="ui divided items">
                <c:forEach var="f" items="${dashboard.feeds}">
                
                    <div class="item">
                        <div class="ui tiny image">
                            <img src="${f.image}" style="cursor: pointer;" onclick="window.open('${mvc.contextPath}/view/feed/${f.id}', '_self');">
                        </div>
                        <div class="content">
                            <a class="header" href="${mvc.contextPath}/view/feed/${f.id}">${f.title}</a>
                            <div class="meta">
                                <span>${f.description}</span>
                            </div>
                            <div class="description">
                                <p>${f.entries.size()} Articles</p>
                            </div>
                            
                            <c:if test="${f.copyright != null}">
                                <div class="extra">
                                    &copy; <span class="date">${f.copyright}</span>
                                </div>
                            </c:if>
                            
                        </div>
                    </div>
                </c:forEach>
                
            </div>
            
        </div>
    </jsp:body>
</t:layout>
