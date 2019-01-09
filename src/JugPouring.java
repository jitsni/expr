import java.util.*;

public class JugPouring {

    public static void main(String... args) {
        List<Node> path = search(0, 5, 0, 3, 4);
        System.out.println(path);
    }

    static List<Node> search(int jug1, int jug1Capacity, int jug2, int jug2Capacity, int target) {
        Node start = new Node(jug1, jug2);

        Map<Node, Node> path = new HashMap<>();
        Queue<Node> queue = new ArrayDeque<>();
        Set<Node> visited = new HashSet<>();
        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            Node u = queue.poll();
            List<Node> edges = edges(u, jug1Capacity, jug2Capacity);

            if (u.jug1 == target || u.jug2 == target) {
				// construct the pouring moves
                List<Node> shortest = new ArrayList<>();
                while (u != null) {
                    shortest.add(u);
                    u = path.get(u);
                }
                Collections.reverse(shortest);
                return shortest;
            }

			// BFS 
            for (Node v : edges) {
                if (!visited.contains(v)) {
                    path.put(v, u);
                    queue.add(v);
                    visited.add(v);
                }
            }
        }
        return null;
    }

    /*
    static void build(Node node, int jug1Capacity, int jug2Capacity) {
        Queue<Node> queue = new ArrayDeque<>();
        Set<Node> visited = new HashSet<>();
        queue.add(node);
        visited.add(node);

        while (!queue.isEmpty()) {
            Node u = queue.poll();
            List<Node> edges = edges(u, jug1Capacity, jug2Capacity);
            for (Node v : edges) {
                u.add(v);
                if (!visited.contains(v)) {
                    queue.add(v);
                    visited.add(v);
                }
            }
        }
    }*/

    static List<Node> edges(Node u, int jug1Capacity, int jug2Capacity) {
        List<Node> edges = new ArrayList<>();

		// empty jug1
        if (u.jug1 != 0) {
            edges.add(new Node(0, u.jug2));
        }

		// empty jug2
        if (u.jug2 != 0) {
            edges.add(new Node(u.jug1, 0));
        }

		// full jug1
        if (u.jug1 != jug1Capacity) {
            edges.add(new Node(jug1Capacity, u.jug2));
        }

		// full jug2
        if (u.jug2 != jug2Capacity) {
            edges.add(new Node(u.jug1, jug2Capacity));
        }

		// pour from jug1 to jug2
        if (u.jug1 > 0) {
            int remaining = jug2Capacity - u.jug2;
            int fill = Math.min(u.jug1, remaining);
            if (fill > 0) {
                edges.add(new Node(u.jug1 - fill, u.jug2 + fill));
            }
        }

		// pour from jug2 to jug1
        if (u.jug2 > 0) {
            int remaining = jug1Capacity - u.jug1;
            int fill = Math.min(u.jug2, remaining);
            if (fill > 0) {
                edges.add(new Node(u.jug1 + fill, u.jug2 - fill));
            }
        }

        return edges;
    }

    static class Node {
        final int jug1;
        final int jug2;

        final List<Node> edges;

        Node(int jug1, int jug2) {
            this.jug1 = jug1;
            this.jug2 = jug2;

            this.edges = new ArrayList<>();
        }

        void add(Node child) {
            edges.add(child);
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof Node) {
                Node other = (Node) o;
                return jug1 == other.jug1 && jug2 == other.jug2;
            }
            return false;
        }

        @Override
        public int hashCode() {
            return 31 * jug1 + jug2;
        }

        @Override
        public String toString() {
            return String.format("%d|%d", jug1, jug2);
        }
    }
}
