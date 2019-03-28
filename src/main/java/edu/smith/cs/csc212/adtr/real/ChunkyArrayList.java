package edu.smith.cs.csc212.adtr.real;

import javax.xml.stream.events.StartDocument;

import edu.smith.cs.csc212.adtr.ListADT;
import edu.smith.cs.csc212.adtr.errors.BadIndexError;
import edu.smith.cs.csc212.adtr.errors.EmptyListError;
import edu.smith.cs.csc212.adtr.errors.TODOErr;

/**
 * This is a data structure that has an array inside each node of an ArrayList.
 * Therefore, we only make new nodes when they are full. Some remove operations
 * may be easier if you allow "chunks" to be partially filled.
 * 
 * @author jfoley
 * @param <T> - the type of item stored in the list.
 */
public class ChunkyArrayList<T> extends ListADT<T> {
	private int chunkSize;
	private GrowableList<FixedSizeList<T>> chunks;

	public ChunkyArrayList(int chunkSize) {
		this.chunkSize = chunkSize;
		chunks = new GrowableList<>();
	}

	private FixedSizeList<T> makeChunk() {
		return new FixedSizeList<>(chunkSize);
	}
	// TODO done, but is it O(n^2) or O(2n)?
	@Override
	public T removeFront() { 
		T removed = chunks.getFront().getFront();
		chunks.getFront().removeFront();
		if (chunks.getFront().isEmpty()) {
			chunks.removeFront();
		}
		return removed;
	}
	// 
	@Override
	public T removeBack() {
		T removed = chunks.getBack().getBack();
		chunks.getBack().removeBack();
		if (chunks.getBack().isEmpty()) {
			chunks.removeBack();
		}
		return removed;
	}
	// TODO
	@Override
	public T removeIndex(int index) {
		checkNotEmpty();
		if (index == 0) {
			T removed = removeFront();
			return removed;
		}
		else {
			int start = 0;
			int chunkIndex = 0;
			for (FixedSizeList<T> chunk : this.chunks) {
				int end = start + chunk.size();
				if (start <= index && index < end) {
					T removed = chunk.getIndex(index-start);
					chunk.removeIndex(index-start);

					if (chunk.isEmpty()) {
						chunks.removeIndex(chunkIndex);
					}
					return removed;
				}
				start = end;
				chunkIndex++;
			}
		}
		throw new BadIndexError(index);
	}
	// 
	@Override
	public void addFront(T item) {
		if (chunks.isEmpty()) {
			FixedSizeList<T> front = makeChunk();
			chunks.addBack(front);
		}
		FixedSizeList<T> front = chunks.getFront();
		if (front.isFull()) {
			front = makeChunk();
			chunks.addFront(front);
		}
		front.addFront(item);
	}
	// 
	@Override
	public void addBack(T item) {
		if (chunks.isEmpty()) {
			FixedSizeList<T> back = makeChunk();
			chunks.addBack(back);
		}
		FixedSizeList<T> back = chunks.getBack();
		if (back.isFull()) {
			back = makeChunk();
			chunks.addBack(back);
		}
		back.addBack(item);
	}
	// Phuong helped me with this.
	@Override
	public void addIndex(int index, T item) {
		// THIS IS THE HARDEST METHOD IN CHUNKY-ARRAY-LIST.
				// DO IT LAST.
				//System.out.println(index);
				int chunkIndex = 0;
				int start = 0;
				if (index == 0) {
					addFront(item);
					return;
				}
				for (FixedSizeList<T> chunk : this.chunks) {
					// calculate bounds of this chunk.
					int end = start + chunk.size();
					// Check whether the index should be in this chunk:
					if (start <= index && index <= (index-start == chunkSize ? end-1:end)) {
						if (chunk.isFull()) {
							// if the chunk is full add a new one
							FixedSizeList<T> back = makeChunk();
							
							T removed = chunk.removeBack();
							chunk.addIndex(index-start, item);
							back.addBack(removed);
							
							chunks.addIndex(chunkIndex+1,back);
						} else {
							chunk.addIndex(index-start, item);
						}
						// upon adding, return.
						return;
					}
					
					// update bounds of next chunk.
					start = end;
					chunkIndex++;
				}
				throw new BadIndexError(index);
	}
@Override
public T getFront() {
	return this.chunks.getFront().getFront();
}

@Override
public T getBack() {
	return this.chunks.getBack().getBack();
}


@Override
public T getIndex(int index) {
	if (this.isEmpty()) {
		throw new EmptyListError();
	}
	int start = 0;
	for (FixedSizeList<T> chunk : this.chunks) {
		// calculate bounds of this chunk.
		int end = start + chunk.size();

		// Check whether the index should be in this chunk:
		if (start <= index && index < end) {
			return chunk.getIndex(index - start);
		}

		// update bounds of next chunk.
		start = end;
	}
	throw new BadIndexError(index);
}
// 
@Override
public void setIndex(int index, T value) {
	checkNotEmpty();
	int start = 0;
	for (FixedSizeList<T> chunk : this.chunks) {
		int end = start + chunk.size();
		if (start <= index && index < end) {
			chunk.setIndex(index-start, value);
			return;
		}
		start = end;
	}
	throw new BadIndexError(index);
}

@Override
public int size() {
	int total = 0;
	for (FixedSizeList<T> chunk : this.chunks) {
		total += chunk.size();
	}
	return total;
}

@Override
public boolean isEmpty() {
	return this.chunks.isEmpty();
}
}