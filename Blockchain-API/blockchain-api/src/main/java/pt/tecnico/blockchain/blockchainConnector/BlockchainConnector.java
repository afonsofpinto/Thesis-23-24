package pt.tecnico.blockchain.blockchainConnector;

import pt.tecnico.blockchain.ReplyData;
import pt.tecnico.blockchain.RequestData;


import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class BlockchainConnector {

    public static ReplyData executeOperation(Integer port, int source,RequestData data) {
        try {
            DatagramSocket socket = new DatagramSocket();

            // SEND DATA //
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(data);
            objectOutputStream.flush();
            byte[] bytes = byteArrayOutputStream.toByteArray();
            InetAddress address = InetAddress.getByName("localhost");
            DatagramPacket packet = new DatagramPacket(bytes, bytes.length, address, port);
            socket.send(packet);
            socket.close();

            // RECEIVE DATA //
            DatagramSocket socketResponse = new DatagramSocket(10500+ source);
            byte[] buffer = new byte[1024];
            packet = new DatagramPacket(buffer, buffer.length);
            socketResponse.receive(packet);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(packet.getData());
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            ReplyData receivedData = (ReplyData) objectInputStream.readObject();
            objectInputStream.close();
            socketResponse.close();
            return receivedData;

        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new RuntimeException("ERROR IN OPERATION");
    }
}
