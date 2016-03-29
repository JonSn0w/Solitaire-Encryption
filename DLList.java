// Solitaire Encryption 
// by Brett Stevenson
// DLList.java
/*	This class contains a doubly-linked list implementation to store the data, upon which 
	various operations are performed to properly encrypt/decrypt the provided message 
	using the provided "deck.txt" file.         */
// Status: Working/Tested
//

public class DLList {
    
    protected LinkNode head;
    protected LinkNode tail;
    
    public DLList() { // default constructor 
	head = tail = null; 
    }

    public boolean isEmpty() { return head == null; } // returns true if list is empty
    
    public int listSize() {  // returns the amount of nodes in the list
	if(isEmpty()) return 0;
	LinkNode temp = head;
	int size = 1;
	while(temp != tail) {
	    size++;
	    temp = temp.next;
	}
	return size;
    }
    
    
    public int getKeyStreams() {    
    // returns a new keystream value for the deck each time method is called
    
// Move 1
	LinkNode twentySeven = find(27);
	LinkNode nextTarget;
	if(twentySeven == tail)
	    nextTarget = head;
	else
	    nextTarget = twentySeven.next;
	int swap = nextTarget.data;
	nextTarget.data = twentySeven.data;
	twentySeven.data = swap;
	
// Move 2
	LinkNode twentyEight = find(28);
	LinkNode nextOne;
	LinkNode nextTwo;
	if(twentyEight == tail) {
	    nextOne = head;
	    nextTwo = head.next;
	} else if(twentyEight.next == tail) {
	    nextOne = tail;
	    nextTwo = head;
	} else {
	    nextOne = twentyEight.next;
	    nextTwo = nextOne.next;
	}
	int swapOne = nextOne.data;
	int swapTwo = nextTwo.data;
	nextTwo.data = twentyEight.data;
	nextOne.data = swapTwo;
	twentyEight.data = swapOne;
	
// Move 3
	LinkNode firstJoker,secondJoker;
	if(getIndex(28) < getIndex(27)) {   // determine which "joker" comes first in deck
	    firstJoker = find(28);
	    secondJoker = find(27);
	} else {
	    firstJoker = find(27);
	    secondJoker = find(28);
	}
	LinkNode temp = firstJoker;
	while(temp != secondJoker.next) {
	    insertRear(temp.data);
	    temp = temp.next;
	}
	temp = head;
	while(temp != secondJoker.next) {
	    insertRear(temp.data);
	    temp = temp.next;
	    deleteFront();
	}
	while(listSize() > 28)   // removes leftover duplicates from the end of the list
	    tail = getNodeAt(27);	
	
// Move 4	
	int lastValue = tail.data;
	LinkNode target; 
	if(lastValue >= 27) //28 & 27 = 27
	    target = getNodeAt(27);
	else 
	    target = getNodeAt(lastValue);
	tail = tail.prev;
	temp = tail;
	while(temp != target.prev) {
	    insertFront(temp.data);
	    temp = temp.prev;
	    deleteRear();
	}
	insertRear(lastValue); // returns the last value to the end of the list
	printAll();
	
//Move 5
	int firstValue = head.data;
	LinkNode keyStream;
	if(firstValue >= 27) //28 & 27 = 27
	    keyStream = getNodeAt(27);
	else
	    keyStream = getNodeAt(firstValue);
	System.out.println("Keystream: " + keyStream.data); 
	return keyStream.data;
    }
    
    
    public void insertFront(int newValue) { 
	//inserts Node containing the newValue to the front of the list 
	if(!withinRange(newValue)) { 
	    System.err.println("**ERROR: The provided deck contains invalid integers**");
	    return; 
	}
	LinkNode temp = new LinkNode();
	temp.data = newValue;
	if (isEmpty()) { 
	    head = tail = temp; 
	    return;
	}
	head.prev = temp;
	temp.next = head;
	head = temp;
    }
    
    public void insertRear(int newValue) {
	// inserts Node containing the newValue to the end of the list 
	if (isEmpty()){
	    insertFront(newValue);
	    return;
	}
	LinkNode temp = new LinkNode();
	temp.data = newValue;
	temp.prev = tail;
	tail.next = temp;
	tail = temp;
    }
    

    private void deleteFront() {  // deletes Node at the front of the list
	if (isEmpty()){ return; }
	if (head == tail ){ //list length is <= 1
	    head = tail = null; //removes last node
	    return;
	}
	head.next.prev = null;
	head = head.next;
    }
    
    private void deleteRear() {  // deletes Node at the end of the list
	if (head == null) { return; }
	if (head == tail ) { 
	    head = tail = null;  // removes last node
	    return;
	}
	tail.prev.next = null;
	tail = tail.prev;
    }

    
    private LinkNode getNodeAt(int index) {
	//returns the Node at the given index
	int count = 0;
	LinkNode temp = head;
	while(count < index) {
	    temp = temp.next;
	    count++;
	}
	if(count == index)
	    return temp;
	return null;
    }
    
    private int getIndex(int targetData) {
	//returns the index of the Node containing the target data
	LinkNode temp = head;
	int tempData;
	int index = 1;
	while(temp != null) {
	    tempData = temp.data;
	    if(tempData == targetData)
		return index;
	    temp = temp.next;
	    index++;
	}
	return 0;
    }
    
    
    private LinkNode find(int target) {
	// returns the Node which contains the given data
	LinkNode temp = head;
	int tempInt;
	while(temp != null) {
	    tempInt = temp.data;
	    if(tempInt == target)
		return temp;
	    temp = temp.next;
	}
	return null;
    }
    
    private int getLast() {
	//returns the data of the last Node in the List
	if (tail == null) 
	    return 0;
	int lastData = tail.data;
	if (lastData == 28)
	    lastData = 27;
	return lastData;
    }
    
    public boolean withinRange(int index) {
	//returns true if the index is within the range of the list.
	if(index <= 28 && index > 0)
	    return true;
	return false;
	}
    
    public void printAll() {
	//prints the values of each Node in the list to STDOUT
	LinkNode temp = head;
	while(temp != null) {
	    System.out.print(temp.data);
	    System.out.print(" ");
	    temp = temp.next;
	}
	System.out.println();
    }
    
    
    protected class LinkNode { //inner LinkNode class
	protected LinkNode next;
	protected LinkNode prev;
	protected int data;
	
	public LinkNode() { 
	    next = prev = null; 
	    data = 0; 
	}
    }
}