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
	// TODO
	@Override
	public T removeIndex(int index) {
		int step = 0;
		T removed;
		for (Node<T> n = this.start; n != null; n = n.next) {
			System.out.println("step " + step);
			System.out.println("index " + index);
			System.out.println("n: " + n.value);
			System.out.println("n next " + n.next.value);
			if (step == index-1) {
				System.out.println("next next " + n.next.next.value);
				removed = n.next.value;
				System.out.println("removed " + removed);
				return removed;
			}
			else {
				System.out.println("doing else");
				if (step == index) {
					n.value = n.next.value;
					System.out.println(n.value);
				}
				removed = n.value;
				step++;
				return removed;
			}
		}
		return null;
	}

	@Override
	public void addFront(T item) {
		this.start = new Node<T>(item, start);
	}
	// TODO
	@Override
	public void addBack(T item) {
		
		throw new TODOErr();
	}
	// TODO
	@Override
	public void addIndex(int index, T item) {
		throw new TODOErr();
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
	
	// TODO
	@Override
	public void setIndex(int index, T value) {
		checkNotEmpty();
		throw new TODOErr();
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
