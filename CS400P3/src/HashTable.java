import java.util.ArrayList;
import java.util.Map;

public class HashTable<K, V> implements HashTableADT<K, V> {
    /* Instance variables and constructors
     */

	ArrayList<V> table;
	int maxEntries;
	
    @Override
    public V put(K key, V value) {
    	int key1 = hashFunction(key);
    	table.set(key1, value);
    	return table.get(key1);
    	
    	
    	
        
    }

    public HashTable(int initialCapacity, double loadFactor)
    {
    	table = new ArrayList<V>(initialCapacity);
    	maxEntries = (int) (initialCapacity * loadFactor);
    }
    
    @Override
    public void clear() {
      for(int i = 0; i < table.size(); i++)
      {
    	  table.set(i, null);
      }
    }

    @Override
    public V get(K key) {
        int key1 = hashFunction(key);
        return table.get(key1);
        
    }

    @Override
    public boolean isEmpty() {
        
        return table.isEmpty();
    }

    @Override
    public V remove(K key) {
       
    	int key1 = hashFunction(key);
    	V temp = table.get(key1);
    	table.set(key1, null); 
        return temp;
    }

    @Override
    public int size() {
       
        return table.size();
    }
    
    public int hashFunction(K key)
    {
    	String str = key.toString();
    	int key1 = 0;
    	for(int i = 0; i < str.length(); i++)
    	{
    		key1 +=  (int) ((int)(str.charAt(i)) * Math.pow(2, 1));
    	}
    	return key1 & table.size();
    }
}


