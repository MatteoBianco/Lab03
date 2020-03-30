package it.polito.tdp.spellchecker.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import it.polito.tdp.spellchecker.*;

public class Model {
	
	private List<String> vocabulary; 
	
	
	public Model() {
			this.vocabulary = new ArrayList<String>();
	}


	public void loadDictionary(String language) {
		this.vocabulary = new ArrayList<String>();
		
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
	
	public List<RichWord> spellCheckTextLinear(List<String> inputTextList){
		List<RichWord> textList = new ArrayList<RichWord>();
		boolean correct = false;
		for(String s : inputTextList) {
			correct = false;
			for(String s1 : vocabulary) {
				if(s.equals(s1)) {
					textList.add(new RichWord(s, true));
					correct = true;
				}
			}
			if(!correct)
				textList.add(new RichWord(s, false));
		}
		return textList;
	}
	
	public List<RichWord> spellCheckTextDicotomic(List<String> inputTextList){
		List<RichWord> textList = new ArrayList<RichWord>();
		boolean correct = false;
		for(String s : inputTextList) {
			correct = false;
			int i = vocabulary.size()/2;
			int iterations = 0;
			while(correct || iterations == (int) (Math.log(vocabulary.size())/Math.log(2))) {
				if(vocabulary.get(i).equals(s)) {
					textList.add(new RichWord(s, true));
					correct = true;
				}
				else if(vocabulary.get(i).compareTo(s) < 0) {
					i = i + ((vocabulary.size()-i)/2);
					iterations ++;
					}
				else if(vocabulary.get(i).compareTo(s) > 0) {
					i = i - (i/2);
					iterations ++;
				}
			}
			if(!correct)
				textList.add(new RichWord(s, false));
		}
		return textList;
	}
}
