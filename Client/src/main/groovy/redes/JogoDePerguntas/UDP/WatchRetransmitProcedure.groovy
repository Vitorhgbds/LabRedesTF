package redes.JogoDePerguntas.UDP

/**
    Thread para verificar se o cliente nao respondeu
*/
class WatchRetransmitProcedure implements Runnable {
    private static final Integer RESPONSE_TIME = 10
    private Map<Integer, Integer> waitingResponse
    private Map<Integer, Integer> waitingResponseCount
    private Map<Integer, String> lastResponses
    private DatagramSocket serverSocket

    WatchRetransmitProcedure(DatagramSocket serverSocket) {
        waitingResponse = [:]
        waitingResponseCount = [:]
        lastResponses = [:]
        this.serverSocket = serverSocket
    }

    @Override
    void run() {
        while (true) {
            try {
                Thread.sleep(1000)
                waitingResponse.each { clientPort, waitingCountTime ->
                    if (waitingCountTime < 1) {
                        int count = waitingResponseCount.get(clientPort)
                        if (count <= 2) {
                            count++
                            waitingResponseCount.put(clientPort, count)
                            sendResponseAgain(clientPort)
                            waitingResponse.put(clientPort, RESPONSE_TIME)
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


    /**
        Envia a ultima mensagem do servidor novamente
    */

    private void sendResponseAgain(int port) {
        String reply = lastResponses.get(port)
        DatagramPacket outgoing = new DatagramPacket(reply.bytes, reply.size(),
            InetAddress.getByName("localhost"), port)
        serverSocket.send(outgoing)
    }
}
