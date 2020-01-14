<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Clubs</title>
        <%@include file="/WEB-INF/includes/commons/web_styles.jsp" %>
    </head>
    <body>
        <%@include file="/WEB-INF/includes/header.jsp" %>
        <main class="container">
            <section class="row">
                <div class="col d-flex justify-content-center p-3">

                    <c:choose>
                        <c:when test="${not empty user.idClub}">
                            <a role="button" class="btn btn-primary" href="<c:url value='/clubs/${user.idClub}'/>">Mi club</a>
                        </c:when>
                        <c:otherwise>
                            <a class="btn btn-primary" href="<c:url value='/clubs/create'/>" role="button">Crear un club</a>
                        </c:otherwise>
                    </c:choose>
                </div>
            </section>

            <section class="row">
                <div class="list-group col">
                    <c:forEach items="${clubList}" var="club">
                        <a href="<c:url value='/clubs/${club.id}'/>" 
                           class="list-group-item list-group-item-action list-group-item-primary">${club.name}</a>
                    </c:forEach>
                </div>
            </section>
        </main>
        <%@include file="/WEB-INF/includes/commons/web_scripts.jsp" %>
    </body>
</html>
