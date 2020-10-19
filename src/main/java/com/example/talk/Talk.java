package com.example.talk;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
public class Talk {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @JsonIgnore
    private Long id;
    private String title;
    private Integer room;
    @ManyToOne(cascade = CascadeType.ALL)
    private Speaker speaker;
    @ElementCollection
    @ManyToMany
    @JoinColumn
    private List<Attendee> attendees;

    protected Talk() {}

    protected Talk(String title, Integer room, Speaker speaker) {
        this.title = title;
        this.room = room;
        this.speaker = speaker;
        this.attendees = new LinkedList<Attendee>();
    }

    public Long getId() {
        return id;
    }

    public String getTitle() { return title; }
    public Integer getRoom() { return room; }
    public Speaker getSpeaker() { return speaker; }
    public List<Attendee> getAttendees() { return attendees; }
}