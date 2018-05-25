/*
 * AUTHOR:  William Cordero
 * DATE:    05/2018
 * PURPOSE: Check the spelling of words, given a dictionary
 */
package spelChek;

import java.util.ArrayList;

public class SpelChek {
    //Public Accessor Methods
    
    public ArrayList<String> getUnknown(ArrayList<String> in, ArrayList<String> dict) {
        input = in;
        dictionary = dict;
        return checkSpelling();
    }
    
    //Class variable
    private ArrayList<String> input;
    private ArrayList<String> dictionary;
    private ArrayList<String> output = new ArrayList<>();
    
    private ArrayList<String> checkSpelling() {
        //for each word in input, check for it in dictionary
        for (String word : input) {
            if (!dictionary.contains(word)) {//if word not contained in dict
                output.add(word);//add to output
            }
        }
        return output;
    }
}
