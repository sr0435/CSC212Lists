package edu.smith.cs.csc212.adtr.real;

import edu.smith.cs.csc212.adtr.ListADT;
import edu.smith.cs.csc212.adtr.errors.BadIndexError;
import edu.smith.cs.csc212.adtr.errors.TODOErr;

public class GrowableList<T> extends ListADT<T> {
	public static final int START_SIZE = 10;
	private Object[] array;
	private int fill;
	
	public GrowableList() {
		this.array = new Object[START_SIZE];
		this.fill = 0;
	}

	@Override
	public T removeFront() {
		this.checkNotEmpty();
		return removeIndex(0);
	}

	@Override
	public T removeBack() {
		this.checkNotEmpty();
		return removeIndex(fill-1);
	}

	@Override
	public T removeIndex(int index) {
		checkNotEmpty();
		checkExclusiveIndex(index);
		
		T removed = this.getIndex(index);
		
		// : Now we need to slide everything to the left. done!
		for (int j=index+1; j<fill; j++) {
			array[j-1] = array[j];
			}
		fill -= 1;
		// When we're done, give back the thing that we removed.
		return removed;
	}

	@Override
	public void addFront(T item) {
		addIndex(0, item);
	}

	@Override
	public void addBack(T item) {
		if (fill >= array.length) {
			this.resizeArray();
		}
		array[fill++] = item;
	}
	
	/**
	 * This private method is called when we need to make room in our GrowableList.
	 */
	
	private void resizeArray() {
		//saves the items in the old array
		Object[] oldArray = array.clone();
		// creates a new array that's twice the size
		array = new Object[fill*2];
		int i = 0;
		for (Object item : oldArray) {
			array[i] = item;
			i++;
		}


	}

	@Override
	public void addIndex(int index, T item) {
		this.checkInclusiveIndex(index);
		if (fill >= array.length) {
			resizeArray();
		}
		// loop backwards, shifting items to the right.
		for (int j=fill; j>index; j--) {
			array[j] = array[j-1];
		}
		// put this item in the middle
		array[index] = item;
		fill++;
	}
	
	@Override
	public T getFront() {
		checkNotEmpty();
		return this.getIndex(0);
	}

	@Override
	public T getBack() {
		checkNotEmpty();
		return this.getIndex(this.fill-1);
	}

	/**
	 * Do not allow unchecked warnings in any other method.
	 * Keep the "guessing" the objects are actually a T here.
	 * Do that by calling this method instead of using the array directly.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public T getIndex(int index) {
		checkNotEmpty();
		checkExclusiveIndex(index);
		return (T) this.array[index];
	}

	@Override
	public int size() {
		return this.fill;
	}

	@Override
	public boolean isEmpty() {
		return this.fill == 0;
	}

	@Override
	public void setIndex(int index, T value) {
		checkInclusiveIndex(index);
		// for set index high test
		if (index == fill) {
			throw new BadIndexError(index);
		}
		array[index] = value;
	}

}
