//Wren Lee
//Generic class with train and generate function
//Sept 10

import java.util.ArrayList;

public class ProbabilityGenerator<T> {
		ArrayList<T> inputArr = new ArrayList(); // arraylist to hold variable amount of input
		ArrayList<T> alphabetArr = new ArrayList(); //arraylist for generates tokens, based on alphabet
		ArrayList<Integer> alphabetCounts = new ArrayList<Integer>(); //arraylist to hold the number of each unique token
		ArrayList<Float> probabilityArr = new ArrayList<Float>(); //arraylist to hold all the probabilities
		ArrayList<T> genArr = new ArrayList(); //arraylist to hold all of the generated values
	
		
		public void train(ArrayList<T> inArr) {
			//run through in arr from outside source and copy all over into the input arr
			for(int i = 0; i < inArr.size(); i++) {
				inputArr.add(inArr.get(i));
			}//for loop
		
			for(int i = 0; i < inputArr.size(); i++) {
			    int index = 0;
				T comparativeVal = (T) inputArr.get(i); //value from input array that will be compared to alphabet array
			    index = alphabetArr.indexOf(inputArr.get(i)); //gets the first reference of the input array 
				if(index == -1) { //if the index doesn't find any similar values
					alphabetArr.add(comparativeVal); //add index value to the end of the alphabet array
					alphabetCounts.add(1);
				}//if there isn't a previous token
				else { //if the index doesn't find an existing value
					alphabetCounts.set(index, alphabetCounts.get(index) + 1); //add at the end of alphabet counts to match with alphabet array
					}//if there's not an existing similar token, make a new one
			}//runs through input array length
			
			float totalCounts = 0;
			for(int i = 0; i < alphabetCounts.size(); i++) {
				totalCounts = alphabetCounts.get(i) + totalCounts; //add all counts up
			}//gets total
			probabilityArr.clear();
			for(int i = 0; i < alphabetCounts.size(); i++) {
				probabilityArr.add((float) (alphabetCounts.get(i)/totalCounts)); //add the probabilities from each of the counts
			}//gets probabilities
		}//train function
		
		public T generate(){ //generate one note
			
			assert(probabilityArr.size()>1); //this object hasn't been trained
			
			float randIndex = (float) Math.random(); //generates a random number from 0 to 1
			boolean found = false;
			int index = 0;
			float total = 0;

			while (!found && index < alphabetCounts.size()-1){
				total +=  probabilityArr.get(index);
				found = randIndex <= total;
				index++;
			}

			if(found) {
				return alphabetArr.get(index-1);
			}
			else {
				return alphabetArr.get(index);
			}
		}//generate functions
		
		
		public ArrayList<T> generateMultiple(int n){ //generate multiple notes
			ArrayList<T> genArr = new ArrayList();
			for(int i=0; i<n; i++) {
				genArr.add(generate());
			}
			return genArr;
		}//generate multiple function
		
		public ArrayList<T> generateMultipleAndPrint(int n){ //generate multiple notes
			ArrayList<T> genArr = new ArrayList();
			for(int i=0; i<n; i++) {
				genArr.add(generate());
			}
			System.out.println(genArr);
			return genArr;
		}//generate multiple function
		
		public void unitTest1() {
			System.out.println("-----Probability Distribution-----");
			for(int i = 0; i < probabilityArr.size(); i++) {
				System.out.println("Token: " + alphabetArr.get(i) + "| Probability:: " + probabilityArr.get(i));
			}//loops through probability arr
			System.out.println("------------");
		}//unit test one: print probabilities
		
		public void unitTest2() {
			generateMultipleAndPrint(20);
		}//unit test two: one melody
	
}//test class definition ends
