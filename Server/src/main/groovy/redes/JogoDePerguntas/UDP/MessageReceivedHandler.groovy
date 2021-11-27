package redes.JogoDePerguntas.UDP

import groovy.transform.Canonical
import groovyjarjarantlr4.v4.runtime.misc.Tuple2

class MessageReceivedHandler {

    private Map<Integer, FileSenderInfo> clientFileInfo

    MessageSenderHandler() {
        this.clientFileInfo = [:]
    }

    void saveClient(Integer clientPort, Integer packetsToReceive) {
        FileSenderInfo fileSenderInfo = new FileSenderInfo(packetsToReceive)
        clientFileInfo.put(clientPort, fileSenderInfo)
    }

    boolean hasClient(Integer clientPort) {
        return clientFileInfo.containsKey(clientPort)
    }

    FileSenderInfo getFromClient(Integer clientPort) {
        return clientFileInfo.get(clientPort)
    }

    int receivePacket(Integer port, Packet packet) {
        FileSenderInfo fileSenderInfo = clientFileInfo.get(port)
        fileSenderInfo.receivePacket(packet)
        return fileSenderInfo.nextPacketToReceive()
    }
}

@Canonical
class FileSenderInfo {
    private final List<Tuple2<Integer, Boolean>> packetsToReceive
    final Map<Integer, Packet> receivedData


    FileSenderInfo(int packetsToReceive) {
        this.packetsToReceive = (1..packetsToReceive).toList().collect {
            return new Tuple2<Integer, Boolean>(it, false)
        }
    }

    Integer nextPacketToReceive() {
        Tuple2<Integer, Boolean> nextPacket = packetsToReceive.find { (!it.item2) }
        return nextPacket.item1
    }


    void receivePacket(Packet packet) {
        int packetId = packet.messageId
        Tuple2<Integer, Boolean> receivedPacket = packetsToReceive.find { it.item1 == packetId }
        packetsToReceive.removeElement(receivedPacket)
        packetsToReceive.add(new Tuple2<Integer, Boolean>(packetId, true))
        receivedData.put(packetId, packet)
    }
}

