package redes.JogoDePerguntas.UDP

import redes.JogoDePerguntas.Server
import redes.JogoDePerguntas.Game.Player

class UDPServer {
    static Map<Integer, Player> playerList
    static volatile Map<Integer, Integer> waitingResponse
    static volatile Map<Integer, Integer> waitingResponseCount
    static volatile int count
    static Map<Integer, String> lastResponses
    static DatagramSocket socketServer

    /*
        Servidor
    */

    static void server() {
        socketServer = new DatagramSocket(5000)
        lastResponses = new HashMap<>()
        // aqui ele reenvia a mensagem (ou sabe quando reenviar)
        waitingResponse = new HashMap<>()
        // aqui ele ta contando quantas mensagens já reenviou
        waitingResponseCount = new HashMap<>()
        byte[] buffer = (' ' * 4096) as byte[]
        new Thread(watch).start()

        // da pra remover
        playerList = new HashMap<>()


        while (true) {
            DatagramPacket incoming = new DatagramPacket(buffer, buffer.length)
            socketServer.receive(incoming)
            String clientMessage = new String(incoming.data, 0, incoming.length)
            String reply = handleResponse(clientMessage, incoming.port)
            if (reply != "Received") {
                lastResponses.put(incoming.port, reply)
                DatagramPacket outgoing = new DatagramPacket(reply.bytes, reply.size(),
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
            while (true) {
                try {
                    Thread.sleep(1000)
                    waitingResponse.each { clientPort, waitingCountTime ->
                        if (waitingCountTime < 1) {
                            count = waitingResponseCount.get(clientPort)
                            if (count <= 2) {
                                count++
                                waitingResponseCount.put(clientPort, count)
                                sendResponseAgain(clientPort)
                                waitingResponse.put(clientPort, Server.properties."response.time" as int)
                            } else {
                                println("$clientPort Removido por não responder após 3 tentativas")
                                waitingResponseCount.remove(clientPort)
                                waitingResponse.remove(clientPort)
                            }
                        } else {
                            waitingCountTime--
                            println(clientPort + " " + waitingCountTime)
                            waitingResponse.put(clientPort, waitingCountTime)
                        }
                    }
                } catch (ignored) {
                }
            }
        }
    }

    /*
        Envia a ultima mensagem do servidor novamente
    */

    static def sendResponseAgain(int port) {
        String reply = lastResponses.get(port)
        DatagramPacket outgoing = new DatagramPacket(reply.bytes, reply.size(),
                InetAddress.getByName("localhost"), port)
        socketServer.send(outgoing)
    }


    /*
        Gerencia as respostas recebidas pelo cliente
    */

    static String handleResponse(String response, Integer port) {
        return "OK"
    }
}
