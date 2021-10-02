package redes.JogoDePerguntas.UDP

import redes.JogoDePerguntas.Game.GameMode
import redes.JogoDePerguntas.Game.Game
import redes.JogoDePerguntas.Game.Player

class UDPServer {
    static Map<Integer, Player> playerList
    def static Server(){
        playerList = new HashMap<>()
        def socketServer = new DatagramSocket(5000)
        def buffer = (' ' * 4096) as byte[]
        Game game = new Game()
        game.Menu()
        while(true) {
            def incoming = new DatagramPacket(buffer, buffer.length)
            socketServer.receive(incoming)
            def s = new String(incoming.data, 0, incoming.length)
            String reply = Response(s, game, incoming.port)
            def outgoing = new DatagramPacket(reply.bytes, reply.size(),
                    incoming.address, incoming.port);
            socketServer.send(outgoing)
        }
    }

    def static Response(String s, Game game, Integer port){ println(port)
        switch (s){
            case "0": return game.Menu()
            case "First Contact":  playerList.put(port,new Player())
                return Game.menu;
                break;
            case "1": playerList.get(port).mode = GameMode.FACIL
                game.Start(playerList.get(port))
                return "Jogo facil iniciado \n\n" + game.getQuestion(playerList.get(port))
                break;
            case "2":
                playerList.get(port).mode = GameMode.MEDIO
                game.Start(playerList.get(port))
                return "Jogo medio iniciado \n\n" + game.getQuestion(playerList.get(port))
                break
            case "3": playerList.get(port).mode = GameMode.DIFICIL
                game.Start(playerList.get(port))
                return "Jogo dificil  iniciado \n\n" + game.getQuestion(playerList.get(port))
                break
            case "A": return game.VerifyAnswer("A",playerList.get(port)) +"\n\n" + game.getQuestion(playerList.get(port))
                break
            case "B": return game.VerifyAnswer("B",playerList.get(port)) +"\n\n" + game.getQuestion(playerList.get(port))
                break
            case "C": return game.VerifyAnswer("C",playerList.get(port)) +"\n\n" + game.getQuestion(playerList.get(port))
                break
            case "D": return game.VerifyAnswer("D",playerList.get(port)) +"\n\n" + game.getQuestion(playerList.get(port))
                break
            default: return "A resposta é invalida. Tente novamente"
        }
    }
}
