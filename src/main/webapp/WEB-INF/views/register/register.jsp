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
                            <small class="text-danger" id="errorUsername" style="display: none">Este usuario ya existe</small>
                        </section>
                        <section class="form-group">
                            <label for="username">Email:</label>
                            <input type="email" class="form-control"
                                   id="email" name="email" 
                                   placeholder="name@example.com" required />
                            <small class="text-danger" id="errorEmail" style="display: none">Este email ya tiene un usuario</small>
                            <small class="text-danger" id="errorEmailFormat" style="display: none">Este no tiene un formato valido</small>
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
                                   id="confirmedPassword" name="confirmedPassword" 
                                   placeholder="Repite contraseña..." required/>
                        </section>
                        <button type="submit" class="btn btn-primary" id="btnRegistrar">Registrarse</button>
                    </div>
                </div>
            </form>
        </main>
        <script type="text/javascript" src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
        <script>
            "use strict";
            var invalido;
            var contextUrl = $('body').data("url");
            var validateUsername = () => {
                if ($('#username').val()) {
                    var url = contextUrl + "/users/" + $('#username').val().trim();

                    //Lets verify the username is available
                    $.ajax({
                        url: url,
                        async: false,
                        success: (data) => {
                            invalido = !!data;
                            if (invalido) {
                                $('#errorUsername').show();
                            } else {
                                $('#errorUsername').hide();
                            }
                            return invalido;
                        }
                    });
                }
            };

            var validateEmail = () => {
                if ($('input#email').val()) {
                    var emailRegexp = new RegExp(/^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/);
                    var email = $('input#email').val().trim();
                    var url = contextUrl + "/emails/" + email;
                    if (emailRegexp.test(email)) {
                        $('#errorEmailFormat').hide();
                        //Lets verify the username is available
                        $.ajax({
                            url: url,
                            async: false,
                            success: (data) => {
//                        console.log("Algo" + data);
                            invalido = !!data;
                            if (invalido) {
                                $('#errorEmail').show();
                            } else {
                                $('#errorEmail').hide();
                            }
                            return invalido;
                            }
                        });
                    } else {
                        $('#errorEmailFormat').show();
                        return true;
                    }
                }
            };

            var validateForm = () => {
                var a = validateUsername();
                var b = validateEmail();
                
                console.log(`a - \${a} b - \${b}`);
                
                $('#btnRegistrar').prop("disabled", invalido);
            };

            $(function () {
//                $('#errorUsername').show();

                //Validate when change any input
//                $('input#username').on("keyup change", validateUsername);
//                $('input#email').on("keyup change", validateEmail);
                $('input#username').on({
                    keydown: function (e) {
                        if (e.which === 32)
                            return false;
                    },
                    change: function () {
                        this.value = this.value.replace(/\s/g, "");
                    }
                });

                $('input').on("keyup change", validateForm);
            });

        </script>
    </body>
</html>
