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
    public ArrayList<String> loadData(String path, String file) throws Exception {
        ArrayList<String> output = new ArrayList<>();
        File f = new File(file);
        if(!f.isFile()) {
            f = new File(path + file);
        }
        try {
            Scanner s = new Scanner(f);
            while(s.hasNext()) {
                output.add(s.next());
            }
            s.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Error: Could not find " + file);
            System.out.println("Please enter the full file path "
                    + "or put the file in the following directory:");
            System.out.println(path);
            throw new Exception(ex);
        }
        
        return output;
    }
}
