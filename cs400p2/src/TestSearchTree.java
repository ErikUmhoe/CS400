import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/////////////////////////////////////////////////////////////////////////////
// Semester:         CS400 Spring 2018
// PROJECT:          cs400_p2_201801
// FILES:            TestSearchTree.java
//                   SearchTreeADT.java
//                   BalancedSearchTree.java
//
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/////////////////////////////////////////////////////////////////////////////
// Semester:         CS400 Spring 2018
// PROJECT:          cs400_p2_201801
// FILES:            TestSearchTree.java
//                   SearchTreeADT.java
//                   BalancedSearchTree.java
//
// USER:             deppeler
//
// Instructor:       Deb Deppeler (deppeler@cs.wisc.edu)
// Bugs:             no known bugs, but not complete either
//
// 2018 Feb 8, 2018 5:13:18 PM TestSearchTree.java 
//////////////////////////// 80 columns wide //////////////////////////////////

/**
 * @author 
 *
 */
public class TestSearchTree {

	SearchTreeADT<String> tree = null;
	String expected = null;
	String actual = null;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		tree = new BalancedSearchTree<String>();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test01_isEmpty_on_empty_tree() {
		expected = "true";
		actual = "" + tree.isEmpty();
		if (! expected.equals(actual))
			fail("expected: "+expected+ " actual: "+actual);
	}

	@Test
	public void test02_ascending_order_on_empty_tree() {
		expected = "";
		actual = tree.inAscendingOrder();
		if (! expected.equals(actual))
			fail("expected: "+expected+ " actual: "+actual);
	}

	@Test
	/** tests that the height of an empty tree is 0 */
	public void test03_height_on_empty_tree() {
		expected = "0";
		actual = "" + tree.height();
		if (! expected.equals(actual))
			fail("expected: "+expected+ " actual: "+actual);
	}

	@Test
	public void test04_isEmpty_after_one_insert() {
		tree.insert("1");
		expected = "false";
		actual = "" + tree.isEmpty();
		if (! expected.equals(actual))
			fail("expected: "+expected+ " actual: "+actual);
	}

	@Test
	/** tests that the ascending order after inserting A item is "A," */
	public void test05_ascending_order_after_one_insert() {
		tree.insert("A");
		expected = "A,";
		actual = tree.inAscendingOrder();
		if (! expected.equals(actual))
			fail("expected: "+expected+ " actual: "+actual);
	}

	@Test
	/** tests that the height after inserting A is 1 */
	public void test06_height_after_one_insert() {
		tree.insert("A");
		expected = "1";
		actual = "" + tree.height();
		if (! expected.equals(actual))
			fail("expected: "+expected+ " actual: "+actual);
	}

	@Test
	/** tests that the height after inserting A and deleting it is 0 */
	public void test07_height_after_one_insert_and_delete() {
		tree.insert("A");
		tree.delete("A");
		expected = "0";
		actual = "" + tree.height();
		if (! expected.equals(actual))
			fail("expected: "+expected+ " actual: "+actual);
	}
	
	@Test
	/** Inserts a random number(10,20) of nodes,
	 *  saving the number of nodes added.
	 *  Then, call height() and determine if the 
	 *  result of height is as expected (the number of nodes added)*/
	 
	public void test08_height_after_many_insert()
	{
		Integer rand = (int) ((Math.random()*10)+10);
		for(int i = 0; i < rand; i++)
		{
			String longString = "Thisisareallylong"
					+ "stringandcharactersofitwillbeinsertedintotheRBTasnodes";
			tree.insert(longString.substring(i, i+1));
		}
		
		expected = "" + rand;
		
		actual = "" + tree.height();
		
		if(! expected.equals(actual))
			fail("expected: " + expected + " actual: " + actual);
		
	}
	
	
	@Test
	/**
	 * Call lookup() on an empty tree, testing to make sure 
	 * that the method correctly returns false without throwing an error
	 */
	public void	test09_lookup_empty()
	{
		expected = "" + false;
		actual = "" + tree.lookup("hello");
		
		if(! expected.equals(actual))
			fail("expected: " + expected + " actual: " + actual);
	}

