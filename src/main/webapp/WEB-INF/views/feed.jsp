<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout title="${feed.title}" icon="${feed.image}">
    <jsp:body>
        
        <br/>
        <div class="ui grid container">
            
            <div class="ui three stackable cards">
                <c:forEach var="entry" items="${feed.entries}">
                    <div class="card">
                        <div class="image" style="cursor: zoom-in;" onclick="$('#${entry.id}').modal('show');">
                            <img src="${entry.imageLink}">
                        </div>
                      
                        <div class="content">
                            <div class="header">${entry.title}</div>
                            
                            <div class="description">
                                ${entry.content}
                            </div>
                            
                            <div class="meta right floated">
                                <a href="${entry.link}" target="_blank">Read full article <i class="external icon"></i></a>
                            </div>
                            
                        </div>
                        <div class="extra content">
                            <span class="right floated">
                                <fmt:formatDate value="${entry.publishedDate}" pattern="hh:mm:ss a"/>
                            </span>
                            <span>
                                <i class="calendar icon"></i>
                                <fmt:formatDate value="${entry.publishedDate}" pattern="dd MMMMM yyyy"/>
                            </span>
                        </div>
                      
                      
                    </div>
                    
                    <div class="ui modal" id="${entry.id}">
                        <div class="header">${entry.title}</div>
                        <div class="image content">
                            <img class="image" src="${entry.imageLink}">

                        </div>
                        <div class="content description">
                            <p>${entry.content}</p>
                            <i class="calendar icon"></i>
                            <fmt:formatDate value="${entry.publishedDate}" pattern="dd MMMMM yyyy hh:mm:ss a"/>
                        </div>
                        <div class="actions">
                            <div class="ui black deny button" style="cursor: pointer;" onclick="$('#${entry.id}').modal('hide');">
                                Close
                            </div>
                            
                            <div class="ui positive right labeled icon button" style="cursor: pointer;" onclick="window.open('${entry.link}', '_blank');">
                                Read full article
                                <i class="external icon"></i>
                            </div>
                        </div>
                    </div>        
                            
                </c:forEach>
              </div>
            
        </div>
        
        
    </jsp:body>
</t:layout>





