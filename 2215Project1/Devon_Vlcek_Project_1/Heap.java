/**
 * Devon Vlcek
 * ITCS 2215-001
 * Project 1
 * 
 * This class contains all methods necessary to manipulate 
 * a max heap. It reads numbers from a .txt file to an ArrayList,
 * builds a max heap, sorts elements in descending order and writes 
 * the newly sorted numbers to a new file.
 * 
 */


import java.util.*;
import java.io.*;



public class Heap {
    private ArrayList<Integer> elements = new ArrayList<Integer>();

    /**
     * Constructor builds ArrayList from text file, and sets the 0th index
     * to hold the size of the list
     * 
     * @param fileName - name of file to read
     * @throws IOException
     */
    public Heap(String fileName) throws IOException
    {
        Scanner input = new Scanner(new File(fileName));
        
        while(input.hasNextInt()){
            elements.add(input.nextInt());
        }
        
        input.close();
        
        elements.add(0, elements.size());
    }

    /**
     * Method iterates through the ArrayList
     * and writes the current state of elements to a text file
     * 
     * @param filename - name to use for new text file
     * @throws IOException
     */
    public void outputResult(String filename) throws IOException
    {
        FileWriter fileWriter = new FileWriter(filename);
        PrintWriter outputFile = new PrintWriter(fileWriter);
        
        for(int i = 1; i <= elements.get(0); i++){
        	outputFile.println(elements.get(i) + " ");
 	   	}  
        outputFile.close();
    }

    /**
     * Parent is swapped with its largest child if parent's value is
     * less than the largest child's value.
     * siftUp is called recursively after each swap until the largest child is 
     * at the appropriate location in the max heap, while never swapping with the 
     * size held at value element.get(0)
     * 
     * @param indexOfNode - index of node to sift up the heap
     */
    public void siftUp(int indexOfNode){
            int indexOfParent = indexOfNode / 2;
        	int indexOfMaxChild = getIndexOfMaxChild(indexOfParent);;
        	
        	if(indexOfParent != 0 && elements.get(indexOfParent) < elements.get(indexOfMaxChild)){
        		swap(indexOfParent, indexOfMaxChild);
        		siftUp(indexOfParent);     
        	}
    }

    /**
     * Method stores the max value in a variable, switches max value
     * with the last element in the list, deletes the max value and updates the size
     * held in the 0th element.
     * The old root is then returned.
     * 
     * @return maxElement - maxElement returned for storage in a sorted array
     */
    public int removeMaxElement()
    {	
    	int maxElement = elements.get(1);
    	
    	if(elements.isEmpty()){
    		return 0;
    	}
    	// Scenario A - root is the only element left in the heap.
    	// Simply remove and return the root.
    	if(elements.get(0) == 1){
    		elements.remove(1);
    		elements.set(0, elements.size()-1);
    		return maxElement;
    	}
    	// Scenario B - Only the root and its 1 child are left in the heap.
    	// Swap both elements, then remove and return the old root
    	else if(elements.get(0) == 2){
    		swap(1, 2);
    		elements.remove(2);
    		elements.set(0, elements.size()-1);
    		return maxElement;
    	}
    	// Scenario C - the root has many children and/or grandchildren.
    	// Swap the root with the last node, remove the old root
    	// and heapify until the last node reaches the appropriate place
    	// in the max heap
    	else{
        	swap(1, elements.get(0));
        	    	
        	elements.remove(elements.size()-1);
        	
        	elements.set(0, elements.size()-1);
            heapify(1);
        	
            return maxElement;
    	}

    }

    /**
     * Method adds a new value after the last heap element and sifts
     * the new value up the heap until it reaches its appropriate location
     * in the max heap
     * 
     * @param value - new element to add to max heap
     */
    public void addElement(int value)
    {
    	elements.add(value);
		
    	// increment size of heap
    	elements.set(0, elements.size() - 1);
       
    	siftUp(elements.get(0));
    }

