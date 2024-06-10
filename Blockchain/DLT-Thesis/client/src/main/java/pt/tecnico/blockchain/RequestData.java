package pt.tecnico.blockchain;

import java.io.Serializable;

public class RequestData implements Serializable {

    private String transferType;
    private int destination;
    private int amount;

    public RequestData(String transferType,int destination, int amount) {
        this.transferType = transferType;
        this.destination = destination;
        this.amount = amount;
    }

    public RequestData(String transferType) { this.transferType = transferType;}

    public String getTransferType() {
        return transferType;
    }

    public int getDestination() {
        return destination;
    }

    public int getAmount() {return amount;}

}

