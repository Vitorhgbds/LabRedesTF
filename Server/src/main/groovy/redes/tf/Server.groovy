package redes.tf

import redes.JogoDePerguntas.UDP.UDPServer

class Server {

    static void main(String[] args) {
        UDPServer server = new UDPServer()
        server.startExecution()
    }
}