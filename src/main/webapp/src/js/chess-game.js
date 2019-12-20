/* global Stomp, gameId, chessType, gameFen, gamePGN */

//(function () {//Creando una conexion

"use strict";
//Creando un nuevo juego
var chessGame;
var chessBoard;
var stompClient;
var kingSquare;
var fromPromotion;
var toPromotion;
var isInvalidPos;

//For IE11 support
Number.isInteger = Number.isInteger || function (value) {
    return typeof value === "number" &&
            isFinite(value) &&
            Math.floor(value) === value;
};

function conectarWebSocket() {
    var socket = SockJS('/ToChess/toChess-games');
    stompClient = Stomp.over(socket);
    stompClient.debug = null;
    stompClient.connect({}, function (frame) {
//        console.log('Conectado: ' + frame);
        stompClient.subscribe('/topic/game/play/' + gameId, moverAlRecibirWebSocket);
    });
}

function moverAlRecibirWebSocket(data) {
    var currentGame = JSON.parse(data.body);
//    console.log("PGN recibido: " + currentGame.pgn);
    var fromSquare = currentGame.lastMove.from;
    var toSquare = currentGame.lastMove.to;
    var promotionSquare = currentGame.lastMove.promotion;

    //Cambiar colores del ultimo movimiento

    chessGame.move({from: fromSquare, to: toSquare, promotion: promotionSquare});
    updateBoard();
}

function getCaptured() {
    var history = chessGame.history({verbose: true});
    var initial = {w: {p: 0, n: 0, b: 0, r: 0, q: 0},
        b: {p: 0, n: 0, b: 0, r: 0, q: 0}};

    var captured = history.reduce(function (acc, move) {
        if ('captured' in move) {
            var piece = move.captured;
            // switch colors since the history stores the color of the player doing the
            // capturing, not the color of the captured piece
            var color = move.color === 'w' ? 'b' : 'w';
            acc[color][piece] += 1;
            return acc;
        } else {
            return acc;
        }
    }, initial);
    return captured;
}

function enviarJugada(from, to, promotion) {
    var newFen = chessGame.fen();
    var newPGN = chessGame.pgn();

    var jugada = {
        fen: newFen,
        pgn: newPGN,
        from: from,
        to: to,
        promotion: promotion
    };
    stompClient.send('/app/game/play/' + gameId, {}, JSON.stringify(jugada));
}

function validateMove(source, target, piece) {
    if (piece.charAt(1) === "P" && (target.charAt(1) === "8" || target.charAt(1) === "1")) {
        $('#selPieceModal').modal("show");
        fromPromotion = source;
        toPromotion = target;
    }
    var move = chessGame.move({from: source, to: target});
//                console.log(source + " -> " + target + ": " + JSON.stringify(move));
    if (move === null) {
        return 'snapback';
    }
    enviarJugada(source, target, piece);
}

function getPiecePosition(game, piece) {
    return [].concat.apply([], game.board()).map(function (p, index) {
        if (p !== null && p.type === piece.type && p.color === piece.color) {
            return index;
        }
    }).filter(Number.isInteger).map(function (piece_index) {
        const row = 'abcdefgh'[piece_index % 8];
        const column = Math.ceil((64 - piece_index) / 8);
        return row + column;
    });
}

