package babyNames;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class Markov {

	private int gender;
	private int min;
	private int max;
	private int order;
	private int numNames;
	private HashMap<String, HashMap<String, Double>> seqMap; 
	private ArrayList<String> existName;
	
	
	
	public Markov(int min, int max, int order, int numNames, int gender) {
		
		this.order = order;
		this.min = min + this.order*2;
		this.max = max + this.order*2;
		this.numNames = numNames; 
		this.gender = gender;
		seqMap = new HashMap<String, HashMap<String, Double>>();
		existName = new ArrayList<String>();
	}
	
	// organizes which file to select from
	
	public void organize()  {												
		File file;
		if(gender ==1) {
			file = new File("namesBoys.txt");
		}
		
		else {
			file = new File("namesGirls.txt");
		}
		
		try {
			//file = new File("testNames.txt");
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String name;
		    while ((name = reader.readLine()) != null) {
		    	existName.add(name);
		        seqGenerator(name);							//calls method which gets each sequence based on Markov order and looks for next letters
		    }
		}
		 catch (IOException e) {
		    e.printStackTrace();
		}
		
		
	}
	
	//collects the sequences and arranges the counts
	
	public void seqGenerator(String name) {
		String newName = "_".repeat(order) + name + "_".repeat(order);  //add dashes so we know when to start and when to end!
		newName = newName.trim();
		//System.out.println(newName + " : " + newName.length() );
		//System.out.println();
		
		for(int i =0; i<(newName.length()-order); i++) {										//go through each name
			HashMap<String,Double> letters = new HashMap<String,Double>();						//each new sequence(Key) gets a HashMap(which will be it's possible next letters)
			
			String seq = newName.substring(i,i+order);											//get a sequence depending on Markov order
			String predict = newName.substring(i+order, i+order+1);								//get next character
			
			if(!seqMap.containsKey(seq)) {														//checks and adds sequence to HashMap if it doesn't exist
				letters.put(predict,1.0);														//adds the subsequent letter with a count 1
				letters.put("total",1.0);														//updates total for total amount of letters after a given sequence
				seqMap.put(seq, letters);														
			}
			
			else {
				letters = seqMap.get(seq);														//if sequence does exist already in HashMap seqMap
				
				if(letters.containsKey(predict)) {
					letters.put("total",letters.get("total")+1.0);								//if the sequence already has seen the next letter then update count and total count
					letters.put(predict, letters.get(predict)+1.0);
					
					
				}
				
				else {
					letters.put("total",letters.get("total")+1.0);								//if it hasn't seen this next letter then add it the HashMap inside the seqMap 
					letters.put(predict,1.0);
				}
				
				
			}
			//letter.put(predict,1);
			
			//seqMap.put(seq, letter);
			
			
		}
		

	}
	
	// this will print the Map with all the possible next options and their given probabilities
	
	public void printMap() {
		System.out.println();
		System.out.println("--------------------");
		System.out.println();
		System.out.println("Sequences and next letters with counts");
		System.out.println();
		for( String sequence : seqMap.keySet()) {
			
			System.out.println("This is the sequence : " + sequence);
			System.out.println("And these are the possible next moves");
			for(String next: seqMap.get(sequence).keySet()) {
				System.out.println();
				System.out.println(next + " : " + seqMap.get(sequence).get(next));
				
			}
			System.out.println();
			System.out.println();
		}
	}
	
	//will set the probabilities for each next character after a given sequence
	
	public void setProbabilities() {
		for( String sequence : seqMap.keySet()) {							//go through each Markov order sequence in seqMap
			
			for(String next: seqMap.get(sequence).keySet()) {
				if(!next.equals("total")) {									//we don't check total obviously
					double count = seqMap.get(sequence).get(next);			// use total to divide each next letter's count by total
					double total = seqMap.get(sequence).get("total");
					double probability = count/total;						// this gives us the probability of this next letter for each sequence
					seqMap.get(sequence).put(next,probability);
				
			}
				
		}
			seqMap.get(sequence).remove("total");							//remove total from the seqMap since we don't need it anymore
	}
	
	

	
	}
	
	
	public ArrayList<String> createNames(){									//create names
		
		ArrayList<String> newNames = new ArrayList<String>();			//make array list which will store the new names
		
		String trigger = "_".repeat(this.order);						//start and end trigger("_") whose length is determined by Markov order
		int realMin = this.min - this.order*2;							//max and min to check if the new name fits requirements
		int realMax = this.max - this.order*2;
		
		for(int i =0; i<numNames;) {									// number of names requirements
			
			String newName  = makeName(trigger).replace("_", "");    //make a name, and remove the triggers ("_")
			if(newName.length()>=realMin && newName.length()<=realMax && !(existName.contains(newName)) ) {			//if the new name is within the acceptable range and doesn't exist add it to new names List
				newNames.add(newName);
				i++;													// only update the count if it fits this condition
			}
			
		}
		
		return newNames;
	}
	
	public String makeName(String trigger ) {
		
		String tempName = trigger;			//this start of our name which is defined by the trigger "_"*order
		//String end = tempName.substring(tempName.length()-this.order, tempName.length());
		//System.out.println("Start name: " + tempName);
		int track = 0;
	
		while(tempName.length()<this.min || (!(tempName.substring(tempName.length()-this.order, tempName.length()).equals(trigger)))) { //the name will be built as long as the tempName is less than min requirements or the tempName hasn't ended 
			String start = tempName.substring(track,track+this.order);
			//System.out.println("we check the kids of this: " + start);
			double randNum = Math.random();
			double cumulative = 0;
			
			if(seqMap.get(start)!=null) {
				HashMap<String, Double> nextChars = seqMap.get(start);   //a HashMap of it's next possible characters and their probabilites

				for(String next: nextChars.keySet()) {
					cumulative += nextChars.get(next);
			
					if(randNum <= cumulative) {
						
						if(next.equals("_") && tempName.length()< (this.min-this.order)) {  //if the model predicts the next value to be the "_" even though the name is still under min requirments
							tempName+="";
						}
						else {
							
							tempName += next;
							//System.out.println("Now name: " + tempName);
							track++;
							break;
						}
					}
				}
		
			}
			
			else {       //if the current sequence has no next char we have no choice but to end the name as is! 
				tempName += "__";
				return tempName;
			}
			
			
		}
		return tempName;
	}
	
	
	}
	
	

