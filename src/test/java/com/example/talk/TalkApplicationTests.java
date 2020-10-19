package com.example.talk;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
class TalkApplicationTests {
	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int port;

	@Autowired
	private TalkController controller;

	@Test
	void contextLoads() {
		assertThat(controller).isNotNull();
	}

	@Test
	void createTalkAndAddAttendee() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Talk> talkEntity = new HttpEntity<>(new Talk("talk1",
				100,
				new Speaker("speaker1", "company1", "speaker1@test.com", "My life is speaking")),
				headers);

		Talk resultTalk = restTemplate.postForObject("http://localhost:" + port + "/addtalk",
													talkEntity,
													Talk.class);

		assertThat(resultTalk.getTitle().equals("talk1"));
		HttpEntity<Attendee> attendeeEntity = new HttpEntity<Attendee>(new Attendee("attendee1", "company1", "attendee1@test.com"));
		Attendee resultAttendee = restTemplate.postForObject("http://localhost:" + port + "/addattendee",
															attendeeEntity,
															Attendee.class);
		assertThat(resultAttendee.getName().equals("attendee1"));
		HttpEntity<AttendeeOperation> attendeeOperationEntity = new HttpEntity<>(new AttendeeOperation("talk1", "attendee1@test.com"),
																				headers);
		resultTalk = restTemplate.postForObject("http://localhost:" + port + "/addattendeetotalk",
												attendeeOperationEntity,
												Talk.class);

		assertThat(resultTalk.getAttendees().stream().filter(a -> a.getEmail().equals("attendee1@test.com")).findFirst().isPresent());



	}

}
