package redes.tf

import redes.tf.udp.UDPServer

class Server {

    static void main(String[] args) {
        UDPServer server = new UDPServer()
        server.startExecution()
    }
}