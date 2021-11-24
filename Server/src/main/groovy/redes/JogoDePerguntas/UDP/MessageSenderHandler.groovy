package redes.JogoDePerguntas.UDP

import groovy.transform.Canonical

class MessageSenderHandler {

    private Integer bufferSize

    private Map<Integer, FileSenderInfo> clientFileInfo

    MessageSenderHandler(Integer bufferSize) {
        this.clientFileInfo = [:]
        this.bufferSize = bufferSize
    }

    List<Packet> buildPacketsToSend(Integer clientPort, Integer size) {
        FileSenderInfo fileSenderInfo = clientFileInfo.get(clientPort)
        return fileSenderInfo.getNext(size)
    }

    List<Packet> loadAndGetFirstPackets(Integer clientPort, byte[] file, Integer startingSize) {
        List<Byte> bytes = file.toList()
        List<List<Byte>> chunks = bytes.collate(bufferSize)
        List<Packet> packets = chunks.withIndex()
                .collect { chunk, chunkId -> new Packet(messageId: chunkId, data: chunk) }
        FileSenderInfo senderInfo = new FileSenderInfo(packets)
        clientFileInfo.put(clientPort, senderInfo)
        List<Packet> nextPackets = senderInfo.getNext(startingSize)
        return nextPackets
    }
}

@Canonical
class FileSenderInfo {
    //Mapa pois estou pensando em usar ele pra tratar a questão do erro usando ele mesmo.
    final Map<Integer, Packet> sentData
    final List<Packet> packetsToSend

    FileSenderInfo(List<Packet> packetsToSend) {
        this.sentData = [:]
        this.packetsToSend = packetsToSend
    }

    List<Packet> getNext(Integer size) {
        List<Packet> toSend = []
        for (int i = 0; i < size; i++) {
            Packet packetToSend = packetsToSend.pop()
            toSend.push(packetToSend)
            sentData.put(packetToSend.messageId, packetToSend)
        }
        return toSend
    }
}

@Canonical
class Packet {
    Integer messageId
    byte[] data
}
