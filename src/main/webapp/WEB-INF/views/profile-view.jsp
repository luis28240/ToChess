<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>${userProfile.username}</title>
        <%@include file="/WEB-INF/includes/commons/web_styles.jsp" %>
    </head>
    <body>
        <%@include file="/WEB-INF/includes/header.jsp" %>
        <main class="container">
            <section class='row'>
                <div class='col-12 col-md'>
                    Email: ${userProfile.email}
                </div>
                <div class='col-12 col-md'>
                    Classical: ${userProfile.classical}
                </div>
            </section>
        </main>
                
        <%@include file="/WEB-INF/includes/commons/web_scripts.jsp" %>
    </body>
</html>
