/*
 * AUTHOR:  William Cordero
 * DATE:    05/2018
 * PURPOSE: Access text files and convert to ArrayLists
 */
package spelChek;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class DataAccess {
    
    public ArrayList<String> loadData(String path, String fileName) throws FileNotFoundException {
        File f = new File(fileName);
        if(!f.isFile()) {
            f = new File(path + fileName);
        }
        return chop(f);
    }
    
    public ArrayList<String> loadData(File file) throws FileNotFoundException {
        return chop(file);
    }
    
    private ArrayList<String> chop(File file) throws FileNotFoundException {
        ArrayList<String> output = new ArrayList<>();

        try (Scanner s = new Scanner(file)) {
            while(s.hasNext()) {
                output.add(s.next());
            }
        }
        
        return output;
    }
}
