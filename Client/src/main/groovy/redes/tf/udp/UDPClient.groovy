package redes.tf.UDP

import java.nio.file.Files

class UDPClient {
    private static final Integer BUFFER_SIZE = 4096
    private MessageSender messageSender
    private MessageReciver messageReciver
    private DatagramSocket socket

    UDPClient() {
        socket = new DatagramSocket()
        messageSender = new MessageSender(socket)

    }


    void startListening(String filePath) {
        byte[] fileData = loadFileData(filePath)
        FileSenderInfo fileSenderInfo = new FileSenderInfo(fileData, BUFFER_SIZE, 1024)
        new Thread(messageSender).start()
        while (true) {
            
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
                } catch (ignored) {
                }
            }
        }
    }

    private byte[] loadFileData(String filePath) {
        return Files.readAllBytes(new File(filePath).toPath())
    }
}