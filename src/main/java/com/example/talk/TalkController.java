package com.example.talk;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@RestController
public class TalkController
{
    @Autowired
    SpeakerService speakerService;
    @Autowired
    TalkService talkService;
    @Autowired
    AttendeeService attendeeService;

    @PostMapping(path= "addattendee", consumes = "application/json", produces = "application/json")
    public @ResponseBody Attendee addAttendee(@RequestBody Attendee attendee) throws Exception {
        Attendee existingAttendee = attendeeService.getForEmail(attendee.getEmail());
        if (null != existingAttendee) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "An attendee with that email already exists.");
        } else {
            return attendeeService.create(attendee);
        }
    }

    @PostMapping(path= "addattendeetotalk", consumes = "application/json", produces = "application/json")
    public @ResponseBody Talk addAttendeeToTalk(@RequestBody AttendeeOperation operation) {
        Talk existingTalk = talkService.getForTitle(operation.getTalkTitle());
        if (null == existingTalk) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "That talk does not exist.");
        }
        Attendee existingAttendee = attendeeService.getForEmail(operation.getAttendeeEmail());
        if (null == existingAttendee) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "That attendee does not exist.");
        }

        if (!existingTalk
                .getAttendees()
                .stream()
                .filter(a -> a.getEmail()
                        .equals(operation.getAttendeeEmail()))
                .findFirst().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Attendee is already in the talk.");
        }

        return talkService.addAttendee(existingTalk, existingAttendee);
    }

    @PostMapping(path= "removeattendeefromtalk", consumes = "application/json", produces = "application/json")
    public @ResponseBody Talk removeAttendeeFromTalk(@RequestBody AttendeeOperation operation) {
        Talk existingTalk = talkService.getForTitle(operation.getTalkTitle());
        if (null == existingTalk) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "That talk does not exist.");
        }
        Attendee existingAttendee = attendeeService.getForEmail(operation.getAttendeeEmail());
        if (null == existingAttendee) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "That attendee does not exist.");
        }

        if (existingTalk
                .getAttendees()
                .stream()
                .filter(a -> a.getEmail()
                        .equals(operation.getAttendeeEmail()))
                .findFirst().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Attendee is not in the talk.");
        }

        return talkService.removeAttendee(existingTalk, existingAttendee);
    }


    @PostMapping(path= "addtalk", consumes = "application/json", produces = "application/json")
    public @ResponseBody Talk addTalk(@RequestBody Talk talk) throws Exception {

        Talk existingTalk = talkService.getForTitle(talk.getTitle());
        if (null != existingTalk) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A talk with that title already exists.");
        } else {
            Speaker speaker = speakerService.getForEmail(talk.getSpeaker().getEmail());
            if (null == speaker) {
                speaker = speakerService.create(new Speaker(talk.getSpeaker().getName(),
                                                talk.getSpeaker().getCompany(),
                                                talk.getSpeaker().getEmail(),
                                                talk.getSpeaker().getBio()));
            }

            return talkService.create(talk.getTitle(), talk.getRoom(), speaker);
        }
    }
}
