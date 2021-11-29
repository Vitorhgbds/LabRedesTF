package redes.tf.udp.senderStrategy

import redes.tf.udp.FileSenderInfo

interface SenderStrategy {
    /**
     * Method to get name the of the sender Strategy
     * @return the name of the strategy
     */
    SenderStrategyName getName()
    /**
     * Will execute the algorithm to send packets to server following the strategy rule
     * @param senderInfo
     */
    void sendByStrategy(FileSenderInfo senderInfo)

    /**
     * What must do when some errors occurs when trying to send message. e.g. Timeout
     * @return the name of the next strategy
     */
    SenderStrategyName handleError()
}

/**
 * The Enum of the names of the strategies
 */
enum SenderStrategyName {
    SLOW_START,
    CONGESTION_AVOIDANCE
}
