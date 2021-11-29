package redes.tf.udp

import groovy.transform.EqualsAndHashCode
import groovy.transform.MapConstructor
import groovy.transform.ToString

@MapConstructor
@ToString
@EqualsAndHashCode
class Packet {
    Integer messageId
    byte[] data

    Packet(Integer messageId, byte[] data) {
        this.messageId = messageId
        this.data = data
    }

    Packet(byte[] receivedMessage) {
        String result = new String(trimData(receivedMessage))
        List<String> splitResult = result.split(";").toList()
        String messageId = splitResult[0]
        String messageContent = splitResult.subList(1, splitResult.size()).join(";")
        this.messageId = Integer.parseInt(messageId)
        data = messageContent.bytes
    }
    static Packet buildAckPacket(Integer packetToAck, Integer nextPacket) {
        return new Packet(messageId: -1, data: "$packetToAck;$nextPacket".bytes)
    }

    String getStringData() {
        return new String(this.data)
    }

    byte[] toBytes() {
        return "${messageId};${this.stringData}".bytes
    }

    private byte[] trimData(byte[] byteData) {
        int i = byteData.length - 1
        while (i >= 0 && byteData[i] == 0 as byte) {
            i--
        }
        return Arrays.copyOf(byteData, i + 1)
    }
}