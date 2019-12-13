/* global contextPath */

(function () {
    "use strict";
    const MAX_TIMEOUT = 15000;
    const INTERVAL_TIME = 1000;
    var socket = new SockJS('/ToChess/match-handler');

    var comenzarABuscar = () => {
        socket.send('match');
        $('#loadingCircle').prop("hidden", false);
    };

    var irAPartida = (data) => {
        var gameId = data.data;
        window.location.href = "http://localhost:8088/ToChess/chess-game/" + gameId;
    };

    $(function () {
        $('#buscar').on('click', comenzarABuscar);
        $.ajax({
            url: "games",
            success: (gameList) => {
                $('#gameList').empty();
                for (var i in gameList) {
                    var game = gameList[i];
                    var html = 
                            `<div class="row justify-content-center mt-4">
                                <div class="col-5">
                                    <a href="http://localhost:8088/ToChess/chess-game/${game.id}">
                                        Blancas: ${game.whitePlayer.username} <br/>
                                        Negras: ${game.blackPlayer.username}
                                    </a>
                                </div>
                                <div class="col">
                                    <a href="http://localhost:8088/ToChess/chess-game/${game.id}">
                                        <div style="width:250px;" class="col" data-fen="${game.fen}" id="chessboardPreview${i}"></div>
                                    </a>
                                </div>
                            </div>`;
                    console.log(html);
                    $('#gameList').append(html);
                    
                    var config = {
                        position: game.fen,
                        showNotation: false,
                        pieceTheme: `${contextPath}/src/img/chesspieces/wikipedia/{piece}.png`
                    };
                    
                    new ChessBoard(`chessboardPreview${i}`, config);
                }
            }
        });
        socket.onopen = () => {
            console.log("Conectando al servidor");
            $('#buscar').prop("disabled", false);
        };
        socket.onmessage = irAPartida;
    });
})();