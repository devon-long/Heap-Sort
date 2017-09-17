/**
 * Devon Vlcek
 * ITCS 2215-001
 * Project 1
 * 
 * This program reads an array of numbers from a file, sorts 
 * the numbers based on max heap properties, and writes the
 * resulting sorted array to a new text file.
 * 
 */

import java.io.*;

public class Main
{
    public static void main(String[] args) throws IOException
    {
        Heap myHeap = new Heap("filename.txt");

        myHeap.buildHeap();
        myHeap.outputResult("buildheapResult.txt");

        myHeap.removeMaxElement();
        myHeap.removeMaxElement();

        myHeap.addElement(30);

        myHeap.heapSort();
        myHeap.outputResult("afterHeapsort.txt");
    }
}