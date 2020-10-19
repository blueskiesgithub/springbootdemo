package com.example.talk;

public class AttendeeOperation {
    String talkTitle;
    String attendeeEmail;

    protected AttendeeOperation(String talkTitle, String attendeeEmail) {
        this.talkTitle = talkTitle;
        this.attendeeEmail = attendeeEmail;
    }

    public String getTalkTitle() { return talkTitle; }
    public String getAttendeeEmail() { return attendeeEmail; }
}
