package redes.tf.udp.senderStrategy

import redes.tf.udp.FileSenderInfo
import redes.tf.udp.MessageSender
import redes.tf.udp.Packet

class SlowStartSenderStrategy implements SenderStrategy {
    private MessageSender sender
    private int exp

    SlowStartSenderStrategy(MessageSender sender) {
        this.sender = sender
        this.exp = 0
    }

    @Override
    SenderStrategyName getName() {
        return SenderStrategyName.SLOW_START
    }

    void sendByStrategy(FileSenderInfo senderInfo) {
        int numberToGet = Math.pow(2, exp) as Integer
        List<Packet> packetsToSend = senderInfo.getNext(numberToGet)
        if (!packetsToSend.isEmpty()) {
            exp++
            packetsToSend.each(sender.&sendMessage)
        }
    }

    @Override
    SenderStrategyName handleError() {
        exp = 0
        return SenderStrategyName.CONGESTION_AVOIDANCE
    }
}
