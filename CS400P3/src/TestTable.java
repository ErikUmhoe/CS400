import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class TestTable {

	public static void main(String[] args) throws IOException {
		HashTable<Object, Object> table = new HashTable(10,.7);
		
		table.put("ABC", 1);
		table.put("123", 2);
		table.put("gafae", 3);
		table.put(new Integer(11), 4);
		table.put(new Integer(31), 5);
		table.put(314.214, 6);
		table.put("FAFA", 7);
		table.put("XD", 8);
		table.put("131", 9);
		table.put("1241241", 10);
		table.put("2412432412", 11);
		
		for(int i = 0; i < table.table.length; i++)
		{
			if(table.table[i] != null)
				System.out.println("i: " + i + " key: " + table.table[i].getKey());
		}	
		
		
//		PerformanceAnalysisHash a = new PerformanceAnalysisHash();
//		a.compareInsertion();


	}

}
