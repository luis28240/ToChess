<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, user-scalable=no">
        <title>Register</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">

    </head>
    <body data-url="${pageContext.request.contextPath}">
        <main class="container">
            <header class="row">
                <h1>Registro</h1>
            </header>
            <form action="<c:url value='/users'/>" method="POST">
                <div class="card">
                    <div class="card-body">
                        <section class="form-group">
                            <label for="username">Usuario:</label>
                            <input type="text" class="form-control" 
                                   id="username" name="username" 
                                   placeholder="usuario1234" required/>
                        </section>
                        <section class="form-group">
                            <label for="username">Email:</label>
                            <input type="email" class="form-control"
                                   id="email" name="email" 
                                   placeholder="name@example.com" required />
                        </section>
                        <section class="form-group">
                            <label for="password">Contraseña:</label>
                            <input type="password" class="form-control" 
                                   id="password" name="password" 
                                   placeholder="Contraseña..." required/>
                        </section>
                        <section class="form-group">
                            <label for="password">Confirmar contraseña:</label>
                            <input type="password" class="form-control" 
                                   id="password" name="confirmedPassword" 
                                   placeholder="Repite contraseña..." required/>
                        </section>
                        <button type="submit" class="btn btn-primary">Registrarse</button>
                    </div>
                </div>
            </form>
        </main>
        <script type="text/javascript" src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
        <script>

            var contextUrl = $('body').data("url");

            var verifyUsername = () => {
                
                var url = contextUrl+"/users/"+$('#username').val();
                
                //Lets verify the username is available
                $.get(url, {}, (data) => {
                    console.log(`data: \${data} | bool-data: \${!!data}`);
                });
            };

            $(function(){
               $('#username').on("keyup change", verifyUsername);
            });

        </script>
    </body>
</html>
