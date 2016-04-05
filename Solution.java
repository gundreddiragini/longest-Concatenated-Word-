import java.io.*;
import java.util.*;

public class Solution {

 /*

 MY APPROACH:

 Words from the file are segregated into different groups based on their length. 

 Creating ArrayList<String>. Each ArrayList is assigned to store words of a particular length.
  e.g: One ArrayList is used for storing all the single lettered words. Some other ArrayList is used to store all the words having two characters and this continues for all the different lengths of words that are available in the input file.  

  ArrayList are dynamically created whenever a word of length 'x' is encountered for the first time.

  Objects of the created ArrayLists are stored into a HashMap.  
  
  */

 public static HashMap < String, List < String >> myHashMap = new HashMap < String, List < String >> (); //HashMap that stores the objects of newly created ArrayLists. Key of the hash map indicates the length of words that are stored in the corresponding ArrayList


 public static List < String > concatenatedWords = new ArrayList < String > (); //ArrayList into which concatenated words are stored

 public static void main(String[] args) {

  String fileToBeRead = ""; //Name of the input file containing data

  List < String > reference; //  used as a reference for the ArrayList objects stored in HashMap 

  List < String > wordCollection = new ArrayList < String > (); // Collection of all words in the file

  String word = null; // represents each word in the input file (one at a time)
  String temp; // temporary variable

  int wlength = 0; //to store length of each words.

  System.out.println("Enter the name of the file to be read (along with file extension):");
  Scanner scan = new Scanner(System.in);

  fileToBeRead = scan.nextLine(); // taking in the name of input file

  try {
   FileReader readFile = new FileReader(fileToBeRead);

   BufferedReader bReader = new BufferedReader(readFile);

   while ((word = bReader.readLine()) != null) { // read words of the file one at a time

    wlength = word.length();

    wordCollection.add(word); // adding every word into an ArrayList

    temp = Integer.toString(wlength);

    if (!myHashMap.containsKey(temp)) { // creating entry for ArrayList in HashMap if there is no previous entry corresponding to length of current word
     myHashMap.put(temp, new ArrayList < String > ());
     reference = myHashMap.get(temp);
     reference.add(word); // Adding the current word into ArrayList corresponding to its length
    } else {
     reference = myHashMap.get(temp);
     reference.add(word); // Adding the current word into ArrayList corresponding to its length
    }


   }
   bReader.close();
  }

  // Catch blocks to respond to errors in reding the given file
  catch (FileNotFoundException ex) {
   System.out.println("Cannot find the file named '" + fileToBeRead + "'");
  } catch (IOException ex) {
   System.out.println("Reading is prohibited from '" + fileToBeRead + "'");
  }


  int testing; //for testing words to check if they are concatenated
  int noOfConcatenatedWords = 0; //variable counting the number of concatenated words

  System.out.println();
  System.out.println();
  System.out.println("Processing the words.......\n");


  for (int j = 0; j < wordCollection.size(); j++) //Iterating through all the words
  {



   //Call a function to check if a word is concatenated
   testing = checkIfConcatenated(wordCollection.get(j), 0, wordCollection.get(j).length(), wordCollection.get(j).length());

   if (testing == 1) { //word is a concatenated word if the function returns '1'

    concatenatedWords.add(wordCollection.get(j)); //adding the list of concatenated words into a collection

    noOfConcatenatedWords++; // incrementing the count of concatenated words
   }

  }

  //Results of processing

  System.out.println("Longest word in concatenated list is:\n" + longestWord());
  System.out.println();
  System.out.println("Second longest word in concatenated list is:\n" + longestWordTwo());
  System.out.println();
  System.out.println("The total count of concatenated words in the file is:\n" + (noOfConcatenatedWords));

 }

 //Function that returns the longest word in the list of concatenated words
 public static String longestWord() {
  String longWord = "";
  int maxWordLength = 0;
  for (int i = 0; i < concatenatedWords.size(); i++) {
   if (concatenatedWords.get(i).length() > maxWordLength) {
    maxWordLength = concatenatedWords.get(i).length();
    longWord = concatenatedWords.get(i);
   }
  }
  return longWord;
 }

 //Function that returns the second longest word in the list of concatenated words
 public static String longestWordTwo() {
  String longWord = "", longWord2 = "";
  int maxWordLength = 0, secondmaxWordLength = 0;

  for (int i = 0; i < concatenatedWords.size(); i++) {

   if (concatenatedWords.get(i).length() > maxWordLength) {
    secondmaxWordLength = maxWordLength;
    longWord2 = longWord;
    maxWordLength = concatenatedWords.get(i).length();
    longWord = concatenatedWords.get(i);
   } else if (concatenatedWords.get(i).length() > secondmaxWordLength) {
    secondmaxWordLength = concatenatedWords.get(i).length();
    longWord2 = concatenatedWords.get(i);
   }

  }

  return longWord2;
 }

 //Recursive function to check if a word is a concatenated word. Function return '1' if a word is concatenated and '0' in other case.
 public static int checkIfConcatenated(String currentword, int start, int end, int length) {
  String subWord, temp;
  int ansOfRecursiveCall;
  List < String > tempList;

  /*Loop checks to see if group of letters from the start of the word are in any of the ArrayList(list that stores words based on their length) that are stored in hashmap.

  If some of the starting letters of current word form another word in the input file, same function is called recursively to process the remaining letters of the current word*/
  for (int i = start + 1; i <= end; i++) {
   temp = Integer.toString(i - start);
   subWord = currentword.substring(start, i);
   if (myHashMap.containsKey(temp)) {

    tempList = myHashMap.get(temp);

    if (i == length && tempList.contains(subWord) && start != 0) {
     return 1;
    }

    if (i == length && !tempList.contains(subWord)) {
     return 0;
    }

    if (tempList.contains(subWord)) {
     ansOfRecursiveCall = checkIfConcatenated(currentword, i, end, length);
     if (ansOfRecursiveCall == 1) return 1;
    }


   }
  }
  return 0;
 }


}