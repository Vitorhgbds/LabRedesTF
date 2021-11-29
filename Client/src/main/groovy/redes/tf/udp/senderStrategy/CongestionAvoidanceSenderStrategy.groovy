package redes.tf.udp.senderStrategy

import redes.tf.udp.FileSenderInfo
import redes.tf.udp.MessageSender
import redes.tf.udp.Packet

class CongestionAvoidanceSenderStrategy implements SenderStrategy {

    private MessageSender sender
    private int currentNumber

    CongestionAvoidanceSenderStrategy(MessageSender sender) {
        this.sender = sender
        this.currentNumber = 1
    }

    @Override
    SenderStrategyName getName() {
        return SenderStrategyName.CONGESTION_AVOIDANCE
    }

    @Override
    void sendByStrategy(FileSenderInfo senderInfo) {
        List<Packet> packets = senderInfo.getNext(currentNumber)
        if (!packets.isEmpty()) {
            currentNumber++
            packets.each(sender.&sendMessage)
        }
    }

    @Override
    SenderStrategyName handleError() {
        currentNumber = (currentNumber / 2) as Integer
        if (currentNumber == 0) currentNumber = 1
        return this.name
    }
}
