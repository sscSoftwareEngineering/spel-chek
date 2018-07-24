/*
 * AUTHOR:  William Cordero
 * DATE:    07/2018
 * PURPOSE: A GUI to access a spell checker with two text files or a database
 */
package spelChek;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class SpelCheckGUI extends Application {
    final String INPUT_TEXT = "Input";
    final String DICT_TEXT = "Dictionary";
    ArrayList<String> input;
    ArrayList<String> dict;
    ArrayList<String> unknown;
    
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Alert altCheck = new Alert(AlertType.ERROR);
        Button btnCheck = new Button("Check");
        Button btnData = new Button("Test File");
        Button btnDict = new Button("Dictionary File");
        Button btnSQL = new Button("Dictionary Database");
        Button btnSQLconnect = new Button("Connect");
        FileChooser fileChooser = new FileChooser();
        Label lblData = new Label(INPUT_TEXT);
        Label lblDict = new Label(DICT_TEXT);
        Label lblSQLusr = new Label("DB-Username");
        Label lblSQLpass = new Label("DB-Password");
        TextArea txtOutput = new TextArea();
        TextField txtSQLusr = new TextField("root");
        TextField txtSQLpass = new TextField("cen3024");
        
        VBox layout = new VBox();
        HBox winData = new HBox();
        HBox winDict = new HBox();
        VBox winSQL = new VBox();
        
        primaryStage.setTitle("SpelChek");
        //Disable the check button until a dictionary is selected.
        btnCheck.setDisable(true);
        
        altCheck.setTitle("Check Error");
        altCheck.setHeaderText("Cannot spell check");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        txtOutput.setEditable(false);
        txtOutput.setPrefRowCount(30);
        txtOutput.setText("Welcome to SpelChek.\nUse the Input and Dictionary "
                + "File buttons above to select the corresponding text files.\n"
                + "Or use the Dictionary Database button and enter "
                + "your database credentials to use a dictionary database.\n"
                + "Then use the check button to spell check.");
        
        btnData.setOnAction(e -> {
            File file = fileChooser.showOpenDialog(primaryStage);
            if (file != null) {
                input = getArrayList(file);
                lblData.setText(file.getPath());
            }
        });
        
        btnDict.setOnAction(e -> {
            btnSQL.setDisable(true);
            File file = fileChooser.showOpenDialog(primaryStage);
            if (file != null) {
                dict = getArrayList(file);
                lblDict.setText(file.getPath());
                btnCheck.setDisable(false);
            }
        });
        
        btnSQL.setOnAction(e -> {
            btnDict.setDisable(true);
            btnSQL.setDisable(true);//Avoid duplicate UI elements
            btnCheck.setDisable(true);
            winSQL.getChildren().addAll(lblSQLusr, txtSQLusr, lblSQLpass, txtSQLpass, btnSQLconnect);
        });
        
        btnSQLconnect.setOnAction(e -> {
            //Do a database check
                String username = txtSQLusr.getText();
                String password = txtSQLpass.getText();
  
                try {
                    btnSQLconnect.setDisable(true);
                    Connection conn = DataAccess.getConnection(username, password);
                    btnSQLconnect.setText("Connected");
                    
                    dict = DataAccess.loadData(conn);
                    
                    btnCheck.setDisable(false);
                } catch (SQLException ex) {
                    btnSQLconnect.setDisable(false);
                    altCheck.setContentText("Error connecting to the database. " 
                            + "Your database credentials may be incorrect.");
                    altCheck.showAndWait();
                    System.out.println(ex);
                } catch (ClassNotFoundException ex) {
                    btnSQLconnect.setDisable(false);
                    altCheck.setContentText("There was a problem loading the jdbc driver.");
                }
        });
        
        
        btnCheck.setOnAction(e -> {
            SpelChek spellChecker = new SpelChek();
            
            txtOutput.clear();
            
            unknown = spellChecker.getUnknown(input, dict);
            unknown.forEach((item) -> {
                txtOutput.appendText(item + " is an unknown word\n");
            });
            
        });
       
        layout.setSpacing(10);
        layout.getChildren().addAll(winData, winDict, winSQL, btnCheck, txtOutput);
        winData.setSpacing(10);
        winData.getChildren().addAll(btnData, lblData);
        winDict.setSpacing(10);
        winDict.getChildren().addAll(btnDict, lblDict);
        winSQL.setSpacing(10);
        winSQL.getChildren().addAll(btnSQL);
        
        Scene scene = new Scene(layout, 600, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private ArrayList<String> getArrayList(File file) {
        DataAccess da = new DataAccess();
        ArrayList<String> output = new ArrayList<>();
        Alert altFile = new Alert(AlertType.ERROR);
        
        try {
            output = da.loadData(file);
        } catch (FileNotFoundException ex) {
            altFile.setTitle("File Error");
            altFile.setHeaderText("Cannot spell check");
            altFile.setContentText("Could not find file");
        }
                
        return output;
    }
}
