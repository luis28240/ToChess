<%--<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>--%>
<header class="container">
    <section class="row justify-content-center">
        <div class="col-8">
            <a class="h1" href="${pageContext.request.contextPath}">ToChess</a>
        </div>
        <div class="col">
            <c:choose>
                <c:when test="${sessionScope.user.anonymous}">
                    <!--${sessionScope.user.anonymous}-->
                    <a role="button" class="btn btn-warning float-right mt-2 ml-2" href="<c:url value='/register'/>">Registrarse</a>
                    <a role="button"  class="btn btn-success float-right mt-2" href="<c:url value='/login'/>">Ingresar</a>
                </c:when>
                <c:otherwise>
                    <a role="button" class="btn btn-danger float-right mt-2 ml-2" href="<c:url value='/logout'/>">Cerrar sesión</a>
                    <a role="button" class="btn btn-primary float-right mt-2" href="<c:url value='/user/${sessionScope.user.username}'/>">Perfil</a>
                </c:otherwise>
            </c:choose>
        </div>
    </section>
    <section class="row">
        <h1>Hola, ${sessionScope.user.username}</h1>
    </section>
    <section class="row">
        <a role="button" class="btn btn-link" href="<c:url value='/user/${sessionScope.user.username}'/>">Torneos</a>
        <a role="button" class="btn btn-link" href="<c:url value='/clubs'/>">Clubs</a>
        <!--<a role="button" class="btn btn-link" data-toggle="modal" data-target="#modalTest" href="#">Crear partida</a>-->
        <!--<a role="button" class="btn btn-link" href="#">Crear partida</a>-->
    </section>
    <hr class="w-100"/>

    <section class="modal fade" id="modalTest" tabindex="-1" role="dialog" aria-labelledby="modalTestTitle" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">

                <div class="modal-header" id="modalTestTitle">
                    <h5 class="modal-title">Modal Title</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>

                <div class="modal-body">
                    <section class="container">
                        <select class="form-control">
                            <option>Option1</option>
                            <option>Option2</option>
                            <option>Option3</option>
                        </select>
                    </section>
                </div>

                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                </div>

            </div>
        </div>
    </section>

</header>