package redes.tf.udp
/*
       Servidor
   */

class UDPServer {
    private static final Integer BUFFER_SIZE = 10
    private DatagramSocket serverSocket
    private MessageReceivedHandler messageReceivedHandler

    UDPServer() {
        serverSocket = new DatagramSocket(5000)
        messageReceivedHandler = new MessageReceivedHandler()
    }

    void startExecution() {
        byte[] buffer = new byte[BUFFER_SIZE]
        while (true) {
            DatagramPacket incoming = new DatagramPacket(buffer, buffer.length)
            println "Will Start listing\n\n"
            serverSocket.receive(incoming)
            Packet packet = new Packet(incoming.data)
            println "Received Packet $packet"
            Packet reply = handleResponse(packet, incoming.port)
            byte[] replyBytes = reply.toBytes()
            DatagramPacket outgoing = new DatagramPacket(replyBytes, replyBytes.size(), incoming.address, incoming.port)
            println "Sending Response"
            serverSocket.send(outgoing)
        }
    }

/*
    Gerencia as respostas recebidas pelo cliente

*/

    private Packet handleResponse(Packet response, Integer port) {
        if (!messageReceivedHandler.hasClient(port)) {
            messageReceivedHandler.saveClient(port, Integer.parseInt(response.stringData))
            println "Client does not exisits, will responde OK"
            return Packet.buildPacketWithResponse('OK')
        } else {
            Optional<Integer> nextPack = messageReceivedHandler.receivePacketAndGenerateNext(port, response)
            if (messageReceivedHandler.fileIsAlreadyAllSent(port) && !nextPack.isPresent()) {
                println "File is complete"
                saveFile(port)
                messageReceivedHandler.cleanClient(port)
                return Packet.buildPacketWithResponse("DONE")
            } else {
                println "Ack and ask next file part"
                return Packet.buildAckPacket(response.messageId, nextPack.get())
            }
        }
    }

    private void saveFile(int clientPort) {
        println "RECEBI O ARQUIVO DO $clientPort"
    }
}
