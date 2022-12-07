import java.util.ArrayList;

import org.jgraph.graph.DefaultEdge;
import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultDirectedGraph;

public class _tokens2Graph {
	public static DirectedGraph<String, DefaultEdge> get_using_adjTerms(ArrayList<String> terms) {
		DirectedGraph<String, DefaultEdge> graph = new DefaultDirectedGraph<>(DefaultEdge.class);
		for (String term : terms)
			graph.addVertex(term);
		for (int i = 1; i < terms.size() - 1; i++) {
			graph.addEdge(terms.get(i), terms.get(i + 1));
			graph.addEdge(terms.get(i), terms.get(i - 1));
		}
		if (terms.size() > 1) {
			graph.addEdge(terms.get(0), terms.get(1));
			graph.addEdge(terms.get(terms.size() - 1), terms.get(terms.size() - 2));
		}
		return graph;
	}
}
