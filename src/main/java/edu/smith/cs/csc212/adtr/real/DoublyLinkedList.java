package edu.smith.cs.csc212.adtr.real;

import edu.smith.cs.csc212.adtr.ListADT;
import edu.smith.cs.csc212.adtr.errors.BadIndexError;
import edu.smith.cs.csc212.adtr.errors.TODOErr;


public class DoublyLinkedList<T> extends ListADT<T> {
	private Node<T> start;
	private Node<T> end;
	
	/**
	 * A doubly-linked list starts empty.
	 */
	public DoublyLinkedList() {
		this.start = null;
		this.end = null;
	}
	

	@Override
	//
	public T removeFront() {
		checkNotEmpty();
		T front = this.start.value;
		this.start = this.start.after;
		return front;
	}
	//
	@Override
	public T removeBack() {
		checkNotEmpty();
		if (start == end) {
			T back = end.value;
			start = start.after;
			return back;
		}
		else {
			T back = this.end.value;
			end = end.before;
			this.end.after = null;
			return back;
		}
	}
	//
	@Override
	public T removeIndex(int index) {
		checkNotEmpty();
		int step = 0;
		if (index == 0) {
			T removed = removeFront();
			return removed;
		}
		else {
			for (Node<T> n = start; n != null; n = n.after) {
				if (step == index-1) {
					T removed = n.after.value;
					n.after = n.after.after;
					return removed;
				}
				step++;
			}
			return null;
		}
	}
	//
	@Override
	public void addFront(T item) {
		if (start == null) {
			start = end = new Node<T>(item);
		}
		else {
			Node<T> newNode = start;
			start = new Node<T>(item);
			start.after = newNode;
			newNode.before = start;
		}
	}
	@Override
	public void addBack(T item) {
		if (this.isEmpty()) {
			start = end = new Node<T>(item);
		} else {
			Node<T> secondLast = end;
			end = new Node<T>(item);
			end.before = secondLast;
			secondLast.after = end;
		}
	}
	//
	@Override
	public void addIndex(int index, T item) {
		checkInclusiveIndex(index);
		if (index == 0 || this.isEmpty()) {
			addFront(item);
		}
		else {
			int step = 0;
			for (Node<T> n = start; n != null; n = n.after) {
				if (n.after == null) {
					addBack(item);
					break;
				}
				else if (step == index-1) {
					Node<T> newNode = new Node<T>(item);
					newNode.before = n;
					newNode.after = n.after;
					n.after = newNode;
					break;
				}
				step++;
			}	 
		}	
	}
	//
	@Override
	public T getFront() {
		checkNotEmpty();
		return start.value;	
	}
	//
	@Override
	public T getBack() {
		checkNotEmpty();
		return end.value;
	}
	//
	@Override
	public T getIndex(int index) {
		checkNotEmpty();
		if (index < 0) {
			throw new BadIndexError(index);
		}
		if (index >= size()) {
			throw new BadIndexError(index);
		}
		int step = 0;
		for (Node<T> n = this.start; n != null; n = n.after) {
			if (index == step) {
				T atIndex = n.value;
				return atIndex;
			}
			step++;
		}
		return null;
	}
	//
	public void setIndex(int index, T value) {
		checkNotEmpty();
		checkExclusiveIndex(index);
		int step =0;
		for (Node<T> n = this.start; n != null; n = n.after) {
			if (step == index) {
				n.value = value;
			}
			step++;
		}
	}
	//
	@Override
	public int size() {
		int count = 0;
		for (Node<T> n = this.start; n != null; n = n.after) {
			count++;
		}
		return count;
	}
	//
	@Override
	public boolean isEmpty() {
		return this.start == null;
	}
	
	/**
	 * The node on any linked list should not be exposed.
	 * Static means we don't need a "this" of DoublyLinkedList to make a node.
	 * @param <T> the type of the values stored.
	 */
	private static class Node<T> {
		/**
		 * What node comes before me?
		 */
		public Node<T> before;
		/**
		 * What node comes after me?
		 */
		public Node<T> after;
		/**
		 * What value is stored in this node?
		 */
		public T value;
		/**
		 * Create a node with no friends.
		 * @param value - the value to put in it.
		 */
		public Node(T value) {
			this.value = value;
			this.before = null;
			this.after = null;
		}
	}
}
