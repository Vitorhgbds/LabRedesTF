package redes.JogoDePerguntas.UDP
import redes.JogoDePerguntas.Server
import redes.JogoDePerguntas.Game.GameMode
import redes.JogoDePerguntas.Game.Game
import redes.JogoDePerguntas.Game.Player

class UDPServer {
    static Map<Integer, Player> playerList
    static volatile Map<Integer,Integer> waitingResponse
    static Map<Integer,String> lastResponses
    def static socketServer

    def static Server(){
        playerList = new HashMap<>()
        waitingResponse = new HashMap<>()
        lastResponses = new HashMap<>()
        socketServer = new DatagramSocket(5000)
        def buffer = (' ' * 4096) as byte[]
        Game game = new Game()
        game.Menu()
        new Thread(watch).start()
        while(true) {
            def incoming = new DatagramPacket(buffer, buffer.length)
            socketServer.receive(incoming)
            def s = new String(incoming.data, 0, incoming.length)
            String reply = Response(s, game, incoming.port)
            if(!reply.equals("Received"))
            {
                lastResponses.put(incoming.port,reply)
                def outgoing = new DatagramPacket(reply.bytes, reply.size(),
                        incoming.address, incoming.port);
                socketServer.send(outgoing)
            }
        }
    }

     static Runnable watch = new Runnable() {

        @Override
        void run() {
            while (true){
                try{
                    Thread.sleep(1000)
                    waitingResponse.each {k,value ->
                        if(value<1) {
                            sendResponseAgain(k)
                            waitingResponse.put(k, Server.properties."response.time" as int)
                        } else {
                            value--
                            println(value)
                            waitingResponse.put(k,value)
                        }

                    }
                }catch(ignored){}
            }
        }
    }

    static def sendResponseAgain(int port){
        def reply = lastResponses.get(port)
        def outgoing = new DatagramPacket(reply.bytes, reply.size(),
                InetAddress.getByName("localhost") , port);
        socketServer.send(outgoing)
    }

    def static Response(String s, Game game, Integer port){ println(port)
        waitingResponse.put(port, Server.properties."response.time" as int)
        switch (s){
            case "Received": println(port + " Recebeu a mensagem")
                waitingResponse.remove(port)
                return "Received"
                break;
            case "0": return game.Menu()
            case "First Contact":
                playerList.put(port,new Player())
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
