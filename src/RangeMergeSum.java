import java.util.*;

/*
Given a list of arrays of time intervals, write a function that calculates the total amount of time covered by the intervals. 

For example: 
input = [(1,4), (2,3)] 
return 3 

input = [(4,6), (1,2)] 
return 3 

input = {{1,4}, {6,8}, {2,4}, {7,9}, {10, 15}} 
return 11
*/

public class RangeMergeSum {

	public static void main(String ... args) {
		List<Range> list = List.of(new Range(1, 4), new Range(6, 8), new Range(2, 4), new Range(7, 9), new Range(10, 15));
		System.out.printf("ranges = %s sum = %d\n", list, sum(list));

		list = List.of(new Range(1, 4), new Range(2, 3));
		System.out.printf("ranges = %s sum = %d\n", list, sum(list));

		list = List.of(new Range(4, 6), new Range(1, 2));
		System.out.printf("ranges = %s sum = %d\n", list, sum(list));
	}

	static int sum(List<Range> list) {
		Set<Range> ranges = new TreeSet<>(list);
		List<Range> merged = new ArrayList<>();
		for(Range range : ranges) {
			if (merged.isEmpty()) {
				merged.add(range);
			} else {
				merge(merged, range);
			}
		}
		return merged.stream().mapToInt(r -> r.end - r.begin).sum();
	}

	private static void merge(List<Range> list, Range range) {
		Range last = list.remove(list.size() - 1);

		boolean intersects = last.end >= range.begin; 
		if (intersects) {
			Range merged = new Range(last.begin, Math.max(last.end, range.end));
			System.out.printf("%s %s intersect, merged = %s\n", last, range, merged);
			list.add(merged);
		} else {
			list.add(last);
			list.add(range);
		}
	}

	static class Range implements Comparable<Range> {
		final int begin;
		final int end;

		Range(int begin, int end) {
			this.begin = begin;
			this.end = end;
		}

		@Override
		public int compareTo(Range other) {
			int first = Integer.compare(begin, other.begin);
			int second = Integer.compare(end, other.end);
			return (first == 0) ? second : first;
		}

		@Override
		public boolean equals(Object range) {
			if (range instanceof Range) {
				Range other = (Range) range;
				return begin == other.begin && end == other.end;
			}
			return false;
		}

		@Override
		public int hashCode() {
			return 31 * begin + end;
		}

		public String toString() {
			return String.format("(%d, %d)", begin, end);
		}
		
	}
}
