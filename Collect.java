package babyNames;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Collect {
	
	//collect class which asks the user details about the names they want to generate

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		Scanner scanner = new Scanner(System.in);
		
		String strGender;
		int min;
		int max;
		int order;
		int numNames;
		int intGender;
		
		
		
		System.out.println("Hello, welcome to your Baby Name Generator!");
		System.out.println();
		System.out.println("Would you like Male or Female Names?");
		strGender = scanner.next().toLowerCase();    //converts input for male or female into lower or uppercase
		
		System.out.println();
		System.out.println("What would you like the minimum length of the name to be?");
		min = scanner.nextInt();
		System.out.println();
		System.out.println("What would you like the maximum length of the name to be?");
		max = scanner.nextInt();
		System.out.println();
		System.out.println("How many names do you want generated?");
		numNames = scanner.nextInt();
		System.out.println();
		System.out.println("What order would you like your Markov Model to be?");
		order = scanner.nextInt();
		System.out.println();
		System.out.println();
		System.out.println("The Names will now be generated!");
		
		if(strGender.contentEquals("male")) {
			intGender = 1;									//makes int versions of gender
		}
		else {
			intGender = 2;
		}
		
		Markov markov = new Markov(min,max,order,numNames,intGender);				//creates Markov class by passing name requirements from input
		doesWork(markov);
		

	}
	
	public static void doesWork(Markov markov){
		markov.organize();										//gets names from existing files and places them in an array
		markov.setProbabilities();								// sets probabilites based on sequence based on Markov order
		//markov.printMap();	
		
		ArrayList<String> list = markov.createNames();			//get's the new names and prints them
		for(String s: list) {
			s += ", ";
			System.out.print(s);
			//System.out.println(s);
			//System.out.println();
		}
		
	}
	
	

}
