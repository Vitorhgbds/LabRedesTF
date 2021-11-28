package redes.tf

import redes.tf.udp.UDPClient

class Client {
    static void main(String[] args) {
        UDPClient client = new UDPClient()
        client.startListening("teste.txt")
    }
}