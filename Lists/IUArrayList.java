import java.util.Arrays;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.ConcurrentModificationException;

/**
 * Array-based implementation of IndexedUnsortedList.
 * An Iterator with working remove() method is implemented, but
 * ListIterator is unsupported. 
 * 
 * @author 
 *
 * @param <T> type to store
 */

/**
 * 
 * @author elena
 *
 * @param <T>
 * 
 * Code to expand the array (if it is already at full capacity:)
 * if(rear == array.length){
			T[] temp = (T[])(new Object[array.length*2]);
			for(int i = 0; i < rear; i++){
				temp[i] = array[i];
			}
			array = temp;
 * Put at beginning of each "add" method
 */

public class IUArrayList<T> implements IndexedUnsortedList<T> {
	private static final int DEFAULT_CAPACITY = 10;
	private static final int NOT_FOUND = -1;
	
	private T[] array;
	private int rear;
	private int modCount;
	
	/** Creates an empty list with default initial capacity */
	public IUArrayList() {
		this(DEFAULT_CAPACITY);
	}
	
	/** 
	 * Creates an empty list with the given initial capacity
	 * @param initialCapacity
	 */
	@SuppressWarnings("unchecked")
	public IUArrayList(int initialCapacity) {
		array = (T[])(new Object[initialCapacity]);
		rear = 0;
		modCount = 0;
	}
	
	/** Double the capacity of array */
	private void expandCapacity() {
		array = Arrays.copyOf(array, array.length*2);
	}

	@Override
	public void addToFront(T element) {
		if (isEmpty()) {
			array[0] = element;
		} else {
			if(rear >= array.length){
				expandCapacity();
			}
			for(int i = rear; i > 0; i--){
				array[i] = array[i-1];
			}
			array[0] = element;
		}
		rear++;
		modCount++;
	}

	@Override
	public void addToRear(T element) {
		add(element);
	}

	@Override
	public void add(T element) {
		if(rear >= array.length){
			expandCapacity();
		}
		array[rear] = element;
		rear++;
		modCount++;		
	}

	@Override
	public void addAfter(T element, T target) {
		for (int i = 0; i < rear; i++) {
			if (array[i].equals(target)) {
				add(i+1, element);
				return;
			}
		}
		throw new NoSuchElementException();	
	}

	@Override
	public void add(int index, T element) {
		if(index>rear) {
			throw new IndexOutOfBoundsException();
		}
		if(rear >= array.length){
			expandCapacity();
		}
		for(int i = rear; i > index; i--){
			array[i] = array[i-1];
		}
		array[index] = element;
		rear++;
		modCount++;		
	}

	@Override
	public T removeFirst() {
		if(isEmpty()){
			throw new NoSuchElementException();
		}
		T val = array[0];
		for(int i = 0; i < rear-1; i++){
			array[i] = array[i+1];
		}
		rear--;
		modCount++;
		return val;
	}

	@Override
	public T removeLast() {
		if(isEmpty()){
			throw new NoSuchElementException();
		}		
		T retVal = array[rear - 1];
		rear--;
		modCount++;
		return retVal;
	}

	@Override
	public T remove(T element) {
		int index = indexOf(element);
		if (index == NOT_FOUND) {
			throw new NoSuchElementException();
		}
		//shift elements
		for (int i = index; i < rear-1; i++) {
			array[i] = array[i+1];
		}
		rear--;
		modCount++;
		return element;
	}

	@Override
	public T remove(int index) {
		if (index<0 || index>=rear) {
			throw new IndexOutOfBoundsException();
		}
		if(isEmpty()){
			throw new NoSuchElementException();
		}
		T val = array[index];
		for(int i = index; i < rear-1; i++){
			array[i] = array[i+1];
		}
		rear--;
		modCount++;
		return val;
	}

	@Override
	public void set(int index, T element) {
		if(index < 0 || index >= rear){
			throw new IndexOutOfBoundsException();
		}
		array[index] = element;
		modCount++;		
	}

	@Override
	public T get(int index) {
		if(index < 0 || index >= rear){
			throw new IndexOutOfBoundsException();
		}
		T value = array[index];
		return value;
	}

