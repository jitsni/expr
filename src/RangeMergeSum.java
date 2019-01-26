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

input = {{ 3, 7 }, { 4, 5 }, {10, 20}, {8, 15}}
return 16
*/

public class RangeMergeSum {

    public static void main(String ... args) {
        List<Range> list = List.of(new Range(1, 4), new Range(6, 8), new Range(2, 4), new Range(7, 9), new Range(10, 15));
        System.out.printf("ranges = %s sum = %d sum1 = %d\n\n", list, sum(list), sum1(list));

        list = List.of(new Range(1, 4), new Range(2, 3));
        System.out.printf("ranges = %s sum = %d sum1 = %d\n\n", list, sum(list), sum1(list));

        list = List.of(new Range(4, 6), new Range(1, 2));
        System.out.printf("ranges = %s sum = %d sum1 = %d\n\n", list, sum(list), sum1(list));

        list = List.of(new Range(3, 7), new Range(4, 5), new Range(10, 20), new Range(8,15));
        System.out.printf("ranges = %s sum = %d sum1 = %d\n\n", list, sum(list), sum1(list));
    }

    // approach 1: insert all ranges into sorted set, then merge
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

    // approach 2: merge the ranges while inserting a range into a sorted set
    static int sum1(List<Range> list) {
        TreeSet<Range> ranges = new TreeSet<>();
        for(Range range : list) {
            // merge with the first lower range (if intersects)
            Range lower = ranges.lower(range);
            if (lower != null && lower.end >= range.begin) {
                ranges.remove(lower);
                lower = new Range(lower.begin, Math.max(lower.end, range.end));
            } else {
                lower = range;
            }

            // merge with the higher intersecting ranges
            while(true) {
                Range higher = ranges.higher(lower);
                if (higher != null && lower.end >= higher.begin) {
                    ranges.remove(higher);
                    lower = new Range(lower.begin, Math.max(lower.end, higher.end));
                } else {
                    break;
                }
            }
            ranges.add(lower);
        }

        int sum = 0;
        for(Range range : ranges) {
            sum += range.end - range.begin;
        }
        return sum;
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
