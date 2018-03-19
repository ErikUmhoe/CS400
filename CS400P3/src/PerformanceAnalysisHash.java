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
    private HashTable hash;
    private TreeMap treemap;
    
    /**
     * Constructor that compares the TreeMap and HashTable data structures.
     * 
     * @throws IOException: If files couldn't be read.
     */
    public PerformanceAnalysisHash() throws IOException{
    	setup();
    }

    /**
     * Constructor to print the report to a given file after comparing the
     * TreeMap and HashTable data structures.
     * 
     * @param details_filename: file that the report gets printed to.
     */
    public PerformanceAnalysisHash(String details_filename){
        try {
        	setup();
        } catch (IOException e) {
        	System.out.println("Error reading files");
        }
        printToFile(details_filename);
    }
    
    /**
     * Calls the methods needed to compare the times / memory
     * usage of a TreeMap vs a HashTable.
     */
    @Override
    public void compareDataStructures() {
        compareInsertion();
        compareSearch();
        compareDeletion();
    }

    /**
     * Prints the report.
     */
    @Override
    public void printReport() {
    	
        System.out.println("The report name : Performance Analysis Report");
        System.out.println("-----------------------------------------------"
        		+ "-------------------------------------------------");
        System.out.println("|            FileName|      Operation| Data Structure|"
        		+ "   Time Taken (micro sec)|     Bytes Used|");
        System.out.println("-----------------------------------------------"
        		+ "-------------------------------------------------");
        // Prints the different reports onto a separate line.
        for(String s: reports)
        	System.out.println(s);
        System.out.println("-----------------------------------------------"
        		+ "-------------------------------------------------");
    }
    
    /**
     * Standalone method for comparing insertion operation
     * across HashTable and TreeMap.
     * Compares insertion by comparing time / memory of filling
     * a TreeMap vs a Hashtable.
     */
    @Override
    public void compareInsertion(){
    	
    	Runtime rt = Runtime.getRuntime();
    	
    	long startTimeTree = System.currentTimeMillis();
    	long startMemTree = rt.freeMemory();
    	treemap = new TreeMap();
    	for(String s : inputData)
    		treemap.put(s, s);
    	long endTimeTree = System.currentTimeMillis();
    	long endMemTree = rt.freeMemory();
    	
    	long startTimeHash = System.currentTimeMillis();
    	long startMemHash = rt.freeMemory();
    	hash = new HashTable(100000,.7);
    	for(String s : inputData)
    	{
    		System.out.println(hash.put(s, s));
    	}
    	long endTimeHash = System.currentTimeMillis();
    	long endMemHash = rt.freeMemory();
    	
    	report("PUT", "HASHTABLE", ((endTimeHash - startTimeHash) * 1000), (endMemHash - startMemHash));
    	report("PUT", "TREEMAP", ((endTimeTree - startTimeTree) * 1000), (endMemTree - startMemTree));
   
    }
    
    /**
     * Standalone method for comparing deletion operation
     * across HashTable and TreeMap.
     * Compares deletion by filling a TreeMap and HashTable then 
     * comparing time / memory it takes to delete all of the elements.
     */
    @Override
    public void compareDeletion() {
    	
    	Runtime rt = Runtime.getRuntime();

    	int i = 0;
    	long startTimeTree = System.currentTimeMillis();
    	long startMemTree = rt.freeMemory();
    	while(!treemap.isEmpty())
    	{
    		treemap.remove(inputData.get(i));
    		i++;
    	}
    	long endTimeTree = System.currentTimeMillis();
    	long endMemTree = rt.freeMemory();
    	
    	i = 0;
    	long startTimeHash = System.currentTimeMillis();
    	long startMemHash = rt.freeMemory();
    	while(!hash.isEmpty())
    	{
    		System.out.println(hash.remove(inputData.get(i)));
    		i++;
    	}
    	long endTimeHash = System.currentTimeMillis();
    	long endMemHash = rt.freeMemory();
    	
    	report("REMOVE", "HASHTABLE", ((endTimeHash - startTimeHash) * 1000), (endMemHash - startMemHash));
    	report("REMOVE", "TREEMAP", ((endTimeTree - startTimeTree) * 1000), (endMemTree - startMemTree));
    	
    }

    /**
     * Standalone method for comparing search operation
     * across HashTable and TreeMap.
     * Compares search by filling a TreeMap and HashTable
     * and comparing times / memory to retrieve a random element.
     */
    @Override
    public void compareSearch() {
    	
    	Runtime rt = Runtime.getRuntime();
    	String random = inputData.get((int)(Math.random() * inputData.size()));

    	long startTimeTree = System.currentTimeMillis();
    	long startMemTree = rt.freeMemory();
    	treemap.get(random);
    	long endTimeTree = System.currentTimeMillis();
    	long endMemTree = rt.freeMemory();

    	long startTimeHash = System.currentTimeMillis();
    	long startMemHash = rt.freeMemory();
    	hash.get(random);
    	long endTimeHash = System.currentTimeMillis();
    	long endMemHash = rt.freeMemory();

    	report("GET", "HASHTABLE", ((endTimeHash - startTimeHash) * 1000), (endMemHash - startMemHash));
    	report("GET", "TREEMAP", ((endTimeTree - startTimeTree) * 1000), (endMemTree - startMemTree));

    }


    /**
     * Opens the given test file and stores the objects each line as a string.
     * 
     * @param filename: Name of the file that contains the data.
     * @throws IOException: Throws if the file couldn't be read.
     */
    @Override
    public void loadData(String filename) throws IOException {

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
    
    /**
     * Loads the different files to be read.
     * 
     * @param filename: name of the file that contains the other files.
     * @throws IOException: Throws if the file couldn't be read.
     */
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
    
    /**
     * Adds a report to the reports array.
     * 
     * @param op: Operation that took place.
     * @param ds: Data structure.
     * @param time: Time it took to complete.
     * @param mem: Memory it took to complete.
     */
    private void report(String op, String ds, long time, long mem) {
    	
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
    
    /**
     * Loads the files to be read and compares the data.
     * @throws IOException: If the files can not be read.
     */
    private void setup() throws IOException {
    	
    	reports = new ArrayList<>();
    	loadFiles("data_details.txt");
    	// Compares the data structures with the data in each file.
    	for(String s: files)
    	{
    		loadData(s);
    		curFile = s;
    		compareDataStructures();
    	}
    }
    /**
     * Prints the report into a given file.
     * @param filename: given file to be printed.
     */
    private void printToFile(String filename) {
    	
    	BufferedWriter writer = null;
    	try {
    		File file = new File(filename);
    		if(!file.exists())
    			file.createNewFile();
    		FileWriter fileWriter = new FileWriter(file);
    		writer = new BufferedWriter(fileWriter);
    		
    		writer.write("The report name : Performance Analysis Report");
    		writer.newLine();
    		writer.write("-----------------------------------------------"
    				+ "-------------------------------------------------");
    		writer.newLine();
    		writer.write("|            FileName|      Operation| Data Structure|"
        		+ "   Time Taken (micro sec)|     Bytes Used|");
    		writer.newLine();
    		writer.write("-----------------------------------------------"
    				+ "-------------------------------------------------");
    		writer.newLine();
    		// Writes the reports on a separate line.
    		for(String s: reports)
    		{
    			writer.write(s);
    			writer.newLine();
    		}
    		writer.write("-----------------------------------------------"
    				+ "-------------------------------------------------");

    	} catch (IOException e) {
    		System.out.println("Error writting to file.");
    	}
    	finally {
    		try {
    			if(writer != null)
    				writer.close();
    		} catch (Exception e) {
    			System.out.println("Error closing the BufferedWriter");
    		}
    	}
    }
    
}