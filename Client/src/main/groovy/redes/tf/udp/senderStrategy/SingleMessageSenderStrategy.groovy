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
    void sendByStrategy(FileSenderInfo senderInfo) {
        List<Packet> packetsToSend = senderInfo.getNext(1)
        packetsToSend.each(sender.&sendMessage)
    }

    @Override
    SenderStrategyName handleError() {
        return name
    }
}
