import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;

public class PerformanceAnalysisHash implements PerformanceAnalysis {

    // The input data from each file is stored in this/ per file
    private ArrayList<String> inputData;
    private ArrayList<String> files;
    private ArrayList<String> reports;
    private String curFile;
    
    public PerformanceAnalysisHash() throws IOException{
    	reports = new ArrayList<>();
    	loadFiles("data_details.txt");
    	for(String s: files)
    	{
    		loadData(s);
    		curFile = s;
    		compareDataStructures();
    	}
    }

    public PerformanceAnalysisHash(String details_filename){
        //TODO: Save the details of the test data files
    }
    @Override
    public void compareDataStructures() {
        compareInsertion();
        compareSearch();
        compareDeletion();
    }

    @Override
    public void printReport() {
        System.out.println("The report name : Performance Analysis Report");
        System.out.println("-----------------------------------------------"
        		+ "-------------------------------------------------");
        System.out.println("|            FileName|      Operation| Data Structure|"
        		+ "   Time Taken (micro sec)|     Bytes Used|");
        System.out.println("-----------------------------------------------"
        		+ "-------------------------------------------------");
        for(String s: reports)
        	System.out.println(s);
        System.out.println("-----------------------------------------------"
        		+ "-------------------------------------------------");
    }

    @Override
    public void compareInsertion(){
    	
    	Runtime rt = Runtime.getRuntime();
    	long startTimeTree = System.currentTimeMillis();
    	TreeMap treemap = new TreeMap();
    	for(String s : inputData)
    	{
    		treemap.put(s, s);
    	}
    	long endTimeTree = System.currentTimeMillis();
    	long startTimeHash = System.currentTimeMillis();
    	HashTable hash = new HashTable(10,.7);
    	for(String s : inputData)
    		hash.put(s, s);
    	long endTimeHash = System.currentTimeMillis();
    	
    	System.out.println("Time of a TreeMap to insert " + 
    	inputData.size() + " items: " + (endTimeTree - startTimeTree));
    	
    	System.out.println("Time of a Hash Table to insert " + 
    	    	inputData.size() + " items: " + (endTimeHash - startTimeHash));
    	report("PUT", "HASHTABLE", (endTimeHash - startTimeHash), 0);
    	report("PUT", "TREEMAP", (endTimeTree - startTimeTree), 0);
   
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
    		treemap.remove(inputData.get(i));
    	long endTimeTree = System.currentTimeMillis();
    	
    	HashTable hash = new HashTable(1000,.8);
    	for(String s : inputData)
    		hash.put(s, s);
    	i = 0;
    	long startTimeHash = System.currentTimeMillis();
    	while(!hash.isEmpty())
    		hash.remove(inputData.get(i));
    	long endTimeHash = System.currentTimeMillis();
    	
    	System.out.println("Time of a TreeMap to delete " + 
    	    	inputData.size() + " items: " + (endTimeTree - startTimeTree));
    	    	
    	System.out.println("Time of a Hash Table to delete " + 
    	    	inputData.size() + " items: " + (endTimeHash - startTimeHash));
    	report("REMOVE", "HASHTABLE", (endTimeHash - startTimeHash), 0);
    	report("REMOVE", "TREEMAP", (endTimeTree - startTimeTree), 0);
    	
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
        File file = new File("data/" + filename);
        BufferedReader br = new BufferedReader(new FileReader(file));
        inputData = new ArrayList<>();
        String line = br.readLine();
        while (line != null) {
            inputData.add(line);
            line = br.readLine();
        }
        br.close();
    }
    
    private void loadFiles(String filename) throws IOException {
    	
    	// Gets the list of data files to be used.
    	File file = new File("data/" + filename);
        BufferedReader br = new BufferedReader(new FileReader(file));
        files = new ArrayList<>();
        String line = br.readLine();
        while (line != null) {
        	line = line.split(",")[0];
            files.add(line);
            line = br.readLine();
        }
        br.close();
        files.remove(0);
    }
    
    private void report(String op, String ds, long time, long mem)
    {
    	String report = "|";
    	for(int i = curFile.length(); i < 20; i++)
    		report += " ";
    	report += curFile + "|";
    	for(int i = op.length(); i < 15; i++)
    		report += " ";
    	report += op + "|";
    	for(int i = ds.length(); i < 15; i++)
    		report += " ";
    	report += ds + "|";
    	for(int i = String.valueOf(time).length(); i < 25; i++)
    		report += " ";
    	report += time + "|";
    	for(int i = String.valueOf(mem).length(); i < 15; i++)
    		report += " ";
    	report += mem + "|";
    	
    	reports.add(report);
    }
    
}