function updateBoard() {
    isInvalidPos = false;
    chessBoard.position(chessGame.fen());

    if (chessGame.in_check()) {
        kingSquare = getPiecePosition(chessGame, {type: 'k', color: chessGame.turn()});
        $('.square-' + kingSquare).css({"background-color": "#e68d8d"});
    } else if (kingSquare) {
        $('.square-' + kingSquare).css({"background-color": ""});
    }

    //Change circle color
    if (chessGame.turn() === 'w') {
        $('#circleTurn').attr("class", "dot-white");
//                    $('#circleTurn').attr('src', circleWhitePath);
    } else {
        $('#circleTurn').attr("class", "dot-black");
//                    $('#circleTurn').attr('src', circleBlackPath);
    }

    //PGN Dashboard
    $('#currentPGN').empty();
    var gameHistory = chessGame.history();
    var auxChessGame = new Chess();
    var html = "";
    for (var i in gameHistory) {
        var move = gameHistory[i];
        auxChessGame.move(move);

        if (i % 2 === 0) {
//            if (i > 0) {
//                html += "</div>";
//                $('#currentPGN').append(html);
//                html = "";
//                console.log("Se ha cerrado row en " + i);
//            }
            html += "<div class='row m-0 p-0 pgn-row'>";
            html += `<div class='col-2 border text-monospace d-flex justify-content-center align-items-center text-muted pgn-row'>${(i / 2) + 1}</div>`;
        }
        var moveBtnHTML = `<div class='col${i % 2 !== 0 ? "" : "-5"} p-0'> <button class='btn-pgn w-100 rounded-0 pgn-row' data-last='${parseInt(i) === gameHistory.length - 1}' data-fen='${auxChessGame.fen()}'>${move}</button></div>`;
//        console.logC(`${i} -> ${gameHistory.length - 1}`);
        html += moveBtnHTML;
//        console.log(html);

//        if (i > 0 && (i % 2 !== 0 || parseInt(i) === gameHistory.length - 1)) {
        if (i % 2 !== 0 || parseInt(i) === gameHistory.length - 1) {
            html += "</div>";
            $('#currentPGN').append(html);
            html = "";
//            console.log("Se ha cerrado row en " + i);
        }

//        console.log("TOTAL ACTUAL: " + $('#currentPGN').html());
    }
    $('.btn-pgn').on("click", cambiarPosicion);
    $('#currentPGN').height($('div[class^=board]').height());
    putGraySquaresIfMate();
//                kingSquare = getPiecePosition(chessGame, {type: 'k', color: chessGame.turn()});
}

function cambiarPosicion() {
//    console.log($(this).data("fen"));
    chessBoard.position($(this).data("fen"));

    isInvalidPos = !($(this).data("last"));
}

function verificarAgarre(source, piece, position, orientation) {
    // do not pick up pieces if the game is over
    if (chessGame.game_over()) {
        return false;
    }

    if (isInvalidPos) {
        return false;
    }

    // only pick up pieces for the side to move
    switch (chessType) {
        case 'white':
            return piece.search(/^w/) !== -1;
        case 'black':
            return piece.search(/^b/) !== -1;
        case 'spectator':
            return false;
    }
}

function selectPromote() {
    var pieceSelected = $(this).attr("value");
    promote(fromPromotion, toPromotion, pieceSelected);
//    console.log("Has seleccionado: " + pieceSelected);
    $('#selPieceModal').modal('hide');
}

function promote(source, target, promotion) {
    var move = chessGame.move({from: source, to: target, promotion: promotion});
    if (move === null) {
        return 'snapback';
    }
    enviarJugada(source, target, promotion);
}

function putGraySquaresIfMate() {
    if (chessGame.game_over()) {
        $('div[class^=square]').css({
            "-webkit-filter": "grayscale(100%)",
            "filter": "grayscale(100%)"
        });
    }
}

$(function () {
    "use strict";
    chessGame = new Chess(gameFen);
    //Asignando la configuracion al board

    //Generando toda la configuracion del tablero
    var boardConfig = {
        position: chessGame.fen(),
        draggable: true,
        pieceTheme: $('#chessBoard').data('themepath'),
        orientation: chessType !== 'spectator' ? chessType : 'white',
//            showNotation: false,
        onDragStart: verificarAgarre,
        onDrop: validateMove,
        onSnapEnd: updateBoard
    };

    chessBoard = Chessboard('chessBoard', boardConfig);
    chessGame.load_pgn(gamePGN);
    $(window).resize(() => {
        chessBoard.resize();
        putGraySquaresIfMate();
        $('#currentPGN').height($('div[class^=board]').height());
    });
    //Conectar
    conectarWebSocket();
    updateBoard();
    $('.btnSelPiece').on('click', selectPromote);
    putGraySquaresIfMate();
});
//})();