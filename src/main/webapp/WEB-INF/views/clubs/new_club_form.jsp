<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create Club form</title>
        <%@include file="/WEB-INF/includes/commons/web_styles.jsp" %>
    </head>
    <body>
        <%@include file="/WEB-INF/includes/header.jsp" %>
        <main class="container">
            <section class="row">
                <div class="col">

                    <form method="POST">
                        <section class="card">
                            <div class="card-header">
                                <h1>Creación de club</h1>
                            </div>

                            <div class="card-body">
                                
                                <div class="form-group">
                                    <label for="txtNombre">Nombre:</label>
                                    <input class="form-control" name="txtName" id="txtNombre" type="text"/>
                                </div>
                                
                                <div class="form-group">
                                    <label for="txtNombre">Máximo usuarios:</label>
                                    <input class="form-control" name="txtMaxUsers" id="txtMaxUsers" type="number"/>
                                </div>

                                <button type="submit" class="btn btn-success">Crear</button>
                            </div>
                        </section>
                    </form>

                </div>
            </section>
        </main>
        <%@include file="/WEB-INF/includes/commons/web_scripts.jsp" %>
    </body>
</html>
