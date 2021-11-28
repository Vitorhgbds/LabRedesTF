package redes.tf.udp

import java.nio.file.Files

class UDPClient {
    private static final Integer BUFFER_SIZE = 10
    private MessageSender messageSender
    private DatagramSocket socket

    UDPClient() {
        socket = new DatagramSocket()
        messageSender = new MessageSender(socket)
    }

    void startListening(String filePath) {
        byte[] fileData = loadFileData(filePath)
        FileSenderInfo fileSenderInfo = new FileSenderInfo(fileData, BUFFER_SIZE, 3)
        int packetsToSendFile = fileSenderInfo.packetsToSend.size()
        messageSender.sendMessage(new Packet(messageId: -2, data: "$packetsToSendFile".bytes))
        //Thread messageSenderThread = new Thread(messageSender).start()
        byte[] buffer = new byte[BUFFER_SIZE]
        while (true) {
            DatagramPacket packetReceived = new DatagramPacket(buffer, BUFFER_SIZE)
            println "Starting to listening\n\n"
            socket.receive(packetReceived)
            println "Receive Packet"
            Packet packet = new Packet(packetReceived.data)
            boolean shouldStop = onReceivedPacket(packet, fileSenderInfo)
            if(shouldStop) break
        }
    }

    private boolean onReceivedPacket(Packet packet, FileSenderInfo fileSenderInfo) {
        messageSender.removeRetransmit(packet.messageId)
        String data = packet.stringData
        if (data == 'OK') {
            println "Server received first packet, will send file from now on"
            List<Packet> packetsToSend = fileSenderInfo.getNext(1)
            packetsToSend.each(messageSender.&sendMessage)
            return false
        } else if (data == 'DONE') {
            println "Server Finished"
            return true
        } else {
            println "Send File Content"
            List<Packet> packetsToSend = fileSenderInfo.getNext(1)
            packetsToSend.each(messageSender.&sendMessage)
            return false
        }

    }

    /*
        DatagramPacket packetToReceive = new DatagramPacket(buffer, bufferSize)
        socket.receive(packetToReceive)
        byte[] receivedMessage = packetToReceive.data
        Packet packet = new Packet(receivedMessage)
        onReceive(packet)
     */

    private byte[] loadFileData(String filePath) {
        return new File(filePath).bytes
    }
}