package redes.tf.udp

import groovy.transform.Canonical

class MessageSenderHandler {
    private FileSenderInfo fileInfo

    MessageSenderHandler(FileSenderInfo fileSenderInfo) {
        fileInfo = fileSenderInfo
    }
}

@Canonical
class FileSenderInfo {
    //Mapa pois estou pensando em usar ele pra tratar a questão do erro usando ele mesmo.
    final Integer bufferSize
    final Map<Integer, Packet> sentData
    final List<Packet> packetsToSend

    FileSenderInfo(byte[] file, Integer bufferSize, Integer idOffset) {
        this.bufferSize = bufferSize - idOffset
        packetsToSend = generatePacketsToSend(file)
        this.sentData = [:]
    }

    Packet retrievePacket(Integer messageId) {
        return sentData.get(messageId)
    }

    List<Packet> getNext(Integer size) {
        List<Packet> toSend = []
        for (int i = 0; i < size && packetsToSend.size() > 0; i++) {
            Packet packetToSend = packetsToSend.pop()
            toSend << packetToSend
            sentData.put(packetToSend.messageId, packetToSend)
        }
        println "Get next got ${toSend.collect{it.messageId}.join(" ")}".toString()
        return toSend
    }

    private List<Packet> generatePacketsToSend(byte[] file) {
        List<List<Byte>> chunksOfBytes = file.toList().collate(bufferSize)
        return chunksOfBytes
            .withIndex().collect { List<Byte> bytes, Integer index ->
                byte[] data = bytes.toArray() as byte[]
                return new Packet(messageId: index, data: data)
            }
    }
}

