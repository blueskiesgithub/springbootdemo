package com.example.talk;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@NamedQuery(
        name="findAllSpeakersWithEmail",
        query="SELECT s FROM Speaker s WHERE s.email = :speakerEmail")
public class Speaker {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @JsonIgnore
    private Long id;
    private String name;
    private String company;
    private String email;
    private String bio;

    protected Speaker() {}

    protected Speaker(String name, String company, String email, String bio) {
        this.name = name;
        this.company = company;
        this.email = email;
        this.bio = bio;
    }

    public Long getId() {
        return id;
    }
    public String getName() { return name;}
    public String getCompany() { return company;}
    public String getEmail() { return email;}
    public String getBio() { return bio;}
}