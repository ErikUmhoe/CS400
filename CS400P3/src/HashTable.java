import java.util.ArrayList;
import java.util.Map;

public class HashTable<K, V> implements HashTableADT<K, V> {
    /* Instance variables and constructors
     */

	ArrayList<HashNode> table;
	int maxEntries;
	
	public HashTable(int initialCapacity, double loadFactor)
	{
		table = new ArrayList<HashNode>(initialCapacity);
		maxEntries = (int) (initialCapacity * loadFactor);
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
    	if(maxEntries <= table.size())
    	{
    		expandTable();
    	}
    	int key1 = hashFunction(key);			//key1 is the hashIndex / table index
    	if(table.get(key1) != null)				
    		handleCollision(key, value);
    	else
    		table.set(key1, new HashNode(key, value));
    	return (V) table.get(key1).getValue();
    	
    	
    	
        
    }

    /*
     * Expands the table to be tablesize * 2 + 1 (approx. next prime number)
     * Then rehashes all the values and assigns them into the new hashtable
     */
    private void expandTable() {
		ArrayList<HashNode> temp = new ArrayList<HashNode>(table.size() * 2 + 1);
		
		for(int i = 0; i < maxEntries; i++)
		{
			temp.set(hashFunction((K) table.get(i).getKey()),  table.get(i));
		}
		maxEntries = maxEntries * 2 + 1;
		
	}

    /**
     * Clear the hashtable of all its contents
     */
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
        return (V) table.get(key1).getValue();
        
    }
    
    /**
     * Checks if the hashtable is empty
     * @return true : if Empty, else False
     */
    @Override
    public boolean isEmpty() {
        
        return table.isEmpty();
    }

    @Override
    public V remove(K key) {
       
    	int key1 = hashFunction(key);
    	V temp = (V) table.get(key1).getValue();
    	table.set(key1, null); 
        return temp;
    }

    @Override
    public int size() {
       
        return table.size();
    }
    
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
    
   private void handleCollision(K key, V value)
   {
	   int key1 = secondHashFunction(key);
	   int i = 0;
	   while(table.get(key1) != null)
	   {
		   
		   key1 += i*key1;
		   i++;
	   }
	   table.set(key1, new HashNode(key, value));
   }
   
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


