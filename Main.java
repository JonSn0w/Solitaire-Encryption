// Solitaire Encryption 
// by Brett Stevenson
// Main.java
/*	This class contains the IO and UI of the encryption program , and converts 
	alpha-numeric values to their alphabetic equivalent and vice versa.          */
// Status: Working/Tested
//

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
   
    boolean encrypting = false;  // tracks the encrypt/decrypt state of program
   
    public static void main(String[] args) {
	Program6 program = new Program6();
	DLList list = new DLList();
	String action = "";
	String filename = "";
	System.out.println("Sn0w Solitaire Encryption");
	System.out.println("by Brett Stevenson");
	String message = "";
	Scanner in = new Scanner(System.in);
	if(args.length == 2) {
	    action = args[0];
	    filename = args[1]; 
	} else if(args.length == 1) {
	    String arg = args[0];
	    if(arg.equals("e") || arg.equals("d")) {
		action = args[0];
		System.out.println("**Please specify the deck file from which the program will read.");
		while(!in.hasNextLine()) 
		    in.nextLine();
		filename = in.nextLine();
	    } else if(arg.contains(".txt")) {
		System.out.println("This program requires that you specify encryption (\"e\") or decryption (\"d\").");
		System.exit(0);
	    } 
	}
	try {
	    Scanner scan = new Scanner(new File(filename));
	    while(scan.hasNextInt()) {
		list.insertRear(scan.nextInt());
	    }
	    scan.close();
	}
	catch (FileNotFoundException e) {
	    System.err.println("The file \"" + filename + "\" could not be found.");
	}
	
	System.out.print("Enter message to be ");
	if(action.equals("e")) 
	    System.out.println("encrypted:");
	else if(action.equals("d"))
	    System.out.println("decrypted:");
	else 
	    System.out.println("...\n**There has been an error**");
	
	while(!in.hasNextLine())
	    in.nextLine();
	message = in.nextLine(); 
	in.close(); 
	if(action.equals("e")) 
	    program.encrypt(list, message);
	else if(action.equals("d")) 
	    program.decrypt(list, message);
    }
    
    public void encrypt(DLList list, String message) {
	encrypting = true;
	String formattedMsg = formatMsg(message);
	int length = formattedMsg.length();
	int [] numVal = getAlphaInts(formattedMsg);
	for(int val : numVal)
	    System.out.print(val + ".");
	int [] keyStreams = new int[length];		
	int i = 0;
	while(i < length){
	    int stream = list.getKeyStreams();
	    if(stream >= 27)
		stream = list.getKeyStreams();
	    keyStreams[i] = stream;
	    i++;
	}
	for(int val : keyStreams)
	    System.out.print(val + ".");
	int [] sum = combine(numVal, keyStreams);
	for(int val : sum)
	    System.out.print(val + ".");
	String encryptedMsg = getAlphaChars(sum);
	System.out.println("The encrypted message is: " +encryptedMsg);
    }
    
    public void decrypt(DLList list, String message) {
	String formattedMsg = formatMsg(message);
	int length = formattedMsg.length();
	int [] numVal = getAlphaInts(formattedMsg);
	for(int val : numVal)
	    System.out.print(val + ".");
	int [] keyStreams = new int[length];
	int i = 0;
	while(i < length){
	    int stream = list.getKeyStreams();
	    if(stream >= 27)
		stream = list.getKeyStreams();
	    keyStreams[i] = stream;
	    i++;
	}
	for(int val : keyStreams)
	    System.out.print(val + ".");
	int [] difference = combine(numVal, keyStreams);
	for(int val : difference)
	    System.out.print(val + ".");
	String decryptedMsg = getAlphaChars(difference);
	System.out.println("The decrypted message is: " + decryptedMsg);	
    }
    
    public String formatMsg(String message) {
	// formats the input to be upper case, divisible by five, and removes spaces.
	message = message.replaceAll("[^A-Za-z]","");
	message = message.toUpperCase();
	StringBuilder sb = new StringBuilder(message);
	int length = message.length();
	while(length%5 != 0) {
	    sb.insert(message.length(), "X");
	    length++;
	}
	String formatted = sb.toString();
	return formatted;
    }
    
    public int[] getAlphaInts(String formattedMsg) {
	// returns the alpha-numeric value for each letter 
	StringBuilder sb = new StringBuilder(formattedMsg);
	int length = formattedMsg.length();
	int[] numVal = new int[length];
	int i = 0;
	char[] arr  = formattedMsg.toCharArray();
	for(char letter : arr) {
	    int value = (int) letter;
	    int mod = 64;   // for upper case
	    if(value <= 90 && value >= 65) {
		value = value - mod;
		numVal[i] = value;
		i++; 
	    }
	    else { System.exit(0); }
	}
	return numVal;
    }

    public String getAlphaChars(int[] result) {
	// returns the corresponding letter of each alpha-numeric value  
	String convertedChars = "";
	for(int value : result) {
	    value = value + 64;
	    char letter = (char) value;
	    convertedChars += letter;
	}
	return convertedChars;
    }
    
    public int[] combine(int[] numVal, int[] keyStreams) {
	// subtracts or adds the keystream values from the given numeric values as dictated by 
    	// the program state and returns the resulting values.
	int[] output = new int[keyStreams.length];
	int value;
	for(int i = 0; i < numVal.length; i++) {
	    if(encrypting)
		value = numVal[i] + keyStreams[i];
	    else
		value = numVal[i] - keyStreams[i];
	    if(value > 26)
		value = value - 26;
	    if(value < 1)
		value = value + 26;
	    output[i] = value;
	}
	return output;
    }
    
}
