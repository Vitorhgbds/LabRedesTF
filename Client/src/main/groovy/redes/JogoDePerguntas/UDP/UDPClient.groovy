package redes.JogoDePerguntas.UDP

class UDPClient {
    def static port = 5001

    def static Client(){
        boolean qualquer=true
        def socket
        while (qualquer){
            qualquer=false
            try{
                socket =new DatagramSocket(port);
            }catch(ignored){
                qualquer = true
                port++
            }
        }
        def data = "First Contact".getBytes("ASCII")
        int i =0;
        while (true){
            if(i!=0) {
                data = System.in.newReader().readLine()
                data = data.getBytes("ASCII")

            }
            if (data=="-1") {
                try{
                    System.exit(0)
                }catch(ignored){

                }
            }
            i++
            def packet = new DatagramPacket(data, data.length,InetAddress.getByName("localhost"),5000)
            socket.send(packet)
            socket.setSoTimeout(30000) // block for no more than 30 seconds
            def buffer = (' ' * 4096) as byte[]
            def response = new DatagramPacket(buffer, buffer.length)
            socket.receive(response)
            def s = new String(response.data, 0, response.length)
            println s
        }
    }

}
