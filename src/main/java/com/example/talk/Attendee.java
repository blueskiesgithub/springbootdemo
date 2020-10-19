package com.example.talk;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.repository.Temporal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class Attendee {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @JsonIgnore
    private Long id;
    private String name;
    private String company;
    private String email;
    @CreatedDate
    private Date registered = new Date();

    protected Attendee() {}

    protected Attendee(String name, String company, String email) {
        this.name = name;
        this.company = company;
        this.email = email;
    }

    public Long getId() {
        return id;
    }
    public String getName() { return name;}
    public String getCompany() { return company;}
    public String getEmail() { return email;}
    public Date getRegistered() { return registered;}
}