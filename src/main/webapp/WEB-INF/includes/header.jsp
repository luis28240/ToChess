<header class="container">
    <section class="row justify-content-center">
        <div class="col-8">
            <h1>Hola, ${sessionScope.user.username}</h1>
        </div>
        <div class="col">
            <c:choose>
                <c:when test="${sessionScope.user.anonymous}">
                    <a role="button" class="btn btn-warning float-right mt-2 ml-2" href="<c:url value='/register'/>">Registrarse</a>
                    <a role="button"  class="btn btn-success float-right mt-2" href="<c:url value='/login'/>">Ingresar</a>
                </c:when>
                <c:otherwise>
                    <a role="button" class="btn btn-danger float-right mt-2 ml-2" href="<c:url value='/logout'/>">Cerrar sesión</a>
                    <a role="button" class="btn btn-primary float-right mt-2" href="#perfil">Perfil</a>
                </c:otherwise>
            </c:choose>
        </div>
    </section>
    <section class="row">
        <a role="button" class="btn btn-link" href="<c:url value='/user/${sessionScope.user.username}'/>">Torneos</a>
        <a role="button" class="btn btn-link" href="#CreateGame">Crear partida</a>
        <!--<a role="button" class="btn btn-link" href="#">Crear partida</a>-->
    </section>
    <hr class="w-100"/>
</header>