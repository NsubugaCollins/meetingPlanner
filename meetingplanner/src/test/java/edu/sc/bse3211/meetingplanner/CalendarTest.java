package edu.sc.bse3211.meetingplanner;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;
import java.util.ArrayList;

public class CalendarTest {
	// Add test methods here. 
	// You are not required to write tests for all classes.
	
	@Test
	public void testAddMeeting_holiday() {
		// Create Janan Luwum holiday
		Calendar calendar = new Calendar();
		// Add to calendar object.
		try {
			Meeting janan = new Meeting(2, 16, "Janan Luwum");
			calendar.addMeeting(janan);
			// Verify that it was added.
			Boolean added = calendar.isBusy(2, 16, 0, 23);
			assertTrue("Janan Luwum Day should be marked as busy on the calendar",added);
		} catch(TimeConflictException e) {
			fail("Should not throw exception: " + e.getMessage());
		}
	}
	
	@Test
	public void testAddMeeting_normal() {
		Calendar calendar = new Calendar();
		try {
			Meeting meeting = new Meeting(1, 15, 9, 10, null, null, "Normal meeting");
			calendar.addMeeting(meeting);
			assertTrue("Meeting should be added successfully", calendar.isBusy(1, 15, 9, 10));
		} catch (TimeConflictException e) {
			fail("Should not throw exception: " + e.getMessage());
		}
	}
	
	@Test
	public void testAddMeeting_invalidMonth() {
		Calendar calendar = new Calendar();
		try {
			Meeting meeting = new Meeting(13, 15, 9, 10, null, null, "Invalid month");
			calendar.addMeeting(meeting);
			fail("Should throw TimeConflictException for invalid month");
		} catch (TimeConflictException e) {
			assertTrue("Exception message should mention month", e.getMessage().contains("Month"));
		}
	}
	
	@Test
	public void testAddMeeting_invalidDay() {
		Calendar calendar = new Calendar();
		try {
			Meeting meeting = new Meeting(1, 32, 9, 10, null, null, "Invalid day");
			calendar.addMeeting(meeting);
			fail("Should throw TimeConflictException for invalid day");
		} catch (TimeConflictException e) {
			assertTrue("Exception message should mention day", e.getMessage().contains("Day"));
		}
	}
	
	@Test
	public void testAddMeeting_invalidTime() {
		Calendar calendar = new Calendar();
		try {
			Meeting meeting = new Meeting(1, 15, 25, 10, null, null, "Invalid time");
			calendar.addMeeting(meeting);
			fail("Should throw TimeConflictException for invalid time");
		} catch (TimeConflictException e) {
			assertTrue("Exception message should mention hour", e.getMessage().contains("hour"));
		}
	}
	
	@Test
	public void testAddMeeting_endBeforeStart() {
		Calendar calendar = new Calendar();
		try {
			Meeting meeting = new Meeting(1, 15, 10, 9, null, null, "End before start");
			calendar.addMeeting(meeting);
			fail("Should throw TimeConflictException for end before start");
		} catch (TimeConflictException e) {
			assertTrue("Exception message should mention starts before ends", e.getMessage().contains("starts before"));
		}
	}
	
	@Test
	public void testAddMeeting_conflict() {
		Calendar calendar = new Calendar();
		try {
			Meeting meeting1 = new Meeting(1, 15, 9, 11, null, null, "First meeting");
			calendar.addMeeting(meeting1);
			Meeting meeting2 = new Meeting(1, 15, 10, 12, null, null, "Conflicting meeting");
			calendar.addMeeting(meeting2);
			fail("Should throw TimeConflictException for conflict");
		} catch (TimeConflictException e) {
			assertTrue("Exception message should mention overlap", e.getMessage().contains("Overlap"));
		}
	}
	
	@Test
	public void testIsBusy_normal() {
		Calendar calendar = new Calendar();
		try {
			Meeting meeting = new Meeting(1, 15, 9, 11, null, null, "Meeting");
			calendar.addMeeting(meeting);
			assertTrue("Should be busy during meeting", calendar.isBusy(1, 15, 9, 11));
			assertTrue("Should be busy overlapping start", calendar.isBusy(1, 15, 8, 10));
			assertTrue("Should be busy overlapping end", calendar.isBusy(1, 15, 10, 12));
		} catch (TimeConflictException e) {
			fail("Should not throw exception: " + e.getMessage());
		}
	}
	
	@Test
	public void testIsBusy_invalidDate() {
		Calendar calendar = new Calendar();
		try {
			calendar.isBusy(13, 15, 9, 11);
			fail("Should throw TimeConflictException for invalid month");
		} catch (TimeConflictException e) {
			assertTrue("Exception should mention month", e.getMessage().contains("Month"));
		}
	}
	
	@Test
	public void testPrintAgenda_month() {
		Calendar calendar = new Calendar();
		Room room = new Room("TestRoom");
		ArrayList<Person> attendees = new ArrayList<>();
		attendees.add(new Person("TestPerson"));
		Meeting meeting = new Meeting(1, 15, 9, 11, attendees, room, "Meeting");
		try {
			calendar.addMeeting(meeting);
			String agenda = calendar.printAgenda(1);
			assertTrue("Agenda should contain meeting description", agenda.contains("Meeting"));
		} catch (TimeConflictException e) {
			fail("Should not throw exception: " + e.getMessage());
		}
	}
	
	@Test
	public void testPrintAgenda_day() {
		Calendar calendar = new Calendar();
		Room room = new Room("TestRoom");
		ArrayList<Person> attendees = new ArrayList<>();
		attendees.add(new Person("TestPerson"));
		Meeting meeting = new Meeting(1, 15, 9, 11, attendees, room, "Meeting");
		try {
			calendar.addMeeting(meeting);
			String agenda = calendar.printAgenda(1, 15);
			assertTrue("Agenda should contain meeting description", agenda.contains("Meeting"));
		} catch (TimeConflictException e) {
			fail("Should not throw exception: " + e.getMessage());
		}
	}
}
