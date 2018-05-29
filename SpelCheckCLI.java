/*
 * AUTHOR:  William Cordero
 * DATE:    05/2018
 * PURPOSE: A CLI to access a spell checker with two text files
 */
package spelChek;

import java.util.ArrayList;
import java.util.Scanner;

public class SpelCheckCLI {
    public static void main(String[] args) {
        final String PATH = System.getProperty("user.dir") + '\\';
        ArrayList<String> input = new ArrayList<>();
        ArrayList<String> dict = new ArrayList<>();
        ArrayList<String> unknown;
        Scanner in = new Scanner(System.in);
        
        System.out.println("Welcome to SpelChek.");
        System.out.println("The current working directory is:");
        System.out.println(PATH);
        System.out.println("Please enter the file name or full path of the text "
                + "file you would like to check");
        
        String dataFile = in.nextLine();
        
        System.out.println("Please enter the file name or full path of the "
                + "dictionary");
        
        String dictFile = in.nextLine();
        
        //Use DataAccess to load data into ArrayLists
        DataAccess da = new DataAccess();
        try {
            input = da.loadData(PATH, dataFile);
            dict = da.loadData(PATH, dictFile);
            
            //Spell check the data using the dictionary
            SpelChek spellChecker = new SpelChek();
            unknown = spellChecker.getUnknown(input, dict);

            for (String item : unknown) {
                System.out.println(item + " is an unknown word");
            }
        } catch (Exception ex) {
            System.out.println("There was an error running the program.");
        }

        
        
        
    }
}
