import java.util.ArrayList;
import java.util.Map;
import java.util.NoSuchElementException;

public class HashTable<K, V> implements HashTableADT<K, V> {
    /* Instance variables and constructors
     */

	HashNode[] table;	//The hash table
	int maxEntries;
	int numItems;		//Current number of items in the table
	double loadFactor;
	
	public HashTable(int initialCapacity, double loadFactor)
	{
		table = new HashNode[initialCapacity];
		this.loadFactor = loadFactor;
		maxEntries = (int) (initialCapacity * loadFactor);
		numItems = 0;
	}
	
	
	/**
    *
    * @param key : The key that goes into the hashtable
    * @param value: The Value associated with the key
    * @return value of the key added to the hashtable,
    *      throws NullPointerException if key is null
    */
	@Override
    public V put(K key, V value) {
    	if(key == null)
    		throw new NullPointerException();
    	numItems++;
    	if(numItems >= table.length || numItems >= maxEntries)
    	{
    		expandTable();
    	}
    	int key1 = hashFunction(key);			//key1 is the hashIndex / table index
    	if(table[key1] != null)
    	{
    		if(!table[key1].getKey().equals("SENTINEL"))
    			handleCollision(key, value);
    	}
    	else
    	{
    		table[key1] = new HashNode(key, value);
    	}
    	
    	return (V) table[key1].getValue();
    	
    	
    	
        
    }

    /*
     * Expands the table to be tablesize * 2 + 1 (approx. next prime number)
     * Then rehashes all the values and assigns them into the new hashtable
     */
    private void expandTable() {
		HashNode[] temp = new HashNode[table.length * 2 + 1];
		maxEntries = (int) (table.length*loadFactor);
		
		
		
		for(int i = 0; i < table.length; i++)
		{
			if(table[i]!= null)
			{
				int key1 = hashFunction((K) table[i].getKey());
				if(temp[key1] != null)
				{
					 while(temp[key1] != null)
					   {
						   
						   key1++;
						   key1 = key1%table.length;
					   }
				
				}
				temp[key1] = table[i];
			}
		}
		table = temp;
	}

    /**
     * Clear the hashtable of all its contents
     */
    @Override
    public void clear() {
      for(int i = 0; i < table.length; i++)
      {
    	  table[i] =  null;
      }
    }

    
    /**
     * @param key: The key for which the value is returned
     * @return The value associated with the key,
     *          else throws NoSuch Element Exception
     */
    @Override
    public V get(K key) {
        int key1 = hashFunction(key);
        if(table[key1].getKey() == key)
        	return (V) table[key1].getValue();
        else{
        	int counter = 0;
        	while(table[key1].getKey() != key)
        	{
        		key1++;
        		key1 %= table.length;
        		counter++;
        		if(counter > table.length)
        			throw new NoSuchElementException();
        		
        	}
        	return (V) table[key1].getValue();
        }
        
    }
    
    /**
     * Checks if the hashtable is empty
     * @return true : if Empty, else False
     */
    @Override
    public boolean isEmpty() {
        
        return !(numItems>0);
    }
    
    /*
     *  /**
     *
     * @param key: Key of the entry to be removed
     * @return value: Value of the key-value pair removed,
     *          null if key did not have a mapping
     * @throws NullPointerException if key is null
     */
    @Override
    public V remove(K key) {
       
    	if(key == null)
    		throw new NullPointerException();
    	int key1 = hashFunction(key);
    	if(table[key1].getKey() != key){
    		int counter = 0;
        	while(table[key1].getKey() != key)
        	{
        		key1++;
        		key1 %= table.length;
        		counter++;
        		if(counter > table.length)
        			throw new NoSuchElementException();
        	}
        }
    	V temp = (V) table[key1].getValue();
    	table[key1] = new HashNode(Integer.MAX_VALUE,"-"); 
    	numItems--;
        return temp;
    }

    /*
     * @return int is the size of the table
     */
    @Override
    public int size() {
       
        return numItems;
    }
    
    
    /*
     * @param key is the key of the item to be inserted into the hashtable
     * @return is an integer hash index / location of the item in the hash table
     * Takes the toString of the key and creates a hashcode by extracting each character's
     * ASCII code and multiplying it by increasing powers of two, then folding these parts
     * and dividing it by the maxEntries / table size to ensure proper wrapping
     */
    private int hashFunction(K key)
    {
    	String str = key.toString();
    	int key1 = 0;
    	for(int i = 0; i < str.length(); i++)
    	{
    		key1 +=  (int) ((int)(str.charAt(i)) * Math.pow(2, i));
    	}
    	key1 = Math.abs(key1);
    	return key1 % table.length;
    }
    
   /*
    * Helper method for put(K key, V value) - handles collisions by double hashing
    * @param key is the key of the item to be inserted into the hash table
    * @param value is the value of the item to be inserted into the hash table
    */
   private void handleCollision(K key, V value)
   {
	   int key1 = hashFunction(key);
	   while(table[key1] != null)
	   {
		   
		   key1 ++;
		   key1 = key1%table.length;
	   }
	   table[key1] = new HashNode(key, value);
   }
   

}
//Hashnode class - each node has a Key and a Value
class HashNode<K,V>
{
	K key;
	V value;
	public HashNode(K key, V value)
	{
		this.key = key;
		this.value = value;
	}
	
	/*
	 * @return is the key of the node
	 */
	public K getKey()
	{
		return key;
	}
	/*
	 * @return is the value of the node
	 */
	public V getValue()
	{
		return value;
	}
}


