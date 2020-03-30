package it.polito.tdp.spellchecker.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import it.polito.tdp.spellchecker.*;

public class Model {
	
	private Set<String> vocabulary; 
	
	
	public Model() {
			this.vocabulary = new HashSet<String>();
	}


	public void loadDictionary(String language) {
		this.vocabulary = new HashSet<String>();
		
		try {
			FileReader fr = new FileReader("C:\\Users\\luca1\\Desktop\\Matte\\Lab03\\Lab03_SpellChecker\\target\\classes\\" + language + ".txt") ;
			BufferedReader br = new BufferedReader(fr);
			String word = "";
			while((word = br.readLine()) != null) {
				vocabulary.add(word);
			}
			br.close();			
		} catch(IOException e) {
			System.out.println("Errore nella lettura dei file");
		}
		
	}
	
	public List<RichWord> spellTextCheck(List<String> inputTextList){
		List<RichWord> textList = new LinkedList<RichWord>();
		for(String s : inputTextList) {
			if(vocabulary.contains(s)) {
				textList.add(new RichWord(s, true));
			}
			else textList.add(new RichWord(s, false));
		}
		return textList;
	}
}
