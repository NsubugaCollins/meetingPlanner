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
}
