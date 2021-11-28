package redes.JogoDePerguntas.UDP

import groovy.transform.Canonical

@Canonical
class Packet {
    Integer messageId
    byte[] data


}