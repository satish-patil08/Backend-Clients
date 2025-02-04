package com.microservices.backend_client.logic.entity;

import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "clients")
public class Clients {

    @Transient
    public static String CLIENT_SEQUENCE = "clients_sequence";

    public Long id;
    public String mongoUrl;

    public Clients() {
    }

    public Clients(Long id, String mongoUrl) {
        this.id = id;
        this.mongoUrl = mongoUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMongoUrl() {
        return mongoUrl;
    }

    public void setMongoUrl(String mongoUrl) {
        this.mongoUrl = mongoUrl;
    }

    @Override
    public String toString() {
        return "Clients{" +
                "id=" + id +
                ", mongoUrl='" + mongoUrl + '\'' +
                '}';
    }
}
