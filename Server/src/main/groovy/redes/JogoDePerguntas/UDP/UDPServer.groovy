package redes.JogoDePerguntas.UDP

class UDPServer {

    def static Server(){
        def socketServer = new DatagramSocket(5000)
        def buffer = (' ' * 4096) as byte[]
        while(true) {
            def incoming = new DatagramPacket(buffer, buffer.length)
            socketServer.receive(incoming)
            def s = new String(incoming.data, 0, incoming.length)
            String reply = "Client said: '$s'"
            def outgoing = new DatagramPacket(reply.bytes, reply.size(),
                    incoming.address, incoming.port);
            socketServer.send(outgoing)
        }
    }

}
