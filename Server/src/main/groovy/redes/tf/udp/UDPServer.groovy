package redes.tf.udp
/*
       Servidor
   */

class UDPServer {
    private static final Integer BUFFER_SIZE = 100
    private MessageSender messageSender

    private DatagramSocket serverSocket
    private MessageReceivedHandler messageReceivedHandler

    UDPServer() {
        serverSocket = new DatagramSocket(5000)
        messageReceivedHandler = new MessageReceivedHandler()
        messageSender = new MessageSender(serverSocket, this.&onError)
    }

    void startExecution() {
        byte[] buffer = new byte[BUFFER_SIZE]
        new Thread(messageSender).start()
        while (true) {
            DatagramPacket incoming = new DatagramPacket(buffer, buffer.length)
            println "Will Start listing\n\n"
            serverSocket.receive(incoming)
            Packet packet = new Packet(incoming.data)
            println "Received Packet $packet"
            Packet reply = handleResponse(packet, incoming.port)
            byte[] replyBytes = reply.toBytes()
            DatagramPacket outgoing = new DatagramPacket(replyBytes, replyBytes.size(), incoming.address, incoming.port)
            messageSender.sendMessage(outgoing)
            println "Sending Response"
            buffer = new byte[BUFFER_SIZE]
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
                messageSender.removeRetransmit(response.messageId)
                return Packet.buildPacketWithResponse("DONE")
            } else {
                println "Ack and ask next file part ${nextPack.get()}"
                return Packet.buildAckPacket(response.messageId, nextPack.get())
            }
        }
    }

    private void saveFile(int clientPort) {
        println "RECEBI O ARQUIVO DO $clientPort"
        FileSenderInfo file = messageReceivedHandler.getFromClient(clientPort)
        Packet[] packetList = new Packet[file.receivedData.size()]
        file.receivedData.each {id,value -> packetList[id] = value }
        try (FileOutputStream fos = new FileOutputStream(new File("file_${clientPort}.txt"))) {
            packetList.each {
                fos.write(it.data)
            }
            fos.flush()
            fos.close()
        } catch (Exception ignore) {}
    }

    private void onError(Integer messageId) {
        println "Error occurs on messageId $messageId"
    }
}
