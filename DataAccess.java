/*
 * AUTHOR:  William Cordero
 * DATE:    05/2018
 * PURPOSE: Access text files or databases and convert to ArrayLists
 */
package spelChek;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    
    public static ArrayList<String> loadData(Connection conn) throws SQLException, ClassNotFoundException {
        ArrayList<String> output = new ArrayList<>();
        PreparedStatement statement = conn.prepareStatement("select * from spelling.word");
        
        ResultSet result = statement.executeQuery();
        while(result.next()){
            output.add(result.getString(1));
        }
        
        return output;
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
    
    public static Connection getConnection(String user, String pass) throws SQLException, ClassNotFoundException {
        String sqlURL = "jdbc:mysql://localhost:3306/spelling";
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection(sqlURL, user, pass);
        return conn;
    }
}
