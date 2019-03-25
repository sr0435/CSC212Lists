package edu.smith.cs.csc212.adtr.real;

import edu.smith.cs.csc212.adtr.ListADT;
import edu.smith.cs.csc212.adtr.errors.BadIndexError;
import edu.smith.cs.csc212.adtr.errors.TODOErr;

public class SinglyLinkedList<T> extends ListADT<T> {
	/**
	 * The start of this list.
	 * Node is defined at the bottom of this file.
	 */
	Node<T> start;
	
	@Override
	public T removeFront() {
		checkNotEmpty();
		T front = this.start.value;
		this.start = this.start.next;
		return front;
	}
	@Override
	public T removeBack() {
		checkNotEmpty();
		for (Node<T> n = this.start; n != null; n = n.next) {
			if (n.next == null) {
				T back = n.value;
				this.start = null;
				return back;
			}
			if (n.next.next == null) {
				T back = n.next.value;
				n.next = null;
				return back;
			}
			else {
				continue;
			}

			}
		return null;
	}
	// 
	@Override
	public T removeIndex(int index) {
		checkNotEmpty();
		int step = 0;
		for (Node<T> n = this.start; n != null; n = n.next) {
			if (index == 0) {
				T removed = this.start.value;
				this.start = this.start.next;
				return removed;
			}
			if (step < index-1) {
				step++;
				continue;
			}
			if (step == index -1) {
				if (n.next != null) {
				T removed = n.next.value;
				n.next = n.next.next;
				step++;
				return removed;
				}
				else {
					T removed = n.next.value;
					return removed;
				}
			}
		}
		return null;
	}

	@Override
	public void addFront(T item) {
		this.start = new Node<T>(item, start);
	}
	// 
	@Override
	public void addBack(T item) {
		if (this.isEmpty()) {
			this.addFront(item);
			return;
		}
		for (Node<T> n = this.start; n != null; n = n.next) {
			if (n.next == null) {
				n.next = new Node<T>(item,null);
				return;
			}
		}
	}
	// 
	@Override
	public void addIndex(int index, T item) {
		if (index == 0 || this.isEmpty()) {
			addFront(item);
		}
		else {
			checkInclusiveIndex(index);
			int step = 0;
			for (Node<T> n = this.start; n != null; n = n.next) {
				if (n.next == null) {
					addBack(item);
					System.out.println("eee");
					break;
				}
				if (step == index-1) {
					n.next = new Node<T>(item,n.next);
					break;
				}
				step++;
			}
		}
	}
	
	
	@Override
	public T getFront() {
		checkNotEmpty();
		T front = this.start.value;
		return front;
	}
	@Override
	public T getBack() {
		checkNotEmpty();
		for (Node<T> n = this.start; n != null; n = n.next) {
			if (n.next == null) {
				T back = n.value;
				return back;
			}
		}
		return null;
	}

	@Override
	public T getIndex(int index) {
		checkNotEmpty();
		int at = 0;
		for (Node<T> n = this.start; n != null; n = n.next) {
			if (at++ == index) {
				return n.value;
			}
		}
		throw new BadIndexError(index);
	}
	
	@Override
	public void setIndex(int index, T value) {
		checkNotEmpty();
		checkExclusiveIndex(index);
		int step = 0;
		for (Node<T> n = this.start; n != null; n = n.next) {
			if (step == index) {
				n.value = value;
			}
			step++;
		}
	}

	@Override
	public int size() {
		int count = 0;
		for (Node<T> n = this.start; n != null; n = n.next) {
			count++;
		}
		return count;
	}

	@Override
	public boolean isEmpty() {
		return this.start == null;
	}
	
	/**
	 * The node on any linked list should not be exposed.
	 * Static means we don't need a "this" of SinglyLinkedList to make a node.
	 * @param <T> the type of the values stored.
	 */
	private static class Node<T> {
		/**
		 * What node comes after me?
		 */
		public Node<T> next;
		/**
		 * What value is stored in this node?
		 */
		public T value;
		/**
		 * Create a node with no friends.
		 * @param value - the value to put in it.
		 */
		public Node(T value, Node<T> next) {
			this.value = value;
			this.next = next;
		}
	}

}
