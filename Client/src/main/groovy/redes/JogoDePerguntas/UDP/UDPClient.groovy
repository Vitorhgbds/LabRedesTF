package redes.JogoDePerguntas.UDP

class UDPClient {
    def static port = 5001
    def static socket
    def static data
    def static i

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
        data = "First Contact".getBytes("ASCII")
        i = 0;
        new Thread(socketWatch).start()
        while (true) {
            if (i != 0) {
                data = System.in.newReader().readLine()
                data = data.getBytes("ASCII")
            }
            if (data == "-1") {
                try {
                    System.exit(0)
                } catch (ignored) {

                }
            }
            def packet = new DatagramPacket(data, data.length, InetAddress.getByName("localhost"), 5000)
            socket.send(packet)
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

                } catch (ignored) {}
            }
        }
    }
}