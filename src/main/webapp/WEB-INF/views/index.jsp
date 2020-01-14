<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, user-scalable=no">
        <title>ToChess - Home</title>
        <link rel="stylesheet"
              href="https://unpkg.com/@chrisoakman/chessboardjs@1.0.0/dist/chessboard-1.0.0.min.css"
              integrity="sha384-q94+BZtLrkL1/ohfjR8c6L+A6qzNH9R2hBLwyoAfu3i/WCvQjzL2RQJ3uNHDISdU"
              crossorigin="anonymous">
        <%@include file="/WEB-INF/includes/commons/web_styles.jsp" %>
        <style>
            .btn-warning{
                color: white;
            }
        </style>
    </head>
    <body>
        <%@include file="/WEB-INF/includes/header.jsp" %>
        <main class="container">
            <section class="row justify-content-center">
                <div class="col-12 d-flex justify-content-center align-items-center">
                    <button class="btn btn-primary" id="buscar" disabled>Buscar partida</button>
                    <div class="spinner-border spinner-border-sm ml-3" id="loadingCircle" role="status" hidden>
                        <span class="sr-only">Loading...</span>
                    </div>
                </div>
                <hr class="w-100"/>
                <section class="row w-100 justify-content-between" id="gameList">

                </section>
            </section>
        </main>
        <script src="https://cdn.jsdelivr.net/npm/sockjs-client@1/dist/sockjs.min.js"></script>
        
        <%@include file="/WEB-INF/includes/commons/web_scripts.jsp" %>
        
        <script src="https://unpkg.com/@chrisoakman/chessboardjs@1.0.0/dist/chessboard-1.0.0.min.js"
                integrity="sha384-8Vi8VHwn3vjQ9eUHUxex3JSN/NFqUg3QbPyX8kWyb93+8AC/pPWTzj+nHtbC5bxD"
        crossorigin="anonymous"></script>
        <script>
            var contextPath = "${pageContext.request.contextPath}";
        </script>
        <script src="<c:url value='/src/js/index.js'/>"></script>
    </body>
</html>
