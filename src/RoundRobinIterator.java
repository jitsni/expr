import java.util.*;

public class RoundRobinIterator implements Iterator<String> {
	private final List<Iterator<String>> iterators;
	private int current = -1;
	private int size;

	public static void main(String ... args) {
		List<Iterator<String>> iterators = new ArrayList<>();
		RoundRobinIterator rit = new RoundRobinIterator(iterators);
		assert !rit.hasNext();

		iterators = new ArrayList<>();
		iterators.add(List.of("one").iterator());
		rit = new RoundRobinIterator(iterators);
		assert rit.hasNext();
		assert rit.hasNext();
		assert rit.hasNext();
		assert "one".equals(rit.next());

		iterators = new ArrayList<>();
		iterators.add(List.of("one", "four").iterator());
		iterators.add(List.of("two").iterator());
		iterators.add(List.of("three", "five").iterator());
		rit = new RoundRobinIterator(iterators);
		assert "one".equals(rit.next());
		assert "two".equals(rit.next());
		assert "three".equals(rit.next());
		assert "four".equals(rit.next());
		assert "five".equals(rit.next());
		assert !rit.hasNext();

		iterators = new ArrayList<>();
		iterators.add(List.of("one", "four").iterator());
		iterators.add(List.of("two", "five", "seven").iterator());
		iterators.add(List.of("three", "six").iterator());
		rit = new RoundRobinIterator(iterators);
		assert "one".equals(rit.next());
		assert "two".equals(rit.next());
		assert "three".equals(rit.next());
		assert "four".equals(rit.next());
		assert "five".equals(rit.next());
		assert "six".equals(rit.next());
		assert "seven".equals(rit.next());
		assert !rit.hasNext();
	}

	private RoundRobinIterator(List<Iterator<String>> iterators) {
		this.iterators = iterators;
		this.size = iterators.size();
		advance();
	}

	@Override
	public boolean hasNext() {
		return current != -1;
	}

	@Override
	public String next() {
		if (!hasNext()) {
			throw new NoSuchElementException();
		}
		String str = iterators.get(current).next();
		advance();
		return str;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

/*
	private void advance() {
		for(int i=0; i < size; i++) {
			current = (current + 1) % size;
			if (iterators.get(current).hasNext()) {
				return;
			}
		}
		current = -1;
	}
*/

	// moves empty iterator to last, changes size
	private void advance() {
		while(size > 0) {
			current = (current + 1) % size;
			if (iterators.get(current).hasNext()) {
				return;
			} else {
				swap(current, size - 1);	
				size--;
				current--;
			}
		}
		current = -1;
	}

	private void swap(int i, int j) {
		Iterator<String> tmp = iterators.get(i);
		iterators.set(i, iterators.get(j));
		iterators.set(j, tmp);
	}

}
