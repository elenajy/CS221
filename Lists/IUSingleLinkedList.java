import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * Single-linked node implementation of IndexedUnsortedList.
 * An Iterator with working remove() method is implemented, but
 * ListIterator is unsupported.
 * 
 * @author elena
 * 
 * @param <T> type to store
 */
public class IUSingleLinkedList<T> implements IndexedUnsortedList<T> {
	private Node<T> head, tail;
	private int size;
	private int modCount;
	
	/** Creates an empty list */
	public IUSingleLinkedList() {
		head = tail = null;
		size = 0;
		modCount = 0;
	}

	@Override
	public void addToFront(T element) {
		Node<T> current = null;
		if (isEmpty()) {
			head = new Node<T>(element);
			tail = head;
		} else if (size==1) {
			head = new Node<T>(element);
			head.setNext(tail);
		} else {
			current = head;
			head = new Node<T>(element);
			head.setNext(current);
		}
		size++;
		modCount++;
	}

	@Override
	public void addToRear(T element) {
		Node<T> newNode = new Node<T>(element);
		if (isEmpty()) {
			head = newNode;
			tail = head;
		} else if (size==1) {
			tail = newNode;
			head.setNext(tail);
		} else {
			tail.setNext(newNode);
			tail = newNode;
		}
		size++;
		modCount++;	
	}

	@Override
	public void add(T element) {
		Node<T> current = null;
		if (isEmpty()) {
			head = new Node<T>(element);
			tail = head;
		} else if (size == 1) {
			tail = new Node<T>(element);
			head.setNext(tail);
		} else {
			current = tail;
			tail = new Node<T>(element);
			current.setNext(tail);
		}
		size++;
		modCount++;	
	}

	@Override
	// Good - wrote in class
	public void addAfter(T element, T target) {
		// locate target Node
		Node<T>targetNode = head;
		boolean found = false;
		while(targetNode != null && !found) {
			if (targetNode.getElement().equals(target)) {
				found = true;
			}
			else {
				targetNode = targetNode.getNext();
			}
		}
		// throw NoSuchElementException if not found
		if (!found) {
			throw new NoSuchElementException();
		}
		// create new Node with element
		Node<T> newNode = new Node(element);
		// set new Node's next to target Node's next
		newNode.setNext(targetNode.getNext());
		// set target Node's next to new node
		targetNode.setNext(newNode);
		// update tail if necessary
		if (newNode.getNext()==null) {
			tail = newNode;
		}
		// increment size
		size++;
		modCount++;
	}

	@Override
	public void add(int index, T element) {
		Node<T> current = head;
		Node<T> newNode = new Node<T>(element);
		if (index<0 || index>size) {
			throw new IndexOutOfBoundsException();
		}
		if (index == 0) {
			if (size == 0) {
				head = newNode;
				tail = newNode;
			} else {
				newNode.setNext(head);
				head = newNode;
			}
		} 
		else {
			for (int i=0; i<index-1; i++) {
				current = current.getNext();
			}
			if (current.equals(tail)) {
				current.setNext(newNode);
				tail = newNode;
			} else {
				newNode.setNext(current.getNext());
				current.setNext(newNode);
			}
		}
		size++;
		modCount++;		
	}

	@Override
	public T removeFirst() {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		T retVal = head.getElement();
		head = head.getNext();
		size--;
		modCount++;
		return retVal;
	}

	@Override
	public T removeLast() {
		Node<T> current = head;
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		T retVal = tail.getElement();
		if (size == 1) {
			head = null;
			tail = null;
		} 
		else {
			while (current.getNext() != tail) {
				current = current.getNext();
			}
			current.setNext(null);
			tail = current;
		}
		size--;
		modCount++;
		return retVal;
	}

	@Override
	// Good - wrote in class
	public T remove(T element) {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		
		boolean found = false;
		Node<T> previous = null;
		Node<T> current = head;
		
		while (current != null && !found) {
			if (element.equals(current.getElement())) {
				found = true;
			} else {
				previous = current;
				current = current.getNext();
			}
		}
		
		if (!found) {
			throw new NoSuchElementException();
		}
		
		if (size() == 1) { //only node
			head = tail = null;
		} else if (current == head) { //first node
			head = current.getNext();
		} else if (current == tail) { //last node
			tail = previous;
			tail.setNext(null);
		} else { //somewhere in the middle
			previous.setNext(current.getNext());
		}
		
		size--;
		modCount++;
		
		return current.getElement();
	}

