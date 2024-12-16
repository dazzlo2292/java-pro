package ru.otus.java.pro.jpql.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "phones_tab")
public class Phone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "number")
    private String number;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    public Phone() {
    }

    public Phone(String number, Client client) {
        this.number = number;
        this.client = client;
    }

    public Phone(int id, String number, Client client) {
        this.id = id;
        this.number = number;
        this.client = client;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public String toString() {
        return "Phone{" +
                "number='" + number + '\'' +
                ", id=" + id +
                '}';
    }
}
