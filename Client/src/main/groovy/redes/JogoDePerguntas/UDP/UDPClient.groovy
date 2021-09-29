package redes.JogoDePerguntas.UDP

class UDPClient {

    def static Client(){
        while (true){
            def data = "Done".getBytes("ASCII")
            def packet = new DatagramPacket(data, data.length,InetAddress.getByName("localhost"),5000)
            def socket = new DatagramSocket();
            socket.send(packet)
            socket.setSoTimeout(30000) // block for no more than 30 seconds
            def buffer = (' ' * 4096) as byte[]
            def response = new DatagramPacket(buffer, buffer.length)
            socket.receive(response)
            def s = new String(response.data, 0, response.length)
            println "Server said: '$s'"
        }
    }

}
