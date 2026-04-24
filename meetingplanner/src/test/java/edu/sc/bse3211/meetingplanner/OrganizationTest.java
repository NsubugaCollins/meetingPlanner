package edu.sc.bse3211.meetingplanner;

import static org.junit.Assert.*;
import org.junit.Test;

public class OrganizationTest {
	// Add test methods here. 
    // You are not required to write tests for all classes.
    
    @Test
    public void testGetEmployee_exists() {
    	Organization org = new Organization();
    	try {
    		Person person = org.getEmployee("Namugga Martha");
    		assertEquals("Name should match", "Namugga Martha", person.getName());
    	} catch (Exception e) {
    		fail("Should not throw exception: " + e.getMessage());
    	}
    }
    
    @Test
    public void testGetEmployee_notExists() {
    	Organization org = new Organization();
    	try {
    		Person person = org.getEmployee("Nonexistent Person");
    		fail("Should throw exception for nonexistent employee");
    	} catch (Exception e) {
    		assertTrue("Exception should mention employee", e.getMessage().contains("employee"));
    	}
    }
    
    @Test
    public void testGetRoom_exists() {
    	Organization org = new Organization();
    	try {
    		Room room = org.getRoom("LLT6A");
    		assertEquals("ID should match", "LLT6A", room.getID());
    	} catch (Exception e) {
    		fail("Should not throw exception: " + e.getMessage());
    	}
    }
    
    @Test
    public void testGetRoom_notExists() {
    	Organization org = new Organization();
    	try {
    		Room room = org.getRoom("Nonexistent Room");
    		fail("Should throw exception for nonexistent room");
    	} catch (Exception e) {
    		assertTrue("Exception should mention room", e.getMessage().contains("room"));
    	}
    }
}
