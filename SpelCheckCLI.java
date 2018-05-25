/*
 * AUTHOR:  William Cordero
 * DATE:    05/2018
 * PURPOSE: A CLI to access a spell checker with two text files
 */
package spelChek;

import java.util.ArrayList;

public class SpelCheckCLI {
    public static void main(String[] args) {
        final String PATH = System.getProperty("user.dir") + '\\';
        String dataFile = args[0];
        String dictFile = args[1];
        ArrayList<String> in = new ArrayList<>();
        ArrayList<String> dict = new ArrayList<>();
        ArrayList<String> unknown;
        
        //Use DataAccess to load data into ArrayLists
        DataAccess da = new DataAccess();
        try {
            in = da.loadData(PATH, dataFile);
            dict = da.loadData(PATH, dictFile);
            
            //Spell check the data using the dictionary
            SpelChek spellChecker = new SpelChek();
            unknown = spellChecker.getUnknown(in, dict);

            for (String item : unknown) {
                System.out.println(item + " is an unknown word");
            }
        } catch (Exception ex) {
            System.out.println("There was an error running the program.");
        }

        
        
        
    }
}
