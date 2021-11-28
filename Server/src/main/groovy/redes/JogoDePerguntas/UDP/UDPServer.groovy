package redes.JogoDePerguntas.UDP
/*
       Servidor
   */

class UDPServer {
    private static final Integer BUFFER_SIZE = 4096
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
            serverSocket.receive(incoming)
            Packet packet = new Packet(incoming.data)
            Packet reply = handleResponse(packet, incoming.port)
            byte[] replyBytes = reply.toBytes()
            DatagramPacket outgoing = new DatagramPacket(replyBytes, replyBytes.size(), incoming.address, incoming.port)
            serverSocket.send(outgoing)
        }
    }

/*
    Gerencia as respostas recebidas pelo cliente

*/

    private Packet handleResponse(Packet response, Integer port) {
        if (!messageReceivedHandler.hasClient(port)) {
            messageReceivedHandler.saveClient(port, Integer.parseInt(response.stringData))
            return Packet.buildPacketWithResponse('OK')
        } else if (response.stringData == 'FINISH') {
            if (messageReceivedHandler.fileIsAlreadyAllSent(port)) {
                saveFile(port)
                return Packet.buildPacketWithResponse("DONE")
            } else {
                int packet = messageReceivedHandler.nextPacketToReceive(port)
                return Packet.buildPacketWithResponse(packet.toString())
            }
        } else {
            int packet = messageReceivedHandler.receivePacketAndGenereteNext(port, response)
            return Packet.buildPacketWithResponse(packet.toString())
        }
    }

    private void saveFile(int clientPort) {

    }
}
