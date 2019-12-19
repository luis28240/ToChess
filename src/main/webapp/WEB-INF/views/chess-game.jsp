<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, user-scalable=no">
        <title>JSP Page</title>
        <link rel="stylesheet"
              href="https://unpkg.com/@chrisoakman/chessboardjs@1.0.0/dist/chessboard-1.0.0.min.css"
              integrity="sha384-q94+BZtLrkL1/ohfjR8c6L+A6qzNH9R2hBLwyoAfu3i/WCvQjzL2RQJ3uNHDISdU"
              crossorigin="anonymous">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
        <style>
            .highlight-white {
                -webkit-box-shadow: inset 0 0 3px 3px yellow;
                -moz-box-shadow: inset 0 0 3px 3px yellow;
                box-shadow: inset 0 0 3px 3px yellow;  
            }
            .highlight-black {
                -webkit-box-shadow: inset 0 0 3px 3px blue;
                -moz-box-shadow: inset 0 0 3px 3px blue;
                box-shadow: inset 0 0 3px 3px blue;  
            }

            #circleTurn{
                height: 25px;
                width: 25px;
                border: 1px solid black;
                border-radius: 50%;
            }

            .dot-black {
                background-color: black;
            }
            .dot-white {
                background-color: white;
            }
            #chessBoard{
                position: relative;
                z-index: 0;
            }
        </style>
    </head>
    <body>
        <%@include file="../includes/header.jsp" %>
        <main class="container">
            <section class="row">
                <h1>${type == "black" ? game.whitePlayer.username : game.blackPlayer.username}</h1>
            </section>
            <section class="row">
                <section class="col-12 col-md-8">
                    <div id="chessBoard"
                         data-themePath="<c:url value='/src/img/chesspieces/wikipedia/{piece}.png'/>">
                    </div>

                </section>
                <section class="col-12 col-md">
                    <div class="row">
                        <div class="dot-${type}" id="circleTurn"></div>
                    </div>
                    <div class="row">
                        <div id="currentPGN" class="w-100"></div>
                    </div>
                </section>
            </section>
            <section class="row">
                <h1>${type == "black" ? game.blackPlayer.username : game.whitePlayer.username}</h1>
            </section>
        </main>

        <!--Modal For Piece selection-->
        <div class="modal fade" id="selPieceModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-body container-fluid">
                        <c:set var="charColor" value="${fn:substring(type, 0, 1)}" />
                        <c:set var="charColor" value='${charColor != "s" ? charColor : "w"}'/>
                        <div class="row justify-content-around">
                            <img src="<c:url value='/src/img/chesspieces/wikipedia/${charColor}N.png'/>" class="btnSelPiece" value="n"/>
                            <img src="<c:url value='/src/img/chesspieces/wikipedia/${charColor}B.png'/>" class="btnSelPiece" value="b"/>
                            <img src="<c:url value='/src/img/chesspieces/wikipedia/${charColor}R.png'/>" class="btnSelPiece" value="r"/>
                            <img src="<c:url value='/src/img/chesspieces/wikipedia/${charColor}Q.png'/>" class="btnSelPiece" value="q"/>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <script type="text/javascript" src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/sockjs-client@1/dist/sockjs.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
        <!--<script src="https://cdn.jsdelivr.net/npm/webstomp-client@1.2.6/dist/webstomp.min.js"></script>-->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>
        <script src="https://unpkg.com/@chrisoakman/chessboardjs@1.0.0/dist/chessboard-1.0.0.min.js"
                integrity="sha384-8Vi8VHwn3vjQ9eUHUxex3JSN/NFqUg3QbPyX8kWyb93+8AC/pPWTzj+nHtbC5bxD"
        crossorigin="anonymous"></script>
        <!--<script src="https://cdnjs.cloudflare.com/ajax/libs/chess.js/0.10.2/chess.js"></script>-->
        <script src="<c:url value='/src/js/chess.js'/>"></script>
        <script src="<c:url value='/src/js/webSocketConnection.js'/>"></script>
        <script>
            var circleWhitePath = '<c:url value='/src/img/icons/matchs/circle-white.svg'/>';
            var circleBlackPath = '<c:url value='/src/img/icons/matchs/circle-black.svg'/>';
            const chessType = '${type}';
            const gameId = '${game.id}';
            const gameFen = '${game.fen}';
            const gamePGN = '${game.pgn}';
        </script>
        <script src="<c:url value='/src/js/chess-game.js'/>"></script>
    </body>
</html>