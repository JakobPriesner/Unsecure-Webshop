package utils.logic;

import org.junit.Test;

import static org.junit.Assert.*;


public class AlwaysFailingTest {
    @Test
    public void testFailing() {
        fail();
    }

    @Test
    public void testWorking(){
        // do nothing
    }
}
