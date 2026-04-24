package edu.sc.bse3211.meetingplanner;

import static org.junit.Assert.*;
import org.junit.Test;
import java.util.ArrayList;

public class RoomTest {
	// Add test methods here. 
    // You are not required to write tests for all classes.
    
    @Test
    public void testRoomConstructor() {
    	Room room = new Room("LLT6A");
    	assertEquals("ID should be 'LLT6A'", "LLT6A", room.getID());
    }
    
    @Test
    public void testAddMeeting_normal() {
    	Room room = new Room("LLT6A");
    	Meeting meeting = new Meeting(1, 15, 9, 11, null, null, "Meeting");
    	try {
    		room.addMeeting(meeting);
    		assertTrue("Room should be busy", room.isBusy(1, 15, 9, 11));
    	} catch (TimeConflictException e) {
    		fail("Should not throw exception: " + e.getMessage());
    	}
    }
    
    @Test
    public void testAddMeeting_conflict() {
    	Room room = new Room("LLT6A");
    	Meeting meeting1 = new Meeting(1, 15, 9, 11, null, null, "First meeting");
    	Meeting meeting2 = new Meeting(1, 15, 10, 12, null, null, "Conflicting meeting");
    	try {
    		room.addMeeting(meeting1);
    		room.addMeeting(meeting2);
    		fail("Should throw TimeConflictException for conflict");
    	} catch (TimeConflictException e) {
    		assertTrue("Exception should mention conflict", e.getMessage().contains("Conflict"));
    	}
    }
    
    @Test
    public void testIsBusy() {
    	Room room = new Room("LLT6A");
    	Meeting meeting = new Meeting(1, 15, 9, 11, null, null, "Meeting");
    	try {
    		room.addMeeting(meeting);
    		assertTrue("Should be busy", room.isBusy(1, 15, 9, 11));
    		assertFalse("Should not be busy at different time", room.isBusy(1, 15, 12, 13));
    	} catch (TimeConflictException e) {
    		fail("Should not throw exception: " + e.getMessage());
    	}
    }
    
    @Test
    public void testPrintAgenda() {
    	Room room = new Room("LLT6A");
    	ArrayList<Person> attendees = new ArrayList<>();
    	attendees.add(new Person("TestPerson"));
    	Meeting meeting = new Meeting(1, 15, 9, 11, attendees, room, "Meeting");
    	try {
    		room.addMeeting(meeting);
    		String agenda = room.printAgenda(1, 15);
    		assertTrue("Agenda should contain meeting", agenda.contains("Meeting"));
    	} catch (TimeConflictException e) {
    		fail("Should not throw exception: " + e.getMessage());
    	}
    }
}
