import java.util.Comparator;

/**
 * Class for sorting lists that implement the IndexedUnsortedList interface,
 * using ordering defined by class of objects in list or a Comparator.
 * As written uses Mergesort algorithm.
 *
 * @author CS221
 */
public class Sort
{	
	/**
	 * Returns a new list that implements the IndexedUnsortedList interface. 
	 * As configured, uses WrappedDLL. Must be changed if using 
	 * your own IUDoubleLinkedList class. 
	 * 
	 * @return a new list that implements the IndexedUnsortedList interface
	 */
	private static <T> IndexedUnsortedList<T> newList() 
	{
		return new IUDoubleLinkedList<T>(); //TODO: replace with your IUDoubleLinkedList for extra-credit
	}
	
	/**
	 * Sorts a list that implements the IndexedUnsortedList interface 
	 * using compareTo() method defined by class of objects in list.
	 * DO NOT MODIFY THIS METHOD
	 * 
	 * @param <T>
	 *            The class of elements in the list, must extend Comparable
	 * @param list
	 *            The list to be sorted, implements IndexedUnsortedList interface 
	 * @see IndexedUnsortedList 
	 */
	public static <T extends Comparable<T>> void sort(IndexedUnsortedList<T> list) 
	{
		mergesort(list);
	}

	/**
	 * Sorts a list that implements the IndexedUnsortedList interface 
	 * using given Comparator.
	 * DO NOT MODIFY THIS METHOD
	 * 
	 * @param <T>
	 *            The class of elements in the list
	 * @param list
	 *            The list to be sorted, implements IndexedUnsortedList interface 
	 * @param c
	 *            The Comparator used
	 * @see IndexedUnsortedList 
	 */
	public static <T> void sort(IndexedUnsortedList <T> list, Comparator<T> c) 
	{
		mergesort(list, c);
	}
	
	/**
	 * Mergesort algorithm to sort objects in a list 
	 * that implements the IndexedUnsortedList interface, 
	 * using compareTo() method defined by class of objects in list.
	 * DO NOT MODIFY THIS METHOD SIGNATURE
	 * 
	 * @param <T>
	 *            The class of elements in the list, must extend Comparable
	 * @param list
	 *            The list to be sorted, implements IndexedUnsortedList interface 
	 */
	
	/**
	 * 	Algorithm: 
	 * 1. If the list has more than one element, divide elements into 
	 * equal-sized left and right lists. 
	 * 2. Recursively mergesort both halves.
	 * 3. Compare the first element of each sorted half and add the 
	 * smaller of the two elements to the end of the original list. 
	 * Continue until all elements have been returned to the original list.
	 */

	private static <T extends Comparable<T>> void mergesort(IndexedUnsortedList<T> list)
	{
		// TODO: Implement recursive mergesort algorithm 
		
		// Base Case, if length is 0 or 1 then we don't have to do anything
		if (list.size() < 2) {
			return;
		}
		
		// Create two lists by splitting original list in half
		IndexedUnsortedList<T> left = new IUDoubleLinkedList<T>();
		IndexedUnsortedList<T> right = new IUDoubleLinkedList<T>();

		// Add first half to left list and add second half to right list
		int mid = list.size()/2;
		
		for (int i=0;i<mid;i++) {
			left.add(list.removeFirst());
		}
		while (!list.isEmpty()) {
			right.add(list.removeFirst());
		}
		
		// Recursion --> mergesort each half of the list again
		mergesort(left);
		mergesort(right);
		
		// Here we now have a list where all the elements are ordered
		
		/**
		 * Notes for Compare To:
		 * Add first element of each list, alternating if necessary
		 * so you add the smallest number to the list.
		 */
		
		while(!left.isEmpty() && !right.isEmpty() ) { // if same number doesn't matter which list we remove from
			if (left.first().compareTo(right.first()) <= 0 ) {
				list.add(left.removeFirst());
			} else { 
				list.add(right.removeFirst());
			}
		}
		
		while (!left.isEmpty()) {
			list.add(left.removeFirst());
		}
		
		while (!right.isEmpty()) {
			list.add(right.removeFirst());
		}
		
	}
		
	/**
	 * Mergesort algorithm to sort objects in a list 
	 * that implements the IndexedUnsortedList interface,
	 * using the given Comparator.
	 * DO NOT MODIFY THIS METHOD SIGNATURE
	 * 
	 * @param <T>
	 *            The class of elements in the list
	 * @param list
	 *            The list to be sorted, implements IndexedUnsortedList interface 
	 * @param c
	 *            The Comparator used
	 */
	private static <T> void mergesort(IndexedUnsortedList<T> list, Comparator<T> c)
	{
		
		// TODO: Implement recursive mergesort algorithm using Comparator
		
		// Base Case, if length is 0 or 1 then we don't have to do anything
		if (list.size() < 2) {
			return;
		}
		
		// Create two lists by splitting original list in half
		IndexedUnsortedList<T> left = newList();
		IndexedUnsortedList<T> right = newList();

		// Add first half to left list and add second half to right list
		int mid = list.size()/2;
		
		for (int i=0;i<mid;i++) {
			left.add(list.removeFirst());
		}
		while (!list.isEmpty()) {
			right.add(list.removeFirst());
		}
		
		// Recursion --> mergesort each half of the list again
		mergesort(left, c);
		mergesort(right, c);
		
		// Here we now have a list where all the elements are ordered
		
		/**
		 * Notes for Compare To:
		 * Add first element of each list, alternating if necessary
		 * so you add the smallest number to the list.
		 */
		
		while(!left.isEmpty() && !right.isEmpty() ) { // if same number doesn't matter which list we remove from
			if (c.compare(left.first(), right.first()) <= 0) {
				list.add(left.removeFirst());
			} else { 
				list.add(right.removeFirst());
			}
		}
		
		while (!left.isEmpty()) {
			list.add(left.removeFirst());
		}
		
		while (!right.isEmpty()) {
			list.add(right.removeFirst());
		}
		
	
	}
}
