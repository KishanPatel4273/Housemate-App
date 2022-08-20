package edu.vassar.cmpu203.nextgenpos.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class SaleTest {

    @Test
    void addLineItem() {
        Sale s = new Sale();
        s.addLineItem("marker", 10);
        assertEquals(0, s.getLineItems().size());
    }

    @Test
    void getLineItems() {
    }

    /**
     *
     */
    @Test
    void testToString() {
        Sale s = new Sale();
        assertEquals("", s.toString(), "non-empty string for empty sale");

        s.addLineItem("orange", 20);
        assertEquals("20 units of orange\n", s.toString());
        s.addLineItem("apple", 1);
        String expectedStr = "20 units of orange\n1 units of apple\n";
        assertEquals(expectedStr, s.toString());
        //assertTrue(s.toString().equals(expectedStr));
        //assertFalse(!s.toString().equals(expectedStr));
    }
}