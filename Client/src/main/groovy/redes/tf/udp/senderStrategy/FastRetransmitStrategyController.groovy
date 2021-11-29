package redes.tf.udp.senderStrategy

class FastRetransmitStrategyController {
    private Map<Integer, Integer> messageByNumberOfAcks

    FastRetransmitStrategyController() {
        this.messageByNumberOfAcks = [:]
    }

    void countMessage(Integer messageId) {
        int count = messageByNumberOfAcks.getOrDefault(messageId, 0)
        messageByNumberOfAcks.put(messageId, count + 1)
    }

    Integer getCountFor(Integer messageId) {
        return messageByNumberOfAcks.getOrDefault(messageId, 0)
    }
}
