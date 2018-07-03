/*
 * AUTHOR:  William Cordero
 * DATE:    07/2018
 * PURPOSE: A JUnit test to verify the accuracy of spelcheck with two text files
 */
package spelChek;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;

public class SpelChekTest {
    
    /**
     * Test of getUnknown method, of class SpelChek.
     */
    @Test
    public void testGetUnknown() {
        final String PATH = "c:\\temp\\";
        
        System.out.println("getUnknown");
        
        DataAccess da = new DataAccess();
        
        try {
            //Load test files
            ArrayList<String> in = da.loadData(PATH, "c:\\temp\\testStates.txt");
            ArrayList<String> dict = da.loadData(PATH, "c:\\temp\\dictionary.txt");

            SpelChek instance = new SpelChek();
            
            //Expected results using above files
            ArrayList<String> expResult = new ArrayList<>();
            expResult.add("Alabama1");
            expResult.add("Alaka");
            expResult.add("Iwa");
            expResult.add("Mariland");

            ArrayList<String> result = instance.getUnknown(in, dict);
            
            assertEquals(expResult, result);
        } catch(FileNotFoundException ex) {
            fail("Could not load the test files.");
        }
            
    }
    
}
