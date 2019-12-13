/* global webstomp */

var stompClient;

function conectarWebSocket() {
    var socket = SockJS('/ToChess/toChess-games');
    stompClient = webstomp.over(socket, ['v10.stomp', 'v11.stomp', 'v12.stomp']);
    stompClient.connect({}, function(frame){
        console.log('Conectado: ' + frame);
        
        stompClient.subscribe('/topic/game/update', function(data){
            console.log(data.body.fen);
        });
        
    });
}

function enviarFen(fen){
    var game = {"fen" : fen};
    stompClient.send('/app/game/send', game, {});
}