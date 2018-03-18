import java.util.ArrayList;
import java.util.Map;

public class HashTable<K, V> implements HashTableADT<K, V> {
    /* Instance variables and constructors
     */

	HashNode[] table;
	int maxEntries;
	int numItems;
	
	public HashTable(int initialCapacity, double loadFactor)
	{
		table = new HashNode[initialCapacity];
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
    	if(numItems >= maxEntries)
    	{
    		expandTable();
    	}
    	int key1 = hashFunction(key);			//key1 is the hashIndex / table index
    	if(table[key1] != null)				
    		handleCollision(key, value);
    	else
    	{
    		table[key1] = new HashNode(key, value);
    		System.out.println("Hash Index: " + key1);
    	}
    	
    	return (V) table[key1].getValue();
    	
    	
    	
        
    }

    /*
     * Expands the table to be tablesize * 2 + 1 (approx. next prime number)
     * Then rehashes all the values and assigns them into the new hashtable
     */
    private void expandTable() {
		HashNode[] temp = new HashNode[table.length * 2 + 1];
		
		for(int i = 0; i < table.length; i++)
		{
			if(table[i]!= null)
				temp[hashFunction((K) table[i].getKey())] =  table[i];
		}
		maxEntries = maxEntries * 2 + 1;
		
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

    @Override
    public V get(K key) {
        int key1 = hashFunction(key);
        if(table[key1].getKey() == key)
        	return (V) table[key1].getValue();
        else{
        	key1 = secondHashFunction(key);
        	int i = 0;
        	while(table[key1].getKey() != key)
        	{
        		key1 += i*key1;
        		i++;
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
        
        return table == null;
    }

    @Override
    public V remove(K key) {
       
    	int key1 = hashFunction(key);
    	if(table[key1].getKey() != key){
        	key1 = secondHashFunction(key);
        	int i = 0;
        	while(table[key1].getKey() != key)
        	{
        		key1 += i*key1;
        		i++;
        	}
        }
    	V temp = (V) table[key1].getValue();
    	table[key1] = null; 
        return temp;
    }

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
    	
    	return key1 % maxEntries;
    }
    
   /*
    * Helper method for put(K key, V value) - handles collisions by double hashing
    * @param key is the key of the item to be inserted into the hash table
    * @param value is the value of the item to be inserted into the hash table
    */
   private void handleCollision(K key, V value)
   {
	   int key1 = secondHashFunction(key);
	   int i = 0;
	   while(table[key1] != null)
	   {
		   
		   key1 += i*key1;
		   key1 = key1%maxEntries;
		   i++;
	   }
	   System.out.println("Hash Index: " + key1);
	   table[key1] = new HashNode(key, value);
   }
   
   /*
    * @param key is the key of the value to be inserted into the hashtable
    * @return is the integer hash index / table index of the item inserted
    * Creates a hash index by doing the algorithm of hashFunction(K key) in reverse order
    */
   private int secondHashFunction(K key)
   {
	   String str = key.toString();
	   int key1 = 0;
	   for(int i = str.length()-1; i >= 0; i--)
	   {
		   key1+= (int)((int)str.charAt(i) * Math.pow(2, i));
	   }
	   return key1 % maxEntries;
   }
}
class HashNode<K,V>
{
	K key;
	V value;
	public HashNode(K key, V value)
	{
		this.key = key;
		this.value = value;
	}
	
	public K getKey()
	{
		return key;
	}
	
	public V getValue()
	{
		return value;
	}
}


