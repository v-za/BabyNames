# BabyNames Project

## Project Description

The project contains an n-th order Markov model intended to generate novel names that follow common letter sequences from two text files containing male and female baby names. 

The program prompts the user to select the gender, minimum length of name, maximum length of name, order of the model and the number of names to generate. After this, using the sequenece of letters found in the two text files containing male and female names a Markov model is built. Next, randomly generated names are produced using the transitions of the Markov model. The program also checks to make sure that the names created are novel before it outputs them.

## Implementation

There are two classes 

(1) Collect.java

	This class collects input from the user and creates a Markov object which is used to build the model and generate the names.

(2) Markov.java
		
	This class has the Markov Model. Functions seqGenerator() and setProbabilties() are used to create the model by using the data files to determine for which letters follow other sequences of letters some percentage of time. The probabilities of letters and previous sequences are stored in a hashtable. A makeName() method is used to generate a name from the probablitiy transitions found in the model while createNames() ensurres that the name constraints are followed. 

	

