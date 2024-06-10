package pt.tecnico.blockchain;

import java.io.Serializable;

public class ReplyData implements Serializable {

    private String reply;
    private int balance;

    public ReplyData(String reply) {
        this.reply = reply;
    }

    public ReplyData(int balance) {
        this.balance = balance;
    }

    public String getReply() {
        return reply;
    }

    public int getBalance() {
        return balance;
    }

}