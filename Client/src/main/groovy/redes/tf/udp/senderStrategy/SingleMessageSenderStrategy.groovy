package redes.tf.udp.senderStrategy

import redes.tf.udp.FileSenderInfo
import redes.tf.udp.MessageSender
import redes.tf.udp.Packet

class SingleMessageSenderStrategy implements SenderStrategy {
    private MessageSender sender

    SingleMessageSenderStrategy(MessageSender sender) {
        this.sender = sender
    }

    @Override
    SenderStrategyName getName() {
        return SenderStrategyName.SINGLE_SENDER
    }

    @Override
    SenderStrategyName sendByStrategy(FileSenderInfo senderInfo) {
        List<Packet> packetsToSend = senderInfo.getNext(1)
        packetsToSend.each(sender.&sendMessage)
        return name
    }

    @Override
    SenderStrategyName handleError() {
        return name
    }
}