    /**
     * Method calls buildHeap to make sure the elements
     * are in a max heap, then calls removeMaxElement() until the list is empty
     * and stores each removed element in a new array.
     * The sorted elements are then added back into the list.
     */
    public void heapSort()
    {
    	buildHeap();
    	int[] heapSortArray = new int[elements.get(0)];
    	int sizeOfHeap = elements.get(0);
    	
    	for(int i = 0; i < sizeOfHeap; i++){
    		heapSortArray[i] = removeMaxElement();
    	}
    	
    	elements.set(0, heapSortArray.length);
    	for(int i = 0; i < heapSortArray.length; i++){
    		elements.add(heapSortArray[i]);
    	}	
    }

    /**
     * Starting with the last parent in the heap, buildHeap
     * calls heapify until the elements are stored in an appropriate
     * max heap formation.
     */
    public void buildHeap()
    {
        for(int i = getIndexOfLastParent(); i > 0; i--){
            heapify(i);
        }        
    }

    
    /**
     * Method swaps elements as long as the parameter node is a
     * parent (less than or equal to the index of the last parent)
     * and the value in the parameter node is greater than the value
     * of its parent.
     * 
     */
    public void heapify(int parentNodeIndex)
    {
    	int indexOfMaxChild = getIndexOfMaxChild(parentNodeIndex);
        
        if(elements.get(parentNodeIndex) < elements.get(indexOfMaxChild)){
		    	
        	swap(parentNodeIndex, indexOfMaxChild);
		    	
        	if(indexOfMaxChild <= getIndexOfLastParent()){
       			heapify(indexOfMaxChild);
       		}
	    }
    }
    
    
    /**
     * This method returns the index of the largest child element.
     * If the size of the ArrayList is even, this means the last parent has no 
     * right child, and left child's index is returned as the max
     * 
     * If a right child exists, it is returned if its value is greater than or equal to the left child's
     * 
     * @param parentNodeIndex - index of the node used to find index of its children
     * @return int index of largest child
     */
    public int getIndexOfMaxChild(int parentNodeIndex){
    	
    	int valueOfLeftChild = elements.get(parentNodeIndex*2);
    	int valueOfRightChild;
    	
    	// If parent only has 1 child, return left child's index
    	if(parentNodeIndex == getIndexOfLastParent() && elements.get(0) % parentNodeIndex == 0)
    		{return (parentNodeIndex*2);}
    	else
    		{valueOfRightChild = elements.get((parentNodeIndex*2)+1);}
        
    	// If parent has 2 children, compare left with right and return the largest child's index
        if(valueOfLeftChild > valueOfRightChild)
            {return (parentNodeIndex*2);}
        else
            {return((parentNodeIndex*2)+1);}    
       
    }
    
    /**
     * Method swaps the parent with its largest child
     */
    public void swap(int indexOfParent, int indexOfMaxChild){
    	
    	int temp = elements.get(indexOfParent);
        elements.set(indexOfParent, elements.get(indexOfMaxChild));
        elements.set(indexOfMaxChild, temp);
 
    }
    
    /**
     * Method returns the index of the last parent in the heap
     * @return n/2 returned as index of last parent
     */
    public int getIndexOfLastParent(){
    	return elements.get(0) / 2;
    }
    
   /**
    * toString method returns the current state of the heap including
    * the size held in elements.get(0)
    */
   public String toString(){
	   String str = "";
	   for(int i = 1; i <= elements.get(0); i++){
		   str += (elements.get(i) + " ");
	   }
	   return str + "\n";
   }
   

   /**
    * Method returns current size of ArrayList
    * @return current size of ArrayList
    */
   public int getSize(){   
	   return elements.size();
   }
   
   /**
    * Method used to check if heap is empty.
    * Prevents removing elements from an empty heap.
    * @return boolean whether the arraylist is empty or not
    */
   public boolean isEmpty(){
	   return (elements.get(0) == 0);
   }
}
