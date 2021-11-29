package redes.tf.udp.senderStrategy

import redes.tf.udp.FileSenderInfo
import redes.tf.udp.MessageSender
import redes.tf.udp.Packet

class SlowStartSenderStrategy implements SenderStrategy {
    private MessageSender sender
    private int exp
    private int expLimit

    SlowStartSenderStrategy(MessageSender sender, int expLimit) {
        this.sender = sender
        this.expLimit = expLimit
        this.exp = 0
    }

    @Override
    SenderStrategyName getName() {
        return SenderStrategyName.SLOW_START
    }

    SenderStrategyName sendByStrategy(FileSenderInfo senderInfo) {
        int numberToGet = Math.pow(2, exp) as Integer
        List<Packet> packetsToSend = senderInfo.getNext(numberToGet)
        if (!packetsToSend.isEmpty()) {
            exp++
            packetsToSend.each(sender.&sendMessage)
        }
        if (exp == expLimit) {
            exp = 0
            return SenderStrategyName.CONGESTION_AVOIDANCE
        } else {
            return SenderStrategyName.SLOW_START
        }
    }

    @Override
    SenderStrategyName handleError() {
        exp = 0
        return SenderStrategyName.CONGESTION_AVOIDANCE
    }
}
