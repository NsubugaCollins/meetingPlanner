package edu.sc.bse3211.meetingplanner;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
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
	
	// Additional tests to align with testing instructions
	
	@Test
	public void testBookVacation_normal() {
		Calendar calendar = new Calendar();
		try {
			// Book a vacation (whole day)
			Meeting vacation = new Meeting(7, 15, "Summer Vacation");
			calendar.addMeeting(vacation);
			// Verify the whole day is busy
			assertTrue("Should be busy all day for vacation", calendar.isBusy(7, 15, 0, 23));
			assertTrue("Should be busy in morning", calendar.isBusy(7, 15, 9, 10));
			assertTrue("Should be busy in evening", calendar.isBusy(7, 15, 20, 22));
		} catch (TimeConflictException e) {
			fail("Vacation booking should succeed: " + e.getMessage());
		}
	}
	
	@Test
	public void testBookVacation_invalidDate() {
		Calendar calendar = new Calendar();
		try {
			Meeting vacation = new Meeting(13, 15, "Invalid Vacation");
			calendar.addMeeting(vacation);
			fail("Should throw TimeConflictException for invalid month");
		} catch (TimeConflictException e) {
			assertTrue("Exception should mention month", e.getMessage().contains("Month"));
		}
	}
	
	@Test
	public void testBookVacation_conflict() {
		Calendar calendar = new Calendar();
		try {
			// Book vacation
			Meeting vacation = new Meeting(7, 15, "Vacation");
			calendar.addMeeting(vacation);
			// Try to book meeting on same day
			Meeting meeting = new Meeting(7, 15, 10, 11, null, null, "Meeting");
			calendar.addMeeting(meeting);
			fail("Should throw TimeConflictException for vacation conflict");
		} catch (TimeConflictException e) {
			assertTrue("Exception should mention overlap", e.getMessage().contains("Overlap"));
		}
	}
	
	@Test
	public void testCheckAvailability_room() {
		Calendar calendar = new Calendar();
		try {
			// Book a meeting
			Room room = new Room("TestRoom");
			ArrayList<Person> attendees = new ArrayList<>();
			attendees.add(new Person("TestPerson"));
			Meeting meeting = new Meeting(3, 10, 14, 16, attendees, room, "Room Meeting");
			calendar.addMeeting(meeting);
			
			// Check availability - should be busy during meeting
			assertTrue("Room should be busy during meeting", calendar.isBusy(3, 10, 14, 16));
			assertTrue("Room should be busy overlapping start", calendar.isBusy(3, 10, 13, 15));
			assertFalse("Room should be free before meeting", calendar.isBusy(3, 10, 12, 13));
			assertFalse("Room should be free after meeting", calendar.isBusy(3, 10, 17, 18));
			
		} catch (TimeConflictException e) {
			fail("Should not throw exception: " + e.getMessage());
		}
	}
	
	@Test
	public void testCheckAvailability_person() {
		Calendar calendar = new Calendar();
		try {
			// Book multiple meetings for a person
			Room room1 = new Room("Room1");
			Room room2 = new Room("Room2");
			ArrayList<Person> attendees1 = new ArrayList<>();
			attendees1.add(new Person("TestPerson"));
			ArrayList<Person> attendees2 = new ArrayList<>();
			attendees2.add(new Person("TestPerson"));
			
			Meeting meeting1 = new Meeting(4, 5, 9, 11, attendees1, room1, "Morning Meeting");
			calendar.addMeeting(meeting1);
			Meeting meeting2 = new Meeting(4, 5, 14, 16, attendees2, room2, "Afternoon Meeting");
			calendar.addMeeting(meeting2);
			
			// Check availability
			assertTrue("Person should be busy during first meeting", calendar.isBusy(4, 5, 9, 11));
			assertTrue("Person should be busy during second meeting", calendar.isBusy(4, 5, 14, 16));
			assertFalse("Person should be free between meetings", calendar.isBusy(4, 5, 12, 13));
			assertFalse("Person should be free different day", calendar.isBusy(4, 6, 9, 11));
			
		} catch (TimeConflictException e) {
			fail("Should not throw exception: " + e.getMessage());
		}
	}
	
	@Test
	public void testPrintAgenda_room() {
		Calendar calendar = new Calendar();
		try {
			// Book multiple meetings
			Room room1 = new Room("Room1");
			Room room2 = new Room("Room2");
			ArrayList<Person> attendees1 = new ArrayList<>();
			attendees1.add(new Person("Person1"));
			ArrayList<Person> attendees2 = new ArrayList<>();
			attendees2.add(new Person("Person2"));
			
			Meeting meeting1 = new Meeting(5, 20, 10, 12, attendees1, room1, "Team Standup");
			calendar.addMeeting(meeting1);
			Meeting meeting2 = new Meeting(5, 20, 15, 17, attendees2, room2, "Client Call");
			calendar.addMeeting(meeting2);
			
			// Print monthly agenda
			String monthlyAgenda = calendar.printAgenda(5);
			assertTrue("Monthly agenda should contain first meeting", monthlyAgenda.contains("Team Standup"));
			assertTrue("Monthly agenda should contain second meeting", monthlyAgenda.contains("Client Call"));
			
			// Print daily agenda
			String dailyAgenda = calendar.printAgenda(5, 20);
			assertTrue("Daily agenda should contain both meetings", 
					  dailyAgenda.contains("Team Standup") && dailyAgenda.contains("Client Call"));
			
		} catch (TimeConflictException e) {
			fail("Should not throw exception: " + e.getMessage());
		}
	}
	
	@Test
	public void testPrintAgenda_person() {
		Calendar calendar = new Calendar();
		try {
			// Book vacation and meeting
			Meeting vacation = new Meeting(8, 1, "Summer Vacation");
			calendar.addMeeting(vacation);
			
			Room room = new Room("Office");
			ArrayList<Person> attendees = new ArrayList<>();
			attendees.add(new Person("TestPerson"));
			Meeting meeting = new Meeting(8, 2, 13, 14, attendees, room, "Doctor Appointment");
			calendar.addMeeting(meeting);
			
			// Print agendas
			String monthlyAgenda = calendar.printAgenda(8);
			assertTrue("Monthly agenda should contain vacation", monthlyAgenda.contains("Summer Vacation"));
			assertTrue("Monthly agenda should contain appointment", monthlyAgenda.contains("Doctor Appointment"));
			
			String vacationDayAgenda = calendar.printAgenda(8, 1);
			assertTrue("Vacation day agenda should show vacation", vacationDayAgenda.contains("Summer Vacation"));
			
		} catch (TimeConflictException e) {
			fail("Should not throw exception: " + e.getMessage());
		}
	}
	
	@Test
	public void testIllegalInputs_comprehensive() {
		Calendar calendar = new Calendar();
		
		// Test invalid month
		try {
			calendar.isBusy(0, 15, 9, 10);
			fail("Should throw exception for month 0");
		} catch (TimeConflictException e) {
			assertTrue("Should mention month", e.getMessage().contains("Month"));
		}
		
		// Test invalid day
		try {
			calendar.isBusy(1, 32, 9, 10);
			fail("Should throw exception for day 32");
		} catch (TimeConflictException e) {
			assertTrue("Should mention day", e.getMessage().contains("Day"));
		}
		
		// Test invalid hour
		try {
			calendar.isBusy(1, 15, 25, 10);
			fail("Should throw exception for hour 25");
		} catch (TimeConflictException e) {
			assertTrue("Should mention hour", e.getMessage().contains("hour"));
		}
		
		// Test end before start
		try {
			calendar.isBusy(1, 15, 15, 10);
			fail("Should throw exception for end before start");
		} catch (TimeConflictException e) {
			assertTrue("Should mention starts before", e.getMessage().contains("starts before"));
		}
	}
}
