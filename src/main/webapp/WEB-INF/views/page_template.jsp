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
                    <button class="btn btn-primary">Crear un club</button>
                </div>
            </section>
        </main>
        <%@include file="/WEB-INF/includes/commons/web_scripts.jsp" %>
    </body>
</html>
