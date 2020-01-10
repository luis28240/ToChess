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
                <div class="col">
                    <a class="btn btn-primary" href="<c:url value='/clubs/create'/>" role="button">Crear un club</a>
                </div>
            </section>
        </main>
        <%@include file="/WEB-INF/includes/commons/web_scripts.jsp" %>
    </body>
</html>
