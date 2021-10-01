package redes.JogoDePerguntas.UDP

import redes.JogoDePerguntas.Game.GameMode
import redes.JogoDePerguntas.Game.Game
class UDPServer {

    def static Server(){
        def socketServer = new DatagramSocket(5000)
        def buffer = (' ' * 4096) as byte[]
        Game.gameMode = GameMode.FACIL
        Game.Start()
        Game.Menu()
        while(true) {
            def incoming = new DatagramPacket(buffer, buffer.length)
            socketServer.receive(incoming)
            def s = new String(incoming.data, 0, incoming.length)
            String reply = Response(s)
            def outgoing = new DatagramPacket(reply.bytes, reply.size(),
                    incoming.address, incoming.port);
            socketServer.send(outgoing)
        }
    }

    def static Response(String s){
        switch (s){
            case "0": return Game.menu;
            case "First Contact": return Game.menu;
                break;
            case "1": Game.gameMode = GameMode.FACIL
                Game.Start()
                return "Jogo facil iniciado \n\n" + Game.getQuestion()
                break;
            case "2":
                Game.gameMode = GameMode.MEDIO
                Game.Start()
                return "Jogo medio iniciado \n\n" + Game.getQuestion()
                break
            case "3": Game.gameMode = GameMode.DIFICIL
                Game.Start()
                return "Jogo dificil  iniciado \n\n" + Game.getQuestion()
                break
            case "A": return Game.VerifyAnswer("A") +"\n\n" + Game.getQuestion()
                break
            case "B": return Game.VerifyAnswer("B") +"\n\n" + Game.getQuestion()
                break
            case "C": return Game.VerifyAnswer("C") +"\n\n" + Game.getQuestion()
                break
            case "D": return Game.VerifyAnswer("D") +"\n\n" + Game.getQuestion()
                break
            case "-1": try{
                System.exit(0)
                } catch(ignored){

                }
            default: return "A resposta é invalida. Tente novamente"
        }
    }
}
