package redes.tf.udp

import redes.tf.udp.Packet

import java.util.function.Consumer

/**
 Thread para verificar se o cliente nao respondeu
 */
class MessageSender implements Runnable {
    private static final Integer SERVER_PORT = 5000
    private static final Integer RESPONSE_TIME = 10
    private Map<Integer, Integer> waitingResponse
    private Map<Integer, Packet> lastMessages
    private DatagramSocket serverSocket
    private boolean onGoing
    private Consumer<Integer> onRetransmit

    MessageSender(DatagramSocket serverSocket, Consumer<Integer> onRetransmit) {
        waitingResponse = [:]
        //      waitingResponseCount = [:]
        lastMessages = [:]
        onGoing = true
        this.onRetransmit = onRetransmit
        this.serverSocket = serverSocket
    }

    void sendMessage(DatagramPacket packet) {
        println "Sending Message"
        serverSocket.send(packet)
        Packet packet1 = new Packet(packet.data)
        lastMessages.put(packet1.messageId, packet1)
        waitingResponse.put(packet1.messageId, RESPONSE_TIME)
    }

    void removeRetransmit(int messageIdToRemove) {
        lastMessages.remove(messageIdToRemove)
    }

    void stopsWatch() {
        onGoing = false
    }

    @Override
    void run() {
        while (onGoing) {
            try {
                Thread.sleep(1000)
                waitingResponse.each { messageId, waitingCountTime ->
                    if (waitingCountTime < 1) {
                        sendResponseAgain(messageId)
                        waitingResponse.put(messageId, RESPONSE_TIME)
                        onRetransmit.accept(messageId)
                    } else {
                        waitingCountTime--
                        println("Retransmit count for message: $messageId - waiting response: $waitingCountTime")
                        waitingResponse.put(messageId, waitingCountTime)
                    }
                }
            } catch (ignored) {
            }
        }
        println "Retransmit messageSender watch loop stops"
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
