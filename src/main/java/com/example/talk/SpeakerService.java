package com.example.talk;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpeakerService {
    @Autowired
    SpeakerRepository repository;
    public Speaker create(Speaker speaker) {
        return repository.save(speaker);
    }

    public Speaker getForEmail(String email) {
        return repository.findByEmail(email);
    }
}
