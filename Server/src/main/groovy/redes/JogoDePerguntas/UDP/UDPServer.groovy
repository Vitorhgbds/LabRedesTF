package redes.JogoDePerguntas.UDP
import redes.JogoDePerguntas.Server
import redes.JogoDePerguntas.Game.GameMode
import redes.JogoDePerguntas.Game.Game
import redes.JogoDePerguntas.Game.Player

class UDPServer {
    static Map<Integer, Player> playerList
    static volatile Map<Integer,Integer> waitingResponse
    static volatile Map<Integer,Integer> waitingResponseCount
    static volatile int count
    static Map<Integer,String> lastResponses
    def static socketServer

    /*
        Servidor
    */
    def static Server(){
        socketServer = new DatagramSocket(5000)
        lastResponses = new HashMap<>()
        // aqui ele reenvia a mensagem (ou sabe quando reenviar)
        waitingResponse = new HashMap<>()
        // aqui ele ta contando quantas mensagens já reenviou
        waitingResponseCount = new HashMap<>()
        def buffer = (' ' * 4096) as byte[]
        new Thread(watch).start()
        
        // da pra remover
        playerList = new HashMap<>()
        Game game = new Game()
        game.Menu()
        
        
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
    /*
        Thread para verificar se o cliente nao respondeu
    */
     static Runnable watch = new Runnable() {

        @Override
        void run() {
            while (true){
                try{
                    Thread.sleep(1000)
                    waitingResponse.each {k,value ->
                        if(value<1) {
                            count = waitingResponseCount.get(k)
                            if (count<=2) {
                                count++
                                waitingResponseCount.put(k, count)
                                sendResponseAgain(k)
                                waitingResponse.put(k, Server.properties."response.time" as int)
                            } else {
                                println("$k Removido por não responder após 3 tentativas")
                                waitingResponseCount.remove(k)
                                waitingResponse.remove(k)
                            }
                        } else {
                            value--
                            println(k + " "+ value)
                            waitingResponse.put(k,value)
                        }
                    }
                }catch(ignored){}
            }
        }
    }

    /*
        Envia a ultima mensagem do servidor novamente
    */
    static def sendResponseAgain(int port){
        def reply = lastResponses.get(port)
        def outgoing = new DatagramPacket(reply.bytes, reply.size(),
                InetAddress.getByName("localhost") , port);
        socketServer.send(outgoing)
    }


    /*
        Gerencia as respostas recebidas pelo cliente
    */
    def static Response(String s, Game game, Integer port){ println(port)
        waitingResponse.put(port, Server.properties."response.time" as int)
        waitingResponseCount.put(port, 0)
        switch (s){

        /*
            Mensagem de resposta do cliente
        */
            case "Received": println(port + " Recebeu a mensagem")
                waitingResponse.remove(port)
                return "Received"
                break;
            case "First Contact":
                playerList.put(port,new Player())
                return Game.menu;
                break;
        //---------------- da pra remover ----------------//
        /*
            Mensagem que retorna o menu
        */
            case "0": return game.Menu()
                break
        /*
            Mensagens que definem o modo de jogo
        */
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
        /*
            Mensagens que mostram as alternativas ecolhidas
        */
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
