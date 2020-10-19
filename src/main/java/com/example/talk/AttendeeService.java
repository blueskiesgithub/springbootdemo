package com.example.talk;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AttendeeService {
    @Autowired
    AttendeeRepository repository;
    public Attendee create(Attendee attendee) {
        return repository.save(attendee);
    }

    public Attendee getForEmail(String email) {
        return repository.findByEmail(email);
    }
}
