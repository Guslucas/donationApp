package br.com.faj.project.donationapp.model;

import java.util.Date;

public class Message {

    private long id;
    private String content;
    private Date date;
    private Donator sender;
    private Donator receiver;

    public Message(long id, String content, Date date, Donator sender, Donator receiver) {
        this.id = id;
        this.content = content;
        this.date = date;
        this.sender = sender;
        this.receiver = receiver;
    }

    //TODO para testes
    public Message(String content, Donator sender, Donator receiver) {
        this.content = content;
        this.sender = sender;
        this.receiver = receiver;
    }


    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public Date getDate() {
        return date;
    }

    public Donator getSender() {
        return sender;
    }

    public Donator getReceiver() {
        return receiver;
    }
}