	@Override
	public T remove(int index) {
		Node<T> previous = null;
		Node<T> current = head;
		T retVal = null;
		if (index<0||index>=size) {
			throw new IndexOutOfBoundsException();
		}
		if (size == 0) {
			throw new IndexOutOfBoundsException();
		}
		if (index==0) {
			retVal = head.getElement();
			head = current.getNext();
		} 
		else {
			for (int i = 0; i < index - 1; i++) {
				current = current.getNext();
			}
			if (current.getNext() == tail) {
				previous = current;
				retVal = tail.getElement();
				previous.setNext(null);
				tail = previous;
			} else {
				previous = current;
				current = current.getNext();
				retVal = current.getElement();
				previous.setNext(current.getNext());
			}
		}
		size--;
		modCount++;
		return retVal;
	}

	@Override
	public void set(int index, T element) {
		Node<T> current = head;
		if (index<0 || index>=size()) {
			throw new IndexOutOfBoundsException();
		}
		if (index == 0) {
			head.setElement(element);
		} 
		else {
			for (int i=0;i<index;i++) {
				current = current.getNext();
			}
			current.setElement(element);
		}
		modCount++;
	}

	@Override
	public T get(int index) {
		Node<T> current = head;
		T retVal = null;
		if (index<0 || index>=size()) {
			throw new IndexOutOfBoundsException();
		}
		if (index == 0) {
			retVal = head.getElement();
		} 
		else {
			for (int i=0;i<index;i++) {
				current = current.getNext();
			}
			retVal = current.getElement();
		}
		
		return current.getElement();		
	}

	@Override
	// Good - wrote in class
	public int indexOf(T element) {
		int index = -1;
		Node<T> current = head;
		int currentIndex = 0;
		while (current != null && index < 0) {
			if (current.getElement().equals(element)) {
				index = currentIndex;
			}
			else {
				current = current.getNext();
				currentIndex++;
			}
		}
		return index;
	}

	@Override
	public T first() {
		T retVal = null;
		if (isEmpty()) {
			throw new NoSuchElementException();
		} else {
			retVal = head.getElement();
		}
		return retVal;
	}

	@Override
	public T last() {
		T retVal = null;
		if (isEmpty()) {
			throw new NoSuchElementException();
		} else {
			retVal = tail.getElement();
		}
		return retVal;
	}

	@Override
	public boolean contains(T target) {
		return indexOf(target) != -1;
	}

	@Override
	// Good - wrote in class
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public int size() {
		return size;
	}
	
	@Override
	public String toString() {
		if (size == 0) {
			return "[]";
		}
		String retVal = "[";
		Node<T> current = head;
		while (current.getNext() != null) {
			retVal += "" + current.getElement() + ",";
			current = current.getNext();
		}
		retVal += current.getElement();
		retVal += "]";
		return retVal;
	}

	@Override
	public Iterator<T> iterator() {
		return new SLLIterator();
	}

	@Override
	public ListIterator<T> listIterator() {
		throw new UnsupportedOperationException();
	}

	@Override
	public ListIterator<T> listIterator(int startingIndex) {
		throw new UnsupportedOperationException();
	}

	/** Iterator for IUSingleLinkedList */
	private class SLLIterator implements Iterator<T> {
		private Node<T> nextNode;
		private int iterModCount;
		private boolean remove;
		
		/** Creates a new iterator for the list */
		public SLLIterator() {
			nextNode = head;
			iterModCount = modCount;
		}

		@Override
		public boolean hasNext() {
			boolean retVal = true;
			if (iterModCount != modCount) {
				throw new ConcurrentModificationException();
			}
			if (nextNode != null) {
				retVal = true;
			}
			else {
				retVal = false;
			}
			return retVal;
		}

		@Override
		public T next() {
			if (iterModCount != modCount) {
				throw new ConcurrentModificationException();
			}
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			T retVal = nextNode.getElement();
			nextNode = nextNode.getNext();
			remove = true;
			return retVal;
		}
		
		@Override
		public void remove() {
			if (iterModCount != modCount) {
				throw new ConcurrentModificationException();
			}
			if (!remove) {
				throw new IllegalStateException();
			}
			if (head == tail) {
				head = tail = null;
			} 
			else if (head.getNext()==nextNode) {
				head = head.getNext();
			} 
			else {
				Node<T> current = head;
				while (current.getNext().getNext() != nextNode) {
					current = current.getNext();
				}
				current.setNext(nextNode);
				if (nextNode == null) {
					tail = current;
				}
			}
			remove = false;
			size--;
			modCount++;
			iterModCount++;
		}
	}
}
