public class DNode<T> 
{
	private DNode<T> next;
	private DNode<T> previous;
	private T element;
	
	/**
	 * @param element;
	 */
	public DNode(T element)
	{
		setElement(element);
		setNext(null);
		setPrevious(null);
	}

	/**
	 * @return reference to the next code
	 */
	public DNode<T> getNext()
	{
		return next;
	}
	
	/**
	 * @param next - reference to the next node
	 */
	public void setNext(DNode<T> next) 
	{
		this.next = next;		
	}
	
	/**
	 * @return reference to the previous code
	 */
	public DNode<T> getPrevious()
	{
		return previous;
		
	}
	/**
	 * @param previous - reference to the previous node
	 */
	public void setPrevious(DNode<T> previous) {
		this.previous = previous;
	}

	/**
	 * @return reference to element stored in code
	 */
	public T getElement()
	{
		return element;
	}
	
	/**
	 * @param element - reference to node stored in node
	 */
	public void setElement(T element) 
	{
		this.element = element;
	}

}