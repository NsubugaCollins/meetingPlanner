package edu.sc.bse3211.meetingplanner;

import static org.junit.Assert.*;
import org.junit.Test;
import java.util.ArrayList;

public class MeetingTest {
	// Add test methods here. 
    // You are not required to write tests for all classes.
    
    @Test
    public void testMeetingConstructor() {
    	Meeting meeting = new Meeting(1, 15, 9, 11, null, null, "Test meeting");
    	assertEquals("Month should be 1", 1, meeting.getMonth());
    	assertEquals("Day should be 15", 15, meeting.getDay());
    	assertEquals("Start time should be 9", 9, meeting.getStartTime());
    	assertEquals("End time should be 11", 11, meeting.getEndTime());
    	assertEquals("Description should be 'Test meeting'", "Test meeting", meeting.getDescription());
    }
    
    @Test
    public void testToString() {
    	Room room = new Room("TestRoom");
    	Person person = new Person("TestPerson");
    	ArrayList<Person> attendees = new ArrayList<>();
    	attendees.add(person);
    	Meeting meeting = new Meeting(1, 15, 9, 11, attendees, room, "Test meeting");
    	String str = meeting.toString();
    	assertTrue("toString should contain date", str.contains("1/15"));
    	assertTrue("toString should contain times", str.contains("9 - 11"));
    	assertTrue("toString should contain room", str.contains("TestRoom"));
    	assertTrue("toString should contain description", str.contains("Test meeting"));
    	assertTrue("toString should contain attendee", str.contains("TestPerson"));
    }
}
