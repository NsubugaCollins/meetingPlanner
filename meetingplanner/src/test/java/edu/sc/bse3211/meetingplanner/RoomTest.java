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
    
    // Additional tests for comprehensive coverage
    
    @Test
    public void testBookRoomForWholeDay() {
    	Room room = new Room("Conference Room A");
    	try {
    		Meeting allDay = new Meeting(9, 10, "All Day Workshop");
    		room.addMeeting(allDay);
    		assertTrue("Room should be busy all day", room.isBusy(9, 10, 0, 23));
    		assertTrue("Room should be busy during any time", room.isBusy(9, 10, 14, 16));
    	} catch (TimeConflictException e) {
    		fail("Whole day booking should succeed: " + e.getMessage());
    	}
    }
    
    @Test
    public void testRoomAvailabilityCheck() {
    	Room room = new Room("Meeting Room 101");
    	try {
    		// Book morning slot
    		Meeting morning = new Meeting(4, 22, 9, 11, null, null, "Morning Briefing");
    		room.addMeeting(morning);
    		// Book evening slot
    		Meeting evening = new Meeting(4, 22, 18, 20, null, null, "Evening Review");
    		room.addMeeting(evening);
    		
    		// Check availability
    		assertTrue("Room should be busy morning", room.isBusy(4, 22, 9, 11));
    		assertTrue("Room should be busy evening", room.isBusy(4, 22, 18, 20));
    		assertFalse("Room should be free midday", room.isBusy(4, 22, 12, 14));
    		assertFalse("Room should be free next day", room.isBusy(4, 23, 9, 11));
    		
    	} catch (TimeConflictException e) {
    		fail("Should not throw exception: " + e.getMessage());
    	}
    }
    
    @Test
    public void testRoomAgendaPrinting() {
    	Room room = new Room("Auditorium");
    	try {
    		// Book maintenance (whole day)
    		Meeting maintenance = new Meeting(7, 4, "Maintenance Day");
    		room.addMeeting(maintenance);
    		// Book meeting on different day
    		ArrayList<Person> attendees = new ArrayList<>();
    		attendees.add(new Person("Attendee"));
    		Meeting meeting = new Meeting(7, 8, 13, 15, attendees, room, "Board Meeting");
    		room.addMeeting(meeting);
    		
    		// Print monthly agenda
    		String monthly = room.printAgenda(7);
    		assertTrue("Monthly agenda should contain maintenance", monthly.contains("Maintenance Day"));
    		assertTrue("Monthly agenda should contain meeting", monthly.contains("Board Meeting"));
    		
    		// Print daily agenda for maintenance day
    		String maintDay = room.printAgenda(7, 4);
    		assertTrue("Maintenance day agenda should show maintenance", maintDay.contains("Maintenance Day"));
    		
    		// Print daily agenda for meeting day
    		String meetDay = room.printAgenda(7, 8);
    		assertTrue("Meeting day agenda should show meeting", meetDay.contains("Board Meeting"));
    		
    	} catch (TimeConflictException e) {
    		fail("Should not throw exception: " + e.getMessage());
    	}
    }
    
    @Test
    public void testRoomBookingConflict() {
    	Room room = new Room("Lab 201");
    	try {
    		Meeting meeting1 = new Meeting(5, 12, 10, 12, null, null, "First Booking");
    		room.addMeeting(meeting1);
    		
    		Meeting overlapping = new Meeting(5, 12, 11, 13, null, null, "Overlapping Booking");
    		room.addMeeting(overlapping);
    		fail("Should throw exception for room conflict");
    	} catch (TimeConflictException e) {
    		assertTrue("Exception should mention room ID", e.getMessage().contains("Lab 201"));
    		assertTrue("Exception should mention conflict", e.getMessage().contains("Conflict"));
    	}
    }
}
