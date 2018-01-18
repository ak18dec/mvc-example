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
                                <c:if test="${not empty f.copyright}">
                                    <i class="copyright icon" title="${f.copyright}"></i>
                                </c:if>
                            </div>
                            <div class="description">
                                <p>${f.entries.size()} Articles</p>
                            </div>
                            <div class="extra">
                                <div class="ui right floated">
                                    <a href="{mvc.uri('DashboardController#refreshFeed' {'id': ${f.id}})}"><i class="large refresh icon" style="color:darkseagreen;cursor: pointer;" title="Refresh"></i></a>
                                    <i class="large trash outline icon" style="color:lightcoral;cursor: pointer;" title="Remove"></i>
                                </div>
                            </div>
                        </div>
                        
                    </div>
                </c:forEach>
                
            </div>
            
        </div>
    </jsp:body>
</t:layout>
