<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Club: ${club.name}</title>
        <%@include file="/WEB-INF/includes/commons/web_styles.jsp" %>
    </head>
    <body>
        <%@include file="/WEB-INF/includes/header.jsp" %>
        <main class="container">
            <section class="row">

                <div class="col-6">Nombre: ${club.name}</div>
                <div class="col-6">Lider: ${club_leader.username}</div>


                <div class="col-6">Máximos jugadores: ${not empty club.maxUsers ? club.maxUsers : "Ilimitado"}</div>
                <c:choose>
                    <c:when test="${user.idClub == club.id and club_leader.id != user.id}">
                        <a role="button" class="btn btn-warning col-6" 
                           href="<c:url value='/clubs/leave'/>">Abandonar</a>
                    </c:when>
                    <c:when test="${empty user.idClub}">
                        <a role="button" class="btn btn-primary col-6" href="<c:url value='/clubs/${club.id}/join'/>">Ingresar</a>
                    </c:when>
                    <c:when test="${club_leader.id == user.id}">El lider no puede abandonar</c:when>
                </c:choose>
            </section>
            <section class="row justify-content-center p-3">
                <c:if test="${club_leader.id == user.id}">
                    <button role="button" class="btn btn-danger col-6" data-club="${club.id}"
                            id="btnDeleteClub">Eliminar</button>
                </c:if>
            </section>
            <hr class="w-100"/>

            <table class="table">
                <thead>
                    <tr>
                        <th scope="col">Username</th>
                        <th scope="col">Email</th>
                        <th scope="col">Classical</th>
                            <c:if test="${club_leader.id == user.id}">
                            <th scope="col">Acciones</th>
                            </c:if>
                    </tr>
                </thead>
                <tbody>

                    <c:forEach items="${memberList}" var="member">
                        <tr>
                            <td><a href="<c:url value='/user/${member.username}'/>">${member.username}</a></td>
                            <td>${member.email}</td>
                            <td>${member.classical}</td>
                            <c:if test="${club_leader.id == user.id}">
                                <td>
                                    <div class="row">
                                        <button role="button" class="btn btn-success col change-leader m-2" 
                                                data-user="${member.id}" data-club="${club.id}">Pasar Liderazgo</button>
                                        <button role="button" class="btn btn-danger col ban-user m-2" 
                                                data-user="${member.id}" data-club="${club.id}">Banear</button>
                                    </div>
                                </td>
                            </c:if>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

        </main>
        <%@include file="/WEB-INF/includes/commons/web_scripts.jsp" %>
        <script>
            var contextPath = "${pageContext.request.contextPath}";
        </script>
        <script>
            "use strict";
            
            function deleteClub() {
                $.ajax({
                    url: contextPath + "/clubs/" + $(this).data("club"),
                    type: 'DELETE',
                    success: function (data, textStatus, jqXHR) {
//                        alert("funciono!");
                        location.href = contextPath + "/clubs";
                    }
                });
            }
            ///{id}/leader/{userId}
            function changeLeaderClub() {
                $.ajax({
                    url: contextPath + "/clubs/" + $(this).data("club") + "/leader/" + $(this).data("user"),
                    type: 'PUT',
                    success: function (data, textStatus, jqXHR) {
//                        alert("funciono!");
                        location.reload();
                    }
                });
            }
            
            ///{id}/user/{userId}
            function banUserClub() {
                $.ajax({
                    url: contextPath + "/clubs/" + $(this).data("club") + "/user/" + $(this).data("user"),
                    type: 'DELETE',
                    success: function (data, textStatus, jqXHR) {
//                        alert("funciono!");
                        location.reload();
                    }
                });
            }

            $(function () {
                $('#btnDeleteClub').on("click", deleteClub);
                $('.change-leader').on("click", changeLeaderClub);
                $('.ban-user').on("click", banUserClub);
            });
        </script>
    </body>
</html>
