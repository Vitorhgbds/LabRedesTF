package redes.tf.udp

/**
 Thread para verificar se o cliente nao respondeu
 */
class MessageSender implements Runnable {
    private static final Integer SERVER_PORT = 5000
    private static final Integer RESPONSE_TIME = 10
    private Map<Integer, Integer> waitingResponse
//    private Map<Integer, Integer> waitingResponseCount
    private Map<Integer, Packet> lastMessages
    private DatagramSocket serverSocket

    MessageSender(DatagramSocket serverSocket) {
        waitingResponse = [:]
        //      waitingResponseCount = [:]
        lastMessages = [:]
        this.serverSocket = serverSocket
    }

    void sendMessage(Packet packet) {
        DatagramPacket packetToSend = this.buildPacketToServer(packet.toBytes())
        println "Sending Message"
        serverSocket.send(packetToSend)
        lastMessages.put(packet.messageId, packet)
        waitingResponse.put(packet.messageId, RESPONSE_TIME)
    }

    void removeRetransmit(int messageIdToRemove) {
        lastMessages.remove(messageIdToRemove)
    }

    @Override
    void run() {
        while (true) {
            try {
                Thread.sleep(1000)
                waitingResponse.each { messageId, waitingCountTime ->
                    if (waitingCountTime < 1) {
                        //waitingResponseCount.put(messageId, count)
                        sendResponseAgain(messageId)
                        waitingResponse.put(messageId, RESPONSE_TIME)
                    } else {
                        waitingCountTime--
                        println(messageId + " " + waitingCountTime)
                        waitingResponse.put(messageId, waitingCountTime)
                    }
                }
            } catch (ignored) {
            }
        }
    }


    /**
     Envia a ultima mensagem do servidor novamente
     */

    private void sendResponseAgain(int messageId) {
        Packet reply = lastMessages.get(messageId)
        byte[] dataToSend = reply.toBytes()
        DatagramPacket outgoing = buildPacketToServer(dataToSend)
        serverSocket.send(outgoing)
    }

    private DatagramPacket buildPacketToServer(byte[] data) {
        return new DatagramPacket(data, data.size(), InetAddress.getByName('localhost'), SERVER_PORT)
    }
}
