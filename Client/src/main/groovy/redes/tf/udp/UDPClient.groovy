package redes.tf.udp

import redes.tf.udp.senderStrategy.CongestionAvoidanceSenderStrategy
import redes.tf.udp.senderStrategy.FastRetransmitStrategyController
import redes.tf.udp.senderStrategy.SenderStrategy
import redes.tf.udp.senderStrategy.SenderStrategyName
import redes.tf.udp.senderStrategy.SingleMessageSenderStrategy
import redes.tf.udp.senderStrategy.SlowStartSenderStrategy


class UDPClient {
    private static final Integer BUFFER_SIZE = 100
    private MessageSender messageSender
    private SenderStrategyName currentStrategyName
    private List<SenderStrategy> strategies
    private FastRetransmitStrategyController fastRetransmit
    private DatagramSocket socket

    UDPClient() {
        fastRetransmit = new FastRetransmitStrategyController()
        socket = new DatagramSocket()
        messageSender = new MessageSender(socket, this.&onError)
        strategies = [new CongestionAvoidanceSenderStrategy(messageSender)]
        //strategies = [new SingleMessageSenderStrategy(messageSender)]
        currentStrategyName = strategies.first().name
    }

    void startListening(String filePath) {
        byte[] fileData = loadFileData(filePath)
        FileSenderInfo fileSenderInfo = new FileSenderInfo(fileData, BUFFER_SIZE, 15)
        int packetsToSendFile = fileSenderInfo.packetsToSend.size()
        messageSender.sendMessage(new Packet(messageId: -2, data: "$packetsToSendFile".bytes))
        new Thread(messageSender).start()
        byte[] buffer = new byte[BUFFER_SIZE]
        while (true) {
            DatagramPacket packetReceived = new DatagramPacket(buffer, BUFFER_SIZE)
            println "Starting to listening\n\n"
            socket.receive(packetReceived)
            println "Receive Packet"
            Packet packet = new Packet(packetReceived.data)
            buffer = new byte[BUFFER_SIZE]
            boolean shouldStop = onReceivedPacket(packet, fileSenderInfo)
            if (shouldStop) break
        }
        println "Will stop message sender retransmit loop"
        messageSender.stopsWatch()

    }

    private boolean onReceivedPacket(Packet packet, FileSenderInfo fileSenderInfo) {
        messageSender.removeRetransmit(packet.messageId)
        Packet ack = Packet.buildAckPacket(packet.messageId, -1)
        String data = packet.stringData
        messageSender.sendMessage(ack)
        if (data == 'OK') {
            println "Server received first packet, will send file from now on"
            List<Packet> packetsToSend = fileSenderInfo.getNext(1)
            if (!packetsToSend.isEmpty()) {
                packetsToSend.each(messageSender.&sendMessage)
            }
            return false
        } else if (data == 'DONE') {
            println "Server Finished"
            return true
        } else {
            String[] ackMessageData = data.split(";")
            Integer requestNextMessageId = Integer.parseInt(ackMessageData[1])
            fastRetransmit.countMessage(requestNextMessageId)
            Integer shouldRetransmitMessage = fastRetransmit.getCountFor(requestNextMessageId)
            //println "Send File Content"
            if (shouldRetransmitMessage >= 3) {
                println "Will retransmit missing message from server ${requestNextMessageId}"
                Packet packetToResend = fileSenderInfo.retrievePacket(requestNextMessageId)
                messageSender.removeRetransmit(requestNextMessageId)
                messageSender.sendMessage(packetToResend)
                onError(requestNextMessageId)
                return false
            }
            SenderStrategy senderStrategy = findCurrentStrategy()
            senderStrategy.sendByStrategy(fileSenderInfo)
            return false
        }
    }

    private void onError(Integer messageId) {
        println "Error occurs on messageId $messageId, will execute strategy error handling for next messages"
        SenderStrategy strategy = findCurrentStrategy()
        SenderStrategyName nextStrategy = strategy.handleError()
        this.currentStrategyName = nextStrategy
    }

    private SenderStrategy findCurrentStrategy() {
        return strategies.find { it.name == this.currentStrategyName }
    }

    private byte[] loadFileData(String filePath) {
        return new File(filePath).bytes
    }
}