	@Test
	/*
	 *Fill a tree with many new nodes and lookup 
	 *a string that was inserted, testing to make sure that lookup() returns true 
	 */
	public void test10_lookup_filled_find()
	{
		String[] inserts = new String[] {"apple", "dog", "zebra", "banana", "hello" , "citrus"};
		
		expected = "" + true;
		for(int i = 0; i < inserts.length; i++)
		{
			tree.insert(inserts[i]);
			
		}
		
		actual = "" + tree.lookup("dog");
		
		if(!expected.equals(actual))
			fail("expected: " + expected + " actual: " + actual);
	}
	
	@Test
	/*Call the delete method on an emptry tree, 
	 * testing so see if this method will return without error
	 */
	public void test11_delete_empty_tree()
	{
		try
		{
			tree.delete("hello");
		}catch(Exception e)
		{
			fail("Expected: No error being thrown. Acutal: " + e.getMessage());
		}
	}
	
	@Test
	/*
	 * Fill the tree with nodes, storing all of the keys for these nodes in an array / arraylist. 
	 *Call remove(T item) on the tree, removing item from the arraylist, then calling inAscendingOrder
	 *to see if the keys from the nodes returned are the same as the keys inserted from the arraylist (minus the item removed)
	 */
	public void test12_delete_full_tree()
	{
		ArrayList<String> inserts = new ArrayList<String>();
		inserts.add("hello");
		inserts.add("cat");
		inserts.add("zebra");
		inserts.add("apple");
		inserts.add("liger");
		
		for(int i = 0; i < inserts.size(); i++)
		{
			tree.insert(inserts.get(i));
		}
		
		tree.delete("apple");
		inserts.remove("apple");
		actual = tree.inAscendingOrder();
		expected = "cat, hello, liger, zebra";
		
		if(!expected.equals(actual))
			fail("Expected: " + expected + " actual: " + actual);	
	}
	
	@Test
	/*
	 * Call insert(null) on a tree and test if insert()
	 *  throws a IllegalArgumentException
	 */
	public void test13_insert_null_item()
	{
		try
		{
			tree.insert(null);
			fail("Expected an IllegalArgumentException to be thrown. No such exception was thrown.");
		}catch(IllegalArgumentException e)
		{
			
		}catch(Exception e)
		{
			fail("Expected an IllegalArgumentException to be thrown. "
					+ "Actual exception thrown: " + e.getMessage());
		}
	}
	
	@Test
	/*
	 * Insert many items in a random order, with the items being stored in an arraylist.
	 * Then sort the arraylist in ascending order,
	 *call inAscendingOrder() and test whether the keys are sorted / returned
	 *in the same order (correct ascending order)
	 */
	public void test14_insert_many_ascending_order()
	{
		ArrayList<String> inserts = new ArrayList<String>();
		inserts.add("hello");
		inserts.add("cat");
		inserts.add("zebra");
		inserts.add("apple");
		inserts.add("liger");
		for(int i = 0; i < inserts.size(); i++)
		{
			tree.insert(inserts.get(i));
		}
		actual = tree.inAscendingOrder();
		Collections.sort(inserts);
		expected = "";
		for(String s : inserts)
			expected += s + ", ";
		expected = expected.substring(0, expected.length()-2);
		
		if(!expected.equals(actual))
			fail("Expected: " + expected + " actual: " + actual);
		
	}
	
	@Test
	/*
	 * Fill a tree with many new nodes and lookup a string that was not inserted, 
	 * testing to make sure that lookup() returns false
	 */
	public void test15_lookup_filled_no_find()
	{
		String[] inserts = new String[] {"apple", "dog", "zebra", "banana", "hello" , "citrus"};
		expected = "" + false;
		for(int i = 0; i < inserts.length; i++)
		{
			tree.insert(inserts[i]);
		}
		
		actual = "" + tree.lookup("I am 100% positive that this string does not exist in the tree");
		
		if(!expected.equals(actual))
			fail("Expected: " + expected + " actual: " + actual);
	}
	
	@Test
	/*
	 * Insert a duplicate item into a tree.
	 * Fails if no exception is thrown.
	 */
	public void test16_insert_duplicate()
	{
		try
		{
			
			tree.insert("A");
			tree.insert("A");
			fail("Expected an exception to be thrown."
					+ " No exception was thrown");
			
		}catch(Exception e)
		{
			
		}
		
		
	}
}