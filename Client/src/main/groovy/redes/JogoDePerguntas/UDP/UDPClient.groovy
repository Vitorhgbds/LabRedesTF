package redes.JogoDePerguntas.UDP

class UDPClient {
    def static port = 5001
    def static socket
    def static data
    def static i
    def static Client(){
        boolean qualquer=true
        while (qualquer){
            qualquer=false
            try{
                socket =new DatagramSocket(port);
            }catch(ignored){
                qualquer = true
                port++
            }
        }
        data = "First Contact".getBytes("ASCII")
        i =0;
        new Thread(socketWatch).start()
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
            def packet = new DatagramPacket(data, data.length,InetAddress.getByName("localhost"),5000)
            socket.send(packet)
            i++
        }
    }

    static Runnable terminalWatch = new Runnable() {
        @Override
        void run() {
            while (true){
                try{
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
                }catch(ignored){}
            }
        }
    }

    static Runnable socketWatch = new Runnable() {
        @Override
        void run() {
            while (true){
                try{
                    def buffer = (' ' * 4096) as byte[]
                    def response = new DatagramPacket(buffer, buffer.length)
                    socket.receive(response)
                    def s = new String(response.data, 0, response.length)
                    println s
                    data = "Received".getBytes("ASCII")
                    def packet = new DatagramPacket(data, data.length,InetAddress.getByName("localhost"),5000)
                    socket.send(packet)
                }catch(ignored){}
            }
        }
    }
}