	@Override
	public int indexOf(T element) {
		int index = NOT_FOUND;
		
		if (!isEmpty()) {
			int i = 0;
			while (index == NOT_FOUND && i < rear) {
				if (element.equals(array[i])) {
					index = i;
				} else {
					i++;
				}
			}
		}
		return index;
	}

	@Override
	public T first() {
		if(isEmpty()){
			throw new NoSuchElementException();
		}
		T value = array[0];
		return value;
	}

	@Override
	public T last() {
		if(isEmpty()){
			throw new NoSuchElementException();
		}
		T value = array[rear-1];
		return value;
	}

	@Override
	public boolean contains(T target) {
		return (indexOf(target) != NOT_FOUND);
	}

	@Override
	public boolean isEmpty() {
		return rear==0;
	}

	@Override
	public int size() {
		return rear;
	}
	
	public void sort() {
		// create a new, temp list of same size
		Integer[] sortArray;
		try {
			sortArray = (Integer[])array;
		}
		catch (Exception e) {
			System.out.println("The Sort method only work on Integers.");
			return;
		}
		
		int temp;

		for (int i=0;i<sortArray.length;i++) {
			for (int j=0;j<sortArray.length;i++) {
				if (sortArray[i]>sortArray[j]) {
					// switches values of i and j
					temp=sortArray[i];
					sortArray[i]=sortArray[j];
					sortArray[j]=temp;
				}
			}
		}
		array = (T[])sortArray;
	}
		
	
	public void reverseSort() {
		// create a new, temp list of same size
		Integer[] sortArray;
		try {
			sortArray = (Integer[])array;
		}
		catch (Exception e) {
			System.out.println("The Reverse Sort method only work on Integers.");
			return;
		}
		
		int temp;

		for (int i=0;i<sortArray.length;i++) {
			for (int j=0;j<sortArray.length;i++) {
				if (sortArray[i]<sortArray[j]) {
					// switches values of i and j
					temp=sortArray[i];
					sortArray[i]=sortArray[j];
					sortArray[j]=temp;
				}
			}
		}
		array = (T[])sortArray;
	}
	
	@Override
	public String toString() {
		String retVal = "[";
		for (int i = 0; i < rear - 1; i++) {
			retVal += "" + array[i] + ",";
		}
		if (rear != 0) {
			retVal += "" + array[rear-1];
		}
		retVal += "]";
		return retVal;
	}

	@Override
	public Iterator<T> iterator() {
		return new ALIterator();
	}

	@Override
	public ListIterator<T> listIterator() {
		throw new UnsupportedOperationException();
	}

	@Override
	public ListIterator<T> listIterator(int startingIndex) {
		throw new UnsupportedOperationException();
	}
	

	/** Iterator for IUArrayList */
	private class ALIterator implements Iterator<T> {
		private int nextIndex;
		private int iterModCount;
		
		private boolean next;
		private boolean remove;
		
		public ALIterator() {
			nextIndex = 0;
			iterModCount = modCount;
		}

		@Override
		public boolean hasNext() {
			boolean value = false;
			if(iterModCount != modCount){
				throw new ConcurrentModificationException();
			}
			if(nextIndex < rear){
				value = true;
			}
			return value;
		}

		@Override
		public T next() {
			T value;
			if(iterModCount != modCount){
				throw new ConcurrentModificationException();
			}
			if(nextIndex < rear){
				value = array[nextIndex];
				nextIndex++;
			}else{
				throw new NoSuchElementException();
			}
			next = true;
			remove = false;
			return value;
		}
		
		@Override
		public void remove() {
			if(iterModCount != modCount){
				throw new ConcurrentModificationException();
			}
			if(next = false || remove == true){
				throw new IllegalStateException();
			}
			if(nextIndex > rear || nextIndex <= 0){
				throw new IllegalStateException();
			}
			for(int i = nextIndex - 1; i < rear; i++){
				array[i] = array[i+1];
			}
			rear--;
			nextIndex--;
			modCount++;
			iterModCount++;
			next = false;
			remove = true;
		}
		
	}
}
