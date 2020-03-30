/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.spellchecker;

import java.math.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import it.polito.tdp.spellchecker.model.Model;
import it.polito.tdp.spellchecker.model.RichWord;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class FXMLController {
	
	private Model model;

    @FXML
    private ComboBox<String> comboBox;

    @FXML
    private TextArea txtArea;

    @FXML
    private Button spellCheck;

    @FXML
    private TextArea wrongWords;

    @FXML
    private Label numErrors;

    @FXML
    private Button clearText;

    @FXML
    private Label timeNeeded;

    @FXML
    void doClearText(ActionEvent event) {
    	txtArea.clear();
    	wrongWords.clear();
    	numErrors.setText("");
    	timeNeeded.setText("");
    }

    @FXML
    void doSpellCheck(ActionEvent event) {
    	
    	long timeStart = System.nanoTime();
    	
    	wrongWords.clear();
    	
    	String text = txtArea.getText();
    	if(text.length() == 0 || text == null) {
    		wrongWords.setText("Inserire delle parole per poter procedere con il controllo ortografico.\n");
    		return;
    	}
    	text = text.toLowerCase().replaceAll("[.,\\/#!?$%\\^&\\+*;:{}=\\-_`~()\\[\\]\"]", " ");
    	
    	String language = comboBox.getValue();
    	if(language == null) {
    		wrongWords.setText("Selezionare una lingua per poter procedere con il controllo ortografico.\n");
    		return;
    	}
    	model.loadDictionary(language);
    	
    	String[] words = text.split("\\s+");
    	List<String> inputTextList = new ArrayList<String>();
    	for(String s : words) {
    		inputTextList.add(s);
    	}
    	List<RichWord> checkedText = new ArrayList<RichWord>();
    	checkedText = model.spellCheckTextLinear(inputTextList);
    	String errors = "";
    	int numWrong = 0;
    	for(RichWord rw : checkedText) {
    		if(!rw.isCorrect()) {
    			errors += rw.toString();
    			numWrong ++;
    		}
    	}
    	wrongWords.appendText(errors);
    	numErrors.setText("Il testo contiene " + numWrong + " errori\n");
    	
    	long timeEnd = System.nanoTime();
    	double time = ((timeEnd - timeStart)/(Math.pow(10, 9)));
    	timeNeeded.setText("Controllo ortografico completato in " + time + " secondi\n");    	
    		   		
    }
		
	@FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {

    }
    
    public void setModel(Model model) {
		this.model = model;
		comboBox.getItems().addAll("Inglese","Italiano","Francese","Spagnolo");
	}
}


