package redes.tf.udp

import groovy.transform.Canonical
import groovyjarjarantlr4.v4.runtime.misc.Tuple2

class MessageReceivedHandler {

    private Map<Integer, FileSenderInfo> clientFileInfo

    MessageReceivedHandler() {
        this.clientFileInfo = [:]
    }

    void cleanClient(Integer port){
        clientFileInfo.remove(port)
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

    Optional<Integer> receivePacketAndGenerateNext(Integer port, Packet packet) {
        FileSenderInfo fileSenderInfo = clientFileInfo.get(port)
        fileSenderInfo.receivePacket(packet)
        return fileSenderInfo.nextPacketToReceive()
    }

    boolean fileIsAlreadyAllSent(int port) {
        FileSenderInfo fileSenderInfo = clientFileInfo.get(port)
        return fileSenderInfo.finish
    }
}

@Canonical
class FileSenderInfo {
    boolean finish = false
    private final List<Tuple2<Integer, Boolean>> packetsToReceive
    final Map<Integer, Packet> receivedData


    FileSenderInfo(int packetsToReceive) {
        this.packetsToReceive = (0..packetsToReceive -1).toList().collect {
            return new Tuple2<Integer, Boolean>(it, false)
        }
        this.receivedData = [:]
    }

    Optional<Integer> nextPacketToReceive() {
        Tuple2<Integer, Boolean> nextPacket = packetsToReceive.find { (!it.item2) }
        return Optional.ofNullable(nextPacket?.item1)
    }


    void receivePacket(Packet packet) {
        int packetId = packet.messageId
        Tuple2<Integer, Boolean> receivedPacket = packetsToReceive.find { it.item1 == packetId }
        packetsToReceive.removeElement(receivedPacket)
        packetsToReceive.add(new Tuple2<Integer, Boolean>(packetId, true))
        receivedData.put(packetId, packet)
        finish = hasPacketToReceive()
    }

    private boolean hasPacketToReceive() {
        return packetsToReceive.every { it.item2 }
    }
}

