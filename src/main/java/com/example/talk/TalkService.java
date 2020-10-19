package com.example.talk;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TalkService {
    @Autowired
    TalkRepository repository;
    public Talk create(String title, Integer room, Speaker speaker) {
        return repository.save(new Talk(title, room, speaker));
    }

    public Talk getForTitle(String title) {
        return repository.findByTitle(title);
    }

    public Talk addAttendee(Talk talk, Attendee attendee) {
        talk.getAttendees().add(attendee);
        return repository.save(talk);
    }

    public Talk removeAttendee(Talk talk, Attendee attendee) {
        talk.getAttendees().remove(attendee);
        return repository.save(talk);
    }
}
