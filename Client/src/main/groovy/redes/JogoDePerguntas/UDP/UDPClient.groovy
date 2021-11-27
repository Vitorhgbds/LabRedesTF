package redes.JogoDePerguntas.UDP

import redes.JogoDePerguntas.UDP.Packet

class UDPClient {
    def static port = 5001
    def static socket
    def static data
    def static i
    static Integer messageid
    static FileSenderInfo fileSenderInfo

    def static Client() {
        // Para verificar se a porta ta ocupada, se ela estiver tenta na porta seguinte
        boolean aux = true
        while (aux) {
            aux = false
            try {
                socket = new DatagramSocket(port)
            } catch (ignored) {
                aux = true
                port++
            }
        }
        messageid = 0;
        Packet packet
        packet.messageId(messageid)
        messageid++
        data = "O MEU BAO VOU TE MANDAR 40 PACOTES BLZ?".getBytes("ASCII")
//        e so por o data dentro do pacote
        //packet.data(data)
        fileSenderInfo.packetsToSend.add(packet)
        i = 0;
        new Thread(socketWatch).start()
        while (true) {
            if (i != 0) {
                //fica verificando o terminal para entradas do usuario
                data = System.in.newReader().readLine()
                data = data.getBytes("ASCII")
            }
            if (data == "-1") {
                try {
                    System.exit(0)
                } catch (ignored) {

                }
            }
            def packet1 = new DatagramPacket(data, data.length, InetAddress.getByName("localhost"), 5000)
            socket.send(packet1)
            i++
        }
    }

    /*
        Thread que verifica se o cliente recebeu novas coisas do servidor
    */
    static Runnable socketWatch = new Runnable() {
        @Override
        void run() {
            while (true) {
                try {
                    byte[] buffer = (' ' * 4096) as byte[]
                    DatagramPacket response = new DatagramPacket(buffer, buffer.length)
                    socket.receive(response)
                    String s = new String(response.data, 0, response.length)
                    println s
                    // - area que avisa ao servidor após receber resposta, para testar o reenvio de mensagens do servidor comentar essa parte:
                    data = "Received".getBytes("ASCII")
                    DatagramPacket packet = new DatagramPacket(data, data.length, InetAddress.getByName("localhost"), 5000)
                    socket.send(packet)
                    socket.setSoTimeout(30000) // timeout para 30 secs
                } catch (ignored) {}
            }
        }
    }
}