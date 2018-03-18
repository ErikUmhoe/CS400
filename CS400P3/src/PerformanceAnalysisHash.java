import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;

public class PerformanceAnalysisHash implements PerformanceAnalysis {

    // The input data from each file is stored in this/ per file
    private ArrayList<String> inputData;
    
    public PerformanceAnalysisHash() throws IOException{
    	loadData("data/StringLarge.txt");
    	
    }

    public PerformanceAnalysisHash(String details_filename){
        //TODO: Save the details of the test data files
    }
    @Override
    public void compareDataStructures() {
        //TODO: Complete this function which compares the ds and generates the details
    }

    @Override
    public void printReport() {
        //TODO: Complete this method
    }

    @Override
    public void compareInsertion(){
    	
    	long startTimeTree = System.currentTimeMillis();
    	TreeMap treemap = new TreeMap();
    	for(String s : inputData)
    	{
    		treemap.put(s, s);
    	}
    	long endTimeTree = System.currentTimeMillis();
    	long startTimeHash = System.currentTimeMillis();
    	HashTable hash = new HashTable(10000,.8);
    	for(String s : inputData)
    	{
    		hash.put(s, s);
    		System.out.println(s);
    	}
    	long endTimeHash = System.currentTimeMillis();
    	
    	System.out.println("Time of a TreeMap to insert " + 
    	inputData.size() + " items: " + (endTimeTree - startTimeTree));
    	
    	System.out.println("Time of a Hash Table to insert " + 
    	    	inputData.size() + " items: " + (endTimeHash - startTimeHash));
   
    }

    @Override
    public void compareDeletion() {
    	TreeMap treemap = new TreeMap();
    	for(String s : inputData)
    	{
    		treemap.put(s, s);
    	}
    	int i = 0;
    	long startTimeTree = System.currentTimeMillis();
    	while(!treemap.isEmpty())
    	{
    		treemap.remove(inputData.get(i));
    		i++;
    	}
    	long endTimeTree = System.currentTimeMillis();
    	System.out.println("Time of a TreeMap to delete " + 
    			inputData.size() + " items: " + (endTimeTree - startTimeTree));
    	
    	HashTable hash = new HashTable(1000,.8);
    	for(String s : inputData)
    		hash.put(s, s);
    	i = 0;
    	long startTimeHash = System.currentTimeMillis();
    	while(!hash.isEmpty())
    	{
    		hash.remove(inputData.get(i));
    		i++;
    	}
    	long endTimeHash = System.currentTimeMillis();
    	
    	System.out.println("Time of a Hash Table to delete " + 
    	inputData.size() + " items: " + (endTimeHash - startTimeHash));
    }

    @Override
    public void compareSearch() {
        //TODO: Complete this method
    }

    /*
    An implementation of loading files into local data structure is provided to you
    Please feel free to make any changes if required as per your implementation.
    However, this function can be used as is.
     */
    @Override
    public void loadData(String filename) throws IOException {

        // Opens the given test file and stores the objects each line as a string
        File file = new File(filename);
        BufferedReader br = new BufferedReader(new FileReader(file));
        inputData = new ArrayList<>();
        String line = br.readLine();
        while (line != null) {
            inputData.add(line);
            line = br.readLine();
        }
        br.close();
    }
    
}