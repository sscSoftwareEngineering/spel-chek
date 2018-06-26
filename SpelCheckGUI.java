/*
 * AUTHOR:  William Cordero
 * DATE:    06/2018
 * PURPOSE: A GUI to access a spell checker with two text files
 */
package spelChek;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
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
        FileChooser fileChooser = new FileChooser();
        Label lblData = new Label(INPUT_TEXT);
        Label lblDict = new Label(DICT_TEXT);
        TextArea txtOutput = new TextArea();
        
        
        primaryStage.setTitle("SpelChek");
        
        altCheck.setTitle("Check Error");
        altCheck.setHeaderText("Cannot spell check");
        altCheck.setContentText("You must select files for input and dictionary first!");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        txtOutput.setEditable(false);
        txtOutput.setPrefRowCount(30);
        txtOutput.setText("Welcome to SpelChek.\nUse the Input and Dictionary "
                + "buttons above to select the corresponding text files.\n"
                + "Then use the check button to spell check.");
        

        Button btnData = new Button("Input");
        btnData.setOnAction(e -> {
            File file = fileChooser.showOpenDialog(primaryStage);
            if (file != null) {
                input = getArrayList(file);
                lblData.setText(file.getPath());
            }
        });
        
        Button btnDict = new Button("Dictionary");
        btnDict.setOnAction(e -> {
            File file = fileChooser.showOpenDialog(primaryStage);
            if (file != null) {
                dict = getArrayList(file);
                lblDict.setText(file.getPath());
            }
        });
        
        Button btnCheck = new Button("Check");
        btnCheck.setOnAction(e -> {
            if (lblData.getText().equals(INPUT_TEXT) || lblDict.getText().equals(DICT_TEXT)) {
                altCheck.showAndWait();
            } else {
                txtOutput.clear();
                //Use DataAccess to load data into ArrayLists
                SpelChek spellChecker = new SpelChek();

                unknown = spellChecker.getUnknown(input, dict);

                unknown.forEach((item) -> {
                    txtOutput.appendText(item + " is an unknown word\n");
                });
            }
            
        });
        
        VBox layout = new VBox();
        HBox winData = new HBox();
        HBox winDict = new HBox();
        layout.setSpacing(10);
        layout.getChildren().addAll(winData, winDict, btnCheck, txtOutput);
        winData.setSpacing(10);
        winData.getChildren().addAll(btnData, lblData);
        winDict.setSpacing(10);
        winDict.getChildren().addAll(btnDict, lblDict);
        
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
