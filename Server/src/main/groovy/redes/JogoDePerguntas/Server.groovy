package redes.JogoDePerguntas

import redes.JogoDePerguntas.UDP.UDPServer

class Server {

    @Lazy
    private static Properties properties

    public static void main(String[] args) {
        new Object() {}
                .getClass()
                .getResource( "/application.properties" )
                .withInputStream {
                    properties.load(it)
                }
        UDPServer.Server()

    }


}