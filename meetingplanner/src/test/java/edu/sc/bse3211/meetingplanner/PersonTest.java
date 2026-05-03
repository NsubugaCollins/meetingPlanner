package edu.sc.bse3211.meetingplanner;

import static org.junit.Assert.*;
import org.junit.Test;
import java.util.ArrayList;

public class PersonTest {
	// Add test methods here. 
    // You are not required to write tests for all classes.
    
    @Test
    public void testPersonConstructor() {
    	Person person = new Person("John Doe");
    	assertEquals("Name should be 'John Doe'", "John Doe", person.getName());
    }
    
    @Test
    public void testAddMeeting_normal() {
    	Person person = new Person("John Doe");
    	Meeting meeting = new Meeting(1, 15, 9, 11, null, null, "Meeting");
    	try {
    		person.addMeeting(meeting);
    		assertTrue("Person should be busy", person.isBusy(1, 15, 9, 11));
    	} catch (TimeConflictException e) {
    		fail("Should not throw exception: " + e.getMessage());
    	}
    }
    
    @Test
    public void testAddMeeting_conflict() {
    	Person person = new Person("John Doe");
    	Meeting meeting1 = new Meeting(1, 15, 9, 11, null, null, "First meeting");
    	Meeting meeting2 = new Meeting(1, 15, 10, 12, null, null, "Conflicting meeting");
    	try {
    		person.addMeeting(meeting1);
    		person.addMeeting(meeting2);
    		fail("Should throw TimeConflictException for conflict");
    	} catch (TimeConflictException e) {
    		assertTrue("Exception should mention conflict", e.getMessage().contains("Conflict"));
    	}
    }
    
    @Test
    public void testIsBusy() {
    	Person person = new Person("John Doe");
    	Meeting meeting = new Meeting(1, 15, 9, 11, null, null, "Meeting");
    	try {
    		person.addMeeting(meeting);
    		assertTrue("Should be busy", person.isBusy(1, 15, 9, 11));
    		assertFalse("Should not be busy at different time", person.isBusy(1, 15, 12, 13));
    	} catch (TimeConflictException e) {
    		fail("Should not throw exception: " + e.getMessage());
    	}
    }
    
    @Test
    public void testPrintAgenda() {
    	Person person = new Person("John Doe");
    	Room room = new Room("TestRoom");
    	ArrayList<Person> attendees = new ArrayList<>();
    	attendees.add(person);
    	Meeting meeting = new Meeting(1, 15, 9, 11, attendees, room, "Meeting");
    	try {
    		person.addMeeting(meeting);
    		String agenda = person.printAgenda(1, 15);
    		assertTrue("Agenda should contain meeting", agenda.contains("Meeting"));
    	} catch (TimeConflictException e) {
    		fail("Should not throw exception: " + e.getMessage());
    	}
    }
    
    // Additional tests for comprehensive coverage
    
    @Test
    public void testBookVacationForPerson() {
    	Person person = new Person("Jane Smith");
    	try {
    		Meeting vacation = new Meeting(11, 25, "Thanksgiving Vacation");
    		person.addMeeting(vacation);
    		assertTrue("Person should be busy all day for vacation", person.isBusy(11, 25, 0, 23));
    		assertTrue("Person should be busy during work hours", person.isBusy(11, 25, 9, 17));
    	} catch (TimeConflictException e) {
    		fail("Vacation booking should succeed: " + e.getMessage());
    	}
    }
    
    @Test
    public void testPersonAvailabilityCheck() {
    	Person person = new Person("Bob Johnson");
    	try {
    		// Book morning meeting
    		Meeting morning = new Meeting(2, 14, 8, 10, null, null, "Morning Standup");
    		person.addMeeting(morning);
    		// Book afternoon meeting
    		Meeting afternoon = new Meeting(2, 14, 13, 15, null, null, "Client Meeting");
    		person.addMeeting(afternoon);
    		
    		// Check availability
    		assertTrue("Should be busy during morning meeting", person.isBusy(2, 14, 8, 10));
    		assertTrue("Should be busy during afternoon meeting", person.isBusy(2, 14, 13, 15));
    		assertFalse("Should be free between meetings", person.isBusy(2, 14, 11, 12));
    		assertFalse("Should be free on different day", person.isBusy(2, 15, 8, 10));
    		
    	} catch (TimeConflictException e) {
    		fail("Should not throw exception: " + e.getMessage());
    	}
    }
    
    @Test
    public void testPersonAgendaPrinting() {
    	Person person = new Person("Alice Cooper");
    	try {
    		// Book vacation
    		Meeting vacation = new Meeting(6, 15, "Summer Vacation");
    		person.addMeeting(vacation);
    		// Book meeting on different day
    		Room room = new Room("MeetingRoom");
    		ArrayList<Person> attendees = new ArrayList<>();
    		attendees.add(person);
    		Meeting meeting = new Meeting(6, 20, 10, 12, attendees, room, "Team Meeting");
    		person.addMeeting(meeting);
    		
    		// Print monthly agenda
    		String monthly = person.printAgenda(6);
    		assertTrue("Monthly agenda should contain vacation", monthly.contains("Summer Vacation"));
    		assertTrue("Monthly agenda should contain meeting", monthly.contains("Team Meeting"));
    		
    		// Print daily agenda for vacation day
    		String vacationDay = person.printAgenda(6, 15);
    		assertTrue("Vacation day agenda should show vacation", vacationDay.contains("Summer Vacation"));
    		
    		// Print daily agenda for meeting day
    		String meetingDay = person.printAgenda(6, 20);
    		assertTrue("Meeting day agenda should show meeting", meetingDay.contains("Team Meeting"));
    		
    	} catch (TimeConflictException e) {
    		fail("Should not throw exception: " + e.getMessage());
    	}
    }
    
    @Test
    public void testPersonMeetingConflict() {
    	Person person = new Person("Charlie Brown");
    	try {
    		Meeting meeting1 = new Meeting(3, 5, 9, 11, null, null, "First Meeting");
    		person.addMeeting(meeting1);
    		
    		Meeting conflicting = new Meeting(3, 5, 10, 12, null, null, "Conflicting Meeting");
    		person.addMeeting(conflicting);
    		fail("Should throw exception for person conflict");
    	} catch (TimeConflictException e) {
    		assertTrue("Exception should mention attendee name", e.getMessage().contains("Charlie Brown"));
    		assertTrue("Exception should mention conflict", e.getMessage().contains("Conflict"));
    	}
    }
}
