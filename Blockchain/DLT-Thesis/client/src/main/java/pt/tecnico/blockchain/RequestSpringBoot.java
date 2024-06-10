package pt.tecnico.blockchain;

import pt.tecnico.blockchain.Keys.RSAKeyStoreById;
import pt.tecnico.blockchain.Messages.tes.TESReadType;
import pt.tecnico.blockchain.contracts.tes.TESClientAPI;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.security.PublicKey;

public class RequestSpringBoot {

    public static final String TYPE_TRANSFER = "TYPE: EXECUTE TRANSFER";

    public static final String TYPE_CHECK_BALANCE = "TYPE: CHECK BALANCE";

    private static TESClientAPI client;

    private static int _port;

    public static void setClient(TESClientAPI tes, int port) {
        client = tes;
        _port = port;
    }

    public static void receiveClientRequests(){
        try {
            DatagramSocket socket = new DatagramSocket(_port);
            while (true) {
                byte[] buffer = new byte[1024];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);

                // Deserialize the received bytes
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(packet.getData());
                ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
                RequestData receivedData = (RequestData) objectInputStream.readObject();
                objectInputStream.close();
                switch(receivedData.getTransferType()) {
                    case TYPE_TRANSFER:
                        issueTransferRequest(receivedData);
                        break;
                    case TYPE_CHECK_BALANCE:
                        issueCheckBalance();
                        break;
                }

                // Do something with the received data
                System.out.println("String value: " + receivedData.getDestination());
                System.out.println("Integer value: " + receivedData.getAmount());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void issueTransferRequest(RequestData receivedData) {
        PublicKey destination = RSAKeyStoreById.getPublicKey(receivedData.getDestination());
        if (destination != null) client.transfer(destination, receivedData.getAmount(),
                10, 10);
        else Logger.logError("Client with ID=" + receivedData.getDestination() + " does not have a valid key");
    }

    private static void issueCheckBalance() {
       client.checkBalance(TESReadType.STRONG,10,10);
    }



}
